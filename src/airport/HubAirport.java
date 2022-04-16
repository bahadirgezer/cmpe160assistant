package airport;

import airline.aircraft.*;

public class HubAirport extends Airport {

    public HubAirport(int ID, double x, double y, double fuelCost, double operationFee, int passengerAircraftCapacity, int cargoAircraftCapacity) {
        super(ID, x, y, fuelCost, operationFee, passengerAircraftCapacity, cargoAircraftCapacity);
    }

    @Override
    public double departAircraft(Aircraft aircraft) {
        if (aircraft instanceof PassengerAircraft) {
            passengerAircraftCount--;
        } else if (aircraft instanceof CargoAircraft) {
            cargoAircraftCount--;
        }
        double fullnessCoefficient = 0.6 * (Math.pow(Math.E, ((double) (cargoAircraftCount+passengerAircraftCount) / (cargoAircraftCapacity+passengerAircraftCapacity))));
        return operationFee * aircraft.getWeightRatio() * 0.8 * 0.95 * fullnessCoefficient;
    }

    @Override
    public double landAircraft(Aircraft aircraft) {
        if (aircraft instanceof PassengerAircraft) {
            passengerAircraftCount++;
        } else if (aircraft instanceof CargoAircraft) {
            cargoAircraftCount++;
        }

        double fullnessCoefficient = 0.6 * (Math.pow(Math.E, ((double) (cargoAircraftCount+passengerAircraftCount) / (cargoAircraftCapacity+passengerAircraftCapacity))));
        return operationFee * aircraft.getWeightRatio() * 0.8 * fullnessCoefficient;
    }

    @Override
    public double getFuelCost(double fuel) {
        return fuelCost * fuel * 0.8; //the fuel gets pricier as the airport gets smaller
    }
}