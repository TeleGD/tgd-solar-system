package solar_system;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import app.ui.ButtonV2;
import solar_system.constructions.CabaneBucheron;
import solar_system.constructions.Ferme;
import solar_system.constructions.Mine;
import solar_system.constructions.Mine2;
import solar_system.constructions.Scierie;
import solar_system.constructions.TNCY;

public class Item {
	private World world;
	private Case tile;
	private String name;
	private ButtonV2 button;
	private int height;
	
	public Item(World world, Case tile, String name, int x, int y) {
		this.world = world;
		this.tile = tile;
		this.name = name;
		Image img = null;
		try {
			img = new Image("res/images/constructions/"+name+".png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.button = new ButtonV2(img, x, y, 48, 48);
		//TODO: Positionnement des coûts et des débits
	}
	
	public Construction nameToConst (String name, Case tile) {
		if(name=="Mine"){
			return new Mine(tile, world.getPlayer());
		}
		if(name=="Mine2"){
			return new Mine2(tile, world.getPlayer());
		}
		if(name=="TNCY"){
			return new TNCY(tile, world.getPlayer());
		}
		if(name=="Ferme"){
			return new Ferme(tile, world.getPlayer());
		}
		if(name=="Scierie"){
			return new Scierie(tile, world.getPlayer());
		}
		if(name=="CabaneBucheron"){
			return new CabaneBucheron(tile, world.getPlayer());
		}
		return null;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		this.button.render(container, game, context);
		//TODO: Gérer l'affichage des coûts et débits
	}
	
	public boolean mousePressed(int arg0, int x, int y) {
		return button.isPressed(x, y);
	}
	
}
