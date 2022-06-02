package project.airport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import project.airline.Airline;
import project.airline.aircraft.Aircraft;
import project.passenger.*;


public abstract class Airport {
	private final int ID;
	private final double x, y;
	protected final double fuelCost;
	protected final double operationFee;
	protected final int aircraftCapacity;
	private final int airportType; // 1 hub, 2 major, 3 regional
	private ArrayList<Passenger> passengers= new ArrayList<Passenger>();
	private HashMap<Integer, Integer> potentialFlights= new HashMap<Integer, Integer>();
	private ArrayList<Integer> linkedWith = new ArrayList<Integer>();
	private HashMap <Integer, Integer> aircrafts = new HashMap<Integer, Integer>();
	
	public Airport(int airportType, int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.fuelCost = fuelCost;
		this.operationFee = operationFee;
		this.aircraftCapacity = aircraftCapacity;
		this.airportType = airportType;
		
	}
	
	public void addPassenger(Passenger passenger) {
		this.passengers.add(passenger);
		int nextAirport = passenger.getDestinations().get(1);
		if (this.potentialFlights.containsKey(nextAirport)) {
			int newValue = this.potentialFlights.get(nextAirport)+1;
			this.potentialFlights.replace(nextAirport, newValue);
		}
		else {
			this.potentialFlights.put(nextAirport, 1);
		}
	}

	public void addAircraft(Airport toAirport, int ID) {
		this.aircrafts.put(toAirport.getID(), ID);
	}
	public void removeAircraft(Airport toAirport) {
		this.aircrafts.remove(toAirport.getID());
	}
	public int getID() {
		return this.ID;
	}
	
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	public double getOperationFee() {
		return this.operationFee;
	}
	public double getDistance(Airport toAirport) {
		return Math.sqrt(Math.pow(x-toAirport.getX(), 2) + Math.pow(y-toAirport.getY(), 2));
	}
	
	public abstract double departAircraft(Aircraft aircraft);
	public abstract double landAircraft(Aircraft aircraft);
	
	
	public int getNoAircrafts() {
		return this.aircrafts.size();
	}
	public int getAircraftCapacity() {
		return aircraftCapacity;
	}
	public void setPassengers(ArrayList<Passenger> newPassengers) {
		this.passengers=newPassengers;
	}
	public double getFuelCost() {
		return this.fuelCost;
	}
	
	
	public void linkWith(int Id) {
		this.linkedWith.add(Id);
	}
	
	
	public ArrayList<Integer> maxPassengersTo(HashMap<Integer, Integer> potentialFLights) {
		ArrayList<Integer> max = new ArrayList<Integer>();
		if (potentialFLights.isEmpty()) {
			return max;
		}
		else {
		int maxValueInMap=(Collections.max(potentialFLights.values())); 
        for (Entry<Integer, Integer> entry : potentialFLights.entrySet()) {
            if (entry.getValue()==maxValueInMap) {
                max.add(entry.getKey()); 
            }
        }
        return max;}
	}

	public HashMap<Integer, Integer> getPotentialMajors() {
		HashMap<Integer, Integer> potentialMajors = new HashMap<Integer, Integer>();
		for (int i:this.potentialFlights.keySet()) {
			if (Airline.airports.get(i) instanceof MajorAirport) {
				potentialMajors.put(i, this.potentialFlights.get(i));
			}
		}
		return potentialMajors;
	}
	public HashMap<Integer, Integer> getPotentialHubs() {
		HashMap<Integer, Integer> potentialHubs = new HashMap<Integer, Integer>();
		for (int i:this.potentialFlights.keySet()) {
			if (Airline.airports.get(i) instanceof HubAirport) {
				potentialHubs.put(i, this.potentialFlights.get(i));
			}
		}
		return potentialHubs;
	}
	public HashMap<Integer, Integer> getPotentialRegs() {
		HashMap<Integer, Integer> potentialRegs = new HashMap<Integer, Integer>();
		for (int i:this.potentialFlights.keySet()) {
			if (Airline.airports.get(i) instanceof RegionalAirport) {
				potentialRegs.put(i, this.potentialFlights.get(i));
			}
		}
		return potentialRegs;
	}

	public int getAirportType() {
		return airportType;
	}

	public ArrayList<Passenger> getPassengers() {
		return passengers;
	}

	public HashMap<Integer, Integer> getPotentialFlights() {
		return potentialFlights;
	}

	public ArrayList<Integer> getLinkedWith() {
		return linkedWith;
	}

	public HashMap<Integer, Integer> getAircrafts() {
		return aircrafts;
	}
	
	
}
