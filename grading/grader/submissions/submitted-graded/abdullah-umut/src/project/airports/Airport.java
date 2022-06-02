package project.airports;

import project.airlines.aircrafts.Aircraft;

public abstract class Airport {
	
	private final int ID;
	private final double x, y;
	protected double fuelCost;
	protected double operationFee;
	protected int aircraftCapacity;
	protected int aircraftNumber;
	protected int airportType;
	
	Airport(int ID, double x, double y){
		this.ID = ID;
		this.x = x;
		this.y = y;
		aircraftNumber = 0;
	}
	
	public double getDistance(Airport airport) {
		return Math.sqrt(Math.pow((this.x-airport.x),2) + Math.pow((this.y-airport.y),2));
	}
	
	
	public abstract double departAircraft(Aircraft aircraft);
	
	public abstract double landAircraft(Aircraft aircraft);

	protected double aircraftRatio() {
		return ((double) aircraftNumber) / aircraftCapacity;
	}
	
	
	public int getID() {
		return ID;
	}

	public double getFuelCost() {
		return fuelCost;
	}
	
	public boolean hasCapacity() {
		return (aircraftCapacity > aircraftNumber);
	}

	public int getAirportType() {
		return airportType;
	}
	
}

