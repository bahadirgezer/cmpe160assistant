package project.airlines.aircraftcontainer.concretecontainer;

import project.airlines.aircraftcontainer.PassengerAircraft;
import project.airportContainer.Airport;

class JetPassengerAircraft extends PassengerAircraft {

	
	
	public JetPassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport,  operationFee);
		this.setMaxWeight(18000);
		this.setFloorArea(30);
		this.setFuelCapacity(10000);
		this.setFuelConsumption(0.7);
		this.setAircraftTypeMultiplier(5);
	}
	public double getFlightCost(Airport toAirport) {
		double fullness = getFullness();
		double distance = Math.pow(Math.abs(currentAirport.getX() * currentAirport.getX() + currentAirport.getY() * currentAirport.getY() - toAirport.getY() * toAirport.getY() - toAirport.getX() * toAirport.getX()), 0.5);
		return fullness * distance * 0.08;
	}
	public double getFuelConsumption(double distance) {
		double distRatio = distance / 2000;
		double bathtubCoeff = 25.9324 * Math.pow(distRatio, 4) -  50.5633 * Math.pow(distRatio, 3) +  35.0554 * Math.pow(distRatio, 2) - 9.90346 * distRatio +  1.97413;
		double takeoffFC = this.getWeight() / this.getFuelWeight() * 0.1;
		double cruiseFC = bathtubCoeff * distance * 0.1;
		return takeoffFC + cruiseFC;
	}
	

	

}
