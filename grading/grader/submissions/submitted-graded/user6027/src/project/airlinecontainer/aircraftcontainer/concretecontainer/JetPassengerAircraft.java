package project.airlinecontainer.aircraftcontainer.concretecontainer;
import project.airlinecontainer.aircraftcontainer.*;
import project.airportcontainer.Airport;

public class JetPassengerAircraft extends PassengerAircraft{

	public JetPassengerAircraft(double operationfee, Airport currentAirport, double weigth, double maxWeight, double floorarea, double fuelCapacity, double fuelConsumption, double aircraftTypeMultiplier) {
		super(operationfee, currentAirport, aircraftTypeMultiplier, aircraftTypeMultiplier, aircraftTypeMultiplier, aircraftTypeMultiplier, aircraftTypeMultiplier, aircraftTypeMultiplier);
		// TODO Auto-generated constructor stub
	}

	protected double weight=10000;
	protected double maxWeight=18000;
	protected double floorArea=30;
	protected double fuelCapacity=10000;
	protected double fuelConsumption=0.7;
	protected double aircraftTypeMultiplier=5;
	
	public double  getFlightCost(Airport toAirport){
		double distancex=Math.pow(currentAirport.get_coordinatesx()-toAirport.get_coordinatesx(),2);
		double distancey=Math.pow(currentAirport.get_coordinatesy()-toAirport.get_coordinatesy(),2);
		double distance=Math.sqrt(distancex+distancey);
		double seat_ratio = this.getseatratio();
		double flight_operation_cost = 0.08*seat_ratio*distance;
		return toAirport.landAircraft(this)+currentAirport.departAircraft(this)+flight_operation_cost;
	}
	
	public double getFuelConsumption(double distance) {
		double distance_ratio=(double) distance/5000;
		double bathtub_coefficient=25.9324*Math.pow(distance_ratio, 4)-50.5633*Math.pow(distance_ratio, 3)+35.0554*Math.pow(distance_ratio, 2)-9.90346*distance_ratio+1.97413;
		double takeoff_fuel_consumption=(double)weight*0.1/0.7;
		double cruise_fuel_consumption = this.fuelConsumption*bathtub_coefficient*distance;
		return takeoff_fuel_consumption + cruise_fuel_consumption;
	}
	public double get_weight(){
		return this.weight;
	}
	public double getMaxWeight() {
		return maxWeight;
	}
	public double getfloorArea() {
		return this.floorArea;
	}
	public double getaircraftTypeMultiplier() {
		return this.aircraftTypeMultiplier;
	}
	public double getfuelcapacity() {
		return this.fuelCapacity;
	}
	
}
