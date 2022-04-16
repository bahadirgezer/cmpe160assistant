package airport;

import java.lang.Math;
import java.util.HashMap;
import passenger.Passenger;

public abstract class Airport {
    HashMap<Integer, Passenger> passengers;
    private final int ID;
    private final double x, y;
    private double operationFee, fuelCost;

    public Airport(int ID, double x, double y, double fuelCost, double operationFee) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.fuelCost = fuelCost;
        this.operationFee = operationFee;
    }

//    public isFull(Aircraft aircraft);

    public boolean equals(Airport other) {
        return (this.ID == other.ID);
    }

    //get euclidean distance between two airports
    public double getDistance(Airport airport) {
        return Math.sqrt(Math.pow(airport.x - this.x, 2) + Math.pow(airport.y - this.y, 2));
    }

    public void addPassenger(Passenger passenger) {
        passengers.put(passenger.getID(), passenger);
    }

    //check if passenger is in the passengers
    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger.getID());
    }
}
