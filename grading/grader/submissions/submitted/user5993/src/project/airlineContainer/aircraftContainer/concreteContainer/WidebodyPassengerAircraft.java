package project.airlineContainer.aircraftContainer.concreteContainer;

import project.airlineContainer.aircraftContainer.PassengerAircraft;
import project.airportContainer.Airport;

public class WidebodyPassengerAircraft extends PassengerAircraft {

	public WidebodyPassengerAircraft(Airport currentAirport, double weight, double maxWeight, double fuelWeight,
			double fuel, double fuelCapacity, int economySeats, int businessSeats, int firstClassSeats,
			int occupiedEconomySeats, int occupiedBusinessSeats, int occupiedFirstClassSeats, double floorArea,double operationFee) {
		super(currentAirport, weight, maxWeight, fuelWeight, fuel, fuelCapacity, economySeats, businessSeats,
				firstClassSeats, occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats,floorArea,operationFee);
		// TODO Auto-generated constructor stub
	}
	protected double weight=135000;
	protected double maxWeight=250000;
	protected double floorArea=450;
	protected double fuelCapacity=140000;
	protected double fuelConsumption=3.0;
	protected double aircraftTypeMultiplier=0.7;
	public double getFlightCost(Airport toAirport){
		double constant=0.15;
		double distance= this.currentAirport.getDistance(toAirport);
		double fullness=this.getFullness();
		double flightOperationCosts=constant*distance*fullness;

		double landingFee=toAirport.landAircraft(this);

		
		double departingFee=this.currentAirport.departAircraft(this);

		
		return 	departingFee+flightOperationCosts+landingFee;
	}
	public double getFuelConsumption(double distance){
		double distanceRatio=distance/14000;
		double bathtubCoefficient = 25.9324*Math.pow(distanceRatio,4 ) - 50.5633* Math.pow(distanceRatio,3) + 35.0554*Math.pow(distanceRatio,2) -9.90346*distanceRatio + 1.97413;
		double takeoffFuelConsumption=weight*0.1/this.fuelWeight;
		double cruiseFuelConsumption=this.fuelConsumption*bathtubCoefficient*distance;
		return takeoffFuelConsumption+cruiseFuelConsumption;
	}
}
