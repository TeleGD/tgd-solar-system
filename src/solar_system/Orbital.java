package solar_system;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Orbital extends Construction {
	
	public Orbital(int lifeMax, int cout,int posX,int posY) {
		super(lifeMax, cout, posX, posY);
	}
	
	public abstract void render (GameContainer container, StateBasedGame game, Graphics context);
	public abstract void update (GameContainer container, StateBasedGame game, int delta);
}
