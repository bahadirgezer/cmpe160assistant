package project.airportcontainer;

import project.airlinecontainer.aircraftcontainer.Aircraft;

public class MajorAirport extends Airport {
	public MajorAirport(long id, double x, double y) {
		super(id, x, y);
	}
	public double departAircraft(Aircraft aircraft) {
		return 0.9*super.operationFee*aircraft.getWeightRatio()*getFullnesscoefficient();
	}
	public double landAircraft(Aircraft aircraft) {
		return super.operationFee*aircraft.getWeightRatio()*getFullnesscoefficient();
	}
}
