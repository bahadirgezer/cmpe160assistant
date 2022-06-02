package airline.aircraft.concrete;

import airline.aircraft.PassengerAircraft;
import airport.Airport;

public class WidebodyPassengerAircraft extends PassengerAircraft {

	public WidebodyPassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport, operationFee);
		super.weight = 135000;
		super.maxWeight = 250000;
		super.floorArea = 450;
		super.fuelCapacity = 140000;
		super.aircraftTypeMultiplier = 0.7;
	}

	protected double fuelConsumption = 3.0;
	//Fuel consumption of the aircraft will be used in the getFuelConsumption() method and declared in Aircraft.java, equal to 0.7.

	public double getFlightCost(Airport toAirport) {
		return getFlightCostHelper(toAirport,0.15);
	}
	
	public double getFuelConsumption(double distance) {
		return getFuelConsumptionHelper(distance, 14000, 0.1, fuelConsumption);
	}
	
}
