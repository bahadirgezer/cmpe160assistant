package project.airlinecontainer.aircraftcontainer;

import project.airportcontainer.Airport;
import project.interfacescontainer.AircraftInterface;

public abstract class Aircraft implements AircraftInterface {
	public Aircraft(Airport currentAirport, double operationFee) {
		this.currentAirport = currentAirport; this.operationFee = operationFee;
	}
	protected Airport currentAirport;
	protected double weight;
	protected double maxWeight;
	protected double fuelWeight = 0.7;
	protected double fuel = 0;
	protected double fuelCapacity;
	protected double weightRatio;
	protected double operationFee;
	protected double aircraftTypeMultiplier;
	protected abstract double getFlightCost(Airport toAirport);
	protected abstract double getFuelConsumption(double distance);
	
	protected double getdistance(Airport currentAirport, Airport toAirport) {
		double c1[] = currentAirport.getCoordinates();
		double c2[] = toAirport.getCoordinates();
		return Math.pow((Math.pow((c1[0]-c2[0]), 2) + Math.pow((c1[1]-c2[1]),2)), 0.5);
	}
	
	public boolean canFly(Airport toAirport) {
		if (this.fuel >= this.getFuelConsumption(this.getdistance(toAirport, this.currentAirport)) && toAirport.getTotalNoofAircraft() < toAirport.getAircraftCapacity() ) return true;
		else return false;
	}
	public double fly(Airport toAirport) {
		double x = this.getFlightCost(toAirport);
		toAirport.setTotalNoofAircraft(toAirport.getTotalNoofAircraft()+1);
		this.currentAirport.setTotalNoofAircraft(this.currentAirport.getTotalNoofAircraft()-1);
		this.fuel-=this.getFuelConsumption(this.getdistance(this.currentAirport, toAirport));
		this.currentAirport = toAirport;
		return x;
	}
	
	public double getFuel() {
		return fuel;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getWeightRatio() {
		return this.weight/this.maxWeight;
	}

	public double addFuel(double fuel) {
		if (this.getFuel()+fuel<this.fuelCapacity) {
			double x = fuel*this.currentAirport.getFuelCost();
			this.weight+=fuel*0.7;
			this.fuel += fuel; 
			return x;
		}
		else return 0;
	}

	public Airport getCurrentAirport() {
		return currentAirport;
	}
	
	public double getFuelWeight() {
		return fuelWeight;
	}
	public void setFuelWeight(double fuelWeight) {
		this.fuelWeight = fuelWeight;
	}
	public double getOperationFee() {
		return operationFee;
	}
	public void setOperationFee(double operationFee) {
		this.operationFee = operationFee;
	}
	public double getAircraftTypeMultiplier() {
		return aircraftTypeMultiplier;
	}
	public void setAircraftTypeMultiplier(double aircraftTypeMultiplier) {
		this.aircraftTypeMultiplier = aircraftTypeMultiplier;
	}
	public void setFuel(double fuel) {
		this.fuel = fuel;
	}
	public void setWeightRatio(double weightRatio) {
		this.weightRatio = weightRatio;
	}
	public void setCurrentAirport(Airport currentAirport) {
		 this.setCurrentAirport(currentAirport);
	}
	public double getMaxWeight() {
		return maxWeight;
	}
	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}
	public double getFuelCapacity() {
		return fuelCapacity;
	}
	public void setFuelCapacity(double fuelCapacity) {
		this.fuelCapacity = fuelCapacity;
	}
	public double fillUp() {
		double x = (fuelCapacity - this.fuel)*this.currentAirport.getFuelCost();
		this.weight += 0.7*(fuelCapacity - this.fuel);
		fuel = fuelCapacity;
		return x;
}

	public boolean hasFuel(double fuel) {
		if (this.fuel >= fuel)
			return true;
		else return false;}

	
	

}
