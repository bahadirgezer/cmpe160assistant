package project.airline_container.aircraft_container.concrete_container;

import project.airline_container.aircraft_container.PassengerAircraft;

public class RapidPassengerAircraft extends PassengerAircraft {
	
	public RapidPassengerAircraft() {
		super(2);
		weight = 80000;
		maxWeight = 185000;
		floorArea = 120;
		fuelCapacity = 120000;
		setFuelConsumption(5.3);
		setAircraftTypeMultiplier(1.9);
		divisor = 7000;
		flightCostConstant = 0.15;
		fuelConstant = 0.1;
	}
	
	@Override
	public double getFuelConsumption(double distance){
		return getWeightFuelRatio() * fuelConstant + getCruiseFuelConsumption(distance);
	}
	
}
