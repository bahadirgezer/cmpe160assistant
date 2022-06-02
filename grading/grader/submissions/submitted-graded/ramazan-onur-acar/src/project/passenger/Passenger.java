package project.passenger;

import java.util.ArrayList;

import project.airline.aircraft.Aircraft;
import project.airport.Airport;

public abstract class Passenger {
	
	private double connectionMultiplier = 1;
	public int type;
	public Airport initial_airport;
	public Airport last_disembarkation;
	double aircraftTypeMultiplier;
	private final long ID;
	private final double weight;
	private final int baggageCount;
	private ArrayList <Airport> destinations;
	double airportMultiplier;
	double seatMultiplier;
	Aircraft current_airplane;
	public int saved_seat_type;
	
	public Passenger(int type, long ID, double weight, int baggageCount, ArrayList <Airport> destinations) {
		this.type = type;
		this.ID = ID;
		this.weight = weight;
		this.baggageCount = baggageCount;
		this.destinations = destinations;
		initial_airport = destinations.get(0);
	}
	public ArrayList<Airport> getDestinations() {
		return destinations;
	}
	public int getDestinationSize() {
		return destinations.size();
	}
	
	public long getID() {
		return ID;
	}
	
	public int getBaggageCount() {
		return baggageCount;
	}
	
	public double getWeight() {
		return weight;
	}
	

	
	public double getDistance() {
		double xs = Math.pow(initial_airport.getX() - last_disembarkation.getX(),2);
		double ys = Math.pow(initial_airport.getY() - last_disembarkation.getY(),2);
		double distance = Math.sqrt(xs+ys);
		return distance;
	}
	
	public double getAirportMultiplier() {
		if (initial_airport.airport_type == 1) {
			if (last_disembarkation.airport_type == 1) {
				airportMultiplier = 0.5;
			}
			else if (last_disembarkation.airport_type == 2) {
				airportMultiplier = 0.7;
			}
			else {
				airportMultiplier = 1;
			}
		}
		else if (initial_airport.airport_type == 2) {
			if (last_disembarkation.airport_type == 1) {
				airportMultiplier = 0.6;
			}
			else if (last_disembarkation.airport_type == 2) {
				airportMultiplier = 0.8;
			}
			else {
				airportMultiplier = 1.8;
			}
			
		}
		else {
			if (last_disembarkation.airport_type == 1) {
				airportMultiplier = 0.9;
			}
			else if (last_disembarkation.airport_type == 2) {
				airportMultiplier = 1.6;
			}
			else {
				airportMultiplier = 3;
			}
			
		}
		initial_airport = last_disembarkation;
		return airportMultiplier;
	}
	
	public boolean canBoard(int seatType) {
		if (seatType <= type) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public boolean board(int seatType) {
		
		if (seatType == 1) {
			seatMultiplier = 0.6;
			saved_seat_type = 1;
		}
		else if (seatType == 2) {
			seatMultiplier = 1.2;
			saved_seat_type = 2;
		}
		
		else if (seatType == 3) {
			seatMultiplier = 3.2;
			saved_seat_type = 3;
			
		}
		return true;

	}
	
	public double disembark(Airport toAirport, double aircraftTypeMultiplier) {
		last_disembarkation = toAirport;
		if (destinations.contains(toAirport)) {
			int ind = destinations.indexOf(toAirport);
			for (int i = 0; i<ind+1; i++) {
				destinations.remove(0);
			}
			return calculateTicketPrice(toAirport, aircraftTypeMultiplier);
		}
		else {
			return 0;
		}
			
		
		
	}

	public boolean connection(int seatType) {
		if (canBoard(seatType)) {
			connectionMultiplier = connectionMultiplier * (0.8);
			if (seatType == 1) {
				seatMultiplier *= 0.6;
			}
			else if (seatType == 2) {
				seatMultiplier *= 1.2;
			}
		
			else if (seatType == 3) {
				seatMultiplier *= 3.2;
			
			}
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public double getConnectionMultiplier() {
		return connectionMultiplier;
	}
	
	abstract double calculateTicketPrice(Airport toAirport,double aircraftTypeMultiplier);
	
}

