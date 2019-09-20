package app.ui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public class MenuItem {
	private int xpos, ypos, xsize, ysize, padding;
	private int imgWidth, imgHeight, textWidth, textHeight;
	private boolean highlighted;
	private Image img;
	private Image bkg, bkgHigh;
	private String text;
	
	public MenuItem(Image img, String text, int padding, int xpos, int ypos) {
		this.setImage(img);
		this.text = text;
		this.padding = padding;
		this.xpos = xpos;
		this.ypos = ypos;
		this.updateSize();
		//this.bkg = AppLoader.loadPicture("/images/menu_item.png");
		//this.bkgHigh = AppLoader.loadPicture("/images/menu_item_highlighted.png");
	}
	
	private void updateSize() {
		xsize = 3*padding;
		ysize = 2*padding;
		
		if (this.img != null) {
			xsize += this.imgWidth;
			if (this.imgHeight >= this.textHeight) ysize += this.imgHeight;
		}
		if (this.text != null) {
			xsize += textWidth;
			if (this.textHeight > this.imgHeight) ysize += this.textHeight;
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
	
	public void updateHighlight(int worldHeight) {
		this.highlighted = this.isPressed(Mouse.getX(), worldHeight-Mouse.getY());
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
		if (img != null) {
			this.imgWidth = img.getWidth();
			this.imgHeight = img.getHeight();
		} else {
			this.imgWidth = 0;
			this.imgHeight = 0;
		}
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		if (text != null) {
			int newTextWidth = context.getFont().getWidth(text);
			this.textHeight = context.getFont().getHeight(text);
			if (this.textWidth != newTextWidth) {
				this.textWidth = newTextWidth;
				this.updateSize();
			}
		}
		if (this.highlighted) {
			if (this.bkgHigh != null) context.drawImage(bkgHigh, xpos, ypos, xpos+xsize, ypos+ysize, 0, 0, bkgHigh.getWidth(), bkgHigh.getHeight());
			else {
				context.setColor(Color.decode("#0099ff"));
				context.fillRect(xpos, ypos, xsize, ysize);
			}
		}
		else {
			if (this.bkg != null) context.drawImage(bkg, xpos, ypos, xpos+xsize, ypos+ysize, 0, 0, bkg.getWidth()-1, bkg.getHeight()-1);
			else {
				context.setColor(Color.blue);
				context.fillRect(xpos, ypos, xsize, ysize);
			}
		}
		context.setColor(Color.white);
		context.setLineWidth(1);
		context.drawLine(xpos, ypos, xpos+xsize, ypos);
		context.drawLine(xpos, ypos, xpos, ypos+ysize);
		context.drawLine(xpos+xsize, ypos, xpos+xsize, ypos+ysize);
		context.drawLine(xpos, ypos+ysize, xpos+xsize, ypos+ysize);
		if (img != null) context.drawImage(img, xpos+padding, ypos+padding);
		if (text != null) context.drawString(text, xpos+imgWidth+2*padding, ypos+(ysize-textHeight)/2);
	}
}
