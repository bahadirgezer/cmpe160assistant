package airline.aircraft.concrete;

import airline.aircraft.PassengerAircraft;
import airport.Airport;

public class PropPassengerAircraft extends PassengerAircraft {
    public PropPassengerAircraft(Airport initialAirport) {
        super(initialAirport);
        maxWeight = 23000;
        weight = 14000;
        floorArea = 60;
        fuelCapacity = 6000;
        fuelConsumption = 0.6;
        aircraftTypeMultiplier = 0.9;
    }

    @Override
    protected double getFlightCost(Airport toAirport) {
        double distance = this.getCurrentAirport().getDistance(toAirport);
        double fullness = this.getFullness();
        return distance * fullness * 0.1;
    }

    protected double getFuelConsumption(double distance) {
        double takeoffFuel = weight * 0.08 / fuelWeight;
        double distanceRatio = distance / 2000;
        double bathTubCoefficient = (25.9324 * Math.pow(distanceRatio, 4)) + (-50.5633 * Math.pow(distanceRatio, 3)) + (35.0554 * Math.pow(distanceRatio, 2)) + (-9.90346 * distanceRatio) + (1.97413);
        double averageFuelConsumption = fuelConsumption * bathTubCoefficient * distance;
        averageFuelConsumption += takeoffFuel;
        return averageFuelConsumption;
    }
}
