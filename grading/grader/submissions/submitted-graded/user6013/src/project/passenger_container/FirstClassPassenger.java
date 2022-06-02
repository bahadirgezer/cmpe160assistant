package project.passenger_container;

import java.util.ArrayList;

import project.airport_container.Airport;

public class FirstClassPassenger extends Passenger {
	public FirstClassPassenger(int p,long a, double b, int c, ArrayList<Airport> d) {
		super(p,a, b, c, d);
		// TODO Auto-generated constructor stub
	}

	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		return (aircraftTypeMultiplier*Airport.getdistance(getPrevlocation(), toAirport)*3.2*super.calculateAirportMultiplier(toAirport)*getConnectionMultiplier())*(1+getBaggageCount()/20);
	}
}
