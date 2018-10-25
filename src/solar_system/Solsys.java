package solar_system;

import java.util.*;
import java.util.Random;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
	
public class Solsys {
	private int nbPlanet;
	private List<Planet> planets;
	private Random r;
		
	public Solsys(int nbPlanet, World world) {
		r= new Random();
		this.nbPlanet= nbPlanet;
		this.planets = new ArrayList<Planet>();
		for (int k=1; k<nbPlanet ; k=k+1 ) {
			addPlanet(new Planet(0,r.nextInt(500)+500,r.nextInt(500)+500,"description",world));
		}
	}
		
	public void addPlanet(Planet p) {
		if (planets.size()<nbPlanet) 
			planets.add(p);
	}
		
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
			for (Planet p: planets) {
				p.render(container, game, context);
			}
	}
		
	public void update(GameContainer container, StateBasedGame game, int delta) {
			for (Planet p : planets) {
				p.update(container, game, delta);
			}
	}
		
}
