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
	private World world;
	private int sizeCase;
	
	private List<Orbital> orbitals;
	
	public Air(int nbOrbitaux, int distance,World world) {
		this.nbOrbitaux = nbOrbitaux;
		this.distance = distance;
		this.orbitals = new ArrayList<Orbital>();
		this.world=world;
		this.sizeCase= (int)(80*world.getFacteurMagique());
	
	}
	
	public Orbital mousePressed(int arg0,int x ,int y) {//Retourne l'orbitale sur laquelle on a clique, null si on ne clique sur aucune orbitale.
		// padding = marge intérieure (distance entre la grille et le bord de l'image)
		
		for (Orbital orbital : orbitals){
			//if(orbital.getCase()!=)
			//Correspond aux coordonnées du coin en haut à gauche de la case sur le satellite ou la station.
//			int coin_x = (int)(orbital.get_x()+orbital.get_size()-this.sizeCase/2);//Math.round(orbital.get_x()+orbital.get_size()-sizeCase);
//			int coin_y = (int)(orbital.get_y()+orbital.get_size()-this.sizeCase/2);//Math.round(orbital.get_y()+orbital.get_size()-sizeCase);
			if(orbital.getCase().mousePressed(arg0,x ,y)){
				System.out.println("Tu as cliqué sur le satellite !");
				return orbital;				
			}
			//System.out.println(coin_x+" "+coin_y+" "+orbital.get_size()+" "+this.sizeCase);
		}
		System.out.println("Oh non, tu as cliqué à côté du satellite...");
		return null;

		
	}
	
	
	
	public void addOrbital(Orbital o) {
		if (orbitals.size() < nbOrbitaux) {
			orbitals.add(o);
		}
		else
			System.out.println("Plus d'orbitaux dispo");
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
		//int i = 0;
		for (Orbital o : orbitals) {
			o.update(container, game, delta);
			//i++;
		}
	}
	
	public void render (GameContainer container, StateBasedGame game, Graphics context, boolean arrierePlan) {
		for (Orbital o : orbitals) {
			o.render(container, game, context, arrierePlan);
			//context.setColor(Red);
			//context.
		}
	}
	
}
