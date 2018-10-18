package solar_system;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class Production {
	private int debit; //debit en qte/s
	private double quantite;
	private String type;
	private Player player;
	
	public Production(int debit, String type, Player player) {
		super();
		this.debit = debit;
		this.type = type;
		this.player = player;
	}
	public void update(GameContainer container, StateBasedGame game, int delta) {
		quantite=delta*double(debit)/1000;
		player.addRessource(type, quantite);
	}
	

}
