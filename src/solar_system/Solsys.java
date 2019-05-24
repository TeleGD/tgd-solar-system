package solar_system;


import java.util.*;
import java.util.Random;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
	
public class Solsys {
	private int nbPlanet;
	private List<Planet> planets;
	private Random r;
	private World world;
	private Image imageSun;
	private Spaceship spaceship;
		
	public Solsys(int nbPlanet, World world) {
		r= new Random();
		this.nbPlanet= nbPlanet;
		this.planets = new ArrayList<Planet>();
		this.world = world;
		//  addPlanet(new Planet(9,0,0,75,"des",world));  Permet d'ajouter une planète à la place du Soleil
		for(int k=0; k<nbPlanet; k++ ) {
			addPlanet(new Planet(1+k,200+100*k ,"description",world));
		}
		try{
			this.imageSun = new Image("res/images/planets/soleil.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.imageSun=imageSun.getScaledCopy(300,300);
		this.spaceship = new Spaceship(900, 600, 0.1, -0.3, world);
	}
	
	public void addPlanet(Planet p) {
		if (planets.size() < nbPlanet)
			planets.add(p);
	}
		
	public void render(GameContainer container, StateBasedGame game, Graphics context) {

		context.drawImage(imageSun,world.getWidth()/2-150,world.getHeight()/2-150);
		for (Planet p: planets) {
			int radius =p.getRadius();
			context.drawImage(p.getImage(),p.getPosX()+world.getWidth()/2-radius,p.getPosY()+world.getHeight()/2-radius);
		}
		//planets.get(0).render(container, game, context);
		this.spaceship.render(container, game, context);
	}
		
	public void update(GameContainer container, StateBasedGame game, int delta) {
		for (Planet p : planets) {
			float angle=p.getAngle()+(float)delta/p.getPeriode();
			p.setAngle(angle);
			float distance=p.getDistance();
			p.setPosX((float)Math.cos((double)angle)*distance);
			p.setPosY((float)Math.sin((double)angle)*distance);
			p.update(container, game, delta);
		}
		this.spaceship.update(container, game, delta);
	}
	
	public Planet planetTouched(int x, int y) 
	{
		for(Planet p : planets) {
			if(Math.hypot(x-p.getPosX()-world.getWidth()/2, y-p.getPosY()-world.getHeight()/2) < p.getRadius()) {
				return p;
			}
		}
		return null;
	}
	
	public void mouseWheelMoved(int change) {
		for (Planet p : planets) {
			p.mouseWheelMoved(change);
		}
	}
	
}

