package solar_system;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class Building extends Construction{
	
	protected int posX;
	protected int posY;
	protected Case tile;
	protected HashMap<String, Resource> resourcesProduced;
	protected Map<String,Double> debits;//Ce qui est prélevé de la case

	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update(GameContainer container, StateBasedGame game, int delta);
	}
}
