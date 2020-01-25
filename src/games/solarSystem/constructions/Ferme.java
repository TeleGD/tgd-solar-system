package games.solarSystem.constructions;

import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import games.solarSystem.Building;
import games.solarSystem.Case;
import games.solarSystem.Player;
import games.solarSystem.Resource;

public class Ferme extends Building {

	private static HashMap<String, Resource> resourcesExploitable ; //TODO Initialiser la HashMap (la même que dans Construction) avec la liste des ressources exploitables par une mine
	static
	{
		resourcesExploitable = new HashMap<String, Resource>();
        resourcesExploitable.put("Nourriture",new Resource("Nourriture"));
	}

	public Ferme (Case tile, Player player){
		super(tile, player);
//		this.posX=tile.getX();
//		this.posY=tile.getY();
		this.lifeMax=80;
		this.life=lifeMax;
		this.debits.put("Nourriture", 0.02);
		this.name = "Ferme super extra bien";

		//Ajout des coûts de la Ferme
		this.cout.put("Bois", 500.0);
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {

	}

	public static boolean constructPossible(Case tileConstructLocation) {
		return resourcesExploitable.containsKey(tileConstructLocation.getResource().getName());
	}
}
