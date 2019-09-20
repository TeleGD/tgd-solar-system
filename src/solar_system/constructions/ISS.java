package solar_system.constructions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import solar_system.Building;
import solar_system.Case;
import solar_system.Player;
import solar_system.Construction;
import solar_system.Resource;
import solar_system.constructions.Vaisseau;
import solar_system.constructions.vaisseaux.Colonisator;
import solar_system.util.Images;

public class ISS extends Building {
	
	int capacity=10;
	int currentCapacity;
	private HashMap <String,Integer> vaisseaux;
	
	public ISS (Case tile, Player player){
		super(tile,player);
//		this.posX=tile.getX();
//		this.posY=tile.getY();
		this.lifeMax=80;
		this.life=lifeMax;
		this.debits.put("Nourriture", 0.02);
		this.name = "Station intergalactique";
		this.player=player;
		this.cout.put("Noyau Linux", 0.0); //TODO : équilibrer le cout d'une station
		
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		
	}
	
	public void initVaisseaux() { //Crée les hashMap avec tous les vaisseaux possibles dedans, initialisés à 0
		this.vaisseaux.put("Colonisator",0); //Ajouter vaisseaux ici
		this.vaisseaux.put("Tesla",0);
		this.vaisseaux.put("Véço",0);
	}
	
	public void addVaisseau(Vaisseau vaisseau) {//Création d'un vaisseau à partir de l'ISS
		 if(this.currentCapacity<this.capacity) {
			tile.getOrbital().getAir().getPlanet().addVaisseau(vaisseau);//Création d'un vaisseau du type, dans la hashmap de la planète
			this.currentCapacity++;//On augmente la capacité actuelle de l'ISS
			System.out.println(vaisseau.getName());
			for(String str : vaisseaux.keySet()) {
				System.out.println(vaisseaux.get(str));
			}
			Integer n=this.vaisseaux.get(vaisseau.getName());
			this.vaisseaux.put(vaisseau.getName(),++n);
		}
		
	}

	public int getNbVaisseaux(Vaisseau vaisseau) {
		return tile.getOrbital().getAir().getPlanet().getNbVaisseaux(vaisseau.getName());
	}
	
	//Retire le vaisseau du nom demandé à l'ISS
	public void removeVaisseau(String vaisseau) {
		tile.getOrbital().getAir().getPlanet().removeVaisseau(vaisseau);
		this.currentCapacity--;
		Integer n=this.vaisseaux.get(vaisseau);
		this.vaisseaux.put(vaisseau,--n);
//		for (String str : vaisseaux) {
//			for (int i=0 ; i<vaisseaux.get(str);i++) {
//				
//			}
		//}
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public int getCurrentCapacity() {
		return this.currentCapacity;
	}
	
	public int getNbVaisseaux(String vaisseau) {//Récupère le nombre de vaisseau du type vaisseau" construits dans l'ISS
		return vaisseaux.get(vaisseau);
	}
	
	
//	public void addVaisseau(Vaisseau vaisseau) {//Ajoute le vaisseau à l'ISS(à utiliser lorsqu'il est acheté)
//		this.listVaisseaux.add(vaisseau);
//	}
	
//	public static boolean constructPossible(Case tileConstructLocation) {
//		return resourcesExploitable.containsKey(tileConstructLocation.getResource().getName());
//	}

//	public static boolean constructPossible(Case tileConstructLocation) {
//		if (player.getResource(costEntry.getKey()).getQuantite()<costEntry.getValue()){
//			return false;
//		}
//		return true;
//		//return resourcesExploitable.containsKey(tileConstructLocation.getResource().getName());
//	}
	//public static boolean constructPossible(Case tileConstructLocation) {
	//	return (player.getResource("Noyau Linux").getQuantite()>=cout.get("Noyau Linux"));
	
	//}
	
}









	
	
	
	