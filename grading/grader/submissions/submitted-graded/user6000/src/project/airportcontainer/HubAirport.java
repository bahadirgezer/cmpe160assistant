package project.airportcontainer;

import project.airlinecontainer.aircraftcontainer.Aircraft;

public class HubAirport extends Airport{
	public HubAirport(long ID,double x,double y,double fuelCost,double operationFee,int aircraftCapacity){	
		super(ID,x,y,fuelCost,operationFee,aircraftCapacity);
	}
	int numberofAircrafts;
	public double departAircraft(Aircraft aircraft) {
		
			double aircraftRatio=numberofAircrafts/aircraftCapacity;
			double aircraftWeightRatio=aircraft.weight()/aircraft.maxWeight();
			double fullnessCoefficient=0.6*Math.exp(aircraftRatio);
			double departureFee=operationFee*aircraftWeightRatio*fullnessCoefficient*0.7;
			this.aircraftsInside.remove(aircraft);
			numberofAircrafts--;
			return departureFee;
	}
	
	public double landAircraft(Aircraft aircraft) {
		double aircraftRatio=numberofAircrafts/aircraftCapacity;
		double aircraftWeightRatio=aircraft.weight()/aircraft.maxWeight();
		double fullnessCoefficient=0.6*Math.exp(aircraftRatio);
		double departureFee=operationFee*aircraftWeightRatio*fullnessCoefficient*0.8;
		numberofAircrafts++;
		this.aircraftsInside.add(aircraft);
		return departureFee;
		
	}
	
}
