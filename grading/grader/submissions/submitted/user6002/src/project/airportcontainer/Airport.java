package project.airportcontainer;

import java.util.ArrayList;

import project.airlinecontainer.aircraftcontainer.Aircraft;
import project.passengercontainer.Passenger;

public abstract class Airport {
	private final int ID;
	private final double x, y;
	protected double fuelCost; //Price of fuel in this airport.
	protected double operationFee;
	protected int aircraftCapacity;
	public int airportType;
	ArrayList<Aircraft> currentAircrafts = new ArrayList<Aircraft>();
	ArrayList<Passenger> passengersAtAirport = new ArrayList<Passenger>();
	
	public Airport(int airportType, int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		this.airportType = airportType;
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.fuelCost = fuelCost;
		this.operationFee = operationFee;
		this.aircraftCapacity = aircraftCapacity;
	}
	
	public ArrayList<Passenger> getPassengersAtAirport() {
		return passengersAtAirport;
	}
	
	public boolean isPassengerAtAirport(Passenger passenger) {
		return passengersAtAirport.contains(passenger);
	}
	
	public void removePassenger(Passenger passenger) {
		passengersAtAirport.remove(passenger);
	}
	
	public void addPassenger(Passenger passenger) {
		passengersAtAirport.add(passenger);
	}
	
	public int getID() {
		return ID;
	}
	
	public double getFuelCost() {
		return fuelCost;
	}
	
	public double getOperationFee() {
		return operationFee;
	}
		
	public abstract double departAircraft(Aircraft aircraft);
	
	public abstract double landAircraft(Aircraft aircraft);
	
	public boolean isFull() {
		if (currentAircrafts.size() == aircraftCapacity) {
			return true;
		} else {
			return false;
		}
	}
	
	public double getDistance(Airport airport) {
		return Math.pow((Math.pow(this.x - airport.x, 2) + Math.pow(this.y - airport.y, 2)), 0.5);
	}
}
