package solar_system;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Building extends Construction{
	
	protected Case tile;
	protected HashMap<String, Resource> resourcesProduced;
	protected Map<String,Double> debits;//Ce qui est prélevé de la case

	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update(container, game, delta);
		// On prélève autant de ressources que possible,
		// et on ne récupère les ressources de la case que si tous les prélèvements ont réussi :
		if (prelevementReussi) {
			double qtiteAjoutee = 0;
			for( Map.Entry<String , Resource> resource : resourcesProduced.entrySet())
			{
				Resource res = (Resource) resource.getValue();
				qtiteAjoutee = (double)delta*debits.get(res.getName());

				if ( res.equals(tile.getResource()) ){   // Cas où la production dépend des ressources sur la case
					qtiteAjoutee = tile.preleveResource(qtiteAjoutee);
				}
				res.modifQuantite(qtiteAjoutee);
			}
		}
		
	}
	
	public void render (GameContainer container, StateBasedGame game, Graphics context, int x, int y) {
		context.drawImage(sprite, x, y);
	}
	
	
}
