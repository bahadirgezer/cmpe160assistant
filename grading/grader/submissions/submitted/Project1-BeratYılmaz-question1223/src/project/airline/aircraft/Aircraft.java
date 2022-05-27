package project.airline.aircraft;

import java.util.ArrayList;

import project.airport.Airport;
import project.interfaces.AircraftInterface;

public abstract class Aircraft implements AircraftInterface {
	protected Airport currentAirport;
	protected double weight;
	protected double operationFee;
	protected double maxWeight;
	protected double fuelWeight = 0.7;
	protected double fuel;
	protected double fuelCapacity;
	
	protected ArrayList<Airport> visitedAirports = new ArrayList<Airport>();
 	
	protected abstract double getFuelConsumption(double distance);
	protected abstract double getFlightCost(Airport toAirport);
	
	public Aircraft(Airport initAirport, double operationFee) {
		this.currentAirport = initAirport;
		this.operationFee = operationFee;
	}
	
	public void setCurrentAirport(Airport airport) {
		this.currentAirport = airport;
	}
	
	public double fly(Airport toAirport) {
		Airport currentAirport = this.getCurrentAirport();
		double dist = currentAirport.getDistance(toAirport);
		double flightCost = getFlightCost(toAirport);
		double fuelConsumption = getFuelConsumption(dist);
		this.fuel -= fuelConsumption;
		this.weight -= fuelConsumption * fuelWeight;
		this.setCurrentAirport(toAirport);
		toAirport.incrementAircraftCount();
		currentAirport.decrementAircraftCount();
		this.pushToVisited(currentAirport);
		this.pushToVisited(toAirport);
		return flightCost;
	}
	public boolean canFly(Airport toAirport) {
		double dist = currentAirport.getDistance(toAirport);
		double fuelConsumption = getFuelConsumption(dist);
		if (this.hasFuel(fuelConsumption) && !(toAirport.isFull())) {
			return true;
		}
		return false;
		
	}
	public double getWeightRatio() {
		double weightRatio = weight / maxWeight;
		return weightRatio;
	}
	
	public boolean hasFuel(double fuel) {
		return this.fuel >= fuel;
	}
	
	public double getFuelCapacity() {
		return this.fuelCapacity;
	}
	

	public double addFuel(double fuelAddedAmount) {
		if (canAddFuel(fuelAddedAmount)) {
		this.fuel += fuelAddedAmount;
		this.weight += fuelAddedAmount * fuelWeight;
		double totalFuelCost = currentAirport.getFuelCost() * fuelAddedAmount; 
		return totalFuelCost;
		}
		return 0;
	}
	
	public boolean canAddFuel(double fuelAddedAmount) {
		return this.fuel + fuelAddedAmount <= fuelCapacity && this.weight + fuelAddedAmount * fuelWeight <= maxWeight;
	}
	
	public boolean canFillUp() {
		return (this.fuelCapacity - this.fuel) * fuelWeight + this.weight <= this.maxWeight;
	}
	
	public double fillUp() {
		if (canFillUp()) {
			double toFill = this.fuelCapacity - this.fuel;
			this.fuel += toFill;
			this.weight += toFill * fuelWeight;
			double totalFuelCost = currentAirport.getFuelCost() * toFill;
			return totalFuelCost;
		}
		
		else {
			return 0;
		}
	}
	
	public double getFuelConsumptionofDistance(double distance) {
		return this.getFuelConsumption(distance);
	}
	
	public Airport getCurrentAirport() {
		return this.currentAirport;
	}
	
	public void pushToVisited(Airport airport) {
		this.visitedAirports.add(airport);
	}
	
	public ArrayList<Airport> getVisited() {
		return this.visitedAirports;
	}
	
	public double getFuel() {
		return this.fuel;
	}
}	

