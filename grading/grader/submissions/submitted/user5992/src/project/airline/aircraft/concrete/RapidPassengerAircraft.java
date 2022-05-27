package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

/**
 * @author Emre KILIC
 *
 */
public class RapidPassengerAircraft extends PassengerAircraft {

    public RapidPassengerAircraft(double operationFee, Airport currentAirport) {
        weight = 80000;
        maxWeight = 185000;
        floorArea = 120;
        fuelCapacity = 120000;
        fuelConsumption = 5.3;
        aircraftTypeMultiplier = 1.9;
        type = 2;
        this.operationFee = operationFee;
        this.currentAirport = currentAirport;
    }

    public double getFlightCost(Airport toAirport){
        return getFullness() * currentAirport.getDistance(toAirport) * 0.2 +
                currentAirport.getDepartureCost(this) + toAirport.getLandingCost(this);
    }

    public double getFuelConsumption(double distance){
        double distanceRatio = distance/7000;
        double takeOff = (weight * 0.1)/fuelWeight;
        double cruise = fuelConsumption * bathtubCoefficient(distanceRatio) * distance;
        return takeOff + cruise;
    }

    @Override
    public double getPredictedConsumption(double distance) {
        predictedWeight = weight + getFuelConsumption(distance);
        double distanceRatio = distance/7000;
        double takeOff = (predictedWeight * 0.1)/fuelWeight;
        double cruise = fuelConsumption * bathtubCoefficient(distanceRatio) * distance;
        return takeOff + cruise;
    }
}
