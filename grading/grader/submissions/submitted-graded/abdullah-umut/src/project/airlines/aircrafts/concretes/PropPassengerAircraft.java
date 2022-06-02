package project.airlines.aircrafts.concretes;

import project.airlines.aircrafts.PassengerAircraft;
import project.airports.Airport;

public class PropPassengerAircraft extends PassengerAircraft {
	
	public PropPassengerAircraft(double operationFee, Airport currentAirport) {
		super(operationFee, currentAirport);
		weight = 14000;
		maxWeight = 23000;
		floorArea = 60;
		fuelCapacity = 6000;
		fuelConsumption = 0.6;
		aircraftTypeMultiplier = 0.9;
	}

	protected double getFlightCost(Airport toAirport){
		double fullness = getFullness();
		double distance = getCurrentAirport().getDistance(toAirport);
		double flightOperationCost = 0.1*fullness*distance;
		double departureCost = getCurrentAirport().departAircraft(this);
		double landingCost =  toAirport.landAircraft(this);
		return flightOperationCost + departureCost + landingCost;
		
	}
	
	protected double getFuelConsumption(double distance){
		double distanceRatio = distance/2000;
		double takeoffFuelConsumption = weight*0.08/fuelWeight;
		double cruiseFuelConsumption = fuelConsumption*bathtub(distanceRatio)*distance;
		return takeoffFuelConsumption + cruiseFuelConsumption;
	}
}
