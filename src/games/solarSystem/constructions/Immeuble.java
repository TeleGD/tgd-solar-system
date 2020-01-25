package games.solarSystem.constructions;

import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import games.solarSystem.Building;
import games.solarSystem.Case;
import games.solarSystem.Player;
import games.solarSystem.Resource;

public class Immeuble extends Building{
	private static HashMap<String, Resource> resourcesExploitable ;
	static 
	{
		resourcesExploitable = new HashMap<>();
        resourcesExploitable.put("Noyaux Linux",new Resource("Noyaux Linux"));
	}
	
	public Immeuble (Case tile, Player player){
		super(tile, player);
		this.lifeMax=100;
		this.life=lifeMax;
		this.debits.put("Noyaux Linux", 0.01);
		this.name = "Maison";
		
		this.cout.put("Bois", 1000.0);
		this.cout.put("Fer", 500.0);
		this.entretiens.put("Nourriture", 0.1);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		
	}
	
	public static boolean constructPossible(Case tileConstructLocation) {
		
		boolean bool=resourcesExploitable.containsKey(tileConstructLocation.getResource().getName());
		return bool;
	}

}
