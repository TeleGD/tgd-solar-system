package games.solarSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppLoader;

import games.solarSystem.constructions.Vaisseau;
import games.solarSystem.constructions.vaisseaux.Tesla;

public class Solsys {
	private int nbPlanet;
	private List<Planet> planets;
	private World world;
	private double centerX, centerY;
	private double zoom;
	private Image imageSun;
	private Velocity velocity;
	private Planet refPlanet;
	private int currentKey; // ID de la touche actuellement pressée (-1 si aucune touche)
	private boolean leftClick; // indique si le bouton gauche de la souris est pressé ou non
	private MenuVaisseau menuVaisseau;
	private Vaisseau vaisseau;
	private ArrayList<Vaisseau> vaisseauList;
	private ArrayList<Explosion> explosions;
	private String cheatCode;
	private boolean ended; // le jeu est-il terminé ?

	public Solsys(int nbPlanet, World world) {
		System.out.println(nbPlanet);
		this.nbPlanet= nbPlanet;
		this.planets = new ArrayList<Planet>();
		this.vaisseauList = new ArrayList<Vaisseau>();
		this.explosions = new ArrayList<Explosion>();
		this.world = world;
		//  addPlanet(new Planet(9,0,0,75,"des",world));  Permet d'ajouter une planète à la place du Soleil
		for(int k=0; k<nbPlanet-1; k++ ) {
			// On ajoute la planète mère sur l'orbite médiane
			// (lorsque le compteur de planète arrive à la moitié du nombre de planètes total dans le système solaire)
			if (k == nbPlanet/2) {
				HashMap<String, Integer> minResources = new HashMap<String, Integer>();
				minResources.put("Bois", 30);
				minResources.put("Fer", 11);
				minResources.put("Nourriture", 23);
				Planet p = new Planet(1+k,200+110*k, 64, minResources, 0, 1, "description",world);
				// Ci-dessus, 0 et 1 représentent respectivement le nombre minimum de satellites et le nombre de stations
				p.setOwner(world.getPlayer());
				addPlanet(p);
			}
			else addPlanet(new Planet(1+k,200+110*k ,"description",world));
		}
		this.imageSun = AppLoader.loadPicture("/images/solarSystem/planets/soleil.png");
		this.imageSun = imageSun.getScaledCopy(300,300);
		this.currentKey = -1;
		this.centerX = world.getWidth()/2;
		this.centerY = world.getHeight()/2;
		this.zoom = 1d;
		this.cheatCode = "1000";
		this.ended = false;
	}

	public void addPlanet(Planet p) {
		if (planets.size() < nbPlanet)
			planets.add(p);
	}

	public Planet planetTouched(int x, int y) {
  		int[] pos = fromScreenPosition(x, y);
		for(Planet p : planets) {
			if(Math.hypot(pos[0]-p.getPosX(), pos[1]-p.getPosY()) < p.getRadius()) {
				if (this.menuVaisseau == null || !this.menuVaisseau.isPressed(x, y))
					return p;
			}
		}
		return null;
	}

  	public void setVelocityVector(Planet p, Velocity v) {
  		this.refPlanet = p;
  		this.velocity = v;
 	}

  	public void rightClick(int xmouse, int ymouse) {
		this.velocity = null;
  		this.refPlanet = this.planetTouched(xmouse, ymouse);
  		if (refPlanet != null) {
  	  		this.menuVaisseau = new MenuVaisseau("Lancer un vaisseau", xmouse, ymouse);
  	  		for (String name : refPlanet.getVaisseauxNames()) {
  	  			if (refPlanet.getNbVaisseaux(name) > 0) {
  	  				this.menuVaisseau.addVaisseau(refPlanet.getVaisseau(name), refPlanet.getNbVaisseaux(name));
  	  			}
  	  		}
  		}
  		else {
  			this.menuVaisseau = null;
  		}
  	}

  	public void setZoom(double zoom) {
  		this.zoom = zoom;
  	}

  	public float getZoom() {
  		return (float)this.zoom;
  	}

  	public double getCenterX() {
  		return this.centerX;
  	}

