package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

/**
 * @author Emre KILIC
 *
 */
public class WideBodyPassengerAircraft extends PassengerAircraft {

    public WideBodyPassengerAircraft(double operationFee, Airport currentAirport) {
        weight = 135000;
        maxWeight = 250000;
        floorArea = 450;
        fuelCapacity = 140000;
        fuelConsumption = 3.0;
        aircraftTypeMultiplier = 0.7;
        type = 1;
        this.operationFee = operationFee;
        this.currentAirport = currentAirport;
    }

    public double getFlightCost(Airport toAirport){
        return getFullness() * currentAirport.getDistance(toAirport) * 0.15 +
                currentAirport.getDepartureCost(this) + toAirport.getLandingCost(this);
    }

    public double getFuelConsumption(double distance){
        double distanceRatio = distance/14000;
        double takeOff = (weight * 0.1)/fuelWeight;
        double cruise = fuelConsumption * bathtubCoefficient(distanceRatio) * distance;
        return takeOff + cruise;
    }

    @Override
    public double getPredictedConsumption(double distance) {
        predictedWeight = weight + getFuelConsumption(distance);
        double distanceRatio = distance/14000;
        double takeOff = (predictedWeight * 0.1)/fuelWeight;
        double cruise = fuelConsumption * bathtubCoefficient(distanceRatio) * distance;
        return takeOff + cruise;
    }
}
