package solar_system;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;
import app.ui.ButtonV2;
import solar_system.constructions.Vaisseau;

public class MenuConstruction {
	private Case selectedCase;
	private ArrayList<String> constructionsPossibles;
	private ArrayList<Item> listItems;
	private World world;
	private Planet planet;
	private int x;
	private int y;
	private int y0; // ordonnée initiale, si jamais on doit reset la postion y
	private int displayHeight;  // Hauteur accordée à l'affichage de MenuConstruction et ses Item
	private int height; // Hauteur nécessaire pour afficher tout MenuConstruction
	private ButtonV2 suppr;
	//private Orbital orbital;
	private boolean orbital;//vaut 1 si c'est une orbitale
	
	private int stringLength;//On récupère la longueur d'une chaine de caractère pour savoir quelle taille pourra faire le bouton

	public MenuConstruction(World world, Planet planet, int x, int y) {//,boolean orbital
		this.world = world;
		this.planet = planet;
		this.x = x;
		this.y = y;
		this.y0 = y;
		this.listItems = new ArrayList<>();
		this.suppr = null;
		this.displayHeight = world.getHeight() - this.y0;
		this.height = 0;
	}

	public void casePressed(Case selectedCase){
		listItems.clear();
		this.y = this.y0;
		this.selectedCase = selectedCase;
		suppr = null;
		if (selectedCase != null) {
			this.constructionsPossibles = selectedCase.infoConstruct();
			Item item;
			for (String name : constructionsPossibles) {
				item = new Item(world, selectedCase, name, x, this.y,(int)(world.getWidth()*0.2-stringLength));//orbital
				listItems.add(item);
				this.y += item.getHeight();
			}
			if (selectedCase.getConstruction() != null) {
				Image imgBoule = AppLoader.loadPicture("/images/constructions/destruction.jpeg");
				suppr = new ButtonV2(imgBoule, x, this.y, 48, 48); //TODO: Gérer sa position en fonction des constructions (améliorations possibles)
			}
		}

		this.height = this.y - this.y0;
	}
	
	public int getXPos() {
		return this.x;
	}
	
	public int getYPos() {
		return this.y;
	}

	public boolean mousePressed(int arg0, int x, int y) {
		for (Item item : listItems) { // Si on clique sur l'un des items
			if (item.mousePressed(arg0, x, y)) {
				casePressed(selectedCase); // On rafraîchit le menu de construction pour tenir compte du fait qu'on ait peut-être construit
				return true;
			}
		}
		if (suppr != null && suppr.isPressed(x, y)) { // Si on clique sur le bouton de destruction
			selectedCase.setConstruction(null);
			/*selectedCase.setBackground(null);
			selectedCase.setBackgroundAsResource();*/
			suppr = null;
			casePressed(selectedCase);
			return true;
		}
		return false;
	}

	public void moveY(int dY) {

		if (listItems.size() == 0){ // Traite le cas de la liste d'item vide
			return;
		}
		if (this.displayHeight > this.height){  // Quand on peut afficher tout MenuConstruction sans avoir à défiler, on interdit le défilement
			return;
		}

		if (listItems.get(0).getY() + dY > this.y0){    // Si on tente de faire descendre les Item tel que l'Item du dessus laisse un espace vide entre lui et la limite haute de MenuConstruction
			dY = this.y0 - listItems.get(0).getY();
		}
		else if ( this.y + dY < this.displayHeight + this.y0) { // Si on tente de faire monter les Item tel que l'Item du dessous laisse un espace vide entre lui et la limite basse de MenuConstruction
			dY = this.displayHeight + this.y0 - this.y;
		}

		if (Mouse.getX() >= this.x && world.getHeight()-Mouse.getY() >= world.getPlayer().getyMinUI()) {    // Vérifie que la souris est sur le menu de construction au moment du scroll
			this.y += dY/5;
			for (Item item : listItems) {
				item.moveY(dY/5);
			}
			if (this.suppr != null) this.suppr.moveY(dY/5);
		}
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
		for (Item item : listItems) {
			item.update(container, game, delta);
			item.setOwnership(this.planet.getOwner() == this.world.getPlayer());
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		stringLength = context.getFont().getWidth("Débit(/s) : | Stock : ");
		for (Item item : listItems) {
			item.render(container, game, context);
		}
		if (suppr != null) {
			suppr.render(container, game, context);
		}
	}

	public String construcRequested(int number) {    // Renvoie le nom du bâtiment numéro 'number' dans le menu des constructions
		if (number < constructionsPossibles.size()) {
			return constructionsPossibles.get(number);
		}
		return "";
	}

}
