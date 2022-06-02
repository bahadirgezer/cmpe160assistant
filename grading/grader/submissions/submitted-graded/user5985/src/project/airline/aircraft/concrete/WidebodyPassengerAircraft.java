package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class WidebodyPassengerAircraft extends PassengerAircraft{
	protected double fuelConsumption = 3.0;
	
	public WidebodyPassengerAircraft(Airport currentAirport, double operationalCost) {
		super(currentAirport, operationalCost);
		this.setWeight(135000);
		this.maxWeight = 250000;
		this.floorArea = 450; // seat area
		this.fuelCapacity = 140000; // fuel volume
		this.aircraftTypeMultiplier = 0.7;
	}
	
	public double getFlightCost(Airport toAirport) {
		double distance = this.currentAirport.getDistance(toAirport);
		double departureCost = this.currentAirport.departAircraft(this);
		double landingCost = toAirport.landAircraft(this);
		double fullness = this.getFullness();		
		double flightOperationCost = fullness * distance * 0.15;
		return landingCost + departureCost + flightOperationCost;
	}
	
	public double getFuelConsumption(double distance) {
		double distanceParameter = distance /(double) 14000.;
		double bathtubCoef = 25.9324 * Math.pow(distanceParameter, 4) - 50.5633 * Math.pow(distanceParameter, 3)
				+ 35.0554 *  Math.pow(distanceParameter, 2) - 9.90346 * distanceParameter + 1.97413;
		double takeOffCons = this.getWeight() * 0.1 / this.fuelWeight;
		double cruiseCons = this.fuelConsumption * bathtubCoef * distance;
		return takeOffCons + cruiseCons;
	}
}
