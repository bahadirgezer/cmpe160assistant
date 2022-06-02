package project.airline.aircraft;

import java.util.DuplicateFormatFlagsException;


import project.airport.Airport;
import project.interfaces.AircraftInterface;

public abstract class Aircraft implements AircraftInterface{
	protected Airport currentAirport;
	protected double weight; // initial and weight at any moment
	protected double maxWeight; // weight <= maxWeight
	protected double fuelWeight = 0.7;
	protected double fuel = 0; // amount of fuel at any moment. Initially 0
	protected double fuelCapacity;
	protected double operationalCost;
	
	public Aircraft(Airport currentAirport, double operationalCost) {
		this.currentAirport = currentAirport;
		this.operationalCost = operationalCost;
	}
	
	public Airport getCurrentAirport() {
		return this.currentAirport;
	}
	
	public void setCurrentAirport(Airport toAirport) {
		this.currentAirport = toAirport;
	}
	
	public double fly(Airport toAirport){
		return this.getFlightCost(toAirport);
	}
	
	public boolean flyCheck(Airport toAirport) {
		if (this.getFuel() >= this.getFuelConsumption(this.currentAirport.getDistance(toAirport)))
			return true;
		return false;
	}
	
	public double getOperationalCost() {
		return this.operationalCost;
	}
	
	// REFUELING FUNCTIONS
	
	public double addFuel(double fuel) {
		if (this.getFuel() + fuel > this.fuelCapacity) 
			return 0;
		this.weight += this.fuelWeight * fuel;
		this.fuel += fuel;
		return this.currentAirport.getFuelCost() * fuel;
	}

	public double fillUp() {
		if (this.getWeight() + (this.fuelCapacity - this.fuel) * fuelWeight > maxWeight) {
			double amount = (this.maxWeight - this.getWeight() - this.fuel)/(double)(fuelWeight);
			double cost = this.currentAirport.getFuelCost() * amount;
			this.fuel = fuelCapacity;
			return cost;
		}
		this.weight += this.fuelWeight * (this.fuelCapacity - this.fuel);
		double cost = this.currentAirport.getFuelCost() * (this.fuelCapacity - this.fuel);
		this.fuel = fuelCapacity;
		return cost;
	}
	
	public boolean hasFuel(double fuel) {
		return this.getFuel() == fuel ? true : false;
	}
	
	public double getWeightRatio() {
		return this.weight /(double) this.maxWeight;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getFuel() {
		return fuel;
	}
	
	public double getFuelCapacity() {
		return this.fuelCapacity;
	}
	
	
	public abstract double getFlightCost(Airport toAirport);
	public abstract double getFuelConsumption(double distance);
}
