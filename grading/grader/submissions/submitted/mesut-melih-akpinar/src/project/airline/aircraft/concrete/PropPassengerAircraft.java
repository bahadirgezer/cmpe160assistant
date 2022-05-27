package project.airline.aircraft.concrete;

import project.airline.Airline;
import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class PropPassengerAircraft extends PassengerAircraft {
    public PropPassengerAircraft(Airport currentAirport, double aircraftOperationFee) {
        super(1, currentAirport, 14000, 23000, 60, 6000, 0.6, 0.9, aircraftOperationFee);
    }

    protected double getFuelConsumption(double distance) {
        return (fuelCurve(distance / 2000) * 0.08 / fuelWeight) + (fuelConsumption * fuelCurve(distance / 2000) * distance);
    }

    public double getFlightCost(Airport toAirport) {
        return currentAirport.departAircraft(this) + toAirport.landAircraft(this) + this.getFullness() * Airline.findDistance(this.currentAirport, toAirport) * 0.1;
    }

    @Override
    public boolean addFuel(double fuel) {
        return false;
    }
}