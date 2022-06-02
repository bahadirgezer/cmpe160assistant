package project.airlineContainer.concreteContainer;

import project.airportContainer.Airport;
import project.airlineContainer.aircraftContainer.*;

public class RapidPassengerAircraft extends PassengerAircraft {

	protected double fuelConsumption = 5.3;
	private static double rapidConstant = 0.2;
	
	public RapidPassengerAircraft() {
		this.weight = 80000;
		this.maxWeight = 185000;
		this.floorArea = 120;
		this.fuelCapacity = 120000;
		this.aircraftTypeMultiplier = 1.9;
	}
	
	public double getFlightCost(Airport toAirport) {
		double distance = this.currentAirport.getDistance(toAirport);
		double fullness = this.getFullness();
		double flightOperationCost = distance*fullness*rapidConstant;
		double departingFee = currentAirport.departAircraft(this);
		double landingFee = toAirport.landAircraft(this);
		return departingFee + flightOperationCost + landingFee;
	}
	
	public double getFuelConsumption(double distance) {
		double distanceRatio = distance/7000;
		double bathtub = getBathtubCurve(distanceRatio);
		double takeOffFuel = this.weight * 0.1 / fuelWeight;
		double cruiseFuel = this.fuelConsumption * bathtub * distance;
		return takeOffFuel + cruiseFuel;
	}
	
	public double getMaxFuelConsumption(double distance) {
		double distanceRatio = distance/7000;
		double bathtub = getBathtubCurve(distanceRatio);
		double takeOffFuel = this.maxWeight * 0.1 / fuelWeight;
		double cruiseFuel = this.fuelConsumption * bathtub * distance;
		return takeOffFuel + cruiseFuel;
	}

}
