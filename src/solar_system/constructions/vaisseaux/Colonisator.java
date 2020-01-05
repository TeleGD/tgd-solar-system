package solar_system.constructions.vaisseaux;

import app.AppLoader;
import solar_system.Planet;
import solar_system.Player;
import solar_system.Solsys;
import solar_system.World;
import solar_system.constructions.Vaisseau;

public class Colonisator extends Vaisseau{
	
	public Colonisator(Player player, Solsys solsys) {
		super(player, solsys);
		this.name = "Colonisator";
		this.sprite = AppLoader.loadPicture("/images/constructions/vseau.png");
		this.v0Max = 0.35;
	}
	
	public void crash(Planet p) {
		if (this.hasLeft) {
			this.crashed = true;
			if (p != null) p.setOwner(player);
		}
	}
}
