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

public class TNCY extends Building {

	private static HashMap<String, Resource> resourcesExploitable ;
	static 
	{
		resourcesExploitable = new HashMap<>();
        resourcesExploitable.put("Cailloux",new Resource("Cailloux"));
        resourcesExploitable.put("Noyaux Linux éduqués",new Resource("Noyaux Linux éduqués"));
	}
	
	public TNCY (Case tile, Player player){
		super(tile, player);
//		this.posX=tile.getX();
//		this.posY=tile.getY();
		this.lifeMax=100;
		this.life=lifeMax;
		this.debits.put("Noyau Linux éduqués", 0.01);
		this.name = "Télécom Nancy";
		
		this.cout.put("Fer", 5000.0);
		this.cout.put("Bois", 500.0);
		this.cout.put("Nourriture", 10000.0);
		this.coutPerpetuel.put("Noyau Linux", 0.01);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		
	}
	
	public static boolean constructPossible(Case tileConstructLocation) {
		return resourcesExploitable.containsKey(tileConstructLocation.getResource().getName());
	}
	
}
