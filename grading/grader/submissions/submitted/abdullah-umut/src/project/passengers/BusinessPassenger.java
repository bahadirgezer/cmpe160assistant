package project.passengers;

import java.util.ArrayList;

import project.airports.Airport;

public class BusinessPassenger extends Passenger {
	
	public BusinessPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations);
		passengerType = 1;
	}

	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		double airportMultiplier = 1;
		double passengerMultiplier = 1.2;
		switch (10*getPreviousAirport().getAirportType()+toAirport.getAirportType()) {
			case 00:
				airportMultiplier = 0.5;
			case 01:
				airportMultiplier = 0.7;
			case 02:
				airportMultiplier = 1;
			case 10:
				airportMultiplier = 0.6;
			case 11:
				airportMultiplier = 0.8;
			case 12:
				airportMultiplier = 1.8;
			case 20:
				airportMultiplier = 0.9;
			case 21:
				airportMultiplier = 1.6;
			case 22:
				airportMultiplier = 3;
		}
		
		return getPreviousAirport().getDistance(toAirport)*aircraftTypeMultiplier*getConnectionMultiplier()
				*airportMultiplier*passengerMultiplier*getSeatMultiplier()*(1+0.05*getBaggageCount());
	}
}
