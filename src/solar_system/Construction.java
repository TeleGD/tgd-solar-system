package solar_system;

import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public abstract class Construction {

	protected int life;
	protected int lifeMax;
	protected String description;
	protected String name;
	protected boolean destructed;
	protected static HashMap<String, Double> cout;
	protected HashMap<String, Double> entretiens;
	protected static Player player;
	protected Image sprite;
	protected boolean prelevementReussi;
	//protected Case tile;


	public Construction(Player player) {
		this.lifeMax = 100;
		this.life = lifeMax;
		this.cout = new HashMap<String, Double>();
		this.name = "";
		this.player = player;
		destructed = false;

		//this.tile = tile;

		entretiens = new HashMap<String, Double>();
	}

	public void takeDamage(int damage) {
		life -= damage;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {

		// On commence par les couts perpétuels= l'entretiens (ceux des ressources prélevées à chaque update) :
		prelevementReussi = true;

		for( Map.Entry<String , Double> entry : entretiens.entrySet())
		{
			String resource_name = entry.getKey();
			double qtite_a_prelever = entry.getValue();

			if ( player.getResource( resource_name ).modifQuantite( - qtite_a_prelever ) == false ) {
				prelevementReussi = false;
			}
		}

	}

	public boolean playerCanConstruct(Player player){

		for(Map.Entry<String, Double> costEntry : cout.entrySet()) {
			if(player.getResource(costEntry.getKey()).getQuantite() < costEntry.getValue()){
				return false;
			}
		}
		return true;
	}
	
	public void giveMeYourMoney(Player player) {
		// Prélève les ressources nécessaires sans vérifier que la construction est possible
		// (condition à vérifier par l'item du menu)
		System.out.println(this.name);
		for(Map.Entry<String, Double> costEntry : cout.entrySet()) {
			player.getResource( costEntry.getKey() ).modifQuantite( - costEntry.getValue() );
			System.out.println("Vous venez de payer "+costEntry.getValue()+" "+costEntry.getKey());
		}
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

	public Image getSprite() {
		return sprite;
	}

}
