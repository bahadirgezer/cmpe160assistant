package project.airport;

import project.airline.aircraft.Aircraft;

public class MajorAirport extends Airport {

	public MajorAirport(int ID, int x, int y, double fuelCost, double operationFee, int aircraftCapacity) {
		super(ID, x, y, fuelCost, operationFee, aircraftCapacity);
		this.setPortType(2);
		this.operationFee = operationFee;
		
	}
	public double departAircraft(Aircraft aircraft) {
		
		double fullnessCoeff = 0.6 * Math.pow(Math.E, (double)this.getNumberOfAircrafts()/ (double)this.getAircraftCapacity());
		//System.out.print("fullness ");
		//System.out.println(fullnessCoeff);
		double weightRatio = (double)aircraft.getWeight() / aircraft.getMaxWeight();
		//System.out.print("weight ");
		//System.out.println(weightRatio);
		//System.out.println("depfee");
		//System.out.println(this.getOperationFee());
		this.setNumberOfAircrafts(this.getNumberOfAircrafts() - 1);
		return this.getOperationFee() * fullnessCoeff * weightRatio * 0.9;
	}
	public double landAircraft(Aircraft aircraft) {
		double fullnessCoeff = 0.6 * Math.pow(Math.E, (double)this.getNumberOfAircrafts()/this.getAircraftCapacity());
		double weightRatio = (double)aircraft.getWeight() / aircraft.getMaxWeight();
		this.setNumberOfAircrafts(this.getNumberOfAircrafts() + 1);
		aircraft.setCurrentAirport(this);
		return this.getOperationFee() * fullnessCoeff * weightRatio;
	}
}
