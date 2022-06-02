package project.passengercontainer;

import java.util.ArrayList;

import project.airlinecontainer.aircraftcontainer.Aircraft;
import project.airportcontainer.Airport;

public abstract class Passenger {
	private final long ID;
	private final double weight;
	private final int baggageCount;
	public Airport boarded;
	private Airport finalDisembarked;
	public int classNumber;
	public double seatMultiplier;
	public double connectionMultiplier = 1;
	public int lastSeatType;
	private ArrayList<Airport> destinations = new ArrayList<Airport>();
	
	public Passenger(int passengerType, long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		classNumber = passengerType;
		this.ID = ID;
		this.weight = weight;
		this.baggageCount = baggageCount;
		this.destinations = destinations;
	}
	
	public long getID() {
		return ID;
	}
	public int getBaggageCount() {
		return baggageCount;
	}
	
	public ArrayList<Airport> getDestinations() {
		return destinations;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public int getClassNumber() {
		return classNumber;
	}
	
	public boolean board(int seatType) {
		if (seatType == 0) {
			seatMultiplier = 0.6;
		} else if (seatType == 1) {
			seatMultiplier = 1.2;
		} else {
			seatMultiplier = 3.2;
		}
		return true;
	}
	
	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		if (destinations.contains(airport)) {
			finalDisembarked = airport;
			destinations.remove(airport);
			seatMultiplier = 1;
			connectionMultiplier = 1;
			return calculateTicketPrice(airport, aircraftTypeMultiplier);
		} else {
			return 0;
		}
	}
	
	public boolean connection(int seatType) {
		if (seatType == 0) {
			seatMultiplier *= 0.6;
		} else if (seatType == 1) {
			seatMultiplier *= 1.2;
		} else {
			seatMultiplier *= 3.2;
		}
		connectionMultiplier *= 0.8;
		return true;
	}
	
	abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);	
}
