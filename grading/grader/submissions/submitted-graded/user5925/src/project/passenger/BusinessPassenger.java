package project.passenger;

import java.util.ArrayList;

import project.airport.Airport;

public class BusinessPassenger extends Passenger{
	
	Airport toAirport;
	double aircraftTypeMultiplier;
	double passengerMultiplier = 1.2;
	
	
	public BusinessPassenger(int type, int ID, double weight, int baggageCount, ArrayList <Airport> destinations, Airport toAirport, double aircraftTypeMultiplier) {
		super(type,ID,weight,baggageCount,destinations);
		this.toAirport = toAirport;
		this.aircraftTypeMultiplier = aircraftTypeMultiplier;
	}

	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		
		double airportMultiplier = getAirportMultiplier();
		double distance = getDistance();
		int baggage = getBaggageCount();
		double connectionMultiplier = getConnectionMultiplier();
		double ticketPrice = distance * aircraftTypeMultiplier * connectionMultiplier * airportMultiplier * passengerMultiplier * Math.pow(105/100, baggage) * seatMultiplier;
		connectionMultiplier = 1;
				
		return ticketPrice;
		
	}
}
