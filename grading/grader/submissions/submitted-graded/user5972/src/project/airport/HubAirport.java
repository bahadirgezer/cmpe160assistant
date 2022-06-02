package project.airport;

import project.airline.aircraft.Aircraft;

public class HubAirport extends Airport {
	public HubAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		super(ID, x, y, fuelCost, operationFee, aircraftCapacity, 0);
	}
	
	public double departAircraft(Aircraft aircraft) {
		return getDepartCost(aircraft);
		
	}
	
	public double landAircraft(Aircraft aircraft) {
		return getLandCost(aircraft);
		
	}
	
	public double getDepartCost(Aircraft aircraft) {
		return (operationFee*aircraft.getWeightRatio()*getFullnessCoef()*0.7);
	}
	
	public double getLandCost(Aircraft aircraft) {
		return (operationFee*aircraft.getWeightRatio()*getFullnessCoef()*0.8);
	}

}
