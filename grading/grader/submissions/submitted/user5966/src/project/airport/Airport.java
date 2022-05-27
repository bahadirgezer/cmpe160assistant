package project.airport;

import project.airline.aircraft.Aircraft;
import project.passenger.BusinessPassenger;
import project.passenger.EconomyPassenger;
import project.passenger.FirstClassPassenger;
import project.passenger.LuxuryPassenger;
import project.passenger.Passenger;
import java.util.*;

public abstract class Airport {
	private final int ID;
	private final double x,y;
	protected double fuelCost;
	protected double operationFee;
	protected int aircraftCapacity;
	protected ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	protected ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
	public final double eulerConstant = Math.exp(1);
	private final int order;
	public Airport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity,int order) {
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.fuelCost = fuelCost;
		this.operationFee = operationFee;
		this.aircraftCapacity = aircraftCapacity;
		this.order = order;
	}
	public abstract double departAircraft(Aircraft aircraft);
	public abstract double landAircraft(Aircraft aircraft);
	public double getDistance(Airport airport) {
		double distance = Math.sqrt(((this.x - airport.x)*(this.x - airport.x)) + ((this.y - airport.y)*(this.y - airport.y)));
		return distance;
	}
	public void addPassenger(Passenger passenger) {
		passengers.add(passenger);
	}
	public void removePassenger(Passenger passenger) {
		passengers.remove(passenger);
	}
	public ArrayList<Passenger> getPassengers(){
		return passengers;
	}
	public int getOrder() {
		return order;
	}
	protected double calculateFullnessCoeff() {
		double aircraftRatio =  aircrafts.size() * 1.0 / aircraftCapacity;
		double fullnesCoeff = Math.pow((eulerConstant),aircraftRatio) * 0.6;
		return fullnesCoeff;
	}
	public double getFuelCost() {
		return fuelCost;
	}
	public boolean isFull() {
		if (aircrafts.size() == aircraftCapacity) {
			return true;
		}
		return false;
	}
	public int getID() {
		return ID;
	}
	public ArrayList<Aircraft> getAircrafts(){
		return aircrafts;
	}
	public int getAircraftCapacity() {
		return aircraftCapacity;
	}
	public int[] calculateBestDestination(HashMap<Integer,Airport> airportDictionary) { //returns the id of best target airport
		HashMap<Integer,Integer> bestDestination = new HashMap<Integer,Integer>();
		int maxKey = 0;
		for(int i = 0;i<passengers.size();i++) {
			Passenger passenger = passengers.get(i);
			if (passenger instanceof EconomyPassenger || passenger instanceof BusinessPassenger) {
				continue;
			}
			for(int j = 0;j<passenger.getDestinations().size();j++) {
				Integer destination = passenger.getDestinations().get(j);
				bestDestination.putIfAbsent(destination, 0);
				if (passenger instanceof LuxuryPassenger) {
					bestDestination.computeIfPresent(destination, (key,value)-> value+100);
				}
				else {
					bestDestination.computeIfPresent(destination, (key,value)-> value+20);
				}
			}
			for(Integer z:bestDestination.keySet()) {
				Airport targetAirport = airportDictionary.get(z);
				if (aircrafts.size()>0) {
					bestDestination.computeIfPresent(z, (key,value)->value+50);
				}
				//if (this instanceof RegionalAirport & targetAirport instanceof RegionalAirport) {
					//bestDestination.computeIfPresent(z, (key,value)-> value*2);
				//}
				if ((getDistance(targetAirport))>4750 || targetAirport.isFull() || targetAirport.equals(this)){
					bestDestination.computeIfPresent(z, (key,value)-> 0);
				}
			}
			maxKey = Collections.max(bestDestination.entrySet(), Map.Entry.comparingByValue()).getKey();
		}
		try {
			int[] destValue = {maxKey,bestDestination.get(maxKey)};;
			return destValue;
		}
		catch(Exception e) {
			int[] destValue = {0,0};
			return destValue;
		}
	}
	public void addAircraft(Aircraft aircraft) {
		aircrafts.add(aircraft);
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
}
