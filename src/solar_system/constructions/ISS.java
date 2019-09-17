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
	
	private HashMap <String,ArrayList<Vaisseau>> vaisseaux;
	private ArrayList<Vaisseau> listVaisseaux;//CHANGER
	
	public ISS (Case tile, Player player){
		super(tile,player);
//		this.posX=tile.getX();
//		this.posY=tile.getY();
		this.lifeMax=80;
		this.life=lifeMax;
		this.debits.put("Nourriture", 0.02);
		this.name = "Station intergalactique";
		this.player=player;
		this.vaisseaux = new HashMap<String,ArrayList<Vaisseau>>();//vaisseaux est la hashmap contenant la liste des vaisseaux associée à leur quantité dans l'ISS
		initVaisseaux();
		this.cout.put("Noyau Linux", 0.0); //TODO : équilibrer le cout d'une station
		
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		
	}
	
	public void initVaisseaux() { //Crée les hashMap avec tous les vaisseaux possibles dedans, initialisés à 0
		this.vaisseaux.put("Colonisator",new ArrayList<Vaisseau>()); //Ajouter vaisseaux ici
	}
	
	public void addVaisseau(Vaisseau vaisseau) { //Ajoute un vaisseau de type vaisseau
		if (this.vaisseaux.containsKey(vaisseau.getName())) {
			this.vaisseaux.get(vaisseau.getName()).add(vaisseau);
		}	
		
	}
	
	public int getNbVaisseaux(Vaisseau vaisseau) {
		return vaisseaux.get(vaisseau.getName()).size();
	}
	
//	public void addVaisseau(Vaisseau vaisseau) {//Ajoute le vaisseau à l'ISS(à utiliser lorsqu'il est acheté)
//		this.listVaisseaux.add(vaisseau);
//	}
	
	//Retire le vaisseau du nom demandé à l'ISS
	public void removeVaisseau(String vaisseau) {//Retire le vaisseau à l'ISS (à utiliser lorqu'il est lancé)
		if(!this.vaisseaux.get(vaisseau).isEmpty()) {//Il y a au moins un vaisseau du type demandé
			this.vaisseaux.get(vaisseau).remove(0);//On enlève le premier vaisseau, si liste non vide, existe toujours
		}
		
	}
	
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









	
	
	
	