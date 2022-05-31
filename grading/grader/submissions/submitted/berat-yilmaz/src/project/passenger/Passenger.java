package project.passenger;

import java.util.ArrayList;

import project.airport.Airport;

public abstract class Passenger {
	private final long ID;
	private final double weight;
	private final int baggageCount;
	private ArrayList<Airport> destinations;
	
	Airport lastDisembarked;
	
	protected double seatMultiplier;
	protected double connectionMultiplier;
	protected int currentSeatType;
	protected int passengerType;
	
	public Passenger(int passengerType, long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		this.passengerType = passengerType;
		this.ID = ID;
		this.weight = weight;
		this.baggageCount = baggageCount;
		this.destinations = destinations;
		this.lastDisembarked = destinations.get(0);
		this.lastDisembarked.addPassenger(this);
	}
	
	
	public boolean board(int seatType) {
		if (seatType == 0) {
			seatMultiplier = 0.6;
			currentSeatType = 0;
		}
		else if (seatType == 1) {
			seatMultiplier = 1.2;
			currentSeatType = 1;
		}
		else if (seatType == 2) {
			seatMultiplier = 3.2;
			currentSeatType = 2;
		}
		connectionMultiplier = 1;
		return true;
	}
	
	
	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		if (!(destinations.contains(airport))) {
			return 0;
		}
		double ticketPrice = calculateTicketPrice(airport, aircraftTypeMultiplier);
		lastDisembarked = airport;
		destinations.remove(airport);
		seatMultiplier = 0;
		connectionMultiplier = 0;
		currentSeatType = 0;
		airport.addPassenger(this);
		return ticketPrice;
	}
	
	
	public boolean connection(int seatType) {
		connectionMultiplier *= 0.8;
		
		if (seatType == 0) {
			seatMultiplier *= 0.6;
			currentSeatType = 0;
		}
		else if (seatType == 1) {
			seatMultiplier *= 1.2;
			currentSeatType = 1;
		}
		else if (seatType == 2) {
			seatMultiplier *= 3.2;
			currentSeatType = 2;
		}
		return true;
	}
	public int getBaggageCount() {
		return baggageCount;
	}
	
	public int getCurrentSeatType() {
		return currentSeatType;
	}
	
	public int getPassengerType() {
		return passengerType;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public long getID() {
		return ID;
	}
	
	public ArrayList<Airport> getDestinations() {
		return destinations;
	}
	
	public boolean canDisembark(Airport toAirport) {
	if (destinations.contains(toAirport)) {
		int lastDisembarkedIndex = destinations.indexOf(lastDisembarked);    
		int currentIndex = destinations.indexOf(toAirport);
		if (currentIndex > lastDisembarkedIndex) {
			return true;
		}
		else {
			return false;
		}
	}
	else {
		return false;
	}
	}

	abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);
}
