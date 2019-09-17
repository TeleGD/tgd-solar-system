package solar_system;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Satellite extends Orbital{
	
	public Satellite(int lifeMax, int cout,float angle, int size, int distance, Resource resource,World world,Air air) {
		super(lifeMax, cout, angle, size, distance, resource,world,air);
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context){
		context.setColor(Color.blue);
		context.fillOval(container.getWidth()/2+get_x()*1f-size, container.getHeight()/2-get_y()/2*1f-size, size * 2, size * 2);
		super.render(container, game, context);
		//System.out.println("Je suis un satellite de position "+get_x()+","+get_y());
	}
	/*
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta, int orbitalSize , int i) {
		super.update(container, game, delta, orbitalSize, i);
	}
	*/
}
