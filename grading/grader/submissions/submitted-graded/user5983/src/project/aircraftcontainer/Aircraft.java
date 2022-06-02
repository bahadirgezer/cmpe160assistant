package project.aircraftcontainer;

import project.airportcontainer.Airport;
import project.interfacescontainer.AircraftInterface;


public abstract class Aircraft implements AircraftInterface {
	protected Airport currentAirport;
	protected double weight;  // this value should not exceed maxweight
	protected double maxWeight; //maximum allowable weight
	protected double fuelWeight=0.7;
	protected double fuel=0; //should not exceed fuel capacity
	protected double fuelCapacity; 
	protected double fuelConsumption;
	protected double aircraftTypeMultiplier;
	protected  double operationFee;
	
	public Aircraft(Airport currentAirport, double operationFee) {
		currentAirport.setAircraftCount(currentAirport.getAircraftCount() + 1);
		this.currentAirport=currentAirport;
		this.operationFee = operationFee;

		
	}
	
	
	
	@Override
	public double fly(Airport toAirport) {
		double flightCost= getFlightCost(toAirport);
		fuel-= getFuelConsumption(currentAirport.getDistance(toAirport));
		currentAirport.setAircraftCount(currentAirport.getAircraftCount()-1);
		toAirport.setAircraftCount(toAirport.getAircraftCount()+1);
		currentAirport=toAirport;
		return flightCost;
	}

	@Override
	public boolean addFuel(double fuel) {
		// TODO Auto-generated method stub
		if (this.fuel+fuel>fuelCapacity || weight+(fuel*fuelWeight)>maxWeight) {
			return false;
		}
		else {
			weight+=fuel*fuelWeight;
			this.fuel+=fuel;
			return true;
		}
	}

	@Override
	public boolean fillUp() {
		// TODO Auto-generated method stub
		if (weight+(fuel*fuelWeight)>maxWeight) {
			return false;
		}
		else {
			weight+=(fuelCapacity-fuel)*fuelWeight;
			fuel=fuelCapacity;
			return true;
		}
	}

	@Override
	public boolean hasFuel(double fuel) {
		// TODO Auto-generated method stub
		if (this.fuel==fuel) {
			return true;
		}
		else {
			return false;
		}
		
	}

	@Override
	public double getWeightRatio() {
		// TODO Auto-generated method stub
		return weight/maxWeight;
	}
	
	
	
	public abstract double getFuelConsumption(double distance);
	
	public abstract double getFlightCost(Airport toAirport);
	
	public Airport getCurrentAirport() {
		return currentAirport;
	}
	
	public double getFuel() {
		return fuel;
	}
	
	public void setFuel(double fuel) {
		this.fuel=fuel;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight=weight;
	}
	
	public double getMaxWeight() {
		return maxWeight;
	}
	
	

	
	
	
	
	
	//public double refuel()
		//should return a double which is the fuel cost
		//fuel cost is calculated by multiplying the fuel amount by the airports fuel cost which the aircraft is refueling at.
}
