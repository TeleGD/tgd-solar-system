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

	public static HashMap<String, Resource> resourcesExploitable ; //TODO Initialiser la HashMap (la même que dans Construction) avec la liste des ressources exploitables par une mine
	static 
	{
		resourcesExploitable = new HashMap<String, Resource>();
        resourcesExploitable.put("Fer",new Resource("Fer"));
	}
	
	public Ferme (Case tile){
		super(tile);
		this.posX=tile.getX();
		this.posY=tile.getY();
		this.lifeMax=80;
		this.life=lifeMax;
		this.debits.put("Fer", 0.5);
		this.name = "Ferme super extra bien";
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		
	}
}