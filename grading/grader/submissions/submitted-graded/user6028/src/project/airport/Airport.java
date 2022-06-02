package project.airport;

import java.util.ArrayList;
import java.util.HashMap;

import project.airline.aircraft.Aircraft;
import project.passenger.Passenger;

public abstract class Airport {
	private final int ID;
	private final double x, y;
	protected double fuelCost;
	protected double operationFee;
	protected int aircraftCapacity;
	protected int aircraftCount;
	protected int airportType;
	
	private HashMap<Long, Passenger> passengerList = new HashMap<Long, Passenger>();
	
	public Airport(int airportType, int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		this.airportType = airportType;
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.fuelCost = fuelCost;
		this.operationFee = operationFee;
		this.aircraftCapacity = aircraftCapacity;
		
	}
	
	public double getDistance(Airport airport) {
		double xDiff = Math.abs(this.x - airport.x);
		double yDiff = Math.abs(this.y - airport.y);
		double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
		return distance;
	}
	
	
	public void addPassenger(Passenger passenger) {
		passengerList.put(passenger.getID(), passenger);
	}
	
	public double getFuelCost() {
		return this.fuelCost;
	}
	
	public double getOperationFee() {
		return this.operationFee;
	}
	
	public void removePassenger(Passenger passenger) {
		passengerList.remove(passenger.getID(), passenger);
	}
	
	public boolean isFull() {
		return (aircraftCount == aircraftCapacity);
	}
	
	public int getAirportType() {
		return airportType;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getPassengerCount() {
		return passengerList.size();
	}
	
	public HashMap<Long, Passenger> getPassengerList(){
		return this.passengerList;
	}
	
	public void incrementAircraftCount() {
		this.aircraftCount++;
	}
	
	public double getAircraftCapacity() {
		return this.aircraftCapacity;
	}
	
	public abstract double landAircraft(Aircraft aircraft);
	public abstract double departAircraft(Aircraft aircraft);	
}
