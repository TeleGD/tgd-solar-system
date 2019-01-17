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
	
	public Ground(Planet planet, World world) {
		/* Créer un objet de classe Ground avec des cases pour sur la planete plt */
		this.planet = planet;
		this.world = world;
		this.radius = (int) Math.floor(this.planet.getRadius()*8.1);
		// Calcul du coin haut-gauche de la zone d'affichage (pour l'instant un carré) des cases
		double half_width = radius/Math.sqrt(2)*world.getHeight()/1080;
		this.x_origin =  (int) (world.getWidth()/2 - Math.floor(half_width) ) + 7;
		this.y_origin =  (int) (world.getHeight()/2 - Math.floor(half_width) ) + 7;
		
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
		context.drawImage(image, container.getWidth()/2-radius*world.getHeight()/1080, container.getHeight()/2-radius*world.getHeight()/1080, container.getWidth()/2+radius*world.getHeight()/1080, container.getHeight()/2+radius*world.getHeight()/1080, 0, 0, image.getWidth()-1, image.getWidth()-1);
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
		
		Resource resource = new Resource("test");
		int resourceQuantity = 0;
		
		// Nombre de cases en longueur :
		int n = (int) Math.floor( ((float)Math.sqrt(2) * (float) radius + 14) / (float) sizeCase);
		
		this.cases = new Case[n][n];
		
		for (int i = 0; i < cases.length ; i++) {
			for (int j = 0; j < cases.length; j++ ) {
				cases[i][j] = new Case(x_origin + i*sizeCase*world.getHeight()/1080, y_origin + j*sizeCase*world.getHeight()/1080, sizeCase*world.getHeight()/1080, resource, resourceQuantity);
			}
		}
	}
	public boolean mousePressed(int arg0,int x ,int y) {
		//TODO verifier que le click est dans le carre
		
		return (x<20 && x>0 && y<70 && y>50);
	}
	
	public void clickGround(int x, int y) {
		
	}
}
