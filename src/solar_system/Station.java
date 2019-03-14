package solar_system;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Station extends Orbital {
	private int nbVaisseaux;
	
	public Station(int lifeMax, int cout,float angle, int size, int distance, Resource resource,World world) {
		super(lifeMax, cout, angle, size, distance, resource,world);
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
	
	
	public void render (GameContainer container, StateBasedGame game, Graphics context){
		context.setColor(Color.red);
		context.fillOval(get_x()*1f-size, get_y()*1f-size, size * 2, size * 2);
		//System.out.println("Je suis une station de position "+get_x()+","+get_y());
		super.render(container, game, context);
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		// TODO Auto-generated method stub
		
	}
}
