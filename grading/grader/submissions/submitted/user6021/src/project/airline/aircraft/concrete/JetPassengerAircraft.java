package project.airline.aircraft.concrete;
import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class JetPassengerAircraft extends PassengerAircraft{
	protected double weight;
	protected double maxWeight;
	protected double floorArea;
	protected double fuelCapacity;
	protected double fuelConsumption;
	protected double aircraftTypeMultiplier;
	
	public JetPassengerAircraft(Airport ID) {
		super(10000, 18000, 30, 10000, 5, ID, 3);//last is operationFee
		this.weight = 10000;
		this.maxWeight = 18000;
		this.floorArea = 30;
		this.fuelCapacity = 10000;
		this.fuelConsumption = 0.7;
		this.aircraftTypeMultiplier = 5;
	}
	
	public double getFlightCost(Airport toAirport) {
		double distance = this.currentAirport.getDistance(toAirport);
		double fullness = this.getFullness();
		double flightOperationCost = distance*fullness*0.08;
		double totalCost =  flightOperationCost+this.getDepartCost()+this.getLandingCost();
		return totalCost;
	}
	

	public double getFuelConsumption(double distance) {
		double distanceRatio = distance/5000;
		double bathtubCoefficient =  25.9324*Math.pow(distanceRatio, 4)-50.5633*Math.pow(distanceRatio, 3)+35.0554*Math.pow(distanceRatio, 2)-9.90346*distanceRatio+1.97413;
		double takeOff = this.currentWeight*0.1/0.7;
		double cruise = this.fuelConsumption*bathtubCoefficient*distance;
		return takeOff+cruise;
	}
}
