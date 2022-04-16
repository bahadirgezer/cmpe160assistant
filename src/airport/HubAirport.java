package airport;

import airline.aircraft.*;

public class HubAirport extends Airport {

    public HubAirport(int ID, double x, double y, double fuelCost, double operationFee, int passengerAircraftCapacity, int cargoAircraftCapacity) {
        super(ID, x, y, fuelCost, operationFee, passengerAircraftCapacity, cargoAircraftCapacity);
    }

    public <HeavyCargoAircraft> boolean isFull(Aircraft aircraft) {
        if (aircraft instanceof HeacyCargoAircraft)
            return false;
    }

    public double getFuel(double fuelAmount) {
        return fuelAmount * fuelCost
    }

}    //TODO: implement isFull method