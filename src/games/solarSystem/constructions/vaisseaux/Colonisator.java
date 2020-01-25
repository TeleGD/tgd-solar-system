package games.solarSystem.constructions.vaisseaux;

import games.solarSystem.Planet;
import games.solarSystem.Player;
import games.solarSystem.Solsys;
import games.solarSystem.constructions.Vaisseau;

import app.AppLoader;

public class Colonisator extends Vaisseau{

	public Colonisator(Player player, Solsys solsys) {
		super(player, solsys);
		this.name = "Colonisator";
		this.sprite = AppLoader.loadPicture("/images/solarSystem/constructions/vseau.png");
		this.v0Max = 0.35;
	}

	public void crash(Planet p) {
		if (this.hasLeft) {
			this.crashed = true;
			if (p != null) p.setOwner(player);
		}
	}
}
