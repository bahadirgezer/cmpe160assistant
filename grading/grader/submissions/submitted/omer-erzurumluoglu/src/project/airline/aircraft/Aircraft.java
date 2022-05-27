package project.airline.aircraft;

import project.interfaces.AircraftInterface;
import project.passenger.Passenger;
import project.airline.Airline;
import project.airport.Airport;

public abstract class Aircraft implements AircraftInterface {
	protected Airport currentAirport;
	protected double weight;
	protected double maxWeight;
	protected double fuelWeight = 0.7;
	protected double fuel;
	protected double fuelCapacity;
	protected double fuelConsumption;
	protected double aircraftTypeMultiplier;
	protected Airline theAirline;

	public Aircraft(Airport currentAirport, double weight, double maxWeight, double fuelCapacity,
			double fuelConsumption, double aircraftTypeMultiplier) {
		this.currentAirport = currentAirport;
		this.weight = weight;
		this.maxWeight = maxWeight;
		this.fuel = 0;
		this.fuelCapacity = fuelCapacity;
		this.fuelConsumption = fuelConsumption;
		this.aircraftTypeMultiplier = aircraftTypeMultiplier;

	}

	public double fly(Airport toAirport) {
		if (canFly(toAirport)) {
			double flightCost = getFlightCost(toAirport);
			fuel -= getFuelConsumption(currentAirport.getDistance(toAirport));
			currentAirport = toAirport;
			return flightCost;
		} else {
			return 0;
		}

	}

	public double addFuel(double fuel) {
		if ((fuel + this.fuel) < fuelCapacity && (weight + fuel * fuelWeight) <= maxWeight) {
			this.fuel += fuel;
			weight += fuel * fuelWeight;
			writeFuel(fuel);
			return (fuel * currentAirport.getFuelCost());
		} else {
			return fillUp();
		}

	}

	public double fillUp() {
		if (this.fuel == fuelCapacity || (weight + fuel * fuelWeight) > maxWeight) {
			writeFuel(0);
			return 0;
		}
		double fuel;
		fuel = (fuelCapacity - this.fuel);
		this.fuel = fuelCapacity;
		weight += fuel * fuelWeight;
		writeFuel(fuel);
		return (fuel * currentAirport.getFuelCost());
	}

	public boolean hasFuel(double fuel) {
		if (this.fuel >= fuel) {
			return true;
		} else {
			return false;
		}
	}

	public double getWeightRatio() {
		return (weight / maxWeight);
	}

	protected abstract double getFuelConsumption(double distance);

	protected abstract double getFlightCost(Airport toAirport);

	public boolean canFly(Airport toAirport) {
		if (toAirport.getAircraftRatio() == 1) {
			return false;
		}
		if (fuel < getFuelConsumption(currentAirport.getDistance(toAirport))) {
			return false;
		}
		if (weight > maxWeight) {
			return false;
		} else {
			return true;
		}

	}

	abstract public double getOperationFee();

	abstract public void setOperationFee(double oF);

	abstract public double loadPassenger(Passenger passenger); // vakit yeterse sil

	abstract public double unloadPassenger(Passenger passenger);

	abstract public boolean setSeats(int economy, int business, int firstClass);

	public void setAirline(Airline airline) {
		theAirline = airline;
	}

	private void writeFuel(double fuel) {
		theAirline.write1("3 " + theAirline.Aircrafts.indexOf(this) + " " + fuel +"\n");
	}

}
