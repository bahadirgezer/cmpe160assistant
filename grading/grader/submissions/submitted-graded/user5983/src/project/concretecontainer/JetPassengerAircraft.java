package project.concretecontainer;

import project.aircraftcontainer.PassengerAircraft;
import project.airportcontainer.Airport;

public class JetPassengerAircraft extends PassengerAircraft {
private double flightConstant=0.08;
public double operationFee;
	
	
	public JetPassengerAircraft(Airport currentAirport,double operationFee) {
		super(currentAirport,operationFee);
		this.weight=10000;
		this.maxWeight=18000;
		this.floorArea=30;
		this.fuelCapacity=10000;
		this.fuelConsumption=0.7;
		this.aircraftTypeMultiplier=5;
	
	}
	
	public double getFlightCost(Airport toAirport){
		double flightOperationCost= getFullness()*flightConstant*currentAirport.getDistance(toAirport);
		return flightOperationCost+currentAirport.departAircraft(this)+toAirport.landAircraft(this);
	}
	public double getFuelConsumption(double distance) {
		double distanceRatio=distance/5000;
		double bathtubCoefficient=25.9324*Math.pow(distanceRatio, 4)-50.5633*Math.pow(distanceRatio, 3)+35.0554*Math.pow(distanceRatio, 2)-9.90346*distanceRatio+1.97413;
		double takeoffConsumption=(weight*0.1)/fuelWeight;
		double cruiseConsumption= fuelConsumption*bathtubCoefficient*distance;
		return takeoffConsumption+cruiseConsumption;
	}

}
