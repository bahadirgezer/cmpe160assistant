package project.interfaces;

import project.airport.Airport;

/**
 * @author Emre KILIC
 *
 */
public interface AircraftInterface {
    double fly(Airport toAirport);
    double addFuel(double fuel);
    double fillUp();
    boolean hasFuel(double fuel);
    double getWeightRatio();
}
