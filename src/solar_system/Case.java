package solar_system;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Case {
	private String resource;
	private int resourceQuantity;
	private Construction construction;
	
	public Case(){
		//Use the matrice indexation and the screen resolution to calculte the position of the case
		resource = null;
		resourceQuantity = 0;
		construction = null;
	}
	
	public Case(String resource, int resourceQuantity){
		//Use the matrice indexation and the screen resolution to calculte the position of the case
		this.resource = resource;
		this.resourceQuantity = resourceQuantity;
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
	
	public void render (GameContainer container, StateBasedGame game, Graphics context, float x, float y, float width, float height) {
		if(resource != null){
			context.setColor(new Color(255, 0, 0, 127));
		}
		else{
			context.setColor(new Color(0, 0, 255, 127));
		}
		context.fillRect((int)x, (int)y, (int)width, (int)height);
	}
}
