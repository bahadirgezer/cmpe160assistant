package project.airline.aircraft.concrete;
import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class WidebodyPassengerAircraft extends PassengerAircraft {
	protected double weight;
	protected double maxWeight;
	protected double floorArea;
	protected double fuelCapacity;
	protected double fuelConsumption;
	protected double aircraftTypeMultiplier;
	
	public WidebodyPassengerAircraft(Airport ID) {
		super(135000, 250000, 450, 140000, 0.7, ID, 1);//last is operationFee
		this.weight = 135000;
		this.maxWeight = 250000;
		this.floorArea = 450;
		this.fuelCapacity = 140000;
		this.fuelConsumption = 3.0;
		this.aircraftTypeMultiplier = 0.7;
	}
	
	public double getFlightCost(Airport toAirport) {
		double distance = this.currentAirport.getDistance(toAirport);
		double fullness = this.getFullness();
		double flightOperationCost = distance*fullness*0.15;
		double totalCost =  flightOperationCost+this.getDepartCost()+this.getLandingCost();
		return totalCost;
	}
	
	public double getFuelConsumption(double distance) {
		double distanceRatio = distance/14000;
		double bathtubCoefficient =  25.9324*Math.pow(distanceRatio, 4)-50.5633*Math.pow(distanceRatio, 3)+35.0554*Math.pow(distanceRatio, 2)-9.90346*distanceRatio+1.97413;
		double takeOff = this.currentWeight*0.1/0.7;
		double cruise = this.fuelConsumption*bathtubCoefficient*distance;
		return takeOff+cruise;
	}

}
