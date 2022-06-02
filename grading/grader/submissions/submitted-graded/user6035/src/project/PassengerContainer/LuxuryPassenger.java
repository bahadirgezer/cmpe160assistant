package project.PassengerContainer;

import project.airportContainer.Airport;

public class LuxuryPassenger extends Passenger {

	public LuxuryPassenger(int ID, double weight, int baggageCount, int currentAirportInd) {
		super(ID, weight, baggageCount, currentAirportInd);
		this.setPassengerType(4);
		// TODO Auto-generated constructor stub
	}
	
	
	public double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		Airport currentAirport = super.getDestinationsWithInd(getCurrentAirportInd());
		double airportMultiplier = calculateAirportMultiplier(currentAirport, toAirport);
		double passengerMultiplier = 15;
		double distance = Math.pow(Math.abs(currentAirport.getX() * currentAirport.getX() + currentAirport.getY() * currentAirport.getY() - toAirport.getY() * toAirport.getY() - toAirport.getX() * toAirport.getX()), 0.5);
		return distance * aircraftTypeMultiplier * super.getConnectionMultiplier() * airportMultiplier * passengerMultiplier * Math.pow(1.05, getBaggageCount());
	}

}
