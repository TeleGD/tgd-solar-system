package solar_system.constructions.vaisseaux;

import app.AppLoader;
import solar_system.Player;
import solar_system.World;
import solar_system.constructions.Vaisseau;

public class Colonisator extends Vaisseau{
	
	public Colonisator(Player player, World world) {
		super(player,world);
		this.name = "Colonisator";
		this.sprite = AppLoader.loadPicture("/images/constructions/Vaisseau.png");
	}
}
