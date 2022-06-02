package project.airportContainer;

import project.airlineContainer.aircraftContainer.Aircraft;

public class HubAirport extends Airport {
	
	public HubAirport(int ID, double x, double y) {
		super(ID, x, y);
		this.airportType = 0;
	}
	
	public double departAircraft(Aircraft aircraft) {
		double ratio = this.getAircraftRatio();
		double fullnessCoefficient = 0.6*Math.pow(Math.E, ratio);
		double weightRatio = aircraft.getWeightRatio();
		double departureFee = this.operationFee*fullnessCoefficient*weightRatio*0.7;
		this.aircraftCount -= 1;
		return departureFee;
	}
	public double landAircraft(Aircraft aircraft) {
		double ratio = this.getAircraftRatio();
		double fullnessCoefficient = 0.6*Math.pow(Math.E, ratio);
		this.aircraftCount += 1;
		double weightRatio = aircraft.getWeightRatio();
		double landingFee = this.operationFee*fullnessCoefficient*weightRatio*0.8;
		return landingFee;
	}
}
