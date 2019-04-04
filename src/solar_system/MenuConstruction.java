package solar_system;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import app.ui.ButtonV2;

public class MenuConstruction {
	private Case selectedCase;
	private ArrayList<String> constructionsPossibles;
	private ArrayList<Item> listItems;
	private World world;
	private int x;
	private int y;
	
	public MenuConstruction(World world, int x, int y) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.listItems = new ArrayList<>();
	}
	
	public void casePressed(Case selectedCase){
		listItems.clear();
		this.selectedCase = selectedCase;
		if (selectedCase != null) {
			this.constructionsPossibles = selectedCase.infoConstruct();
			int yItem = this.y;
			Item item;
			for (String name : constructionsPossibles) {
				item = new Item(world, selectedCase, name, x, yItem);
				listItems.add(item);
				yItem += item.getHeight();
			}
		}
	}
	
	public boolean mousePressed(int arg0, int x, int y) {
		for (Item item : listItems) {
			if (item.mousePressed(arg0, x, y)) { return true; }
		}
		return false;
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		for (Item item : listItems) {
			item.render(container, game, context);
		}
	}
	
	public String construcRequested(int number) {    // Renvoie le nom du bâtiment numéro 'number' dans le menu des constructions
		if (number<constructionsPossibles.size()) {
			return constructionsPossibles.get(number);
		}
		return "";
	}
	
}
