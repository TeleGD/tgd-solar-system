package solar_system;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import solar_system.util.Images;

public class Planet {
	
	
	static Random r = new Random();
	private int type;
	private String description;
	private Ground ground;
	private Air air;
	private int radius;
	private float angle,distance,posx,posy;
	private World world;
	private Image image;
	private Color color;
	private float mass,periode;
	//private Orbital orbital;
	
	public Planet(int type,float angle,float distance, String description, World world) {
		this(type,angle,distance,r.nextInt(50)+50,description, world);
	}
	
	public Planet(int type,float angle, float distance ,int radius, String description, World world) {
		this.angle=angle;
		this.distance=distance;
		this.type = type;
		this.description=description;
		this.radius = radius;
		//this.ground = new Ground(this, world);
		this.air = new Air(2,(int)(5.0/4)*radius);
		this.image = Images.getPlanet(/*r.nextInt(4)*/0);
		this.world=world;
		this.mass=(4f/3f)*(float)Math.PI*(float)Math.pow((double)radius,3)*type;
		this.periode=(float)Math.sqrt(Math.pow((double)radius, 3));
		this.color=new Color(type*120,0,255/type);
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		angle+=(float)delta/periode;
		posx=(float)Math.cos((double)angle)*distance;
		posy=(float)Math.sin((double)angle)*distance;
		air.update(container, game, delta);
		
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
		context.setColor(color);
		context.fillOval(posx+(world.getWidth()-radius)/2, posy+(world.getHeight()-radius)/2, radius, radius);
		air.render(container, game, context);
	}
	
	
	public int getRadius() {
		return radius;
	}
	
}
