package solar_system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Player {
	private Map<String , Double> ressources = new HashMap<String,Double >();
	private int [] technologie = new int[2];
	private List<Planet> knownPlanets;
	private int DiscoveredPlanet;
	private World world;
	
	
	// initialiZation des ressources du joueur
	
	public Player(World world) {
		ressources.put("Nourriture",0.);
		ressources.put("Fer",10.);
		ressources.put("Noyau Linux",1.);
		this.world=world;
		
	}
	

	public void render(GameContainer container, StateBasedGame game, Graphics context){
		int size;
		int comp=0;
		size=ressources.size();//taille du hashmap
		context.setColor(Color.black);
		context.fillRect(0, 0,world.getWidth(),world.getHeight()/20);
		for(Map.Entry<String , Double> ressource: ressources.entrySet()){
			context.setColor(Color.white);
			context.drawString(ressource.getKey()+" : "+ressource.getValue(), world.getWidth()*comp/size, world.getHeight()/40);//Essai d'affichage des ressources :reste à miodif l'emplacement de chaque(pas 399,10));
			comp+=1;
		}
		
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
		
		
	}
	
	
	public void addRessource(String type , double augmentation ){
		if(ressources.containsValue(type)){
		ressources.put(type,ressources.get(type)+augmentation);	
		// Fabien nous garantit que ca ne va pas merder.  
	
		}
	}
}
