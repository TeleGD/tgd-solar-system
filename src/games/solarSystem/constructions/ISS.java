package games.solarSystem.constructions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import games.solarSystem.Building;
import games.solarSystem.Case;
import games.solarSystem.Construction;
import games.solarSystem.Player;
import games.solarSystem.constructions.Vaisseau;

public class ISS extends Building {

	public ISS (Case tile, Player player){
		super(tile,player);
//		this.posX=tile.getX();
//		this.posY=tile.getY();
		this.lifeMax=80;
		this.life=lifeMax;
		//this.debits.put("Nourriture", 0.02);
		this.name = "Station intergalactique";
		Construction.player = player;
		cout.put("Bois", 100.0); //TODO : équilibrer le cout d'une station
		cout.put("Nourriture", 500.0);
		cout.put("Fer", 100.0);

	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {

	}

	public void addVaisseau(Vaisseau vaisseau) {
		tile.getOrbital().getAir().getPlanet().addVaisseau(vaisseau);
	}

	public int getNbVaisseaux(Vaisseau vaisseau) {
		return tile.getOrbital().getAir().getPlanet().getNbVaisseaux(vaisseau.getName());
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
	//	return (player.getResource("Noyaux Linux").getQuantite()>=cout.get("Noyaux Linux"));

	//}

}
