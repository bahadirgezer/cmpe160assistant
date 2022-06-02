package project.airlinecontainer.aircraftcontainer.concretecontainer;

import project.airlinecontainer.aircraftcontainer.PassengerAircraft;
import project.airportcontainer.Airport;

public class JetPassengerAircraft extends PassengerAircraft {
	public JetPassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport, operationFee);
		super.weight = 10000;
		super.maxWeight = 18000;
		super.floorArea = 30;
		super.fuelCapacity = 10000;
		super.aircraftTypeMultiplier = 5;
	}
	protected double fuelConsumption = 0.7;
	public double getFlightCost(Airport toAirport) {
		double distance = this.getdistance(super.currentAirport, toAirport);
		double flightOperationCost = 0.08*distance*super.getFullness();
		return super.currentAirport.departAircraft(this) + toAirport.landAircraft(this) + flightOperationCost;
	}
	public double getFuelConsumption(double distance) {
		double distanceRatio = distance/5000.0;
		double bathtubCoefficient = 25.9324*Math.pow(distanceRatio, 4) - 50.5633*Math.pow(distanceRatio, 3) + 35.0554*Math.pow(distanceRatio, 2) - 9.90346*Math.pow(distanceRatio, 1) + 1.97413;
		double takeoffFuelConsumption = weight*0.1/super.fuelWeight;
		double cruiseFuelConsumption = bathtubCoefficient*distance*this.fuelConsumption;
		return takeoffFuelConsumption + cruiseFuelConsumption;
	}

}
