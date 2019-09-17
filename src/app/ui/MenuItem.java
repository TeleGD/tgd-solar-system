package app.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public class MenuItem {
	private int xpos, ypos, xsize, ysize, padding;
	private int imgWidth, textWidth;
	private Image img;
	private String text;
	
	public MenuItem(Image img, String text, int padding, int xpos, int ypos) {
		this.img = img;
		this.text = text;
		this.padding = padding;
		this.xpos = xpos;
		this.ypos = ypos;
		this.xsize = 3*padding;
		this.ysize = 2*padding;
		
		if (this.img != null) {
			this.xsize += this.img.getWidth();
			this.ysize += this.img.getHeight();
			this.imgWidth = this.img.getWidth();
		}
		if (this.text != null) {
			this.textWidth = 100;
			this.xsize += textWidth;
		}
	}
	
	public int getWidth() {
		return this.xsize;
	}
	
	public int getHeight() {
		return this.ysize;
	}
	
	public int getXPos() {
		return this.xpos;
	}
	
	public int getYPos() {
		return this.ypos;
	}
	
	public Image getImage() {
		return this.img;
	}
	
	public String getText() {
		return this.text;
	}
	
	public boolean isPressed(int xmouse, int ymouse) {
		return (xpos <= xmouse && xmouse <= xpos+xsize && ypos <= ymouse && ymouse <= ypos+ysize);
	}
	
	public void setWidth(int width) {
		this.xsize = width;
	}
	
	public void setHeight(int height) {
		this.ysize = height;
	}
	
	public void setXPos(int x) {
		this.xpos = x;
	}
	
	public void setYPos(int y) {
		this.ypos = y;
	}
	
	public void setImage(Image img) {
		this.img = img;
		this.imgWidth = img.getWidth();
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor(Color.blue);
		context.fillRect(xpos, ypos, xsize, ysize);
		context.setColor(Color.white);
		context.setLineWidth(1);
		context.drawLine(xpos, ypos, xpos+xsize, ypos);
		context.drawLine(xpos, ypos, xpos, ypos+ysize);
		context.drawLine(xpos+xsize, ypos, xpos+xsize, ypos+ysize);
		context.drawLine(xpos, ypos+ysize, xpos+xsize, ypos+ysize);
		if (img != null) context.drawImage(img, xpos+padding, ypos+padding);
		if (text != null) context.drawString(text, xpos+imgWidth+2*padding, padding);
	}
}
