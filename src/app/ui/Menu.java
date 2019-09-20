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
	private int padding, titleWidth, titleHeight;
	protected ArrayList<Item> items;
	
	public Menu(String title, int xpos, int ypos) {
		this.title = title;
		this.xpos = xpos;
		this.ypos = ypos;
		this.padding = 8;
		this.items = new ArrayList<Item>();
		this.updateSize();
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addItem(Item item) {
		items.add(item);
		updateSize();
		setPos(xpos, ypos);
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
		this.xsize = 2*padding+titleWidth;
		this.ysize = 2*padding+titleHeight;
		// On détermine la taille horizontale maximale parmi tous les items
		for (MenuItem item : items) {
			if (item.getWidth() > this.xsize) this.xsize = item.getWidth();
			this.ysize += item.getHeight();
		}
		// Cette taille maximale est définie comme étant la taille du menu
		// On ajuste en conséquence la taille de tous les items
		for (MenuItem item : items) item.setWidth(this.xsize);
	}
	
	public void setPos(int x, int y) {
		this.xpos = x;
		this.ypos = y;
		this.updateSize();
		int curY = 2*padding+titleHeight;
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setXPos(this.xpos);
			items.get(i).setYPos(this.ypos+curY);
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
	
	public void update(GameContainer container, StateBasedGame game, int delta) {
		for (Item item : items) {
			item.updateHighlight(container.getHeight());
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		for (Item item : items) {
			item.render(container, game, context);
		}
		int theight = context.getFont().getHeight(title);
		int twidth = context.getFont().getWidth(title);
		if (this.titleWidth != twidth || this.titleHeight != theight) {
			this.titleWidth = twidth;
			this.titleHeight = theight;
			this.updateSize();
		}
		context.setColor(Color.white);
		context.fillRect(xpos, ypos, xsize, 2*padding+titleHeight);
		context.setColor(Color.black);
		context.drawString(title, xpos+padding, ypos+padding);
		context.setColor(Color.green);
		context.setLineWidth(1);
		context.drawLine(xpos, ypos, xpos+xsize, ypos);
		context.drawLine(xpos, ypos, xpos, ypos+ysize);
		context.drawLine(xpos+xsize, ypos, xpos+xsize, ypos+ysize);
		context.drawLine(xpos, ypos+ysize, xpos+xsize, ypos+ysize);
	}
}
