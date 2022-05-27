package project.passenger_container;

import java.util.ArrayList;

import project.airport_container.Airport;

public class BusinessPassenger extends Passenger {
	
	public BusinessPassenger(int ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations, 1);
		passengerMultiplier = 1.2;
	}

	@Override
	public double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		return findDistance(toAirport) * aircraftTypeMultiplier *
				connectionMultiplier * airportMultiplier * passengerMultiplier * (1.0 + getBaggageCount() * 0.5);
	}

}
