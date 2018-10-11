package app.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

@SuppressWarnings("serial")
public class TGDComponent extends Rectangle implements MouseListener, KeyListener{
	public static final float AUTOMATIC = -1;

	protected Color backgroundColor;
	protected Color backgroundColorEntered;
	protected Color backgroundColorPressed;
	protected Color borderColor;
	protected Color borderColorEntered;
	protected Color borderColorPressed;

	protected int borderWidth;
	protected int cornerRadius;
	protected float paddingTop;
	protected float paddingBottom;
	protected float paddingRight;
	protected float paddingLeft;

	protected boolean mousePressed;
	protected boolean mouseEntered;
	protected boolean hasFocus;

	private OnClickListener listener;

	protected long time;

	protected long timeInit;

	public TGDComponent(GameContainer container,float x, float y, float width, float height) {
		super(x, y, width, height);
		paddingTop=5;
		paddingBottom=5;
		paddingRight=5;
		paddingLeft=5;
		initDefaultUI();
		timeInit=System.currentTimeMillis();

		container.getInput().addMouseListener(this);
		container.getInput().addKeyListener(this);

	}

	protected void initDefaultUI() {
		setBackgroundColor(new Color(255,255,255));
		setBackgroundColorEntered(null);
		setBackgroundColorPressed(null);
		setBorderWidth(0);

		hasFocus=true;

	}

	public void update(GameContainer container,StateBasedGame game, int delta) throws SlickException{
		time=System.currentTimeMillis();
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setAntiAlias(true);
		g.resetLineWidth();

		time=System.currentTimeMillis();

		if(mousePressed && backgroundColorPressed!=null)g.setColor(backgroundColorPressed);
		else if(mouseEntered  && backgroundColorEntered!=null)g.setColor(backgroundColorEntered);
		else g.setColor(backgroundColor);

		g.fillRoundRect(x, y, getWidth(), getHeight(), cornerRadius);

		if(borderWidth>0){

			if(mousePressed && borderColorPressed!=null)g.setColor(borderColorPressed);
			else if(mouseEntered  && borderColorEntered!=null)g.setColor(borderColorEntered);
			else g.setColor(borderColor);
			g.setLineWidth(borderWidth);
			g.drawRoundRect(x, y, getWidth(), getHeight(), cornerRadius);

		}
		g.resetLineWidth();
	}

	protected float getAutomaticWidth() {
		return 100;
	}

	public float getAutomaticHeight() {
		return 30;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Color getBackgroundColorEntered() {
		return backgroundColorEntered;
	}

	public Color getBackgroundColorPressed() {
		return backgroundColorPressed;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setBackgroundColorEntered(Color backgroundColorEntered) {
		this.backgroundColorEntered = backgroundColorEntered;
	}

	public void setBackgroundColorPressed(Color backgroundColorPressed) {
		this.backgroundColorPressed = backgroundColorPressed;
	}

	public int getCornerRadius() {
		return cornerRadius;
	}

	public void setCornerRadius(int cornerRadius) {
		this.cornerRadius = cornerRadius;
	}

	public float getPaddingTop() {
		return paddingTop;
	}

	public void setPaddingTop(int paddingTop) {
		this.paddingTop = paddingTop;
	}

	public float getPaddingBottom() {
		return paddingBottom;
	}

	public void setPaddingBottom(int paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	public float getPaddingRight() {
		return paddingRight;
	}

	public void setPaddingRight(int paddingRight) {
		this.paddingRight = paddingRight;
	}

	public float getPaddingLeft() {
		return paddingLeft;
	}

	public void setPaddingLeft(int paddingLeft) {
		this.paddingLeft = paddingLeft;
	}
	public void setPadding(int paddingTop, int paddingRight, int paddingBottom, int paddingLeft) {
		setPaddingLeft(paddingLeft);
		setPaddingRight(paddingRight);
		setPaddingTop(paddingTop);
		setPaddingBottom(paddingBottom);

	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public void setBorderColorPressed(Color borderColor) {
		this.borderColorPressed = borderColor;
	}

	public Color getBorderColorPressed() {
		return borderColorPressed;
	}

	public void setBorderColorEntered(Color borderColor) {
		this.borderColorEntered = borderColor;
	}
	public Color getBorderColorEntered() {
		return borderColorEntered;
	}
	public int getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}

	@Override
	public void inputEnded() {}

	@Override
	public void inputStarted() {}

	@Override
	public boolean isAcceptingInput() {return true;}

	@Override
	public void setInput(Input arg0) {}

	@Override
	public void mouseWheelMoved(int arg0) {}

	@Override
	public void mouseClicked(int type, int x, int y, int count) {
		if( listener!=null){
			if(contains(x,y))listener.onClick(this);
		}
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		if(System.currentTimeMillis()-time>500)hasFocus=false;
		if(!hasFocus)return;
	}

	@Override
	public void mouseMoved(int ox, int oy, int x, int y) {
		if(System.currentTimeMillis()-time>500)hasFocus=false;
		mouseEntered=contains(x, y);
	}

	@Override
	public void mousePressed(int arg0, int x, int y) {
		if(System.currentTimeMillis()-time>500)hasFocus=false;
		mousePressed=contains(x, y);
	}

	@Override
	public void mouseReleased(int arg0, int x, int y) {
		if(System.currentTimeMillis()-time>500)hasFocus=false;
		mousePressed=false;

	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		if(System.currentTimeMillis()-time>500)hasFocus=false;
		if(!hasFocus)return;
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		if(System.currentTimeMillis()-time>500)hasFocus=false;

		// TODO Auto-generated method stub

	}

	@Override
	public  float getWidth(){
		if(width!=AUTOMATIC) return width;
		else return getAutomaticWidth();
	}
	@Override
	public  float getHeight(){
		if(height!=AUTOMATIC) return height;
		else return getAutomaticHeight();
	}

	@Override
	public boolean contains(float x, float y){

		if(x<this.x)return false;
		if(x>this.x+getWidth()) return false;
		if(y<this.y) return false;
		if(y>this.y+getHeight()) return false;

		return true;

	}

	public void setOnClickListener(OnClickListener listener){
		this.listener=listener;
	}

	public interface OnClickListener{
		void onClick(TGDComponent componenent);
	}
	public boolean hasFocus() {
		return hasFocus;
	}

	public void setHasFocus(boolean hasFocus) {
		this.hasFocus = hasFocus;
	}
}
