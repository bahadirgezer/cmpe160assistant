package project.interfacescontainer;

import project.airportcontainer.Airport;

public interface AircraftInterface {
	double fly(Airport toAirport);
	boolean addFuel(double fuel);
	boolean fulle();
	boolean hasFuel(double fuel);
	double getWeightRatio();
	
}
