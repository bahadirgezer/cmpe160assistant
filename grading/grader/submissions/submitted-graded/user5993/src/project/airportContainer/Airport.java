package project.airportContainer;
import java.util.ArrayList;

import project.airlineContainer.aircraftContainer.Aircraft;
import project.passengerContainer.Passenger;


public abstract class Airport {
	private final int ID ;
	private final double x;
	private final double y;
	protected double fuelCost;
	public double getFuelCost() {
		return fuelCost;
	}
	public void setFuelCost(double fuelCost) {
		this.fuelCost = fuelCost;
	}
	public int getId() {
		return this.ID;
	}
	
	protected double operationFee;
	protected int aircraftCapacity;
	public ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
	protected ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	
	public void setPassengers(ArrayList<Passenger> passengers) {
		this.passengers = passengers;
	}

	public Airport(int iD, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		ID = iD;
		this.x = x;
		this.y = y;
		this.fuelCost = fuelCost;
		this.operationFee = operationFee;
		this.aircraftCapacity = aircraftCapacity;
	}
	public double getDistance(Airport toAirport) {
		double x1=this.getX();
		double y1=this.getY();
		
		double x2=toAirport.getX();
		double y2=toAirport.getY();
		return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public boolean isFull() {

		if (aircrafts.size()<aircraftCapacity) {

			return false;
		}
		else {

			return true;
		}
		
		
	}
	public ArrayList<Passenger> getPassengers() {
		return this.passengers;
	}
	public double getOperationFee() {
		return this.operationFee;
		
	}
	
	
	public abstract double departAircraft(Aircraft aircraft);
	public abstract double landAircraft(Aircraft aircraft);
	
}
