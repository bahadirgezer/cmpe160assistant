package project.airport_container;

import project.airline_container.aircraft_container.Aircraft;

public class HubAirport extends Airport {

	private double departCoefficient = 0.7;
	private double landCoefficient = 0.8;
	
	public HubAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		super(ID, x, y, fuelCost, operationFee, aircraftCapacity);
	}

	@Override
	public double departAircraft(Aircraft aircraft) {
		return operationFee * aircraft.getWeightRatio() * getFullnessCoefficient() * departCoefficient;
	}

	@Override
	public double landAircraft(Aircraft aircraft) {
		return  operationFee * aircraft.getWeightRatio() * getFullnessCoefficient() * landCoefficient;
	}
	
}
