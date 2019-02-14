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
		
	public Solsys(int nbPlanet, World world) {
		r= new Random();
		this.nbPlanet= nbPlanet;
		this.planets = new ArrayList<Planet>();
		this.world = world;
		//  addPlanet(new Planet(9,0,0,75,"des",world));  Permet d'ajouter une planète à la place du Soleil
		for(int k=0; k<nbPlanet; k++ ) {
			addPlanet(new Planet(1+k,(float)0.5*r.nextFloat()*2f*(float)Math.PI ,200+100*k ,"description",world));
		}
		try{
			this.imageSun = new Image("res/images/planets/soleil.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.imageSun=imageSun.getScaledCopy(300,300);
	}
	
	public void addPlanet(Planet p) {
		if (planets.size()<nbPlanet)
			planets.add(p);
	}
		
	public void render(GameContainer container, StateBasedGame game, Graphics context) {

		context.drawImage(imageSun,world.getWidth()/2-150,world.getHeight()/2-150);
		for (Planet p: planets) {
			p.render(container, game, context);
		}

	}
		
	public void update(GameContainer container, StateBasedGame game, int delta) {
		for (Planet p : planets) {
			p.update(container, game, delta);
		}
	}
	
	public Ground planetTouched(int x, int y) 
	{
		for(Planet p : planets) {
			if(Math.hypot(x-p.getPosX()-world.getWidth()/2, y-p.getPosY()-world.getHeight()/2) < p.getRadius()) {
				return p.getGround();
			}
		}
		return null;
	}
	
}

