package project.interfacescontainer;

import project.airportcontainer.Airport;

public interface AircraftInterface {
	double fly(Airport toAirport);
	boolean addFuel(double fuel);
	boolean fillUp(); //refuels to full capacity
	boolean hasFuel(double fuel);
	double getWeightRatio(); //can be used for optimization

}
