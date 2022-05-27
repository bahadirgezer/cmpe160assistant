package project.passengercontainer;

import java.util.ArrayList;

import project.airlinecontainer.aircraftcontainer.Aircraft;
import project.airportcontainer.Airport;
import project.airportcontainer.HubAirport;
import project.airportcontainer.MajorAirport;
import project.airportcontainer.RegionalAirport;

public class LuxuryPassenger extends Passenger{

public LuxuryPassenger(long id, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(id, weight, baggageCount, destinations);
		// TODO Auto-generated constructor stub
	}

	private double airport_multiplier;
	
	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		if (last_disembarked_airport instanceof HubAirport) {
			if (toAirport instanceof HubAirport) {
				airport_multiplier=0.5;
			}
			if (toAirport instanceof MajorAirport) {
				airport_multiplier=0.7;
			}
			if (toAirport instanceof RegionalAirport) {
				airport_multiplier=1;
			}	
		}
		if (last_disembarked_airport instanceof MajorAirport) {
			if (toAirport instanceof HubAirport) {
				airport_multiplier=0.6;
			}
			if (toAirport instanceof MajorAirport) {
				airport_multiplier=0.8;
			}
			if (toAirport instanceof RegionalAirport) {
				airport_multiplier=1.8;
			}	
		}
		if (last_disembarked_airport instanceof RegionalAirport) {
			if (toAirport instanceof HubAirport) {
				airport_multiplier=0.9;
			}
			if (toAirport instanceof MajorAirport) {
				airport_multiplier=1.6;
			}
			if (toAirport instanceof RegionalAirport) {
				airport_multiplier=3.0;
			}
		}
		double distancex=Math.pow(toAirport.get_coordinatesx()-last_disembarked_airport.get_coordinatesx(),2);
		double distancey=Math.pow(toAirport.get_coordinatesy()-last_disembarked_airport.get_coordinatesy(),2);
		double distance=Math.sqrt(distancex+distancey);
		return distance*15*airport_multiplier*aircraftTypeMultiplier*connectionMultiplier*seatMultiplier*(1+0.05*getbaggageCount());			
	}
}
