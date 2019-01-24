package solar_system;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


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
	private float facteur_magique;
	
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
		
		generateCases();
		
		this.air = new Air(2,(int)(5.0/4)*radius);
		air.addOrbital(new Satellite(20,0,100,100,0.3, 50,(int)(5.0/4)*radius ));
		
		try{
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
		context.drawImage(image, x_origin, y_origin, x_bas_droite, y_bas_droite, 0, 0, taille_x, taille_y);
		for (Case[] tab : cases) {
			for (Case c : tab) {
				c.render(container, game, context);
			}
		}
		air.render(container, game, context, false);
		// carre rouge pour revenir 
		context.setColor(Color.red);
		context.fillRect(0,50,20,20);
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
		int padding = (int)( (radius*2-n*sizeCase)*facteur_magique/2 );
		
		for (int i = 0; i < cases.length ; i++) {
			for (int j = 0; j < cases.length; j++ ) {
				cases[i][j] = new Case(x_origin+padding+(int)(i*sizeCase*facteur_magique), y_origin+padding+(int)(j*sizeCase*facteur_magique), (int)(sizeCase*facteur_magique), resource, resourceQuantity);
			}
		}
	}
	public boolean mousePressed(int arg0,int x ,int y) { 
		// Gère les clics sur le Ground.
		selectedCase = selectCase(x,y); // Récupère la case sélectionnée si elle existe.
		
		if (x<20 && x>0 && y<70 && y>50) { // Clic sur le carré de retour.
			selectedCase = null; // On désélectionne la case.
			return(true);
		}

		return (false);
		
	}
	
	public Case selectCase(int x , int y){
		// Retourne la case dans laquelle le point (x,y) est contenu. 
		int renderedSize = sizeCase * world.getHeight()/1080;
		if (y - y_origin < 0 || (y - y_origin)/renderedSize >= cases.length || x - x_origin < 0 || (x - x_origin)/renderedSize >= cases.length){
			return (null); // Aucune case ne contient (x,y) !
		}
		else {
			return (cases[(x - x_origin)/renderedSize][(y - y_origin)/renderedSize]);}
	}

}
