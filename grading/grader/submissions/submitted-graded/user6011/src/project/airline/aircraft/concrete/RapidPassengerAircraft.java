package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class RapidPassengerAircraft extends PassengerAircraft {
	
	protected double weight;
	protected double maxWeight;
	protected double floorArea;
	protected double fuelCapacity;
	protected double fuelConsumption;
	protected double aircraftTypeMultiplier;
	protected double operationFee;
	
	public RapidPassengerAircraft(double operationFeeRapid, Airport currentAirport) {
		super.weight = 80000;
		super.maxWeight = 185000;
		super.floorArea = 120;
		super.fuelCapacity = 120000;
		this.fuelConsumption = 5.3;
		super.aircraftTypeMultiplier = 1.9;
		super.operationFee = operationFeeRapid;
		super.currentAirport = currentAirport;
	}
	
	protected double getFlightCost(Airport toAirport) {
		return currentAirport.getDistance(toAirport)*getFullness()*0.2 + currentAirport.departAircraft(this) + toAirport.landAircraft(this);
	}
	
	protected double getFuelConsumption(double distance) {
		double bathtubCoefficient = 25.9324*Math.pow(distance/7000, 4) - 50.5633*Math.pow(distance/7000, 3) + 35.0554*Math.pow(distance/7000, 2) - 9.90346*distance/7000 + 1.97413;
		return weight*0.1/fuelWeight + fuelConsumption*distance*bathtubCoefficient;
	}
}
