package project.airline.aircraft;

import project.airport.Airport;
import project.interfaces.AircraftInterface;

public abstract class Aircraft implements AircraftInterface {
	
	protected Airport currentAirport;
	protected double weight;
	protected double maxWeight;
	protected double fuelWeight = 0.7;
	protected double fuel = 0;
	protected double fuelCapacity;
	protected double operationFee;
	protected double aircraftTypeMultiplier;
	
	public double fly(Airport toAirport) {
		double consumedFuel = getFuelConsumption(currentAirport.getDistance(toAirport));
		fuel -= consumedFuel;
		weight -= consumedFuel*fuelWeight;
		return getFlightCost(toAirport);
	}
	public double getWeightRatio() {
		return weight/maxWeight;
	}
	public boolean canFly(Airport toAirport) {
		if (fuel >= getFuelConsumption(currentAirport.getDistance(toAirport))) {
			if (toAirport.getCurrentAircraftNumber() < toAirport.getAircraftCapacity()) {
				return true;
			}
		}
		return false;
	}
	public boolean canAddFuel(double fuel) {
		if (weight + fuel*fuelWeight <= maxWeight) {
			if (this.fuel + fuel <= fuelCapacity && this.fuel + fuel >= 0) {
				return true;
			}
		}
		return false;
	}
	public double addFuel(double fuel) {
		this.fuel += fuel;
		weight += fuel*fuelWeight;
		return fuel*currentAirport.getFuelCost();
	}
	public boolean canFillUp() {
		if ((fuelCapacity-fuel)*fuelWeight + weight > maxWeight) {
			return false;
		}
		return true;
	}
	public double fillUp() {
		double cost = (fuelCapacity-fuel)*currentAirport.getFuelCost();
		fuel = fuelCapacity;
		weight += (fuelCapacity-fuel)*fuelWeight;
		return cost;
	}
	public boolean hasFuel(double fuel) {
		return (fuel <= this.fuel);
	}
	public double neededFuel(Airport toAirport, double minFuel, double maxFuel, double defaultFuel, int recursiveCall, boolean isFirstCall) {
		if (isFirstCall) {
			addFuelForMethod(-1*defaultFuel);
		}
		if (recursiveCall == 0) {
			if ((minFuel + maxFuel)/2 >= getFuelConsumption(currentAirport.getDistance(toAirport))) {
				addFuelForMethod(defaultFuel - getFuel());
				return (minFuel + maxFuel)/2;
			}
			addFuelForMethod(defaultFuel);
			return maxFuel;
		}
		addFuelForMethod(maxFuel/2);
		if ((minFuel + maxFuel)/2 >= getFuelConsumption(currentAirport.getDistance(toAirport))) {
			addFuelForMethod(-1*maxFuel/2);
			return neededFuel(toAirport, minFuel, maxFuel/2, defaultFuel, recursiveCall - 1, false);
		}
		return neededFuel(toAirport, maxFuel/2, maxFuel, defaultFuel, recursiveCall - 1, false);
	}
	protected void addFuelForMethod(double fuel) {
		this.fuel += fuel;
		weight += fuel*fuelWeight;
	}
	public Airport getCurrentAirport() {
		return currentAirport;
	}
	public void setCurrentAirport(Airport currentAirport) {
		this.currentAirport = currentAirport;
	}
	public double getOperationFee() {
		return operationFee;
	}
	public double getMaxWeight() {
		return maxWeight;
	}
	public double getFuel() {
		return fuel;
	}
	public double getFuelCapacity() {
		return fuelCapacity;
	}
	abstract protected double getFuelConsumption(double distance);
	abstract protected double getFlightCost(Airport toAirport);
}
