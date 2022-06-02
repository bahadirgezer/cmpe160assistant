package project.airline_container.aircraft_container.concrete_container;

import project.airline_container.aircraft_container.PassengerAircraft;
import project.airport_container.Airport;

public class PropPassengerAircraft extends PassengerAircraft {
	protected double weight=1400;
	protected double maxWeight=2300;
	public static double getAvWeight() {
		return 900;
	}
	protected double floorArea = 60;
	protected double seatArea=0;
	protected double fuelCapacity=6000;
	protected double fuelConsumption=0.6;
	protected double aircraftTypeMultiplier=0.9;
	protected double constant1=0.08;
	protected int divisor = 2000;
	public PropPassengerAircraft(double a,Airport b) {
		super.operationfee = a;
		super.currentAirport = b;
	}
	public PropPassengerAircraft() {
	}
	public double getAircraftTypeMultiplier() {
		return aircraftTypeMultiplier;
	}
	protected double getFlightCost(Airport toAirport)
	{
		return toAirport.getDepartureCost(this)+toAirport.getLandingCost(this)+(super.getTotaloccupiedseats()/super.getTotalseats())*Airport.getdistance(super.currentAirport, toAirport)*0.1;
	}
	protected double getFuelConsumption(double distance) {
		double x = distance/divisor;
		double bathtub_coefficient = 25.9324*Math.pow(x, 4)-50.5633*Math.pow(x, 3)+ 35.0554*Math.pow(x, 2)-9.90346*x + 1.97413;
		return fuelConsumption*bathtub_coefficient*distance+weight*constant1/fuelWeight;

	}
	public double seeFuelConsumption(double distance,double a) {
		double x = distance/divisor;
		double bathtub_coefficient = 25.9324*Math.pow(x, 4)-50.5633*Math.pow(x, 3)+ 35.0554*Math.pow(x, 2)-9.90346*x + 1.97413;
		return fuelConsumption*bathtub_coefficient*distance+(weight+a)*constant1/fuelWeight;
	}
	public double getFuelCapacity() {
		return fuelCapacity;
	}
	public double getFloorArea() {
		return floorArea;
	}
	public void setWeight(double a) {
		this.weight = a;
	}
	public double getWeight() {
		return weight;
	}
	
	public double getMaxWeight() {
		return maxWeight;
	}
	public static double CalculateWeightofFuelNeeded(double distance) {
		double x = distance/2000;
		double bathtub_coefficient = 25.9324*Math.pow(x, 4)-50.5633*Math.pow(x, 3)+ 35.0554*Math.pow(x, 2)-9.90346*x + 1.97413;
		return 0.6*bathtub_coefficient*distance*0.7+2300*0.08;

	}
	@Override
	public double getAvailableWeight() {
		// TODO Auto-generated method stub
		return maxWeight-weight;
	}
	@Override
	public double getOpFee() {
		// TODO Auto-generated method stub
		return operationfee;
	}

	

}
