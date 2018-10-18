package solar_system.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Images {
	
	private static ArrayList<Image> planet = new ArrayList<Image>();
	private static HashMap<String,Image> construction = new HashMap<String,Image>();
	
	static {
		try {
			for (int i=0; i<5; i++) {
				planet.add(new Image("res/images/planet/planet"+i+".png"));
			}
			
			construction.put("mine",new Image("res/images/construction/mine.png"));
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
		
	public static Image getPlanet(int i) {
		return planet.get(i);
	}

	public static Image getConstruction(String nom) {
		return construction .get(nom);
	}
}
