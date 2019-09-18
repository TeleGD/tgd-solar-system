package solar_system.constructions.vaisseaux;

import solar_system.Player;
import solar_system.World;
import solar_system.constructions.Vaisseau;

public class Colonisator extends Vaisseau{
	
	public Colonisator(Player player, World world) {
		super(player,world);
	}
	
	public String getName() {
		return "Colonisator";
	}
}
