package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class WidebodyPassengerAircraft extends PassengerAircraft {
	public WidebodyPassengerAircraft(Airport currentAirport,double operationFee) {
		super(currentAirport,135000,250000,140000,3.0,0.7,operationFee,450);
	}
	public double getFlightCost(Airport toAirport){
		double distance = currentAirport.getDistance(toAirport);
		double fullness = getFullness();
		double flightOperationCost = fullness * distance * 0.15;
		return flightOperationCost;
	}
	public double getFuelConsumption(double distance) {
		double distanceRatio = distance/14000;
		double bathtubCoefficient = ((25.9324*Math.pow(distanceRatio, 4))-(50.5633*Math.pow(distanceRatio, 3))+(35.0554*Math.pow(distanceRatio, 2))-(9.90346*distanceRatio)+1.97413);
		double takeoffConsumption = weight*0.1/fuelWeight;
		double cruiseConsumption = fuelConsumption * bathtubCoefficient * distance;
		return takeoffConsumption + cruiseConsumption;
	}
}
