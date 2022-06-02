package project.airportcontainer;

import project.aircraftcontainer.Aircraft;

public class RegionalAirport extends Airport {
	

	private double departureConstant=1.2;
	private double landingConstant=1.3;
	
	
	public RegionalAirport(long iD2, double x2, double y2, double fuelCost2, double operationFee2,
						   int aircraftCapacity2) {
		super(iD2, x2, y2, fuelCost2, operationFee2, aircraftCapacity2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double departAircraft(Aircraft aircraft) {
		// TODO Auto-generated method stub
		return operationFee*getFullnessCoefficient()*aircraft.getWeightRatio()*departureConstant;
	}

	@Override
	public double landAircraft(Aircraft aircraft) {
		// TODO Auto-generated method stub
		return operationFee*getFullnessCoefficient()*aircraft.getWeightRatio()*landingConstant;
	}
	
	public int getType() {
		return 3;
	}

}
