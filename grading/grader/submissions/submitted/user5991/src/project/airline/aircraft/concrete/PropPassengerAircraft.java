package project.airline.aircraft.concrete;
import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;

public class PropPassengerAircraft extends PassengerAircraft {
	

	//Airport currentAirport,double weight,double maxWeight,double floorArea,double fuelCapacity,double fuelConsumption,double aircraftTypeMultiplier,double operationFee
	public PropPassengerAircraft(Airport currentAirport,double operationFee) {
		super(currentAirport,14000,23000,60,6000,0.6,0.9,operationFee);
		}
	
	protected double getFlightCost(Airport toAirport) {
		//departure ve landing costlarý da eklemem lazým
		double distance = currentAirport.getDistance(toAirport);
		double fullness = getFullness();
		return 0.1*distance*fullness;
	}
	
	protected double getFuelConsumption(double distance) {
		double distanceratio = distance/2000;
		double bathtubcoef = 25.9324*Math.pow(distanceratio, 4)-50.5633*Math.pow(distanceratio, 3)+35.0554*Math.pow(distanceratio, 2)-9.90346*distanceratio+1.97413;
		double takeofffuelcons = weight*0.08/fuelWeight;
		double cruisefuel = fuelConsumption*bathtubcoef*distance;
		return takeofffuelcons+cruisefuel;
	}
	
	

	
	
}
