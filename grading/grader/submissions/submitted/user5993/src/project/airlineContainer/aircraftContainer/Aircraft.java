package project.airlineContainer.aircraftContainer;

import java.util.ArrayList;

import project.airportContainer.Airport;
import project.interfacesContainer.AircraftInterface;
import project.passengerContainer.Passenger;

public abstract class Aircraft implements AircraftInterface {
	protected Airport currentAirport;
	protected double weight;
	protected double maxWeight;
	protected double fuelWeight=0.7;
	protected double fuel=0;
	protected double fuelCapacity;
	protected double operationFee;
	
	public void setOperationFee(double operationFee) {
		this.operationFee = operationFee;
	}
	public double getOperationFee() {
		return operationFee;
	}
	public ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	public ArrayList<Passenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(ArrayList<Passenger> passengers) {
		this.passengers = passengers;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public Aircraft(Airport currentAirport, double weight, double maxWeight, double fuelWeight, double fuel,
			double fuelCapacity,double operationFee) {
		super();
		this.currentAirport = currentAirport;
		this.weight = weight;
		this.maxWeight = maxWeight;
		this.fuelWeight = fuelWeight;
		this.fuel = fuel;
		this.fuelCapacity = fuelCapacity;
		this.operationFee=operationFee;
	}
	
	protected abstract double getFuelConsumption(double distance);
	abstract double getFlightCost(Airport toAirport);
	public double getMaxWeight() {
		return maxWeight;
	}
	public double getWeight() {
		return weight;
	}
	public Airport getCurrentAirport() {
		return currentAirport;
	}
	public void setCurrentAirport(Airport currentAirport) {
		this.currentAirport = currentAirport;
	}
	public double getWeightRatio() {
		return this.getWeight()/this.getMaxWeight();
	}
	
	public boolean canFly(Airport toAirport) {
		if (toAirport.isFull()) {
			return false;
		}
		double distance=this.currentAirport.getDistance(toAirport);
		double fuelConsumption=this.getFuelConsumption(distance);

		
		if (this.fuel<fuelConsumption) {
			return false;
		}
		return true;
		
	}
	
	
	public double fly(Airport toAirport) {
		
		if (this.canFly(toAirport)) {
			double distance=this.currentAirport.getDistance(toAirport);
			double fuelConsumption=this.getFuelConsumption(distance);
			double FlightCost =this.getFlightCost(toAirport);
			this.weight-=fuelConsumption*fuelWeight;
			this.fuel-=fuelConsumption;
			toAirport.aircrafts.add(this);
			this.currentAirport.aircrafts.remove(this);
			this.currentAirport=toAirport;
			return FlightCost ;
		}
		return 0;
	}
	
	public boolean hasFuel(double fuel) {
		if (fuel<=this.fuel) {
			return true;
		}
		else {
			return false;
		}
	}
	public double addFuel(double newFuel) {
		if ((this.weight+newFuel*this.fuelWeight<=this.maxWeight) && (this.fuel+newFuel<=this.fuelCapacity)) {
		this.fuel+=newFuel;
		this.weight+=newFuel*this.fuelWeight;
		return newFuel*this.currentAirport.getFuelCost();
		}
		else {
			return 0;
		}
		
	}
	public double fillUp() {
		double maxFuelVolume=this.fuelCapacity-this.fuel;
		double maxFuelWeight=(this.maxWeight-this.weight)/this.fuelWeight;
		if (maxFuelVolume<maxFuelWeight) {
			return this.addFuel(maxFuelVolume);
		}
		else {
			return this.addFuel(maxFuelWeight);
		}
	}
	


}
