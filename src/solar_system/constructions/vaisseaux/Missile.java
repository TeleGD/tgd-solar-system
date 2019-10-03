package solar_system.constructions.vaisseaux;

import app.AppLoader;
import solar_system.Planet;
import solar_system.Player;
import solar_system.World;
import solar_system.constructions.Vaisseau;

public class Missile extends Vaisseau{
	
	public Missile(Player player, World world) {
		super(player,world);
		this.name = "Missile";
		this.sprite = AppLoader.loadPicture("/images/constructions/Vaisseau.png");
		this.v0Max = 0.4;
	}
	
	public void crash(Planet p) {
		if (this.hasLeft) {
			this.crashed = true;
			if (p != null) {
				p.destruct(2);
				this.split(2);
			}
		}
	}
}
