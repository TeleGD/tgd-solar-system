package solar_system;

import app.AppLoader;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class Explosion {

	private float aspectRatio;
	private int x;
	private int y;
	private Animation animation; // animation
	private int spriteWidth;
	private int spriteHeight;
	protected boolean side; // true : Regarde vers la droite, false : Regarde vers la gauche
	private int frameDuration = 200;


	/**
	 * @param aspectRatio
	 * @param spritePath chemin vers l'image contenant les sprites d'animation
	 * @param spriteWidth largeur d'un sprite à l'affichage
	 * @param spriteHeight hauteur d'un sprite à l'affichage
	 * @param spriteNaturalWidth largeur d'un sprite dans le spriteSheet des ressources du jeu
	 * @param spriteNaturalHeight hauteur d'un sprite dans le spriteSheet des ressources du jeu
	 * @param x
	 * @param y
	 * @param nbFramesOnX
	 */
	public Explosion(float aspectRatio, String spritePath, int spriteWidth, int spriteHeight, int spriteNaturalWidth, int spriteNaturalHeight, int x, int y, int nbFramesOnX, int nbFramesOnY, int animLineToLoad){
		this.aspectRatio = aspectRatio;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.x = x;
		this.y = y;

		// Chargements des sprites dans les animations :
		SpriteSheet spriteSheet = null;
		Image spriteImage = AppLoader.loadPicture(spritePath);
		spriteSheet = new SpriteSheet(spriteImage, spriteNaturalWidth, spriteNaturalHeight);

		animation = new Animation();

		if (nbFramesOnY == 1) {
			loadAnimation(spriteSheet,0, nbFramesOnX-1, animLineToLoad);
		}
		else {
			loadAnimationOnMultipleLines(spriteSheet,0, nbFramesOnX-1, nbFramesOnY);
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		context.drawAnimation(animation, this.x , this.y);
	}

	public void loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int animLineToLoad) {
		for (int i = startX; i < endX; i++) {
			animation.addFrame(spriteSheet.getSprite(i, animLineToLoad).getScaledCopy((int) (spriteWidth * this.aspectRatio), (int) (spriteHeight * this.aspectRatio)), this.frameDuration);
		}
	}

	public void loadAnimationOnMultipleLines(SpriteSheet spriteSheet, int startX, int endX, int endY) {
		for (int i = startX; i < endX; i++) {
			for (int j = 0; j < endY; j++) {
				animation.addFrame(spriteSheet.getSprite(i, j).getScaledCopy((int) (spriteWidth * this.aspectRatio), (int) (spriteHeight * this.aspectRatio)), this.frameDuration);
			}
		}
	}
}
