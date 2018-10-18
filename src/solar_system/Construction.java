package solar_system;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Construction {
	protected int life;
	protected int lifeMax;
	protected ArrayList<Production> productions;
	protected String caracteristiques;
	protected Image image;
	protected boolean detruit;
	protected Player player;
	
	public Construction() {
		detruit=false;
	}
	
	public void takeDamage(int damage) {	
		life -= damage;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) {
		for (Production prod :productions) {
			prod.update(container, game, delta);
		}
	}
	public void render() {
		
	}
	public void isDead() {
		if (life<=0) {
			detruit=true;
		}
	}
	
	public boolean isDetruit() {
		return detruit;
	}

	public void setDetruit(boolean detruit) {
		this.detruit = detruit;
	}
}


