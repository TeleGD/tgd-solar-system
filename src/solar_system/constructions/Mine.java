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
public class Mine extends Construction {

	public static HashMap<String, Resource> resourcesExploitable ; //TODO Initialiser la HashMap (la même que dans Construction) avec la liste des ressources exploitables par une mine
	static 
	{
		resourcesExploitable = new HashMap<String, Resource>();
        resourcesExploitable.put("Fer",new Resource("Fer"));
	}
	
	public Mine (int lifeMax, int cout,int posX,int posY, Case tile){
		super(lifeMax, cout, posX, posY, tile);
//		this.image = Images.getConstruction("mine");
		this.lifeMax=100;
		this.life=lifeMax;
		this.debits.put("Fer", 5.0);
		this.name = "Mine";
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		
	}
}

