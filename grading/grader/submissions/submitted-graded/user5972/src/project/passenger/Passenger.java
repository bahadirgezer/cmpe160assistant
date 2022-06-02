package project.passenger;

import project.airport.Airport;
import java.util.ArrayList;

public abstract class Passenger {
	private final int ID;
	private final double weight;
	private final int baggageCount;
	private ArrayList<Airport> destinations; // index 0 is always is which airport he is in
	private double seatMultiplier;
	private int seatType; // economy=0, business=1, firstClass=2
	private int passengerType; // economy=0, business=1, firstClass=2, luxury=3
	protected Airport oldAirport;
	protected double connectionMultiplier;

	public Passenger(int ID, double weight, int baggageCount, ArrayList<Airport> destinations, int passengerType) {
		this.ID = ID;
		this.baggageCount = baggageCount;
		this.weight = weight;
		this.destinations = destinations;
		this.passengerType = passengerType;
		this.connectionMultiplier = 1;
	}

	public boolean board(int seatType) {
		switch (seatType) {
		case 0:
			this.seatMultiplier = 0.6;
			break;
		case 1:
			this.seatMultiplier = 1.2;
			break;
		case 2:
			this.seatMultiplier = 3.2;
			break;
		default:
			return false;
		}
		connectionMultiplier = 1;
		this.seatType = seatType;
		return true;
	}

	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		if (destinations.contains(airport)) {
			oldAirport = destinations.get(0);
			int del_old_airports = destinations.indexOf(airport);
			for (int i = 0; i < del_old_airports; i++) {
				destinations.remove(i);
			}
			double ticketPrize = calculateTicketPrice(airport, aircraftTypeMultiplier);
			seatType = 0;
			seatMultiplier = 0;
			connectionMultiplier = 0;
			return ticketPrize;
		} else {
			return 0;
		}
	}

	public boolean connection(int seatType) {
		switch (seatType) {
		case 0:
			seatMultiplier *= 0.6;
			break;
		case 1:
			seatMultiplier *= 1.2;
			break;
		case 2:
			seatMultiplier *= 3.2;
			break;
		default:
			return false;
		}
		connectionMultiplier *= 0.8;
		return true;
	}

	abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);

	protected Airport getCurrentAirport() {
		return destinations.get(0);
	}

	protected int getBaggageCount() {
		return baggageCount;
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

	public boolean equals(Passenger otherPassenger) {
		if (otherPassenger.ID == this.ID) {
			return true;
		} else {
			return false;
		}
	}	
	protected double getSeatMultiplier() {
		return seatMultiplier;
	}
	
	public int getID() {
		return ID;
	}
	
	public ArrayList<Airport> getDestinations() {
		return destinations;
	}
}
