package project.passenger;

import java.util.ArrayList;

import project.airport.Airport;

public class FirstClassPassenger extends Passenger{
	
	public FirstClassPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(2, ID, weight, baggageCount, destinations);
	}

	@Override
	double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier) {
		double airportMultiplier = 0;
		switch (lastDisembarked.getAirportType()) {
		case 0: //hub
			switch (airport.getAirportType()) {
			case 0: //hub
				airportMultiplier = 0.5;
				break;
			case 1: //major
				airportMultiplier = 0.7;
				break;
			case 2: //regional
				airportMultiplier = 1;
				break;
			}
		case 1: //major
			switch (airport.getAirportType()) {
			case 0: //hub
				airportMultiplier = 0.6;
				break;
			case 1: //major
				airportMultiplier = 0.8;
				break;
			case 2: //regional
				airportMultiplier = 1.8;
				break;
			}
		case 2: //regional
			switch (airport.getAirportType()) {
			case 0: //hub
				airportMultiplier = 0.9;
				break;
			case 1: //major
				airportMultiplier = 1.6;
				break;
			case 2: //regional
				airportMultiplier = 3.0;
				break;
			}
		}
		double passengerMultiplier = 3.2; 
		double ticketPrice = lastDisembarked.getDistance(airport) * aircraftTypeMultiplier * connectionMultiplier * airportMultiplier * passengerMultiplier * seatMultiplier;
		ticketPrice = ticketPrice * (Math.pow(1.05, this.getBaggageCount()));
		return ticketPrice;
	}
}
