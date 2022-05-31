package project.airlines.aircrafts.concretes;

import project.airlines.aircrafts.PassengerAircraft;
import project.airports.Airport;

public class WidebodyPassengerAircraft extends PassengerAircraft {

	public WidebodyPassengerAircraft(double operationFee, Airport currentAirport) {
		super(operationFee, currentAirport);
		weight = 135000;
		maxWeight = 250000;
		floorArea = 450;
		fuelCapacity = 140000;
		fuelConsumption = 3;
		aircraftTypeMultiplier = 0.7;
	}

	protected double getFlightCost(Airport toAirport){
		double fullness = getFullness();
		double distance = getCurrentAirport().getDistance(toAirport);
		double flightOperationCost = 0.15*fullness*distance;
		double departureCost = getCurrentAirport().departAircraft(this);
		double landingCost =  toAirport.landAircraft(this);
		return flightOperationCost + departureCost + landingCost;
		
	}
	
	protected double getFuelConsumption(double distance){
		double distanceRatio = distance/2000;
		double takeoffFuelConsumption = weight*0.1/fuelWeight;
		double cruiseFuelConsumption = fuelConsumption*bathtub(distanceRatio)*distance;
		return takeoffFuelConsumption + cruiseFuelConsumption;
	}
}
