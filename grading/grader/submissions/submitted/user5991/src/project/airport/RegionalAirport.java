package project.airport;

import project.airline.aircraft.Aircraft;

public class RegionalAirport extends Airport {
	
	
	public RegionalAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		super(ID, x, y, fuelCost, operationFee, aircraftCapacity);
	}

	public double departAircraft(Aircraft aircraft) {
		double aircraftratio = (double)(aircrafts.size())/(double)(aircraftCapacity);
		double fullnesscoefficient = 0.6*Math.exp(aircraftratio);
		double aircraftweightratio = aircraft.getWeightRatio();
		aircrafts.remove(aircraft);
		return operationFee*aircraftweightratio*fullnesscoefficient*1.2;
	}
	public double landAircraft(Aircraft aircraft) {
		double aircraftratio = (double)(aircrafts.size())/(double)(aircraftCapacity);
		double fullnesscoefficient = 0.6*Math.exp(aircraftratio);
		double aircraftweightratio = aircraft.getWeightRatio();
		aircrafts.add(aircraft);
		return operationFee*aircraftweightratio*fullnesscoefficient*1.3;
	}
}
