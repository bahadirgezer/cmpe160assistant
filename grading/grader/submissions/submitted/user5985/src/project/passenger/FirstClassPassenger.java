package project.passenger;

import java.util.ArrayList;


import project.airport.Airport;
import project.airport.HubAirport;
import project.airport.MajorAirport;

public class FirstClassPassenger extends Passenger{
	public FirstClassPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations);
		this.passengerType = 2;
	}

	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		double passengerMultiplier = 3.2;
		double airportMultiplier;
		if (this.destinations.get(this.getFlightIndex()).getClass() == HubAirport.class) { 
			if (toAirport.getClass() == HubAirport.class)
				airportMultiplier = 0.5;
			else if (toAirport.getClass() == MajorAirport.class)
				airportMultiplier = 0.7;
			else
				airportMultiplier = 1.0;
		}
		else if (this.destinations.get(this.getFlightIndex()).getClass() == MajorAirport.class) {
			if (toAirport.getClass() == HubAirport.class)
				airportMultiplier = 0.6;
			else if (toAirport.getClass() == MajorAirport.class)
				airportMultiplier = 0.8;
			else
				airportMultiplier = 1.8;
		}
		else {
			if (toAirport.getClass() == HubAirport.class)
				airportMultiplier = 0.9;
			else if (toAirport.getClass() == MajorAirport.class)
				airportMultiplier = 1.6;
			else
				airportMultiplier = 3.0;
		}
		double distance = this.destinations.get(this.getFlightIndex()).getDistance(toAirport);
		double ticketPrice = aircraftTypeMultiplier * distance * airportMultiplier * passengerMultiplier * this.connectionMultipler;
		return ticketPrice + ticketPrice * 0.05 * this.getBaggageCount();
	}
}
