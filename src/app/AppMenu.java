package app;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import app.elements.MenuItem;

public abstract class AppMenu extends AppPage {

	static private Font menuFont;

	static private int menuLineHeight;

	static {
		AppMenu.menuFont = AppLoader.loadFont("/fonts/vt323.ttf", AppFont.BOLD, 24);

		AppMenu.menuLineHeight = 30;
	}

	private List<MenuItem> menu;

	private boolean menuVisibility;

	protected int menuBoxWidth;
	protected int menuBoxHeight;
	protected int menuBoxX;
	protected int menuBoxY;

	private int menuWidth;
	private int menuHeight;
	private int menuX;
	private int menuY;

	private int menuScrollY;
	private int menuScrollHeight;
	private int itemHeight;
	private int selectedItem;

	private boolean menuBlink;
	private int menuBlinkPeriod;
	private int menuBlinkCountdown;

	public AppMenu(int ID) {
		super(ID);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {
		super.init(container, game);

		this.menuBoxX = this.contentX;
		this.menuBoxY = this.subtitleBoxY + this.subtitleBoxHeight + AppPage.gap;
		this.menuBoxWidth = this.contentWidth;
		this.menuBoxHeight = this.hintBoxY - this.menuBoxY - AppPage.gap;

		this.itemHeight = AppMenu.menuLineHeight;

		this.menuVisibility = true;

		this.menuBlink = false;
		this.menuBlinkPeriod = 1000;
		this.menuBlinkCountdown = 0;

		this.setMenu(new ArrayList<MenuItem>());
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.getInput().clearKeyPressedRecord();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update(container, game, delta);
		Input input = container.getInput();
		boolean BUTTON_A = input.isKeyPressed(Input.KEY_ENTER);
		boolean BUTTON_B = input.isKeyPressed(Input.KEY_ESCAPE);
		boolean KEY_UP = input.isKeyPressed(Input.KEY_UP);
		boolean KEY_DOWN = input.isKeyPressed(Input.KEY_DOWN);
		boolean BUTTON_UP = KEY_UP && !KEY_DOWN;
		boolean BUTTON_DOWN = KEY_DOWN && !KEY_UP;
		if (BUTTON_A) {
			int size = this.menu.size();
			if (size != 0) {
				this.menu.get(this.selectedItem).itemSelected();
			}
		}
		if (BUTTON_B) {
			int size = this.menu.size();
			if (size != 0) {
				this.menu.get(size - 1).itemSelected();
			}
		}
		if (BUTTON_UP) {
			if (this.selectedItem > 0) {
				this.selectedItem--;
				if (this.selectedItem == this.menuScrollY - 1) {
					this.menuScrollY--;
				}
			} else {
				this.selectedItem = menu.size() - 1;
				this.menuScrollY = menu.size() - this.menuScrollHeight;
			}
		}
		if (BUTTON_DOWN) {
			if (this.selectedItem < menu.size() - 1) {
				this.selectedItem++;
				if (this.selectedItem == this.menuScrollY + this.menuScrollHeight) {
					this.menuScrollY++;
				}
			} else {
				this.selectedItem = 0;
				this.menuScrollY = 0;
			}
		}
		if (this.menuBlink) {
			this.menuBlinkCountdown = (this.menuBlinkCountdown + this.menuBlinkPeriod - delta) % this.menuBlinkPeriod;
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		super.render(container, game, context);
		this.renderMenu(container, game, context);
	}

	private void renderMenu(GameContainer container, StateBasedGame game, Graphics context) {
		if (this.menuVisibility) {
			int dx = -35;
			context.setFont(AppMenu.menuFont);
			context.setColor(AppPage.foregroundColor);
			for (int i = this.menuScrollY, l = i + this.menuScrollHeight; i < l; i++) {
				int dy = this.itemHeight * (i - this.menuScrollY);
				context.drawString(this.menu.get(i).getContent(), this.menuX, this.menuY + dy);
				if (i == this.selectedItem) {
					boolean menuBlink = this.menuBlink && this.menuBlinkCountdown <= this.menuBlinkPeriod / 2;
					if (!menuBlink) {
						context.setColor(AppPage.highlightColor);
					}
					context.drawString(">>", this.menuX + dx, this.menuY + dy);
					context.drawString("<<", this.menuX + this.menuWidth - dx, this.menuY + dy);
					if (!menuBlink) {
						context.setColor(AppPage.foregroundColor);
					}
				}
			}
		}
	}

	public void setMenu(List<MenuItem> menu) {
		this.menu = new ArrayList<MenuItem>();
		this.menu.addAll(menu);
		this.selectedItem = 0;
		this.menuScrollY = 0;
		this.menuScrollHeight = Math.min(this.menuBoxHeight / this.itemHeight, this.menu.size());
		this.menuWidth = 0;
		for (MenuItem item: this.menu) {
			int width = AppMenu.menuFont.getWidth(item.getContent());
			if (width > this.menuWidth) {
				this.menuWidth = width;
			}
		}
		this.menuHeight = this.menuScrollHeight * this.itemHeight;
		this.menuX = this.menuBoxX + (this.menuBoxWidth - this.menuWidth) / 2;
		this.menuY = this.menuBoxY + (this.menuBoxHeight - this.menuHeight) / 2;
	}

	public List<MenuItem> getMenu() {
		List<MenuItem> menu = new ArrayList<MenuItem>();
		menu.addAll(this.menu);
		return menu;
	}

}
