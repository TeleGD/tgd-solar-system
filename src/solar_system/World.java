package solar_system;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class World extends BasicGameState {

	private int ID;
	private int state;
	private int width;
	private int height;
	private Solsys solsys;
	private Player player;
	private boolean mouv;
	private Planet planetSelected;
	private Image image;
	private boolean dispRessources;
	
	public World (int ID) {
		this.ID = ID;
		this.state = -1;
	}

	@Override
	public int getID () {
		return this.ID;
	}

	@Override
	public void init (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au chargement du programme */
		this.width = container.getWidth ();
		this.height = container.getHeight ();
		 System.out.println(width + "" + height);
		
	}

	@Override
	public void enter (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à l'apparition de la page */
		if (this.state == 0) {
			this.play (container, game);
		} else if (this.state == 2) {
			this.resume (container, game);
		}
	}
	
	@Override
	public void leave (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à la disparition de la page */
		if (this.state == 1) {
			this.pause (container, game);
		} else if (this.state == 3) {
			this.stop (container, game);
		}
	}

	@Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		Input input = container.getInput ();
		if (input.isKeyDown (Input.KEY_ESCAPE)) {
			this.setState (1);
			game.enterState (2, new FadeOutTransition (), new FadeInTransition ());
		}
		/*if (!mouv) {
			mouv = true;
		} else {
			solsys.update(container, game, delta);
			player.update(container, game, delta);
		}*/
		solsys.update(container, game, delta);
		player.update(container, game, delta);
	}

	@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
		if(planetSelected == null){//Si aucune planète n'est sélectionnée
			solsys.render(container, game, context);
		}
		else{//Si une planète est sélectionnée, on affiche le render de son ground.
			planetSelected.getGround().render(container, game, context);
		}
		player.render(container, game, context);
	}

	public void play (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au début du jeu */
		this.player= new Player(this);
		this.solsys= new Solsys(5,this);
		this.mouv = true;
		this.planetSelected = null;
		this.dispRessources = false;
	}

	public void pause (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la mise en pause du jeu */
	}

	public void resume (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
	}

	public void stop (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois à la fin du jeu */
	}

	public void setState (int state) {
		this.state = state;
	}

	public int getState () {
		return this.state;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public boolean getDispRessources(){
		return dispRessources;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public void mousePressed(int arg0, int x, int y) 
	{
		if (planetSelected == null) {  // Sur le systeme , gère le clique sur une planète
			planetSelected = solsys.planetTouched(x, y); // planetSelected recoit la planète touchée
			mouv = false;
			// null sinon
		}
			
		else if (player.mousePressed(arg0,x,y)){//Si le joueur clique sur "Autres ressources"
			if(dispRessources){
				dispRessources=false;
			}
			else{
				dispRessources=true;
			}
		}
		
		else { // Gère l'interaction avec le ground: Construction + Selection de case + Retour
			
			if (planetSelected.getGround().mousePressed(arg0,x,y)) { // A t on cliqué sur le carré rouge ?
				planetSelected=null;
			}
			//else if()
		}
	}

	public void mouseMoved(int oldX, int oldY,int newX, int newY) {
		if(planetSelected==null) {
			mouv = false;
		}
	}

}

