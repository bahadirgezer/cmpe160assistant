package project.airportcontainer;

import project.airlinecontainer.aircraftcontainer.Aircraft;

public class RegionalAirport extends Airport {
	public RegionalAirport(long id, double x, double y) {
		super(id, x, y);
	}
	public double departAircraft(Aircraft aircraft) {
		return 1.2*super.operationFee*aircraft.getWeightRatio()*this.getFullnesscoefficient();
	}
	public double landAircraft(Aircraft aircraft) {
		
		double opFee =super.operationFee; double ratio = aircraft.getWeightRatio(); double fullnessCoefficient = 1.3*this.getFullnesscoefficient();
		return opFee*ratio*fullnessCoefficient;
	}
}
