package project.airline.aircraft.concrete;
import project.airline.aircraft.*;
import project.airport.*;

public class PropPassengerAircraft extends PassengerAircraft {
	
	public double distance;
	protected double weight = 14000;
	protected double max_weight = 23000;
	protected double floorArea = 60;
	protected double fuelCapacity = 6000;
	protected double fuelConsumption = 0.6;
	protected double aircraftTypeMultiplier = 0.9;
	
	public PropPassengerAircraft(Airport located_airport) {
		this.located_airport = located_airport;
	}
	
	
	public double getFuelConsumption(double distance) {
		
		double d_ratio = distance/2000;
		double bathtub_coefficient = 25.9324 * Math.pow(d_ratio, 4) - 50.5633 * Math.pow(d_ratio, 3) + 35.0554 * Math.pow(d_ratio, 2) - 9.90346 * d_ratio + 1.97413;
		double take_off = weight * 0.08 * fuelWeight;
		double cruise = fuelConsumption * bathtub_coefficient * distance;
		return cruise + take_off;
				
		
	}
	

	public double getFlightCost(Airport toAirport) {
		double distance = getDistance(previous_airport, toAirport);
		double operational_cost = distance * this.getFullness() * 0.1;
		double flight_cost = previous_airport.departAircraft(this) + toAirport.landAircraft(this) + operational_cost;
		return flight_cost;
	}

	public double getFC(Airport toAirport) {
		return getFlightCost(toAirport);
	}
		
		
}
