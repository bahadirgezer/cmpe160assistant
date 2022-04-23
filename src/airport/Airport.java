package airport;

import airline.aircraft.Aircraft;
import airline.aircraft.PassengerAircraft;
import passenger.Passenger;

import java.util.HashMap;

public abstract class Airport {
    HashMap<Integer, Passenger> passengers;
    private final int ID;
    private final double x, y;
    protected double operationFee, fuelCost;
    protected int passengerAircraftCapacity, cargoAircraftCapacity;
    protected int passengerAircraftCount, cargoAircraftCount;


    public Airport(int ID, double x, double y, double fuelCost, double operationFee, int passengerAircraftCapacity, int cargoAircraftCapacity) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.fuelCost = fuelCost;
        this.operationFee = operationFee;
        this.passengerAircraftCapacity = passengerAircraftCapacity;
        this.cargoAircraftCapacity = cargoAircraftCapacity;
    }

    public boolean isFull(Aircraft aircraft) {
        if (aircraft instanceof PassengerAircraft) {
            if (passengerAircraftCapacity == passengerAircraftCount) {
                return true;
            }
            return false;
        }
//        else if (aircraft instanceof CargoAircraft) {
//            if (cargoAircraftCapacity == cargoAircraftCount) {
//                    return true;
//            }
//            return false;
//        }
        return true;
    }

    public abstract double departAircraft(Aircraft aircraft);

    public abstract double landAircraft(Aircraft aircraft);

    public abstract double getFuelCost(double fuel);

    public boolean equals(Airport other) {
        return (this.ID == other.ID);
    }

    //get euclidean distance between two airports
    public double getDistance(Airport airport) {
        return Math.sqrt(Math.pow(airport.x - this.x, 2) + Math.pow(airport.y - this.y, 2));
    }

    //addCargo method adds cargo to the airport
    //public void addCargo(Cargo cargo) {
//        cargos.put(cargo.getID(), cargo);
//    }

    //removecargo method removes cargo from the airport
    //public void removeCargo(Cargo cargo) {
//        cargos.remove(cargo.getID());
//    }

    public int getID() {
        return ID;
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
