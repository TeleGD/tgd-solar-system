package solar_system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Player {
	private Map<String , Resource> resources = new HashMap<String,Resource >();
	private int [] technologie = new int[2];
	private List<Planet> knownPlanets;
	private int DiscoveredPlanet;
	
	private World world;
	
	
	// initialiZation des ressources du joueur
	
	public Player(World world) {
		initRessources();
		this.world=world;
	}
	

	public void render(GameContainer container, StateBasedGame game, Graphics context){
		int size;
		int comp=0;
		size=resources.size()+1;//taille du hashmap
		context.setColor(Color.black);
		context.fillRect(0, 0,world.getWidth(),world.getHeight()/20);
		for(Map.Entry<String , Resource> resource: resources.entrySet()){
			context.setColor(Color.white);
			//Essai d'affichage des ressources :reste à miodif l'emplacement de chaque(pas 399,10));
			context.drawString(resource.getKey()+" : "+(int)resource.getValue().getQuantite(), 50 + world.getWidth()*comp/size, world.getHeight()/40);
			// affichage de l'image de la ressource "scaled"
			context.drawImage(resource.getValue().getImage().getScaledCopy(40,40),world.getWidth()*comp/size,world.getHeight()/50);
			comp+=1;
		}
		context.drawString("Autres Ressources...", world.getWidth()*size/(size+1), world.getHeight()/40);
		if(world.getDispRessources()){
			context.drawString(resourcesToString(),world.getWidth()*size/(size+1) , world.getHeight()/20);
		}
		
	}
	
/////////////////////////////////////////////////////////////////////
	//VA CHERCHER TES RESSOURCES TOI MEME !!! <3//
/////////////////////////////////////////////////////////////////////

	public void update (GameContainer container, StateBasedGame game, int delta) {
		
	}
	
	public Resource getResource(String name) {
		return resources.get(name);
	}
	
	public void initRessources(){
			resources.put("Bois",new Resource("Bois"));
			resources.put("Fer",new Resource("Fer"));
			resources.put("Noyau Linux", new Resource("Noyau Linux"));
			resources.put("Cailloux",new Resource("Cailloux"));
			resources.put("Nourriture", new Resource("Nourriture"));
		}
	
	public String resourcesToString(){
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String , Resource> resource: resources.entrySet()){
			sb.append(resource.getKey()+" : "+(int)resource.getValue().getQuantite()+"\n");
		}
		return sb.toString();
	}
	
	public Map<String,Resource> getResources(){
		return resources;
	}
	
	public boolean mousePressed(int arg0,int x ,int y){
		//Aled les commentaires ???
		return (x<world.getWidth() && x>world.getWidth()*resources.size()/(resources.size()+1) && y<world.getHeight()/20 && y>0);
	}
}