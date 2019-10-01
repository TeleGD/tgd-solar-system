package solar_system.constructions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import solar_system.Construction;
import solar_system.Planet;
import solar_system.Player;
import solar_system.World;

public abstract class Vaisseau extends Construction{// TODO : A mettre en abstract quand on enverra les vaisseaux construits 
	
	protected double x;
	protected double y;
	protected double vx;
	protected double vy;
	protected double v0Max;
	protected float angle;
	protected boolean launched;
	protected boolean hasLeft;
	protected boolean crashed;
	protected World world;
	protected Image img;
	
	public Vaisseau(Player player, World world){
		super(player) ;
		this.name="Vaisseau spatial";
		this.lifeMax=80;
		this.life=lifeMax;
		cout.put("Fer",50.0);
		cout.put("Bois",20.0);
		this.world = world;
		this.v0Max = 1000;
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
		double distance2 = Math.pow(x-(p.getPosX()+world.getWidth()/2), 2)+ Math.pow(y-(p.getPosY()+world.getHeight()/2), 2);
		return Math.sqrt(distance2);
	}
	
	public double getV0Max() {
		return this.v0Max;
	}
	
	public void nextOrbitalPosition(int delta) {
		double rx = this.x - world.getWidth()/2;
		double ry = this.y - world.getHeight()/2;
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
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
		if (!crashed && launched) {
			nextOrbitalPosition(delta);
		}
	}
	
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		if (!crashed && launched && img != null) {
			context.drawImage(img, (float)x, (float)y);
		}
	}

}
