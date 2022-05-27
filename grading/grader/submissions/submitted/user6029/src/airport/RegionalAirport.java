package airport;

import airline.aircraft.Aircraft;

public class RegionalAirport extends Airport {

	public RegionalAirport(int id, double x, double y, int aircraftCapacity, double operationFee, double fuel_cost) {
		super(id, x, y, aircraftCapacity, operationFee, fuel_cost);
		super.airportType = "regional";
	}

	@Override
	public double departAircraft(Aircraft aircraft) {
		double aircraftWeightRatio, aircraftRatio, fullnessCoefficient, constant, departureFee;
		constant = 1.2;
		double numberOfAircraft = numberOfAircrafts();
		aircraftRatio = (numberOfAircraft/aircraftCapacity);
		fullnessCoefficient = 0.6 * Math.pow(Math.E, aircraftRatio);
		aircraftWeightRatio = aircraft.getWeightRatio();
		departureFee = operationFee * aircraftWeightRatio * fullnessCoefficient * constant;
		removeAircraft(aircraft);
		return departureFee;
	}

	@Override
	public double landAircraft(Aircraft aircraft) {
		double aircraftWeightRatio, aircraftRatio, fullnessCoefficient, constant, landingFee;
		constant = 1.3;
		aircraftRatio = numberOfAircrafts()/aircraftCapacity;
		fullnessCoefficient = 0.6 * Math.pow(Math.E, aircraftRatio); 
		aircraftWeightRatio = aircraft.getWeightRatio();
		landingFee = operationFee * aircraftWeightRatio * fullnessCoefficient * constant;
		addAircraft(aircraft);
		return landingFee;
	}
}
