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

    private double getFuelConsumption(double distance) {
        double distanceRatio = distance / 14000;
        double bathTubCoefficient = (25.9324 * Math.pow(distance, 4)) + (-50.5633 * Math.pow(distance, 3)) + (35.0554 * Math.pow(distance, 2)) + (-9.90346 * distance) + (1.97413);
        double averageFuelConsumption = fuelConsumption * bathTubCoefficient;
        if ()
    }

}
