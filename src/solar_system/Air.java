package solar_system;

import java.util.*;

public class Air {
	private int nbOrbitaux;
	private int distance;
	
	private List<Orbital> orbitals;
	
	public Air(int nbOrbitaux, int distance) {
		this.nbOrbitaux = nbOrbitaux;
		this.distance = distance;
		
		this.orbitals = new ArrayList<Orbital>();
	}
	
	public void addOrbital(Orbital o) {
		if (orbitals.size()<nbOrbitaux)
			orbitals.add(o);
	}
	
}
