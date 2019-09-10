package solar_system;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import solar_system.constructions.Mine;
import solar_system.constructions.Mine2;
import solar_system.constructions.TNCY;
import solar_system.constructions.Ferme;
import solar_system.constructions.Scierie;
import solar_system.constructions.CabaneBucheron;
import solar_system.constructions.ISS;

public class Case {
	private Resource resource;
	private double resourceQuantity;
	private Construction construction;
	private int x, y, size;
	private Image backgroundImg;
	private int imageConstructSize; // Hauteur des images des constructions, dans le menu des constructions
	private Orbital orbital;

	public Case(int x, int y, int size,Orbital orbital){
		this.x = x;
		this.y = y;
		this.size = size;
		resource = null;
		resourceQuantity = 0;
		construction = null;
		imageConstructSize = 150;
		this.orbital = orbital;
	}


	public Case(int x, int y, int size, Resource resource, int resourceQuantity){
		this.x = x;
		this.y = y;
		this.size = size;
		this.resource = resource;
		this.resourceQuantity = (double)resourceQuantity;
		construction = null;
		this.orbital = null;

		setBackgroundAsResource();

	}

	public void setBackgroundAsResource() { // Permet de placer en arrière-plan de la case l'image de la ressource de la case

		if (this.resource==null){
			this.backgroundImg = null;
		} else {
			// TODO: Utiliser le getImage de Resource
			if (this.resource.getName() == "Bois"){
				this.backgroundImg = AppLoader.loadPicture("/images/resources/foret.png");
			}
			else {
				this.backgroundImg = AppLoader.loadPicture(this.resource.imagePath(this.resource.getName()));
			}
		}
	}

	public void setConstruction(Construction constr){
		this.construction = constr;
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {

		context.setColor(new Color(0, 0, 0, 127));
		context.drawRect(x, y, size, size);
		if(construction != null) {
			if(construction instanceof Building) {
				Building building = (Building)construction;
				building.render(container, game, context, x, y);
			}
		}
		else if(backgroundImg != null){
			context.drawImage(backgroundImg.getScaledCopy(size-1,size-1),x+1,y+1);
		}
	}


	public void renderHighlighted (GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor(new Color(127, 0, 0, 127));
		context.drawRect(x, y, size, size);
		context.drawRect(x+1, y+1, size-2, size-2);
	}

	public void update (GameContainer container, StateBasedGame game, int delta) {
		if (construction != null) {
			construction.update(container, game, delta);
		}
	}

	public boolean mousePressed(int arg0,int x ,int y){
		if(x>this.x && x<this.x+size && y>this.y && y<this.y+size){
			return true;
		}
		return false;
	}


	public double preleveResource(double quantiteAPrelever) {
		if(quantiteAPrelever <= resourceQuantity){
			resourceQuantity = resourceQuantity-quantiteAPrelever;
			return quantiteAPrelever;
		} else {
			double resourcePreleved = resourceQuantity;
			resourceQuantity = 0;
			return resourcePreleved;
		}
	}

	public ArrayList<String> infoConstruct(){
		//Retourne les constructions faisables sur la case cliquée

		ArrayList<String> construPossible = new ArrayList<>();
		if(orbital == null || orbital instanceof Satellite ){
			if (construction==null) {
				if(Mine.constructPossible(this)) {
					construPossible.add("Mine");
				}
				if(Ferme.constructPossible(this)){
					construPossible.add("Ferme");
				}
				if(Scierie.constructPossible(this)){
					construPossible.add("Scierie");
				}
				if(CabaneBucheron.constructPossible(this)){
					construPossible.add("CabaneBucheron");
				}
				if(TNCY.constructPossible(this)){
					construPossible.add("TNCY");
				}
			} else {
				if (construction instanceof Mine) {
					construPossible.add("Mine2");
				}
			}

		}
		if (orbital instanceof Station){
			if(construction == null){
				construPossible.add("ISS");
			}
			else{
				if(construction instanceof ISS){
					construPossible.add("Colonisator");
					//TODO : Ajouter tous les vaisseaux de constructions.vaisseaux
					
				}
			}
		}
		return construPossible;

	}

	public void setBackground(Image img) {
		 backgroundImg = img;
	}

	public double getResourceQuantity() {
		return resourceQuantity;
	}

	public Resource getResource(){
		return resource;
	}

	public Construction getConstruction(){
		return construction;
	}

	public void setResource(Resource r){
		resource = r;
		setBackgroundAsResource();
	}

	public void setRessourceQuantity(double quantity){
		resourceQuantity=quantity;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSize() {
		return this.size;
	}

	public Orbital getOrbital(){
		return this.orbital;
	}
}
