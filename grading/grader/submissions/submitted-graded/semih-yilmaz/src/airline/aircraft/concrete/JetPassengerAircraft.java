package airline.aircraft.concrete;

import airline.aircraft.PassengerAircraft;
import airport.Airport;

public class JetPassengerAircraft extends PassengerAircraft {

	public JetPassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport, operationFee);
		super.weight = 10000;
		super.maxWeight = 18000;
		super.floorArea = 30;
		super.fuelCapacity = 10000;
		super.aircraftTypeMultiplier = 5;
	}

	protected double fuelConsumption = 0.7;
	//Fuel consumption of the aircraft will be used in the getFuelConsumption() method and declared in Aircraft.java, equal to 0.7.

	public double getFlightCost(Airport toAirport) {
		return getFlightCostHelper(toAirport,0.08);
	}
	
	public double getFuelConsumption(double distance) {
		return getFuelConsumptionHelper(distance, 5000, 0.1, fuelConsumption);
	}
}
