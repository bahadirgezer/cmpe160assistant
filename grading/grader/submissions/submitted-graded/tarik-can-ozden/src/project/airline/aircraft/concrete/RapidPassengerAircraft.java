package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class RapidPassengerAircraft extends PassengerAircraft {

	public RapidPassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport,  operationFee);
		this.weight = 80000;
		this.maxWeight = 185000;
		this.floorArea = 120;
		this.fuelCapacity = 120000;
		this.fuelConsumption = 5.3;
		this.aircraftTypeMultiplier = 1.9;
		this.operationFee = operationFee;
	}
	public double getFlightCost(Airport toAirport) {
		double fullness = getFullness();
		
		//System.out.println("f ");
		//System.out.println(fullness);
		double distance = Math.pow(Math.pow(currentAirport.getX() - toAirport.getX(), 2) + Math.pow(currentAirport.getY() - toAirport.getY(), 2), 0.5);
		//System.out.println("d ");
		//System.out.println(distance);
		return fullness * distance * 0.2;
	}
	public double getFuelConsumption(double distance) {
		double distRatio = (double)distance / 2000;
		double bathtubCoeff = 25.9324 * Math.pow(distRatio, 4) -  50.5633 * Math.pow(distRatio, 3) +  35.0554 * Math.pow(distRatio, 2) - 9.90346 * distRatio +  1.97413;
		double takeoffFC = this.getWeight() / this.getFuelWeight() * 0.1;
		double cruiseFC = bathtubCoeff * distance * 0.1;
		return takeoffFC + cruiseFC;
	}
	
	
}
