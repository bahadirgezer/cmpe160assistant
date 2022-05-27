package project.airlines.aircraftcontainer.concretecontainer;

import project.airlines.aircraftcontainer.PassengerAircraft;
import project.airportContainer.Airport;

public class RapidPassengerAircraft extends PassengerAircraft {

	public RapidPassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport,  operationFee);
		this.weight = 80000;
		this.maxWeight = 185000;
		this.floorArea = 120;
		this.fuelCapacity = 120000;
		this.fuelConsumption = 5.3;
		this.aircraftTypeMultiplier = 1.9;
	}
	public double getFlightCost(Airport toAirport) {
		double fullness = getFullness();
		double distance = Math.pow(Math.abs(currentAirport.getX() * currentAirport.getX() + currentAirport.getY() * currentAirport.getY() - toAirport.getY() * toAirport.getY() - toAirport.getX() * toAirport.getX()), 0.5);
		return fullness * distance * 0.2;
	}
	public double getFuelConsumption(double distance) {
		double distRatio = distance / 2000;
		double bathtubCoeff = 25.9324 * Math.pow(distRatio, 4) -  50.5633 * Math.pow(distRatio, 3) +  35.0554 * Math.pow(distRatio, 2) - 9.90346 * distRatio +  1.97413;
		double takeoffFC = this.getWeight() / this.getFuelWeight() * 0.1;
		double cruiseFC = bathtubCoeff * distance * 0.1;
		return takeoffFC + cruiseFC;
	}
	
	
}
