package solar_system.constructions.vaisseaux;

import app.AppLoader;
import solar_system.Player;
import solar_system.Solsys;
import solar_system.World;
import solar_system.constructions.Vaisseau;

public class Tesla extends Vaisseau{
	
	public Tesla(Player player, Solsys solsys) {
		super(player, solsys);
		this.name = "Tesla";
		this.sprite = AppLoader.loadPicture("/images/constructions/tesla.png");
		cout.put("Fer",100.0);
		cout.put("Bois",20.0);
		this.v0Max = 0.5;
	}
}
