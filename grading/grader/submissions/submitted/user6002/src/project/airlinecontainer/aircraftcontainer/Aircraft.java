package project.airlinecontainer.aircraftcontainer;

import java.util.ArrayList;

import project.airportcontainer.Airport;
import project.interfacescontainer.AircraftInterface;
import project.passengercontainer.Passenger;

public abstract class Aircraft implements AircraftInterface{
	protected Airport currentAirport;
	protected double weight;
	protected double maxWeight;
	protected double fuelWeight = 0.7;
	protected double fuel; //0 at initialization.
	protected double fuelCapacity;
	protected double fuelConsumption;
	protected double aircraftTypeMultiplier;
	public double operationFee;
	
	public Aircraft(Airport currentAirport, double operationFee) {
		this.currentAirport = currentAirport;
		this.operationFee = operationFee;
	}
	public abstract ArrayList<Passenger> getPassengersAtAircraft();
	protected abstract double getFuelConsumption(double distance);
	public abstract double getFlightCost(Airport toAirport);
	public abstract int getEconomySeats();
	public abstract int getBusinessSeats();
	public abstract int getFirstClassSeats();
	
	public double getFuel() {
		return fuel;
	}
	
	public double getFuelCapacity() {
		return fuelCapacity;
	}
	
	public double getWeightRatio() {
		return weight / maxWeight;
	}
	
	public Airport getCurrentAirport() {
		return currentAirport;
	}
	
	public void setCurrentAirport(Airport airport) {
		this.currentAirport = airport;
	}
	
	public boolean canFly(Airport toAirport) {
		if (getFuelConsumption(currentAirport.getDistance(toAirport)) > fuel) {
			return false;
		} else {
			return true;
		}
	}
	
	public double fly(Airport toAirport) {
		double fuelAmount = getFuelConsumption(currentAirport.getDistance(toAirport));
		fuel -= fuelAmount;
		weight -= fuelAmount * fuelWeight;
		return this.getFlightCost(toAirport);
	}
	
	public boolean addFuel(double fuelAmount) {
		if (fuel + fuelAmount <= fuelCapacity) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasFuel(double fuelAmount) {
		if (fuel >= fuelAmount) {
			return true;
		} else {
			return false;
		}
	}
	
	public double refuel(double fuelAmount) {
		fuel += fuelAmount;
		weight += fuelAmount * fuelWeight;
		double fuelCost = fuelAmount * currentAirport.getFuelCost();
		return fuelCost;
	}
	
	public boolean fulle() {
		return true;
	}
	
	public double maxRefuel() {
		double fuelAmount = this.fuelCapacity - this.fuel;
		fuel += fuelAmount;
		weight += fuelAmount * fuelWeight;
		double fuelCost = fuelAmount * currentAirport.getFuelCost();
		return fuelCost;
	}
}
