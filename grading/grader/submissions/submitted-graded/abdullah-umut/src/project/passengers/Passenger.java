package project.passengers;

import java.util.ArrayList;

import project.airports.Airport;

public abstract class Passenger {
	
	private final long ID;
	private final double weight;
	private final int baggageCount;
	private ArrayList<Airport> destinations;
	private double seatMultiplier;
	private double connectionMultiplier;
	private int prevDest;
	private int seatType;
	protected int passengerType;
	

	Passenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations){
		this.ID = ID;
		this.weight = weight;
		this.baggageCount = baggageCount;
		this.destinations = destinations;
		
	}
	
	public ArrayList<Integer> destie(){
		ArrayList<Integer> a = new ArrayList<Integer>();
		for (Airport i : destinations) {
			a.add(i.getID());
		}
		return a;
	}
	
	public boolean board(int seatType) {
		connectionMultiplier = 1;
		this.seatType = seatType;
		switch (seatType) {
			case 0:
				seatMultiplier = 0.6;
			case 1:
				seatMultiplier = 1.2;
			case 2:
				seatMultiplier = 3.2;
		}
		return true;
	}
	
	public boolean canDisembark(Airport airport) {
		return (disembarkIndex(airport) != -1);
	}
	
	private int disembarkIndex(Airport airport) {
		for (int i = prevDest; i < destinations.size(); i++) {
			if (destinations.get(i).getID() == airport.getID()) {
				return i;
			}
		}
		return -1;
	}
	
	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		int i = disembarkIndex(airport);
		if (i != -1) {
			double fakePrice = calculateTicketPrice(airport, aircraftTypeMultiplier);
			prevDest = i;
			return fakePrice;
		}
		return 0;
	}
	
	public boolean connection(int seatType) {
		connectionMultiplier *= 0.8;
		this.seatType = seatType;
		switch (seatType) {
			case 0:
				seatMultiplier *= 0.6;
			case 1:
				seatMultiplier *= 1.2;
			case 2:
				seatMultiplier *= 3.2;
		}
		return true;
	}
	
	abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);

	protected double getSeatMultiplier() {
		return seatMultiplier;
	}

	protected double getConnectionMultiplier() {
		return connectionMultiplier;
	}

	protected int getBaggageCount() {
		return baggageCount;
	}
	
	public Airport getPreviousAirport() {
		return destinations.get(prevDest);
	}

	public void setSeatType(int seatType) {
		this.seatType = seatType;
	}
	public int getSeatType() {
		return seatType;
	}

	public double getWeight() {
		return weight;
	}

	public int getPassengerType() {
		return passengerType;
	}

	public long getID() {
		return ID;
	}
}
