package airport;

import java.lang.Math;
import aircraft.Aircraft;

public abstract class Airport {
    Aircraft planes[];
    private int aircraftCapacity;
    private final double x;
    private final double y;
    private int aircraftCount;
    private final double fuelCost;
    private final double fuelCapacity;
    private double fuelAmount;
    private final double operationFee;
    private final double airportTax;


    
    public Airport(int aircraftCapacity, double x, double y, double fuelCost, double fuelCapacity, double operationFee, double airportTax) {
        this.aircraftCapacity = aircraftCapacity;
        this.x = x;
        this.y = y;
        this.aircraftCount = 0;
        this.fuelCost = fuelCost;
        this.fuelCapacity = fuelCapacity;
        fuelAmount = fuelCapacity;
        this.operationFee = operationFee;
        this.airportTax = airportTax;

    }
    
    /**
     * Checkout if two airports are the same
     */
    public boolean equals(Airport other) {
        return (this.x == other.x && this.y == other.y);
    }

    //Print an array of Airports (for testing)
    public static void printAirports(Airport[] airports) {
        for (Airport airport : airports) {
            System.out.println(airport.toString());
        }
    }

    //toString method for testing
    public String toString() {
        return "Airport: " + x + "," + y;
    }

    //add fuel to the airport
    public void addFuel(double fuel) {
        fuelAmount += fuel;
    }

    //get the fuel amount
    public double getFuelAmount() {
        return fuelAmount;
    }

    
    abstract boolean arrival(Aircraft aircraft);
    abstract boolean departure(Aircraft aircraft);
    abstract void loadFuel(double amount);

    abstract public double getArrivalFee(double passengerCount, double weight, int planeType);
    abstract public double getArrivalFee(double weight, int planeType);

    abstract public double getDepartureFee();
    
    //get euclidean distance between two airports
    public double getDistance(Airport airport) {
        return Math.sqrt(Math.pow(airport.x - this.x, 2) + Math.pow(airport.y - this.y, 2));
    }

    public double getDistance(double x, double y) {
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
    }
}
