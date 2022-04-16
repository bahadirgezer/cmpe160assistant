package interfaces;

import airport.Airport;

public interface AircraftInterface {
    boolean fly(Airport toAirport);
    void printContents();
    boolean isEmpty();
    boolean isFull();
    Airport getCurrentAirport();
    boolean hasFuel();
    boolean hasFuel(Double fuel);
    boolean canAddFuel(double fuel);
    double addFuel(double fuel); //add fuel to the airline.aircraft, checks weight and fuel capacity
    double getFlightCost();
    
}
