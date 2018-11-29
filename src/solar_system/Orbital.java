package solar_system;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Orbital extends Construction {
	private double speed;
	private float size;
	private double angle;
	
	public Orbital(int lifeMax, int cout,int posX,int posY, double speed, double size) {
		super(lifeMax, cout, posX, posY,new Case(posX, posY, 80, new Resource("Fer"), 12));
		this.speed=0.3;
		this.size = (float) size;
		this.angle = 0;
	}
	
	public void render (GameContainer container, StateBasedGame game, Graphics context){
		context.setColor(Color.blue);
		context.fillOval(posX*1f, posY*1f, size * 2, size * 2);
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta, int orbitals_size, int i) {
		angle += delta*this.getSpeed()%(2*Math.PI);
		this.set_X((int)(container.getWidth()/2+size*Math.cos(angle)));
		this.set_Y((int)(container.getHeight()/2-size*Math.sin(angle)));
		//System.out.println("X : "+ posX + " Y : "+ posY);
	}
	
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
