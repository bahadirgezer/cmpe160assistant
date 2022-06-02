package project.airportcontainer;

import project.airlinecontainer.aircraftcontainer.Aircraft;

public class RegionalAirport extends Airport{
	int numberofAircrafts;
	public RegionalAirport(long ID,double x,double y,double fuelCost,double operationFee,int aircraftCapacity){	
		super(ID,x,y,fuelCost,operationFee,aircraftCapacity);
	}
	public double departAircraft(Aircraft aircraft) {
		double aircraftRatio=(double)numberofAircrafts/aircraftCapacity;
		double aircraftWeightRatio=(double)aircraft.weight()/aircraft.maxWeight();
		double fullnessCoefficient=0.6*Math.exp(aircraftRatio);
		double departureFee=operationFee*aircraftWeightRatio*fullnessCoefficient*1.2;
		this.aircraftsInside.remove(aircraft);
		numberofAircrafts--;
		return departureFee;
	}
	public double landAircraft(Aircraft aircraft) {
		double aircraftRatio=(double)numberofAircrafts/aircraftCapacity;
		double aircraftWeightRatio=(double)aircraft.weight()/aircraft.maxWeight();
		double fullnessCoefficient=0.6*Math.exp(aircraftRatio);
		double departureFee=operationFee*aircraftWeightRatio*fullnessCoefficient*1.3;
		this.aircraftsInside.add(aircraft);
		numberofAircrafts++;
		return departureFee;
	}
	
}
