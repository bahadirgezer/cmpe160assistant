package project.passengerContainer;

import java.util.ArrayList;

import project.airportContainer.Airport;

public class LuxuryPassenger extends Passenger {

	public LuxuryPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations);
		this.passengerType = 3;
		this.passengerMultiplier = 15;
	}

	@Override
	/**
	 * Calculates and returns the ticket price.
	 * distance * aircraftTypeMultiplier * connectionMultiplier * airportMultiplier * passengerMultiplier
	 * @param airport The arrival airport
	 * @param aircraftTypeMultiplier 
	 *
	 */
	protected double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier) {
		//TODO check if passengerMultiplier and seatMultiplier are the same things
		double travelDistance = airport.getDistance(this.previousAirport);
		
		//take the necessary multiplier from the array
		double airportMultiplier = airportMultipliers[previousAirport.airportType][airport.airportType];
		
		//retrieve the multiplier; the multiplier is defined at boarding or transferring
		double seatMult = this.seatMultiplier; //this is it or passengerMultiplier is constant
		
		double rawTicketPrice = travelDistance * airportMultiplier * seatMult * aircraftTypeMultiplier * this.connectionMultiplier * this.passengerMultiplier;
		double percentFifth = rawTicketPrice*5/100;
		double ticketPrice = rawTicketPrice + percentFifth*getBaggageCount();
		return ticketPrice;
	}

}
