package project.airlineContainer.concreteContainer;

import project.airlineContainer.aircraftContainer.PassengerAircraft;
import project.airportContainer.Airport;

public class WidebodyPassengerAircraft extends PassengerAircraft {
	
	protected double fuelConsumption = 3.0;
	private static double wideConstant = 0.15;
	
	public WidebodyPassengerAircraft() {
		this.weight = 135000;
		this.maxWeight = 250000;
		this.floorArea = 450;
		this.fuelCapacity = 140000;
		this.aircraftTypeMultiplier = 0.7;
	}
	
	public double getFlightCost(Airport toAirport) {
		double distance = this.currentAirport.getDistance(toAirport);
		double fullness = this.getFullness();
		double flightOperationCost = distance*fullness*wideConstant;
		double departingFee = currentAirport.departAircraft(this);
		double landingFee = toAirport.landAircraft(this);
		return departingFee + flightOperationCost + landingFee;
	}
	
	public double getFuelConsumption(double distance) {
		double distanceRatio = distance/14000;
		double bathtub = getBathtubCurve(distanceRatio);
		double takeOffFuel = this.weight * 0.1 / fuelWeight;
		double cruiseFuel = this.fuelConsumption * bathtub * distance;
		return takeOffFuel + cruiseFuel;
	}
	
	public double getMaxFuelConsumption(double distance) {
		double distanceRatio = distance/14000;
		double bathtub = getBathtubCurve(distanceRatio);
		double takeOffFuel = this.maxWeight * 0.1 / fuelWeight;
		double cruiseFuel = this.fuelConsumption * bathtub * distance;
		return takeOffFuel + cruiseFuel;
	}
	


	

}
