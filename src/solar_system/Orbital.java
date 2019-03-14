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
	protected float size;
	protected double angle;
	private int distance;
	private Image backgroundImg;
	private Resource resource;
	private int x;
	private int y;
	protected Case tile;
	private World world;
	private int sizeCase;
	
	
	public Orbital(int lifeMax, int cout,float angle,int size, int distance, Resource resource,World world) {
		this.speed=0.001;
		this.size = (float) size;
		this.angle = 0;
		this.distance = distance+size;
		
		if (this.resource==null){
			this.backgroundImg = null;
		} else {
			try{
				this.backgroundImg = new Image(resource.imagePath(resource.getName()));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		this.sizeCase= (int)(80*world.getFacteurMagique());
		this.tile=new Case(0,0,sizeCase);
		tile.setX((int)(distance*Math.cos(angle)+size-this.sizeCase/2));
		tile.setY((int)(distance*Math.sin(angle)+size-this.sizeCase/2));
		this.world=world;		
	}
	
	public void render (GameContainer container, StateBasedGame game, Graphics context){
		this.tile.render(container, game, context);
	}

	public void update (GameContainer container, StateBasedGame game, int delta  /* , int orbitals_size, int i */ ) {
		angle = ( angle + delta*this.getSpeed() ) % (2*Math.PI);
		this.x=(int)(distance*Math.cos(angle));
		this.y=(int)(distance*Math.sin(angle));
		tile.setX((int)(container.getWidth()/2+x-this.sizeCase/2));
		tile.setY((int)(container.getHeight()/2-y/2-this.sizeCase/2));
		
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
	public float get_size(){
		return this.size;
	}
	
	public Case getCase(){
		return this.tile;
	}
	
	public double getAngle(){
		return this.angle;
	}
}
