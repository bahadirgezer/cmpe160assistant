package project.passenger;

import java.util.ArrayList;

import project.airport.Airport;

public class BusinessPassenger extends Passenger{
	
	double passengerMultiplier = 1.2;
	
	
	public BusinessPassenger(int type, long ID, double weight, int baggageCount, ArrayList <Airport> destinations) {
		super(type,ID,weight,baggageCount,destinations);
	}

	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		
		double airportMultiplier = getAirportMultiplier();
		double distance = getDistance();
		int baggage = getBaggageCount();
		double connectionMultiplier = getConnectionMultiplier();
		double ticketPrice = distance * aircraftTypeMultiplier * connectionMultiplier * airportMultiplier * passengerMultiplier * (1 + 0.05 *baggage) * seatMultiplier;
		connectionMultiplier = 1;
				
		return ticketPrice;
		
	}
}
