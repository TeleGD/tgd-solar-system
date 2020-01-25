package games.solarSystem.constructions.vaisseaux;

import games.solarSystem.Player;
import games.solarSystem.Solsys;
import games.solarSystem.constructions.Vaisseau;

public class Debris extends Vaisseau{

	public Debris(Player player, Solsys solsys) {
		super(player,solsys);
		this.name = "Débris";
		cout.put("Fer",100.0);
		cout.put("Bois",20.0);
		this.v0Max = 0.5;
	}
}
