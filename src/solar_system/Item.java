package solar_system;

import java.util.ArrayList;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;
import app.AppPage;
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
	private ArrayList<ResourceIcon> iconCostDebit;
	private int xName, yName;
	private int imageConstructSize;
	
	public Item(World world, Case tile, String name, int x, int y) {
		
		this.world = world;
		this.tile = tile;
		this.name = name;
		imageConstructSize = 150;
		
		Image imgConstruction = null;
		iconCostDebit = new ArrayList<>();
		Image imageTemp = AppLoader.loadImage("/images/constructions/"+name+".png");
		imgConstruction = imageTemp.getScaledCopy(imageConstructSize,imageConstructSize) ; // on met toutes les images à la même taille (et carrées)
		this.button = new ButtonV2(imgConstruction, x, y, imageConstructSize, imageConstructSize);
		
		xName = x + imageConstructSize;
		yName = y + imageConstructSize + 8;

		// Définition de variables temporaires nous indiquant notre position actuelle
		// (on se déplace pour positionner des objets uns par uns).
		int currentX = x+imgConstruction.getWidth()+8;	// marge de 8 pixels
		int currentY = y;
		Construction construction = nameToConst(name, tile);
		Image img;
		// Pour chaque ressource en coût de la construction, on ajoute l'icône correspondant à la liste iconCostDebit
		for (String k : construction.cout.keySet()) {
			try{
				img = new Image(Resource.imagePath(k));
				iconCostDebit.add(new ResourceIcon(currentX, currentY, img, construction.cout.get(k).intValue()));
			} catch (SlickException e) {
				e.printStackTrace();
			}
			// Comme on les affiche en colonne, on garde notre position X actuelle et on descend en Y
			currentY += 50;  // On se positionne une ligne en dessous
		}
		// Parfois le bouton de la construction est plus grand que tous les icônes sur la droite, parfois c'est l'inverse.
		currentY = Math.max(currentY+10,y+imgConstruction.getHeight()+10);
		
		currentY += 24;
		for (String k : construction.debits.keySet()) {
			try{
				img = new Image(Resource.imagePath(k));
				iconCostDebit.add(new ResourceIcon(currentX, currentY, img, construction.debits.get(k).intValue()));
			} catch (SlickException e) {
				e.printStackTrace();
			}
			currentX -= 48;
		}
		currentY += 50; // On se positionne une ligne en dessous
		this.height = currentY - y;
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
		for (ResourceIcon ri : iconCostDebit) {
			ri.render(container, game, context);
		}
		int largeur = context.getFont().getWidth(name);
		context.drawString(name, this.xName-largeur, this.yName);
	}
	
	public boolean mousePressed(int arg0, int x, int y) {
		if (button.isPressed(x, y)) {
			Construction constr = nameToConst(name, tile);
			if ( constr.playerCanConstruct( world.getPlayer() ) ) { // Si le joueur a les ressources requises pour la construction :
				tile.setConstruction( constr );
				// TODO : Retirer les ressources du joueur (ce que la construction a coûté)
			}
			return true;
		}
		// TODO : Gérer la destruction du bâtiment
		return false;
	}
	
}
