package solar_system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import solar_system.constructions.Ferme;
import solar_system.constructions.Mine;

public class Ground {
	
	private int sizeCase = 80;
	private Planet planet;
	private World world;
	private Case[][] cases;
	private int x_origin;	//Abscisse du coin supérieur gauche
	private int y_origin;	//Ordonnée du coin supérieur gauche
	private Image image;
	private int radius;
	private Air air;
	private Case selectedCase;
	private int padding; // Décalage des cases par rapport au point supérieur gauche de l'image de la planère (global car nécessaire pour la sélection des cases).
	private float facteur_magique;
	private boolean menuConstruction;
	private int coinMenuX;
	private int coinMenuY;
	private int imageConstructSize; // Hauteur des images des constructions, dans le menu des constructions
	private List<String> constructionsPossibles;
	private List<Image> imagesConstructions;
	
	public Ground(Planet planet, World world) {
		/* Créer un objet de classe Ground avec des cases pour sur la planete plt */
		this.planet = planet;
		this.world = world;
		this.radius = (int) Math.floor(this.planet.getRadius()*8.1);
		this.selectedCase = null;
		// Origine de la planète (toutes les longueurs sont multipliées par un certain facteur,
		// le facteur magique.)
		this.facteur_magique = (float)(world.getHeight())/1080;
		this.x_origin = world.getWidth()/2 - (int)(radius*facteur_magique);
		this.y_origin = world.getHeight()/2 - (int)(radius*facteur_magique);
		menuConstruction = false;
		coinMenuX = 10000; // tant que le coin du menu n'a pas été calculé, on prend une grande valeur pour que le menu soit hors du champ.
		coinMenuY = 10000;
		imageConstructSize = 150;
		generateCases();
		
		this.air = new Air(2,(int)(5.0/4)*radius);
		Resource resource = new Resource("Fer"); // TODO : à changer
		air.addOrbital(new Satellite(20,0,100,100, 50,(int)(5.0/4)*radius,resource));
		
		try{  // désormais, image correspond à l'image de la planète en arrière plan.
			this.image = new Image(planet.getNomImage());
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
		
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		
		// Cette fonction contient deux fois "air.render()" : c'est normal (cf définition de air.render())
		air.render(container, game, context, true);
		int x_bas_droite = this.x_origin+(int)(2*radius*facteur_magique);
		int y_bas_droite = this.y_origin+(int)(2*radius*facteur_magique);
		int taille_x = image.getWidth()-1;
		int taille_y = image.getHeight()-1;
		// Affichage de la planète
		context.drawImage(image, x_origin, y_origin, x_bas_droite, y_bas_droite, 0, 0, taille_x, taille_y);
		// Affichage des cases
		for (Case[] tab : cases) {
			for (Case c : tab) {
				c.render(container, game, context);
			}
		}
		// Affichage du 'Air'
		air.render(container, game, context, false);
		// carre rouge pour revenir 
		context.setColor(Color.red);
		context.fillRect(0,50,20,20);
		
		if (selectedCase != null) {
			selectedCase.renderHighlighted (container, game, context);
		}
		// Affichage du Menu des constructions

		if (selectedCase != null) {
			if (selectedCase.getConstruction() == null) {
				coinMenuX = (int)(0.8*world.getWidth());
				coinMenuY = (int)(0.1*world.getHeight());
				renderMenuConstruct(container, game, context);
			} else {
				coinMenuY = world.getHeight()-24*3;
				Construction c = selectedCase.getConstruction();
				context.setColor(Color.white);
				coinMenuX = world.getWidth() - 16 - context.getFont().getWidth(c.getName());
				context.drawString(c.getName(), coinMenuX, coinMenuY);
				coinMenuY += 24;
				coinMenuX = world.getWidth() - 16 - context.getFont().getWidth("Vie : "+c.life + "/" + c.lifeMax);
				context.drawString("Vie : "+c.life + "/" + c.lifeMax, coinMenuX, coinMenuY);
				String les_debits = "";
				for (Map.Entry<String , Double> debit : c.debits.entrySet()) {
					les_debits += ("\t" + debit.getKey() + " : " + debit.getValue());
				}
				coinMenuY += 24;
				coinMenuX = world.getWidth() - 16 - context.getFont().getWidth(les_debits);
				context.drawString(les_debits, coinMenuX, coinMenuY);
			}

		}
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
		for (Case[] tab : cases) {
			for (Case c : tab) {
				c.update(container, game, delta);
			}
		}
		air.update(container, game, delta);
	}
	
	
	public void generateCases() {
		
		// Génère le tableau de dimension 2 "Cases" et le remplit de "Case"
		//TODO : prendre une ressource aléatoire dans une liste
		
		Resource resource;
		int resourceQuantity = 3000;
		
		// Nombre de cases en longueur :
		int n = (int) Math.floor( ((float)Math.sqrt(2) * (float) radius - 14) / (float) sizeCase);
		
		this.cases = new Case[n][n];
		// padding = marge intérieure (distance entre la grille et le bord de l'image)
		padding = (int)( (radius*2-n*sizeCase)*facteur_magique/2 );
		
		for (int i = 0; i < cases.length ; i++) {
			for (int j = 0; j < cases.length; j++ ) {
				if ((i+j)%4==0){
					resource = world.getPlayer().getResource("Fer");
				} else if ((i+j)%4==1) {
					resource = world.getPlayer().getResource("Bois");
				} else if ((i+j)%4==2) {
					resource = world.getPlayer().getResource("Cailloux");
				} else {
					resource = world.getPlayer().getResource("Nourriture");
				}
				cases[i][j] = new Case(x_origin+padding+(int)(i*sizeCase*facteur_magique), y_origin+padding+(int)(j*sizeCase*facteur_magique), (int)(sizeCase*facteur_magique), resource, resourceQuantity);
			}
		}
	}
	
	public Construction nameToConst (String name, Case tile) {
		if(name=="Mine"){
			return new Mine(tile);
		}
		if(name=="Ferme"){
			return new Ferme(tile);
		}
		return null;
	}
	
	public boolean mousePressed(int arg0,int x ,int y) { 
		// Gère les clics sur le Ground.
		
		
		// Construction d'un bâtiment :
		
		if (menuConstruction && selectedCase != null) {  // On vérifie si le joueur veut construire un bâtiment.

			if (x>=coinMenuX && x<=coinMenuX+imageConstructSize && y>=coinMenuY && y<coinMenuY+4*imageConstructSize) { // Clic sur la Mine
				
				int number = (y-coinMenuY)/imageConstructSize;
				String construct = construcRequested(number);
				if (construct != "") {
					if (constructionsPossibles.contains(construct)) {
						selectedCase.setConstruction( nameToConst(construct, selectedCase) );
						selectedCase.setBackground(getConstructImage(number));  // Actuellement, on peut changer l'image sur une case.
					}
				}
			}
		}

		selectedCase = selectCase(x,y); // Récupère la case sélectionnée si elle existe.
		
		// Modification de la liste des constructions à afficher dans le menu des constructions :
		
		if (selectedCase != null) {  // On adapte les listes du menu de constructions à la case nouvellement sélectionnée.
			Image imageTemp;
			constructionsPossibles = selectedCase.infoConstruct(selectedCase);
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
			if(selectedCase.getConstruction()==null) {
				menuConstruction = true;
			}
		} else {
			menuConstruction = false;
		}
		
		if (x<20 && x>0 && y<70 && y>50) { // Clic sur le carré de retour.
			selectedCase = null; // On désélectionne la case.
			menuConstruction = false;
			return(true);
		}

		return (false);
		
	}
	
	public Case selectCase(int x , int y){     // TODO : Correction à faire : pour les cases vers le bas ou vers la gauche, il y a un débordement de la 'hitbox' sur les cases d'à côté
		// Retourne la case dans laquelle le point (x,y) est contenu. 
		int renderedSize = sizeCase * world.getHeight()/1080;
		if (y - (y_origin+padding) < 0 || (y - (y_origin+padding))/renderedSize >= cases.length || x - (x_origin+padding) < 0 || (x - (x_origin+padding))/renderedSize >= cases.length){
			return (null); // Aucune case ne contient (x,y) !
		}
		else {
			return (cases[(x - (x_origin+padding))/renderedSize][(y - (y_origin+padding))/renderedSize]);}
	}
	
	

	public void renderMenuConstruct (GameContainer container, StateBasedGame game, Graphics context) {
		// Affiche le menu des constructions

		if (imagesConstructions.size()!=0) {
			Image img;
			for(int i=0; i<imagesConstructions.size(); i++) {
				img = imagesConstructions.get(i);
				context.drawImage(img, coinMenuX, coinMenuY+i*imageConstructSize);
			}
		} else {
			context.setColor(Color.white);
			context.drawString( "Pas de construction possible", (int) (0.75*world.getWidth()), (int) (0.2*world.getHeight()) );
			context.drawString( "sur cette case", (int) (0.75*world.getWidth()), (int) (0.2*world.getHeight())+24 );
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
