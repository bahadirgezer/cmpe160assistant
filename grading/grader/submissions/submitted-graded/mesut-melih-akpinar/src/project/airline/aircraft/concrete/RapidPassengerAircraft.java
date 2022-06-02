package project.airline.aircraft.concrete;

import project.airline.Airline;
import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class RapidPassengerAircraft extends PassengerAircraft {
    public RapidPassengerAircraft(Airport currentAirport, double aircraftOperationFee) {
        super(3, currentAirport, 80000, 185000, 120, 120000, 5.3, 1.9, aircraftOperationFee);
    }

    protected double getFuelConsumption(double distance) {
        return (fuelCurve(distance / 7000) * 0.1 / fuelWeight) + (fuelConsumption * fuelCurve(distance / 7000) * distance);
    }

    public double getFlightCost(Airport toAirport) {
        return currentAirport.departAircraft(this) + toAirport.landAircraft(this) + this.getFullness() * Airline.findDistance(this.currentAirport, toAirport) * 0.2;
    }
}