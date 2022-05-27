package project.airline_container.aircraft_container.concrete_container;

import project.airline_container.aircraft_container.PassengerAircraft;
import project.airport_container.Airport;

public class WidebodyPassengerAircraft extends PassengerAircraft {
	protected double weight=135000;
	protected double maxWeight=250000;
	public static double getAvWeight() {
		return 115000;
	}
	protected double floorArea = 450;
	public double getFloorArea() {
		return floorArea;
	}
	public double getAircraftTypeMultiplier() {
		return aircraftTypeMultiplier;
	}
	protected double seatArea=0;
	protected double fuelCapacity=140000;
	protected double fuelConsumption=3.0;
	protected double aircraftTypeMultiplier=0.7;
	protected double constant1=0.1;
	protected int divisor = 14000;
	public WidebodyPassengerAircraft(double a,Airport b) {
		super.operationfee = a;
		super.currentAirport = b;
	}
	public WidebodyPassengerAircraft() {

	}
	protected double getFlightCost(Airport toAirport)
	{
		return this.currentAirport.getDepartureCost(this)+toAirport.getLandingCost(this)+(super.getTotaloccupiedseats()/super.getTotalseats())*Airport.getdistance(super.currentAirport, toAirport)*0.15;
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
	public void setWeight(double a) {
		this.weight = a;
	}
	public double getFuelCapacity() {
		return fuelCapacity;
	}
	public static double CalculateWeightofFuelNeeded(double distance) {
		double x = distance/14000;
		double bathtub_coefficient = 25.9324*Math.pow(x, 4)-50.5633*Math.pow(x, 3)+ 35.0554*Math.pow(x, 2)-9.90346*x + 1.97413;
		return 3.0*bathtub_coefficient*distance*0.7+250000*0.1;

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
