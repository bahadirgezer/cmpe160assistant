package project.airport;

import project.airline.aircraft.Aircraft;

public class MajorAirport extends Airport{

	public MajorAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		super(2, ID, x, y, fuelCost, operationFee, aircraftCapacity);
	}

	@Override
	public double departAircraft(Aircraft aircraft) {
		double aircraftRatio = (double)this.getNoAircrafts()/(double)this.getAircraftCapacity();
		double fullnessCoeficient = 0.6*Math.pow(Math.E, aircraftRatio);
		return this.operationFee*aircraft.getWeightRatio()*fullnessCoeficient*0.9;
	}

	@Override
	public double landAircraft(Aircraft aircraft) {
		double aircraftRatio = (double)this.getNoAircrafts()/(double)this.getAircraftCapacity();
		double fullnessCoeficient = 0.6*Math.pow(Math.E, aircraftRatio);
		return this.operationFee*aircraft.getWeightRatio()*fullnessCoeficient;
	}


}
