package games.solarSystem.constructions.vaisseaux;

import games.solarSystem.Player;
import games.solarSystem.Solsys;
import games.solarSystem.constructions.Vaisseau;

import app.AppLoader;

public class Tesla extends Vaisseau{

	public Tesla(Player player, Solsys solsys) {
		super(player, solsys);
		this.name = "Tesla";
		this.sprite = AppLoader.loadPicture("/images/solarSystem/constructions/tesla.png");
		cout.put("Fer",100.0);
		cout.put("Bois",20.0);
		cout.put("Noyaux Linux éduqués",4.0);
		this.v0Max = 0.5;
	}
}
