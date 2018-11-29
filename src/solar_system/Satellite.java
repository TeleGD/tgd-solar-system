package solar_system;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Satellite extends Orbital{
	public Satellite(int lifeMax, int cout,int posX,int posY, double speed, double size) {
		super(lifeMax, cout, posX, posY, speed, size);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta, Orbital o, int orbitalSize, int i) {
		// TODO Auto-generated method stub
		super.update(container, game, delta, o, orbitalSize, i);
		
	}
}
