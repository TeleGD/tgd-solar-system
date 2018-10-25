package solar_system;


public class Resource {
	
	private String name;
	private double quantite;
	
	public Resource(String name) {
		this.name=name;
		this.quantite=0;
	}
	
	
	public void addResource (double qtite) {
		this.quantite+=qtite;
	}
	
	public void render() {
		// A faire.
	}
	
}
