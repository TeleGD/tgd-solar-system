package games.solarSystem.util;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import app.AppLoader;

public class Background {
	private int width;
	private int height;
	private double lambda0, phi0;
	private double angle;
	private Image starmap;
	private Image generated;

	public Background(int width, int height){
		this.width = width;
		this.height = height;
		this.starmap = AppLoader.loadPicture("/images/solarSystem/starmap_4k.jpg"); // projection équirectangulaire
	}
	
	public void generate() throws SlickException {
		this.generated = new Image(width, height);
		// TODO : projection de Mercator
	}
}
