package project.interfaces;
import project.airport.Airport;

public interface AircraftInterface {
	public double fly(Airport toAirport);
	double addFuel(double fuel);
	double fillUp();
	boolean hasFuel(double fuel);
	double getWeightRatio();
}
