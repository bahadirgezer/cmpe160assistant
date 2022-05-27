package project.airport_container;

import project.airline_container.aircraft_container.Aircraft;

public class RegionalAirport extends Airport {

	private double departCoefficient = 1.2;
	private double landCoefficient = 1.3;
	
	public RegionalAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
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
