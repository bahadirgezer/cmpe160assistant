package project.passengercontainer;

import java.util.ArrayList;

import project.airportcontainer.Airport;
import project.airportcontainer.HubAirport;
import project.airportcontainer.MajorAirport;
import project.airportcontainer.RegionalAirport;

public class BusinessPassenger extends Passenger {
	

	public BusinessPassenger(long id, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(id, weight, baggageCount, destinations);
	}

	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		double airportMultiplier = 0;
		if (this.getAirport() instanceof HubAirport) {
			if (toAirport instanceof HubAirport) airportMultiplier = 0.5;
			if (toAirport instanceof MajorAirport) airportMultiplier = 0.7;
			if (toAirport instanceof RegionalAirport) airportMultiplier = 1;
		}
		if (this.getAirport() instanceof MajorAirport) {
			if (toAirport instanceof HubAirport) airportMultiplier = 0.6;
			if (toAirport instanceof MajorAirport) airportMultiplier = 0.8;
			if (toAirport instanceof RegionalAirport) airportMultiplier = 1.8;
		}
		if (this.getAirport() instanceof RegionalAirport) {
			if (toAirport instanceof HubAirport) airportMultiplier = 0.9;
			if (toAirport instanceof MajorAirport) airportMultiplier = 1.6;
			if (toAirport instanceof RegionalAirport) airportMultiplier = 3;
		}
		double passengerMultiplier = 1.2;
		double c1[] = this.getAirport().getCoordinates();
		double c2[] = toAirport.getCoordinates();
		double distance = Math.pow((Math.pow(c1[0]-c2[0], 2) + Math.pow(c2[1]-c2[1],0)), 0.5);
		double rawTicket = super.seatMultiplier*super.connectionMultiplier*distance*airportMultiplier*aircraftTypeMultiplier*passengerMultiplier;
		return rawTicket*(100+5*this.getBaggageCount())/100;
	}
}
