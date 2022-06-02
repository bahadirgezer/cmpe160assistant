package project.airline.aircraft;
import project.airport.Airport;
import project.interfaces.*;

public abstract class Aircraft implements AircraftInterface{
	public Airport located_airport;
	public Airport previous_airport;
	protected double fuelWeight = 0.7;
	public double current_fuel = 0;
	protected double fuelCapacity = 0;
	public double weight = 0;
	public double max_weight = 0.00001;
	
	public Aircraft(Airport located_airport) {
		this.located_airport = located_airport;
	}
	
	
	public boolean canFly(Airport toAirport) {
		double distance = getDistance(located_airport, toAirport);
		double kk = getFuelConsumption(distance);
		if (toAirport.current_aircrafts.size() == toAirport.getCapacity()) {
			return false;
		}
		else if (kk > current_fuel) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public abstract double getFuelConsumption(double distance);
	public abstract double getFC(Airport toAirport);
	
	public double getFuelWeight() {
		return fuelWeight;
	}
	
	public double getFuelCapacity() {
		return fuelCapacity;
	}
	
	public double fly(Airport toAirport) {
		previous_airport = located_airport;
		located_airport = toAirport;
		double flight_cost = getFC(located_airport);
		return flight_cost;
		
	}
	
	public double getDistance(Airport firstAirport, Airport secondAirport) {
		double xs = Math.pow(firstAirport.getX() - secondAirport.getX(),2);
		double ys = Math.pow(firstAirport.getY() - secondAirport.getY(),2);
		double distance = Math.sqrt(xs+ys);
		return distance;
	}
	

	public double addFuel(double fuel) {
		double cost =  fuel * located_airport.getFuelCost();
		current_fuel += fuel;
		return cost;
				
	}

	public double fillUp() {
		double cost =(fuelCapacity-current_fuel) * located_airport.getFuelCost();
		current_fuel = fuelCapacity;
		return cost;
	}

	public boolean hasFuel(double fuel) {
		if (fuel < fuelCapacity) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public double getWeightRatio() {
		double jj = weight/max_weight;
		return jj;
	}
	
}
