package airline.aircraft.concrete;

import airline.aircraft.PassengerAircraft;
import airport.Airport;
import passenger.Passenger;

import java.util.HashMap;

public class WidebodyPassengerAircraft extends PassengerAircraft {
    public WidebodyPassengerAircraft(Airport initialAirport) {
        super(initialAirport);
        maxWeight = 250000;
        weight = 135000;
        floorArea = 450;
        fuelCapacity = 140000;
        fuelConsumption = 3.0;
        aircraftTypeMultiplier = 0.7;
    }

    protected double getFuelConsumption(double distance) {
        double takeoffFuel = weight * 0.1 / fuelWeight;
        double distanceRatio = distance / 14000;
        double bathTubCoefficient = (25.9324 * Math.pow(distanceRatio, 4)) + (-50.5633 * Math.pow(distanceRatio, 3)) + (35.0554 * Math.pow(distanceRatio, 2)) + (-9.90346 * distanceRatio) + (1.97413);
        double averageFuelConsumption = fuelConsumption * bathTubCoefficient * distance;
        averageFuelConsumption += takeoffFuel;
        return averageFuelConsumption;
    }
}
