package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class PropPassengerAircraft extends PassengerAircraft {
	public PropPassengerAircraft(Airport currentAirport,double operationFee) {
		super(currentAirport,14000,23000,6000,0.6,0.9,operationFee,60);
	}
	public double getFlightCost(Airport toAirport){
		double distance = currentAirport.getDistance(toAirport);
		double fullness = getFullness();
		double flightOperationCost = fullness * distance * 0.1;
		return flightOperationCost;
	}
	public double getFuelConsumption(double distance) {
		double distanceRatio = distance/2000;
		double bathtubCoefficient = ((25.9324*Math.pow(distanceRatio, 4))-(50.5633*Math.pow(distanceRatio, 3))+(35.0554*Math.pow(distanceRatio, 2))-(9.90346*distanceRatio)+1.97413);
		double takeoffConsumption = weight*0.08/fuelWeight;
		double cruiseConsumption = fuelConsumption * bathtubCoefficient * distance;
		return takeoffConsumption + cruiseConsumption;
	}
	
}
