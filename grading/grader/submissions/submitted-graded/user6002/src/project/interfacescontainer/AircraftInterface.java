package project.interfacescontainer;

import project.airportcontainer.Airport;

public interface AircraftInterface {
	public double fly(Airport toAirport);
	boolean addFuel(double fuel);
	boolean fulle(); //Refuel to full capacity
	boolean hasFuel(double fuel); //Check if the aircraft has a specified amount of fuel.
	double getWeightRatio(); //Return the ratio of weight to maximum weight.
}
