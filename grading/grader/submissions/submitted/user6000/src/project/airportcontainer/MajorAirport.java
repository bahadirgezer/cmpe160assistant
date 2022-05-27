package project.airportcontainer;

import project.airlinecontainer.aircraftcontainer.Aircraft;

public class MajorAirport extends Airport{
	int numberofAircrafts;
	public MajorAirport(long ID,double x,double y,double fuelCost,double operationFee,int aircraftCapacity){	
		super(ID,x,y,fuelCost,operationFee,aircraftCapacity);
	}
	public double departAircraft(Aircraft aircraft) {
		
			double aircraftRatio=(double)numberofAircrafts/aircraftCapacity;
			double aircraftWeightRatio=(double)aircraft.weight()/aircraft.maxWeight();
			double fullnessCoefficient=0.6*Math.exp(aircraftRatio);
			double departureFee=operationFee*aircraftWeightRatio*fullnessCoefficient*0.9;
			this.aircraftsInside.remove(aircraft);
			numberofAircrafts--;
			return departureFee;
	}
	
	public double landAircraft(Aircraft aircraft) {
		double aircraftRatio=(double)numberofAircrafts/aircraftCapacity;
		double aircraftWeightRatio=(double)aircraft.weight()/aircraft.maxWeight();
		double fullnessCoefficient=0.6*Math.exp(aircraftRatio);
		double departureFee=operationFee*aircraftWeightRatio*fullnessCoefficient;
		this.aircraftsInside.add(aircraft);
		numberofAircrafts++;
		return departureFee;
		
	}
}
