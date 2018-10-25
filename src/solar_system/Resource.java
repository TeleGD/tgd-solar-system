package solar_system;


public class Resource {
	
	private String name;
	private double quantite;
	
	public Resource(String name) {
		this.name=name;
		this.quantite=0;
	}
	
	
	public boolean modifQuantite (double qtite) {  
		/* Ajoute ou prélève (si qtite<0)
			Renvoie true si l'opération a réussi,
			Renvoie false si l'oprération est interrompue
				(prélèvement supérieur à la quantite disponible).
		*/
		if (qtite+quantite<0){
			return false;
		}
		quantite+=qtite;
		return true;
	}
	
	public String getName () {
		return name;
	}
	
	public double getQuantite () {
		return quantite;
	}
	
	public void preleveResource (double qtite) {
		quantite-=qtite;
	}
	
	
	public void render() {
		// A faire.
	}
	
}
