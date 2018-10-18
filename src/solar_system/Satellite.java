package solar_system;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import solar_system.util.Images;

public class Satellite extends Construction{
	private int debit;
	private String type;
	private Image image;
	
	public Satellite(int d, String t) {
		this.debit = d;
		this.type = t;
		this.image = Images.getSatellite((new Random()).nextInt(5));
	}
	
	public int getDebit() {
		return debit;
	}
	public String getType() {
		return type;
	}
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
		context.drawImage(image,container.getWidth()/2-radius,container.getHeight()/2-radius,container.getWidth()/2+radius,container.getHeight()/2+radius, 0, 0, image.getWidth()-1, image.getHeight()-1);
	}
}
