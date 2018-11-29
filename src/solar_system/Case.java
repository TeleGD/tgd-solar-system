package solar_system;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

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
		
		if(resource != null){
			context.setColor(new Color(0, 0, 0, 127));
		}
		else{
			context.setColor(new Color(0, 0, 0, 127));
		}
		context.drawRect(x, y, size, size);
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
	
	public double getResourceQuantity() {
		return resourceQuantity;
	}
	
	public Resource getResource(){
		return resource;
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
