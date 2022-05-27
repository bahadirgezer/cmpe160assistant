package project.airportContainer;

import project.airlines.aircraftcontainer.Aircraft;

public class HubAirport extends Airport {

	
	public HubAirport(int ID, int x, int y, double fuelCost, double operationFee, int aircraftCapacity) {
		super(ID, x, y, fuelCost, operationFee, aircraftCapacity);
		this.setPortType(1);
	}
	
	public double departAircraft(Aircraft aircraft) {
		
		double fullnessCoeff = 0.6 * Math.pow(Math.E, this.getNumberOfAircrafts()/this.getAircraftCapacity());
		double weightRatio = aircraft.getWeight() / aircraft.getMaxWeight();
		this.setNumberOfAircrafts(getNumberOfAircrafts() - 1);
		return this.getOperationFee() * fullnessCoeff * weightRatio * 0.7;
		
	}
	public double landAircraft(Aircraft aircraft) {
		double fullnessCoeff = 0.6 * Math.pow(Math.E, this.getNumberOfAircrafts()/this.getAircraftCapacity());
		double weightRatio = aircraft.getWeight() / aircraft.getMaxWeight();
		this.setNumberOfAircrafts(getNumberOfAircrafts() + 1);
		aircraft.setCurrentAirport(this);
		return this.getOperationFee() * fullnessCoeff * weightRatio * 0.8;
	}

}
