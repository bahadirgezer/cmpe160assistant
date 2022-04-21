package airline.aircraft.concrete;

import airline.aircraft.PassengerAircraft;
import airport.Airport;

public class JetPassengerAircraft extends PassengerAircraft {
    public JetPassengerAircraft(Airport initialAirport) {
        super(initialAirport);
        maxWeight = 18000;
        weight = 10000;
        floorArea = 30;
        fuelCapacity = 10000;
        fuelConsumption = 0.7;
        aircraftTypeMultiplier = 5;
    }

    protected double getFuelConsumption(double distance) {
        double takeoffFuel = weight * 0.1 / fuelWeight;
        double distanceRatio = distance / 5000;
        double bathTubCoefficient = (25.9324 * Math.pow(distanceRatio, 4)) + (-50.5633 * Math.pow(distanceRatio, 3)) + (35.0554 * Math.pow(distanceRatio, 2)) + (-9.90346 * distanceRatio) + (1.97413);
        double averageFuelConsumption = fuelConsumption * bathTubCoefficient * distance;
        averageFuelConsumption += takeoffFuel;
        return averageFuelConsumption;
    }

}
