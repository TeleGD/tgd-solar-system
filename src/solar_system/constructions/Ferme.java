package solar_system.constructions;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import solar_system.Case;
import solar_system.Construction;
import solar_system.Resource;
import solar_system.util.Images;

public class Ferme extends Construction {

	private static HashMap<String, Resource> resourcesExploitable ; //TODO Initialiser la HashMap (la même que dans Construction) avec la liste des ressources exploitables par une mine
	static 
	{
		resourcesExploitable = new HashMap<String, Resource>();
        resourcesExploitable.put("Nourriture",new Resource("Nourriture"));
	}
	
	public Ferme (Case tile){
		super(tile);
		this.posX=tile.getX();
		this.posY=tile.getY();
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