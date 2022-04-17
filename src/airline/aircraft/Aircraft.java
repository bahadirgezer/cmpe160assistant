package airline.aircraft;

import airport.Airport;
import interfaces.AircraftInterface;

public abstract class Aircraft implements AircraftInterface {
    Airport currentAirport;
    protected double weight, maxWeight;
    protected double fuel, fuelCapacity;
    protected double fuelWeight;
    protected double floorSpace;
    protected double operationFee;
    protected double aircraftTypeMultiplier;



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

        return fuelCost;
    }
    public double addFuel(double fuel) {
        double fuelCost = currentAirport.getFuelCost(fuel);
        this.fuel += fuel;
        this.weight += fuel * fuelWeight;

        return fuelCost;
    }

    public boolean fly(Airport toAirport) {
        //fuel consumption needs to drop with weight loss, differential equation?
        double fuelNeeded = getFuelConsumption(toAirport.getDistance(currentAirport));
        
        if (fuelNeeded > fuel) {
            return false;
        }
        fuel -= fuelNeeded;
        //need to check the relationship between fuelWeight and fuelConsumption
        weight -= fuelNeeded * fuelWeight;

        currentAirport = toAirport;
        return true;
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

    public double getFuelConsumption(double distance) { //not complete
        return distance;
    }

}
