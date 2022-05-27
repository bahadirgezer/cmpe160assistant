package project.passenger;

import java.util.ArrayList;

import project.airport.Airport;
import project.airport.HubAirport;
import project.airport.MajorAirport;
import project.airport.RegionalAirport;

public class EconomyPassenger extends Passenger {
	private final double passengerMultiplier = 0.6;
	public EconomyPassenger(long ID, double weight, int baggageCount, ArrayList<Integer> destinations,Airport currentAirport) {
		super(ID, weight, baggageCount, destinations,currentAirport);
	}
	@Override
	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		double airportMultiplier = 1;
		if (currentAirport instanceof HubAirport) {
			if (toAirport instanceof HubAirport) {
				airportMultiplier = 0.5;
			}
			if (toAirport instanceof MajorAirport) {
				airportMultiplier = 0.7;
			}
			if (toAirport instanceof RegionalAirport) {
				airportMultiplier = 1;
			}
		}
		if (currentAirport instanceof MajorAirport) {
			if (toAirport instanceof HubAirport) {
				airportMultiplier = 0.6;
			}
			if (toAirport instanceof MajorAirport) {
				airportMultiplier = 0.8;
			}
			if (toAirport instanceof RegionalAirport) {
				airportMultiplier = 1.8;
			}
		}
		if (currentAirport instanceof RegionalAirport) {
			if (toAirport instanceof HubAirport) {
				airportMultiplier = 0.9;
			}
			if (toAirport instanceof MajorAirport) {
				airportMultiplier = 1.6;
			}
			if (toAirport instanceof RegionalAirport) {
				airportMultiplier = 3;
			}
		}
		double price = passengerMultiplier * currentAirport.getDistance(toAirport) * aircraftTypeMultiplier * getConnectionMultiplier() * airportMultiplier * getSeatMultiplier();
		double baggageMultiplier = 1 + (0.05 * getBaggageCount());
		return price * baggageMultiplier;
	}
}
