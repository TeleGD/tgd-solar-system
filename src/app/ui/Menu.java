package app.ui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Menu<Item extends MenuItem> {
	private String title;
	private int xpos, ypos;
	private int xsize, ysize;
	protected ArrayList<Item> items;
	
	public Menu(String title, int xpos, int ypos) {
		this.title = title;
		this.xpos = xpos;
		this.ypos = ypos;
		this.items = new ArrayList<Item>();
		this.updateSize();
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addItem(Item item) {
		items.add(item);
		updateSize();
	}
	
	public void removeItem(int index) {
		items.remove(index);
		updateSize();
	}
	
	public void reset() {
		items.clear();
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public Item getItem(int index) {
		return this.items.get(index);
	}
	
	private void updateSize() {
		int max = 240;
		this.ysize = 32;
		for (MenuItem item : items) {
			if (item.getWidth() > max) max = item.getWidth();
			this.ysize += item.getHeight();
		}
		this.xsize = max;
	}
	
	public void setPos(int x, int y) {
		this.xpos = x;
		this.ypos = y;
		int curY = 32;
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setXPos(this.xpos);
			items.get(i).setYPos(curY);
			curY += items.get(i).getHeight();
		}
	}
	
	public int length() {
		return items.size();
	}
	
	public int getWidth() {
		return this.xsize;
	}
	
	public int getHeight() {
		return this.ysize;
	}
	
	// Renvoie vrai si le menu est cliqué (dont la barre de titre)
	public boolean isPressed(int xmouse, int ymouse) {
		return (xpos <= xmouse && xmouse <= xpos+xsize && ypos <= ymouse && ymouse <= ypos+ysize);
	}
	
	// Renvoie l'index de l'item cliqué (-1 si aucun item n'est cliqué)
	public int getPressedItem(int xmouse, int ymouse) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).isPressed(xmouse, ymouse)) return i;
		}
		return -1;
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		for (Item item : items) {
			item.render(container, game, context);
		}
		context.setColor(Color.white);
		context.fillRect(xpos, ypos, xsize, 32);
		context.setColor(Color.black);
		context.drawString(title, xpos+10, ypos+10);
		context.setColor(Color.green);
		context.setLineWidth(1);
		context.drawLine(xpos, ypos, xpos+xsize, ypos);
		context.drawLine(xpos, ypos, xpos, ypos+ysize);
		context.drawLine(xpos+xsize, ypos, xpos+xsize, ypos+ysize);
		context.drawLine(xpos, ypos+ysize, xpos+xsize, ypos+ysize);
	}
}
