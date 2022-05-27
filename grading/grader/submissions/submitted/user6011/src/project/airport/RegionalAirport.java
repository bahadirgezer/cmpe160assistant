package project.airport;

import project.airline.aircraft.Aircraft;

public class RegionalAirport extends Airport{
	
	public RegionalAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		super(ID, x, y, fuelCost, operationFee, aircraftCapacity, 2);
	}
	
	public double departAircraft(Aircraft aircraft) {
		double fullnessCoefficient = 0.6 * java.lang.Math.exp(currentAircraftNumber/aircraftCapacity);
		double aircraftWeightRatio = aircraft.getWeightRatio();
		currentAircraftNumber -= 1;
		return 1.2 * fullnessCoefficient * aircraftWeightRatio * operationFee;
	}
	
	public double landAircraft(Aircraft aircraft) {
		double fullnessCoefficient = 0.6 * java.lang.Math.exp(currentAircraftNumber/aircraftCapacity);
		double aircraftWeightRatio = aircraft.getWeightRatio();
		aircraft.setCurrentAirport(this);
		currentAircraftNumber += 1;
		return 1.3 * fullnessCoefficient * aircraftWeightRatio * operationFee;
	}
}
