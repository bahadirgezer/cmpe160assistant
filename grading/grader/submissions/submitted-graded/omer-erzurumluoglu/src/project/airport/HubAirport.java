package project.airport;

import project.airline.aircraft.Aircraft;

public class HubAirport extends Airport {
	public HubAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		super(ID, x, y, fuelCost, operationFee, aircraftCapacity, 0);
	}

	public double departAircraft(Aircraft aircraft) {
		double x = getDepartCost(aircraft);
		totalAircraftNo -= 1;
		return x;

	}

	public double landAircraft(Aircraft aircraft) {
		double x = getLandCost(aircraft);
		totalAircraftNo += 1;
		return x;
	}

	public double getDepartCost(Aircraft aircraft) {
		return (operationFee * aircraft.getWeightRatio() * getFullnessCoef() * 0.7);
	}

	public double getLandCost(Aircraft aircraft) {
		return (operationFee * aircraft.getWeightRatio() * getFullnessCoef() * 0.8);
	}

}
