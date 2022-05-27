package airline.aircraft.concrete;

import airline.aircraft.PassengerAircraft;
import airport.Airport;

public class PropPassengerAircraft extends PassengerAircraft {

	protected double fuelConsumption = 0.6;
	
	public PropPassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport, operationFee);
		super.weight = 14000;
		super.maxWeight = 23000;
		super.floorArea = 60;
		super.fuelCapacity = 6000;
		super.aircraftTypeMultiplier = 0.9;
	}

	
	public double getFlightCost(Airport toAirport) {
		return getFlightCostHelper(toAirport,0.1);
	}
	
	public double getFuelConsumption(double distance) {
		return getFuelConsumptionHelper(distance, 2000, 0.08, fuelConsumption);
	}

}
