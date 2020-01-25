package games.solarSystem.constructions.vaisseaux;

import games.solarSystem.Planet;
import games.solarSystem.Player;
import games.solarSystem.Solsys;
import games.solarSystem.constructions.Vaisseau;

import app.AppLoader;

public class Missile extends Vaisseau{

	public Missile(Player player, Solsys solsys) {
		super(player, solsys);
		this.name = "Missile";
		this.sprite = AppLoader.loadPicture("/images/solarSystem/constructions/Vaisseau.png");
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
