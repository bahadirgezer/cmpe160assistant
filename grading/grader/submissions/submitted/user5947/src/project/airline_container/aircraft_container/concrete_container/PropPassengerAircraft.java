package project.airline_container.aircraft_container.concrete_container;

import project.airline_container.aircraft_container.PassengerAircraft;

public class PropPassengerAircraft extends PassengerAircraft {
	
	public PropPassengerAircraft() {
		super(0);
		weight = 14000;
		maxWeight = 23000;
		floorArea = 60;
		fuelCapacity = 6000;
		setFuelConsumption(0.6);
		setAircraftTypeMultiplier(0.9);
		divisor = 2000;
		flightCostConstant = 0.1;
		fuelConstant = 0.08;
	}
	
	@Override
	public double getFuelConsumption(double distance){
		return getWeightFuelRatio() * fuelConstant + getCruiseFuelConsumption(distance);
	}
	
}
