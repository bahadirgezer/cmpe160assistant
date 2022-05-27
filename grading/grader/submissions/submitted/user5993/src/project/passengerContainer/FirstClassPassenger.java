package project.passengerContainer;

import java.util.ArrayList;

import project.airportContainer.Airport;

public class FirstClassPassenger extends Passenger {
	final double passengerMultiplier=3.2;
	public FirstClassPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations);
		// TODO Auto-generated constructor stub
	}

	@Override
	double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		Airport lastAirport=this.lastAirport;
		double distance=lastAirport.getDistance(toAirport);
		double airportMultiplier=this.calculateAirportMultiplier(toAirport);
		int baggageCount=this.getBaggageCount();
		double connectionMultiplier=this.connectionMultiplier;
		double price= distance*aircraftTypeMultiplier*connectionMultiplier*airportMultiplier*passengerMultiplier*(1+0.05*baggageCount);
		return price;
		// TODO Auto-generated method stub
	}


}
