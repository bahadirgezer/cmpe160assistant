package airport;

import java.util.ArrayList;
import java.util.HashMap;

import airline.aircraft.Aircraft;
import airline.aircraft.PassengerAircraft;
import passenger.Passenger;

public abstract class Airport {

	
	private final int ID;
	private final double x, y;
	protected double fuelCost;
	
	protected double operationFee;
	protected int aircraftCapacity;
	
	protected String airportType;
	protected ArrayList<PassengerAircraft> aircrafts = new ArrayList<PassengerAircraft>();
	protected HashMap<Long, Passenger> passengers = new HashMap<Long, Passenger>();
	
	Airport( int id, double x,double y, int aircraftCapacity, double operationFee,	double fuel_cost){
		this.ID = id;
		this.x = x;
		this.y = y;
		this.aircraftCapacity = aircraftCapacity;
		this.operationFee = operationFee;
		this.fuelCost = fuel_cost;
	}
	
	
	public int getID() {
		return ID;
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
	
	public String getAirportType(){
		return airportType;
	}
	
	public HashMap<Long, Passenger> getPassengers() {
		return passengers;
	}


	public void addAircraft(Aircraft aircraft) {
		aircrafts.add((PassengerAircraft) aircraft);
	}
	
	public void removeAircraft(Aircraft aircraft) {
		aircrafts.remove(aircraft);
	}
	
	public boolean isFull() {
		if(aircrafts.size()< aircraftCapacity)
			return false;
		else
			return true;
	}
	
	public ArrayList<PassengerAircraft> getAircrafts() {
		return aircrafts;
	}


	public void addPassenger(Passenger passenger) {
		passengers.put(passenger.getID(), passenger);
	}
	
	public void removePassenger(Passenger passenger) {
		passengers.remove(passenger.getID());
	}
	
	public int numberOfPassengers() {
		return passengers.size();
	}
	
	public double numberOfAircrafts() {
		return aircrafts.size();
	}
	
	//Does the necessary departure operations and returns the departure fee.
	public abstract double departAircraft(Aircraft aircraft);
	
	//Does the necessary landing operations and returns the landing fee.
	public abstract double landAircraft(Aircraft aircraft);
	
	public double getDistance(Airport toAirport) {
		double distance = Math.sqrt(Math.pow((Math.abs(toAirport.getX() - this.getX())),2) + Math.pow((Math.abs(toAirport.getY() - this.getY())),2));
		return distance;
	}
}