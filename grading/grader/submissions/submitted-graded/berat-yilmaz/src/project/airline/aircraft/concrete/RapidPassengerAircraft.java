package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class RapidPassengerAircraft extends PassengerAircraft {
	
	public RapidPassengerAircraft(Airport initAirport, double operationFee) {
		super(initAirport, operationFee);
		super.weight = 80000;
		super.maxWeight = 185000;
		super.floorArea = 120;
		super.aircraftTypeMultiplier = 1.9;
		super.fuelCapacity = 120000;
		super.operationFee = operationFee;
		this.operationFee  = operationFee;
		// TODO Auto-generated constructor stub
	}

	protected double weight = 80000;
	protected double maxWeight = 185000;
	protected double floorArea = 120;
	protected double fuelCapacity = 120000;
	protected double fuelConsumption = 5.3;
	protected double aircraftTypeMultiplier = 1.9;
	protected double operationFee;
	
	protected double getFlightCost(Airport toAirport){
		double departureCost = currentAirport.departAircraft(this);
		double dist = currentAirport.getDistance(toAirport);
		double fullness = getFullness();
		double aircraftConstant = 0.2;
		double flightOperationCost = dist * fullness * aircraftConstant;
		double landingCost = toAirport.landAircraft(this);
		double flightCost = flightOperationCost + landingCost + departureCost;
		return flightCost;
	}
	
	protected double getFuelConsumption(double distance) {
		double dstRatio = distance / 7000;
		double bathtubCoefficient = 25.9324 * Math.pow(dstRatio, 4) - 50.5633 * Math.pow(dstRatio, 3) + 35.0554 * Math.pow(dstRatio, 2) - 9.90346 * dstRatio + 1.97413;
		double takeoffFuelConsumption = (super.weight * 0.1) / super.fuelWeight;
		double cruiseFuelConsumption = bathtubCoefficient * distance * fuelConsumption;
		double finalFuelConsumption = takeoffFuelConsumption + cruiseFuelConsumption;
		return finalFuelConsumption;
	}
	
	

}


