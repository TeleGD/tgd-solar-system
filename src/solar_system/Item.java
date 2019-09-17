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
import solar_system.constructions.Vaisseau;
import solar_system.constructions.vaisseaux.Colonisator;
import solar_system.constructions.ISS;


//TODO : Afficher le nombre de vaisseau du type DEJA construits lorsqu'on clique sur une ISS

public class Item {
	private World world;
	private Case tile;
	private String name;
	private Construction constr;
	private ButtonV2 button;
	private int height;
	private ArrayList<ResourceIcon> iconCostProduc;
	private int xName, yName;
	private int imageConstructSize;
	private boolean canConstruct;
	private boolean ownership;
	private String nbVaisseaux=""; //Sert à indiquer combien de vaisseaux de ce type(uniquement en cliquant sur une ISS)

	public Item(World world, Case tile, String name, int x, int y) {

		this.world = world;
		this.tile = tile;
		this.name = name;
		this.constr = nameToConst(name, tile);
		this.imageConstructSize = 150;
		this.canConstruct = false;
		Image imgConstruction = null;
		iconCostProduc = new ArrayList<>();
		Image imageTemp = AppLoader.loadPicture("/images/constructions/"+name+".png");
		imgConstruction = imageTemp.getScaledCopy(imageConstructSize,imageConstructSize) ; // on met toutes les images à la même taille (et carrées)
		this.button = new ButtonV2(imgConstruction, x, y, imageConstructSize, imageConstructSize);

		xName = x + imageConstructSize;
		yName = y + imageConstructSize + 8;

		// Définition de variables temporaires nous indiquant notre position actuelle
		// (on se déplace pour positionner des objets uns par uns).
		int currentX = x+imgConstruction.getWidth()+8;	// marge de 8 pixels
		int currentY = y;
		Image img;
		// Pour chaque ressource en coût de la construction, on ajoute l'icône correspondant à la liste iconCostDebit
		//if(tile.getOrbital() == null || tile.getOrbital() instanceof Satellite){
			for (String k : this.constr.cout.keySet()) {
				img = AppLoader.loadPicture(Resource.imagePath(k));
				iconCostProduc.add(new ResourceIcon(currentX, currentY, img, this.constr.cout.get(k).intValue()));
				// Comme on les affiche en colonne, on garde notre position X actuelle et on descend en Y
				currentY += 50;  // On se positionne une ligne en dessous
			}
			if (this.constr instanceof Building) {
				for (String k : ((Building)this.constr).debits.keySet()) {
					img = AppLoader.loadPicture(Resource.imagePath(k));
					// Pour l'instant, seule une ressource est produite,
					// on n'affiche donc pas le débit mais la quantité max disponible :
					iconCostProduc.add(new ResourceIcon(currentX, currentY, img, (int) tile.getResourceQuantity()));
					// iconCostProduc.add(new ResourceIcon(currentX, currentY, img, this.constr.debits.get(k).intValue()));
					currentX -= 48;
				}
			}


		// Parfois le bouton de la construction est plus grand que tous les icônes sur la droite, parfois c'est l'inverse.
		currentY = Math.max(currentY+10,y+imgConstruction.getHeight()+10);

		currentY += 24;

		currentY += 50; // On se positionne une ligne en dessous
		this.height = currentY - y;
	}

	public Construction nameToConst (String name, Case tile) {
		switch(name) {
			case "Mine" : return new Mine(tile, world.getPlayer());
			case "Mine2" : return new Mine2(tile, world.getPlayer());
			case "TNCY" : return new TNCY(tile, world.getPlayer());
			case "Ferme" : return new Ferme(tile, world.getPlayer());
			case "Scierie" : return new Scierie(tile, world.getPlayer());
			case "CabaneBucheron" : return new CabaneBucheron(tile, world.getPlayer());
			case "ISS" : return new ISS(tile, world.getPlayer());
			case "Colonisator" : return new Colonisator(world.getPlayer());
			}
		return null;
	}

	public int getHeight() {
		return this.height;
	}

	public boolean playerCanConstruct() {
		return this.canConstruct;
	}

	public boolean getOwnership() {
		return this.ownership;
	}

	public void setOwnership(boolean ownership) {
		this.ownership = ownership;
	}

	public void moveY(int dY) {
		this.button.moveY(dY);
		for (ResourceIcon ri : iconCostProduc) {
			ri.moveY(dY);
		}
		this.yName += dY;
	}

	public void update (GameContainer container, StateBasedGame game, int delta) {
		if (this.constr != null) this.canConstruct = this.ownership && this.constr.playerCanConstruct(world.getPlayer());
		if (canConstruct) {
			button.setAlpha(1);
		}
		else {
			button.setAlpha(0.5f);
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		this.button.render(container, game, context);
		for (ResourceIcon ri : iconCostProduc) {
			ri.render(container, game, context);
		}
		int largeur = context.getFont().getWidth(name);
		String str;
		if (constr instanceof Vaisseau) {
			str=name + " (" + ((ISS)tile.getConstruction()).getNbVaisseaux((Vaisseau) constr) + ")";
		}
		else {
			str=name;
		}
		context.drawString(str , this.xName-largeur, this.yName);
	}

	public boolean mousePressed(int arg0, int x, int y) {
		if (button.isPressed(x, y)) {
			System.out.println(name);
			//TODO :
			if (constr instanceof Vaisseau){//Si on chercher à construire un vaisseau, on l'ajoute à sa station
				if (tile.getConstruction() instanceof ISS) {
					//TODO : Si il a les ressources, faire payer le joueur
					Vaisseau vaisseau = (Vaisseau) constr;
					((ISS)tile.getConstruction()).addVaisseau(vaisseau);
					constr.giveMeYourMoney(world.getPlayer());//On fait payer le joueur
					System.out.println("Et on ajoute un vaisseau !");
					nbVaisseaux = "Nombre de " + vaisseau.getName() + ": "  + ((ISS)tile.getConstruction()).getNbVaisseaux((Vaisseau) constr); //Pb : ne s'affichera qu'à partir du moment ou on a cobstruit au miins 1 vaisseau du type.
					System.out.println(nbVaisseaux);
				}

			}
			else {// Si on cherche à construire autre chose qu'un vaisseau (sur une case)
				if ( canConstruct ) { // Si le joueur a les ressources requises pour la construction :
					constr.giveMeYourMoney(world.getPlayer());	// On fait payer le joueur
					tile.setConstruction( constr );	// On construit la construction
				}
				return true;
			}

		}
		return false;
	}

}
