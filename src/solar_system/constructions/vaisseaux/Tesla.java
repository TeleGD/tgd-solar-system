package solar_system.constructions.vaisseaux;

import app.AppLoader;
import solar_system.Player;
import solar_system.World;
import solar_system.constructions.Vaisseau;

public class Tesla extends Vaisseau{
	
	public Tesla(Player player, World world) {
		super(player,world);
		this.name = "Tesla";
		this.sprite = AppLoader.loadPicture("/images/constructions/tesla.png");
		this.cout.put("Fer",100.0);
		this.cout.put("Bois",20.0);
		this.v0Max = 0.5;
	}
}
