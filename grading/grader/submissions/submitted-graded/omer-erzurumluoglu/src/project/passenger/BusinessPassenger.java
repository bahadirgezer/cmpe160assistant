package project.passenger;

import java.util.ArrayList;

import project.airport.Airport;

public class BusinessPassenger extends Passenger {

	public BusinessPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations, 1);
	}

	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		return oldAirport.getDistance(toAirport) * oldAirport.getAirportMultiplier(toAirport) * aircraftTypeMultiplier
				* connectionMultiplier * 1.2 * getSeatMultiplier() * (20 + getBaggageCount()) / 20;
	}

}
