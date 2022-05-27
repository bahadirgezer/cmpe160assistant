package project.airline_container.aircraft_container.concrete_container;

import project.airline_container.aircraft_container.PassengerAircraft;
import project.airport_container.Airport;

public class JetPassengerAircraft extends PassengerAircraft {
	protected double weight=10000;
	protected double maxWeight=18000;
	protected double floorArea = 30;
	protected double seatArea=0;
	protected double fuelCapacity=10000;
	protected double fuelConsumption=0.7;
	protected double aircraftTypeMultiplier=5;
	protected double constant1=0.1;
	protected int divisor = 5000;
	public JetPassengerAircraft(double a,Airport b) {
		super.operationfee = a;
		super.currentAirport = b;
	}
	public JetPassengerAircraft() {

	}
	public double getAircraftTypeMultiplier() {
		return aircraftTypeMultiplier;
	}
	protected double getFlightCost(Airport toAirport)
	{
		return this.currentAirport.departAircraft(this)+toAirport.landAircraft(this)+(super.getTotaloccupiedseats()/super.getTotalseats())*Airport.getdistance(super.currentAirport, toAirport)*0.08;
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
	public double getWeight() {
		return weight;
	}
	public void setWeight(double a) {
		this.weight = a;
	}
	public double getMaxWeight() {
		return maxWeight;
	}
	public static double CalculateWeightofFuelNeeded(double distance) {
		double x = distance/5000;
		double bathtub_coefficient = 25.9324*Math.pow(x, 4)-50.5633*Math.pow(x, 3)+ 35.0554*Math.pow(x, 2)-9.90346*x + 1.97413;
		return 0.7*bathtub_coefficient*distance*0.7+18000*0.1;
		
	}
	@Override
	public double getAvailableWeight() {
		// TODO Auto-generated method stub
		return maxWeight-weight;
	}
	public static double getAvWeight() {
		return 8000;
	}
	@Override
	public double getOpFee() {
		// TODO Auto-generated method stub
		return operationfee;
	}

	
}
