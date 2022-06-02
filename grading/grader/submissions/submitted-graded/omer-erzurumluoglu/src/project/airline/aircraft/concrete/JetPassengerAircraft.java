package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class JetPassengerAircraft extends PassengerAircraft {
	
	static private double operationFee;
	
	public JetPassengerAircraft(Airport currentAirport) {
		super(currentAirport, 10000, 18000, 10000, 0.7, 5.0);
		floorArea = 30;

	}

	protected double getFlightCost(Airport toAirport) {
		return getFullness() * currentAirport.getDistance(toAirport) * 0.08 + currentAirport.getDepartCost(this)
				+ toAirport.landAircraft(this);
	}

	protected double getFuelConsumption(double distance) {
		double disRatio = (distance / 5000);
		double bathtubCoef = (25.9324 * Math.pow(disRatio, 4) - 50.5633 * Math.pow(disRatio, 3)
				+ 35.0554 * Math.pow(disRatio, 2) - 9.90346 * disRatio + 1.97413);
		double anotherConstant = 0.1;
		return (weight * anotherConstant / fuelWeight + fuelConsumption * bathtubCoef * distance);
	}

	public void setOperationFee(double oF) {
		operationFee = oF;
	}

	public double getOperationFee() {
		return operationFee;
	}

}
