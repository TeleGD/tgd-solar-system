package games.solarSystem.constructions;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import games.solarSystem.Building;
import games.solarSystem.Case;
import games.solarSystem.Player;
import games.solarSystem.Resource;

public class TNCY extends Building {

	private static HashMap<String, Resource> resourcesExploitable ;
	static
	{
		resourcesExploitable = new HashMap<>();
        resourcesExploitable.put("Cailloux",new Resource("Cailloux"));
        // resourcesExploitable.put("Noyaux Linux éduqués",new Resource("Noyaux Linux éduqués"));
	}

	public TNCY (Case tile, Player player){
		super(tile, player);
//		this.posX=tile.getX();
//		this.posY=tile.getY();
		this.lifeMax=100;
		this.life=lifeMax;
		this.debits.put("Noyaux Linux éduqués", 0.01);
		this.name = "Télécom Nancy";

		this.cout.put("Fer", 5000.0);
		this.cout.put("Bois", 500.0);
		this.cout.put("Nourriture", 10000.0);
		this.entretiens.put("Noyaux Linux", 0.01);
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {

	}
	
	/*public void update(GameContainer container, StateBasedGame game, int delta) {
		prelevementReussi = true;

		for( Map.Entry<String , Double> entry : entretiens.entrySet())
		{
			String resource_name = entry.getKey();
			double qtite_a_prelever = entry.getValue();

			if ( player.getResource( resource_name ).modifQuantite( - qtite_a_prelever ) == false ) {
				prelevementReussi = false;
			}
		}

	}*/

	public static boolean constructPossible(Case tileConstructLocation) {
		return resourcesExploitable.containsKey(tileConstructLocation.getResource().getName());
	}

}
