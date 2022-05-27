package project.airline_container.aircraft_container.concrete_container;

import project.airline_container.aircraft_container.PassengerAircraft;

public class WidebodyPassengerAircraft extends PassengerAircraft {

	public WidebodyPassengerAircraft() {
		super(1);
		weight = 135000;
		maxWeight = 250000;
		floorArea = 450;
		fuelCapacity = 140000;
		setFuelConsumption(3.0);
		setAircraftTypeMultiplier(0.7);
		divisor = 14000;
		flightCostConstant = 0.2;
		fuelConstant = 0.1;
	}
	
	@Override
	public double getFuelConsumption(double distance){
		return getWeightFuelRatio() * fuelConstant + getCruiseFuelConsumption(distance);
	}
	
}
