package project.airlines.aircraftcontainer;


import project.airportContainer.Airport;
import project.interfacescontainer.AircraftInterface;

public abstract class Aircraft implements AircraftInterface {
	protected Airport currentAirport;
	protected double weight;
	protected double maxWeight;
	protected double fuelWeight=0.7;
	protected double fuel;
	protected double fuelCapacity;
	protected double fuelConsumption;
	protected double operationFee;
	public Aircraft(Airport currentAirport, double operationFee) {
		
	}

	public Airport getCurrentAirport() {
		return currentAirport;
	}
	public void setCurrentAirport(Airport currentAirport) {
		this.currentAirport = currentAirport;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}
	public void setFuelWeight(double fuelWeight) {
		this.fuelWeight = fuelWeight;
	}
	public void setFuel(double fuel) {
		this.fuel = fuel;
	}
	public void setFuelCapacity(double fuelCapacity) {
		this.fuelCapacity = fuelCapacity;
	}
	public double getWeight() {
		return weight;
	}
	public double getMaxWeight() {
		return maxWeight;
	}
	public double getFuelWeight() {
		return fuelWeight;
	}
	public double getFuel() {
		return fuel;
	}
	public double getFuelCapacity() {
		return fuelCapacity;
	}
	public abstract double getFuelConsumption(double distance);
	
	public double fly(Airport toAirport) {
		double cost = this.getCurrentAirport().departAircraft(this) + toAirport.landAircraft(this);
		this.setFuel(this.getFuel()-this.getFuelConsumption(currentAirport.distanceToAirport(toAirport)));
		return cost;
	}
	public double addFuel(double fuel) {
		if(this.getFuel() + fuel <= this.getFuelCapacity()) {
			this.setWeight(fuel * this.getFuelWeight());
			this.setFuel(this.getFuel() + fuel);
			return fuel * this.getCurrentAirport().getFuelCost();
		} else {
			return 0;
		}
	}
	public double fillUp() {
		double fuelAdded = (this.getFuelCapacity()-this.getFuel());
		this.setWeight((this.getFuelCapacity()-this.getFuel()) * this.getFuelWeight());
		this.setFuel(this.getFuelCapacity());
		return fuelAdded * this.getCurrentAirport().getFuelCost();
	}
	public boolean hasFuel(double fuel) {
		if(this.getFuel() >= fuel) return true;
		else return false;
	}
	public double getWeightRatio() {
		return this.getWeight() / this.getMaxWeight();
	}
	
	
	
}
