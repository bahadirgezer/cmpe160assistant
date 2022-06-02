package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class JetPassengerAircraft extends PassengerAircraft {
	
	protected double weight;
	protected double maxWeight;
	protected double floorArea;
	protected double fuelCapacity;
	protected double fuelConsumption;
	protected double aircraftTypeMultiplier;
	protected double operationFee;
	
	public JetPassengerAircraft(double operationFeeJet, Airport currentAirport) {
		super.weight = 10000;	
		super.maxWeight = 18000;
		super.floorArea = 30;
		super.fuelCapacity = 10000;
		this.fuelConsumption = 0.7;
		super.aircraftTypeMultiplier = 5;
		super.operationFee = operationFeeJet;
		super.currentAirport = currentAirport;
	}
	
	protected double getFlightCost(Airport toAirport) {
		return currentAirport.getDistance(toAirport)*getFullness()*0.08 + currentAirport.departAircraft(this) + toAirport.landAircraft(this);
	}
	
	protected double getFuelConsumption(double distance) {
		double bathtubCoefficient = 25.9324*Math.pow(distance/5000, 4) - 50.5633*Math.pow(distance/5000, 3) + 35.0554*Math.pow(distance/5000, 2) - 9.90346*distance/5000 + 1.97413;
		return weight*0.1/fuelWeight + fuelConsumption*distance*bathtubCoefficient;
	}
}
