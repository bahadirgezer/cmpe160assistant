package project.airline.aircraft;

import java.util.ArrayList;

import project.airline.*;
import project.airport.Airport;
import project.interfaces.AircraftInterface;
import project.passenger.Passenger;

public abstract class Aircraft implements AircraftInterface {
	
	
	protected Airport currentAirport;
	protected double weight;
	protected double maxWeight;
	protected double fuelWeight = 0.7;
	protected double fuel = 0;
	protected double fuelCapacity;
	protected double currentWeight;
	private double aircraftTypeMultiplier;
	protected double departCost;
	protected double landingCost;
	
	
	
	public Aircraft(double weight, double maxWeight, double fuelCapacity, Airport ID, double aircraftTypeMultiplier) {
		this.weight = weight;
		this.maxWeight = maxWeight;
		this.fuelCapacity = fuelCapacity;
		this.currentWeight = weight;
		this.aircraftTypeMultiplier = aircraftTypeMultiplier;
		this.currentAirport = ID;
	}
	
	
	public double fly(Airport toAirport) { //will be executed if boolean fly in airline is true
		double distance = this.currentAirport.getDistance(toAirport);
		this.setDeaprtCost(this.currentAirport.departAircraft(this));
		this.setLoadingCost(toAirport.landAircraft(this));
		this.fuel -= this.getFuelConsumption(distance);
		this.currentWeight -= this.getFuelConsumption(distance)*this.fuelWeight;
		Airline.log += String.format("1 %d %d\n", toAirport.getID(), Airline.aircrafts.indexOf(this));
		double cost = this.getFlightCost(toAirport);
		this.currentAirport.removeAircraft(toAirport);
		toAirport.addAircraft(this.currentAirport, Airline.aircrafts.indexOf(this));
		this.currentAirport=toAirport;
		return cost;
	}
	
	private void setLoadingCost(double landAircraft) {
		this.landingCost = landAircraft;
		
	}
	protected double getLandingCost() {
		return this.landingCost;
	}


	public abstract double loadPassenger(Passenger passenger);
	protected abstract double getFlightCost(Airport toAirport);
	public abstract double getFuelConsumption(double distance);


	public double getCurrentWeight() {
		return this.currentWeight;
	}
	
	public double getMaxWeight() {
		return this.maxWeight;
	}
	
	public double getWeight() {
		return this.weight;
	}
	public double getDepartCost() {
		return this.departCost;
	}
	public void setDeaprtCost(double depart) {
		this.departCost = depart;
	}

	@Override
	public double addFuel(double fuel) {
		if (this.fuel+fuel>this.fuelCapacity) {
			double diff = this.fuelCapacity-this.fuel;
			this.fuel = this.fuelCapacity;
			this.currentWeight += diff*this.fuelWeight;
			return diff*this.currentAirport.getFuelCost();
		}
		else if (this.currentWeight+fuel*this.fuelWeight>this.maxWeight) {
			double diff = (this.maxWeight-this.currentWeight)/this.fuelWeight;
			this.currentWeight += diff*this.fuelWeight;
			this.fuel+= diff;
			return diff*this.currentAirport.getFuelCost();
		}
		else {
			Airline.log+= String.format("3 %d %f\n", Airline.aircrafts.indexOf(this), fuel);

			this.fuel += fuel;
			this.currentWeight += fuel*this.fuelWeight;
			return fuel*this.currentAirport.getFuelCost();
			}
	}
	
	public double dumpFuel(double fuel) {
		if (this.fuel < fuel) {
			return 0;
		}
		else {
			Airline.log += String.format("3 %d -%f\n",  Airline.aircrafts.indexOf(this), fuel);

			this.fuel -= fuel;
			this.weight -= fuel*this.fuelWeight;
			return 0;
			
		}
	}

	@Override
	public double fillUp() {
		if (this.currentWeight+(this.fuelCapacity-this.fuel)*this.fuelWeight>this.maxWeight) {
			System.out.println("hey");
		return 0;
		}
		else {
			double addedFuel = this.fuelCapacity - this.fuel;
			Airline.log += String.format("3 %d %f\n", Airline.aircrafts.indexOf(this), (this.fuelCapacity-this.fuel));

			this.currentWeight += addedFuel*this.fuelWeight;
			this.fuel = this.fuelCapacity;
			return addedFuel*this.currentAirport.getFuelCost();
		}
	}

	@Override
	public boolean hasFuel(double fuel) {
		if (fuel<=this.fuel) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public double getWeightRatio() {
		return this.currentWeight/this.maxWeight;
	}
	public Airport getCurrentAirport() {
		return currentAirport;
	}
	public void setCurrentAirport(Airport currentAirport) {
		this.currentAirport = currentAirport;
	}


	public abstract double getAvailableWeight();

	public double getFuel() {
		return this.fuel;
	}
	
	public abstract double unloadPassenger(Passenger passenger);
	public abstract boolean hasEconomySeat();
	public abstract boolean hasFirstClassSeat();
	public abstract boolean hasBusinessSeat();


	public abstract boolean setAllEconomy();
	public abstract boolean setSeats(int economy, int business, int firstClass);

	public abstract ArrayList<Passenger> getPassengers();

	public double getFuelCapacity() {
		return this.fuelCapacity;
	}


	public double getAircraftTypeMultiplier() {
		return aircraftTypeMultiplier;
	}
}
