package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class JetPassengerAircraft extends PassengerAircraft {
	
	public JetPassengerAircraft(Airport initAirport, double operationFee) {
		super(initAirport, operationFee);
		super.weight = 10000;
		super.maxWeight = 18000;
		super.floorArea = 30;
		super.fuelCapacity = 10000;
		super.aircraftTypeMultiplier = 5;
		super.operationFee = operationFee;
		// TODO Auto-generated constructor stub
	}


	protected double weight = 10000;
	protected double maxWeight = 18000;
	protected double floorArea = 30;
	public double fuelCapacity = 10000;
	protected double fuelConsumption = 0.7;
	protected double aircraftTypeMultiplier = 5;
	protected double operationFee;
	
	protected double getFlightCost(Airport toAirport) {
		double departureCost = currentAirport.departAircraft(this);
		double dist = currentAirport.getDistance(toAirport);
		double fullness = getFullness();
		double aircraftConstant = 0.08;
		double flightOperationCost = dist * fullness * aircraftConstant;
		double landingCost = toAirport.landAircraft(this);
		double flightCost = flightOperationCost + landingCost + departureCost;
		return flightCost;
	}
	
	protected double getFuelConsumption(double distance) {
		double dstRatio = distance / 5000;
		double bathtubCoefficient = 25.9324 * Math.pow(dstRatio, 4) - 50.5633 * Math.pow(dstRatio, 3) + 35.0554 * Math.pow(dstRatio, 2) - 9.90346 * dstRatio + 1.97413;
		double takeoffFuelConsumption = (weight * 0.1) / fuelWeight;
		double cruiseFuelConsumption = bathtubCoefficient * distance * fuelConsumption;
		double finalFuelConsumption = takeoffFuelConsumption + cruiseFuelConsumption;
		return finalFuelConsumption;
	}
	

	



}
