package solar_system;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Station extends Orbital {
	
	public Station(int lifeMax, int cout,float angle, int size, int distance, Resource resource,World world) {
		super(lifeMax, cout, angle, size, distance, resource,world);
	}
	
	
	
	
	public void render (GameContainer container, StateBasedGame game, Graphics context){
//		context.setColor(Color.red);
//		context.fillOval(get_x()*1f-size, get_y()*1f-size, size * 2, size * 2);
//		//System.out.println("Je suis une station de position "+get_x()+","+get_y());
//		super.render(container, game, context);
		context.setColor(Color.red);
		context.fillOval(container.getWidth()/2+get_x()*1f-size, container.getHeight()/2-get_y()/2*1f-size, size * 2, size * 2);
		super.render(container, game, context);
	}
	//@Override
//	public void update(GameContainer container, StateBasedGame game, int delta) {
//		// TODO Auto-generated method stub
//		
//	}
}
