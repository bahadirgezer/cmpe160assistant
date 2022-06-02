package project.airline.aircraft;


import project.airport.Airport;
import project.interfaces.AircraftInterface;

public abstract class Aircraft implements AircraftInterface {
	protected Airport currentAirport;
	protected double weight;
	protected double maxWeight;
	protected double fuelWeight=0.7;
	protected double fuel;
	protected double fuelCapacity;
	protected double fuelConsumption;
	protected double operationFee;
	public abstract double getFlightCost(Airport toAirport);
	public Aircraft(Airport currentAirport, double operationFee) {
		this.currentAirport = currentAirport;
		this.operationFee = operationFee;
	}
	
	public Airport getCurrentAirport() {
		return currentAirport;
	}
	public void setCurrentAirport(Airport currentAirport) {
		this.currentAirport = currentAirport;
	}
	
	public double getOperationFee() {
		return operationFee;
	}

	public void setOperationFee(double operationFee) {
		this.operationFee = operationFee;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}
	public void setFuelWeight(double fuelWeight) {
		this.fuelWeight = fuelWeight;
	}
	public void setFuel(double fuel) {
		this.fuel = fuel;
	}
	public void setFuelCapacity(double fuelCapacity) {
		this.fuelCapacity = fuelCapacity;
	}
	public double getWeight() {
		return weight;
	}
	public double getMaxWeight() {
		return maxWeight;
	}
	public double getFuelWeight() {
		return fuelWeight;
	}
	public double getFuel() {
		return fuel;
	}
	public double getFuelCapacity() {
		return fuelCapacity;
	}
	public abstract double getFuelConsumption(double distance);
	
	public double fly(Airport toAirport) {
		double opCost = this.getFlightCost(toAirport);
		double departcost = this.getCurrentAirport().departAircraft(this);
		double landcost = toAirport.landAircraft(this);
		//System.out.println("landcost " + Double.toString(landcost));
		//System.out.println("departcost " + Double.toString(departcost));
		//System.out.println("opcost " + Double.toString(opCost));
		this.setFuel(this.getFuel() - this.getFuelConsumption(currentAirport.distanceToAirport(toAirport)));
		return opCost + departcost + landcost;
	}
	public double addFuel(double fuel) {
		if(this.getFuel() + fuel <= this.getFuelCapacity()) {
			this.setWeight(fuel * this.getFuelWeight());
			this.setFuel(this.getFuel() + fuel);
			return fuel * this.getCurrentAirport().getFuelCost();
		} else {
			return 0;
		}
	}
	public double getAvailableWeight() {
		return this.getMaxWeight() - this.getWeight();
	}
	public double fillUp() {
		if((this.getFuelCapacity()-this.getFuel()) * this.getFuelWeight() <= this.getAvailableWeight()) {
			double fuelAdded = (this.getFuelCapacity()-this.getFuel());
			this.setWeight(this.getWeight() + (this.getFuelCapacity()-this.getFuel()) * this.getFuelWeight());
			this.setFuel(this.getFuelCapacity());
			return fuelAdded * this.getCurrentAirport().getFuelCost();
		} else {
			double fuelAdded = this.getAvailableWeight()/this.getFuelWeight();
			this.setFuel(this.getFuel() + this.getAvailableWeight()/this.getFuelWeight());
			this.setWeight(maxWeight);
			return fuelAdded * this.getCurrentAirport().getFuelCost();
		}
		
	}
	public boolean hasFuel(double fuel) {
		if(this.getFuel() >= fuel) return true;
		else return false;
	}
	public double getWeightRatio() {
		return this.getWeight() / this.getMaxWeight();
	}
	
	
	
}
