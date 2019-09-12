package solar_system;


import java.util.*;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;


import app.AppLoader;

public class Solsys {
	private int nbPlanet;
	private List<Planet> planets;
	private World world;
	private Image imageSun;
	private Velocity velocity;
	private Planet refPlanet;
	private int currentKey; // ID de la touche actuellement pressée (-1 si aucune touche)
	private boolean leftClick; // indique si le bouton gauche de la souris est pressé ou non
	private Spaceship spaceship;

	public Solsys(int nbPlanet, World world) {
		System.out.println(nbPlanet);
		this.nbPlanet= nbPlanet;
		this.planets = new ArrayList<Planet>();
		this.world = world;
		//  addPlanet(new Planet(9,0,0,75,"des",world));  Permet d'ajouter une planète à la place du Soleil
		for(int k=0; k<nbPlanet-1; k++ ) {
			// On ajoute la planète mère sur l'orbite médiane
			// (lorsque le compteur de planète arrive à la moitié du nombre de planètes total dans le système solaire)
			if (k == nbPlanet/2) {
				HashMap<String, Integer> minResources = new HashMap<String, Integer>();
				minResources.put("Bois", 40);
				minResources.put("Fer", 12);
				minResources.put("Nourriture", 30);
				Planet p = new Planet(1+k,200+110*k, 64, minResources, 0, 1, "description",world);
				p.setOwner(world.getPlayer());
				addPlanet(p);
			}
			else addPlanet(new Planet(1+k,200+110*k ,"description",world));
		}
		this.imageSun = AppLoader.loadPicture("/images/planets/soleil.png");
		this.imageSun = imageSun.getScaledCopy(300,300);
		this.currentKey = -1;
	}

	public void addPlanet(Planet p) {
		if (planets.size() < nbPlanet)
			planets.add(p);
	}

	public Planet planetTouched(int x, int y)
	{
		for(Planet p : planets) {
			if(Math.hypot(x-p.getPosX()-world.getWidth()/2, y-p.getPosY()-world.getHeight()/2) < p.getRadius()) {
				return p;
			}
		}
		return null;
	}

  	public void setVelocityVector(Planet p, Velocity v) {
  		this.refPlanet = p;
  		this.velocity = v;
 	}

  	public void mousePressed(int arg0, int x, int y) {
  		if (arg0 == 0) { // Clic gauche
  			this.leftClick = true;
  		}
	}

  	public void mouseReleased(int arg0, int x, int y) {
  		if (arg0 == 0) { // Clic gauche
  			this.leftClick = false;
  		}
	}

	public void mouseWheelMoved(int change) {
		if (velocity != null) {
			velocity.setVelocity(velocity.getNorm()+(float)change/10000, velocity.getAngle());
		}
	}

	public void keyPressed(int key, char c) {
		this.currentKey = key;
		if (key == Input.KEY_ENTER) {
			if (velocity != null) this.spaceship = new Spaceship(velocity.getX(), velocity.getY(), velocity.getNorm()*Math.cos(velocity.getAngle()), velocity.getNorm()*Math.sin(velocity.getAngle()), world);
		}
		else if (key == Input.KEY_SPACE) {
			this.velocity.makeSimulation(world.getWidth()/2, world.getHeight()/2);
		}
		else if (key == Input.KEY_0) {
			for (Planet p : this.planets) {
				p.setOwner(world.getPlayer());
			}
		}
	}

	public void keyReleased(int key, char c) {
		this.currentKey = -1;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		for (Planet p: planets) {
			int radius =p.getRadius();
			context.drawImage(p.getImage(),p.getPosX()+world.getWidth()/2-radius,p.getPosY()+world.getHeight()/2-radius);
		}
		//planets.get(0).render(container, game, context);
		if (this.spaceship != null) this.spaceship.render(container, game, context);
		context.drawImage(imageSun,world.getWidth()/2-150,world.getHeight()/2-150);
		if (this.velocity != null) this.velocity.render(container, game, context);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		boolean colliding = false;
		for (Planet p : planets) {
			float angle=p.getAngle()+(float)delta/p.getPeriode();
			p.setAngle(angle);
			float distance=p.getDistance();
			p.setPosX((float)Math.cos((double)angle)*distance);
			p.setPosY((float)Math.sin((double)angle)*distance);
			p.update(container, game, delta);
			// Collision du vaisseau avec la planète
			if (this.spaceship != null) {
				double distance2 = Math.pow(spaceship.getX()-(p.getPosX()+world.getWidth()/2), 2)+ Math.pow(spaceship.getY()-(p.getPosY()+world.getHeight()/2), 2);
				if (distance2 < Math.pow(p.getRadius(), 2)) {
					this.spaceship.crash();
					if (this.spaceship.hasLeft()) p.setOwner(this.world.getPlayer());
					colliding = true;
				}
			}
		}
		if (this.spaceship != null) {
			if (!colliding && !this.spaceship.hasLeft()) this.spaceship.left();
			double distance2 = Math.pow(spaceship.getX()-world.getWidth()/2, 2)+ Math.pow(spaceship.getY()-world.getHeight()/2, 2);
			if (distance2 < Math.pow(this.imageSun.getWidth()*0.6, 2)/2) {
				this.spaceship.crash();
			}
		}
		if (this.velocity != null) {
			//velocity.moveAngle((float)delta/refPlanet.getPeriode());
			velocity.setPos((int)refPlanet.getPosX()+world.getWidth()/2, (int)refPlanet.getPosY()+world.getHeight()/2);
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
				float dx = Mouse.getX()-velocity.getX();
				float dy = world.getHeight()-Mouse.getY()-velocity.getY();
				double angle = Math.PI/2;
				if (dx != 0) angle = Math.atan(dy/dx);
				if (dx < 0) angle += Math.PI;
				if (dx == 0 && dy < 0) angle = -Math.PI/2;
				velocity.setVelocity(Math.abs(velocity.getNorm()), angle);
			}
			velocity.update(container, game, delta);
		}
		if (this.spaceship != null) this.spaceship.update(container, game, delta);
	}

}
