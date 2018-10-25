package solar_system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
	private Map<String , Double> ressources = new HashMap<String,Double >(); 
	private int [] technologie = new int[2];
	private List<Planet> knownPlanets;
	private int DiscoveredPlanet;
	
	// initialiZation des ressources du joueur
	
	public Player() {
		ressources.put("Nourriture",0.);
		ressources.put("Fer",10.);
		ressources.put("Noyau Linux",1.);
	}
	
	public void addRessource(String type , double augmentation ){
		if(ressources.containsValue(type)){
		ressources.put(type,ressources.get(type)+augmentation);	
		// Fabien nous garantit que ca ne va pas merder.  
	
		}
	}
}
