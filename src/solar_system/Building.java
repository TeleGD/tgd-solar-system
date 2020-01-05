package solar_system;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public class Building extends Construction{
	
	protected Case tile;
	protected HashMap<String, Resource> resourcesProduced;
	protected Map<String,Double> debits;//Ce qui est prélevé de la case
	//protected HashMap<String,Double> coutPerpetuel;//Ce qui ezst prélevé au joueur par frame, "entretiens"
	//C'est la même chose que l'entretiens !!!

	//TODO : constructeur;
	public Building(Case tile,Player player) {
		super(player);
		this.tile=tile;
		resourcesProduced = new HashMap<String, Resource>();
		Resource resOfCase = tile.getResource() ;
		//System.out.println(resOfCase.getName());
		if((tile.getOrbital() instanceof Station) == false && resOfCase.getName() != "Cailloux"){
			resourcesProduced.put(resOfCase.getName(), resOfCase);
		}
		else if (tile.getResource() != null && tile.getResource().getName()=="Cailloux") {
			//System.out.println("POh, un caillou !");
			Resource noyo = player.getResource("Noyaux Linux éduqués");
			resourcesProduced.put(noyo.getName(),noyo);
		}
		//coutPerpetuel = new HashMap<String,Double>();

		debits = new HashMap<String,Double>();
		
		Image imageTemp = AppLoader.loadPicture("/images/constructions/"+this.getClass().getSimpleName()+".png"); // L'image doit avoir le même nom que la classe
		sprite = imageTemp.getScaledCopy(tile.getSize(),tile.getSize()) ;
	}
	
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
