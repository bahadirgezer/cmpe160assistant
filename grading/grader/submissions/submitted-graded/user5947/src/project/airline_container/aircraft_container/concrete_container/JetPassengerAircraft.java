package project.airline_container.aircraft_container.concrete_container;

import project.airline_container.aircraft_container.PassengerAircraft;

public class JetPassengerAircraft extends PassengerAircraft {
	
	public JetPassengerAircraft() {
		super(3);
		weight = 10000;
		maxWeight = 18000;
		floorArea = 30;
		fuelCapacity = 10000;
		setFuelConsumption(0.7);
		setAircraftTypeMultiplier(5);
		divisor = 5000;
		flightCostConstant = 0.08;
		fuelConstant = 0.1;
	}
	
	@Override
	public double getFuelConsumption(double distance){
		return getWeightFuelRatio() * fuelConstant + getCruiseFuelConsumption(distance);
	}
	
}
