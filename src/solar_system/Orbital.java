package solar_system;

import java.util.ArrayList;

public class Orbital {
	
	private ArrayList<Station> station;
	private ArrayList<Satellite> satellite;
	
	public Orbital() {
		this.station = new ArrayList<Station>();
		this.satellite = new ArrayList<Satellite>();
	}
	
	public void addStation(Station s) {
		this.station.add(s);
	}
	public void delStation(Station s) {
		this.station.remove(s);
	}
	public void addSatellite(Satellite s) {
		this.satellite.add(s);
	}
	public void delSatellite(Satellite s) {
		this.satellite.remove(s);
	}
	public ArrayList<Station> getStation(){
		return this.station;
	}
	public ArrayList<Satellite> getSatellite(){
		return this.satellite;
	}
}
