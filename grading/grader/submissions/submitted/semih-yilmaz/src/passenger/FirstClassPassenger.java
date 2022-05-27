package passenger;

import java.util.ArrayList;

import airline.aircraft.Aircraft;
import airport.Airport;

public class FirstClassPassenger extends Passenger {

	public FirstClassPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations, Airport currentAirport) {
		super(ID, weight, baggageCount, destinations, currentAirport);
		passengerType = "firstclass";
	}

	@Override
	double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier) {
		double baseTicketPrice, finalTicketPrice;
		final double passengerMultiplier = 3.2;
		double airportMultiplier = calculateAirportMultiplier(airport);
		double distance = currentAirport.getDistance(airport);
		baseTicketPrice = distance * aircraftTypeMultiplier * connectionMultiplier * airportMultiplier * passengerMultiplier * seatMultiplier;
		finalTicketPrice = baseTicketPrice * (1 + 0.05 * getBaggageCount());
		return finalTicketPrice;
	}

}
