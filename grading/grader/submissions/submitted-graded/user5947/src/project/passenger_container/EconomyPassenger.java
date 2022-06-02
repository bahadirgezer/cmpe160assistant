package project.passenger_container;

import java.util.ArrayList;

import project.airport_container.Airport;

public class EconomyPassenger extends Passenger {
			
	public EconomyPassenger(int ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations, 0);
		passengerMultiplier = 0.6;
	}
	
	@Override
	public double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		return findDistance(toAirport) * aircraftTypeMultiplier * 
				connectionMultiplier * airportMultiplier * passengerMultiplier * (1.0 + getBaggageCount() * 0.5);
	}
}
