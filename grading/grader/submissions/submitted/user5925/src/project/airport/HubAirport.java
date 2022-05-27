package project.airport;

import project.airline.aircraft.Aircraft;

public class HubAirport extends Airport{
	
	public HubAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		super(ID,x,y,fuelCost,operationFee,aircraftCapacity);
	}
	
	public double departAircraft(Aircraft aircraft) {
		double aircraft_ratio = current_aircrafts.size() / aircraftCapacity;
		double fullness_coefficient = 0.6 * java.lang.Math.exp(aircraft_ratio);
		double aircraft_weight_ratio = aircraft.getWeightRatio();
		double departureFee = operationFee * aircraft_weight_ratio * fullness_coefficient * 0.7;
		current_aircrafts.remove(aircraft);
		return departureFee;
		
	}

	public double landAircraft(Aircraft aircraft) {
		double aircraft_ratio = current_aircrafts.size() / aircraftCapacity;
		double fullness_coefficient = 0.6 * java.lang.Math.exp(aircraft_ratio);
		double aircraft_weight_ratio = aircraft.getWeightRatio();
		double landingFee = operationFee * aircraft_weight_ratio * fullness_coefficient * 0.8;
		current_aircrafts.add(aircraft);
		return landingFee;
	}
}
