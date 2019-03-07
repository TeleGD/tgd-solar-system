package solar_system.constructions;

import java.util.HashMap;

import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import solar_system.Case;
import solar_system.Player;
import solar_system.Construction;
import solar_system.Resource;
import solar_system.util.Images;

public class Mine2 extends Construction {

	private static HashMap<String, Resource> resourcesExploitable ; //TODO Initialiser la HashMap (la même que dans Construction) avec la liste des ressources exploitables par une mine
	static 
	{
		resourcesExploitable = new HashMap<>();
        resourcesExploitable.put("Fer",new Resource("Fer"));
	}
	
	public Mine2 (Case tile, Player player){
		super(tile, player);
		this.posX=tile.getX();
		this.posY=tile.getY();
		this.lifeMax=100;
		this.life=lifeMax;
		this.debits.put("Fer", 0.05);
		this.name = "Mine B-)";
		
		//Ajout des coûts de la Mine
		this.cout.put("Fer", 2000.0);
		this.coutPerpetuel.put("Nourriture", 0.04);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
	}
	
	public static boolean constructPossible(Case tileConstructLocation) {
		return resourcesExploitable.containsKey(tileConstructLocation.getResource().getName());
	}
	
}
