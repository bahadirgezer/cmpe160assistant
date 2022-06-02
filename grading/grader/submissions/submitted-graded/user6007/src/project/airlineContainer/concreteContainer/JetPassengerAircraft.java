package project.airlineContainer.concreteContainer;

import project.airlineContainer.aircraftContainer.PassengerAircraft;
import project.airportContainer.Airport;


public class JetPassengerAircraft extends PassengerAircraft {
	protected double fuelConsumption = 0.7;
	private static double jetConstant = 0.08;
	
	public JetPassengerAircraft() {
		this.weight = 10000;
		this.maxWeight = 18000;
		this.floorArea = 30;
		this.fuelCapacity = 10000;
		this.aircraftTypeMultiplier = 5;
	}
	
	public double getFlightCost(Airport toAirport) {
		double distance = this.currentAirport.getDistance(toAirport);
		double fullness = this.getFullness();
		double flightOperationCost = distance*fullness*jetConstant;
		double departingFee = currentAirport.departAircraft(this);
		double landingFee = toAirport.landAircraft(this);
		return departingFee + flightOperationCost + landingFee;
	}
	
	public double getFuelConsumption(double distance) {
		double distanceRatio = distance/5000;
		double bathtub = getBathtubCurve(distanceRatio);
		double takeOffFuel = this.weight * 0.1 / fuelWeight;
		double cruiseFuel = this.fuelConsumption * bathtub * distance;
		return takeOffFuel + cruiseFuel;
	}
	
	public double getMaxFuelConsumption(double distance) {
		double distanceRatio = distance/5000;
		double bathtub = getBathtubCurve(distanceRatio);
		double takeOffFuel = this.maxWeight * 0.1 / fuelWeight;
		double cruiseFuel = this.fuelConsumption * bathtub * distance;
		return takeOffFuel + cruiseFuel;
	}
	
	


}
