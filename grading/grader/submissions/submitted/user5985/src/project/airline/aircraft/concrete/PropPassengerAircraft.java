package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;


public class PropPassengerAircraft extends PassengerAircraft{
	protected double fuelConsumption = 0.6;
	
	public PropPassengerAircraft(Airport currentAirport, double operationalCost) {
		super(currentAirport, operationalCost);
		this.setWeight(14000);
		this.maxWeight = 23000;
		this.floorArea = 60; // seat area
		this.fuelCapacity = 6000; // fuel volume
		this.aircraftTypeMultiplier = 0.9;	
	}
	
	public double getFlightCost(Airport toAirport) {
		double distance = this.currentAirport.getDistance(toAirport);
		double departureCost = this.currentAirport.departAircraft(this);
		double landingCost = toAirport.landAircraft(this);
		double fullness = this.getFullness();		
		double flightOperationCost = fullness * distance * 0.1;
		return landingCost + departureCost + flightOperationCost;
	}
	
	public double getFuelConsumption(double distance) {
		double distanceParameter = distance /(double) 2000;
		double bathtubCoef = 25.9324 * Math.pow(distanceParameter, 4) - 50.5633 * Math.pow(distanceParameter, 3)
				+ 35.0554 *  Math.pow(distanceParameter, 2) - 9.90346 * distanceParameter + 1.97413;
		double takeOffCons = this.getWeight() * 0.08 / this.fuelWeight;
		double cruiseCons = this.fuelConsumption * bathtubCoef * distance;
		return takeOffCons + cruiseCons;
	}
}
