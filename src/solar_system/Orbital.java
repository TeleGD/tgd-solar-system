package solar_system;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Orbital extends Construction {
	private double speed;
	public Orbital(int lifeMax, int cout,int posX,int posY, double speed) {
		super(lifeMax, cout, posX, posY);
		this.speed=0.3;
	}
	
	public abstract void render (GameContainer container, StateBasedGame game, Graphics context);
	public abstract void update (GameContainer container, StateBasedGame game, int delta);
	
	public int get_X() {
		return(this.posX);
	}
	public int get_Y() {
		return(this.posY);
	}
	public void set_X(int x) {
		this.posX = x;
	}
	public void set_Y(int y) {
		this.posY = y;
	}
	public double getSpeed() {
		return(this.speed);
	}
}
