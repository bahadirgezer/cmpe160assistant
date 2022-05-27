package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

/**
 * @author Emre KILIC
 *
 */
public class JetPassengerAircraft extends PassengerAircraft {
    public JetPassengerAircraft(double operationFee, Airport currentAirport) {
        weight = 10000;
        maxWeight = 18000;
        floorArea = 30;
        fuelCapacity = 10000;
        fuelConsumption = 0.7;
        aircraftTypeMultiplier = 5;
        type = 3;
        this.operationFee = operationFee;
        this.currentAirport = currentAirport;
    }

    public double getFlightCost(Airport toAirport){
        assert weight >= 10000;
        double fullness = getFullness();
        double distance = currentAirport.getDistance(toAirport);
        double departCost = currentAirport.getDepartureCost(this);
        double landCost = toAirport.getLandingCost(this);
        return fullness * distance * 0.08 +
                departCost + landCost;
    }

    public double getFuelConsumption(double distance){
        double distanceRatio = distance/5000;
        double takeOff = (weight * 0.1)/fuelWeight;
        double cruise = fuelConsumption * bathtubCoefficient(distanceRatio) * distance;
        return takeOff + cruise;
    }

    @Override
    public double getPredictedConsumption(double distance) {
        predictedWeight = weight + getFuelConsumption(distance);
        double distanceRatio = distance/5000;
        double takeOff = (predictedWeight * 0.1)/fuelWeight;
        double cruise = fuelConsumption * bathtubCoefficient(distanceRatio) * distance;
        return takeOff + cruise;
    }
}
