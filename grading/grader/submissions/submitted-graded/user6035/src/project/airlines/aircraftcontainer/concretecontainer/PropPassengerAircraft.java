package project.airlines.aircraftcontainer.concretecontainer;

import project.airlines.aircraftcontainer.PassengerAircraft;
import project.airportContainer.Airport;

class PropPassengerAircraft extends PassengerAircraft {


	public PropPassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport,  operationFee);
		this.weight = 14000;
		this.maxWeight = 23000;
		this.floorArea = 60;
		this.fuelCapacity = 6000;
		this.fuelConsumption = 0.6;
		this.aircraftTypeMultiplier = 0.9;
	}
	public double getFlightCost(Airport toAirport) {
		double fullness = getFullness();
		double distance = Math.pow(Math.abs(currentAirport.getX() * currentAirport.getX() + currentAirport.getY() * currentAirport.getY() - toAirport.getY() * toAirport.getY() - toAirport.getX() * toAirport.getX()), 0.5);
		return fullness * distance * 0.1;
	}
	public double getFuelConsumption(double distance) {
		double distRatio = distance / 2000;
		double bathtubCoeff = 25.9324 * Math.pow(distRatio, 4) -  50.5633 * Math.pow(distRatio, 3) +  35.0554 * Math.pow(distRatio, 2) - 9.90346 * distRatio +  1.97413;
		double takeoffFC = this.getWeight() / this.getFuelWeight() * 0.1;
		double cruiseFC = bathtubCoeff * distance * 0.08;
		return takeoffFC + cruiseFC;
	}
	
	
}
