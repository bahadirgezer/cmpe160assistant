package project.PassengerContainer;

import project.airportContainer.Airport;

public class FirstClassPassenger extends Passenger {

	public FirstClassPassenger(int ID, double weight, int baggageCount, int currentAirportInd) {
		super(ID, weight, baggageCount, currentAirportInd);
		this.setPassengerType(3);
		// TODO Auto-generated constructor stub
	}
	
	
	public double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		Airport currentAirport = super.getDestinationsWithInd(getCurrentAirportInd());
		double airportMultiplier = calculateAirportMultiplier(currentAirport, toAirport);
		double passengerMultiplier = 3.2;
		double distance = Math.pow(Math.abs(currentAirport.getX() * currentAirport.getX() + currentAirport.getY() * currentAirport.getY() - toAirport.getY() * toAirport.getY() - toAirport.getX() * toAirport.getX()), 0.5);
		return distance * aircraftTypeMultiplier * super.getConnectionMultiplier() * airportMultiplier * passengerMultiplier * ((100.0 +  5.0 * getBaggageCount())/100.0);
	}
}
