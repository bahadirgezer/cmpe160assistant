package airline.aircraft.concrete;

import airline.aircraft.PassengerAircraft;
import airport.Airport;

public class RapidPassengerAircraft extends PassengerAircraft {
    public RapidPassengerAircraft(Airport initialAirport) {
        super(initialAirport);
        maxWeight = 185000;
        weight = 80000;
        floorArea = 120;
        fuelCapacity = 120000;
        fuelConsumption = 5.3;
        aircraftTypeMultiplier = 1.9;
    }

    @Override
    protected double getFlightCost(Airport toAirport) {
        double distance = this.getCurrentAirport().getDistance(toAirport);
        double fullness = this.getFullness();
        double cost = distance * fullness * 0.2;
        cost += currentAirport.departAircraft(this);
        cost += toAirport.landAircraft(this);
        currentAirport = toAirport;
        return cost;

    }


    protected double getFuelConsumption(double distance) {
        double takeoffFuel = weight * 0.1 / fuelWeight;
        double distanceRatio = distance / 7000;
        double bathTubCoefficient = (25.9324 * Math.pow(distanceRatio, 4)) + (-50.5633 * Math.pow(distanceRatio, 3)) + (35.0554 * Math.pow(distanceRatio, 2)) + (-9.90346 * distanceRatio) + (1.97413);
        double averageFuelConsumption = fuelConsumption * bathTubCoefficient * distance;
        averageFuelConsumption += takeoffFuel;
        return averageFuelConsumption;
    }
    
}
