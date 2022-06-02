package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class PropPassengerAircraft extends PassengerAircraft {
	
	public PropPassengerAircraft(Airport initAirport, double operationFee) {
		super(initAirport, operationFee);
		super.weight = 14000;
		super.maxWeight = 23000;
		super.floorArea = 60;
		super.aircraftTypeMultiplier = 0.6;
		super.fuelCapacity = 6000;
		super.operationFee = operationFee;
		this.operationFee  = operationFee;
		// TODO Auto-generated constructor stub
	}

	protected double weight = 14000;
	protected double maxWeight = 23000;
	protected double floorArea = 60;
	protected double fuelCapacity = 6000;
	protected double fuelConsumption = 0.6;
	protected double aircraftTypeMultiplier = 0.9;
	protected double operationFee;
	
	protected double getFlightCost(Airport toAirport) {
		double departureCost = currentAirport.departAircraft(this);
		double dist = currentAirport.getDistance(toAirport);
		double fullness = getFullness();
		double aircraftConstant = 0.1;
		double flightOperationCost = dist * fullness * aircraftConstant;
		double landingCost = toAirport.landAircraft(this);
		double flightCost = flightOperationCost + landingCost + departureCost;
		return flightCost;
	}
	
	protected double getFuelConsumption(double distance) {
		double dstRatio = distance / 2000;
		double bathtubCoefficient = 25.9324 * Math.pow(dstRatio, 4) - 50.5633 * Math.pow(dstRatio, 3) + 35.0554 * Math.pow(dstRatio, 2) - 9.90346 * dstRatio + 1.97413;
		double takeoffFuelConsumption = (weight * 0.08) / fuelWeight;
		double cruiseFuelConsumption = bathtubCoefficient * distance * fuelConsumption;
		double finalFuelConsumption = takeoffFuelConsumption + cruiseFuelConsumption;
		return finalFuelConsumption;
	}
	
	

}
