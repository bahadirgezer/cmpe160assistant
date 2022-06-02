package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class WidebodyPassengerAircraft extends PassengerAircraft {
	
	protected double weight;
	protected double maxWeight;
	protected double floorArea;
	protected double fuelCapacity;
	protected double fuelConsumption;
	protected double aircraftTypeMultiplier;
	protected double operationFee;
	
	public WidebodyPassengerAircraft(double operationFeeWidebody, Airport currentAirport) {
		super.weight = 135000;
		super.maxWeight = 250000;
		super.floorArea = 450;
		super.fuelCapacity = 140000;
		this.fuelConsumption = 3.0;
		super.aircraftTypeMultiplier = 0.7;
		super.operationFee = operationFeeWidebody;
		super.currentAirport = currentAirport;
	}
	
	protected double getFlightCost(Airport toAirport) {
		return currentAirport.getDistance(toAirport)*getFullness()*0.15 + currentAirport.departAircraft(this) + toAirport.landAircraft(this);
	}
	
	protected double getFuelConsumption(double distance) {
		double bathtubCoefficient = 25.9324*Math.pow(distance/14000, 4) - 50.5633*Math.pow(distance/14000, 3) + 35.0554*Math.pow(distance/14000, 2) - 9.90346*distance/14000 + 1.97413;
		return weight*0.1/fuelWeight + fuelConsumption*distance*bathtubCoefficient;
	}
}
