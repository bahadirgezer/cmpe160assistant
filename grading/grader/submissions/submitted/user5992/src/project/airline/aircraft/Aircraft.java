package project.airline.aircraft;

import project.airport.Airport;
import project.interfaces.AircraftInterface;

/**
 * @author Emre KILIC
 *
 */
public abstract class Aircraft implements AircraftInterface {
    protected Airport currentAirport;
    protected double weight;
    protected double predictedWeight;
    protected double maxWeight;
    protected double fuelWeight = 0.7;
    protected double fuel;
    protected double fuelCapacity;
    protected double fuelConsumption;
    protected double aircraftTypeMultiplier;
    protected double operationFee;
    protected int type;

    public double fly(Airport toAirport){
        double fuelConsumed = getFuelConsumption(currentAirport.getDistance(toAirport));
        double cost = getFlightCost(toAirport);
        assert canFly(toAirport) == 0;
        assert weight <= maxWeight;
        fuel -= fuelConsumed;
        weight -= fuelWeight * fuelConsumed;
        currentAirport.departAircraft(this);
        toAirport.landAircraft(this);
        currentAirport = toAirport;
        return cost;
    }

    @Override
    public double addFuel(double fuel) {
        assert canAddFuel(fuel);
        this.fuel += fuel;
        weight += fuelWeight * fuel;
        return currentAirport.getFuelCost(fuel);
    }

    @Override
    public double fillUp() {
        double fuel = fuelCapacity - this.fuel;
        return addFuel(fuel);
    }

    @Override
    public boolean hasFuel(double fuel) {
        return fuel <= this.fuel;
    }

    /**
     * @param toAirport the destination
     * @return 0 when aircraft can fly <p>
     * 1 when aircraft is already there <p>
     * 2 when there is a range limitation <p>
     * 3 when airport is full <p>
     * 4 when there is not enough fuel <p>
     */
    public int canFly(Airport toAirport){
        double fuelNeeded = getFuelConsumption(currentAirport.getDistance(toAirport));
        if (currentAirport.equals(toAirport)){
            return 1;
        }else if (fuelCapacity < fuelNeeded){
            return 2;
        }else if (toAirport.isFull()){
            return 3;
        }else if (!hasFuel(fuelNeeded)){
            return 4;
        }else {
            return 0;
        }
    }

    public boolean canAddFuel(double fuel){
        return (this.fuel + fuel) <= fuelCapacity && weight + fuel * fuelWeight <= maxWeight;
    }

    @Override
    public double getWeightRatio() {
        return weight / maxWeight;
    }

    public Airport getCurrentAirport() {
        return currentAirport;
    }

    public double getFuel() {
        return fuel;
    }

    public int getType() {
        return type;
    }

    public abstract double getFlightCost(Airport toAirport);
    public abstract double getFuelConsumption(double distance);
    public abstract double getPredictedConsumption(double distance);
}
