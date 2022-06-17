package project.interfaces;

import project.airport.Airport;

public interface AircraftInterface{
    double fly(Airport toAirport);
    double addFuel(double amount);
    double fillUp();
    boolean hasFuel(double amount);
    double getWeightRatio();
}
