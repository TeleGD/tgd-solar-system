package solar_system;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Velocity {
	private double norm; // Vitesse représentée
	private double angle;
	private int x; // Origine du vecteur
	private int y;
	private int x2; // Pointe de la flèche
	private int y2;
	private int x3; // Premier côté de la flèche
	private int y3;
	private int x4; // Deuxième côté de la flèche
	private int y4;
	
	public Velocity(double norm, double angle) {
		this.norm = norm;
		this.angle = angle;
	}
	
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setVelocity(double norm, double angle) {
		this.norm = norm;
		this.angle = angle;
	}
	
	public void moveAngle(double deltaAngle) {
		this.angle += deltaAngle;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public double getNorm() {
		return this.norm;
	}
	
	public double getAngle() {
		return this.angle;
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
		x2 = (int)(x+norm*Math.cos(angle)*400);
		y2 = (int)(y+norm*Math.sin(angle)*400);
		x3 = (int)(x2+20*Math.cos(angle+5*Math.PI/6));
		y3 = (int)(y2+20*Math.sin(angle+5*Math.PI/6));
		x4 = (int)(x2+20*Math.cos(angle-5*Math.PI/6));
		y4 = (int)(y2+20*Math.sin(angle-5*Math.PI/6));
	}
	
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor(Color.white);
		context.drawLine(x, y, x2, y2);
		context.drawLine(x2, y2, x3, y3);
		context.drawLine(x2, y2, x4, y4);
	}
}
