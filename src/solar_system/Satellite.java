package solar_system;

public class Satellite {
	private int debit;
	private String type;
	
	public Satellite(int d, String t) {
		this.debit = d;
		this.type = t;
	}
	
	public int getDebit() {
		return debit;
	}
	public String getType() {
		return type;
	}
}
