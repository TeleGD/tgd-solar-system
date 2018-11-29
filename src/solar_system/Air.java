package solar_system;

import java.util.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Air {
	private int nbOrbitaux;
	private int distance;
	
	private List<Orbital> orbitals;
	
	public Air(int nbOrbitaux, int distance) {
		this.nbOrbitaux = nbOrbitaux;
		this.distance = distance;
		this.orbitals = new ArrayList<Orbital>();
	}
	
	public void addOrbital(Orbital o) {
		if (orbitals.size() < nbOrbitaux) {
			orbitals.add(o);
		}
		else
			System.out.println("Plus d'orbitaux dispo");
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
		int i = 0;
		for (Orbital o : orbitals) {
			o.update(container, game, delta);
			i++;
		}
	}
	
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		for (Orbital o : orbitals) {
			o.render(container, game, context);
		}
	}
}
