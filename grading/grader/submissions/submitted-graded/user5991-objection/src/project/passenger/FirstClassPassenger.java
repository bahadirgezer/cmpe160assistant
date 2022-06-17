package project.passenger;
import java.util.ArrayList;

import project.airline.aircraft.Aircraft;
import project.airport.Airport;
import project.airport.MajorAirport;
import project.airport.RegionalAirport;
public class FirstClassPassenger extends Passenger {
	
	
	
	public FirstClassPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations);
	}

	
	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		int prevairport = 0;
		int nextairport = 0;
		if(currentAirport instanceof MajorAirport) {prevairport=1;}
		else if(currentAirport instanceof RegionalAirport) {prevairport=2;}
		if(toAirport instanceof MajorAirport) {nextairport = 1;}
		else if(toAirport instanceof RegionalAirport) {nextairport = 2;}
		double airportMultiplier=1;
		switch(prevairport) {
		case 0:
			if(nextairport==0) {airportMultiplier=0.5;}
			else if(nextairport==1) {airportMultiplier=0.7;}
			else {airportMultiplier=1.0;}
			break;
		case 1:
			if(nextairport==0) {airportMultiplier=0.6;}
			else if(nextairport==1) {airportMultiplier=0.8;}
			else {airportMultiplier=1.8;}
			break;
		case 2:
			if(nextairport==0) {airportMultiplier=0.9;}
			else if(nextairport==1) {airportMultiplier=1.6;}
			else {airportMultiplier=3.0;}
			break;
		}
		double passengerMultiplier = 3.2;
		double distance = currentAirport.getDistance(toAirport);
		double ticketprice = distance*aircraftTypeMultiplier*getConnectionMultiplier()*airportMultiplier*passengerMultiplier*getSeatMultiplier();
		ticketprice += ticketprice*getBaggageCount()*0.05;
		return ticketprice;
	}
}
