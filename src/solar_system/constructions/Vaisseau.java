package solar_system.constructions;

import solar_system.Construction;
import solar_system.Player;

public class Vaisseau extends Construction{
	
	public Vaisseau(Player player){
		super(player) ;
		this.name="Vaisseau spatial";
		this.lifeMax=80;
		this.life=lifeMax;
		this.cout.put("Noyo",0.0);
	}

}
