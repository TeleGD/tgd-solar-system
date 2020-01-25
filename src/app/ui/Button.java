package app.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import app.AppFont;
import app.AppLoader;

@SuppressWarnings("serial")
public class Button extends TGDComponent{

	private String text;

	private Color textColor;
	private Color textColorEntered;
	private Color textColorPressed;

	private Font textFont;
	private int textSize;

	private boolean upperCaseLock;
	private boolean selectable;
	private boolean selected;
	private Color backgroundColorSelected;

	public Button(GameContainer container,float x,float y,float width,float height){
		super(container,x,y,width,height);
	}

	public Button(String titre, GameContainer container,float x,float y,float width,float height) {
		this(container,x,y,width,height);
		setText(titre);
	}

	@Override
	protected void initDefaultUI() {
		super.initDefaultUI();

		setText("");
		setTextSize(14);
		setTextColor(new Color(255,255,255));
		setTextColorPressed(new Color(255,255,255));
		setTextColorEntered(new Color(0,0,0));
		setTextFont(AppLoader.loadFont("/fonts/vt323.ttf",AppFont.BOLD,textSize));

		setPaddingTop(5);
		setPaddingBottom(5);
		setPaddingLeft(10);
		setPaddingRight(10);

		setBorderWidth(1);
		setBorderColor(Color.white);
		setCornerRadius(0);

		setBackgroundColor(new Color(255,255,255,0));
		setBackgroundColorEntered(new Color(255,255,255));
		setBackgroundColorPressed(new Color(125,5,5));
		setBackgroundColorSelected(getBackgroundColorPressed());

		setUpperCaseLock(false);
		setSelectable(false);
	}

	//SLICK METHOD

	public void update(GameContainer container,StateBasedGame game, int delta) {
		super.update(container, game, delta);
		if(mouseEntered) hasFocus=true;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		super.render(container, game, g);
		if (!visible) {
			return;
		}

		if(mousePressed)g.setColor(textColorPressed);
		else if(mouseEntered)g.setColor(textColorEntered);
		else g.setColor(textColor);

		g.setFont(textFont);
		g.drawString(text, x+paddingLeft+(getWidth()-paddingLeft-paddingRight)/2-textFont.getWidth(text)/2, y+paddingTop+(getHeight()-paddingTop-paddingBottom)/2-textFont.getHeight(text)/2);
		g.resetFont();

	}

	//GETTERS AND SETTERS

	@Override
	public float getAutomaticWidth(){
		return textFont.getWidth(text)+paddingLeft+paddingRight;
	}
	@Override
	public float getAutomaticHeight(){
		return textFont.getHeight(text)+paddingTop+paddingBottom;
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public Font getTextFont() {
		return textFont;
	}

	public void setTextFont(Font textFont) {
		this.textFont = textFont;
	}

	@Override
	public void mouseClicked(int type, int x, int y, int count) {
		super.mouseClicked(type, x, y, count);
		if (System.currentTimeMillis() - time > 300) {
			hasFocus = false;
		}
		if (contains(x, y) && hasFocus && visible) {
			if (selectable) {
				selected = !selected;
				if (selected) {
					setBackgroundColor(getBackgroundColorSelected());
				} else {
					setBackgroundColor(getBackgroundColorSelected());
				}
			}
		}
	}

	@Override
	public void mouseMoved(int ox, int oy, int x, int y) {
		super.mouseMoved(ox, oy, x, y);
		if (contains(x,y) && visible) {
			hasFocus = true;
		}
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public boolean isUpperCaseLock() {
		return upperCaseLock;
	}

	public void setUpperCaseLock(boolean upperCaseLock) {
		this.upperCaseLock = upperCaseLock;
	}

	public Color getTextColorEntered() {
		return textColorEntered;
	}

	public void setTextColorEntered(Color textColorEntered) {
		this.textColorEntered = textColorEntered;
	}

	public Color getTextColorPressed() {
		return textColorPressed;
	}

	public void setTextColorPressed(Color textColorPressed) {
		this.textColorPressed = textColorPressed;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public Color getBackgroundColorSelected() {
		return backgroundColorSelected;
	}

	public void setBackgroundColorSelected(Color backgroundColorSelected) {
		this.backgroundColorSelected = backgroundColorSelected;
	}

}
