package project.passenger;

import java.util.ArrayList;

import project.airport.Airport;

public class LuxuryPassenger extends Passenger {
	public LuxuryPassenger(int ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations, 3);
	}

	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		return oldAirport.getDistance(toAirport) * oldAirport.getAirportMultiplier(toAirport) * aircraftTypeMultiplier
				* connectionMultiplier * 15 * getSeatMultiplier() * (20 + getBaggageCount()) / 20;

	}

}
