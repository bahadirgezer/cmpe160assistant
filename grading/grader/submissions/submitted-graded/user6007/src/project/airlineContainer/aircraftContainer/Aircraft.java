package project.airlineContainer.aircraftContainer;

//import java.util.ArrayList;

import project.airportContainer.Airport;
import project.interfacesContainer.AircraftInterface;
import java.util.ArrayList;
import project.passengerContainer.*;
//import project.passengerContainer.Passenger;

//import project.passengerContainer.*;

public abstract class Aircraft implements AircraftInterface {
	protected Airport currentAirport;
	protected double weight;
	protected double maxWeight;
	protected double fuelWeight = 0.7;
	protected double fuel;
	protected double fuelCapacity;
	protected double aircraftTypeMultiplier;
	
	
	public int getCurrentAirport() {
		return this.currentAirport.getID();
	}
	public Airport getCurrentAirportObj() {
		return this.currentAirport;
	}
	public void setCurrentAirport(Airport airport){
		this.currentAirport = airport;
		
		
	}
	
	public double getFuelCapacity() {
		return this.fuelCapacity;
	}
	
	
	
	//necessary boolean helpers
	public boolean canFly(Airport toAirport) {
		if (toAirport.isFull()) {
			return false;
		}
		double fuel = this.getMaxFuelConsumption(this.currentAirport.getDistance(toAirport));
		if (this.fuel - fuel < 0) {
			return false;
		}
		if (currentAirport.equals(toAirport)) {
			return false;
		}
		return true;
		
	}
	/**
	 * public double fly(Airport toAirport)
	 * Does the necessary fuel operations, calculates the flight cost and returns 
	 * the flight cost.
	 * @param toAirport the destination
	 * @return flightCost cost of the flight
	 */
	public double fly(Airport toAirport) {
		double distance = currentAirport.getDistance(toAirport);
		double flightCost = this.getFlightCost(toAirport);
		double fuel = this.getFuelConsumption(distance);
		this.fuel -= fuel;
	
		return flightCost;
	}
	
	public double getWeightRatio() {
		return this.weight/this.maxWeight;
	}
	
	//abstract methods to help method calling
	public abstract double getFuelConsumption(double distance);
	public abstract double getMaxFuelConsumption(double distance);
	public abstract double getFlightCost(Airport toAirport);
	public abstract int getAvailableEconomy();
	public abstract int getAvailableBusiness();
	public abstract int getAvailableFirstClass();
	public abstract boolean setSeats (int economy, int business, int firstclass);
	public abstract boolean setAllEconomy();
	public abstract boolean setAllBusiness();
	public abstract boolean setAllFirstClass();
	public abstract boolean setRemainingEconomy();
	public abstract boolean setRemainingBusiness();
	public abstract boolean setRemainingFirstClass();
	public abstract double getFloorArea();
	public abstract void setOperationCost(double operationCost);
	public abstract double getAircraftMultiplier();
	public abstract ArrayList<Passenger> getPassengers();
	
	//fuel operations
	public double receiveFuelCost(double fuelCost) {
		return fuelCost;
	}
	/**
	 * Checks if the specified amount of fuel can be added to aircraft.
	 * If the desired fuel amount exceeds the maximum fuel capacity or the maximum weight, 
	 * fuel cannot be loaded.
	 * @param double fuel
	 * @return true or false
	 */
	public boolean canAddFuel(double fuel) {
		if (this.fuel + fuel > fuelCapacity)
			return false;
		else if (weight + fuel*fuelWeight > maxWeight)
			return false;
		else
			return true;
	}
	
	/**Adds fuel to the plane.
	 * @param fuel
	 * @return the cost of the fuel added.
	 */
	public double addFuel(double fuel) {
		double cost = fuel*currentAirport.getFuelCost();
		this.fuel += fuel;
		this.weight += fuel*fuelWeight;
		return cost;
	}
	
	/**Fills the fuel of the plane.
	 * @param fuel
	 * @return the cost of the fuel added.
	 */
	public double fillUp() {
		double fuelAmount = fuelCapacity - fuel;
		double cost = currentAirport.getFuelCost()*(fuelAmount);
		this.fuel += fuelAmount;
		this.weight += (fuelAmount)*this.fuelWeight;
		return cost;
	}
	
	public boolean hasFuel() {
		if (this.fuel != 0)
			return true;
		else
			return false;
	}
	public double getFuel() {
		return fuel;
	}
}
