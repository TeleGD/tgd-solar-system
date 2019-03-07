package solar_system;

import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Construction {
	
	protected int life;
	protected int lifeMax;
	protected int posX;
	protected int posY;
	protected String description;
	protected String name;
	protected Map<String,Double> debits;
	protected boolean destructed;
	protected Case tile;
	protected HashMap<String, Resource> resourcesProduced;
	protected HashMap<String, Double> cout;
	protected HashMap<String, Double> coutPerpetuel;
	protected Player player;
	
	public Construction(Case tile, Player player) {
		this.lifeMax = 100;
		this.life = lifeMax;
		this.cout = new HashMap<String, Double>();
		this.name = "";
		destructed = false;
		
		this.tile = tile;
		resourcesProduced = new HashMap<String, Resource>();
		Resource resOfCase = tile.getResource() ;
		resourcesProduced.put(resOfCase.getName(), resOfCase);
		
		debits = new HashMap<String,Double>();
		coutPerpetuel = new HashMap<String, Double>();
	}
	
	public void takeDamage(int damage) {
		life -= damage;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
		// On commence par les couts perpétuels (ceux des ressources prélevées à chaque update) :
		boolean prelevementReussi = true;
		
		for( Map.Entry<String , Double> entry : coutPerpetuel.entrySet())
		{
			String resource_name = entry.getKey();
			double qtite_a_prelever = entry.getValue();
			if ( player.getResource( resource_name ).modifQuantite( qtite_a_prelever ) == false ) {
				
				prelevementReussi = false;
			}
		}
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
	
	public boolean playerCanConstruct(Player player){
		
		for(Map.Entry<String, Double> costEntry : cout.entrySet()) {
			if(player.getResource(costEntry.getKey()).getQuantite() < costEntry.getValue()){
				return false;
			}
		}
		// Si la construction est possible, on prélève les ressources :
		
		for(Map.Entry<String, Double> costEntry : cout.entrySet()) {
			player.getResource( costEntry.getKey() ).modifQuantite( - costEntry.getValue() );
		}
		return true;
	}
	
	public void isDead() {
		if (life<=0) {
			destructed=true;
		}
	}
	
	public boolean isDestructed() {
		return destructed;
	}

	public void setDetruit(boolean detruit) {
		this.destructed = detruit;
	}

	public String getName() {
		return name;
	}
	
	
}