  	public double getCenterY() {
  		return this.centerY;
  	}

  	public int[] toScreenPosition(double x, double y) {
  		// Convertit une position dans le référentiel du système solaire (à zoom 1)
  		// en position à afficher à l'écran en fonction de la position du centre et du zoom.
  		x *= zoom;
  		y *= zoom;
  		x += this.centerX;
  		y += this.centerY;
  		int[] pos = {(int)x, (int)y};
  		return pos;
  	}

  	public int[] fromScreenPosition(double x, double y) {
  		// Convertit une position dans le référentiel du système solaire (à zoom 1)
  		// en position à afficher à l'écran en fonction de la position du centre et du zoom.
  		x -= this.centerX;
  		y -= this.centerY;
  		x /= zoom;
  		y /= zoom;
  		int[] pos = {(int)x, (int)y};
  		return pos;
  	}

  	public void mousePressed(int arg0, int x, int y) {
  		if (arg0 == 0) { // Clic gauche
  			this.leftClick = true;
  			if (this.menuVaisseau != null && !this.menuVaisseau.isPressed(x, y)) {
  				this.menuVaisseau = null;
  			} else if (this.menuVaisseau != null && this.menuVaisseau.isPressed(x, y)) {
  				int i = this.menuVaisseau.getPressedItem(x, y);
  				if (i >= 0) this.vaisseau = this.menuVaisseau.getItem(i).getVaisseau();
  				if (this.vaisseau != null) this.velocity = new Velocity(0.3, this.vaisseau.getV0Max(), 0, this);
  				this.menuVaisseau = null;
  			}
  		}
	}

  	public void mouseReleased(int arg0, int x, int y) {
  		if (arg0 == 0) { // Clic gauche
  			this.leftClick = false;
  		}
	}

	public void mouseWheelMoved(int change) {
		if (velocity != null && currentKey != Input.KEY_LCONTROL && currentKey != Input.KEY_RCONTROL) {
			velocity.setVelocity(velocity.getNorm()+(float)change/10000, velocity.getAngle());
		} else if ((zoom > 0.02 || change > 0) && (zoom < 50 || change < 0)) {
			double factor = 1+(double)change/2000;
			zoom *= factor;
			centerX += (centerX - Mouse.getX())*(factor-1);
			centerY += (centerY - (this.world.getHeight()-Mouse.getY()))*(factor-1);
		}
	}

	public void keyPressed(int key, char c) {
		this.currentKey = key;
		if (key == Input.KEY_ENTER) {
			if (vaisseau != null && velocity != null) {
				Vaisseau vt = refPlanet.getVaisseau(vaisseau.getName());
				//TODO : Comprendre pourquoi le vaisseau obtenu par la ligne ci-dessus peut être nul
				// (il s'agit de la seule ligne où l'on ajoute des éléments à vaisseauList, et on
				// s'est déjà retrouvé avec un élément null en itérant sur cette liste...)
				if (vt != null) this.vaisseauList.add(vt);
				for (Vaisseau v : vaisseauList) {
					if (!v.isLaunched()) {
						v.launch(velocity.getX(), velocity.getY(), velocity.getNorm()*Math.cos(velocity.getAngle()), velocity.getNorm()*Math.sin(velocity.getAngle()));
					}
				}
				refPlanet.removeVaisseau(vaisseau.getName());
				this.vaisseau = null;
				this.velocity = null;
			}
		}
		else if (key == Input.KEY_SPACE) {
			if (this.velocity != null)
				this.velocity.makeSimulation();
		}
		else if (key == Input.KEY_0) {  // Cheat pour coloniser toutes les planètes
			for (Planet p : this.planets) {
				p.setOwner(world.getPlayer());
			}
		}
		// Recentrer l'affichage
		else if (key == Input.KEY_C) {
			this.zoom = 1;
			this.centerX = this.world.getWidth()/2;
			this.centerY = this.world.getHeight()/2;
		}
		if (c == this.cheatCode.charAt(0)) this.cheatCode = this.cheatCode.substring(1);
		else this.cheatCode = "1000";
		if (this.cheatCode.length() == 0) {
			for (String res : this.world.getPlayer().getResources().keySet()) {
				this.world.getPlayer().getResource(res).modifQuantite(1000);
			}
			this.cheatCode = "1000";
		}

		if (key == Input.KEY_E){    //TODO :
			if (ThreadLocalRandom.current().nextBoolean()) {
				explosions.add(new Explosion(this, "/images/solarSystem/animations/explosion_circle.png", "/sounds/solarSystem/explosion_planet.ogg" , 300, 300,256, 128, ThreadLocalRandom.current().nextInt(0, world.getWidth() - 300), ThreadLocalRandom.current().nextInt(0, world.getHeight() - 300), 3, 4, 0, 1300));
//				explosions.add(new Explosion(this, "/images/solarSystem/animations/explosion_circle.png", "/sounds/solarSystem/explosion_ship.wav" , 300,256, 128, ThreadLocalRandom.current().nextInt(0, world.getWidth() - 300), ThreadLocalRandom.current().nextInt(0, world.getHeight() - 300), 3, 4, 0, 1300, 300));
			} else {
				explosions.add(new Explosion(this, "/images/solarSystem/animations/black_hole.png", "/sounds/solarSystem/explosion_ship.ogg" , 400, 300,480, 270, ThreadLocalRandom.current().nextInt(0, world.getWidth() - 300), ThreadLocalRandom.current().nextInt(0, world.getHeight() - 300), 8, 11, 0, 1300));
			}
		}
	}

