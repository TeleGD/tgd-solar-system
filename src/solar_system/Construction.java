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
	protected Case c;
	
	public Construction(int lifeMax, int cout,int posX,int posY) {
		this.life = life;
		this.lifeMax = life;
		this.posX=posX;
		this.posY=posY;
		this.cout = cout;
		destructed = false;
		
		c = null;
		
		debits = new HashMap<String,Double >();
	}
	
	public Construction(int lifeMax, int cout,int posX,int posY, Case c) {
		this.life = life;
		this.lifeMax = life;
		this.posX=posX;
		this.posY=posY;
		this.cout = cout;
		destructed = false;
		
		this.c = c;
		
		debits = new HashMap<String,Double >();
	}
	
	public void takeDamage(int damage) {	
		life -= damage;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) {

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


