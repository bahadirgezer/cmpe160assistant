package project.passenger;

import java.util.ArrayList;

import project.airport.Airport;

public abstract class Passenger {
	
	private final long ID;
	private final double weight;
	private final int baggageCount;
	protected int seatType;
	protected int passengerType;
	private ArrayList<Airport> destinations;
	protected double seatMultiplier = 1;
	protected double connectionMultiplier = 1;
	protected int previousDisembarkIndex = 0;
	
	public Passenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		this.ID = ID;
		this.weight = weight;
		this.baggageCount = baggageCount;
		this.destinations = destinations;
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
	public double disembark(Airport airport, double aircrafttTypeMultiplier) {
		if (canDisembark(airport)) {
			double ticketPrice = calculateTicketPrice(airport, aircrafttTypeMultiplier);
			seatMultiplier = 1;
			connectionMultiplier = 1;
			for (int i = previousDisembarkIndex+1; i < destinations.size(); i++) {
				if (destinations.get(i).equals(airport)) {
					previousDisembarkIndex = i;
				}
			}
			return ticketPrice;
		}
		return 0;
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
	public boolean canDisembark(Airport airport) {
		for (int i = previousDisembarkIndex+1; i < destinations.size(); i++) {
			if (destinations.get(i).equals(airport)) {
				return true;
			}
		}
		return false;
	}
	public long getID() {
		return ID;
	}
	public double getWeight() {
		return weight;
	}
	public int getPassengerType() {
		return passengerType;
	}
	public void setSeatType(int seatType) {
		this.seatType = seatType;
	}
	public int getSeatType() {
		return seatType;
	}
	public ArrayList<Airport> getDestinations() {
		return destinations;
	}
	public int getBaggageCount() {
		return baggageCount;
	}
	protected abstract double calculateTicketPrice(Airport toAirport, double aircrafttTypeMultiplier);
}
