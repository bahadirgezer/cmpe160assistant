package project.airlinecontainer.aircraftcontainer.concretecontainer;

import project.airlinecontainer.aircraftcontainer.PassengerAircraft;
import project.airportcontainer.Airport;

public class RapidPassengerAircraft extends PassengerAircraft{
	public RapidPassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport, operationFee);
		weight = 80000;
		maxWeight = 185000;
		floorArea = 120;
		fuelCapacity = 120000; //Volume
		fuelConsumption = 5.3;
		aircraftTypeMultiplier = 1.9;
	}
	
	public double getFlightCost(Airport toAirport) {
		double flightCost;
		double flightOperationCost = this.getFullness() * currentAirport.getDistance(toAirport) * 0.2;
		flightCost = flightOperationCost + currentAirport.departAircraft(this) + toAirport.landAircraft(this);
		return flightCost;
	}
	
	public double bathtubFunction(double x) {
		return (25.9324 * Math.pow(x, 4)) - (50.5633 * Math.pow(x, 3)) + (35.0554 * Math.pow(x, 2)) - (9.90346 * x) + (1.97413);
	}
	
	protected double getFuelConsumption(double distance) {
		double distanceRatio = distance / 7000;
		double bathtubCoefficient = bathtubFunction(distanceRatio);
		double takeoff = this.weight * 0.1 / fuelWeight;
		double cruise = fuelConsumption * bathtubCoefficient * distance;
		double totalFuel = takeoff + cruise;
		return totalFuel;
	}
}
