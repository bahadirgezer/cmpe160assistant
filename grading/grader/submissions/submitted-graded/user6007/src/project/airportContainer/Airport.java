package project.airportContainer;

import java.lang.Math;

import project.airlineContainer.aircraftContainer.Aircraft;
import project.passengerContainer.*;
import java.util.ArrayList;

public abstract class Airport {
	private final int ID;
	private final double x, y;
	protected double fuelCost;
	protected double operationFee;
	protected int aircraftCapacity;
	protected int aircraftCount;
	public ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	public ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
	
	
	public int getEconomy() {
		int counter = 0;
		for (Passenger i: this.passengers) {
			if (i.passengerType == 0) {
				counter++;
			}	
		}
		return counter;
	}
	public int getBusiness() {
		int counter = 0;
		for (Passenger i: this.passengers) {
			if (i.passengerType == 1) {
				counter++;
			}	
		}
		return counter;
	}
	public int getFirstClass() {
		int counter = 0;
		for (Passenger i: this.passengers) {
			if (i.passengerType == 2) {
				counter++;
			}	
		}
		return counter;
	}
	public int getLuxury() {
		int counter = 0;
		for (Passenger i: this.passengers) {
			if (i.passengerType == 3) {
				counter++;
			}	
		}
		return counter;
	}
	
	
	
	
	public String toString() {
		return "[" + this.ID + "," + this.x + "," + this.y + "]";
	}
	
	
	
	public double getOperationFee() {
		return operationFee;
	}

	public void setOperationFee(double operationFee) {
		this.operationFee = operationFee;
	}

	public int getAircraftCapacity() {
		return aircraftCapacity;
	}

	public void setAircraftCapacity(int aircraftCapacity) {
		this.aircraftCapacity = aircraftCapacity;
	}

	public int getAircraftCount() {
		return aircraftCount;
	}

	public void addAircraft(int aircraftCount) {
		this.aircraftCount += aircraftCount;
	}

	public int getAirportType() {
		return airportType;
	}

	public void setAirportType(int airportType) {
		this.airportType = airportType;
	}

	public void setFuelCost(double fuelCost) {
		this.fuelCost = fuelCost;
	}

	public int airportType;
	
	protected Airport(int ID, double x, double y) {
		this.ID = ID;
		this.x = x;
		this.y = y;
	}
	
	public abstract double departAircraft(Aircraft aircraft);
	public abstract double landAircraft(Aircraft aircraft);
	
	public boolean equals(Airport airport) {
		if (this.ID == airport.ID)
			return true;
		else
			return false;
	}
	/**
	 * Checks if the airport is full.
	 * @return true if full, false if not
	 */
	public boolean isFull() {
		if (aircraftCount == aircraftCapacity)
			return true;
		else
			return false;
	}
	
	/**
	 * Calculates the distance between this airport and the specified airport
	 * @param airport the desired airport to calculate distance
	 * @return the distance
	 */
	public double getDistance(Airport airport) {
		double dist_x = this.x - airport.x;
		double dist_y = this.y - airport.y;
		return Math.abs(Math.sqrt(Math.pow(dist_x, 2) + Math.pow(dist_y, 2)));
	}
	
	protected double getAircraftRatio() {
		return (double)aircraftCount/aircraftCapacity;
	}
	
	public double getFuelCost() {
		return fuelCost;
	}
	
	public int getID() {
		return this.ID;
	}
}
