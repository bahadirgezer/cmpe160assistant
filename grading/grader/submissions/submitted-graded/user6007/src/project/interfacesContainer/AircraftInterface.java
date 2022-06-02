package project.interfacesContainer;

import project.airportContainer.Airport;

public interface AircraftInterface {
	double fly(Airport toAirport);
	double addFuel(double fuel);
	boolean canAddFuel(double fuel);
	double fillUp();
	boolean hasFuel();
	double getWeightRatio();
	
}
