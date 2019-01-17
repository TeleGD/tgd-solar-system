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
	private Ground ground;
	private int width;
	private int height;
	private Solsys solsys;
	private Player player;
	private boolean mouv;
	private boolean planetTouched;
	private Image image;
	private boolean dispRessources;
	
	public World (int ID) {
		this.ID = ID;
		this.state = -1;
		this.dispRessources = false;
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
		if (!mouv) {
			mouv = true;
		} else if (!planetTouched) {
			if(ground == null)
				solsys.update(container, game, delta);
			else
				this.ground.update(container, game, delta);
			player.update(container, game, delta);
		}
	}

	@Override
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
		if(ground == null)
			solsys.render(container, game, context);
		else
			this.ground.render(container, game, context);
		player.render(container, game, context);
	}

	public void play (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au début du jeu */
		this.solsys= new Solsys(5,this);
		this.player= new Player(this);
		this.ground = null;
		this.mouv = true;
		this.planetTouched = false;
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
	
	@Override
	public void mousePressed(int arg0, int x, int y) 
	{
		Ground tempGround = null;
		if (ground == null) {
			tempGround = solsys.planetTouched(x, y);
		}
		if(tempGround!= null)
			ground = tempGround;
		if (ground!=null) {
			if (ground.mousePressed(arg0,x,y)) {
				ground=null;
			}
			if (player.mousePressed(arg0,x,y)){
				if(dispRessources){
					dispRessources=false;
				}
				else{
					dispRessources=true;
				}
			}
		}
	}

	public void mouseMoved(int oldX, int oldY,int newX, int newY) {
		mouv = false;
		planetTouched = solsys.planetTouched(newX, newY) != null;
	}
}

