package solar_system;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class Production {
	private int debit; //debit en qte/s
	private double quantite;
	private String type;
	private Player player;
	private Case tile;
	
	public Production(int debit, String type, Player player, Case tile) {
		super();
		this.debit = debit;
		this.type = type;
		this.player = player;
		this.tile=tile;
	}
	public void update(GameContainer container, StateBasedGame game, int delta) {
		quantite=delta*(double)debit/1000;
		if (quantite>tile.getResourceQuantity()){
			player.addRessource(type, quantite);
			tile.preleveResource(quantite);
		} else if (tile.getResourceQuantity()>0){
			player.addRessource(type, tile.getResourceQuantity());
			tile.preleveResource(tile.getResourceQuantity());
		}
	}
	

}
