package solar_system.constructions;

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
import solar_system.util.Images;

public class Mine extends Building {

	private static HashMap<String, Resource> resourcesExploitable ; //TODO Initialiser la HashMap (la même que dans Construction) avec la liste des ressources exploitables par une mine
	static 
	{
		resourcesExploitable = new HashMap<>();
        resourcesExploitable.put("Fer",new Resource("Fer"));
	}

	
	public Mine (Case tile, Player player){
		super(tile, player);
//		this.posX=tile.getX();
//		this.posY=tile.getY();
		this.lifeMax=100;
		this.life=lifeMax;
		this.debits.put("Fer", 0.005);
		this.name = "Mine";
		
		this.cout.put("Bois", 2000.0);
		this.cout.put("Nourriture", 2000.0);
		this.entretiens.put("Nourriture", 0.02);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		
	}
	
	public static boolean constructPossible(Case tileConstructLocation) {
		return resourcesExploitable.containsKey(tileConstructLocation.getResource().getName());
	}
	
}
