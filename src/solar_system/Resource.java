package solar_system;

import java.util.concurrent.ThreadLocalRandom;

import org.newdawn.slick.Image;

import app.AppLoader;

public class Resource {

	private String name;
	private double quantite;
	private Image imageOnPlanet;
	private Image imageOnUI;

	public Resource(String name) {
		this.name=name;
		this.quantite=0;

		if (name == "Bois") {
			quantite = 100;
			imageOnPlanet = AppLoader.loadPicture("/images/resources/foret.png");
		}
		else if (name == "Noyaux Linux") {
			quantite = 0;
			imageOnPlanet = AppLoader.loadPicture("/images/resources/noyo_raw.png");
			imageOnUI = AppLoader.loadPicture("/images/resources/noyo_raw.png");
		}
		else if (name == "Noyaux Linux éduqués") {
			quantite = 0;
			imageOnUI = AppLoader.loadPicture("/images/resources/noyo.png");
		}
		else {
			imageOnPlanet = AppLoader.loadPicture(imagePath(name));
		}
		imageOnUI = AppLoader.loadPicture(imagePath(name));
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

	public Image getImageOnPlanet() {
		return imageOnPlanet;
	}
	
	public void setAlpha(float alpha) {
		this.imageOnPlanet.setAlpha(alpha);
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
			case "Noyaux Linux": path = "/images/resources/noyo_raw.png";
			break;
			case "Noyaux Linux éduqués" : path = "/images/resources/noyo.png";
			break;
			default : path = "/images/resources/cailloux.png";
			//case "Noyaux Linux": path = "/resources/";
		}
		return path;
	}
	
	//TODO : Equilibrage (Ressources par cases)
	public int resourceQuantity(boolean satellite) {
		double min=0;
		double fact=0;
		switch (this.name) {
			case "Fer" :
				min=300;
				fact=100;
				break;
				//return 500;
			case "Bois" :
				min=800;
				fact=80;
				break;
				//return 1500;
			case "Cailloux" :
				min=0;
				fact=0;
				break;
				//return 0;
			case "Nourriture" :
				min=1000;
				fact=200;
				break;
				//return 2000;
			case "Noyaux Linux" :
				min=40;
				fact=5;
				break;
				//return 10;
			default :
				//return 0;
		}
		if (satellite) {
			min = min*0.2;
			fact *= 2.5;
		}
		return (int)(min+fact*ThreadLocalRandom.current().nextInt(0, 10));
	}

	public Image getImageOnUI() {
		return imageOnUI;
	}

	public boolean isEqual(Resource res) {
		// TODO Auto-generated method stub
		if(this.isEqual(res)) {
			return true;
		}
		return false;
	}
}
