package solar_system;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Ground {
	
	private Planet planet;
	private Case[][] cases;
	private int x_origin;	//Abscisse du coin supérieur gauche
	private int y_origin;	//Ordonnée du coin supérieur gauche
	private int case_width;
	
	public Ground(Planet planet, Case[][] cases) {
		/* Créer un objet de classe Ground avec des cases pour sur la planete plt */
		this.planet = planet;
		this.cases = cases;
		double half_width = planet.rayon/sqrt(2));
		this.x_origin = planet.x_center - floor(half_width);
		this.y_origin = planet.y_center - floor(half_width);
		this.case_width = floor((2*half_width)/cases.length());
		
		/* à faire : - une fonction de placement qui dit aux cases où se placer
		 */ 
	}
		
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		for (int i = 0; i < cases.length ; i++) {
			for (int j = 0; j<cases.length; j++ ) {
				cases[i][j].x = x_origin + i*case_width;
				cases[i][j].y = y_origin + j*case_width;
			}
		}
	}
	
}
