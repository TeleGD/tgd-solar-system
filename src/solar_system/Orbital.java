package solar_system;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Orbital extends Construction {
	private double speed;
	private float size;
	private double angle;
	private int distance;
	private Image backgroundImg;
	private Resource resource;
	
	public Orbital(int lifeMax, int cout,int posX,int posY, int size, int distance, Resource resource) {
		super(new Case(posX, posY, 80, resource, 12));
		this.posX = posX;
		this.posY = posY;
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
	
	public void render (GameContainer container, StateBasedGame game, Graphics context, boolean arrierePlan){
		if ( (arrierePlan && Math.sin(angle)>0)  ||   (!(arrierePlan) && !(Math.sin(angle)>0)) ) {
		context.setColor(Color.blue);
		context.fillOval(posX*1f-size, posY*1f-size, size * 2, size * 2);
		}
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta  /* , int orbitals_size, int i */ ) {
		angle = ( angle + delta*this.getSpeed() ) % (2*Math.PI);
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
