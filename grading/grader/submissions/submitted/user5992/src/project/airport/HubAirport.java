package project.airport;

import project.airline.aircraft.Aircraft;

/**
 * @author Emre KILIC
 *
 */
public class HubAirport extends Airport {

    public HubAirport(long id, double x, double y, int aircraftCapacity, double fuelCost, double operationFee, int type) {
        super(id, x, y, aircraftCapacity, type);
        this.fuelCost = fuelCost;
        this.operationFee = operationFee;
    }

    @Override
    public double getFuelCost(double fuel) {
        return fuelCost * fuel;
    }

    @Override
    public double departAircraft(Aircraft aircraft) {
        double cost = getDepartureCost(aircraft);
        aircrafts.remove(aircraft);
        return cost;
    }

    @Override
    public double getDepartureCost(Aircraft aircraft) {
        double fulCo = fullnessCoefficient();
        double weiRat = aircraft.getWeightRatio();
        return operationFee * fulCo * weiRat * 0.70;
    }

    @Override
    public double landAircraft(Aircraft aircraft) {
        assert !isFull();
        double cost = getLandingCost(aircraft);
        aircrafts.add(aircraft);
        return cost;
    }

    @Override
    public double getLandingCost(Aircraft aircraft) {
        double operationFee = this.operationFee;
        double fuCo = fullnessCoefficient();
        double weRa = aircraft.getWeightRatio();
        return operationFee * fullnessCoefficient() * aircraft.getWeightRatio() * 0.80;
    }
}
