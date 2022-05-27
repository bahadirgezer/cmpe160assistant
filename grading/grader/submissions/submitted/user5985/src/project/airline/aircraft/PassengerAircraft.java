package project.airline.aircraft;

import java.util.ArrayList;

import project.airport.Airport;
import project.interfaces.PassengerInterface;
import project.passenger.Passenger;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface{
	protected ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	protected double aircraftTypeMultiplier;
	protected double floorArea;
	private int economySeats, businessSeats, firstClassSeats;
	private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
	protected int ID;
	
	public PassengerAircraft(Airport currentAirport, double operationalCost) {
		super(currentAirport, operationalCost);
	}
	public double getFullness() {
		return (occupiedBusinessSeats + occupiedEconomySeats + occupiedFirstClassSeats)/
				(double)(economySeats + businessSeats + firstClassSeats);
	}
	
	public ArrayList<Passenger> getPassengers() {
		return this.passengers;
	}
	
	public double getAircraftTypeMultiplier() {
		return this.aircraftTypeMultiplier;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
		
	public double loadPassenger(Passenger passenger) {
		passenger.getCurrentAirport().removePassenger(passenger);
		this.passengers.add(passenger);
		this.weight += passenger.getWeight();
		double constant;
		if (passenger.getPassengerType() == 0) {
			// economy passenger
			if (this.occupiedEconomySeats < this.economySeats) {
				constant = 1.2; // seat type = 0
				passenger.setSeatType(0);
				this.occupiedEconomySeats++;
			}
			else
				return this.operationalCost;	
		}
		
		else if (passenger.getPassengerType() == 1) {
			// business passenger
			if (this.occupiedBusinessSeats < this.businessSeats) {
				constant = 1.5;
				passenger.setSeatType(1);
				this.occupiedBusinessSeats++;
			}
			else if (this.occupiedEconomySeats < this.economySeats) {
				constant = 1.2;
				this.occupiedEconomySeats++;
				passenger.setSeatType(0);
			}
			else
				return this.operationalCost;
		}
		
		else if (passenger.getPassengerType() == 2) {
			// first class passenger
			if (this.occupiedFirstClassSeats < this.firstClassSeats) {
				constant = 2.5;
				this.occupiedFirstClassSeats++;
				passenger.setSeatType(2);
			}
			else if (this.occupiedBusinessSeats < this.businessSeats) {
				constant = 1.5;
				this.occupiedBusinessSeats++;
				passenger.setSeatType(1);
			}
			else if (this.occupiedEconomySeats < this.economySeats) {
				constant = 1.2;
				this.occupiedEconomySeats++;
				passenger.setSeatType(0);
			}

			else{
				return this.operationalCost;
			}
		}
		
		else {
			// luxury passenger
			if (this.occupiedFirstClassSeats < this.firstClassSeats) {
				constant = 2.5;
				this.occupiedFirstClassSeats++;
				passenger.setSeatType(2);
			}
			else if (this.occupiedBusinessSeats < this.businessSeats) {
				constant = 1.5;
				this.occupiedBusinessSeats++;
				passenger.setSeatType(1);
			}
			else if (this.occupiedEconomySeats < this.economySeats) {
				constant = 1.2;
				this.occupiedEconomySeats++;
				passenger.setSeatType(0);
			}

			else{
				return this.operationalCost;
			}
		}
		passenger.board(passenger.getSeatType());
		double loadingFee = this.operationalCost * aircraftTypeMultiplier * constant;
		return loadingFee;
	}
	
	public double unloadPassenger(Passenger passenger) {
		this.weight -= passenger.getWeight();
		if (passenger.canDisembark(passenger.getCurrentAirport())) {
			this.currentAirport.addPassenger(passenger);
			
			double seatConstant;
			if (passenger.getSeatType() == 0) // economy seat
				seatConstant = 1.0;
			else if (passenger.getSeatType() == 1)
				seatConstant = 2.8;
			else
				seatConstant = 7.5;
			passenger.board(passenger.getSeatType());
			return passenger.disembark(currentAirport, this.aircraftTypeMultiplier) * seatConstant; //passenger.getSeatMultiplier();
		}
		else {
			return operationalCost * -1;
		}
	}
	
	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		if (passenger.canDisembark(currentAirport) == false && toAircraft.isFull() == false) {
			this.passengers.remove(passenger);
			toAircraft.passengers.add(passenger);
			double seatConstant;
			if (passenger.getSeatType() == 0) // economy seat
				seatConstant = 1.0;
			else if (passenger.getSeatType() == 1)
				seatConstant = 2.8;
			else
				seatConstant = 7.5;
			System.out.println("6 " + passenger.getPassengerID() + " " + this.getID() + " " + toAircraft.getID() + " " + this.currentAirport.getID());
			return passenger.disembark(currentAirport, seatConstant) * seatConstant;	
		}
		else
			return operationalCost;
	}



	public boolean isFull() {
		return (this.occupiedBusinessSeats + this.occupiedEconomySeats + this.occupiedFirstClassSeats) 
				== (this.businessSeats + this.economySeats + this.firstClassSeats) ? true : false;
	}
	
	public boolean isFull(int seatType) {
		if (seatType == 0)
			return this.economySeats == this.occupiedEconomySeats ? true : false;
		else if (seatType == 1)
			return this.businessSeats == this.occupiedBusinessSeats ? true : false;
		else
			return this.firstClassSeats == this.occupiedFirstClassSeats ? true : false;
	}
	
	public boolean isEmpty() {
		if (passengers.size() == 0)
			return true;
		return false;
	}
	
	public double getAvailableWeight() {
		return this.maxWeight - this.weight;
	}
	
	public boolean setSeats(int economy, int business, int firstClass) {
		if (economy + business * 3 + firstClass * 8 <= floorArea) {
			this.firstClassSeats = firstClass;
			this.businessSeats = business;
			this.economySeats = economy;
			return true;
		}
		return false;
	}
	
	public boolean setAllEconomy() {
		this.economySeats = (int)this.floorArea / 1;
		this.businessSeats = 0;
		this.firstClassSeats = 0;
		return true;
	}
	
	public boolean setAllBusiness() {
		this.economySeats = 0;
		this.businessSeats = (int)this.floorArea / 3;
		this.firstClassSeats = 0;
		return true;
	}
	
	public boolean setAllFirstClass() {
		this.economySeats = 0;
		this.businessSeats = 0;
		this.firstClassSeats = (int)this.floorArea / 8;
		return true;
	}	
	
	public boolean setRemainingEconomy() {
		int remaining = (int) (this.floorArea - this.economySeats - 3 * this.businessSeats - 8 * this.businessSeats);
		this.setSeats(this.economySeats + remaining, this.businessSeats, this.firstClassSeats);
		return true;
	}
	public boolean setRemainingBusiness() {
		int remaining = (int) (this.floorArea - this.economySeats - 3 * this.businessSeats - 8 * this.businessSeats);
		this.setSeats(this.economySeats, this.businessSeats + remaining / 3, this.firstClassSeats);
		return true;
	}
	public boolean setRemainingFirstClass() {
		int remaining = (int) (this.floorArea - this.economySeats - 3 * this.businessSeats - 8 * this.businessSeats);
		this.setSeats(this.economySeats, this.businessSeats, this.firstClassSeats + remaining / 8);
		return true;
	}	
}
