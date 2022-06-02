package project.airline_container.aircraft_container;

import java.util.HashMap;

import project.airport_container.Airport;
import project.interfaces_container.AircraftInterface;
import project.passenger_container.Passenger;
import project.utils.LogWriter;
import project.utils.Util;

public abstract class Aircraft implements AircraftInterface {

	private double fuelDensity = 0.7;
	
	protected Airport currentAirport;
	protected double weight;
	protected double maxWeight;
	protected double fuel = 0;
	protected double fuelWeight = 0;
	protected double fuelCapacity;
	
	private double fuelConsumption;
	private double operationFee;
	private double aircraftTypeMultiplier;
	private double lastFuelCost;
	
	protected int ID;
	private static int aircraftCounter = 0; 
	
	protected static String[] operationFeeList;
	
	private HashMap<Long, Passenger> passengers = new HashMap<>();
	
	public Aircraft() {
		super();
		ID = aircraftCounter++;
	}
	
	public double getFuelConsumption() {
		return fuelConsumption;
	}

	public void setFuelConsumption(double fuelConsumption) {
		this.fuelConsumption = fuelConsumption;
	}

	public double getOperationFee() {
		return operationFee;
	}

	public void setOperationFee(double operationFee) {
		this.operationFee = operationFee;
	}

	public void setAircraftTypeMultiplier(double aircraftTypeMultiplier) {
		this.aircraftTypeMultiplier = aircraftTypeMultiplier;
	}

	public double getLastFuelCost() {
		return lastFuelCost;
	}
	
	public void setLastFuelCost(double fuel) {
		lastFuelCost = fuel * currentAirport.getFuelCost();
	}
	
	@Override
	public double fly(Airport toAirport) {
		double fuelConsumption = getFuelConsumption(Util.findDistance(currentAirport, toAirport));
		fuel -= fuelConsumption;
		weight -= fuelConsumption * fuelDensity;
		return getFlightCost(toAirport);
	}
	
	@Override
	public boolean addFuel(double fuel) {
		if(this.fuel + fuel < fuelCapacity && fuel * fuelDensity + weight <= maxWeight) {
			setLastFuelCost(fuel);
			this.fuel += fuel;
			weight = fuel * fuelDensity;
			LogWriter.write("3 " + ID + " " + fuel + " = -" + lastFuelCost);
			return true;
		}
		return false;
	}
	
	public boolean dumpFuel(double amount) {
		if (fuel < amount) {
			return false;
		}
		fuel -= amount;
		weight -= fuel * fuelDensity;
		LogWriter.write("3 " + ID + " -" + amount + " = 0.0");
		return true;
	}

	@Override
	public boolean fillUp() {
		double requiredFuel = fuelCapacity - fuel;
		if (requiredFuel * fuelDensity + weight > maxWeight) {
			return false;
		}
		setLastFuelCost(requiredFuel);
		LogWriter.write("3 " + ID + " -" + requiredFuel + " = -" + lastFuelCost);
		fuel = fuelCapacity;
		weight = fuel * fuelDensity;
		return true;
	}

	@Override
	public boolean hasFuel(double fuel) {
		return fuel > 0;
	}

	@Override
	public double getWeightRatio() {
		return weight / maxWeight;
	}

	public static void setOperationFeeList(String[] operationFeeList) {
		Aircraft.operationFeeList = operationFeeList;
	}

	public HashMap<Long, Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(HashMap<Long, Passenger> passengers) {
		this.passengers = passengers;
	}
	
	public void addPassenger(Passenger passenger) {
		passengers.put(passenger.getID(), passenger);
	}

	public double getAircraftTypeMultiplier() {
		return aircraftTypeMultiplier;
	}
	
	public int getID() {
		return ID;
	}

	public abstract double getFuelConsumption(double distance);
	
	public abstract double getFlightCost(Airport toAirport);

}
