package solar_system;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Resource {
	
	private String name;
	private double quantite;
	private Image image;
	
	public Resource(String name) {
		this.name=name;
		this.quantite=0;
		
		if (name == "Bois") {
			quantite = 100;
		}
		
		try {
			image = new Image(imagePath(name));
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}
	
	public boolean modifQuantite (double qtite) {  
		/* Ajoute ou prélève (si qtite<0)
			Renvoie true si l'opération a réussi,
			Renvoie false si l'opération est interrompue
				(prélèvement supérieur à la quantite disponible).
		*/
		if (qtite+quantite<0){
			return false;
		}
		quantite+=qtite;
		return true;
	}
	
	public String getName() {
		return name;
	}
	
	public double getQuantite () {
		return quantite;
	}
	
	public Image getImage () {
		return image;
	}
	
	public void render() {
		//TODO wtf non !
	}
	
	public String imagePath(String name) {
		String path = null;
		switch (name) {
			case "Fer": path = "res/images/resources/fer_minerai.png";
				break;
			case "Bois": path = "res/images/resources/Bois.png";
				break;
			case "Cailloux": path = "res/images/resources/cailloux.png";
				break;
			case "Nourriture": path = "res/images/resources/Nourriture.png";
			break;
			case "Noyau Linux": path = "res/images/resources/noyo.png";
			break;
			default : path = "res/images/resources/cailloux.png";
			//case "Noyaux Linux": path = "res/resources/";
		}
		return path;
	}
}
