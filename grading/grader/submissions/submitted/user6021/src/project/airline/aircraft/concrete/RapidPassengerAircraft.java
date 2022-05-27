package project.airline.aircraft.concrete;
import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class RapidPassengerAircraft extends PassengerAircraft{
	protected double weight;
	protected double maxWeight;
	protected double floorArea;
	protected double fuelCapacity;
	protected double fuelConsumption;
	protected double aircraftTypeMultiplier;
	
	public RapidPassengerAircraft(Airport ID) {
		super(80000, 185000, 120, 120000, 1.9, ID, 2);//last is operationFee
		this.weight = 80000;
		this.maxWeight = 185000;
		this.floorArea = 120;
		this.fuelCapacity = 120000;
		this.fuelConsumption = 5.3;
		this.aircraftTypeMultiplier = 1.9;
	}
	
	public double getFlightCost(Airport toAirport) {
		double distance = this.currentAirport.getDistance(toAirport);
		double fullness = this.getFullness();
		double flightOperationCost = distance*fullness*0.2;
		double totalCost =  flightOperationCost+this.getDepartCost()+this.getLandingCost();
		return totalCost;
	}
	
	public double getFuelConsumption(double distance) {
		double distanceRatio = distance/7000;
		double bathtubCoefficient =  25.9324*Math.pow(distanceRatio, 4)-50.5633*Math.pow(distanceRatio, 3)+35.0554*Math.pow(distanceRatio, 2)-9.90346*distanceRatio+1.97413;
		double takeOff = this.currentWeight*0.1/0.7;
		double cruise = this.fuelConsumption*bathtubCoefficient*distance;
		return takeOff+cruise;
	}

}
