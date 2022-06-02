package project.airportcontainer;
import project.airlinecontainer.aircraftcontainer.*;
import project.passengercontainer.Passenger;

import java.util.ArrayList;

public abstract class Airport {
	
	public  Airport(long id, double x, double y,double fuelcost,double operationfee,int aircraftCapacity) {
		this.ID=id;
		this.x=x;
		this.y=y;
		this.fuelCost=fuelcost;
		this.operationFee=operationfee;
		this.aircraftCapacity=aircraftCapacity;
	}
	
	public long getID() {
		return ID;
	}

	public abstract double departAircraft(Aircraft aircraft);
	public abstract double landAircraft(Aircraft aircraft);
	protected ArrayList<Aircraft> current_aircrafts=new ArrayList<Aircraft>();
	private final long ID;
	private final double x, y;
	protected double fuelCost;
	protected double operationFee;
	protected int aircraftCapacity;
	


	public double get_coordinatesx() {
		return this.x;
	}
	public double get_coordinatesy() {
		return this.y;
	}
	public double getfuelCost(){
		return this.fuelCost;
	}
	public void add_aircraft(Aircraft aircraft) {
		this.current_aircrafts.add(aircraft);
	}
	public void remove_aircraft(Aircraft aircraft) {
		this.current_aircrafts.remove((Object)aircraft);
	}
	public ArrayList<Aircraft> get_current_aircrafts(){
		return this.current_aircrafts;
	}
	public int get_aircraftCapacity() {
		return this.aircraftCapacity;
	}
}
