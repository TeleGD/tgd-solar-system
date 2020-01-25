package app.ui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import app.AppFont;
import app.AppLoader;

/**
 * TextField Personnalisable a gogo
 * Constructeur: le gameContainer, la posittion x,y et la taille width height
 *
 * personnalisable graphisme à gogo via les setters:
 * - couleur/taille de bordure
 * - marges entre les bordures (paddingLeft,paddingTop, ...)s
 * - placeHolder (textSize, textFont, textColor)
 * - text (placeHolderTextSize, placeHolderTextFont, placeHolderTextColor)
 * - curseur (enabled, color, width)
 * - bordure (color, width)
 *
 * autres points de personnalisations:
 * - upperCaseLock: Forcer la casse
 * - addUnauthorizeKey (Ajouter des key input, que tu ne veux pas qui soit détecté)
 *
 */
@SuppressWarnings("serial")
public class TextField extends TGDComponent{

	private String text;
	private String placeHolder;

	private Color placeHolderColor;
	private Color textColor;
	private Color cursorColor;

	private Font textFont;
	private Font placeHolderFont;

	private int textSize;
	private int placeHolderTextSize;

	private float cursorWidth;

	private boolean cursorEnabled;
	private boolean upperCaseLock;
	private int maxNumberOfLetter;

	private ArrayList<Integer> unauthorizedKeys;

	private EnterActionListener listener;
	private boolean onlyFigures;
	private boolean overflowMode;

	public TextField(GameContainer container,float x,float y,float width,float height){
		super(container,x,y,width,height);

		unauthorizedKeys=new ArrayList<Integer>();
		unauthorizedKeys.add(Input.KEY_RIGHT);
		unauthorizedKeys.add(Input.KEY_LEFT);
		unauthorizedKeys.add(Input.KEY_UP);
		unauthorizedKeys.add(Input.KEY_DOWN);
		unauthorizedKeys.add(Input.KEY_ENTER);

	}

	@Override
	protected void initDefaultUI() {
		super.initDefaultUI();

		setPlaceHolder("Entrez votre texte...");
		setPlaceHolderTextSize(15);
		setPlaceHolderColor(new Color(140, 140, 140));
		setPlaceHolderFont(AppLoader.loadFont("/fonts/vt323.ttf", AppFont.PLAIN, placeHolderTextSize));

		setText("");
		setTextSize(15);
		setTextColor(new Color(255, 255, 255));
		setTextFont(AppLoader.loadFont("/fonts/vt323.ttf", AppFont.BOLD, textSize));

		setPaddingLeft(10);
		setPaddingRight(10);
		setPaddingTop(7);
		setPaddingBottom(7);

		setCursorEnabled(true);
		setCursorColor(new Color(200, 5, 5));
		setCursorWidth(2);

		setBorderWidth(1);
		setBorderColor(Color.white);

		setCornerRadius(0);
		setBackgroundColor(new Color(255, 255, 255, 0));
		setBackgroundColorFocused(null);

		setMaxNumberOfLetter(-1);
		setOnlyFigures(false);
		setUpperCaseLock(false);
	}

	//SLICK METHOD
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		super.render(container, game, g);
		if(text.length()>0){

			g.setColor(textColor);
			g.setFont(textFont);
			g.drawString(text, x+paddingLeft, y+paddingTop);

		}else {
			g.setColor(placeHolderColor);
			g.setFont(placeHolderFont);
			g.drawString(placeHolder, x+paddingLeft, y+paddingTop);

		}

		if(cursorEnabled && (System.currentTimeMillis()-timeInit)%800<400 && hasFocus){
			g.setColor(cursorColor);
			g.fillRect(x+paddingLeft+Math.min(textFont.getWidth(text),getWidth()-paddingRight-paddingLeft), y+paddingTop,cursorWidth,getHeight()-paddingBottom-paddingTop);
		}

