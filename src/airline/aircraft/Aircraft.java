package airline.aircraft;

import airport.Airport;
import interfaces.AircraftInterface;

public abstract class Aircraft implements AircraftInterface {
    Airport currentAirport;
    protected double weight, maxWeight;
    protected double fuel, fuelCapacity;
    protected double fuelWeight;
    protected double operationFee;
    protected double aircraftTypeMultiplier;
    protected double fuelConsumption;



    //airline.aircraft constructor
    protected Aircraft(Airport initialAirport) {
        currentAirport = initialAirport;
        fuel = 0.0;
        fuelWeight = 0.7;
    }

    public boolean canAddFuel(double fuel) {
        if (fuelCapacity < fuel + this.fuel) {
            return false;
        }
        if (maxWeight < weight + fuel * fuelWeight) {
            return false;
        }
        return true;
    }

    public double fillUpFuel() {
        double fuelAmount = this.fuelCapacity - this.fuel;
        double fuelCost = currentAirport.getFuelCost(fuelAmount);
        this.fuel += fuelAmount;
        this.weight += fuelAmount * fuelWeight;

        return -fuelCost;
    }
    public double addFuel(double fuel) {
        double fuelCost = currentAirport.getFuelCost(fuel);
        this.fuel += fuel;
        this.weight += fuel * fuelWeight;

        return -fuelCost;
    }

    public boolean dumpFuel(double fuel) {
        if (this.fuel < fuel) {
            return false;
        }
        this.fuel -= fuel;
        this.weight -= fuel * fuelWeight;
        return true;
    }


    public boolean canFly(Airport toAirport) {
        if (getFuelConsumption(toAirport.getDistance(currentAirport)) > fuel) {
            return false;
        }
        return true;
    }

    protected abstract double getFlightCost(Airport toAirport); //must implement this

    public double fly(Airport toAirport) { //must be used after canFly()
        double fuelNeeded = getFuelConsumption(toAirport.getDistance(currentAirport));
        fuel -= fuelNeeded;
        weight -= fuelNeeded * fuelWeight;
        currentAirport = toAirport;
        return this.getFlightCost(toAirport);
    }


    public Airport getCurrentAirport() {
        return currentAirport;
    }

    public double getWeightRatio() {
        return weight / maxWeight;
    }

    public boolean hasFuel(double fuel) {
        return this.fuel >= fuel ? true : false;
    }

    public boolean hasFuel() {
        return this.fuel > 0 ? true : false;
    }

    protected abstract double getFuelConsumption(double distance);

}
