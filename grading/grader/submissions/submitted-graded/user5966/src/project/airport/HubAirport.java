package project.airport;

import project.airline.aircraft.Aircraft;

public class HubAirport extends Airport {
	public HubAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity,int order) {
		super(ID, x, y, fuelCost, operationFee, aircraftCapacity,order);
	}

	@Override
	public double departAircraft(Aircraft aircraft) {
		double fullnesCoeff = calculateFullnessCoeff();
		double weightRatio = aircraft.getWeightRatio();
		aircrafts.remove(aircraft);
		return operationFee * weightRatio * fullnesCoeff * 0.7;
	}

	@Override
	public double landAircraft(Aircraft aircraft) {
		double fullnesCoeff = calculateFullnessCoeff();
		double weightRatio = aircraft.getWeightRatio();
		aircrafts.add(aircraft);
		aircraft.setCurrentAirport(this);
		return operationFee * weightRatio * fullnesCoeff * 0.8;
	}

}
