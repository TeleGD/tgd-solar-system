package solar_system;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Station extends Orbital {
	private int nbVaisseaux;
	
	public Station(int lifeMax, int cout,int posX,int posY, double speed, double size) {
		super(lifeMax, cout, posX, posY, speed, size);
		this.nbVaisseaux = 0;
	}
	
	public void setV(int n /*nombre de vaisseaux reçu*/) {
		this.nbVaisseaux = this.getNbVaisseaux() + n;
	}
	
	public void envoyerVaisseaux(Station s, int nbve /*nombre de vaisseaux envoyé*/){
		this.nbVaisseaux = this.getNbVaisseaux() - nbve;
		s.setV(nbve);
	}
	
	public int getNbVaisseaux() {
		return nbVaisseaux;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		// TODO Auto-generated method stub
		
	}
}
