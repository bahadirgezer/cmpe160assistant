package project.airport_container;

import project.airline_container.aircraft_container.Aircraft;

public class MajorAirport extends Airport {

	private double departCoefficient = 0.9;
	private double landCoefficient = 1;
	
	public MajorAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		super(ID, x, y, fuelCost, operationFee, aircraftCapacity);
	}

	@Override
	public double departAircraft(Aircraft aircraft) {
		return operationFee * aircraft.getWeightRatio() * getFullnessCoefficient() * departCoefficient;
	}

	@Override
	public double landAircraft(Aircraft aircraft) {
		return getFullnessCoefficient() * aircraft.getWeightRatio() * operationFee * landCoefficient;
	}
	
}
