package solar_system;

import solar_system.constructions.Vaisseau;


public class MenuItemVaisseau extends app.ui.MenuItem {
	
	private Vaisseau vaisseau;
	
	public MenuItemVaisseau(Vaisseau vaisseau, int nbv, int padding, int xpos, int ypos) {
		super(vaisseau.getSprite().getScaledCopy(24, 24), vaisseau.getName()+" : "+nbv, padding, xpos, ypos);
		this.vaisseau = vaisseau;
	}
	
	public MenuItemVaisseau(Vaisseau vaisseau, int nbv) {
		this(vaisseau, nbv, 8, 128, 128);
	}
	
	public Vaisseau getVaisseau() {
		return this.vaisseau;
	}
}
