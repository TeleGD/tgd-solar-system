package solar_system;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import solar_system.util.Images;

public class Planet {
	
	private int type;
	private String description;
	private Ground ground;
	private Air air;
	private int radius;
	
	private Random r;
	
	private Image image;
	private Color color;
	//private Orbital orbital;
	
	public Planet(int type, String description) {
		r = new Random();
		this.type = type;
		this.description=description;
		this.radius = r.nextInt(400)+400;
		this.ground = new Ground(this, null);
		this.air = new Air(2,(int)(5.0/4)*radius);
		this.image = Images.getPlanet(/*r.nextInt(4)*/0);
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
		context.drawImage(image,container.getWidth()/2-radius,container.getHeight()/2-radius,container.getWidth()/2+radius,container.getHeight()/2+radius, 0, 0, image.getWidth()-1, image.getHeight()-1, new Color((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255)));

	}
	
	
	public int getRadius() {
		return radius;
	}
	
}
