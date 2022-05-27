package project.airline.aircraft;

import project.airport.Airport;
import project.interfaces.AircraftInterface;

public abstract class Aircraft implements AircraftInterface {
	protected Airport currentAirport;
	protected int ID;
	protected double weight;
	protected double maxWeight;
	protected double fuelWeight = 0.7;
	protected double fuel = 0;
	protected double fuelCapacity;
	protected double fuelConsumption;
	protected double aircraftTypeMultiplier;
	public Aircraft(Airport currentAirport,double weight,double maxWeight,double fuelCapacity,double fuelConsumption,double aircraftTypeMultiplier) {
		this.currentAirport = currentAirport;
		this.weight = weight;
		this.maxWeight = maxWeight;
		this.fuelCapacity = fuelCapacity;
		this.fuelConsumption = fuelConsumption;
		this.aircraftTypeMultiplier = aircraftTypeMultiplier;
	}
	public boolean canFly(Airport toAirport) {
		if (fuel >= getFuelConsumption(currentAirport.getDistance(toAirport))){
			return true;
		}
		return false;
	}
	protected abstract double getFuelConsumption(double distance);
	@Override
	public double fly(Airport toAirport) {
		double flightCost = getFlightCost(toAirport);
		double distance = currentAirport.getDistance(toAirport);
		double spentFuel = getFuelConsumption(distance);
		flightCost+= currentAirport.departAircraft(this);
		flightCost += toAirport.landAircraft(this);
		fuel -= spentFuel;
		weight -= spentFuel*fuelWeight;
		return flightCost;
	}
	protected abstract double getFlightCost(Airport toAirport);
	@Override
	public double addFuel(double fuel) {
		this.fuel += fuel;
		this.weight += fuel * fuelWeight;
		return fuel * currentAirport.getFuelCost();
	}
	@Override
	public double fillUp() {
		double emptyFuel = fuelCapacity - fuel;
		fuel += emptyFuel;
		weight += emptyFuel * fuelWeight;
		return emptyFuel * currentAirport.getFuelCost();
	}
	public double fillForAirport(Airport toAirport) {
		if (canFly(toAirport)==false){
			double neededFuel = (getFuelConsumption(currentAirport.getDistance(toAirport))-fuel)*1.112;
			return addFuel(neededFuel);
		}
		return 0;
	}
	@Override
	public boolean hasFuel(double fuel) {
		if (this.fuel >= fuel) {
			return true;
		}
		return false;
	}
	@Override
	public double getWeightRatio() {
		return weight/maxWeight;
	}
	public Airport getCurrentAirport() {
		return currentAirport;
	}
	public double getWeight() {
		return weight;
	}
	public double getMaxWeight() {
		return maxWeight;
	}
	public double getAircraftTypeMultiplier() {
		return aircraftTypeMultiplier;
	}
	public int getID() {
		return ID;
	}
	public void setCurrentAirport(Airport airport) {
		currentAirport = airport;
	}
}
