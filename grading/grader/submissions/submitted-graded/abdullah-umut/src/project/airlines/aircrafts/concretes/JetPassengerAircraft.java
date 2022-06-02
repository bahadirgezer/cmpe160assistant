package project.airlines.aircrafts.concretes;

import project.airlines.aircrafts.PassengerAircraft;
import project.airports.Airport;

public class JetPassengerAircraft extends PassengerAircraft {
	
	public JetPassengerAircraft(double operationFee, Airport currentAirport) {
		super(operationFee, currentAirport);
		weight = 10000;
		maxWeight = 18000;
		floorArea = 30;
		fuelCapacity = 10000;
		fuelConsumption = 0.7;
		aircraftTypeMultiplier = 5;
	}

	protected double getFlightCost(Airport toAirport){
		double fullness = getFullness();
		double distance = getCurrentAirport().getDistance(toAirport);
		double flightOperationCost = 0.08*fullness*distance;
		double departureCost = getCurrentAirport().departAircraft(this);
		double landingCost =  toAirport.landAircraft(this);
		return flightOperationCost + departureCost + landingCost;
		
	}
	
	protected double getFuelConsumption(double distance){
		double distanceRatio = distance/5000;
		double takeoffFuelConsumption = weight*0.1/fuelWeight;
		double cruiseFuelConsumption = fuelConsumption*bathtub(distanceRatio)*distance;
		return takeoffFuelConsumption + cruiseFuelConsumption;
	}}
