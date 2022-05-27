package project.airline.aircraft.concrete;

import project.airline.Airline;
import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class WidebodyPassengerAircraft extends PassengerAircraft {
    public WidebodyPassengerAircraft(Airport currentAirport, double aircraftOperationFee) {
        super(2, currentAirport, 135000, 250000, 450, 140000, 3.0, 0.7, aircraftOperationFee);
    }

    protected double getFuelConsumption(double distance) {
        return (fuelCurve(distance / 14000) * 0.1 / fuelWeight) + (fuelConsumption * fuelCurve(distance / 14000) * distance);
    }

    public double getFlightCost(Airport toAirport) {
        return currentAirport.departAircraft(this) + toAirport.landAircraft(this) + this.getFullness() * Airline.findDistance(this.currentAirport, toAirport) * 0.15;
    }
}