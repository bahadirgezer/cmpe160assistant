package project.passenger;

import java.util.ArrayList;

import project.airport.Airport;

public class EconomyPassenger extends Passenger {
	public EconomyPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations, 0);
	}

	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		return oldAirport.getDistance(toAirport) * oldAirport.getAirportMultiplier(toAirport) * aircraftTypeMultiplier
				* connectionMultiplier * 0.6 * getSeatMultiplier() * (20 + getBaggageCount()) / 20;

	}

}
