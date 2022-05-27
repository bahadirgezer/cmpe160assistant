package project.airport;

import project.airline.Airline;
import project.airline.aircraft.Aircraft;
import project.airline.aircraft.PassengerAircraft;
import project.passenger.*;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Airport {
    private final int type;
    private final long ID;
    private final double x, y;
    protected double fuelCost;
    protected double operationFee;
    protected int aircraftCapacity;
    private ArrayList<Aircraft> aircrafts;
    private ArrayList<Passenger> passengers;

    public Airport(int type, long ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.fuelCost = fuelCost;
        this.operationFee = operationFee;
        this.aircraftCapacity = aircraftCapacity;
        this.aircrafts = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    public void addPassenger(String type, long id, double weight, int baggageCount, long destination) {
        Passenger passenger;
        ArrayList<Airport> destinations = new ArrayList<>();
        destinations.add(Airline.findAirportByIndex(destination));
        if(Objects.equals(type, "economy")) passenger = new EconomyPassenger(id, weight, baggageCount, destinations);
        else if(Objects.equals(type, "business")) passenger = new BusinessPassenger(id, weight, baggageCount, destinations);
        else if(Objects.equals(type, "first")) passenger = new FirstClassPassenger(id, weight, baggageCount, destinations);
        else if(Objects.equals(type, "luxury")) passenger = new LuxuryPassenger(id, weight, baggageCount, destinations);
        else passenger = null;
        passenger.lastEmbark = this;
        passengers.add(passenger);
    }

    public abstract double departAircraft(Aircraft aircraft);
    public abstract double landAircraft(Aircraft aircraft);

    public boolean isFull() {
        if(aircrafts.size() > aircraftCapacity){
            System.err.println("Airport is more than full");
        }
        return aircrafts.size() >= aircraftCapacity;
    }

    public boolean isEqual(Airport airport) {
        return (this.ID == airport.ID);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getFuelCost(double fuel) {
        return -fuel * fuelCost;
    }

    public double getType() {
        return type;
    }

    public double getAircraftRatio() {
        return 1.0 * aircrafts.size()/aircraftCapacity;
    }

    public double getOperationFee() {
        return operationFee;
    }

    public long getID() {
        return ID;
    }

    public Passenger getFirstPassenger() {
        if (passengers.size() == 0) return null;
        Passenger passenger = passengers.get(0);
        return passenger;
    }

    public void addAircraft(PassengerAircraft currentAircraft) {
        aircrafts.add(currentAircraft);
    }
}
