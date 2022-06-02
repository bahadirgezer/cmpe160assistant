package project.airline.aircraft.concrete;
import project.airline.aircraft.*;
import project.airport.Airport;


public class JetPassengerAircraft extends PassengerAircraft{
	
	public double distance;
	public double current_fuel = 0;
	protected double weight = 10000;
	protected double max_weight = 18000;
	protected double floorArea = 30;
	protected double fuelCapacity = 10000;
	protected double fuelConsumption = 0.7;
	protected double aircraftTypeMultiplier = 5;
	
	public JetPassengerAircraft(Airport located_airport, double operationFee) {
		super(located_airport,operationFee);
	}
	
	public double getFuelConsumption(double distance) {
		
		double d_ratio = distance/5000;
		double bathtub_coefficient = 25.9324 * Math.pow(d_ratio, 4) - 50.5633 * Math.pow(d_ratio, 3) + 35.0554 * Math.pow(d_ratio, 2) - 9.90346 * d_ratio + 1.97413;
		double take_off = weight * 0.1 / fuelWeight;
		double cruise = fuelConsumption * bathtub_coefficient * distance;
		return cruise + take_off;
		
		
	}


	public double getFlightCost(Airport toAirport) {
		double distance = getDistance(previous_airport, toAirport);
		double operational_cost = distance * this.getFullness() * 0.08;
		double flight_cost = previous_airport.departAircraft(this) + toAirport.landAircraft(this) + operational_cost;
		return flight_cost;
	}

	public double getFC(Airport toAirport) {
		return getFlightCost(toAirport);
	}
		

}
