package airline.aircraft;

import airport.Airport;
import interfaces.AircraftInterface;

public abstract class Aircraft implements AircraftInterface {

	
	protected Airport currentAirport;
	
	protected double weight;
	
	protected double maxWeight;
	
	protected final double fuelWeight = 0.7;

	protected double fuel;
	//This field holds the amount of fuel the aircraft has at any moment. It should not exceed fuelCapacity. fuelCapacity and this field are both volumes, so you should multiply fuel amount with fuelWeight when you want to get the weight for some amount of fuel. This value is zero upon initialization.

	protected double fuelCapacity;
	//Fuel capacity of the airplane. It is different and fixed for every type of aircraft.
	
	Aircraft(Airport currentAirport){
		fuel = 0;
		this.currentAirport = currentAirport;
	}
	
	public abstract double getFlightCost(Airport toAirport);
	
	public abstract double getFuelConsumption(double distance);
	
	
	public double fly(Airport toAirport) {
		double flightCost = getFlightCost(toAirport);
		double fuelConsumption = getFuelConsumption(currentAirport.getDistance(toAirport));
		fuel = fuel - fuelConsumption;
		weight = weight - fuelConsumption*fuelWeight;
		currentAirport = toAirport;
		return flightCost;
	}
	
	public boolean canFly(Airport toAirport) {
		double fuelConsumption = getFuelConsumption(currentAirport.getDistance(toAirport));
		if(fuel < fuelConsumption) {
			//System.out.println("fuel is not enough");
			return false;
		}
		if(toAirport.isFull() == true) {
			System.out.println("destination is full");
			return false;
		}
		return true;
	}
	
	public Airport getCurrentAirport() {
		return currentAirport;
	}

	public double getFuel() {
		return fuel;
	}

	public double refuel(double fuel) {
		weight = weight + fuel*fuelWeight;
		this.fuel = this.fuel + fuel;
		return fuel * currentAirport.getFuelCost();
	}
	
	@Override
	public boolean addFuel(double fuel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fillUp() {
		return true;
	}

	@Override
	public boolean hasFuel(double fuel) {
		if(this.fuel >= fuel)
			return true;
		else
			return false;
	}

	@Override
	public double getWeightRatio() {
		return weight/maxWeight;
	}
	
	public double avaiableFuelCapacity() {
		double cap1 = fuelCapacity - fuel;
		double cap2 = (maxWeight - weight)/fuelWeight;
		//System.out.println(cap1 + " cap " + cap2);
		if(cap1<=cap2) {
			return cap1;
		}
		else {
			return cap2; 
		}
	
	}
	
}
