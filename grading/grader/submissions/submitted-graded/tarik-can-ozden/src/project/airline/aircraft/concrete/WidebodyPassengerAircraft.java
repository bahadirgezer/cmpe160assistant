package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class WidebodyPassengerAircraft extends PassengerAircraft {

	public WidebodyPassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport,  operationFee);
		this.weight = 135000;
		this.maxWeight = 250000;
		this.floorArea = 450;
		this.fuelCapacity = 140000;
		this.fuelConsumption = 3.0;
		this.aircraftTypeMultiplier = 0.7;
	}
	
	public double getFlightCost(Airport toAirport) {
		double fullness = getFullness();
		double distance = Math.pow(Math.pow(currentAirport.getX() - toAirport.getX(), 2) + Math.pow(currentAirport.getY() - toAirport.getY(), 2), 0.5);
		return fullness * distance * 0.15;
	}
	public double getFuelConsumption(double distance) {
		double distRatio = (double)distance / 14000;
		double bathtubCoeff = 25.9324 * Math.pow(distRatio, 4) -  50.5633 * Math.pow(distRatio, 3) +  35.0554 * Math.pow(distRatio, 2) - 9.90346 * distRatio +  1.97413;
		double takeoffFC = this.getWeight() / this.getFuelWeight() * 0.1;
		double cruiseFC = bathtubCoeff * distance * 0.1;
		return takeoffFC + cruiseFC;
	}
	
	

}
