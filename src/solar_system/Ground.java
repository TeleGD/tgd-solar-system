package solar_system;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

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
	private List<Image> imagesConstructions;  // doit contenir les images de toutes les constructions du jeu (pour le menu des constructions).
	
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
		imagesConstructions = new ArrayList<Image>();

		// on stocke temporairement dans 'image' les images des constructions à ajouter à la liste.
		try{   // Image de la Mine
			this.image = new Image("res/images/constructions/Mine.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		imagesConstructions.add(image);
		try{  // Image de la Ferme
			this.image = new Image("res/images/constructions/Ferme.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		imagesConstructions.add(image);
		
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
		if (menuConstruction) {
			coinMenuX = (int)(0.8*world.getWidth());
			coinMenuY = (int)(0.1*world.getHeight());
			int N_image = 1;
			for(Image img : imagesConstructions) {
				taille_x = img.getWidth()-1;
				taille_y = img.getHeight()-1;
				context.drawImage(img, coinMenuX, coinMenuY+(N_image-1)*200, coinMenuX+200, coinMenuY+N_image*200, 0, 0, taille_x, taille_y);
				N_image++;
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
		
		Resource resource = new Resource("Fer");
		int resourceQuantity = 0;
		
		// Nombre de cases en longueur :
		int n = (int) Math.floor( ((float)Math.sqrt(2) * (float) radius - 14) / (float) sizeCase);
		
		this.cases = new Case[n][n];
		// padding = marge intérieure (distance entre la grille et le bord de l'image)
		padding = (int)( (radius*2-n*sizeCase)*facteur_magique/2 );
		
		for (int i = 0; i < cases.length ; i++) {
			for (int j = 0; j < cases.length; j++ ) {
				cases[i][j] = new Case(x_origin+padding+(int)(i*sizeCase*facteur_magique), y_origin+padding+(int)(j*sizeCase*facteur_magique), (int)(sizeCase*facteur_magique), resource, resourceQuantity);
			}
		}
	}
	public boolean mousePressed(int arg0,int x ,int y) { 
		// Gère les clics sur le Ground.
		
		if (menuConstruction && selectedCase != null) {  // On vérifie si le joueur veut construire un bâtiment.
			
			if (x>=coinMenuX && x<=coinMenuX+200 && y>=coinMenuY && y<coinMenuY+200) { // Clic sur la Mine
				// selectedCase.setConstruction( new Mine(selectedCase) );
				selectedCase.setBackground(imagesConstructions.get(0));  // Actuellement, on peut changer l'image sur une case.
				// TODO : il faudra intégrer le changement d'image à la construction du bâtiment. Cela n'a pas été fait ici car la construction du bâtiment est Bugée.
			}
			if (x>=coinMenuX && x<=coinMenuX+200 && y>=coinMenuY+200 && y<coinMenuY+400) { // Clic sur la deuxième construction
				//TODO : penser à IMPORTER la classe de la construction voulue.
				// selectedCase.setConstruction( new Ferme(selectedCase) );
				selectedCase.setBackground(imagesConstructions.get(1));
			}
		}

		selectedCase = selectCase(x,y); // Récupère la case sélectionnée si elle existe.
		
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

}
