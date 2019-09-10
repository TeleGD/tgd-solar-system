package solar_system;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import app.AppLoader;

public class Resource {

	private String name;
	private double quantite;
	private Image image;

	public Resource(String name) {
		this.name=name;
		this.quantite=0;

		if (name == "Bois") {
			quantite = 100;
			image = AppLoader.loadPicture("/images/resources/foret.png");
		}
		else {
			image = AppLoader.loadPicture(imagePath(name));
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
	
	public void setAlpha(float alpha) {
		this.image.setAlpha(alpha);
	}

	public void render() {
		//TODO wtf non !
	}

	public static String imagePath(String name) {
		String path = null;
		switch (name) {
			case "Fer": path = "/images/resources/fer_minerai.png";
				break;
			case "Bois": path = "/images/resources/Bois.png";
				break;
			case "Cailloux": path = "/images/resources/cailloux.png";
				break;
			case "Nourriture": path = "/images/resources/Nourriture.png";
			break;
			case "Noyau Linux": path = "/images/resources/noyo.png";
			break;
			default : path = "/images/resources/cailloux.png";
			//case "Noyaux Linux": path = "/resources/";
		}
		return path;
	}
}
