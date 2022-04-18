package airline.aircraft.concrete;

import airline.aircraft.PassengerAircraft;
import airport.Airport;
import passenger.Passenger;

import java.util.HashMap;

public class WidebodyPassengerAircraft extends PassengerAircraft {
    protected WidebodyPassengerAircraft(Airport initialAirport) {
        super(initialAirport);
        maxWeight = 8000;
        weight = 1000;
        floorArea = 1000;
        fuelCapacity = 8000;
        fuelConsumption = 0.5;
        aircraftTypeMultiplier = 2;
    }
    public double getFlightCost(Airport toAirport) {

        return 0;
    }
}
