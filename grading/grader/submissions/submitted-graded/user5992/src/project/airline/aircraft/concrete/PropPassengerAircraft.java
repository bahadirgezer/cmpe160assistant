package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

/**
 * @author Emre KILIC
 *
 */
public class PropPassengerAircraft extends PassengerAircraft {

    public PropPassengerAircraft(double operationFee, Airport currentAirport) {
        weight = 14000;
        maxWeight = 23000;
        floorArea = 60;
        fuelCapacity = 6000;
        fuelConsumption = 0.6;
        aircraftTypeMultiplier = 0.9;
        type = 0;
        this.operationFee = operationFee;
        this.currentAirport = currentAirport;
    }

    public double getFlightCost(Airport toAirport){
        assert weight >= 14000;
        return getFullness() * currentAirport.getDistance(toAirport) * 0.1 +
                currentAirport.getDepartureCost(this) + toAirport.getLandingCost(this);
    }

    public double getFuelConsumption(double distance){
        double distanceRatio = distance/2000;
        double takeOff = (weight * 0.08)/fuelWeight;
        double cruise = fuelConsumption * bathtubCoefficient(distanceRatio) * distance;
        return takeOff + cruise;
    }

    @Override
    public double getPredictedConsumption(double distance) {
        predictedWeight = weight + getFuelConsumption(distance);
        double distanceRatio = distance/2000;
        double takeOff = (predictedWeight * 0.08)/fuelWeight;
        double cruise = fuelConsumption * bathtubCoefficient(distanceRatio) * distance;
        return takeOff + cruise;
    }
}
