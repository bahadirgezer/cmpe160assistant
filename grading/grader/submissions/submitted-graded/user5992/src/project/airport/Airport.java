package project.airport;

import project.airline.aircraft.Aircraft;
import project.passenger.Passenger;

import java.util.LinkedList;

/**
 * @author Emre KILIC
 *
 */
public abstract class Airport {
    private final long ID;
    private final double x,y;
    private final int type;
    protected double fuelCost;
    protected double operationFee;
    protected int aircraftCapacity;
    protected final LinkedList<Aircraft> aircrafts = new LinkedList<>();
    protected final LinkedList<Passenger> passengers = new LinkedList<>();

    protected Airport(long id, double x, double y, int aircraftCapacity, int type) {
        ID = id;
        this.x = x;
        this.y = y;
        this.aircraftCapacity = aircraftCapacity;
        this.type = type;
    }

    public long getID() {
        return ID;
    }

    public boolean isFull(){
        return aircrafts.size() == aircraftCapacity;
    }

    public double getDistance(Airport airport) {
        return Math.sqrt(Math.pow(this.x-airport.x, 2)+Math.pow(this.y-airport.y,2));
    }

    public boolean equals(Airport airport) {
        return airport.ID == ID;
    }

    public void addPassenger(Passenger passenger){
        passengers.add(passenger);
    }

    public void removePassenger(Passenger passenger){
        passengers.remove(passenger);
    }

    public LinkedList<Passenger> getPassengers() {
        return passengers;
    }

    public void addAircraft(Aircraft aircraft){
        aircrafts.add(aircraft);
    }

    public LinkedList<Aircraft> getAircrafts() {
        return aircrafts;
    }

    protected double fullnessCoefficient(){
        double aircraftRatio = (double) aircrafts.size()/ (double) aircraftCapacity;
        return 0.6*Math.exp(aircraftRatio);
    }

    public abstract double getFuelCost(double fuel);
    public abstract double departAircraft(Aircraft aircraft);
    public abstract double getDepartureCost(Aircraft aircraft);
    public abstract double landAircraft(Aircraft aircraft);
    public abstract double getLandingCost(Aircraft aircraft);

    public int getType() {
        return type;
    }
}
