package app.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

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
		setTextFont(AppLoader.loadFont("/fonts/vt323.ttf",java.awt.Font.BOLD,textSize));

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

		setUpperCaseLock(false);
	}

	//SLICK METHOD

	public void update(GameContainer container,StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		if(mouseEntered) hasFocus=true;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		super.render(container, game, g);

		if(mousePressed)g.setColor(textColorPressed);
		else if(mouseEntered)g.setColor(textColorEntered);
		else g.setColor(textColor);

		g.setFont(textFont);
		g.drawString(text, x+paddingLeft+(getWidth()-paddingLeft-paddingRight)/2-textFont.getWidth(text)/2, y+paddingTop+(getHeight()-paddingTop-paddingBottom)/2-textFont.getHeight(text)/2);

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

}
