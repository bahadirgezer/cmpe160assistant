package project.airlineContainer.concreteContainer;

import project.airlineContainer.aircraftContainer.PassengerAircraft;
import project.airportContainer.*;

public class PropPassengerAircraft extends PassengerAircraft {
	protected double fuelConsumption = 0.6; 
	private static double propConstant = 0.1;
	
	public PropPassengerAircraft() {
		this.weight = 14000;
		this.maxWeight = 23000;
		this.floorArea = 60;
		this.fuelCapacity = 6000;
		this.aircraftTypeMultiplier = 0.9;
	}
	
	public double getFlightCost(Airport toAirport) {
		double distance = this.currentAirport.getDistance(toAirport);
		double fullness = this.getFullness();
		double flightOperationCost = distance*fullness*propConstant;
		double departingFee = currentAirport.departAircraft(this);
		double landingFee = toAirport.landAircraft(this);
		return departingFee + flightOperationCost + landingFee;
	}
	
	public double getFuelConsumption(double distance) {
		double distanceRatio = distance/2000;
		double bathtub = getBathtubCurve(distanceRatio);
		double takeOffFuel = this.weight * 0.08 / fuelWeight;
		double cruiseFuel = this.fuelConsumption * bathtub * distance;
		return takeOffFuel + cruiseFuel;
	}
	
	public double getMaxFuelConsumption(double distance) {
		double distanceRatio = distance/2000;
		double bathtub = getBathtubCurve(distanceRatio);
		double takeOffFuel = this.maxWeight * 0.08 / fuelWeight;
		double cruiseFuel = this.fuelConsumption * bathtub * distance;
		return takeOffFuel + cruiseFuel;
	}

	



}
