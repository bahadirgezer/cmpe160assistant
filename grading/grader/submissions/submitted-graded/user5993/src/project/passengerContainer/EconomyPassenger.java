package project.passengerContainer;

import java.util.ArrayList;

import project.airlineContainer.aircraftContainer.concreteContainer.JetPassengerAircraft;
import project.airlineContainer.aircraftContainer.concreteContainer.PropPassengerAircraft;
import project.airlineContainer.aircraftContainer.concreteContainer.RapidPassengerAircraft;
import project.airlineContainer.aircraftContainer.concreteContainer.WidebodyPassengerAircraft;
import project.airportContainer.Airport;

public class EconomyPassenger extends Passenger {
	final double passengerMultiplier=0.6;
	public EconomyPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations);
		// TODO Auto-generated constructor stub
	}
	
	double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		Airport lastAirport=this.lastAirport;
		
		double distance=lastAirport.getDistance(toAirport);
		
		double airportMultiplier=this.calculateAirportMultiplier(toAirport);
		int baggageCount=this.getBaggageCount();
		double connectionMultiplier=this.connectionMultiplier;
		
	
		double price= distance*aircraftTypeMultiplier*connectionMultiplier*airportMultiplier*passengerMultiplier*(1+0.05*baggageCount);
		
		return price;

	}



}
