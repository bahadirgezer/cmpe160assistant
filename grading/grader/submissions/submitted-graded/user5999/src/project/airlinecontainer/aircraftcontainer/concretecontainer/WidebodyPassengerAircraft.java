package project.airlinecontainer.aircraftcontainer.concretecontainer;

import project.airlinecontainer.aircraftcontainer.PassengerAircraft;
import project.airportcontainer.Airport;

public class WidebodyPassengerAircraft extends PassengerAircraft {
	public WidebodyPassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport, operationFee);
		super.weight = 135000;
		super.maxWeight = 250000;
		super.floorArea = 450;
		super.fuelCapacity = 140000;
		super.aircraftTypeMultiplier = 0.7;
	}
	private double fuelConsumption = 3.0;

	public double getFlightCost(Airport toAirport) {
		
		double distance = this.getdistance(super.currentAirport, toAirport);
		double flightOperationCost = 0.15*distance*super.getFullness();
		double depart = super.currentAirport.departAircraft(this); double land = toAirport.landAircraft(this);
		double x = depart + land +flightOperationCost;
		return x;
	}
	public double getFuelConsumption(double distance) {
		double distanceRatio = distance/14000.0;
		double bathtubCoefficient = 25.9324*Math.pow(distanceRatio, 4) - 50.5633*Math.pow(distanceRatio, 3) + 35.0554*Math.pow(distanceRatio, 2) - 9.90346*Math.pow(distanceRatio, 1) + 1.97413;
		double takeoffFuelConsumption = super.weight*0.1/super.fuelWeight;
		double cruiseFuelConsumption = bathtubCoefficient*distance*this.fuelConsumption;
		return takeoffFuelConsumption + cruiseFuelConsumption;
	}
}
