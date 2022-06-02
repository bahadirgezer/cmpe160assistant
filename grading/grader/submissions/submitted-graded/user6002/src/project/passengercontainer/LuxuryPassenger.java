package project.passengercontainer;

import java.util.ArrayList;

import project.airportcontainer.Airport;

public class LuxuryPassenger extends Passenger{
	public final double passengerMultiplier = 15;
	
	public LuxuryPassenger(int passengerType, long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(passengerType, ID, weight, baggageCount, destinations);
		//classNumber = 3;
	}
	
	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		double airportMultiplier;
		if (boarded.airportType == 0) {
			if (toAirport.airportType == 0) {
				airportMultiplier = 0.5;
			} else if (toAirport.airportType == 1) {
				airportMultiplier = 0.7;
			} else {
				airportMultiplier = 1;
			}
		} else if (boarded.airportType == 1) {
			if (toAirport.airportType == 0) {
				airportMultiplier = 0.6;
			} else if (toAirport.airportType == 1) {
				airportMultiplier = 0.8;
			} else {
				airportMultiplier = 1.8;
			}
		} else {
			if (toAirport.airportType == 0) {
				airportMultiplier = 0.9;
			} else if (toAirport.airportType == 1) {
				airportMultiplier = 1.6;
			} else {
				airportMultiplier = 3.0;
			}
		}
		double ticketPrice = this.boarded.getDistance(toAirport) * aircraftTypeMultiplier * connectionMultiplier * airportMultiplier * passengerMultiplier * seatMultiplier;
		int increment = 5 * getBaggageCount();
		return ticketPrice * (100 + increment) / 100;
	}
}
