package project.airportcontainer;

import java.util.ArrayList;


import project.aircraftcontainer.Aircraft;
import project.passengercontainer.Passenger;



 public abstract class Airport {
	protected final long ID; 
	protected  final double x,y; //coordinates
	protected double fuelCost; // price of fuel in this airport
	protected double operationFee; // fee paid for certain operations
	protected int aircraftCapacity;
	protected int aircraftCount;
	public ArrayList<Passenger> passengers= new ArrayList<>();
	
	
	public Airport(long iD2, double x2, double y2, double fuelCost2, double operationFee2, int aircraftCapacity2) {
		// TODO Auto-generated constructor stub
		this.ID=iD2;
		this.x=x2;
		this.y=y2;
		this.fuelCost=fuelCost2;
		this.operationFee=operationFee2;
		this.aircraftCapacity=aircraftCapacity2;
	}
	
	
	public abstract double departAircraft(Aircraft aircraft);
		// does the necessary departure operations and returns the departure fee
	
	
	
	public abstract double landAircraft(Aircraft aircraft);
		// does the necessary departure operations and returns the departure fee
	
	public boolean equals(Airport other) {
		if (this.ID==other.ID) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isFull() {
		if (aircraftCount==aircraftCapacity) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addPassenger(Passenger passenger) {
		passengers.add(passenger);
	}
	
	public void removePassenger(Passenger passenger) {
		passengers.remove(passenger);
	}
	
	// we can add boolean equals(Airport other), boolean isFull(), double getDistance(Airport airport) void addPassenger(Passenger passenger)
	public double getDistance(Airport airport) {
		return Math.pow(Math.pow(airport.y-this.y,2)+Math.pow(airport.x-this.x, 2),0.5);
	}
	
	protected double getFullnessCoefficient() {
		double aircraftRatio= (double)((double)(aircraftCount)/(double)(aircraftCapacity));
		return 0.6*Math.pow(Math.E,aircraftRatio);
	}
	
	public boolean checkPassenger(Passenger passenger) {
		for (Passenger passenger1 : passengers) {
			if (passenger1.getID()==passenger.getID()) {
				return true;
			}
		}
		return false;
	}
	
	public abstract int getType();

	public long getID(){
		return ID;
	}
	
	public int getAircraftCount() {
		return aircraftCount;
	}
	public void setAircraftCount(int count) {
		this.aircraftCount=count;
	}
	
	public double getFuelCost() {
		return fuelCost;
	}
}
