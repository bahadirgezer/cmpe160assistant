package project.airline_container.aircraft_container.concrete_container;

import project.airline_container.aircraft_container.PassengerAircraft;
import project.airport_container.Airport;

public class RapidPassengerAircraft extends PassengerAircraft {
	protected double weight=80000;
	protected double maxWeight=185000;
	public static double getAvWeight() {
		return 105000;
	}
	protected double floorArea = 120;
	protected double seatArea=0;
	protected double fuelCapacity=120000;
	protected double fuelConsumption=5.3;
	protected double aircraftTypeMultiplier=1.9;
	protected double constant1=0.1;
	protected int divisor = 7000;
	public RapidPassengerAircraft(double a,Airport b) {
		super.operationfee = a;
		super.currentAirport = b;
	}
	public RapidPassengerAircraft() {
	}
	public double getAircraftTypeMultiplier() {
		return aircraftTypeMultiplier;
	}
	protected double getFlightCost(Airport toAirport)
	{
		return toAirport.getDepartureCost(this)+toAirport.getLandingCost(this)+(super.getTotaloccupiedseats()/super.getTotalseats())*Airport.getdistance(super.currentAirport, toAirport)*0.2;
	}
	protected double getFuelConsumption(double distance) {
		double x = distance/divisor;
		double bathtub_coefficient = 25.9324*Math.pow(x, 4)-50.5633*Math.pow(x, 3)+ 35.0554*Math.pow(x, 2)-9.90346*x + 1.97413;
		return fuelConsumption*bathtub_coefficient*distance+weight*constant1/fuelWeight;

	}
	public double getFloorArea() {
		return floorArea;
	}
	public double getFuelCapacity() {
		return fuelCapacity;
	}
	public double seeFuelConsumption(double distance,double a) {
		double x = distance/divisor;
		double bathtub_coefficient = 25.9324*Math.pow(x, 4)-50.5633*Math.pow(x, 3)+ 35.0554*Math.pow(x, 2)-9.90346*x + 1.97413;
		return fuelConsumption*bathtub_coefficient*distance+(weight+a)*constant1/fuelWeight;
	}
	public static double CalculateWeightofFuelNeeded(double distance) {
		double x = distance/7000;
		double bathtub_coefficient = 25.9324*Math.pow(x, 4)-50.5633*Math.pow(x, 3)+ 35.0554*Math.pow(x, 2)-9.90346*x + 1.97413;
		return 5.3*bathtub_coefficient*distance*0.7+185000*0.1;

	}
	public void setWeight(double a) {
		this.weight = a;
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
	public double getWeight() {
		return weight;
	}
	public double getMaxWeight() {
		return maxWeight;
	}
}
