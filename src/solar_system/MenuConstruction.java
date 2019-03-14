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
	}
	
	public void casePressed(Case selectedCase){
		this.selectedCase = selectedCase;
		this.constructionsPossibles = selectedCase.infoConstruct();
		int yItem = this.y;
		Item item;
		for (String name : constructionsPossibles) {
			item = new Item(world, selectedCase, name, x, yItem);
			listItems.add(item);
			yItem += item.getHeight();
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
	
	public boolean mousePressed2(int arg0, int x, int y) {
		
		// Construction d'un bâtiment :
		
		if (menuConstruction && selectedCase != null) {  // On vérifie si le joueur veut construire un bâtiment.
			
			if (x>=coinMenuX && x<=coinMenuX+imageConstructSize && y>=coinMenuY && y<coinMenuY+4*(imageConstructSize+hauteurTextMenuConstruct)) {
				
				// Il faut cliquer sur l'image, et non pas le texte écrit en-dessous :
				int number = (y-coinMenuY)/(imageConstructSize+hauteurTextMenuConstruct);
				
				if ( y <= coinMenuY+(number+1)*(imageConstructSize+hauteurTextMenuConstruct)-hauteurTextMenuConstruct) {
					String construct = construcRequested(number);
					
					if (construct != "") {
						if (constructionsPossibles.contains(construct)) {
							Player player = this.world.getPlayer();
							Construction constr = nameToConst(construct, selectedCase);
							if ( constr.playerCanConstruct( world.getPlayer() ) ) { // Si le joueur a les ressources requises pour la construction :
								selectedCase.setConstruction( constr );
								selectedCase.setBackground( getConstructImage(number));
							} else {
								constructionFailed = true;
							}
						}
					}
				}
			}
			
			// Pour détruire un batiment :
			if ( selectedCase.getConstruction() != null &&
				 x >= coinMenuX + 40 && x < coinMenuX + 90 &&
				 y >= coinBoutonDestruct && y < coinBoutonDestruct + 50 ) {
				
				selectedCase.setConstruction(null);
				selectedCase.setBackground(null);
				selectedCase.setBackgroundAsResource();
			}
		}

		selectedCase = selectCase(x,y); // Récupère la case sélectionnée si elle existe.
		
		// Modification de la liste des constructions à afficher dans le menu des constructions :
		
		if (selectedCase != null) {  // On adapte les listes du menu de constructions à la case nouvellement sélectionnée.
			Image imageTemp;
			constructionsPossibles = selectedCase.infoConstruct();
			imagesConstructions = new ArrayList<Image>();
			
			for (int i=0; i<constructionsPossibles.size(); i++) {
				try{
					imageTemp = new Image("res/images/constructions/"+constructionsPossibles.get(i)+".png");
					imagesConstructions.add( imageTemp.getScaledCopy(imageConstructSize,imageConstructSize) ); // on met toutes les images à la même taille (et carrées)
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (selectedCase != null) {
			menuConstruction = true;
		} else {
			menuConstruction = false;
		}
		
		if (x<48 && x>0 && y<98 && y>50) { // Clic sur l'image de retour.
			selectedCase = null; // On désélectionne la case.
			menuConstruction = false;
			return(true);
		}

		return (false);
		
	}
	
	public void renderMenuConstruct (GameContainer container, StateBasedGame game, Graphics context) {
		// Affiche le menu des constructions

		coinBoutonDestruct = -100;
		if (imagesConstructions.size()!=0) {
			Image img;
			int tailleImg = 128;
			int currentHeight = coinMenuY;
			int largeur, initialHeight;
			int mX = 24;	// mX pour marginX, la marge à droite
			for(int i=0; i<constructionsPossibles.size(); i++) {
				Construction c = nameToConst(constructionsPossibles.get(i), selectedCase);
				
				/** Affichage du nom **/
				largeur = context.getFont().getWidth(c.getName());
				context.setColor(Color.white);
				context.drawString(c.getName(), world.getWidth()-largeur-mX, currentHeight);
				currentHeight += hauteurTextMenuConstruct;
				initialHeight = currentHeight;
				
				/** Affichage de l'image **/
				try {
					img = new Image("res/images/constructions/"+constructionsPossibles.get(i)+".png");
					boutonsConstructions.add(new ButtonV2(img, world.getWidth()-tailleImg-mX-50, currentHeight, tailleImg, tailleImg));
				} catch (SlickException e) {
					e.printStackTrace();
				}
				currentHeight += imageConstructSize;
				
				/** Affichage des coûts **/
				currentHeight = initialHeight;
				//largeur = context.getFont().getWidth("Coûts :");
				//context.drawString("Coûts :", world.getWidth()-largeur-mX, currentHeight);
				//currentHeight += hauteurTextMenuConstruct;
				int currentWidth = world.getWidth()-mX-50;
				for (String k : c.cout.keySet()) {
					try{
						img = new Image(Resource.imagePath(k));
						context.drawImage(img, currentWidth, currentHeight, currentWidth+48, currentHeight+48, 0, 0, img.getWidth(), img.getHeight());
					} catch (SlickException e) {
						e.printStackTrace();
					}
					// Tout ce qui suit sert à afficher la valeur sur la droite :
					largeur = context.getFont().getWidth(Integer.toString(c.cout.get(k).intValue()));
					context.drawString(Integer.toString(c.cout.get(k).intValue()), currentWidth+48-largeur, currentHeight+32);
					currentHeight += 50;
				}
				currentHeight = Math.max(currentHeight+10,initialHeight+imageConstructSize+10);
				
				/** Affichage des débits **/
				largeur = context.getFont().getWidth("Gains :");
				context.drawString("Gains :", world.getWidth()-largeur-mX, currentHeight);
				currentHeight += hauteurTextMenuConstruct;
				currentWidth = world.getWidth()-24;
				for (String k : c.debits.keySet()) {
					try{
						img = new Image(Resource.imagePath(k));
						context.drawImage(img, currentWidth, currentHeight, currentWidth+48, currentHeight+48, 0, 0, img.getWidth(), img.getHeight());
					} catch (SlickException e) {
						e.printStackTrace();
					}
					currentWidth -= 48;
					// Tout ce qui suit sert à afficher la valeur sur la droite :
					largeur = context.getFont().getWidth(Integer.toString(c.debits.get(k).intValue()));
					context.drawString(Integer.toString(c.debits.get(k).intValue()), currentWidth+48-largeur, currentHeight+32);
				}
				currentHeight += 64;
			}
			coinBoutonDestruct = coinMenuY + 15 + imagesConstructions.size() * (imageConstructSize + hauteurTextMenuConstruct);
		} else {
			context.setColor(Color.white);
			context.drawString( "Pas de construction possible", (int) (0.75*world.getWidth()), (int) (0.2*world.getHeight()) );
			context.drawString( "sur cette case", (int) (0.75*world.getWidth()), (int) (0.2*world.getHeight())+24 );
			coinBoutonDestruct = coinMenuY + 200;
		}
		if (selectedCase.getConstruction() != null) {
			context.setColor(Color.red);
			context.drawRect(coinMenuX+40, coinBoutonDestruct, 50, 50);
		}
	}
	
	public String construcRequested(int number) {    // Renvoie le nom du bâtiment numéro 'number' dans le menu des constructions
		if (number<constructionsPossibles.size()) {
			return constructionsPossibles.get(number);
		}
		return "";
	}
	
	public Image getConstructImage(int number) {    // Renvoie le nom du bâtiment numéro 'number' dans le menu des constructions
		if (number<imagesConstructions.size()) {
			return imagesConstructions.get(number);
		}
		return null;
	}
}
