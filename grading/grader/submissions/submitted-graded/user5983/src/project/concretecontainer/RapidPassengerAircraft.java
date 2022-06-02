package project.concretecontainer;

import project.aircraftcontainer.PassengerAircraft;

import project.airportcontainer.Airport;

public class RapidPassengerAircraft extends PassengerAircraft {
private double flightConstant=0.2;
public double operationFee;
	
	
	public RapidPassengerAircraft(Airport currentAirport,double operationFee) {
		super(currentAirport,operationFee);
		this.weight=80000;
		this.maxWeight=185000;
		this.floorArea=120;
		this.fuelCapacity=120000;
		this.fuelConsumption=5.3;
		this.aircraftTypeMultiplier=1.9;
	
	}
	
	public double getFlightCost(Airport toAirport){
		double flightOperationCost= getFullness()*flightConstant*currentAirport.getDistance(toAirport);
		return flightOperationCost+currentAirport.departAircraft(this)+toAirport.landAircraft(this);
	}
	public double getFuelConsumption(double distance) {
		double distanceRatio=distance/7000;
		double bathtubCoefficient=25.9324*Math.pow(distanceRatio, 4)-50.5633*Math.pow(distanceRatio, 3)+35.0554*Math.pow(distanceRatio, 2)-9.90346*distanceRatio+1.97413;
		double takeoffConsumption=(weight*0.1)/fuelWeight;
		double cruiseConsumption= fuelConsumption*bathtubCoefficient*distance;
		return takeoffConsumption+cruiseConsumption;
	}
}
