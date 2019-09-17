package solar_system;

import solar_system.constructions.Vaisseau;


public class MenuItemVaisseau extends app.ui.MenuItem {
	
	private Vaisseau vaisseau;
	
	public MenuItemVaisseau(Vaisseau vaisseau, int nbv, int padding, int xpos, int ypos) {
		super(vaisseau.sprite, vaisseau.name+" : "+nbv, padding, xpos, ypos);
		this.vaisseau = vaisseau;
	}
	
	public Vaisseau getVaisseau() {
		return this.vaisseau;
	}
}
