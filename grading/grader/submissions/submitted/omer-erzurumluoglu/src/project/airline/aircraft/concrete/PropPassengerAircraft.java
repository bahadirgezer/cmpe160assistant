package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class PropPassengerAircraft extends PassengerAircraft {

	static private double operationFee;

	public PropPassengerAircraft(Airport currentAirport) {
		super(currentAirport, 14000, 23000, 6000, 0.6, 0.9);
		floorArea = 60;
	}

	protected double getFlightCost(Airport toAirport) {
		return (getFullness() * currentAirport.getDistance(toAirport) * 0.1 + currentAirport.getDepartCost(this)
				+ toAirport.landAircraft(this));
	}

	protected double getFuelConsumption(double distance) {
		double disRatio = (distance / 2000);
		double bathtubCoef = (25.9324 * Math.pow(disRatio, 4) - 50.5633 * Math.pow(disRatio, 3)
				+ 35.0554 * Math.pow(disRatio, 2) - 9.90346 * disRatio + 1.97413);
		double anotherConstant = 0.08;
		return (weight * anotherConstant / fuelWeight + fuelConsumption * bathtubCoef * distance);
	}

	public void setOperationFee(double oF) {
		operationFee = oF;
	}

	public double getOperationFee() {
		return operationFee;
	}

}
