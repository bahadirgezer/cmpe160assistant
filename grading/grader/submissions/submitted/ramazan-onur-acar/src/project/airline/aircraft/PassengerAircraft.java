package project.airline.aircraft;
import java.util.ArrayList;

import project.airport.Airport;
import project.interfaces.*;
import project.passenger.BusinessPassenger;
import project.passenger.EconomyPassenger;
import project.passenger.FirstClassPassenger;
import project.passenger.LuxuryPassenger;
import project.passenger.Passenger;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface{
	public ArrayList <Passenger> passengers_airplane;
	protected double floorArea;
	private int economySeats, businessSeats,firstClassSeats;
	private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
	protected double aircraftTypeMultiplier;
	
	
	public PassengerAircraft(Airport located_airport, double operationFee) {
		super(located_airport,operationFee);
	}

	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		
		passengers_airplane.remove(passenger);
		toAircraft.passengers_airplane.add(passenger);
		double constant =1;
		if (passenger instanceof EconomyPassenger) {
			if (isFull(1) == false) { 
				occupiedEconomySeats += 1;
				constant = 1.2;
			}
		}
		else if(passenger instanceof BusinessPassenger) {
			if (isFull(2) == false) { 
				occupiedBusinessSeats += 1;
				constant = 1.5;
			}
			else if (isFull(1) == false) { 
				occupiedEconomySeats += 1;
				constant = 1.2;
			}
		}
		else if(passenger instanceof FirstClassPassenger || passenger instanceof LuxuryPassenger) {
			if (isFull(3) == false) {
				occupiedFirstClassSeats += 1;
				constant = 2.5;
			}
			else if (isFull(2) == false) { 
				occupiedBusinessSeats += 1;
				constant = 1.5;
			}
			else if (isFull(1) == false) { 
				occupiedEconomySeats += 1;
				constant = 1.2;
			}
		}
		else {
			return operationFee;
		}
		
		double loading_fee = operationFee * aircraftTypeMultiplier * constant;
		return loading_fee;
	}

	public double loadPassenger(Passenger passenger) {
		double constant =1;
		if (passenger instanceof EconomyPassenger) {
			if (isFull(1) == false) { 
				occupiedEconomySeats += 1;
				constant = 1.2;
			}
		}
		else if(passenger instanceof BusinessPassenger) {
			if (isFull(2) == false) { 
				occupiedBusinessSeats += 1;
				constant = 1.5;
			}
			else if (isFull(1) == false) { 
				occupiedEconomySeats += 1;
				constant = 1.2;
			}
		}
		else if(passenger instanceof FirstClassPassenger || passenger instanceof LuxuryPassenger) {
			if (isFull(3) == false) {
				occupiedFirstClassSeats += 1;
				constant = 2.5;
			}
			else if (isFull(2) == false) { 
				occupiedBusinessSeats += 1;
				constant = 1.5;
			}
			else if (isFull(1) == false) { 
				occupiedEconomySeats += 1;
				constant = 1.2;
			}
		}
		else {
			return operationFee;
		}
		
		double loading_fee = operationFee * aircraftTypeMultiplier * constant;
		passengers_airplane.add(passenger);
		return loading_fee;
	}
	

	public double unloadPassenger(Passenger passenger) {
		double para = passenger.disembark(located_airport, aircraftTypeMultiplier);
		passengers_airplane.remove(passenger);
		double bcd = 0;
		if (para == 0) {
			bcd = operationFee * (-1);
		}
		else {
			if (passenger.saved_seat_type == 1) {
				bcd = para;
			}
			else if (passenger.saved_seat_type== 2) {
				bcd = para*2.8;
			}
			else if (passenger.saved_seat_type ==3) {
				bcd = para*7.5;
			}
		}
		
		return bcd;

	}

	public boolean isFull() {
		if (getFullness() == 1) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isFull(int seatType) {
		if (seatType == 1) {
			if (occupiedEconomySeats == economySeats) {
				return true;
			}
		}
		else if (seatType == 2) {
			if (occupiedBusinessSeats == businessSeats) {
				return true;
			}
		}
		else {
			if (occupiedFirstClassSeats == firstClassSeats) {
				return true;
			}
		}
		return false;
			
	}

	public boolean isEmpty() {
		if (passengers_airplane.size()==0) {
			return true;
		}
		else {
			return false;
		}
	}

	public double getAvailableWeight() {
		double klm = max_weight - weight;
		return klm;
	}

	public boolean setSeats(int economy, int business, int firstClass) {
		if (economy*1 + business*3 + firstClass*8 > floorArea) {
			return false;
		}
		else {
			economySeats = economy;
			businessSeats = business;
			firstClassSeats = firstClass;
			return true;
		}
	}

	public boolean setAllEconomy() {
		economySeats = (int) (floorArea/1);
		businessSeats = 0;
		firstClassSeats = 0;
		
		return true;
	}


	public boolean setAllBusiness() {
		businessSeats = (int) (floorArea /3);
		firstClassSeats = 0;
		economySeats = 0;
		return true;
	}

	public boolean setAllFirstClass() {
		firstClassSeats = (int) (floorArea / 8);
		economySeats = 0;
		firstClassSeats = 0;
		return true;
	}

	public boolean setRemainingEconomy() {
		if (floorArea-economySeats*1-businessSeats*3-firstClassSeats*8 > 0) {
			economySeats += (int) ((floorArea-economySeats*1-businessSeats*3-firstClassSeats*8)/1);
			return true;
		}
		else {
			return false;
		}
	}

	public boolean setRemainingBusiness() {
		if (floorArea-economySeats*1-businessSeats*3-firstClassSeats*8 > 0) {
			businessSeats += (floorArea-economySeats*1-businessSeats*3-firstClassSeats*8)/3;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean setRemainingFirstClass() {
		if (floorArea-economySeats*1-businessSeats*3-firstClassSeats*8 > 0) {
			firstClassSeats += (floorArea-economySeats*1-businessSeats*3-firstClassSeats*8);
			return true;
		}
		else {
			return false;
		}
	}

	public double getFullness() {
		if (economySeats + firstClassSeats + businessSeats == 0) {
			return 0;
		}
		double fullness = passengers_airplane.size() / (economySeats + firstClassSeats + businessSeats);
		return fullness;
	}
	
	public int getEconomySeats() {
		return economySeats;
	}

	
	
}
