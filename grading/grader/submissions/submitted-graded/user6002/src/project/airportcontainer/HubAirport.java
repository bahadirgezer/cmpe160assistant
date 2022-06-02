package project.airportcontainer;

import project.airlinecontainer.aircraftcontainer.Aircraft;

public class HubAirport extends Airport{
	public HubAirport(int airportType, int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		super(airportType, ID, x, y, fuelCost, operationFee, aircraftCapacity);
		// airportType = 0;
	}
	
	public double departAircraft(Aircraft aircraft) {
		double aircraftRatio = this.currentAircrafts.size() / this.aircraftCapacity;
		double fullnessCoefficient = 0.6 * Math.pow(Math.E, aircraftRatio);
		double aircraftWeightRatio = aircraft.getWeightRatio();
		double departureFee = operationFee * aircraftWeightRatio * fullnessCoefficient * 0.7;
		this.currentAircrafts.remove(aircraft);
		return departureFee;
	}
	
	public double landAircraft(Aircraft aircraft) {
		double aircraftRatio = this.currentAircrafts.size() / this.aircraftCapacity;
		double fullnessCoefficient = 0.6 * Math.pow(Math.E, aircraftRatio);
		double aircraftWeightRatio = aircraft.getWeightRatio();
		double landingFee = operationFee * aircraftWeightRatio * fullnessCoefficient * 0.8;
		this.currentAircrafts.add(aircraft);
		aircraft.setCurrentAirport(this);
		return landingFee;
	}
}
