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
	private int distance;
	
	public Orbital(int lifeMax, int cout,int posX,int posY, double speed, double size, int distance) {
		super(lifeMax, cout, posX, posY,new Case(posX, posY, 80, new Resource("Fer"), 12));
		this.speed=0.001;
		this.size = (float) size;
		this.angle = 0;
		this.distance = distance+(int)size;
	}
	
	public void render (GameContainer container, StateBasedGame game, Graphics context, boolean arrierePlan){
		if ( (arrierePlan && Math.sin(angle)>0)  ||   (!(arrierePlan) && !(Math.sin(angle)>0)) ) {
		context.setColor(Color.blue);
		context.fillOval(posX*1f-size, posY*1f-size, size * 2, size * 2);
		}
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta  /* , int orbitals_size, int i */ ) {
		angle += delta*this.getSpeed()%(2*Math.PI);
		this.set_X((int)(container.getWidth()/2+distance*Math.cos(angle)));
		this.set_Y((int)(container.getHeight()/2-distance*Math.sin(angle)/2));
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
