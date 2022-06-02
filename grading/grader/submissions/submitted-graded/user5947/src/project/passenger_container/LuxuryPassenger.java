package project.passenger_container;

import java.util.ArrayList;

import project.airport_container.Airport;

public class LuxuryPassenger extends Passenger {
	
	public LuxuryPassenger(int ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations, 2);
		passengerMultiplier = 15;
	}

	@Override
	public double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		return findDistance(toAirport) * aircraftTypeMultiplier * 
				connectionMultiplier * airportMultiplier * passengerMultiplier * (1.0 + getBaggageCount() * 0.5);
	}

}
