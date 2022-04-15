package airport;

import java.lang.Math;
import aircraft.Aircraft;

public abstract class Airport {
    int aircraftCapacity;
    int aircraftCount;
    int x, y;
    final int id;
    final int type; //0 = regional, 1 = major, 2 = hub;

    public Airport(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
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

    public boolean equals(Airport other) {
        return (this.id == other.id);
    }
    
    //get euclidean distance between two airports
    public double getDistance(Airport airport) {
        return Math.sqrt(Math.pow(airport.x - this.x, 2) + Math.pow(airport.y - this.y, 2));
    }

    public double getDistance(double x, double y) {
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
    }

    public boolean hasPassengerCapacity() {

    }

    public boolean hasCargoCapacity() {
        return 
    }
}
