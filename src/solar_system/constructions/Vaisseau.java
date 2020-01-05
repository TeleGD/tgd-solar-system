package solar_system.constructions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

import solar_system.Construction;
import solar_system.Planet;
import solar_system.Player;
import solar_system.Solsys;
import solar_system.World;
import solar_system.constructions.vaisseaux.*;

public abstract class Vaisseau extends Construction { 
	
	protected double x;
	protected double y;
	protected double vx;
	protected double vy;
	protected double v0Max;
	protected float angle;
	protected boolean launched;
	protected boolean hasLeft;
	protected boolean splitted;
	protected boolean crashed;
	protected Solsys solsys;
	protected Image img;
	protected ArrayList<Debris> debris;
	
	public Vaisseau(Player player, Solsys solsys){
		super(player) ;
		this.name="Vaisseau spatial";
		this.lifeMax=80;
		this.life=lifeMax;
		cout.put("Fer",50.0);
		cout.put("Bois",20.0);
		this.solsys = solsys;
		this.v0Max = 1000;
		this.debris = new ArrayList<>();
	}
	
	public void launch(int x0, int y0, double vx0, double vy0) {
		this.x = x0;
		this.y = y0;
		this.vx = vx0;
		this.vy = vy0;
		this.launched = true;
		this.hasLeft = false;
		this.crashed = false;
		if (this.sprite != null) {
			this.img = sprite.getScaledCopy(64, 64);
			this.img.setCenterOfRotation(32, 32);
		}
		
	}
	
	public boolean isLaunched() {
		return this.launched;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public void setImage(Image img) {
		this.img = img;
		this.img.setCenterOfRotation(img.getWidth()/2, img.getHeight()/2);
	}
	
	public int getSize() {
		return this.img.getWidth();
	}
	
	public void setSize(int width) {
		this.img = this.img.getScaledCopy(width, width);
	}
	
	public boolean hasLeft() {
		return this.hasLeft;
	}
	
	public void left() {
		this.hasLeft = true;
	}
	
	public boolean hasCrashed() {
		return this.crashed;
	}
	
	public void crash(Planet p) {
		if (hasLeft) this.crashed = true;
	}
	
	// Distance en pixels
	public double getDistance(Planet p) {
		return Math.hypot(x-p.getPosX(), y-p.getPosY());
	}
	
	public double getDistance(Vaisseau v) {
		double distance2 = Math.pow(this.x-v.getX(), 2) + Math.pow(this.y-v.getY(), 2);
		return Math.sqrt(distance2);
	}
	
	public double getV0Max() {
		return this.v0Max;
	}
	
	public void nextOrbitalPosition(int delta) {
		double rx = this.x;
		double ry = this.y;
		double r3 = Math.pow(rx*rx+ry*ry, 1.5);
		vx -= delta*40*rx/r3;	// Variation de la vitesse = accélération
		vy -= delta*40*ry/r3;
		x += delta*vx;
		y += delta*vy;
		if (this.img != null) {
			angle = (float)(Math.atan2(vx, -vy)*180/Math.PI)-90;
			this.img.setRotation(angle);
		}
	}
	
	public void split(int n) {
		if (!splitted) {
			double x = this.img.getWidth()*1d/n;
			double y = this.img.getHeight()*1d/n;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					Debris d = new Debris(player, solsys);
					d.launch((int)(this.x+i*x), (int)(this.y+j*y), vx+Math.random()-0.5, vy+Math.random()-0.5);
					d.setImage(this.img.getSubImage((int)(i*x), (int)(j*y), (int)x, (int)y));
					debris.add(d);
				}
			}
			splitted = true;
			hasLeft = true;
			crashed = true;
		}
	}
	
	public ArrayList<Debris> getDebris() {
		return debris;
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
		if (!crashed && launched) {
			nextOrbitalPosition(delta);
		}
		if (Math.hypot(x, y) > 1e5) {
			this.crashed = true;
		}
	}
	
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		if (!crashed && launched && img != null) {
			int[] pos = this.solsys.toScreenPosition(x, y);
			Image scaledImg = img.getScaledCopy(solsys.getZoom());
			scaledImg.setAlpha(img.getAlpha());
			scaledImg.setRotation(img.getRotation());
			context.drawImage(scaledImg, pos[0], pos[1]);
		}
	}

}
