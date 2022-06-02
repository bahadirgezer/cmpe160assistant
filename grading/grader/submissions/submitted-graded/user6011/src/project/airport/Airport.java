package project.airport;

import java.util.HashMap;

import project.airline.aircraft.Aircraft;
import project.passenger.Passenger;

public abstract class Airport {
	
	private final int ID;
	private final double x, y;
	protected double fuelCost;
	protected double operationFee;
	protected int aircraftCapacity;
	protected int currentAircraftNumber = 0;
	protected HashMap<Long, Passenger> passengers = new HashMap<Long, Passenger>();
	public int airportType;
	
	public Airport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity, int airportType) {
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.fuelCost = fuelCost;
		this.operationFee = operationFee;
		this.aircraftCapacity = aircraftCapacity;
		this.airportType = airportType;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getFuelCost() {
		return fuelCost;
	}
	public int getCurrentAircraftNumber() {
		return currentAircraftNumber;
	}
	public int getAircraftCapacity() {
		return aircraftCapacity;
	}
	public double getDistance(Airport airport) {
		return Math.sqrt(Math.pow(x-airport.getX(), 2) + Math.pow(y-airport.getY(), 2));
	}
	public void addPassenger(Passenger passenger) {
		passengers.put(passenger.getID(), passenger);
	}
	public void removePassenger(Passenger passenger) {
		passengers.remove(passenger.getID());
	}
	public HashMap<Long, Passenger> getPassengers() {
		return passengers;
	}
	public int getID() {
		return ID;
	}
	
	public abstract double departAircraft(Aircraft aircraft);
	public abstract double landAircraft(Aircraft aircraft);

}








