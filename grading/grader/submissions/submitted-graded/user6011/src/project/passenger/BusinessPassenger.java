package project.passenger;

import java.util.ArrayList;

import project.airport.Airport;

public class BusinessPassenger extends Passenger {
	
	public BusinessPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations);
		super.passengerType = 1;
	}
	
	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		double airportMultiplier = 0;
		switch (getDestinations().get(previousDisembarkIndex).airportType) {
		case 0:
			switch (toAirport.airportType) {
			case 0:
				airportMultiplier = 0.5;
				break;
			case 1:
				airportMultiplier = 0.7;
				break;
			case 2:
				airportMultiplier = 1;
				break;
			}
		case 1:
			switch (toAirport.airportType) {
			case 0:
				airportMultiplier = 0.6;
				break;
			case 1:
				airportMultiplier = 0.8;
				break;
			case 2:
				airportMultiplier = 1.8;
				break;
			}
		case 2:
			switch (toAirport.airportType) {
			case 0:
				airportMultiplier = 0.9;
				break;
			case 1:
				airportMultiplier = 1.6;
				break;
			case 2:
				airportMultiplier = 3;
				break;
			}
		}
		double ticketPrice = 1.2*airportMultiplier*seatMultiplier*aircraftTypeMultiplier*connectionMultiplier*toAirport.getDistance(getDestinations().get(previousDisembarkIndex));
		ticketPrice *= (5*getBaggageCount() + 100)/100;
		return ticketPrice;
	}
}
