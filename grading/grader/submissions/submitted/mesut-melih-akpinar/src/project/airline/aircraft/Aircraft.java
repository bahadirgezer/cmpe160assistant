package project.airline.aircraft;

import project.airline.Airline;
import project.airport.Airport;
import project.interfaces.AircraftInterface;
import project.passenger.Passenger;

public abstract class Aircraft implements AircraftInterface {
    private final int type;
    protected Airport currentAirport;
    protected double weight, maxWeight;
    protected double floorArea;
    protected double fuel, fuelWeight, fuelCapacity, fuelConsumption;
    protected double aircraftOperationFee;
    protected double aircraftTypeMultiplier;

    public Aircraft(int type, Airport currentAirport, double weight, double maxWeight, double floorArea, double fuelCapacity, double fuelConsumption, double aircraftTypeMultiplier, double aircraftOperationFee) {
        this.type = type;
        this.currentAirport = currentAirport;
        this.weight = weight;
        this.maxWeight = maxWeight;
        this.floorArea = floorArea;
        this.fuel = 0;
        this.fuelWeight = 0.7;
        this.fuelCapacity = fuelCapacity;
        this.fuelConsumption = fuelConsumption;
        this.aircraftTypeMultiplier = aircraftTypeMultiplier;
        this.aircraftOperationFee = aircraftOperationFee;
    }

    public double fly(Airport toAirport) {
        double cost = this.getFlightCost(toAirport);
        double consumedFuel = getFuelConsumption(Airline.findDistance(currentAirport, toAirport));
        fuel -= consumedFuel;
        weight -= consumedFuel * fuelWeight;
        currentAirport = toAirport;
        return -cost;
    }

    public boolean addFuel(double fuel) {
        if (this.fuel + fuel > fuelCapacity) {
            return false;
        }
        this.fuel += fuel;
        return true;
    }

    public boolean fillUp() {
        double maxFuel = fuelCapacity - fuel;
        maxFuel = Math.min(maxFuel, (maxWeight - weight) / fuelWeight);
        this.weight += maxFuel * fuelWeight;
        this.fuel += maxFuel;
        return true;
    }

    public double getMaxFuelFillable() {
        return Math.min(fuelCapacity - fuel, (maxWeight - weight) / fuelWeight);
    }

    public boolean hasFuel(double fuel) {
        return this.fuel >= fuel;
    }

    public double getWeightRatio() {
        return weight/maxWeight;
    }

    abstract public double getFlightCost(Airport toAirport);
    abstract protected double getFuelConsumption(double distance);
    abstract public boolean canBeLoaded(Passenger passenger);
    abstract public double getFullness();

    public boolean isFuelEnough(Airport toAirport) {
        return fuel > getFuelConsumption(Airline.findDistance(currentAirport, toAirport));
    }

    public boolean isIn(Airport airport) {
        return currentAirport.isEqual(airport);
    }

    public Airport getCurrentAirport() {
        return currentAirport;
    }

    public int getType() {
        return type;
    }

    public double refuel(double fuel) {
        this.fuel += fuel;
        return currentAirport.getFuelCost(fuel);
    }

}