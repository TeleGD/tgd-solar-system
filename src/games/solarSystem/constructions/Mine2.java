package games.solarSystem.constructions;

import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import games.solarSystem.Building;
import games.solarSystem.Case;
import games.solarSystem.Player;
import games.solarSystem.Resource;

public class Mine2 extends Building {

	private static HashMap<String, Resource> resourcesExploitable ; //TODO Initialiser la HashMap (la même que dans Construction) avec la liste des ressources exploitables par une mine
	static
	{
		resourcesExploitable = new HashMap<>();
        resourcesExploitable.put("Fer",new Resource("Fer"));
	}

	public Mine2 (Case tile, Player player){
		super(tile, player);
//		this.posX=tile.getX();
//		this.posY=tile.getY();
		this.lifeMax=100;
		this.life=lifeMax;
		this.debits.put("Fer", 0.05);
		this.name = "Mine B-)";

		//Ajout des coûts de la Mine
		this.cout.put("Fer", 2000.0);
		this.cout.put("Nourriture", 5000.0);
		this.entretiens.put("Nourriture", 0.04);
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
	}

	public static boolean constructPossible(Case tileConstructLocation) {
		return resourcesExploitable.containsKey(tileConstructLocation.getResource().getName());
	}

}
