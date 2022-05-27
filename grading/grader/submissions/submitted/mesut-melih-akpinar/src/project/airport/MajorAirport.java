package project.airport;

import project.airline.aircraft.Aircraft;

public class MajorAirport extends Airport {
    public MajorAirport(int type, long ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
        super(type, ID, x, y, fuelCost, operationFee, aircraftCapacity);
    }

    @Override
    public double departAircraft(Aircraft aircraft) {
        double fullnessCoefficient = 0.6 * Math.exp(getAircraftRatio());
        return operationFee * fullnessCoefficient * aircraft.getWeightRatio() * 0.9;
    }

    @Override
    public double landAircraft(Aircraft aircraft) {
        double fullnessCoefficient = 0.6 * Math.exp(getAircraftRatio());
        return operationFee * fullnessCoefficient * aircraft.getWeightRatio() * 0.9;
    }
}