	public void keyReleased(int key, char c) {
		this.currentKey = -1;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		for (Planet p: planets) {
			int radius = p.getRadius();
			int[] pos = toScreenPosition(p.getPosX()-radius, p.getPosY()-radius);
			Image scaledImg = p.getImage().getScaledCopy((float)zoom);
			scaledImg.setAlpha(p.getImage().getAlpha());
			context.drawImage(scaledImg, pos[0], pos[1]);
		}
		//planets.get(0).render(container, game, context);
		for (Vaisseau v : vaisseauList) v.render(container, game, context);
		for (Explosion explosion: explosions) {
			explosion.render(container, game, context);
		}
		int[] pos = toScreenPosition(-imageSun.getWidth()/2, -imageSun.getHeight()/2);
		context.drawImage(imageSun.getScaledCopy((float)zoom), pos[0], pos[1]);
		if (this.velocity != null) this.velocity.render(container, game, context);
		if (this.menuVaisseau != null) this.menuVaisseau.render(container, game, context);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		boolean colliding = false;
		if (!ended && planets.isEmpty()) {
			ended = true;
			game.enterState(4, new FadeOutTransition(), new FadeInTransition());
		}
		for (int i = planets.size()-1; i >= 0; i--) {
			Planet p = planets.get(i);
			float angle=p.getAngle()+(float)delta/p.getPeriode();
			p.setAngle(angle);
			float distance=p.getDistance();
			p.setPosX((float)Math.cos((double)angle)*distance);
			p.setPosY((float)Math.sin((double)angle)*distance);
			p.update(container, game, delta);
			// Collision du vaisseau avec la planète
			for (Vaisseau v : vaisseauList) {
				// Le -v.getSize()/4 permet de détecter une collision non pas quand le centre du vaisseau arrive
				// sur la planète (autrement dit si le centre du vaisseau et le centre de la planètes sont distancés
				// de moins du rayon de la planète), mais plutôt quand environ un quart du vaisseau commence à entrer
				// dans la planète.
				if (v.getDistance(p)-v.getSize()/4 < p.getRadius()) {
					v.crash(p);
					colliding = true;
				}
			}
			if (p.isDestructed()) {
				Explosion explosion = new Explosion(this, "/images/solarSystem/animations/black_hole.png", "/sounds/solarSystem/explosion_planet.ogg", p.getRadius()*3, p.getRadius()*3, 480, 270, (int) p.getPosX() - p.getRadius(), (int) p.getPosY() - p.getRadius(), 8, 11, 0, 1300);
				planets.remove(i);
				vaisseauList.addAll(p.getDebris());
				// Explosion de la planète :
				explosions.add(explosion);
			}
			if (refPlanet != null && refPlanet.isDestructed()) {
				refPlanet = null;
				menuVaisseau = null;
				velocity = null;
			}
		}
		// Faire avancer chaque vaisseau et gérer la collision avec le soleil
		for (int i = 0; i < vaisseauList.size(); i++) {
			Vaisseau v = vaisseauList.get(i);
			if (!colliding && !v.hasLeft()) v.left();
			double distance2 = Math.pow(v.getX(), 2) + Math.pow(v.getY(), 2);
			if (distance2 < Math.pow(this.imageSun.getWidth()*0.6, 2)/2) {
				v.crash(null);
			}
			// On a lancé une Tesla super loin
			if (!ended && v instanceof Tesla && Math.hypot(v.getX(), v.getY()) > 2000) {
				ended = true;
				game.enterState(5, new FadeOutTransition(), new FadeInTransition());
			}
			// Gestion des crashs avec les autres vaisseaux
			for (int j = 0; j < i; j++) {
				Vaisseau v2 = vaisseauList.get(j);
				/* On vérifie au préalable que le vaisseau v2 n'est pas déjà crashé et que sa taille
				 * est supérieure à 12 pixels (puisque le vaisseau est subdivisé en "sous-vaisseaux" à
				 * chaque collision, on arrête les subdivisions lorsque le vaisseau est trop petit) */
				if (!v2.hasCrashed() && v2.getSize() > 16 && v.getDistance(v2) < (v.getSize()+v2.getSize())/2) {
					v.split(2);
					v2.split(2);
					vaisseauList.addAll(v.getDebris());
					vaisseauList.addAll(v2.getDebris());
				}
			}
			v.update(container, game, delta);
		}
		// Supprimer les vaisseaux crashés
		for (int i = vaisseauList.size()-1; i >= 0; i--) if (vaisseauList.get(i).hasCrashed()) vaisseauList.remove(i);
		// Changement de la norme et de la direction de la vélocité
		if (this.velocity != null) {
			velocity.setPos((int)refPlanet.getPosX(), (int)refPlanet.getPosY());
			if (currentKey == Input.KEY_LEFT) {
				velocity.moveAngle(-0.01);
			} else if (currentKey == Input.KEY_RIGHT) {
				velocity.moveAngle(+0.01);
			} else if (currentKey == Input.KEY_UP) {
				velocity.setVelocity(velocity.getNorm()+0.002, velocity.getAngle());
			} else if (currentKey == Input.KEY_DOWN) {
				velocity.setVelocity(velocity.getNorm()-0.002, velocity.getAngle());
			}
			if (leftClick) {
				int mousePos[] = fromScreenPosition(Mouse.getX(), world.getHeight()-Mouse.getY());
				float dx = mousePos[0]-velocity.getX();
				float dy = mousePos[1]-velocity.getY();
				double angle = Math.PI/2;
				if (dx != 0) angle = Math.atan(dy/dx);
				if (dx < 0) angle += Math.PI;
				if (dx == 0 && dy < 0) angle = -Math.PI/2;
				velocity.setVelocity(Math.abs(velocity.getNorm()), angle);
			}
			velocity.update(container, game, delta);
		}
		// Position du menu des vaisseaux
		if (this.menuVaisseau != null) {
	  		int[] pos = toScreenPosition(refPlanet.getPosX(), refPlanet.getPosY());
			this.menuVaisseau.setPos(pos[0], pos[1]);
			this.menuVaisseau.update(container, game, delta);
		}
		// Gestion des explosions
		for (int i = explosions.size()-1; i >= 0 ; i--) {
			explosions.get(i).update(container, game, delta);
		}
		// Déplacement de l'affichage avec la souris
		if (Mouse.isButtonDown(0) && (this.velocity == null || Keyboard.isKeyDown(Input.KEY_LCONTROL) || Keyboard.isKeyDown(Input.KEY_RCONTROL))) {
			this.centerX += Mouse.getDX();
			this.centerY -= Mouse.getDY();
		}
	}

	public void removeExplosion(Explosion explosion){
		explosions.remove(explosion);
	}

	public World getWorld() {
		return world;
	}
}
