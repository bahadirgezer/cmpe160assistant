package project.airportContainer;

import project.airlineContainer.aircraftContainer.Aircraft;

public class MajorAirport extends Airport {

	public MajorAirport(int iD, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		super(iD, x,  y,  fuelCost,  operationFee,  aircraftCapacity);
	}
	public double departAircraft(Aircraft aircraft) {
		double aircraftRatio= (double) this.aircrafts.size()/this.aircraftCapacity;
		double fullnessCoefficient=0.6*Math.exp(aircraftRatio);
		double aircraftWeightRatio= (double) aircraft.getWeight()/aircraft.getMaxWeight();
		double departureFee =this.operationFee*fullnessCoefficient*aircraftWeightRatio*0.9;
		this.aircrafts.remove(aircraft);
		return departureFee;
	}
	public double landAircraft(Aircraft aircraft) {
		double aircraftRatio= (double) this.aircrafts.size()/this.aircraftCapacity;
		double fullnessCoefficient=0.6*Math.exp(aircraftRatio);
		double aircraftWeightRatio= (double) aircraft.getWeight()/aircraft.getMaxWeight();
		double landingFee =this.operationFee*fullnessCoefficient*aircraftWeightRatio;
		this.aircrafts.add(aircraft);
		return landingFee;
	}
	

}
