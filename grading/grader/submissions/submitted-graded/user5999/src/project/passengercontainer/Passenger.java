package project.passengercontainer;

import java.util.ArrayList;
import project.airportcontainer.Airport;

public abstract class Passenger {
	public Passenger(long id, double weight, int baggageCount, ArrayList<Airport> destinations) {
		this.ID=id;
		this.baggageCount=baggageCount;
		this.weight=weight;
		this.destinations = destinations;
	}
	public ArrayList<Airport> getDestinations() {
		return destinations;
	}

	protected void setDestinations(ArrayList<Airport> destinations) {
		this.destinations = destinations;
	}

	public int getSeatMultiplier() {
		return seatMultiplier;
	}

	public void setSeatMultiplier(int seatMultiplier) {
		this.seatMultiplier = seatMultiplier;
	}
	private final long ID;
	private final double weight;
	private final int baggageCount;
	private ArrayList<Airport> destinations;
	
	int seatMultiplier = 1;
	int destinationNumber = 0;
	protected int connectionMultiplier = 1;
	protected double seatConstant;
	
	public long getID() {
		return ID;
	}

	public double getSeatConstant() {
		return seatConstant;
	}

	public void setSeatConstant(double seatConstant) {
		this.seatConstant = seatConstant;
	}

	public Airport getAirport() {
		return destinations.get(destinationNumber);
	}
	
	public int getBaggageCount() {
		return baggageCount;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public boolean board(int seatType) {
		if (seatType == 0) seatMultiplier*=0.6;
		if (seatType == 1) seatMultiplier*=1.2;
		if (seatType == 2) seatMultiplier*=3.2;
		return true;
	}
	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		if (this.destinations.contains(airport) && this.destinations.indexOf(airport) > this.destinationNumber) {
			this.destinationNumber = this.destinations.indexOf(airport);
			return calculateTicketPrice(airport, aircraftTypeMultiplier);
		}
		else return 0;
	}
	public boolean connection(int seatType) {
		if (seatType == 0) seatMultiplier*=0.6;
		if (seatType == 1) seatMultiplier*=1.2;
		if (seatType == 2) seatMultiplier*=3.2;
		connectionMultiplier *= 0.8;
		return true;
		
	}
	abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);

}
