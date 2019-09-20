package solar_system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;
import app.ui.ButtonV2;

import solar_system.constructions.Ferme;
import solar_system.constructions.Mine;
import solar_system.constructions.Mine2;
import solar_system.constructions.TNCY;
import solar_system.constructions.Vaisseau;
import solar_system.constructions.Scierie;
import solar_system.constructions.CabaneBucheron;

import java.util.concurrent.ThreadLocalRandom;

public class Ground {

	private int sizeCase = 80;
	private Planet planet;
	private World world;
	private Case[][] cases;
	private int x_origin;	//Abscisse du coin supérieur gauche
	private int y_origin;	//Ordonnée du coin supérieur gauche
	private Image image;
	private Image imageBack; // image du bouton pour retourner au système
	private int radius;
	private Case selectedCase;
	private int padding; // Décalage des cases par rapport au point supérieur gauche de l'image de la planère (global car nécessaire pour la sélection des cases).
	private float facteur_magique;
	private boolean menuConstructionBoolean;
	private int coinMenuX; // Pour le menu des constructions
	private int coinMenuY;
	private int coinInfoX; // Pour les informations sur les constructions
	private int coinInfoY;
	private int imageConstructSize; // Hauteur des images des constructions, dans le menu des constructions
	private List<String> constructionsPossibles;
	private List<Image> imagesConstructions;
	private List<ButtonV2> boutonsConstructions = new ArrayList<>();
	private int hauteurTextMenuConstruct;
	private int coinBoutonDestruct; // position verticale du bouton pour détruire un batiment
	protected boolean constructionFailed; // Vaut true si le joueur a demandé la construction d'un batiment pour lequel il n'a pas assez de ressources
	private MenuConstruction menuConstruction;

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
		menuConstructionBoolean = false;

		coinMenuX = (int)(0.8*world.getWidth());
		coinMenuY = (int)(0.1*world.getHeight());
		menuConstruction = new MenuConstruction(world, planet, coinMenuX, coinMenuY);

		coinMenuX = 10000; // tant que le coin du menu n'a pas été calculé, on prend une grande valeur pour que le menu soit hors du champ.
		coinMenuY = 10000;
		imageConstructSize = 150;
		hauteurTextMenuConstruct = 26;
		constructionFailed = false;
		generateCases();

		//this.air = new Air(5,(int)(5.0/4)*radius,this.world,planet);
		Resource resource = new Resource("Fer"); // TODO : à changer
		/////air.addOrbital(new Satellite(20,0,100,100, 50,(int)(5.0/4)*radius,resource,this.world));

		// désormais, image correspond à l'image de la planète en arrière plan.
		this.image = AppLoader.loadPicture(planet.getNomImage()).copy();
		this.imageBack = AppLoader.loadPicture("/images/retour.png");
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {

		// Cette fonction contient deux fois "air.render()" : c'est normal (cf définition de air.render())
		//air.render(container, game, context, true);
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
		//air.render(container, game, context, false);

		// imageBack pour revenir
		context.drawImage(imageBack.getScaledCopy(48, 48), 0, 64);

		if (selectedCase != null) {
			selectedCase.renderHighlighted (container, game, context);
		}

	}

	public void update (GameContainer container, StateBasedGame game, int delta) {
		boolean isOwner = this.planet.getOwner() == this.world.getPlayer();
		for (Case[] tab : cases) {
			for (Case c : tab) {
				if (isOwner) c.setAlpha(1);
				else c.setAlpha(0.5f);
				c.update(container, game, delta);
			}
		}
		if (this.menuConstruction != null) this.menuConstruction.update(container, game, delta);
		if (isOwner) this.image.setAlpha(1);
		else this.image.setAlpha(0.5f);
	}


