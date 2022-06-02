package project.airlinecontainer.aircraftcontainer.concretecontainer;

import project.airlinecontainer.aircraftcontainer.PassengerAircraft;
import project.airportcontainer.Airport;

public class RapidPassengerAircraft extends PassengerAircraft {
	public RapidPassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport, operationFee);
		super.weight = 80000;
		super.maxWeight = 185000;
		super.floorArea = 120;
		super.fuelCapacity = 120000;
		
		super.aircraftTypeMultiplier = 1.9;
	}
	protected double fuelConsumption = 5.3;
	
	public double getFlightCost(Airport toAirport) {
		double distance = this.getdistance(super.currentAirport, toAirport);
		double flightOperationCost = 0.2*distance*super.getFullness();
		return super.currentAirport.departAircraft(this) + toAirport.landAircraft(this) + flightOperationCost;
	}
	public double getFuelConsumption(double distance) {
		double distanceRatio = distance/7000.0;
		double bathtubCoefficient = 25.9324*Math.pow(distanceRatio, 4) - 50.5633*Math.pow(distanceRatio, 3) + 35.0554*Math.pow(distanceRatio, 2) - 9.90346*Math.pow(distanceRatio, 1) + 1.97413;
		double takeoffFuelConsumption = weight*0.1/super.fuelWeight;
		double cruiseFuelConsumption = bathtubCoefficient*distance*this.fuelConsumption;
		return takeoffFuelConsumption + cruiseFuelConsumption;
	}
}
