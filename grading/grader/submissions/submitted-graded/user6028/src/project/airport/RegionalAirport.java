package project.airport;

import project.airline.aircraft.Aircraft;

public class RegionalAirport extends Airport {
	public RegionalAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		super(2, ID, x, y, fuelCost, operationFee, aircraftCapacity);
		// TODO Auto-generated constructor stub
	}

			
			public double departAircraft(Aircraft aircraft) {
				double aircraftRatio = aircraftCount / aircraftCapacity;
				double fullnessCoefficient = Math.pow(Math.E, aircraftRatio) * 0.6;
				super.aircraftCount--;
				double weightRatio = aircraft.getWeightRatio();
				double departureFee = operationFee * weightRatio * fullnessCoefficient * 1.2;
				return departureFee;
			}
			
			public double landAircraft(Aircraft aircraft) {
				aircraft.setCurrentAirport(this);
				double aircraftRatio = aircraftCount / aircraftCapacity;
				double fullnessCoefficient = Math.pow(Math.E, aircraftRatio) * 0.6;
				super.aircraftCount++;
				double weightRatio = aircraft.getWeightRatio();
				double landingFee = operationFee * weightRatio * fullnessCoefficient * 1.3;
				return landingFee;
			}
}
