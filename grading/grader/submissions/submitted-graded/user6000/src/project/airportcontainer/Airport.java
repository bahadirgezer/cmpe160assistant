package project.airportcontainer;

import java.util.ArrayList;

import project.airlinecontainer.aircraftcontainer.Aircraft;

public abstract class Airport {
	private final long ID;
	private final double x, y;
	protected double fuelCost;
	protected double operationFee;
	protected int aircraftCapacity;
	public ArrayList<Aircraft> aircraftsInside = new ArrayList<Aircraft>(); 
	public double getoperationFee() {
		return operationFee;
	}
	public long getID() {
		return this.ID;
	}
	public Airport(long ID,double x,double y,double fuelCost,double operationFee,int aircraftCapacity) {
		this.ID=ID;
		this.x=x;
		this.y=y;
		this.fuelCost=fuelCost;
		this.operationFee=operationFee;
		this.aircraftCapacity=aircraftCapacity;
	}
	public double getFuelCost() {
		return fuelCost;
	}
	public double getxPosition() {
		return x;
	}
	public double getyPosition() {
		return y;
	}
	public abstract double departAircraft(Aircraft aircraft) ;
		
	
	public abstract double landAircraft(Aircraft aircraft) ;
	
}
