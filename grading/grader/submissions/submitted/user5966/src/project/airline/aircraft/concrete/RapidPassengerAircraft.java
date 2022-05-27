package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class RapidPassengerAircraft extends PassengerAircraft {
	public RapidPassengerAircraft(Airport currentAirport,double operationFee) {
		super(currentAirport,80000,185000,120000,5.3,1.9,operationFee,120);
		// TODO Auto-generated constructor stub
	}
	public double getFlightCost(Airport toAirport){
		double distance = currentAirport.getDistance(toAirport);
		double fullness = getFullness();
		double flightOperationCost = fullness * distance * 0.2;
		return flightOperationCost;
	}
	public double getFuelConsumption(double distance) {
		double distanceRatio = distance/7000;
		double bathtubCoefficient = ((25.9324*Math.pow(distanceRatio, 4))-(50.5633*Math.pow(distanceRatio, 3))+(35.0554*Math.pow(distanceRatio, 2))-(9.90346*distanceRatio)+1.97413);
		double takeoffConsumption = weight*0.1/fuelWeight;
		double cruiseConsumption = fuelConsumption * bathtubCoefficient * distance;
		return takeoffConsumption + cruiseConsumption;
	}
}
