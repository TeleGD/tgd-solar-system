package pages;

import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppMenu;
import app.elements.MenuItem;

import games.solarSystem.World;

public class HappyEnd extends AppMenu {

	public HappyEnd(int ID) {
		super(ID);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {
		super.initSize(container, game, 600, 400);
		super.init(container, game);
		this.setTitle("Adieu et bon voyage !");
		this.setSubtitle("La mission a été réussie avec succès.");
		this.setMenu(Arrays.asList(new MenuItem[] {
			new MenuItem("Admirer le soleil") {
				public void itemSelected() {
					((World) game.getState(3)).setState(2);
					game.enterState(3, new FadeOutTransition(), new FadeInTransition());
				}
			},
			new MenuItem("Rejouer") {
				public void itemSelected() {
					((World) game.getState(3)).setState(0);
					game.enterState(1, new FadeOutTransition(), new FadeInTransition());
				}
			},
			new MenuItem("Quitter") {
				public void itemSelected() {
					container.exit();
				}
			}
		}));
		this.setHint("gg");
	}

}
