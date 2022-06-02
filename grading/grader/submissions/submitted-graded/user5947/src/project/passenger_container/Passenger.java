package project.passenger_container;

import java.util.ArrayList;

import project.airline_container.aircraft_container.PassengerAircraft;
import project.airport_container.Airport;
import project.airport_container.HubAirport;
import project.airport_container.MajorAirport;
import project.airport_container.RegionalAirport;

import project.utils.Util;

public abstract class Passenger {

	protected double passengerMultiplier;
	
	protected double airportMultiplier;
	protected double seatMultiplier;
	protected double connectionMultiplier = 1;
	
	private final long ID;
	private final double weight;
	private final int baggageCount;
	private ArrayList<Airport> destinations;
	
	private Airport airport;
	private Airport lastDisembarkation;
	
	private PassengerAircraft aircraft;

	private int passengerType;
	private int seatType;

	private double loadSeatConstant;
	private double unloadSeatConstant;
	
	public Passenger(int ID, double weight, int baggageCount, ArrayList<Airport> destinations, int passengerType) {
		super();
		this.ID = ID;
		this.weight = weight;
		this.baggageCount = baggageCount;
		airport = destinations.get(0);
		lastDisembarkation = airport;
		this.destinations = destinations;
		this.passengerType = passengerType;
	}
	
	public boolean board(int seatType) {
		return true;
	}
	
	public void setAircraft(PassengerAircraft aircraft) {
		this.aircraft = aircraft;
		for (int i = passengerType; i >= 0; i--) {
			if (aircraft.getAvailableSeatList()[i] > 0) {
				seatType = i;
				setSeatConstants();
				setSeatMultiplier();
				return;
			}
		}
	}
	
	public Airport getAirport() {
		return airport;
	}

	public void setAirport(Airport airport) {
		setAirportMultiplier(airport);
		this.airport = airport;
	}

	public double findDistance(Airport toAirport) {
		return Util.findDistance(lastDisembarkation, toAirport);
	}
	
	public PassengerAircraft getAircraft() {
		return aircraft;
	}
	
	public int getSeatType() {
		return seatType;
	}
	
	public double disembark(Airport toAirport, double aircraftTypeMultiplier) {
		airport.removePassenger(this.getID());
		toAirport.addPassenger(this);
		if (!destinations.contains(toAirport)) {
			connectionMultiplier = 1;
			return 0;
		}
		double revenue = calculateTicketPrice(toAirport, aircraftTypeMultiplier);
		connectionMultiplier = 1;
		setAirport(toAirport);
		return revenue;
	}
	
	public boolean connection() {
		connectionMultiplier *= 0.8;
		return true;
	}
	
	public void setSeatConstants() {
		switch (seatType){
			case 0:
				loadSeatConstant = 1.2;
				unloadSeatConstant = 1;
				break;
			case 1:
				loadSeatConstant = 1.5;
				unloadSeatConstant = 2.8;
				break;
			case 2:
			case 3:
				loadSeatConstant = 2.5;
				unloadSeatConstant = 7.5;
		}
	}
	
	public void setSeatMultiplier() {
		switch (seatType){
			case 0:
				seatMultiplier = 0.6;
				break;
			case 1:
				seatMultiplier = 1.2;
				break;
			case 2:
			case 3:
				seatMultiplier = 3.2;
		}
	}
	
	public double getLoadSeatConstant() {
		return loadSeatConstant;
	}

	public double getUnloadSeatConstant() {
		return unloadSeatConstant;
	}
	
	public void setAirportMultiplier(Airport toAirport) {
		if(lastDisembarkation.getClass().equals(HubAirport.class)) {
			if(toAirport.getClass().equals(HubAirport.class)) {
				airportMultiplier = 0.5;
			}
			else if(toAirport.getClass().equals(MajorAirport.class)) {
				airportMultiplier = 0.7;
			}
			else if(toAirport.getClass().equals(RegionalAirport.class)) {
				airportMultiplier = 1;
			}
		}
		else if(lastDisembarkation.getClass().equals(MajorAirport.class)) {
			if(toAirport.getClass().equals(HubAirport.class)) {
				airportMultiplier = 0.6;
			}
			else if(toAirport.getClass().equals(MajorAirport.class)) {
				airportMultiplier = 0.8;
			}
			else if(toAirport.getClass().equals(RegionalAirport.class)) {
				airportMultiplier = 1.8;
			}
		}
		else if(lastDisembarkation.getClass().equals(RegionalAirport.class)) {
			if(toAirport.getClass().equals(HubAirport.class)) {
				airportMultiplier = 0.9;
			}
			else if(toAirport.getClass().equals(MajorAirport.class)) {
				airportMultiplier = 1.6;
			}
			else if(toAirport.getClass().equals(RegionalAirport.class)) {
				airportMultiplier = 3.0;
			}
		}
	}

	public void setDestinations(ArrayList<Airport> destinations) {
		this.destinations = destinations;
	}
	
	public ArrayList<Airport> getDestinations() {
		return destinations;
	}

	public int getBaggageCount() {
		return baggageCount;
	}
	
	public double getWeight() {
		return weight;
	}

	public long getID() {
		return ID;
	}
	
	public abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);

}
