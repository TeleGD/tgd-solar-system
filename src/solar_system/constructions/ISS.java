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
	
	public void addVaisseau(Vaisseau vaisseau) {
		tile.getOrbital().getAir().getPlanet().addVaisseau(vaisseau);
	}

	public int getNbVaisseaux(Vaisseau vaisseau) {
		return tile.getOrbital().getAir().getPlanet().getNbVaisseaux(vaisseau);
	}
	
	//Retire le vaisseau du nom demandé à l'ISS
	public void removeVaisseau(String vaisseau) {
		tile.getOrbital().getAir().getPlanet().removeVaisseau(vaisseau);
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









	
	
	
	