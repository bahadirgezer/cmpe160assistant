package project.airportcontainer;

import project.airlinecontainer.aircraftcontainer.Aircraft;

public class HubAirport extends Airport {
	public HubAirport(long id, double x, double y) {
		super(id, x, y);
	}
	public double departAircraft(Aircraft aircraft) {
		
		return 0.7*super.operationFee*aircraft.getWeightRatio()*getFullnesscoefficient();
	}
	public double landAircraft(Aircraft aircraft) {
		return 0.8*super.operationFee*aircraft.getWeightRatio()*getFullnesscoefficient();
	}

}
