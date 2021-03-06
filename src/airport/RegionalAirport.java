package airport;

import airline.aircraft.Aircraft;
import airline.aircraft.CargoAircraft;
import airline.aircraft.PassengerAircraft;

public class RegionalAirport extends Airport{

    public RegionalAirport(int ID, double x, double y, double fuelCost, double operationFee, int passengerAircraftCapacity, int cargoAircraftCapacity) {
        super(ID, x, y, fuelCost, operationFee, passengerAircraftCapacity, cargoAircraftCapacity);
    }

    @Override
    public double departAircraft(Aircraft aircraft) {
        if (aircraft instanceof PassengerAircraft) {
            passengerAircraftCount--;
        }
//        else if (aircraft instanceof CargoAircraft) {
//            cargoAircraftCount--;
//        }
        double fullnessCoefficient = 0.6 * (Math.pow(Math.E, ((double) (cargoAircraftCount+passengerAircraftCount) / (cargoAircraftCapacity+passengerAircraftCapacity))));
        return operationFee * aircraft.getWeightRatio() * 1.2 * fullnessCoefficient;
    }

    @Override
    public double landAircraft(Aircraft aircraft) {
        if (aircraft instanceof PassengerAircraft) {
            passengerAircraftCount++;
        }
//        else if (aircraft instanceof CargoAircraft) {
//            cargoAircraftCount++;
//        }
        double fullnessCoefficient = 0.6 * (Math.pow(Math.E, ((double) (cargoAircraftCount+passengerAircraftCount) / (cargoAircraftCapacity+passengerAircraftCapacity))));
        return operationFee * aircraft.getWeightRatio() * 1.3 * fullnessCoefficient;
    }

    @Override
    public double getFuelCost(double fuel) {
        return fuelCost * fuel; //the fuel gets pricier as the airport gets smaller
    }
}
