package project.airport_container;

import project.airline_container.aircraft_container.Aircraft;

public class RegionalAirport extends Airport {
	public RegionalAirport(int a,double b,double c,double d,double e,int f){
		super(a,b,c,d,e,f,3);
	}
	@Override
	public double departAircraft(Aircraft aircraft) {
		// TODO Auto-generated method stub
		double a = getDepartureCost(aircraft);
		super.aircraftNo -= 1;
		return a;
	}

	@Override
	public double landAircraft(Aircraft aircraft) {
		// TODO Auto-generated method stub
		double a = getLandingCost(aircraft);
		super.aircraftNo += 1;
		return a;
	}

	@Override
	public double getDepartureCost(Aircraft aircraft) {
		// TODO Auto-generated method stub
		
		return super.operationFee*0.6*Math.pow(Math.E, getaircraftRatio())*(aircraft.getWeightRatio())*1.2;
	}
	@Override
	public double getLandingCost(Aircraft aircraft) {
		// TODO Auto-generated method stub
		return super.operationFee*0.6*Math.pow(Math.E, getaircraftRatio())*(aircraft.getWeightRatio())*1.3;
	}
}
