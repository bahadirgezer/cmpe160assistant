package project.airlinecontainer.aircraftcontainer;

import project.airportcontainer.Airport;
import java.util.ArrayList;

import project.interfacescontainer.AircraftInterface;
import project.passengercontainer.Passenger;

public abstract class Aircraft implements AircraftInterface{
	protected Airport currentAirport;
	protected double weight;
	protected double maxWeight;
	protected double fuelWeight=0.7;
	protected double fuel;
	protected double fuelCapacity;
	protected double operationFee;
	protected abstract double getFuelConsumption(double distance);
	protected abstract double getFlightCost(Airport toAirport);
	public abstract double getFullness();
	public abstract double loadPassenger(Passenger passenger);
	public abstract double unloadPassenger(Passenger passenger);
	
	ArrayList<Passenger> listofPassengers= new ArrayList<Passenger>();
	
	
	
 	public Aircraft(Airport currentAirport, double operationFee) {
		super();
		this.currentAirport = currentAirport;
		this.operationFee = operationFee;
	}
	public double weight() {
		return weight;
	}
	public double maxWeight() {
		return maxWeight;
	}
public boolean isFlyPossible(Airport toAirport) {
	double distance= Math.pow(( Math.pow((toAirport.getxPosition()-currentAirport.getxPosition()),2)+Math.pow((toAirport.getyPosition()-currentAirport.getyPosition()),2)),0.5);

	double fuelConsumption=this.getFuelConsumption(distance);
	if(fuelConsumption<=fuel) {
	return true;
	}
	else {
		return false;
	}
}
public double fly(Airport toAirport) {
	double distance= Math.pow(( Math.pow((toAirport.getxPosition()-currentAirport.getxPosition()),2)+Math.pow((toAirport.getyPosition()-currentAirport.getyPosition()),2)),0.5);

	double fuelConsumption=this.getFuelConsumption(distance);
	
	
		this.currentAirport=toAirport;
		this.fuel=this.fuel-fuelConsumption;
		
	
	for(int i=0; i<this.listofPassengers.size();i++) {
		listofPassengers.get(i).currentAirport=toAirport;
	}
	return this.getFlightCost(toAirport);
	
}



}
