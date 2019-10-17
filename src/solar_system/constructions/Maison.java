package solar_system.constructions;

import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import solar_system.Building;
import solar_system.Case;
import solar_system.Player;
import solar_system.Resource;

public class Maison extends Building{
	
	private static HashMap<String, Resource> resourcesExploitable ;
	static 
	{
		resourcesExploitable = new HashMap<>();
        resourcesExploitable.put("Noyaux Linux",new Resource("Noyaux Linux"));
	}
	
	public Maison (Case tile, Player player){
		super(tile, player);
		this.lifeMax=100;
		this.life=lifeMax;
		this.debits.put("Noyaux Linux", 0.001);
		this.name = "Maison";
		
		this.cout.put("Bois", 500.0);
		this.entretiens.put("Nourriture", 0.1);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		
	}
	
	public static boolean constructPossible(Case tileConstructLocation) {
		
		boolean bool=resourcesExploitable.containsKey(tileConstructLocation.getResource().getName());
		return bool;
	}

}
