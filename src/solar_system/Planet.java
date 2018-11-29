package solar_system;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
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
	private Color color;
	private float mass,periode;
	private Orbital orbital;
	private String nomImage;
	private Image image;
	
	public Planet(int type,float angle,float distance, String description, World world) {
		this(type,angle,distance,(r.nextInt(50)+50)/2,description, world);
	}
	
	public Planet(int type,float angle, float distance ,int radius, String description, World world) {
		this.angle=(float) (r.nextDouble()*Math.PI*2);
		this.distance=distance;
		this.type = type;
		this.description=description;
		this.radius = radius;
		this.air = new Air(2,(int)(5.0/4)*radius);
		this.world=world;
		this.mass=(4f/3f)*(float)Math.PI*(float)Math.pow((double)radius,3)*type;
		this.periode=(float)Math.sqrt(Math.pow((double)radius, 3));
		this.color=new Color(type*120,0,255/type);
		posx=(float)Math.cos((double)angle)*distance;
		posy=(float)Math.sin((double)angle)*distance;
		this.orbital=new Satellite(20,0,0,0,0.3);
		air.addOrbital(orbital);
		
		Random rnd = new Random();
		this.nomImage = "res/images/planets/"+rnd.nextInt(4)+".png";
		try{
			this.image = new Image(nomImage);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.image=image.getScaledCopy(radius*2,radius*2);
		this.ground = new Ground(this, world);
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		angle+=(float)0.1*delta/periode;
		posx=(float)Math.cos((double)angle)*distance;
		posy=(float)Math.sin((double)angle)*distance;
		air.update(container, game, delta);
		ground.update(container, game, delta);
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
		context.drawImage(image,posx+world.getWidth()/2-radius,posy+world.getHeight()/2-radius);
		//context.setColor(color);
		//context.fillOval(posx+world.getWidth()/2-radius, posy+world.getHeight()/2-radius, radius * 2, radius * 2);
		air.render(container, game, context);
		}
	
	public String getNomImage() {
		return nomImage;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public 	float getPosX() {
		return posx;
	}
	
	public float getPosY() {
		return posy;
	}
	
	public Ground getGround() {
		return ground;
	}
}
