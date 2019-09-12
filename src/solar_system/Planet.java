package solar_system;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import app.AppLoader;

import solar_system.util.Images;

public class Planet {


	static Random r = new Random();
	private int type;
	private String description;
	private Ground ground;
	private int radius;
	private int radius2;
	private float angle,distance,posx,posy;
	private World world;
	private Color color;
	private float mass,periode;
	private String nomImage;
	private Image image;
	private float coeffSpeed; // Permet de résuire la vitesse de rotations des planètes
	private Air air;
	private HashMap<String, Integer> minResources;
	private List<Orbital> orbitals;
	private Player owner;
	
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
		this.color=new Color(type*120,0,255/type);
		posx=(float)Math.cos((double)this.angle)*this.distance;
		posy=(float)Math.sin((double)this.angle)*this.distance;
		Random rnd = new Random();
		this.nomImage = "/images/planets/"+rnd.nextInt(6)+".png";
		this.image = AppLoader.loadPicture(nomImage);
		this.image=image.getScaledCopy(radius*2,radius*2);
		this.image.setAlpha(0.6f);
		this.minResources = minResources;
		this.ground = new Ground(this, world);
		this.air = new Air(5,(int)(5.0/4)*radius2, minSatellite, minStation, world, this);
		this.orbitals = air.getOrbitals();
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

	public void setVelocityVector(Velocity v) {
		System.out.println("On ajoute une vélocité !");
	}

	public void mouseWheelMoved(int change) {
		ground.mouseWheelMoved(change);
	}

}
