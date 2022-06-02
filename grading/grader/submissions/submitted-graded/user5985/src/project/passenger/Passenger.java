package project.passenger;

import java.util.ArrayList;

import project.airport.Airport;

public abstract class Passenger {
	/*
	 * Passenger Type
	 * 0 Economy
	 * 1 Business
	 * 2 First
	 * 3 Luxury
	 */
	
	/*
	 * seat type
	 * 0 - economy
	 * 1 - business
	 * 2 - first-class
	 */
	
	private final long ID;
	private final double weight;
	protected int passengerType;
	private final int baggageCount;
	private int seatType;
	private double seatMultiplier;
	protected ArrayList<Airport> destinations;
	protected Airport currentAirport;
	protected int flightIndex = 0;
	protected double connectionMultipler = 1;
	
	public Passenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		this.ID = ID;
		this.weight = weight;
		this.baggageCount = baggageCount;
		this.destinations = destinations;
		this.currentAirport = destinations.get(0);
	}
	
	public double getWeight() {
		return this.weight;
	}
		
	
	public double getSeatMultiplier() {
		return seatMultiplier;
	}
	
	public int getPassengerType() {
		return this.passengerType;
	}
	
	public long getPassengerID() {
		return this.ID;
	}
	
	public boolean board(int seatType) {		
		if (seatType == 0)
			seatMultiplier = 0.6;
		else if (seatType == 1)
			seatMultiplier = 1.2;
		else {
			seatMultiplier = 3.2;
		}
		return true;
	}
	
	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		double ticketPrice = this.calculateTicketPrice(airport, aircraftTypeMultiplier);
		this.board(this.getSeatType());
		this.currentAirport = airport;
		return ticketPrice * this.seatMultiplier;
	}
	
	public boolean canDisembark(Airport airport) {
		if (this.destinations.contains(airport))
			return true;
		return false;
	}
	
	protected int getBaggageCount() {
		return baggageCount;
	}
	
	public boolean connection(int seatType) {
		if (seatType == 0)
			seatMultiplier = 0.6;
		else if (seatType == 1)
			seatMultiplier = 1.2;
		else {
			seatMultiplier = 3.2;
		}
		this.connectionMultipler = connectionMultipler * Math.pow(0.8, getFlightIndex());
		return true;
	}
	
	public Airport getCurrentAirport() {
		return this.currentAirport;
	}
	
	public void setCurrentAirport(Airport airport) {
		this.currentAirport = airport;
	}
	
	public int getFlightIndex() {
		return flightIndex;
	}

	public void setFlightIndex(int flightIndex) {
		this.flightIndex = flightIndex;
	}
	
	public ArrayList<Airport> getDestinations(){
		return this.destinations;
	}
	
	public int getSeatType() {
		return seatType;
	}

	public void setSeatType(int seatType) {
		this.seatType = seatType;
	}
	
	protected abstract double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier);
}
