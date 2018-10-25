package solar_system;

import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Construction {
	protected int life;
	protected int lifeMax;
	protected int posX;
	protected int posY;
	protected String description;
	protected int cout;
	protected Map<String,Double> debits;
	protected boolean destructed;
	protected Case tile;
	protected HashMap<String, Resource> resourcesProduced;
	
	
	public Construction(int lifeMax, int cout,int posX,int posY, Case tile) {
		this.life = life;
		this.lifeMax = life;
		this.posX=posX;
		this.posY=posY;
		this.cout = cout;
		destructed = false;
		
		this.tile = tile;
		
		resourcesProduced = new HashMap<String, Resource>();
		
		Resource resOfCase = tile.getResource() ;
		resourcesProduced.put(resOfCase.getName(), resOfCase);
		debits = new HashMap<String,Double >();
	}
	
	public void takeDamage(int damage) {	
		life -= damage;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) {
		for( Map.Entry resource : resourcesProduced.entrySet())
		{
			Resource res = (Resource) resource;
			double qtiteAjoutee = (double)delta*debits.get(res.getName());			

			if ( res.equals(tile.getResource()) ){   // Cas où la production dépend des ressources sur la case
				qtiteAjoutee = tile.preleveResource(qtiteAjoutee);
			}
			res.modifQuantite(qtiteAjoutee);
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		
	}
	public void isDead() {
		if (life<=0) {
			destructed=true;
		}
	}
	
	public boolean isDestructed() {
		return destructed;
	}

	public void setDetruit(boolean detruit) {
		this.destructed = detruit;
	}
}


