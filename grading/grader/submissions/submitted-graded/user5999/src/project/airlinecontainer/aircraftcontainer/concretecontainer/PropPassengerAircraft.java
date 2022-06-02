package project.airlinecontainer.aircraftcontainer.concretecontainer;

import project.airlinecontainer.aircraftcontainer.PassengerAircraft;
import project.airportcontainer.Airport;

public class PropPassengerAircraft extends PassengerAircraft {
	public PropPassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport, operationFee);
		super.weight = 14000;
		super.maxWeight = 23000;
		super.floorArea = 60;
		super.fuelCapacity = 6000;
		super.aircraftTypeMultiplier = 0.9;
	}
	protected double fuelConsumption = 0.6;
	public double getFlightCost(Airport toAirport) {
		double distance = this.getdistance(super.currentAirport, toAirport);
		double flightOperationCost = 0.1*distance*super.getFullness();
		return super.currentAirport.departAircraft(this) + toAirport.landAircraft(this) + flightOperationCost;
	}
	public double getFuelConsumption(double distance) {
		double distanceRatio = distance/2000.0;
		double bathtubCoefficient = 25.9324*Math.pow(distanceRatio, 4) - 50.5633*Math.pow(distanceRatio, 3) + 35.0554*Math.pow(distanceRatio, 2) - 9.90346*Math.pow(distanceRatio, 1) + 1.97413;
		double takeoffFuelConsumption = weight*0.08/super.fuelWeight;
		double cruiseFuelConsumption = bathtubCoefficient*distance*this.fuelConsumption;
		return takeoffFuelConsumption + cruiseFuelConsumption;
	}

}
