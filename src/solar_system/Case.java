package solar_system;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import solar_system.constructions.Mine;

public class Case {
	private Resource resource;
	private double resourceQuantity;
	private Construction construction;
	private int x, y, size;
	private Image backgroundImg;
	
	public Case(int x, int y, int size){
		this.x = x;
		this.y = y;
		this.size = size;
		resource = null;
		resourceQuantity = 0;
		construction = null;
	}

	
	public Case(int x, int y, int size, Resource resource, int resourceQuantity){
		this.x = x;
		this.y = y;
		this.size = size;
		this.resource = resource;
		this.resourceQuantity = (double)resourceQuantity;
		construction = null;
		
		if (this.resource==null){
			this.backgroundImg = null;
		} else {
			try{
				this.backgroundImg = new Image(resource.imagePath(resource.getName()));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setConstruction(Construction construction){
		if(this.construction == null){
			this.construction = construction;
		}
		else{
			System.out.println("Case déjà ocupé");
		}
	}
	
	public void render (GameContainer container, StateBasedGame game, Graphics context) {

		context.setColor(new Color(0, 0, 0, 127));
		context.drawRect(x, y, size, size);
		if(backgroundImg != null){
			context.drawImage(backgroundImg.getScaledCopy(size,size),x,y);
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
	
	public ArrayList<String> infoConstruct(Case tile){
		//Retourne les constructions faisables sur la case cliquée
		
		ArrayList<String> construPossible = new ArrayList<>();
		
		//Pour chaque nouvelle construction, ajouter un if comme ci-dessous
		if(Mine.constructPossible(tile)){
			construPossible.add("Mine");
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
}
