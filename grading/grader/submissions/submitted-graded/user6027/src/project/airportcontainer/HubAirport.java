package project.airportcontainer;
import project.airlinecontainer.aircraftcontainer.*;
import java.util.*;

public class HubAirport extends Airport{

	
	public HubAirport(long id, double x, double y,double fuelcost,double operationfee,int aircraftCapacity) {
		super(id, x, y, fuelcost, operationfee, aircraftCapacity);
	}
	public double departAircraft(Aircraft aircraft) {
		double aircraft_ratio=(double)current_aircrafts.size()/aircraftCapacity;
		double fullnes_coefficient=0.6*Math.exp(aircraft_ratio);
		double aircraft_weight_ratio=aircraft.getWeightRatio();
		current_aircrafts.remove((Object)aircraft);
		return operationFee*fullnes_coefficient*aircraft_weight_ratio*0.7;
	}
	public double landAircraft(Aircraft aircraft) {
		double aircraft_ratio=(double)current_aircrafts.size()/aircraftCapacity;
		double fullnes_coefficient=0.6*Math.exp(aircraft_ratio);
		double aircraft_weight_ratio=aircraft.getWeightRatio();
		current_aircrafts.add(aircraft);
		return operationFee*fullnes_coefficient*aircraft_weight_ratio*0.8;
	}
	
}
