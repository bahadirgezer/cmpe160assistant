package passenger;

import java.util.ArrayList;

import airline.aircraft.Aircraft;
import airport.Airport;

public abstract class Passenger {


	private final long ID;
	private final double weight;
	private final int baggageCount;
	private ArrayList<Airport> destinations;
	
	double connectionMultiplier;
	double seatMultiplier;
	double currentSeatType;
	protected Airport currentAirport;
	protected String passengerType;
	
	public long getID() {
		return ID;
	}

	public double getWeight() {
		return weight;
	}

	int getBaggageCount() {
		return baggageCount;
	}

	public ArrayList<Airport> getDestinations() {
		return destinations;
	}

	public Airport getCurrentAirport() {
		return currentAirport;
	}

	Passenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations, Airport currentAirport){
		this.weight = weight;
		this.ID = ID;
		this.baggageCount = baggageCount;
		this.destinations = destinations;
		this.currentAirport = currentAirport;
	}
	
	public boolean board(int seatType) {
		if(seatType == 1){
			connectionMultiplier = 1;
			seatMultiplier = 0.6;
			currentSeatType = 1;
			return true;
		}
		else if(seatType == 2){
			connectionMultiplier = 1;
			seatMultiplier = 1.2;
			currentSeatType = 2;
			return true;
		}
		else if(seatType == 3) {
			connectionMultiplier = 1;
			seatMultiplier = 3.2;
			currentSeatType = 3;
			return true;
		}
		else
		return false;
	}
	
	public String getPassengerType() {
		return passengerType;
	}

	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		int index = destinations.indexOf(airport);
		if(index >= 0) {
			// setting new current airport
			double ticketPrice = calculateTicketPrice(airport, aircraftTypeMultiplier);
			//connectionMultiplier = 1;
			//seatMultiplier = 0;
			//currentSeatType = 0;
			this.currentAirport = airport;
			for(int i = 0; i <= index; i++) {
				destinations.remove(0);
			}
			return ticketPrice;
		}
		else
			return 0;
	}
	
	public boolean connection(int seatType) {
		if(seatType == 1){
			connectionMultiplier = connectionMultiplier * 0.8;
			seatMultiplier = seatMultiplier * 0.6;
			currentSeatType = 1;
			return true;
		}
		else if(seatType == 2){
			connectionMultiplier = connectionMultiplier * 0.8;
			seatMultiplier = seatMultiplier * 1.2;
			currentSeatType = 2;
			return true;
		}
		else if(seatType == 3) {
			connectionMultiplier = connectionMultiplier * 0.8;
			seatMultiplier = seatMultiplier * 3.2;
			currentSeatType = 3;
			return true;
		}
		else
		return false;
	}
	
	public double getCurrentSeatType() {
		return currentSeatType;
	}

	public boolean hasDestination(Airport toAirport) {
		if(this.destinations.contains(toAirport))
			return true;
		else
			return false;
	}
	
	abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);
	
	public double calculateAirportMultiplier(Airport toAirport) {
		double airportMultiplier = 0;
		if(currentAirport.getAirportType().equals("hub")) {
			if(toAirport.getAirportType().equals("hub")) {
				airportMultiplier =  0.5;
			}
			else if(toAirport.getAirportType().equals("major")) {
				airportMultiplier =  0.7;
			}
			else if(toAirport.getAirportType().equals("regional")) {
				airportMultiplier =  1;
			}
		}
		else if(currentAirport.getAirportType().equals("major")) {
			if(toAirport.getAirportType().equals("hub")) {
				airportMultiplier =  0.6;
			}
			else if(toAirport.getAirportType().equals("major")) {
				airportMultiplier =  0.8;
			}
			else if(toAirport.getAirportType().equals("regional")) {
				airportMultiplier =  1.8;
			}
		}
		else if(currentAirport.getAirportType().equals("regional")) {
			if(toAirport.getAirportType().equals("hub")) {
				airportMultiplier =  0.9;
			}
			else if(toAirport.getAirportType().equals("major")) {
				airportMultiplier =  1.6;
			}
			else if(toAirport.getAirportType().equals("regional")) {
				airportMultiplier =  3;
			}
		}
		else {
			System.out.println("error in calculating airport multiplier in passenger.java");
		}
		return airportMultiplier;
	}
}
