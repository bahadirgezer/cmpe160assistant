package project.passenger;

import project.airport.Airport;

public class LuxuryPassenger extends Passenger {

	public LuxuryPassenger(long ID, double weight, int baggageCount, int currentAirportInd) {
		super(ID, weight, baggageCount, currentAirportInd);
		this.setPassengerType(4);
		// TODO Auto-generated constructor stub
	}
	
	
	public double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		
		Airport currentAirport = super.getDestinationsWithInd(getCurrentAirportInd());
		double airportMultiplier = calculateAirportMultiplier(currentAirport, toAirport);
		double passengerMultiplier = 15;
		double distance = Math.pow(Math.pow(currentAirport.getX() - toAirport.getX(), 2) + Math.pow(currentAirport.getY() - toAirport.getY(), 2), 0.5);
		//System.out.println("ap");
		//System.out.println(airportMultiplier);
		//System.out.println("dist");
		//System.out.println(distance);
		double tp = distance * aircraftTypeMultiplier * super.getConnectionMultiplier() * airportMultiplier * passengerMultiplier * (1.0 + 0.05 * getBaggageCount());
		//System.out.println("pm");
		//System.out.println(tp);
		//System.out.println(super.getConnectionMultiplier());
		return tp;
	}

}
