package project.passenger;

import java.util.ArrayList;

import project.airport.Airport;

public class FirstClassPassenger extends Passenger {
	public FirstClassPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations, 2);
	}

	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		return oldAirport.getDistance(toAirport) * oldAirport.getAirportMultiplier(toAirport) * aircraftTypeMultiplier
				* connectionMultiplier * 3.2 * getSeatMultiplier() * (20 + getBaggageCount()) / 20;

	}

}
