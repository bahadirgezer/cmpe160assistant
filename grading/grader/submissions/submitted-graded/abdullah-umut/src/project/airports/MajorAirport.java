package project.airports;

import project.airlines.aircrafts.Aircraft;

public class MajorAirport extends Airport {
	
	public MajorAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity){
		super(ID, x, y);
		this.fuelCost = fuelCost;
		this.operationFee = operationFee;
		this.aircraftCapacity = aircraftCapacity;
		airportType = 0;
	}

	public double departAircraft(Aircraft aircraft) {
		double fullnessCoefficient = 0.6*Math.exp(aircraftRatio());
		double aircraftWeightRatio = aircraft.getWeightRatio();
		aircraftNumber -= 1;
		return operationFee*aircraftWeightRatio*fullnessCoefficient*0.9;
		}
	
	public double landAircraft(Aircraft aircraft) {
		double fullnessCoefficient = 0.6*Math.exp(aircraftRatio());
		double aircraftWeightRatio = aircraft.getWeightRatio();
		aircraftNumber += 1;
		return operationFee*aircraftWeightRatio*fullnessCoefficient*0.8;
	}
	
}
