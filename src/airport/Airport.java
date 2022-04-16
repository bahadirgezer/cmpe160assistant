package airport;

import java.lang.Math;
import java.util.HashMap;

import airline.aircraft.Aircraft;
import cargo.Cargo;
import passenger.Passenger;

public abstract class Airport {
    HashMap<Integer, Passenger> passengers;
    HashMap<Integer, Cargo> cargos;
    private final int ID;
    private final double x, y;
    private double operationFee, fuelCost;
    protected int passengerAircraftCapacity;
    protected int cargoAircraftCapacity;

    protected int fuelCost;

    public Airport(int ID, double x, double y, double fuelCost, double operationFee, int passengerAircraftCapacity, int cargoAircraftCapacity) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.fuelCost = fuelCost;
        this.operationFee = operationFee;
        this.passengerAircraftCapacity = passengerAircraftCapacity;
        this.cargoAircraftCapacity = cargoAircraftCapacity;
    }

    public abstract <T> boolean isFull(Aircraft aircraft);

    public abstract double departAircraft(Aircraft aircraft);

    public boolean equals(Airport other) {
        return (this.ID == other.ID);
    }

    //get euclidean distance between two airports
    public double getDistance(Airport airport) {
        return Math.sqrt(Math.pow(airport.x - this.x, 2) + Math.pow(airport.y - this.y, 2));
    }

    //addCargo method adds cargo to the airport
    public void addCargo(Cargo cargo) {
        cargos.put(cargo.getID(), cargo);
    }

    //removecargo method removes cargo from the airport
    public void removeCargo(Cargo cargo) {
        cargos.remove(cargo.getID());
    }

    public void addPassenger(Passenger passenger) {
        passengers.put(passenger.getID(), passenger);
    }

    //check if passenger is in the passengers
    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger.getID());
    }
    //will add add and remove cargo operations.
}
