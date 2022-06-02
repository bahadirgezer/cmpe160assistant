package project.airline_container.aircraft_container;

import java.util.ArrayList;

import project.airport_container.Airport;
import project.interfaces_container.PassengerInterface;
import project.passenger_container.Passenger;
import project.utils.LogWriter;
import project.utils.Util;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {

	protected double floorArea;
	private int economySeats, businessSeats, firstClassSeats;
	private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
	private int[] availableSeatList = new int[3];
	protected double divisor;

	protected double flightCostConstant;
	protected double fuelConstant;
	
	// economy, business, firstClass in order; 
	private int[] seatAreaList = new int[] {1, 3, 8};
	private int occupiedArea = 0;
	
	private final double efficientDistance;
	
	protected ArrayList<Passenger> passengers = new ArrayList<>();

	public PassengerAircraft(int type) {
		setOperationFee(Double.parseDouble(operationFeeList[type]));
		efficientDistance = findEfficientDistance(100, Double.MAX_VALUE);
	}

	public double getEfficientDistance() {
		return efficientDistance;
	}
	
	public int getSeatCapacity() {
		return economySeats + businessSeats + firstClassSeats;
		
	}
	public int getNofOccupiedSeats() {
		return occupiedEconomySeats + occupiedBusinessSeats + occupiedFirstClassSeats;
	}
	
	@Override
	public double getFlightCost(Airport toAirport) {
		return Util.findDistance(currentAirport, toAirport) * getFullness() * flightCostConstant;
	}
	
	public double getCruiseFuelConsumption(double distance){
		double distanceRatio = distance / divisor;
		double bathtubCoefficient = Math.pow(distanceRatio, 4) * 25.9324 - Math.pow(distanceRatio, 3) * 50.5633 + Math.pow(distanceRatio, 2) * 35.0554 - distanceRatio * 9.90346 + 1.97413;
		return distance * bathtubCoefficient * getFuelConsumption();
	}

	private double findEfficientDistance(double dist, double val) {
		if (Math.abs(val) < 2) {
			return dist;
		}
		if (val < derivative(dist * 3 / 2)) {
			return findEfficientDistance(dist * 3 / 2, derivative(dist * 3 / 2));
		}
		else {
			return findEfficientDistance(dist * 2 / 3, derivative(dist * 2 / 3));
		}
	}
	
	private double derivative(double distance) {
		return 4 * 25.9324 / divisor * Math.pow(distance, 3) - 3 * 50.5633 / divisor + 2 * 35.0554 / divisor - 9.90346 / divisor;
	}

	public double getWeightFuelRatio() {
		return weight / fuelWeight;
	}
	
	@Override
	public double loadPassenger(Passenger passenger) {
		if (passenger.getWeight() + weight > maxWeight && occupiedArea + seatAreaList[passenger.getSeatType()] > floorArea && passenger.getSeatType() > 2 || passenger.getSeatType() < 0) {
			System.out.println("Aircraft weight limit exceeded / wrong seat type / not enough area left (Passenger couldn't be placed)");
			return getOperationFee();
		}
		switch (passenger.getSeatType()){
			case 0:
				occupiedEconomySeats++;
				availableSeatList[0]--;
				break;
			case 1:
				occupiedBusinessSeats++;
				availableSeatList[1]--;
				break;
			case 2:
				occupiedFirstClassSeats++;
				availableSeatList[2]--;
				break;
		}
		double loadingFee = getOperationFee() * getAircraftTypeMultiplier() * passenger.getLoadSeatConstant();
		LogWriter.write("4 " + passenger.getID() + " " + ID + " " + passenger.getAirport().getId() + " = -" + loadingFee);
		passenger.setAircraft(this);
		passengers.add(passenger);
		occupiedArea += seatAreaList[passenger.getSeatType()];
		return loadingFee;
	}

	@Override
	public double unloadPassenger(Passenger passenger) {
		if (passenger.getSeatType() > 2 || passenger.getSeatType() < 0) {
			System.out.println("Wrong seat type (Passenger couldn't be unloaded)");
			return -getOperationFee();
		}
		switch (passenger.getSeatType()){
			case 0:
				occupiedEconomySeats--;
				availableSeatList[0]++;
				break;
			case 1:
				occupiedBusinessSeats--;
				availableSeatList[1]++;
				break;
			case 2:
				occupiedFirstClassSeats--;
				availableSeatList[2]++;
				break;
		}
		double revenue = passenger.disembark(currentAirport, getAircraftTypeMultiplier()) * passenger.getUnloadSeatConstant();
		LogWriter.write("5 " + passenger.getID() + " " + ID + " " + passenger.getAirport().getId() + "= " + revenue);
		occupiedArea -= seatAreaList[passenger.getSeatType()];
		passengers.remove(passenger);
		return revenue;
	}
	
	@Override
	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		if (passenger.getSeatType() > 2 || passenger.getSeatType() < 0) {
			return getOperationFee();
		}
		unloadPassenger(passenger);
		passenger.connection();
		double fee = toAircraft.loadPassenger(passenger);
		LogWriter.write("6 " + passenger.getID() + " " + ID + " " + toAircraft.getID() + " = -" + fee);
		return fee;
	}
	
	@Override
	public boolean isFull() {
		return getNofOccupiedSeats() >= getSeatCapacity();
	}
	
	@Override
	public boolean isFull(int seatType) {
		switch (seatType) {
			case 0:
				return economySeats >= occupiedEconomySeats;
			case 1:
				return businessSeats >= occupiedBusinessSeats;
			case 2:
				return firstClassSeats >= occupiedFirstClassSeats;
			default:
				return false;
		}
	}
	
	@Override
	public double getFullness() {
		return (double)getNofOccupiedSeats() / getSeatCapacity();
	}
	
	@Override
	public boolean isEmpty() {
		return (occupiedFirstClassSeats & occupiedBusinessSeats & occupiedEconomySeats) == 0;
	}
	
	@Override
	public double getAvailableWeight() {
		return maxWeight - weight;
	}
	
	@Override
	public boolean setSeats(int economy, int business, int firstClass) {
		if (economy * seatAreaList[0] + business * seatAreaList[1] + firstClass * seatAreaList[2] <= floorArea) {
			economySeats = economy;
			businessSeats = business;
			firstClassSeats = firstClass;
			availableSeatList[0] = economy;
			availableSeatList[1] = business;
			availableSeatList[2] = firstClass;
			LogWriter.write("2 " + ID + " " + economy + " " + business + " " + firstClass);
		}
		return false;
	}

	@Override
	public boolean setAllEconomy() {
		economySeats = (int)floorArea / seatAreaList[0];
		return true;
	}

	@Override
	public boolean setAllBusiness() {
		businessSeats = (int)floorArea / seatAreaList[1];
		return true;
	}

	@Override
	public boolean setAllFirstClass() {
		firstClassSeats = (int)floorArea / seatAreaList[2];
		return true;
	}

	@Override
	public boolean setRemainingEconomy() {
		economySeats += (int)(floorArea - occupiedArea) / seatAreaList[0];
		return true;
	}

	@Override
	public boolean setRemainingBusiness() {
		businessSeats += (int)(floorArea - occupiedArea) / seatAreaList[1];
		return true;
	}

	@Override
	public boolean setRemainingFirstClass() {
		firstClassSeats += (int)(floorArea - occupiedArea) / seatAreaList[2];
		return true;
	}

	public int[] getAvailableSeatList() {
		return availableSeatList;
	}
	
}
