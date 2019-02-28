package solar_system;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Orbital {
	private double speed;
	private float size;
	private double angle;
	private int distance;
	private Image backgroundImg;
	private Resource resource;
	private int x;
	private int y;
	
	
	public Orbital(int lifeMax, int cout,int posX,int posY, int size, int distance, Resource resource) {
		this.x = posX;
		this.y = posY;
		this.speed=0.001;
		this.size = (float) size;
		this.angle = 0;
		this.distance = distance+(int)size;
		
		if (this.resource==null){
			this.backgroundImg = null;
		} else {
			try{
				this.backgroundImg = new Image(resource.imagePath(resource.getName()));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
	
	public abstract void render (GameContainer container, StateBasedGame game, Graphics context, boolean arrierePlan);

	public void update (GameContainer container, StateBasedGame game, int delta  /* , int orbitals_size, int i */ ) {
		angle = ( angle + delta*this.getSpeed() ) % (2*Math.PI);
		this.x=(int)(container.getWidth()/2+distance*Math.cos(angle));
		this.y=(int)(container.getHeight()/2-distance*Math.sin(angle)/2);
		//System.out.println("X : "+ posX + " Y : "+ posY);
	}
	
	public int get_x() {
		return(this.x);
	}
	public int get_y() {
		return(this.y);
	}
	public void set_X(int x) {
		this.x = x;
	}
	public void set_Y(int y) {
		this.y = y;
	}
	public double getSpeed() {
		return(this.speed);
	}
}
