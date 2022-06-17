package project.airport;

import project.airline.aircraft.Aircraft;

public abstract class Airport {

	
	public abstract double departAircraft(Aircraft aircraft);
	public abstract double landAircraft(Aircraft aircraft);
	private final int ID;
	private final double x, y;
	protected double fuelCost;
	protected double operationFee;
	private int portType;
	protected int aircraftCapacity;
	private int numberOfAircrafts;
	Airport(int ID, int x, int y, double fuelCost, double operationFee, int aircraftCapacity) {
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.fuelCost = fuelCost;
		this.operationFee = operationFee;
		this.aircraftCapacity = aircraftCapacity;
		this.numberOfAircrafts = 0;
	}

	public int getPortType() {
		return portType;
	}
	public void setPortType(int portType) {
		this.portType = portType;
	}
	public double getFuelCost() {
		return fuelCost;
	}
	public void setFuelCost(double fuelCost) {
		this.fuelCost = fuelCost;
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
	public int getID() {
		return ID;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public int getNumberOfAircrafts() {
		return numberOfAircrafts;
	}
	public void setNumberOfAircrafts(int numberOfAircrafts) {
		this.numberOfAircrafts = numberOfAircrafts;
	}
	public double distanceToAirport(Airport toAirport) {
		return Math.pow(Math.pow(this.getX() - toAirport.getX(), 2) + Math.pow(this.getY() - toAirport.getY(), 2), 0.5);

	}

}
