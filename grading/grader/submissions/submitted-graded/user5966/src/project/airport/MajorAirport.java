package project.airport;

import project.airline.aircraft.Aircraft;

public class MajorAirport extends Airport {
	public MajorAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity,int order) {
		super(ID, x, y, fuelCost, operationFee, aircraftCapacity, order);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double departAircraft(Aircraft aircraft) {
		double fullnesCoeff = calculateFullnessCoeff();
		double weightRatio = aircraft.getWeightRatio();
		aircrafts.remove(aircraft);
		return operationFee * weightRatio * fullnesCoeff * 0.9;
	}

	@Override
	public double landAircraft(Aircraft aircraft) {
		double fullnesCoeff = calculateFullnessCoeff();
		double weightRatio = aircraft.getWeightRatio();
		aircrafts.add(aircraft);
		aircraft.setCurrentAirport(this);
		return operationFee * weightRatio * fullnesCoeff;
	}

}