	public void generateCases() {
		// Génère le tableau de dimension 2 "heightCases" et le remplit de "Case"

		Resource resource;
		int resourceQuantity;

		// Nombre de cases en longueur :
		int n = (int) Math.floor( ((float)Math.sqrt(2) * (float) radius - 14) / (float) sizeCase);
		
		// Définition des ressources à placer à partir des ressources minimales requises
		ArrayList<String> resourcesNames = new ArrayList<>();
		if (planet.getMinResources() != null) {
			for (String name : planet.getMinResources().keySet()) {
				int quantity = planet.getMinResources().get(name);
				for(int i = 0; i < quantity; i++) resourcesNames.add(name);
			}
		}
		
		// On complète la liste des ressources avec des ressources aléatoires (s'il reste de la place)
		while (resourcesNames.size() < n*n) {
			resourcesNames.add(randomResourceName());
		}
		
		// On mélange tout !
		Collections.shuffle(resourcesNames);

		this.cases = new Case[n][n];
		// padding = marge intérieure (distance entre la grille et le bord de l'image)
		padding = (int)( (radius*2-n*sizeCase)*facteur_magique/2 );

		for (int i = 0; i < n ; i++) {
			for (int j = 0; j < n; j++ ) {
				String name = resourcesNames.get(n*i+j);
				resource = world.getPlayer().getResource(name);
				resourceQuantity = resourceQuantity(name);

				cases[i][j] = new Case(x_origin+padding+(int)(i*sizeCase*facteur_magique), y_origin+padding+(int)(j*sizeCase*facteur_magique), (int)(sizeCase*facteur_magique), resource, resourceQuantity);
			}
		}
	}

	public Building nameToConst (String name, Case tile) {
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

	public String randomResourceName() {

		String resource;
		int rand = ThreadLocalRandom.current().nextInt(0, 17);  // Donne un nombre entier aléatoire entre 0 et 4 inclus

		// Pour le noyau Linux :
		if (rand==0) {
			resource = "Noyau Linux";
		} else if (rand%4==0){
			resource = "Fer";
		} else if (rand%4==1) {
			resource = "Bois";
		} else if (rand%4==2) {
			resource = "Cailloux";
		} else {
			resource = "Nourriture";
		}
		return resource;
	}

	public int resourceQuantity(String res) {
		switch (res) {
			case "Fer" :
				return 500;
			case "Bois" :
				return 1500;
			case "Cailloux" :
				return 3000;
			case "Nourriture" :
				return 2000;
			case "Noyau Linux" :
				return 10;
			default :
				return 0;
		}
	}

	public boolean mousePressed(int arg0, int x, int y) {
		// Gère les clics sur le Ground.

		if (constructionFailed) { // pour faire disparaitre le message, le joueur doit cliquer n'importe où.
			constructionFailed = false;
		}

		// Construction d'un bâtiment :
		if (menuConstruction.mousePressed(arg0, x, y)) return false;

		selectedCase = selectCase(x,y); // Récupère la case sélectionnée si elle existe.
		menuConstruction.casePressed(selectedCase);



		// Modification de la liste des constructions à afficher dans le menu des constructions :


		if (x<48 && x>0 && y<98 && y>50) { // Clic sur l'image de retour.
			selectedCase = null; // On désélectionne la case.
			menuConstructionBoolean = false;
			return(true);
		}

		return (false);

	}

	public void mouseWheelMoved(int change) {
		menuConstruction.moveY(change);
	}

	public Case selectCase(int x , int y){     // TODO : Correction à faire : pour les cases vers le bas ou vers la gauche, il y a un débordement de la 'hitbox' sur les cases d'à côté
		// Retourne la case dans laquelle le point (x,y) est contenu.
		int renderedSize = sizeCase * world.getHeight()/1080;
		if (y - (y_origin+padding) < 0 || (y - (y_origin+padding))/renderedSize >= cases.length || x - (x_origin+padding) < 0 || (x - (x_origin+padding))/renderedSize >= cases.length){//Si le clic est hors de la grille de la planète
			System.out.println("On a pas cliqué sur la grille visiblement...");
			if(planet.getAir().mousePressed(0,x,y)==null){//
				System.out.println("C'est très null tout ça...");
				return (null); // Aucune case ne contient (x,y) !
			}
			else{//Sinon retourner la case sur l'orbitale cliquée
				Orbital orbital = planet.getAir().mousePressed(0,x,y);
				System.out.println("Hey, la case est renvoyée, j'en fais quoi ?");
				return(orbital.getCase());
			}
		}
		else {
			return (cases[(x - (x_origin+padding))/renderedSize][(y - (y_origin+padding))/renderedSize]);}
	}

	public void renderMenuConstruct (GameContainer container, StateBasedGame game, Graphics context) {
		menuConstruction.render(container, game, context);
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
