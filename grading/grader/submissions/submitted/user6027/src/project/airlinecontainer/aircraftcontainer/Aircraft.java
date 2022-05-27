package project.airlinecontainer.aircraftcontainer;
import java.util.ArrayList;

import project.airportcontainer.Airport;
import project.interfacescontainer.AircraftInterface;
import project.passengercontainer.Passenger;

public abstract class Aircraft implements AircraftInterface{

	protected Airport currentAirport;
	protected double weight;
	protected double maxWeight;
	protected double fuelWeight=0.7;
	protected double fuel=0.0;
	protected double fuelCapacity;
	protected double operationfee;
	public abstract double getFuelConsumption(double distance);
	protected abstract double getFlightCost(Airport toAirport);
	//public abstract double loadPassenger(Passenger passenger);
	protected ArrayList<Passenger> current_passenger=new ArrayList<Passenger>();
	public abstract double getfuelcapacity();
	
	
	public double getOperationfee() {
		return operationfee;
	}
	public void setOperationfee(double operationfee) {
		this.operationfee = operationfee;
	}
	public Aircraft(double operationfee, Airport currentAirport, double weigth, double maxWeight, double floorarea, double fuelCapacity, double fuelConsumption, double aircraftTypeMultiplier) {
		this.operationfee=operationfee;
		this.currentAirport=currentAirport;
		this.weight=weigth;
		this.maxWeight=maxWeight;
		this.fuelCapacity=fuelCapacity;

	}
	public boolean can_fly_happen(Airport toAirport, double distance) {
		if (currentAirport!=toAirport) {	
			return hasFuel(this.getFuelConsumption(distance));
		}
		else {
			return false;
		}
	}
	
	public double fly(Airport toAirport) {
		double distancex=Math.pow(currentAirport.get_coordinatesx()-toAirport.get_coordinatesx(),2);
		double distancey=Math.pow(currentAirport.get_coordinatesy()-toAirport.get_coordinatesy(),2);
		double distance=Math.sqrt(distancex+distancey);
		double x = this.getFlightCost(toAirport);
		this.fuel=this.fuel-this.getFuelConsumption(distance);		
		currentAirport.remove_aircraft(this);
		this.currentAirport=toAirport;
		toAirport.add_aircraft(this);
		// this.weight-=this.getFuelConsumption(distance)*0.7;
		return x;
	}
	public Airport getCurrentAirport() {
		return currentAirport;
	}
	public void setCurrentAirport(Airport currentAirport) {
		this.currentAirport = currentAirport;
	}
	public double addFuel(double fuel) {
		this.fuel+=fuel;
		this.weight+=this.fuelWeight*fuel;
		return this.currentAirport.getfuelCost()*fuel;
	}
	public double fillUp() {
		double cost=currentAirport.getfuelCost()*(this.getfuelcapacity()-this.fuel);
		this.fuel=this.getfuelcapacity();
		this.weight+=this.fuel*0.7;
		return cost;
	}
	
	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}
	public boolean hasFuel(double fuel) {
		if (this.fuel>=fuel)
			return true;
		else
			return false;
	}
	public double getWeightRatio() {
		return (double)this.get_weight()/this.getMaxWeight();
	}
	
	public ArrayList<Passenger> get_current_passenger(){
		return current_passenger;
	}
	public void set_weight() {
		this.weight=this.get_weight();
	}
	public double get_weight(){
		return this.weight;
	}
	public double getMaxWeight() {
		return maxWeight;
	}
	public void set_weight(double weight) {
		this.weight=weight;
	}
	public void set_fuels_value() {
		this.fuel=0.0;
	}
	public double get_fuel() {
		return this.fuel;
	}
	
}
