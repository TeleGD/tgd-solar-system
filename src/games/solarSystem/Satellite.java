package games.solarSystem;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public class Satellite extends Orbital{

	public Satellite(int lifeMax, int cout,float angle, int size, int distance, Resource resource,int resourceQuantity,World world,Air air) {
		super(lifeMax, cout, angle, size, distance,world,air);
		this.tile=new Case(0,0,sizeCase,resource,resourceQuantity,this);
		tile.setX((int)(distance*Math.cos(angle)+size-this.sizeCase/2));
		tile.setY((int)(distance*Math.sin(angle)+size-this.sizeCase/2));

	}

	public void render (GameContainer container, StateBasedGame game, Graphics context){
		context.setColor(Color.blue);
		String nomImage = "/images/solarSystem/planets/satellite/0.png";
		//String nomImage = "/images/solarSystem/planets/satellite/"+rnd.nextInt(6)+".png";
		//this.nomImage = "/images/solarSystem/planets/"+rnd.nextInt(6)+".png";
		Image image = AppLoader.loadPicture(nomImage).copy();
		//context.fillOval(container.getWidth()/2+get_x()*1f-size, container.getHeight()/2-get_y()/2*1f-size, size * 2, size * 2);
		float posX = container.getWidth()/2+get_x()*1f-size;
		float posY = container.getHeight()/2-get_y()/2*1f-size;
		context.drawImage(image,posX,posY,posX+size*2,posY+size*2,0,0,image.getWidth(),image.getHeight());
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
