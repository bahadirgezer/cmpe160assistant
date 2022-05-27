package project.airport;

import project.airline.aircraft.Aircraft;

public class MajorAirport extends Airport {
	public MajorAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		super(ID, x, y, fuelCost, operationFee, aircraftCapacity, 1);
	}
	
	public double departAircraft(Aircraft aircraft) {
		return getDepartCost(aircraft);
		
	}
	
	public double landAircraft(Aircraft aircraft) {
		return getLandCost(aircraft);
		
	}
	
	public double getDepartCost(Aircraft aircraft) {
		return (operationFee*aircraft.getWeightRatio()*getFullnessCoef()*0.9);
	}
	
	public double getLandCost(Aircraft aircraft) {
		return (operationFee*aircraft.getWeightRatio()*getFullnessCoef());
	}

}
