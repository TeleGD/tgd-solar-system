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
	private int radius;
	private int posx,posy;
	private Random r;
	
	private Image image;
	private Color color;
	//private Orbital orbital;
	
	public Planet(int type,int posx,int posy, String description, World world) {
		r = new Random();
		this.posx=posx;
		this.posy=posy;
		this.type = type;
		this.description=description;
		this.radius = r.nextInt(100)+100;
		//this.ground = new Ground(this, world);
		this.image = Images.getPlanet(/*r.nextInt(4)*/0);
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
		context.setColor(color);
		context.fillOval(posx, posy, radius, radius);
	}
	
	
	public int getRadius() {
		return radius;
	}
	
}
