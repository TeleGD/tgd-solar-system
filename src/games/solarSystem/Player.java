package games.solarSystem;

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

	private int yMinUI;

	private World world;


	// initialiZation des ressources du joueur

	public Player(World world) {
		initRessources();
		this.world=world;
		initUI();
	}

	/**
	 * Initialise les attributs de l'affichage des ressources en haut de l'écran
	 */
	public void initUI(){
		yMinUI = world.getHeight()/50+40;
		//TODO : initialiser ici plus de trucs pour l'affichage du menu, plutôt que de les calculer dans render()
	}


	public void render(GameContainer container, StateBasedGame game, Graphics context){
		int nbRes=0;//Compteur des ressources(sans les cailloux)
		int chaineWidth=0;//la longueur toutale de la chaine de caractère et des images
		String str;
		String str2;
		int esp;//Espaces entre chaque ressource affichée
		int chaineHeight;//Hauteur d'une chaine de caractère
		int posY;//Emplacement du texte en hauteur(coin en bas à gauche)
		int yMinUI;//Hauteur du rectangle noir sous le menu

		chaineHeight=context.getFont().getHeight("AZETYUIOPMLKJHGFDSQWXCVBN");
		posY=world.getHeight()/50+20-chaineHeight/2;

		context.setColor(Color.black);
		yMinUI=world.getHeight()/50+40;
		context.fillRect(0, 0,world.getWidth(),yMinUI);


		for(Map.Entry<String , Resource> resource: resources.entrySet()){

			if(resource.getKey()!="Cailloux") {
				nbRes++;
				str=resource.getKey()+" : 10000";//+(int)(resource.getValue().getQuantite());
				chaineWidth+=context.getFont().getWidth(str);//On ajoute la longeuur de la chaine de caractères
				chaineWidth+=40;//On ajoute la largeur d'une image.
			}
		}
		esp=(int)((world.getWidth()-chaineWidth)/(nbRes-1));//Espace entre deux ressources
		context.setColor(Color.white);
		chaineWidth=0;
		for(Map.Entry<String , Resource> resource: resources.entrySet()){
			if(resource.getKey()!="Cailloux") {
				str=resource.getKey()+" : 10000";
				str2=resource.getKey()+" : "+(int)(resource.getValue().getQuantite());
				context.drawImage(resource.getValue().getImageOnUI().getScaledCopy(40,40),chaineWidth,world.getHeight()/50);//On affiche l'image
				chaineWidth+=40;//On ajoute la largeur d'une image.
				context.drawString(str2,chaineWidth,posY);
				chaineWidth+=context.getFont().getWidth(str);//On ajoute la longeuur de la chaine de caractères
				chaineWidth+=esp;

			}
		}

//		context.drawString("Autres Ressources...", world.getWidth()*size/(size+1), world.getHeight()/40);
//		if(world.getDispRessources()){
//			context.drawString(resourcesToString(),world.getWidth()*size/(size+1) , world.getHeight()/20);
//		}

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
			resources.put("Nourriture", new Resource("Nourriture"));
			resources.put("Fer",new Resource("Fer"));
			resources.put("Noyaux Linux", new Resource("Noyaux Linux"));
			resources.put("Noyaux Linux éduqués", new Resource("Noyaux Linux éduqués"));
			resources.put("Cailloux",new Resource("Cailloux"));


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

	public int getyMinUI() {
		return yMinUI;
	}
}
