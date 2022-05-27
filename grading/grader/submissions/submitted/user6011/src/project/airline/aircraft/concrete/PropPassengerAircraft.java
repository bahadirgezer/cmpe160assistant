package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class PropPassengerAircraft extends PassengerAircraft {
	
	protected double weight;
	protected double maxWeight;
	protected double floorArea;
	protected double fuelCapacity;
	protected double fuelConsumption;
	protected double aircraftTypeMultiplier;
	protected double operationFee;
	
	public PropPassengerAircraft(double operationFeeProp, Airport currentAirport) {
		super.weight = 14000;
		super.maxWeight = 23000;
		super.floorArea = 60;
		super.fuelCapacity = 6000;
		this.fuelConsumption = 0.6;
		super.aircraftTypeMultiplier = 0.9;
		super.operationFee = operationFeeProp;
		super.currentAirport = currentAirport;
	}
	
	protected double getFlightCost(Airport toAirport) {
		return currentAirport.getDistance(toAirport)*getFullness()*0.1 + currentAirport.departAircraft(this) + toAirport.landAircraft(this);
	}
	
	protected double getFuelConsumption(double distance) {
		double bathtubCoefficient = 25.9324*Math.pow(distance/2000, 4) - 50.5633*Math.pow(distance/2000, 3) + 35.0554*Math.pow(distance/2000, 2) - 9.90346*distance/2000 + 1.97413;
		return weight*0.08/fuelWeight + fuelConsumption*distance*bathtubCoefficient;
	}
}
