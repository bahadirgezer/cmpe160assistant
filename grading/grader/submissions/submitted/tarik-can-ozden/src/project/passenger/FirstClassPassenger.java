package project.passenger;

import project.airport.Airport;

public class FirstClassPassenger extends Passenger {

	public FirstClassPassenger(long ID, double weight, int baggageCount, int currentAirportInd) {
		super(ID, weight, baggageCount, currentAirportInd);
		this.setPassengerType(3);
		// TODO Auto-generated constructor stub
	}
	
	
	public double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		Airport currentAirport = super.getDestinationsWithInd(getCurrentAirportInd());
		double airportMultiplier = calculateAirportMultiplier(currentAirport, toAirport);
		double passengerMultiplier = 3.2;
		double distance = Math.pow(Math.pow(currentAirport.getX() - toAirport.getX(), 2) + Math.pow(currentAirport.getY() - toAirport.getY(), 2), 0.5);
		double tp = distance * aircraftTypeMultiplier * super.getConnectionMultiplier() * airportMultiplier * passengerMultiplier * (1.0 + 0.05 * getBaggageCount());
		return tp;
	}
}
