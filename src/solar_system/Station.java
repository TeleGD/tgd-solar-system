package solar_system;

public class Station {
	private int nbVaisseaux;
	
	public Station() {
		this.nbVaisseaux = 0;
	}
	public void setV(int n /*nombre de vaisseaux reçu*/) {
		this.nbVaisseaux = this.getNbVaisseaux() + n;
	}
	public void envoyerVaisseaux(Station s, int nbve /*nombre de vaisseaux envoyé*/){
		this.nbVaisseaux = this.getNbVaisseaux() - nbve;
		s.setV(nbve);
	}
	public int getNbVaisseaux() {
		return nbVaisseaux;
	}
}
