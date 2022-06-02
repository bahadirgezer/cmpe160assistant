package interfaces;

import airport.Airport;

public interface AircraftInterface {

	/**
	 * 
	 * @param toAirport
	 * @return
	 */
	abstract double fly(Airport toAirport);
	
	boolean addFuel(double fuel);
	
	// Refuels the aircraft to its full capacity.
	boolean fillUp();
	
	
	// Checks if the aircraft has the specified amount of fuel.
	boolean hasFuel(double fuel);
	

	// Returns the ratio of weight to maximum weight.
	double getWeightRatio();
	
}
