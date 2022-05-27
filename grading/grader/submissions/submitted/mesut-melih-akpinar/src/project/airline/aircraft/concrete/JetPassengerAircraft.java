package project.airline.aircraft.concrete;

import project.airline.Airline;
import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class JetPassengerAircraft extends PassengerAircraft {

    public JetPassengerAircraft(Airport currentAirport, double aircraftOperationFee) {
        super(4, currentAirport, 10000, 18000, 30, 10000, 0.7, 5, aircraftOperationFee);
    }

    public double getFlightCost(Airport toAirport) {
        return currentAirport.departAircraft(this) + toAirport.landAircraft(this) + this.getFullness() * Airline.findDistance(this.currentAirport, toAirport) * 0.08;
    }

    protected double getFuelConsumption(double distance) {
        return (fuelCurve(distance / 5000) * 0.1 / fuelWeight) + (fuelConsumption * fuelCurve(distance / 5000) * distance);
    }
}
