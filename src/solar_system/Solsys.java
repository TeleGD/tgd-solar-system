package solar_system;

import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
	
public class Solsys {
	private int nbPlanet;
	private List<Planet> planets;
		
		
	public Solsys(int nbPlanet) {
		this.nbPlanet= nbPlanet;
		this.planets = new ArrayList<Planet>();
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

