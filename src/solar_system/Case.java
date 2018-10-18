package solar_system;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Case {
	private String resource;
	private double resourceQuantity;
	private Construction construction;
	private int x, y, size;
	
	public Case(){
		//Use the matrice indexation and the screen resolution to calculte the position of the case
		resource = null;
		resourceQuantity = 0;
		construction = null;
	}
	
	public Case(String resource, int resourceQuantity){
		//Use the matrice indexation and the screen resolution to calculte the position of the case
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
			context.setColor(new Color(255, 0, 0, 127));
		}
		else{
			context.setColor(new Color(0, 0, 255, 127));
		}
		context.fillRect(x, y, size, size);
	}
	

	public void preleveResource(double quantiteAPrelever) {
		resourceQuantity=resourceQuantity-quantiteAPrelever;
	}
	public double getResourceQuantity() {
		return resourceQuantity;
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
