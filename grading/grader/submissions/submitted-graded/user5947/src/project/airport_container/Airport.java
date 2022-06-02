package project.airport_container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import project.airline_container.Airline;
import project.airline_container.aircraft_container.Aircraft;
import project.passenger_container.Passenger;
import project.utils.Util;

public abstract class Airport {
	
	private final long ID;
	private final double x, y;
	protected double fuelCost;
	
	protected double operationFee;
	protected int aircraftCapacity;

	protected ArrayList<Aircraft> aircrafts = new ArrayList<>();
	private HashMap<Long, Passenger> passengers = new HashMap<>();
	private Map<Long, Double> distances;
	
	public Airport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		super();
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.fuelCost = fuelCost;
		this.operationFee = operationFee;
		this.aircraftCapacity = aircraftCapacity;
	}

	public void setDistances() {
		HashMap<Long, Double> distancesUnsorted = new HashMap<>();
		for (Airport airport: Airline.getInstance().getAirports()) {
			if (!airport.equals(ID)) {
				distancesUnsorted.put(airport.getId(), Util.findDistance(airport, this));
			}
		}
		distances = Util.sortByValue(distancesUnsorted);
	}
	
	public Map<Long, Double> getDistances() {
		return distances;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public boolean equals(long ID) {
		return this.ID == ID;
	}

	public HashMap<Long, Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(HashMap<Long, Passenger> passengers) {
		this.passengers = passengers;
	}
	
	public void addPassenger(Passenger passenger) {
		passengers.put(passenger.getID(), passenger);
	}
	
	public void removePassenger(long id) {
		passengers.remove(id);
	}
	
	public double getFullnessCoefficient() {
		return Math.pow(0.6 * Math.E, (double) aircrafts.size() / aircraftCapacity);
	}
	
	public double getFuelCost() {
		return fuelCost;
	}
	
	public long getId() {
		return ID;
	}
	
	public abstract double departAircraft(Aircraft aircraft);
	public abstract double landAircraft(Aircraft aircraft);
	
}
