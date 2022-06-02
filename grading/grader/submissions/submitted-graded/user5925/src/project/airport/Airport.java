package project.airport;

import java.util.ArrayList;

import project.airline.aircraft.Aircraft;
import project.passenger.Passenger;

public abstract class Airport {

	private final int ID;
	private final double x, y;
	protected double fuelCost;
	protected double operationFee;
	protected int aircraftCapacity;
	public int airport_type = 0; 
	public ArrayList <Passenger> passenger_list;
	public ArrayList <Aircraft> current_aircrafts;
	
	
	Airport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.fuelCost = fuelCost;
		this.operationFee = operationFee;
		this.aircraftCapacity = aircraftCapacity;
		
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	public int getID() {
		return ID;
	}
	
	public double getOperationFee() {
		return operationFee;
	}
	
	public double getFuelCost() {
		return fuelCost;
	}
	
	public int getAircraftCapacity() {
		return aircraftCapacity;
	}
	
	public int getCapacity() {
		return aircraftCapacity;
	}
	
	public void addPassenger(Passenger passenger) {
		passenger_list.add(passenger);
	}
	
	public void removePassenger(Passenger passenger) {
		passenger_list.remove(passenger);
	}
	
	public abstract double departAircraft(Aircraft aircraft);
	public abstract double landAircraft(Aircraft aircraft);
	
}
