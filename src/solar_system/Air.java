package solar_system;

import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.util.concurrent.ThreadLocalRandom;

public class Air {
	private int nbOrbitaux;
	private int distance;
	private World world;
	private int sizeCase;
	private int nbSatelite;
	private int nbStation;
	private int radius;
	private Planet planet;
	//private Resource resource;
	private float facteur_magique;
	
	private List<Orbital> orbitals;
	
	public Air(int nbOrbitaux, int distance, int minSatellite, int minStation, World world, Planet planet) {
		this.facteur_magique = (float)(world.getHeight())/1080;
		this.planet = planet;
		this.radius = planet.getRadius2 ();
		this.nbOrbitaux = nbOrbitaux;
		this.distance = distance;
		this.orbitals = new ArrayList<Orbital>();
		this.world = world;
		this.sizeCase = (int)(80*world.getFacteurMagique());
		this.nbSatelite = Math.max(minSatellite, (int) (Math.random()*4));
		//80% de chance d'avoir une station autour d'une planète autre qu'une planète mère.
		this.nbStation = Math.max(minStation, (int) (Math.random()*100));//Générer int aléatoire.
		if(this.nbStation>20) {
			this.nbStation=1;
		}
		else {
			this.nbStation=0;
		}
		
		//System.out.println("Decompte :\n"+nbSatelite+"\n"+nbStation+"\n");
		int nbOrbitals = this.nbSatelite+this.nbStation;
		Resource resource;
		for(int i=0;i<this.nbSatelite;i++){
			resource = world.getPlayer().getResource(planet.getGround().randomResourceName());//On génère le nom d'une ressource aléatoirement.
			int rand = resource.resourceQuantity(true);
			orbitals.add(new Satellite(20,0,(float)(Math.PI*i*2/nbOrbitals), 50,(int)(5.0/4*radius),resource,rand,this.world,this));
			//System.out.println(planet.getGround().randomResourceName());
//			orbitals.get(i).getCase().setResource(resource2);//Ona ajoute cette ressource sur le satelliite qu'on vient de créer.
//			orbitals.get(i).getCase().setRessourceQuantity(resource2.getQuantite());
		}
		for(int j=0;j<this.nbStation;j++){
			orbitals.add(new Station(20,0,(float)(Math.PI*(j+this.nbSatelite)*2/nbOrbitals), 50,(int)(5.0/4*radius), this.world,this));
		}
	
	}
	
	public Orbital mousePressed(int arg0,int x ,int y) {//Retourne l'orbitale sur laquelle on a clique, null si on ne clique sur aucune orbitale.
		// padding = marge intérieure (distance entre la grille et le bord de l'image)
		double distance;
		for (Orbital orbital : orbitals){
			//if(orbital.getCase()!=)
			//Correspond aux coordonnées du coin en haut à gauche de la case sur le satellite ou la station.
//			int coin_x = (int)(orbital.get_x()+orbital.get_size()-this.sizeCase/2);//Math.round(orbital.get_x()+orbital.get_size()-sizeCase);
//			int coin_y = (int)(orbital.get_y()+orbital.get_size()-this.sizeCase/2);//Math.round(orbital.get_y()+orbital.get_size()-sizeCase);
			distance=Math.sqrt(Math.pow((x-world.getWidth()/2),2)+Math.pow((y-world.getHeight()/2),2));
			//System.out.println(distance+" : "+);
			if(planet.getPosY()>orbital.get_y() || distance>this.radius*this.facteur_magique){//planet.getRadius()>orbital.get_distance()){
				if(orbital.getCase().mousePressed(arg0,x ,y)){
					
					return orbital;				
				}
			}
			
			//System.out.println(coin_x+" "+coin_y+" "+orbital.get_size()+" "+this.sizeCase);
		}
		//System.out.println("Oh non, tu as cliqué à côté du satellite...");
		//System.out.println(this.nbSatelite+" "+this.nbStation);
		return null;

		
	}
	
	public void filterOrbitals(List<Orbital> listFront,List<Orbital> listBack){
		for (Orbital orbital : orbitals){
			if(Math.sin(orbital.getAngle())>0){
				listBack.add(orbital);
			}
			else{
				listFront.add(orbital);
			}
		}
	}
	
	
	
	
	public void addOrbital(Orbital o) {
		if (orbitals.size() < nbOrbitaux) {
			orbitals.add(o);
		}
		else
			System.out.println("Plus d'orbitaux dispo");
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
		//int i = 0;
		for (Orbital o : orbitals) {
			o.update(container, game, delta);
			//i++;
		}
	}
	
	public List<Orbital> getOrbitals(){
		return orbitals;
	}
	
	public Planet getPlanet() {
		return this.planet;
	}
	
}
