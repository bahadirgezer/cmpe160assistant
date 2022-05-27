package project.airlinecontainer.aircraftcontainer.concretecontainer;

import project.airlinecontainer.aircraftcontainer.PassengerAircraft;
import project.airportcontainer.Airport;

public class JetPassengerAircraft extends PassengerAircraft{
	public JetPassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport, operationFee);
		weight = 10000;
		maxWeight = 18000;
		floorArea = 30;
		fuelCapacity = 10000; //Volume
		fuelConsumption = 0.7;
		aircraftTypeMultiplier = 5;
	}
	
	public double getFlightCost(Airport toAirport) {
		double flightCost;
		double flightOperationCost = this.getFullness() * currentAirport.getDistance(toAirport) * 0.08;
		flightCost = flightOperationCost + currentAirport.departAircraft(this) + toAirport.landAircraft(this);
		return flightCost;
	}
	
	public double bathtubFunction(double x) {
		return (25.9324 * Math.pow(x, 4)) - (50.5633 * Math.pow(x, 3)) + (35.0554 * Math.pow(x, 2)) - (9.90346 * x) + (1.97413);
	}
	
	protected double getFuelConsumption(double distance) {
		double distanceRatio = distance / 5000;
		double bathtubCoefficient = bathtubFunction(distanceRatio);
		double takeoff = this.weight * 0.1 / fuelWeight;
		double cruise = fuelConsumption * bathtubCoefficient * distance;
		double totalFuel = takeoff + cruise;
		return totalFuel;
	}
}
