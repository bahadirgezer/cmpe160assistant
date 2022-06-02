package project.airport;

import project.airline.aircraft.Aircraft;

public class HubAirport extends Airport {
	public HubAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		super(0, ID, x, y, fuelCost, operationFee, aircraftCapacity);
	}
	
	
	
	public double departAircraft(Aircraft aircraft) {
		double aircraftRatio = aircraftCount / aircraftCapacity;
		double fullnessCoefficient = Math.pow(Math.E, aircraftRatio) * 0.6;
		super.aircraftCount--;
		double weightRatio = aircraft.getWeightRatio();
		double departureFee = operationFee * weightRatio * fullnessCoefficient * 0.7;
		return departureFee;
	}
	
	public double landAircraft(Aircraft aircraft) {
		aircraft.setCurrentAirport(this);
		double aircraftRatio = aircraftCount / aircraftCapacity;
		double fullnessCoefficient = Math.pow(Math.E, aircraftRatio) * 0.6;
		super.aircraftCount++;
		double weightRatio = aircraft.getWeightRatio();
		double landingFee = operationFee * weightRatio * fullnessCoefficient * 0.8;
		return landingFee;
	}
}
