package project.airport;

import project.airline.aircraft.Aircraft;

public abstract class Airport {
	private final int ID;
	private final double x, y;
	protected double fuelCost;
	protected double operationFee;
	protected int aircraftCapacity;
	protected int totalAircraftNo;
	private int airportType; // Hub=0, Major=1, Regional=2

	public Airport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity, int airportType) {
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.fuelCost = fuelCost;
		this.operationFee = operationFee;
		this.aircraftCapacity = aircraftCapacity;
		this.airportType = airportType;
		totalAircraftNo = 0;
	}

	public double getFuelCost() {
		return fuelCost;
	}

	public abstract double departAircraft(Aircraft aircraft);
	
	public abstract double getDepartCost(Aircraft aircraft);

	public abstract double landAircraft(Aircraft aircraft);
	
	public abstract double getLandCost(Aircraft aircraft);
	
	public double getAircraftRatio() {
		return ((totalAircraftNo*1.0)/aircraftCapacity);
	}

	public double getFullnessCoef() {
		return 0.6*Math.exp(getAircraftRatio());
	}
	public double getDistance(Airport toAirport) {
		return Math.pow(Math.pow(x - toAirport.x, 2) + Math.pow(y - toAirport.y, 2), 0.5);
	}

	public boolean equals(Airport comparedAirport) {
		if (ID == comparedAirport.ID) {
			return true;
		} else {
			return false;
		}
	}
	public int getAirportType() {
		return airportType;
	}
	
	public double getAirportMultiplier(Airport toAirport) {
		switch (airportType) {
		case 0:
			switch (toAirport.getAirportType()) {
			case 0:
				return 0.5;
			case 1:
				return 0.7;
			case 2:
				return 1;
			default:
				return -1;
			}
		case 1:
			switch (toAirport.getAirportType()) {
			case 0:
				return 0.6;
			case 1:
				return 0.8;
			case 2:
				return 1.8;
			default:
				return -1;			
			}
		case 2:
			switch (toAirport.getAirportType()) {
			case 0:
				return 0.9;
			case 1:
				return 1.6;
			case 2:
				return 3.0;
			default:
				return -1;			
			}
		default:
			return -1;
		}
	}

	public double getOperationFee() {
		return operationFee;
	}
	
	public int getID() {
		return ID;
	}
	
	public void placeNewAircraft() {
		totalAircraftNo +=1;
	}

}
