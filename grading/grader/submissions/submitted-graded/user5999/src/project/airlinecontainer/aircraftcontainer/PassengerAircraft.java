package project.airlinecontainer.aircraftcontainer;


import project.airportcontainer.Airport;
import project.interfacescontainer.PassengerInterface;
import project.passengercontainer.BusinessPassenger;
import project.passengercontainer.EconomyPassenger;
import project.passengercontainer.Passenger;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {
	public PassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport, operationFee);
	}
	protected double floorArea;
	protected double usedFloor = 0;
	private int economySeats, businessSeats, firstClassSeats = 0;
	private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats = 0;
	
	public boolean canLoad(Passenger passenger) {
		if (this.loadPassenger(passenger) == super.operationFee && this.getAvailableWeight() >= passenger.getWeight()) {
			this.unloadPassenger(passenger);
			return false;
		}
		else {
			this.unloadPassenger(passenger);
			return true;
		}
	}
	
	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		if (toAircraft.canLoad(passenger)) {
			if (passenger.getSeatConstant() == 1.0) {
				this.occupiedEconomySeats-=1;
				super.weight-=passenger.getWeight();
			}
			if (passenger.getSeatConstant() == 2.8) {
				this.occupiedBusinessSeats-=1;
				super.weight-=passenger.getWeight();		
			}
			else {
				this.occupiedFirstClassSeats-=1;
				super.weight-=passenger.getWeight();
			}
			return toAircraft.loadPassenger(passenger);
		}
		else return super.operationFee;
	}
	

	public double loadPassenger(Passenger passenger) {
		if (passenger instanceof EconomyPassenger) {
			if (this.occupiedEconomySeats < economySeats) {
				this.occupiedEconomySeats+=1;
				passenger.setSeatConstant(1.0);
				passenger.board(0);
				super.weight+=passenger.getWeight();
				return 1.2*super.operationFee*super.aircraftTypeMultiplier;
			}
			else return super.operationFee;
		}
		else if (passenger instanceof BusinessPassenger) {
			if (this.occupiedBusinessSeats < this.businessSeats) {
				this.occupiedBusinessSeats+=1;
				passenger.setSeatConstant(2.8);
				passenger.board(1);
				super.weight+=passenger.getWeight();
				return 1.5*super.operationFee*super.aircraftTypeMultiplier;
			}
			else if (this.occupiedEconomySeats < this.economySeats) {
				this.occupiedEconomySeats+=1;
				passenger.setSeatConstant(1.0);
				passenger.board(0);
				super.weight+=passenger.getWeight();
				return 1.2*super.operationFee*super.aircraftTypeMultiplier;
			}
			else return super.operationFee;
		}
		else {
			if (this.occupiedFirstClassSeats < this.firstClassSeats) {
				this.occupiedFirstClassSeats += 1;
				passenger.setSeatConstant(7.5);
				passenger.board(2);
				super.weight+=passenger.getWeight();
				return 2.5*super.operationFee*super.aircraftTypeMultiplier;
			}
			else if (this.occupiedBusinessSeats < this.businessSeats) {
				this.occupiedBusinessSeats+=1;
				passenger.setSeatConstant(2.8);
				passenger.board(1);
				super.weight+=passenger.getWeight();
				return 1.5*super.operationFee*super.aircraftTypeMultiplier;
			}
			else if (this.occupiedEconomySeats < this.economySeats) {
				this.occupiedEconomySeats+=1;
				passenger.setSeatConstant(1.0);
				passenger.board(0);
				super.weight+=passenger.getWeight();
				return 1.2*super.operationFee*super.aircraftTypeMultiplier;
			}
			else return super.operationFee; 
		}
	}
	
		
	public double unloadPassenger(Passenger passenger) {
		if (passenger.getSeatConstant() == 1.0) {
			if (passenger.disembark(currentAirport, super.aircraftTypeMultiplier) != 0) {
				this.occupiedEconomySeats-=1;
				super.weight-=passenger.getWeight();
				return passenger.getSeatConstant()*passenger.disembark(currentAirport, 0);
			}
			else return super.operationFee;
		}
		if (passenger.getSeatConstant() == 2.8) {
			if (passenger.disembark(currentAirport, super.aircraftTypeMultiplier) != 0) {
				this.occupiedBusinessSeats-=1;
				super.weight-=passenger.getWeight();
				return passenger.getSeatConstant()*passenger.disembark(currentAirport, 0);
			}
			else return super.operationFee;				
		}
		else {
			if (passenger.disembark(currentAirport, super.aircraftTypeMultiplier) != 0) {
				this.occupiedFirstClassSeats-=1;
				super.weight-=passenger.getWeight();
				return passenger.getSeatConstant()*passenger.disembark(currentAirport, 0);
			}
			else return super.operationFee;	
		}
	}
		
	public boolean isFull() {
		if (this.occupiedBusinessSeats == this.businessSeats && this.occupiedEconomySeats == this.economySeats && this.occupiedFirstClassSeats == this.firstClassSeats) return true;
		else return false;
	}
	public boolean isFull(int seatType) {
		if (seatType == 0) {
			if (this.occupiedEconomySeats == this.economySeats) return true;
			else return false;
		}
		else if (seatType == 1) {
			if (this.occupiedBusinessSeats == this.businessSeats) return true;
			else return false;
		}
		else {
			if (this.occupiedFirstClassSeats == this.firstClassSeats) return true;
			else return false;
		}
	}
	public boolean isEmpty() {
		if (occupiedBusinessSeats+occupiedEconomySeats+occupiedFirstClassSeats == 0) return true;
		else return false;
	}
	public double getAvailableWeight() {
		return this.maxWeight-this.weight;
			
	}
	public boolean setSeats(int economy, int business, int firstClass) {
		if (economy+business*3+firstClass*8 < this.floorArea) {
			this.economySeats = economy;
			this.businessSeats = business;
			this.firstClassSeats = firstClass;
			this.usedFloor = economy+business*3+firstClass*8;
			return true;
		}
		else return false;
			
	}
	public boolean setAllEconomy() {
		int seats = (int) this.floorArea/1;
		this.economySeats = seats;
		this.businessSeats = 0;
		this.firstClassSeats = 0;
		this.usedFloor = seats;
		return true;
	}
	public boolean setAllBusiness() {
		int seats = (int) this.floorArea/3;
		this.economySeats = 0;
		this.businessSeats = seats;
		this.firstClassSeats = 0;
		this.usedFloor = seats*3;
		return true;
	}
	public boolean setAllFirstClass() {
		int seats = (int) this.floorArea/8;
		this.economySeats = 0;
		this.businessSeats = 0;
		this.firstClassSeats = seats;
		this.usedFloor = seats*8;
		return true;
	}
	public boolean setRemainingEconomy() {
		int seats = (int) (this.floorArea - this.usedFloor)/1;
		this.economySeats += seats;
		return true;
	}
	public boolean setRemainingBusiness() {
		int seats = (int) (this.floorArea - this.usedFloor)/3;
		this.businessSeats += seats;
		return true;
	}
	public boolean setRemainingFirstClass() {
		int seats = (int) (this.floorArea - this.usedFloor)/8;
		this.firstClassSeats += seats;
		return true;
	}
	public double getFullness() {
		return (double) ((occupiedBusinessSeats+occupiedEconomySeats+occupiedFirstClassSeats)/(economySeats+businessSeats+firstClassSeats));
	}
}
