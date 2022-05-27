package project.airport;

import java.util.ArrayList;

import project.airline.aircraft.Aircraft;
import project.airline.aircraft.PassengerAircraft;
import project.passenger.Passenger;

public abstract class Airport {
	private final int ID;
	private final double x, y;
	protected double fuelCost;
	protected double operationFee;
	protected int aircraftCapacity;

	protected ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
	protected ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	
	Airport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity){
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.fuelCost = fuelCost;
		this.operationFee = operationFee;
		this.aircraftCapacity = aircraftCapacity; // max number of aircraft this airport can hold
	}
	
	public void addAircraft(PassengerAircraft pasAir) {
		this.aircrafts.add(pasAir);
	}
	
	public double getFuelCost() {
		return fuelCost;
	}
	
	public boolean equals(int ID) {
		return ID == this.getID() ? true : false;
	}
	
	public boolean isFull() {
		return this.aircraftCapacity == aircrafts.size() ? true : false;
	}
	
	public double getDistance(Airport other) {
		return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));	
	}
	
	public void addPassenger(Passenger passenger) {
		this.passengers.add(passenger);
	}
	
	public void removePassenger(Passenger passenger) {
		this.passengers.remove(passenger);
	}
	
	protected double getFullnessCoefficient() {
		return this.aircrafts.size()/(double)aircraftCapacity;
	}
	
	public int getID() {
		return ID;
	}
	
	public ArrayList<Passenger> getPassengers(){
		return this.passengers;
	}
	
	public abstract double departAircraft(Aircraft aircraft);
	public abstract double landAircraft(Aircraft aircraft);	
}
