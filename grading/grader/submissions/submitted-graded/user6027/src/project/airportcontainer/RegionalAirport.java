package project.airportcontainer;

import project.airlinecontainer.aircraftcontainer.Aircraft;

public class RegionalAirport extends Airport {

	public RegionalAirport(long id, double x, double y,double fuelcost,double operationfee,int aircraftCapacity) {
		super(id, x, y, fuelcost, operationfee, aircraftCapacity);
	}
	public double departAircraft(Aircraft aircraft) {
		double aircraft_ratio=(double)current_aircrafts.size()/aircraftCapacity;
		double fullnes_coefficient=0.6*Math.exp(aircraft_ratio);
		double aircraft_weight_ratio=(double) aircraft.get_weight()/aircraft.getMaxWeight();
		current_aircrafts.remove((Object)aircraft);
		return super.operationFee*fullnes_coefficient*aircraft_weight_ratio*1.2;
	}
	public double landAircraft(Aircraft aircraft) {
		double aircraft_ratio=(double)current_aircrafts.size()/aircraftCapacity;
		double fullnes_coefficient=0.6*Math.exp(aircraft_ratio);
		double aircraft_weight_ratio=aircraft.getWeightRatio();
		current_aircrafts.add(aircraft);
		return super.operationFee*fullnes_coefficient*aircraft_weight_ratio*1.3;
	}
}
