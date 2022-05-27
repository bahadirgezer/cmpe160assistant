package project.airportcontainer;

import project.airlinecontainer.aircraftcontainer.Aircraft;

public abstract class Airport {
	public Airport(long id, double x, double y) {
		this.ID=id;
		this.x=x;
		this.y=y;
	}
	private final long ID;
	private final double x;
	private final double y;
	protected double fuelCost;
	protected double operationFee;
	protected int aircraftCapacity;
	protected int totalNoofAircraft;
	
	public abstract double departAircraft(Aircraft aircraft);
	
	public abstract double landAircraft(Aircraft aircraft);
	
	public double getFullnesscoefficient() {
		double aircraftRatio = (double) totalNoofAircraft/aircraftCapacity;
		return 0.6*Math.exp(aircraftRatio);
	}
	
	
	public long getID() {
		return ID;
	}
	
	public double getFuelCost() {
		return fuelCost;
	}
	
	protected double getOperationFee() {
		return operationFee;
	}

	public void setOperationFee(double operationFee) {
		this.operationFee = operationFee;
	}

	public void setFuelCost(double fuelCost) {
		this.fuelCost = fuelCost;
	}

	public int getAircraftCapacity() {
		return aircraftCapacity;
	}

	public void setAircraftCapacity(int aircraftCapacity) {
		this.aircraftCapacity = aircraftCapacity;
	}

	public int getTotalNoofAircraft() {
		return totalNoofAircraft;
	}

	public void setTotalNoofAircraft(int totalNoofAircraft) {
		this.totalNoofAircraft = totalNoofAircraft;
	}

	public double[] getCoordinates() {
		double coordinates[] = {this.x, this.y};
		return coordinates;
	}
	
	
}
