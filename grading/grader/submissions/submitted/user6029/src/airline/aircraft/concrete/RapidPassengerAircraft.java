package airline.aircraft.concrete;

import airline.aircraft.PassengerAircraft;
import airport.Airport;

public class RapidPassengerAircraft extends PassengerAircraft {

	public RapidPassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport, operationFee);
		super.weight = 80000;
		super.maxWeight = 185000;
		super.floorArea = 120;
		super.fuelCapacity = 120000;
		super.aircraftTypeMultiplier = 1.9;
	}

	protected double fuelConsumption = 5.3;
	//Fuel consumption of the aircraft will be used in the getFuelConsumption() method and declared in Aircraft.java, equal to 0.7.

	public double getFlightCost(Airport toAirport) {
		return getFlightCostHelper(toAirport,0.2);
	}
	
	public double getFuelConsumption(double distance) {
		return getFuelConsumptionHelper(distance, 7000, 0.1, fuelConsumption);
	}
}
