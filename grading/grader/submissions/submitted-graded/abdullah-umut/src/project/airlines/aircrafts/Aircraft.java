package project.airlines.aircrafts;

import project.airports.Airport;
import project.interfaces.AircraftInterface;

public abstract class Aircraft implements AircraftInterface {

	protected Airport currentAirport;
	protected double weight;
	protected double maxWeight;
	protected double fuelWeight = 0.7;
	protected double fuel = 0;
	protected double fuelCapacity;
	protected double operationFee;
	protected double fuelConsumption;
	protected double aircraftTypeMultiplier;
	
	Aircraft(double operationFee, Airport currentAirport){
		this.operationFee = operationFee;
	}
	
	public boolean canFly(Airport toAirport) {
		return (this.fuel >= getFuelConsumption(getCurrentAirport().getDistance(toAirport))) && (toAirport.hasCapacity());
	}
	
	public double fly(Airport toAirport) {
		this.fuel -= getFuelConsumption(getCurrentAirport().getDistance(toAirport));
		currentAirport = toAirport;
		return getFlightCost(toAirport);
		
	}
	
	protected abstract double getFlightCost(Airport toAirport);
	protected abstract double getFuelConsumption(double distance);

	public boolean canAddFuel(double fuel) {
		return (this.fuel + fuel <= fuelCapacity) && (weight + fuel*fuelWeight <= maxWeight);
	}
	
	public boolean canFillUp() {
		return canAddFuel(fuelCapacity-fuel);
	}
	
	@Override
	public double addFuel(double fuel) {
		weight += fuel*fuelWeight;
		this.fuel += fuel;
		return getCurrentAirport().getFuelCost()*fuel;
	}

	@Override
	public double fillUp() {
		return addFuel(fuelCapacity-fuel);
	}

	public double fillUpFuel() {
		return fuelCapacity-fuel;
	}
	
	@Override
	public boolean hasFuel(double fuel) {
		return (this.fuel >= fuel);
	}

	
	
	
	
	@Override
	public double getWeightRatio() {
		return weight/maxWeight;
	}
	
	protected double bathtub(double distanceRatio){
		return 25.9324*Math.pow(distanceRatio, 4) - 50.5633*Math.pow(distanceRatio, 3) +  35.0554*Math.pow(distanceRatio, 2) - 9.90346*Math.pow(distanceRatio, 1) + 1.97413;
	}

	public Airport getCurrentAirport() {
		return currentAirport;
	}
}
