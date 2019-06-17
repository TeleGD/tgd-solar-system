package solar_system.constructions;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import solar_system.Case;
import solar_system.Player;
import solar_system.Construction;
import solar_system.Resource;
import solar_system.util.Images;

public class ISS extends Construction {
	
	public ISS (Case tile, Player player){
		super(tile,player);
		this.posX=tile.getX();
		this.posY=tile.getY();
		this.lifeMax=80;
		this.life=lifeMax;
		this.debits.put("Nourriture", 0.02);
		this.name = "Station intergalactique";
		this.player=player;
		
		this.cout.put("Noyau Linux", 3.0);
		
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		
	}
//	public static boolean constructPossible(Case tileConstructLocation) {
//		return resourcesExploitable.containsKey(tileConstructLocation.getResource().getName());
//	}

//	//public static boolean constructPossible(Case tileConstructLocation) {
//		if (player.getResource(costEntry.getKey()).getQuantite()<costEntry.getValue()){
//			return false;
//		}
//		return true;
//	}

}









	
	
	
	