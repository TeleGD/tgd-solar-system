package solar_system.constructions;

import solar_system.Case;
import solar_system.Construction;
import solar_system.Production;
import solar_system.util.Images;

public class Mine extends Construction {

	
	public Mine (Case tile){
		super(tile);
		this.image = Images.getConstruction("mine");
		this.lifeMax=100;
		this.life=lifeMax;
		this.productions.add(new Production(3, "fer", player, tile));
	}
	
	
}

