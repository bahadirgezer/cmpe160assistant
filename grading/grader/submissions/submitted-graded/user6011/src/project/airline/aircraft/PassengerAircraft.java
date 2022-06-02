package project.airline.aircraft;

import java.util.HashMap;

import project.interfaces.PassengerInterface;
import project.passenger.Passenger;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {
	
	protected double floorArea;
	private int economySeats, businessSeats, firstClassSeats;
	private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
	protected HashMap<Long, Passenger> passengers = new HashMap<Long, Passenger>();
	
	public double loadPassenger(Passenger passenger) {
		if (weight + passenger.getWeight() > maxWeight) {
			return operationFee;
		}
		for (int i = passenger.getPassengerType(); i >= 0; i--) {
			switch (i) {
			case 0:
				if (occupiedEconomySeats == economySeats) {
					break;
				}
				occupiedEconomySeats += 1;
				passenger.setSeatType(0);
				weight += passenger.getWeight();
				currentAirport.removePassenger(passenger);
				addPassenger(passenger);
				passenger.board(0);
				return operationFee*aircraftTypeMultiplier*1.2;
			case 1:
				if (occupiedBusinessSeats == businessSeats) {
					break;
				}
				occupiedBusinessSeats += 1;
				passenger.setSeatType(1);
				weight += passenger.getWeight();
				currentAirport.removePassenger(passenger);
				addPassenger(passenger);
				passenger.board(1);
				return operationFee*aircraftTypeMultiplier*1.5;
			case 2:
				if (occupiedFirstClassSeats == firstClassSeats) {
					break;
				}
				occupiedFirstClassSeats += 1;
				passenger.setSeatType(2);
				weight += passenger.getWeight();
				currentAirport.removePassenger(passenger);
				addPassenger(passenger);
				passenger.board(2);
				return operationFee*aircraftTypeMultiplier*2.5;
			}
		}
		return operationFee;
	}
	public double unloadPassenger(Passenger passenger) {
		if ((passenger.canDisembark(currentAirport)) == false) {
			return operationFee;
		}
		double seatConstant = 1;
		switch(passenger.getSeatType()) {
		case 0:
			occupiedEconomySeats -= 1;
			break;
		case 1:
			occupiedBusinessSeats -= 1;
			seatConstant = 2.8;
			break;
		case 2:
			occupiedFirstClassSeats -= 1;
			seatConstant = 7.5;
			break;
		}
		weight -= passenger.getWeight();
		currentAirport.addPassenger(passenger);
		removePassenger(passenger);
		double price = passenger.disembark(currentAirport, aircraftTypeMultiplier);
		return seatConstant*price;
	}
	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		if (toAircraft.getAvailableWeight() + passenger.getWeight() > toAircraft.getMaxWeight()) {
			return toAircraft.getOperationFee();
		}
		switch(passenger.getSeatType()) {
		case 0:
			occupiedEconomySeats -= 1;
			break;
		case 1:
			occupiedBusinessSeats -= 1;
			break;
		case 2:
			occupiedFirstClassSeats -= 1;
			break;
		}
		weight -= passenger.getWeight();
		currentAirport.addPassenger(passenger);
		removePassenger(passenger);
		double loadingFee = toAircraft.loadTransferringPassenger(passenger);
		if (loadingFee == toAircraft.getOperationFee()) {
			switch(passenger.getSeatType()) {
			case 0:
				occupiedEconomySeats += 1;
				break;
			case 1:
				occupiedBusinessSeats += 1;
				break;
			case 2:
				occupiedFirstClassSeats += 1;
				break;
			}
			weight += passenger.getWeight();
			currentAirport.removePassenger(passenger);
			addPassenger(passenger);
		}
		return loadingFee;
	}
	protected double loadTransferringPassenger(Passenger passenger) {
		for (int i = passenger.getPassengerType(); i >= 0; i--) {
			switch (i) {
			case 0:
				if (occupiedEconomySeats == economySeats) {
					break;
				}
				occupiedEconomySeats += 1;
				passenger.setSeatType(0);
				weight += passenger.getWeight();
				currentAirport.removePassenger(passenger);
				addPassenger(passenger);
				passenger.connection(0);
				return operationFee*aircraftTypeMultiplier*1.2;
			case 1:
				if (occupiedBusinessSeats == businessSeats) {
					break;
				}
				occupiedBusinessSeats += 1;
				passenger.setSeatType(1);
				weight += passenger.getWeight();
				currentAirport.removePassenger(passenger);
				addPassenger(passenger);
				passenger.connection(1);
				return operationFee*aircraftTypeMultiplier*1.5;
			case 2:
				if (occupiedFirstClassSeats == firstClassSeats) {
					break;
				}
				occupiedFirstClassSeats += 1;
				passenger.setSeatType(2);
				weight += passenger.getWeight();
				currentAirport.removePassenger(passenger);
				addPassenger(passenger);
				passenger.connection(2);
				return operationFee*aircraftTypeMultiplier*2.5;
			}
		}
		return operationFee;
	}
	public boolean isFull() {
		if (economySeats + businessSeats + firstClassSeats - occupiedEconomySeats - occupiedBusinessSeats - occupiedFirstClassSeats == 0) {
			return true;
		}
		return false;
	}
	public boolean isFull(int seatType) {
		if (seatType == 0) {
			return (economySeats-occupiedEconomySeats == 0);
		} else if (seatType == 1) {
			return (businessSeats-occupiedBusinessSeats == 0);
		} else {
			return (firstClassSeats-occupiedFirstClassSeats == 0);
		}
	}
	public boolean isEmpty() {
		return !(this.isFull());
	}
	public double getAvailableWeight() {
		return weight;
	}
	public boolean setSeats(int economy, int business, int firstClass) {
		if (economy + 3*business + 8*firstClass > floorArea) {
			return false;
		}
		economySeats = economy;
		businessSeats = business;
		firstClassSeats = firstClass;
		floorArea -= (economy + 3*business + 8*firstClass);
		return true;
	}
	public boolean setAllEconomy() {
		floorArea += economySeats + 3*businessSeats + 8*firstClassSeats;
		businessSeats = 0;
		firstClassSeats = 0;
		economySeats = (int)floorArea;
		floorArea = 0;
		return true;
	}
	public boolean setAllBusiness() {
		floorArea += economySeats + 3*businessSeats + 8*firstClassSeats;
		economySeats = 0;
		firstClassSeats = 0;
		double seats = floorArea/3;
		floorArea -= 3*seats;
		businessSeats = (int)seats;
		return true;
	}
	public boolean setAllFirstClass() {
		floorArea += economySeats + 3*businessSeats + 8*firstClassSeats;
		businessSeats = 0;
		economySeats = 0;
		double seats = floorArea/8;
		floorArea -= 8*seats;
		businessSeats = (int)seats;
		return true;
	}
	public boolean setRemainingEconomy() {
		economySeats = (int)floorArea;
		floorArea = 0;
		return true;
	}
	public boolean setRemainingBusiness() {
		double seats = floorArea/3;
		floorArea -= 3*seats;
		businessSeats = (int)seats;
		return true;
	}
	public boolean setRemainingFirstClass() {
		double seats = floorArea/8;
		floorArea -= 8*seats;
		businessSeats = (int)seats;
		return true;
	}
	public void clearAllSeats() {
		floorArea += economySeats + 3*businessSeats + 8*firstClassSeats;
		economySeats = 0;
		businessSeats = 0;
		economySeats = 0;
	}
	public double getFullness() {
		return (occupiedEconomySeats + occupiedBusinessSeats + occupiedFirstClassSeats)/(economySeats + businessSeats + firstClassSeats);
	}
	public void addPassenger(Passenger passenger) {
		passengers.put(passenger.getID(), passenger);
	}
	public void removePassenger(Passenger passenger) {
		passengers.remove(passenger.getID());
	}
	public HashMap<Long, Passenger> getPassengers() {
		return passengers;
	}
	public double getFloorArea() {
		return floorArea;
	}
	public int getEconomySeats() {
		return economySeats;
	}
	public int getBusinessSeats() {
		return businessSeats;
	}
	public int getFirstClassSeats() {
		return firstClassSeats;
	}
}
