package games.solarSystem.util;

import java.util.ArrayList;
import java.util.List;

public class Background {
	protected double width;
	protected double height;
	protected double minDist;
	protected double newPointsCount;
	protected List<int[]> samplePoint;

	//Constructeur
	public Background() {
		this(1920, 1080, 5, 0);
	}

	public Background(double width, double height, double minDist, double newPointsCount){
		this.width = width;
		this.height = height;
		this.minDist = minDist;
		this.newPointsCount = newPointsCount;
		this.samplePoint = new ArrayList<int[]>();
	}

	public void setMinDist(double minDist){
		this.minDist = minDist;
	}

	public void generatePoisson(){
		//Création de la grille basé sur cellSize
		double cellSize = minDist/Math.sqrt(2);


	}
}
