package solar_system.util;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Images {
	
	private static ArrayList<Image> planet = new ArrayList<Image>();
	private static ArrayList<Image> satellite = new ArrayList<Image>();
	
	static {
		try {
			for (int i=0; i<5; i++) {
				planet.add(new Image("res/images/planet/planet"+i+".png"));
				satellite.add(new Image("res/images/satellite/satellite"+i+".png"));
				
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
		
	public static Image getPlanet(int i) {
		return planet.get(i);
	}

	public static Image getSatellite(int i) {
		return satellite .get(i);
	}
}
