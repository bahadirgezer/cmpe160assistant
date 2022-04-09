package airport;

import java.lang.Math;
import aircraft.Aircraft;

abstract class Airport {
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

    abstract boolean arrival(Aircraft plane);
    abstract boolean departure(Aircraft plane);
    abstract void loadFuel(double amount);

    /**
     * 
     * @param passengerCount
     * @param weight
     * @param fuel
     * @param planeType
     * @return
     */
    public double getArrivalFee(double passengerCount, double weight, int planeType) {
        if (planeType == 0) {

        }
        if (passengerCount > 200 || weight) {
            return 
        }
    }

    public double getDepartureFee() {

    }
    
    public double getDistance(double x, double y) {
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
    }
}