		g.resetFont();
	}

	@Override
	public float getAutomaticWidth(){
		return Math.max(textFont.getWidth(text),placeHolderFont.getWidth(placeHolder))+paddingLeft+paddingRight;
	}

	@Override
	public float getAutomaticHeight(){
		return Math.max(textFont.getHeight(text),placeHolderFont.getHeight(placeHolder))+paddingTop+paddingBottom;
	}

	//GETTERS AND SETTERS
	public String getPlaceHolder() {
		return placeHolder;
	}

	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Color getPlaceHolderColor() {
		return placeHolderColor;
	}

	public void setPlaceHolderColor(Color placeHolderColor) {
		this.placeHolderColor = placeHolderColor;
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

	public Font getPlaceHolderFont() {
		return placeHolderFont;
	}

	public void setPlaceHolderFont(Font placeHolderFont) {
		this.placeHolderFont = placeHolderFont;
	}

	public int getPlaceHolderTextSize() {
		return placeHolderTextSize;
	}

	public void setPlaceHolderTextSize(int placeHolderTextSize) {
		this.placeHolderTextSize = placeHolderTextSize;
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	private void setCursorColor(Color color) {
		this.cursorColor=color;
	}

	private void setCursorWidth(float width){
		this.cursorWidth=width;
	}

	public int getCornerRadius() {
		return cornerRadius;
	}

	public void setCornerRadius(int cornerRadius) {
		this.cornerRadius = cornerRadius;
	}

	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		if(!hasFocus)return;

		if(key==Input.KEY_BACK){
			if(text.length()>0)text=text.substring(0, text.length()-1);
		}else if(key==Input.KEY_ENTER)
		{
			//valider
			if(listener!=null){
				listener.onEnterPressed();
			}
			hasFocus=false;
		}
		else if(!unauthorizedKeys.contains(key) && ((int)c)!=0 && (overflowMode || text.length()<maxNumberOfLetter || maxNumberOfLetter==-1) &&  (c+"").length()>0){

			if(key == Input.KEY_0) text += "0";
			else if(key == Input.KEY_1 || key == Input.KEY_NUMPAD1) text += "1";
			else if(key == Input.KEY_2 || key == Input.KEY_NUMPAD2) text += "2";
			else if(key == Input.KEY_3 || key == Input.KEY_NUMPAD3) text += "3";
			else if(key == Input.KEY_4 || key == Input.KEY_NUMPAD4) text += "4";
			else if(key == Input.KEY_5 || key == Input.KEY_NUMPAD5) text += "5";
			else if(key == Input.KEY_6 || key == Input.KEY_NUMPAD6) text += "6";
			else if(key == Input.KEY_7 || key == Input.KEY_NUMPAD7) text += "7";
			else if(key == Input.KEY_8 || key == Input.KEY_NUMPAD8) text += "8";
			else if(key == Input.KEY_9 || key == Input.KEY_NUMPAD9) text += "9";
			else if(c ==(char)0) text += "0";
			else if(c ==(char)1) text += "1";
			else if(c ==(char)2) text += "2";
			else if(c ==(char)3) text += "3";
			else if(c ==(char)4) text += "4";
			else if(c ==(char)5) text += "5";
			else if(c ==(char)6) text += "6";
			else if(c ==(char)7) text += "7";
			else if(c ==(char)8) text += "8";
			else if(c ==(char)9) text += "9";
			else{
				if(!onlyFigures)text+=c;
			}
			if(overflowMode && text.length()>maxNumberOfLetter && maxNumberOfLetter!=-1)
			{
				text = text.substring(text.length()-maxNumberOfLetter);
			}

			if(upperCaseLock)text=text.toUpperCase();
		}
	}

	@Override
	public void mouseClicked(int type, int x, int y, int count) {
		if(!contains(x,y)){
			hasFocus=false;
		}else{
			hasFocus=true;
		}

		super.mouseClicked(type, x, y, count);
	}

	@Override
	public void keyReleased(int key, char c) {
		super.keyReleased(key, c);
	}

	public boolean isUpperCaseLock() {
		return upperCaseLock;
	}

	public void setUpperCaseLock(boolean upperCaseLock) {
		this.upperCaseLock = upperCaseLock;
	}

	public void addUnauthorizedKey(int key){
		unauthorizedKeys.add(key);
	}

	public void removeUnauthorizedKey(int key){
		unauthorizedKeys.remove((Integer)key);
	}

	public boolean isCursorEnabled() {
		return cursorEnabled;
	}

	public void setCursorEnabled(boolean cursorEnabled) {
		this.cursorEnabled = cursorEnabled;
	}

	public void setEnterActionListener(EnterActionListener listener){
		this.listener=listener;
	}

	public int getMaxNumberOfLetter() {
		return maxNumberOfLetter;
	}

	public void setMaxNumberOfLetter(int maxNumberOfLetter) {
		this.maxNumberOfLetter = maxNumberOfLetter;
	}

	public void setOnlyFigures(boolean onlyFigures) {
		this.onlyFigures = onlyFigures;
	}

	public void setOverflowMode(boolean overflowMode) {
		this.overflowMode = overflowMode;
	}

	public interface EnterActionListener{
		void onEnterPressed();
	}

}
