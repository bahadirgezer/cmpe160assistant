package project.airlinecontainer.aircraftcontainer.concretecontainer;

import project.airlinecontainer.aircraftcontainer.PassengerAircraft;
import project.airportcontainer.Airport;

public class PropPassengerAircraft extends PassengerAircraft{
	public PropPassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport, operationFee);
		weight = 14000;
		maxWeight = 23000;
		floorArea = 60;
		fuelCapacity = 6000; //Volume
		fuelConsumption = 0.6;
		aircraftTypeMultiplier = 0.9;
	}
	
	public double getFlightCost(Airport toAirport) {
		double flightCost;
		double flightOperationCost = this.getFullness() * currentAirport.getDistance(toAirport) * 0.1;
		flightCost = flightOperationCost + currentAirport.departAircraft(this) + toAirport.landAircraft(this);
		return flightCost;
	}
	
	public double bathtubFunction(double x) {
		return (25.9324 * Math.pow(x, 4)) - (50.5633 * Math.pow(x, 3)) + (35.0554 * Math.pow(x, 2)) - (9.90346 * x) + (1.97413);
	}
	
	protected double getFuelConsumption(double distance) {
		double distanceRatio = distance / 2000;
		double bathtubCoefficient = bathtubFunction(distanceRatio);
		double takeoff = this.weight * 0.08 / fuelWeight;
		double cruise = fuelConsumption * bathtubCoefficient * distance;
		double totalFuel = takeoff + cruise;
		return totalFuel;
	}
}
