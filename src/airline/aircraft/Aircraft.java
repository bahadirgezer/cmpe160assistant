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

    abstract double getFuelConsumption(double distance);

//    private double getFuelConsumption(double distance) { //not complete
//
//        double distanceRatio = distance /
//        double potentialfuel
//        double potentialFuel = fuel;
//        double potentialWeight = weight;
//        double fuelConsumption = 0.0;
//
//        double takeOffFuelWeight = weight * 0.1;
//        double takeOffFuel = takeOffFuelWeight / fuelWeight;
//
//        potentialFuel -= takeOffFuel;
//        potentialWeight -= takeOffFuelWeight;
//        fuelConsumption += takeOffFuel;
//
//        double range = 2000;
//        double fuelEquiv = (potentialWeight / fuelWeight);
//        double constant1 = (range * (1 - (fuelEquiv))) / (1 - Math.pow(Math.E, -fuelWeight * this.fuelConsumption * range));
//        double cruiseFuelConsumption = ((-constant1 * Math.pow(Math.E, -fuelWeight * this.fuelConsumption * distance)) + (fuelEquiv * distance) + constant1);
//        double cuiseFuelConsumptionWeight = cruiseFuelConsumption * fuelWeight;
//
//        potentialFuel -= cruiseFuelConsumption;
//        potentialWeight -= cuiseFuelConsumptionWeight;
//        fuelConsumption += cruiseFuelConsumption;
//
//        double landingFuelWeight = weight * 0.09;
//        double landingFuel = landingFuelWeight /fuelWeight;
//
//        potentialFuel -= landingFuel;
//        potentialWeight -= landingFuelWeight;
//        fuelConsumption += cruiseFuelConsumption;
//
//        return fuelConsumption;
//    }

}
