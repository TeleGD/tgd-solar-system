package solar_system;

import solar_system.constructions.Vaisseau;

public class MenuVaisseau extends app.ui.Menu<MenuItemVaisseau> {

	public MenuVaisseau(String title, int xpos, int ypos) {
		super(title, xpos, ypos);
	}
	
	public Vaisseau getPressedVaisseau(int xmouse, int ymouse) {
		int i = super.getPressedItem(xmouse, ymouse);
		if (i >= 0) return super.getItem(i).getVaisseau();
		return null;
	}
}
