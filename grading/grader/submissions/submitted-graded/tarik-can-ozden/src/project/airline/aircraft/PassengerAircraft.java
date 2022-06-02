package project.airline.aircraft;
import java.util.ArrayList;

import project.airport.Airport;
import project.interfaces.AircraftInterface;
import project.interfaces.PassengerInterface;
import project.passenger.Passenger;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {
	private int economySeats, businessSeats, firstClassSeats;
	private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
	protected double floorArea;
	protected double fuelCapacity;
	protected double fuelConsumption;
	protected double aircraftTypeMultiplier;
	protected ArrayList <Passenger> passengersList;
	protected double operationFee;
	public PassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport, operationFee);
		this.passengersList = new ArrayList<Passenger>();
		this.operationFee = operationFee;
	}

	
	public double getOperationFee() {
		return operationFee;
	}


	public void setOperationFee(double operationFee) {
		this.operationFee = operationFee;
	}


	public int getEconomySeats() {
		return economySeats;
	}
	
	public void setEconomySeats(int economySeats) {
		this.economySeats = economySeats;
	}
	public int getBusinessSeats() {
		return businessSeats;
	}
	public ArrayList<Passenger> getPassengersList() {
		return passengersList;
	}
	public void setPassengersList(ArrayList<Passenger> passengersList) {
		this.passengersList = passengersList;
	}
	public void setBusinessSeats(int businessSeats) {
		this.businessSeats = businessSeats;
	}
	public int getFirstClassSeats() {
		return firstClassSeats;
	}
	public void setFirstClassSeats(int firstClassSeats) {
		this.firstClassSeats = firstClassSeats;
	}
	public int getOccupiedEconomySeats() {
		return occupiedEconomySeats;
	}
	public void setOccupiedEconomySeats(int occupiedEconomySeats) {
		this.occupiedEconomySeats = occupiedEconomySeats;
	}
	public int getOccupiedBusinessSeats() {
		return occupiedBusinessSeats;
	}
	public void setOccupiedBusinessSeats(int occupiedBusinessSeats) {
		this.occupiedBusinessSeats = occupiedBusinessSeats;
	}
	public int getOccupiedFirstClassSeats() {
		return occupiedFirstClassSeats;
	}
	public void setOccupiedFirstClassSeats(int occupiedFirstClassSeats) {
		this.occupiedFirstClassSeats = occupiedFirstClassSeats;
	}
	public double getFloorArea() {
		return floorArea;
	}
	public void setFloorArea(double floorArea) {
		this.floorArea = floorArea;
	}
	public double getFuelCapacity() {
		return fuelCapacity;
	}
	public void setFuelCapacity(double fuelCapacity) {
		this.fuelCapacity = fuelCapacity;
	}
	public double getFuelConsumption() {
		return fuelConsumption;
	}
	public void setFuelConsumption(double fuelConsumption) {
		this.fuelConsumption = fuelConsumption;
	}
	public double getAircraftTypeMultiplier() {
		return aircraftTypeMultiplier;
	}
	public void setAircraftTypeMultiplier(double aircraftTypeMultiplier) {
		this.aircraftTypeMultiplier = aircraftTypeMultiplier;
	}
	
	public double loadPassenger(Passenger passenger) {
		//if(this.isFull(passenger.getPassengerType())) {
		if(this.isFull()) {
			return this.getOperationFee();
		} else {
			this.passengersList.add(passenger);
			double loadingFee = this.getOperationFee();
			this.setWeight(this.getWeight() + passenger.getWeight());
			this.setOccupiedEconomySeats(getOccupiedEconomySeats()+1);
			passenger.setIsLoaded(1);
			/*if(passenger.getPassengerType() == 1)*/ loadingFee *= 1.2;
			//else if (passenger.getPassengerType() == 2)loadingFee *= 1.5;
			//else loadingFee *= 2.5;
			return loadingFee * this.getAircraftTypeMultiplier();
			
		}
	}
	
	
	public double unloadPassenger(Passenger passenger) {
		if(passenger.disembarkCheck(this.getCurrentAirport())) {
			this.passengersList.remove(passenger);
			double seatConstant = 1;
			/*
			if(passenger.getPassengerType() == 1) seatConstant *= 1.0;
			else if (passenger.getPassengerType() == 2) seatConstant *= 2.8;
			else seatConstant *= 7.5;
			*/
			this.setOccupiedEconomySeats(getOccupiedEconomySeats()-1);
			this.setWeight(this.getWeight() - passenger.getWeight());
			passenger.setIsLoaded(0);
			return seatConstant * passenger.disembark(currentAirport, aircraftTypeMultiplier);
		} else {
			return this.getCurrentAirport().getOperationFee();
		}
	}
	
	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		double operationFee = this.getCurrentAirport().getOperationFee();
		if(toAircraft.isFull(passenger.getPassengerType())) {
			return operationFee;
		} else {
			this.passengersList.remove(passenger);
			this.setWeight(this.getWeight() - passenger.getWeight());
			toAircraft.passengersList.add(passenger);
			toAircraft.setWeight(toAircraft.getWeight() + passenger.getWeight());
			double loadingFee = operationFee;
			/*
			if(passenger.getPassengerType() == 1) loadingFee *= 1.2;
			else if (passenger.getPassengerType() == 2)loadingFee *= 1.5;
			else loadingFee *= 2.5;
			*/
			return loadingFee;
		}
	}
	
	public boolean isFull() {
		if(this.getEconomySeats() == this.getOccupiedEconomySeats() 
				&& this.getFirstClassSeats() == this.getOccupiedFirstClassSeats() 
				&& this.getBusinessSeats() == this.getOccupiedBusinessSeats()) {
			return true;
		} else return false;
	}
	
	public boolean isFull(int seatType) {
		if(seatType == 1) {
			return (this.getEconomySeats() == this.getOccupiedEconomySeats());
		} else if (seatType == 2) {
			return (this.getBusinessSeats() == this.getOccupiedBusinessSeats());
		} else {
			return (this.getFirstClassSeats() == this.getOccupiedFirstClassSeats());
		}
	}
	
	public boolean isEmpty() {
		if(this.passengersList.size() == 0) return true;
		else return false;
	}
	public double getAvailableWeight() {
		return this.getMaxWeight() - this.getWeight();
	}
	public boolean setSeats(int economy, int business, int firstClass) {
		this.setEconomySeats(economy);
		this.setBusinessSeats(business);
		this.setFirstClassSeats(firstClass);
		return true;
	}
	public boolean setAllEconomy() {
		if(this.isEmpty()) {
			this.setEconomySeats((int)this.getFloorArea());
			return true;
		} else return false;
	}
	public boolean setAllBusiness() {
		if(this.isEmpty()) {
			this.setBusinessSeats((int)(this.getFloorArea() / 3));
			return true;
		} else return false;
	}
	public boolean setAllFirstClass() {
		if(this.isEmpty()) {
			this.setFirstClassSeats((int)(this.getFloorArea() / 8));
			return true;
		} else return false;
	}
	public boolean setRemainingEconomy() {
		int setFloor = this.getEconomySeats() + this.getBusinessSeats() * 3 + this.getFirstClassSeats() * 8;
		this.setEconomySeats(this.getEconomySeats() + (int)this.getFloorArea() - setFloor);
		return true;
	}
	public boolean setRemainingBusiness() {
		int setFloor = this.getEconomySeats() + this.getBusinessSeats() * 3 + this.getFirstClassSeats() * 8;
		this.setBusinessSeats(this.getBusinessSeats() + ((int)this.getFloorArea() - setFloor)/3);
		return true;
	}
	public boolean setRemainingFirstClass() {
		int setFloor = this.getEconomySeats() + this.getBusinessSeats() * 3 + this.getFirstClassSeats() * 8;
		this.setBusinessSeats(this.getFirstClassSeats() + ((int)this.getFloorArea() - setFloor)/8);
		return true;
	}
	
	public double getFullness() {
		return ( (double)(this.getOccupiedEconomySeats() + this.getOccupiedBusinessSeats() + this.getOccupiedFirstClassSeats()) / (this.getEconomySeats() + this.getBusinessSeats() + this.getFirstClassSeats()));
	}
	
	
}
