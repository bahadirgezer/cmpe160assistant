package project.airport;

import project.airline.aircraft.Aircraft;

public class RegionalAirport extends Airport {
    public RegionalAirport(int type, long ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
        super(type, ID, x, y, fuelCost, operationFee, aircraftCapacity);
    }

    @Override
    public double departAircraft(Aircraft aircraft) {
        double fullnessCoefficient = 0.6 * Math.exp(this.getAircraftRatio());
        return operationFee * fullnessCoefficient * aircraft.getWeightRatio() * 1.2;
    }

    @Override
    public double landAircraft(Aircraft aircraft) {
        double fullnessCoefficient = 0.6 * Math.exp(getAircraftRatio());
        return operationFee * fullnessCoefficient * aircraft.getWeightRatio() * 1.3;
    }
}
