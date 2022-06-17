package project.concretecontainer;

import project.aircraftcontainer.PassengerAircraft;

import project.airportcontainer.Airport;

public class WidebodyPassengerAircraft extends PassengerAircraft{
private double flightConstant=0.15;

	
	
	public WidebodyPassengerAircraft(Airport currentAirport,double operationFee) {
		super(currentAirport,operationFee);
		this.weight=135000;
		this.maxWeight=250000;
		this.floorArea=450;
		this.fuelCapacity=140000;
		this.fuelConsumption=3.0;
		this.aircraftTypeMultiplier=0.7;
	
	}
	
	public double getFlightCost(Airport toAirport){
		double flightOperationCost= getFullness()*flightConstant*currentAirport.getDistance(toAirport);
		return flightOperationCost+currentAirport.departAircraft(this)+toAirport.landAircraft(this);
	}
	public double getFuelConsumption(double distance) {
		double distanceRatio=distance/14000;
		double bathtubCoefficient=25.9324*Math.pow(distanceRatio, 4)-50.5633*Math.pow(distanceRatio, 3)+35.0554*Math.pow(distanceRatio, 2)-9.90346*distanceRatio+1.97413;
		double takeoffConsumption=(weight*0.1)/fuelWeight;
		double cruiseConsumption= fuelConsumption*bathtubCoefficient*distance;
		return takeoffConsumption+cruiseConsumption;
	}
}
