package project.airport;

import project.airline.aircraft.Aircraft;

public class MajorAirport extends Airport {
	public MajorAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		super(1, ID, x, y, fuelCost, operationFee, aircraftCapacity);
		// TODO Auto-generated constructor stub
	}

		
		public double departAircraft(Aircraft aircraft) {
			double aircraftRatio = getAircraftCount() / getAircraftCapacity();
			double fullnessCoefficient = Math.pow(Math.E, aircraftRatio) * 0.6;
			double weightRatio = aircraft.getWeightRatio();
			double departureFee = operationFee * weightRatio * fullnessCoefficient * 0.9;
			return departureFee;
		}
		
		public double landAircraft(Aircraft aircraft) {
			double aircraftRatio = getAircraftCount() / getAircraftCapacity();
			double fullnessCoefficient = Math.pow(Math.E, aircraftRatio) * 0.6;
			double weightRatio = aircraft.getWeightRatio();
			double landingFee = operationFee * weightRatio * fullnessCoefficient;
			return landingFee;
		}
}
