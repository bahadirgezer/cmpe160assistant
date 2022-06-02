package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class WidebodyPassengerAircraft extends PassengerAircraft {
	
	public WidebodyPassengerAircraft(Airport initAirport, double operationFee) {
		super(initAirport, operationFee);
		super.weight = 135000;
		super.maxWeight = 250000;
		super.floorArea = 450;
		super.aircraftTypeMultiplier = 0.7;
		super.fuelCapacity = 140000;
		super.operationFee = operationFee;
		this.operationFee  = operationFee;
		// TODO Auto-generated constructor stub
	}

	protected double weight = 135000;
	protected double maxWeight = 250000;
	protected double floorArea = 450;
	protected double fuelCapacity = 140000;
	protected double fuelConsumption = 3.0;
	protected double aircraftTypeMultiplier = 0.7;
	protected double operationFee;
	
	protected double getFlightCost(Airport toAirport){
		double fullness = getFullness();
		double departureCost = currentAirport.departAircraft(this);
		double dist = currentAirport.getDistance(toAirport);
		double aircraftConstant = 0.15;
		double flightOperationCost = dist * fullness * aircraftConstant;
		double landingCost = toAirport.landAircraft(this);
		double flightCost = flightOperationCost + landingCost + departureCost;
		return flightCost;
	}
	
	protected double getFuelConsumption(double distance) {
		double dstRatio = distance / 14000;
		double bathtubCoefficient = 25.9324 * Math.pow(dstRatio, 4) - 50.5633 * Math.pow(dstRatio, 3) + 35.0554 * Math.pow(dstRatio, 2) - 9.90346 * dstRatio + 1.97413;
		double takeoffFuelConsumption = (super.weight * 0.1) / super.fuelWeight;
		double cruiseFuelConsumption = bathtubCoefficient * distance * fuelConsumption;
		double finalFuelConsumption = takeoffFuelConsumption + cruiseFuelConsumption;
		return finalFuelConsumption;
	}
	
}
