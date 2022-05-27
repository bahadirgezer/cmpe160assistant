package project.interfaces;

import project.airport.Airport;

public interface AircraftInterface {
	public double fly (Airport toAirport);
	public double addFuel(double fuel);
	public double fillUp();
	boolean hasFuel(double fuel);
	double getWeightRatio();
}
