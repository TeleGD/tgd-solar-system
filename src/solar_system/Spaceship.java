package solar_system;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Spaceship {
	private int x;
	private int y;
	private double vx;
	private double vy;
	private World world;
	
	public Spaceship(int x0, int y0, double vx0, double vy0, World world) {
		this.x = x0;
		this.y = y0;
		this.vx = vx0;
		this.vy = vy0;
		this.world = world;
	}
	
	public void nextOrbitalPosition(int delta) {
		int rx = this.x - world.getWidth()/2;
		int ry = this.y - world.getHeight()/2;
		double r3 = Math.pow(rx*rx+ry*ry, 1.5);
		vx -= delta*40*rx/r3;	// Variation de la vitesse = accélération
		vy -= delta*40*ry/r3;
		x += (int) delta*vx;
		y += (int) delta*vy;
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
		nextOrbitalPosition(delta);
	}
	
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor(Color.green);
		context.fillOval(x, y, 48, 48);
	}
}
