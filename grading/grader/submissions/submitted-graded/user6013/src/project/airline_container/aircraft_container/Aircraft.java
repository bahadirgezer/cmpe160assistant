package project.airline_container.aircraft_container;

import project.airport_container.Airport;
import project.interfaces_container.AircraftInterface;


public abstract class Aircraft implements AircraftInterface {
	protected Airport currentAirport;
	protected double weight;
	protected double maxWeight;
	public abstract double getWeight();
	public abstract void setWeight(double a);
	public double getMaxWeight() {
		return maxWeight;
	}
	protected double fuelWeight=0.7;
	protected double fuel=0;
	public double getFuel() {
		return fuel;
	}
	protected double fuelCapacity;
	public double getFuelCapacity() {
		return fuelCapacity;
	}
	protected double aircraftTypeMultiplier;
	public abstract double getAircraftTypeMultiplier();
	public double fly(Airport toAirport) {
		double temp = getFuelConsumption(Airport.getdistance(currentAirport,toAirport));
		double a =getFlightCost(toAirport);
		this.fuel-= temp;
		this.weight -= temp*fuelWeight;
		currentAirport=toAirport;
		return a;
		}
	public boolean fly_check(Airport toAirport) {
		if(!(toAirport.getaircraftRatio() == 1.0) && this.fuel-getFuelConsumption(Airport.getdistance(currentAirport,toAirport))>=0 && !(currentAirport.equals(toAirport))){
			return true;
		}else {
			return false;
		}
		}
	public Airport getCurrentAirport() {
		return currentAirport;
	}
	@Override
	public double addFuel(double fuel) {
		// TODO Auto-generated method stub

		if(fuel>this.fuel && this.getWeight()+this.fuelWeight*(fuel-this.fuel)<=this.getMaxWeight()&&fuel<=this.getFuelCapacity() ) {
			double a = fuel-this.fuel;
			this.setWeight(this.getWeight()+a*this.fuelWeight);
			this.fuel = fuel;
			return currentAirport.getFuelCost()*(a);
			}else {
				return 0;
			}
	}
	@Override
	public double fillUp() {
		// TODO Auto-generated method stub
		
		if(this.weight+this.fuelWeight*(this.fuelCapacity-this.fuel)<=this.maxWeight)
		{
		this.weight += this.fuelWeight*(fuelCapacity-fuel);
		return currentAirport.getFuelCost()*(this.fuelCapacity-this.fuel);
		}else {
			return 0;
		}
	}
	@Override
	public boolean hasFuel(double fuel) {
		// TODO Auto-generated method stub
		if(this.fuel==fuel) {
			return true;
		}else {
			return false;
		}
	}
	protected abstract double getFuelConsumption(double distance);
	protected abstract double getFlightCost(Airport toAirport);
	public abstract double seeFuelConsumption(double distance,double a);

}
 