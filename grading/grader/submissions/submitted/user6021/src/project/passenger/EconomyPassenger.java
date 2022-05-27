package project.passenger;
import java.util.ArrayList;

import project.airport.Airport;
import project.airline.*;

public class EconomyPassenger extends Passenger{
	private double passengerMultiplier = 0.6;
	
	public EconomyPassenger(Long ID, double weight, int baggageCount, ArrayList<Integer> destinations) {
		super(ID, weight, baggageCount, destinations, 0);
	}

	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		Airport myCurrentAirport = Airline.airports.get(this.currAirportIndex);
		double airportMultiplier = this.getAirportMultiplier(myCurrentAirport, toAirport);
		double distance = myCurrentAirport.getDistance(toAirport);
		double ticketPrice = distance*aircraftTypeMultiplier*this.connectionMultiplier*airportMultiplier*this.passengerMultiplier*((double)(this.getBaggageCount())*0.05+1)*this.seatMultiplier;
		return ticketPrice;}



}
