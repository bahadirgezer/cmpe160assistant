package project.interfaces;

import project.airport.Airport;

public interface AircraftInterface {
    double fly(Airport toAirport);
    boolean addFuel(double fuel);
    boolean fillUp();
    double getMaxFuelFillable();
    boolean hasFuel(double fuel);
    double getWeightRatio();
}
