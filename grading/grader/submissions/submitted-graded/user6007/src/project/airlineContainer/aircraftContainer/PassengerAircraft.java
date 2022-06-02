package project.airlineContainer.aircraftContainer;

import project.interfacesContainer.PassengerInterface;
import java.util.ArrayList;
import java.lang.Math;
import project.airportContainer.*;
import project.passengerContainer.Passenger;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {
	protected double floorArea;
	private int economySeats, businessSeats, firstClassSeats;
	private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
	
	protected double operationFee;
	public ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	
	
	public ArrayList<Passenger> getPassengers(){
		return this.passengers;
	}
	
	
	public double getFloorArea() {
		return this.floorArea - this.economySeats - 3*this.businessSeats - 8*this.firstClassSeats;
	}
	
	
	public boolean canReach(Airport airport) {
		if (this.getFuelCapacity() < this.getMaxFuelConsumption(this.currentAirport.getDistance(airport))) {
			return false;
		}
		return true;
	}
	
	
	
	public void setOperationCost(double operationCost) {
		this.operationFee = operationCost;
	}
	
	//TODO boolean methods for load, unload, transfer
	
	
	//Passenger.board() first, PassengerAircraft.loadPassenger() second
	//otherwise latestSeatType will return erroneous values
	public double loadPassenger(Passenger passenger) {
		double constant = 1;
		this.passengers.add(passenger);
		switch ((int)passenger.latestSeatType) {
		case 0: 
			this.occupiedEconomySeats++;
			constant = 1.2;
			break;
		case 1:
			this.occupiedBusinessSeats++;
			constant = 1.5;
			break;
		case 2:
			this.occupiedFirstClassSeats++;
			constant = 2.5;
			break;
		default: break;
		}
		this.weight += passenger.getWeight();
		double loadingFee = this.operationFee * this.aircraftTypeMultiplier * constant;
			
		return loadingFee;
	}
	


	public double unloadPassenger(Passenger passenger) {
		double constant = 1;
		this.passengers.remove(passenger);
		switch ((int)passenger.latestSeatType) {
		case 0: 
			this.occupiedEconomySeats--;
			constant = 1.0;
		case 1:
			this.occupiedBusinessSeats--;
			constant = 2.8;
		case 2:
			this.occupiedFirstClassSeats--;
			constant = 7.5;
		default: break;
		}
		this.weight -= passenger.getWeight();
		this.getCurrentAirportObj().passengers.add(passenger);
		double unloadFee = passenger.disembark(currentAirport, this.aircraftTypeMultiplier) * constant;
			
		return unloadFee;
	}
	
	
	
	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		double constant = 1;
		this.passengers.remove(passenger);
		toAircraft.passengers.add(passenger);
		switch ((int)passenger.latestSeatType) {
		case 0: 
			this.occupiedEconomySeats--;
			toAircraft.occupiedEconomySeats++;
			constant = 1.2;
		case 1:
			this.occupiedBusinessSeats--;
			toAircraft.occupiedBusinessSeats++;
			constant = 1.5;
		case 2:
			this.occupiedFirstClassSeats--;
			toAircraft.occupiedFirstClassSeats++;
			constant = 2.5;
		default: break;
			
		}
		//TODO check if fees are for this or toAircraft
		 double transferFee = this.operationFee * this.aircraftTypeMultiplier * constant;
			
		return transferFee;
	}
	
	protected static double getBathtubCurve(double distance) {
		double a = 25.9324;
		double b = 50.5633;
		double c = 35.0554;
		double d = 9.90346;
		double e = 1.97413;
		return a*Math.pow(distance, 4) - b*Math.pow(distance, 3) + c*Math.pow(distance, 2) - d*Math.pow(distance, 1) + e;
	}
	
	

	public double getAvailableWeight() {
		double weightSum = 0;
		for (Passenger i : this.passengers) {
			weightSum += i.getWeight();
		}
		return weightSum;
	}

	
	
	
	//seat operations
	public boolean setSeats(int economy, int business, int firstClass) {
		int occupiedFloor = economy + 3*business + 8*firstClass;
		if (occupiedFloor>floorArea)
			return false;
		else {
			this.economySeats = economy;
			this.businessSeats = business;
			this.firstClassSeats = firstClass;
			return true;
		}
	}
	public boolean setAllEconomy() {
		this.economySeats = (int)floorArea;
		return true;
	}
	public boolean setAllBusiness() {
		this.businessSeats = (int)floorArea/3;
		return true;
	}
	public boolean setAllFirstClass() {
		this.firstClassSeats = (int)floorArea/8;
		return true;
	}
	public boolean setRemainingEconomy() {
		int occupiedFloor = this.economySeats + 3*this.businessSeats + 8*this.firstClassSeats;
		this.economySeats += (int) floorArea - occupiedFloor;
		return true;
	}
	public boolean setRemainingBusiness() {
		int occupiedFloor = this.economySeats + 3*this.businessSeats + 8*this.firstClassSeats;
		this.businessSeats += ((int) floorArea - occupiedFloor)/3;
		return true;
	}
	public boolean setRemainingFirstClass() {
		int occupiedFloor = this.economySeats + 3*this.businessSeats + 8*this.firstClassSeats;
		this.firstClassSeats += ((int) floorArea - occupiedFloor)/8;
		return true;
	}
	
	//fullness operations
	public boolean isFull() {
		if(this.economySeats - this.occupiedEconomySeats + this.businessSeats - this.occupiedBusinessSeats + this.firstClassSeats - this.occupiedFirstClassSeats == 0)
			return true;
		else
			return false;
	}
	
	public boolean isFull(int seatType) {
		switch (seatType) {
		case 0: if (this.economySeats == this.occupiedEconomySeats)
					return true;
				else
					return false;
		case 1: if (this.businessSeats == this.occupiedBusinessSeats)
					return true;
				else
					return false;
		case 2: if (this.firstClassSeats == this.occupiedFirstClassSeats)
					return true;
				else
					return false;
		default: throw new IllegalArgumentException("Invalid seat type.");
		}
	}
	
	public boolean isEmpty() {
		int occupiedSeats = this.occupiedBusinessSeats + this.occupiedEconomySeats + this.occupiedFirstClassSeats;
		if (occupiedSeats == 0)
			return true;
		else
			return false;
	}
	
	public double getFullness() {
		int occupiedSeats = this.occupiedBusinessSeats + this.occupiedEconomySeats + this.occupiedFirstClassSeats;
		int allSeats = this.economySeats + this.businessSeats + this.firstClassSeats;
		return ((double) occupiedSeats)/allSeats;
	}
	
	public int getAvailableEconomy() {
		return economySeats - occupiedEconomySeats;
	}
	public int getAvailableBusiness() {
		return businessSeats - occupiedBusinessSeats;
	}
	public int getAvailableFirstClass() {
		return firstClassSeats - occupiedFirstClassSeats;
	}
	
	public double getAircraftMultiplier() {
		return this.aircraftTypeMultiplier;
	}
	
}
