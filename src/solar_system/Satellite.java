package solar_system;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Satellite extends Orbital{
	
	public Satellite(int lifeMax, int cout,int posX,int posY, int size, int distance, Resource resource,World world) {
		super(lifeMax, cout, posX, posY, size, distance, resource,world);
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context, boolean arrierePlan){
		if ( (arrierePlan && Math.sin(angle)>0)  ||   (!(arrierePlan) && !(Math.sin(angle)>0)) ) {
			context.setColor(Color.blue);
			context.fillOval(get_x()*1f-size, get_y()*1f-size, size * 2, size * 2);
			super.render(container, game, context, arrierePlan);
			//System.out.println("Je suis un satellite de position "+get_x()+","+get_y());
		}
	}
	/*
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta, int orbitalSize , int i) {
		super.update(container, game, delta, orbitalSize, i);
	}
	*/
}
