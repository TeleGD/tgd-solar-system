package games.solarSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import games.solarSystem.constructions.Vaisseau;
import games.solarSystem.constructions.vaisseaux.Debris;

import app.AppLoader;

public class Planet {

	static Random r = new Random();
	private int type;
	private String description;
	private Ground ground;
	private int radius;
	private int radius2;
	private float angle,distance,posx,posy;
	private World world;
	private float mass,periode;
	private boolean destructed;
	private String nomImage;
	private Image image;
	private float coeffSpeed; // Permet de résuire la vitesse de rotations des planètes
	private Air air;
	private HashMap<String, Integer> minResources;
	private List<Orbital> orbitals;
	private Player owner;
	private HashMap <String,ArrayList<Vaisseau>> vaisseaux;
	private ArrayList<Debris> debris;


	// ArrayList<HashMap<String, Integer>> resourcesMin,

	public Planet(int type, float distance, String description, World world) {
		this(type, distance, (r.nextInt(50)+50)/2, null, 0, 0, description, world);
	}

	public Planet(int type, float distance, int radius, String description, World world) {
		this(type, distance, radius, null, 0, 0, description, world);
	}

	public Planet(int type, float distance, HashMap<String, Integer> minResources, String description, World world) {
		this(type, distance, (r.nextInt(50)+50)/2, minResources, 0, 0, description, world);
	}

	public Planet(int type, float distance ,int radius, HashMap<String, Integer> minResources, int minSatellite, int minStation, String description, World world) {
		this.radius2 = (int) Math.floor(radius*8.1);
		this.angle=(float) (r.nextDouble()*Math.PI*2);
		this.distance=distance;
		this.type = type;
		this.description=description;
		this.radius = radius;
		this.world=world;
		this.mass=(4f/3f)*(float)Math.PI*(float)Math.pow((double)radius,3)*type;
		this.periode=(float)Math.sqrt(Math.pow((double)radius, 3));
		this.coeffSpeed = 10f;
		periode *= coeffSpeed;
		posx=(float)Math.cos((double)this.angle)*this.distance;
		posy=(float)Math.sin((double)this.angle)*this.distance;
		Random rnd = new Random();
		this.nomImage = "/images/solarSystem/planets/"+rnd.nextInt(6)+".png";
		this.image = AppLoader.loadPicture(nomImage);
		this.image = image.getScaledCopy(radius*2,radius*2);
		this.image.setAlpha(0.6f);
		this.minResources = minResources;
		this.ground = new Ground(this, world);
		this.air = new Air(5,(int)(5.0/4)*radius2, minSatellite, minStation, world, this);
		this.orbitals = air.getOrbitals();
		this.vaisseaux = new HashMap<String,ArrayList<Vaisseau>>();//vaisseaux est la hashmap contenant la liste des vaisseaux associée à leur quantité dans l'ISS
		this.debris = new ArrayList<>();
		initVaisseaux();
	}

	public void update (GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		ground.update(container, game, delta);
		air.update(container, game, delta);
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */

		//air.render(container, game, context, true);
		List<Orbital> listFront= new ArrayList<Orbital>();//Liste des orbitales devant la planète
		List<Orbital> listBack= new ArrayList<Orbital>();//Orbitales derrières la planète
		air.filterOrbitals(listFront, listBack);
		for(Orbital orbital : listBack){//On affihe les orbitales derrières la planète
			orbital.render(container, game, context);
		}
		ground.render(container, game, context);//Affichage planète
		for(Orbital orbital : listFront){//Affichage orbitales devant
			orbital.render(container, game, context);
		}
		ground.renderMenuConstruct(container, game, context);
	}

	public String getNomImage() {
		return nomImage;
	}

	public int getRadius() {
		return radius;
	}

	public int getRadius2() {
		return radius2;
	}

	public float getPosX() {
		return posx;
	}

	public float getPosY() {
		return posy;
	}

	public void setPosX(float x){
		this.posx=x;
	}

	public void setPosY(float y){
		this.posy=y;
	}

	public Player getOwner() {
		return this.owner;
	}

	public void setOwner(Player p) {
		this.owner = p;
		this.image.setAlpha(1);
	}

	public float getDistance(){
		return distance;
	}

	public float getPeriode(){
		return periode;
	}

	public float getAngle(){
		return this.angle;
	}

	public void setAngle(float angle){
		this.angle=angle;
	}

	public Image getImage(){
		return this.image;
	}

	public Ground getGround() {
		return ground;
	}

	public Air getAir(){
		return air;
	}

	public HashMap<String, Integer> getMinResources() {
		return this.minResources;
	}

	public void mouseWheelMoved(int change) {
		ground.mouseWheelMoved(change);
	}

	///A PROPOS DES VAISSEAUX APPARTENANT À LA PLANÈTE :
	public void initVaisseaux() { //Crée les hashMap avec tous les vaisseaux possibles dedans, initialisés à 0
		this.vaisseaux.put("Colonisator",new ArrayList<Vaisseau>()); //Ajouter vaisseaux ici
		this.vaisseaux.put("Missile",new ArrayList<Vaisseau>());
		this.vaisseaux.put("Tesla",new ArrayList<Vaisseau>());
	}

	public void addVaisseau(Vaisseau vaisseau) { //Ajoute un vaisseau de type vaisseau
		if (this.vaisseaux.containsKey(vaisseau.getName())) {
			this.vaisseaux.get(vaisseau.getName()).add(vaisseau);
		}
	}

	public int getNbVaisseaux(String name) {
		return vaisseaux.get(name).size();
	}

	public Set<String> getVaisseauxNames() {
		return this.vaisseaux.keySet();
	}

	public Vaisseau getVaisseau(String name) {
		if (!this.vaisseaux.get(name).isEmpty()) {
			return this.vaisseaux.get(name).get(0);
		} return null;
	}

	//Retire le vaisseau du nom demandé à l'ISS
	public void removeVaisseau(String vaisseau) { //Retire le vaisseau à l'ISS (à utiliser lorqu'il est lancé)
		if(!this.vaisseaux.get(vaisseau).isEmpty()) { //Il y a au moins un vaisseau du type demandé
			this.vaisseaux.get(vaisseau).remove(0); //On enlève le premier vaisseau, si liste non vide, existe toujours
		}
	}

	public void destruct(int n) {
		if (!destructed) {
			double x = this.image.getWidth()*1d/n;
			double y = this.image.getHeight()*1d/n;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					Debris d = new Debris(owner, world.getSolsys());
					d.launch((int)(posx+i*1.1*x), (int)(posy+j*1.1*y), Math.random()-0.5, Math.random()-0.5);
					d.setImage(this.image.getSubImage((int)(i*x), (int)(j*y), (int)x, (int)y));
					debris.add(d);
				}
			}
		}
		this.destructed = true;
	}

	public boolean isDestructed() {
		return this.destructed;
	}

	public ArrayList<Debris> getDebris() {
		return this.debris;
	}

}
