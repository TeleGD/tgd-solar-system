package app.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public class ButtonV2 {
	private int xpos, ypos, xsize, ysize;
	private Image img;
	public ButtonV2(Image img, int xpos, int ypos, int xsize, int ysize) {
		this.img   = img;
		this.xpos  = xpos;
		this.ypos  = ypos;
		this.xsize = xsize;
		this.ysize = ysize;
	}
	
	public boolean isPressed(int xmouse, int ymouse) {
		return (xpos <= xmouse && xmouse <= xpos+xsize && ypos <= ymouse && ymouse <= ypos+ysize);
	}
	
	public void moveY(int dY) {
		this.ypos += dY;
	}
	
	public void setAlpha(float alpha) {
		this.img.setAlpha(alpha);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		context.drawImage(img, xpos, ypos, xpos+xsize, ypos+ysize, 0, 0, img.getWidth(), img.getHeight());
	}

	public int getYpos() {
		return ypos;
	}
}
