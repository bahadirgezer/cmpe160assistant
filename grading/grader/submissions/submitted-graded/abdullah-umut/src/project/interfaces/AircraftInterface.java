package project.interfaces;

import project.airports.Airport;

public interface AircraftInterface {

	double fly(Airport toAirport);
	double addFuel(double fuel);
	double fillUp();
	boolean hasFuel(double fuel);
	double getWeightRatio();
	
}
