package solar_system;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Ground {
	
	private int sizeCase = 80;
	private Planet planet;
	private World world;
	private Case[][] cases;
	private int x_origin;	//Abscisse du coin supérieur gauche
	private int y_origin;	//Ordonnée du coin supérieur gauche
	private int case_width;
	
	public Ground(Planet planet, World world) {
		/* Créer un objet de classe Ground avec des cases pour sur la planete plt */
		this.planet = planet;
		this.world = world;
		
		// Calcul du coin haut-gauche de la zone d'affichage (pour l'instant un carré) des cases
		double half_width = planet.getRadius()/Math.sqrt(2);
		this.x_origin =  (int) (world.getWidth()/2 - Math.floor(half_width) );
		this.y_origin =  (int) (world.getHeight()/2 - Math.floor(half_width) );
		this.case_width = (int) Math.floor((2*half_width)/cases.length);
		
		generateCases();
	}
		
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		for (Case[] tab : cases) {
			for (Case c : tab) {
				c.render(container, game, context);
			}
		}
	}
	
	public void generateCases() {
		// Génère le tableau de dimension 2 "Cases" et le remplit de "Case"
		
		// Nombre de cases en longueur :
		int n = (int) Math.floor(Math.sqrt(2 * planet.getRadius())/ sizeCase);
		
		this.cases = new Case[n][n];
		
		for (int i = 0; i < cases.length ; i++) {
			for (int j = 0; j < cases.length; j++ ) {
				cases[i][j] = new Case();
				cases[i][j].setX(x_origin + i*case_width);
				cases[i][j].setY(y_origin + j*case_width);
			}
		}
	}
	
	
}
