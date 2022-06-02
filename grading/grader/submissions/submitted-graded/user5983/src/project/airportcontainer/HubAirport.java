package project.airportcontainer;

import project.aircraftcontainer.Aircraft;

public class HubAirport extends Airport {
	private double departureConstant=0.7;
	private double landingConstant=0.8;
	
			
	
	public HubAirport(long ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		// TODO Auto-generated constructor stub
		super(ID,x,y,fuelCost,operationFee,aircraftCapacity);
		
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
		return 1;
	}
	
	

}
