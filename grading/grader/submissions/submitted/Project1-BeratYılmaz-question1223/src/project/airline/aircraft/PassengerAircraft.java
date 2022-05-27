package project.airline.aircraft;

import java.util.HashMap;

import project.airport.Airport;
import project.interfaces.PassengerInterface;
import project.passenger.Passenger;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {
	
	public PassengerAircraft(Airport initAirport, double operationFee) {
		super(initAirport, operationFee);
		// TODO Auto-generated constructor stub
	}

	private HashMap<Long, Passenger> passengerList = new HashMap<Long, Passenger>(); //diff collection??
	
	protected double floorArea;
	
	private int economySeats;
	private int businessSeats;
	private int firstClassSeats;
	
	private int occupiedEconomySeats;
	private int occupiedBusinessSeats;
	private int occupiedFirstClassSeats;
	
	private int economyArea = 1;
	private int businessArea = 3;
	private int firstClassArea = 8;

	protected double aircraftTypeMultiplier;
	
	

	public double loadPassenger(Passenger passenger) { 
		double seatConstant = 0;
		if (isLoadValid(passenger)) { 
			switch (assignSeat(passenger)) {
			case 0:
				occupiedEconomySeats += 1;
				seatConstant = 1.2; 
				passenger.board(0);
				break;
			case 1:
				occupiedBusinessSeats += 1;
				seatConstant = 1.5;
				passenger.board(1);
				break;
			case 2:
				occupiedFirstClassSeats += 1;
				seatConstant = 2.5;
				passenger.board(2);
				break;
			}
			this.weight += passenger.getWeight();
			this.passengerList.put(passenger.getID(), passenger); 
			double loadingFee = this.operationFee * this.aircraftTypeMultiplier * seatConstant;
			//this.getCurrentAirport().removePassenger(passenger);
			return loadingFee;
		}
		return this.operationFee;
	}
	
	public boolean isLoadValid(Passenger passenger) {
		if (passenger.getWeight() > getAvailableWeight()) {
			return false;
		}
		switch(passenger.getPassengerType()) {
		case 0:
			if (occupiedEconomySeats < economySeats) {
				return true;
			}
			else {
				return false;
			}
		case(1):
			if (occupiedBusinessSeats < businessSeats || occupiedEconomySeats < economySeats) {
				return true;
			}
			else {
				return false;
			}
		case(2):
			if (occupiedFirstClassSeats < firstClassSeats || occupiedBusinessSeats < businessSeats || occupiedEconomySeats < economySeats) {
				return true;
			}
			else {
				return false;
			}
		}
		return false; 
	}
	
	public int assignSeat(Passenger passenger) {
		switch(passenger.getPassengerType()) {
		case 0:
			return 0;
		case 1: 
			if (!isFull(1)) {
				return 1;
			}
			else {
				return 0;
			}
		case 2:
			if (!isFull(2)) {
				return 2;
			}
			else if (!isFull(1)) {
				return 1;
			}
			else;
				return 0;
		case 3:
			if (!isFull(2)) {
				return 2;
			}
			else if (!isFull(1)) {
				return 1;
			}
			else;
				return 0;
		}
		return 0;
	}
	
	public HashMap<Long, Passenger> getPassengerMap() {
		return passengerList;
	}
	
	public double getFullness() {
		int totalOccupiedSeats = occupiedEconomySeats + occupiedBusinessSeats + occupiedFirstClassSeats;
		int totalSeats = economySeats + businessSeats + firstClassSeats;
		double seatRatio = (double) totalOccupiedSeats / (double) totalSeats;
		return seatRatio;
	}
	
	//transfer passenger object from aircraft to airport
	public double unloadPassenger(Passenger passenger) {
		double seatConstant = 0;
		if (passenger.canDisembark(this.currentAirport)) {
			switch (passenger.getCurrentSeatType()) {
			case 0:
				occupiedEconomySeats -= 1;
				seatConstant = 1.0;
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
		this.weight -= passenger.getWeight();
		double disembarkationRevenue = passenger.disembark(this.currentAirport, this.aircraftTypeMultiplier);
		this.passengerList.remove(passenger.getID(), passenger);
		return disembarkationRevenue * seatConstant;
		}
		return this.operationFee;
	}
	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		double seatConstant = 0;
		if (toAircraft.isLoadValid(passenger)) {
			this.passengerList.remove(passenger.getID(), passenger);
			switch (toAircraft.assignSeat(passenger)) {
			case 0:
				toAircraft.incrementOccupiedEconomy();
				seatConstant = 1.2; 
				passenger.connection(0);
				break;
			case 1:
				toAircraft.incrementOccupiedBusiness();
				seatConstant = 1.5;
				passenger.connection(1);
				break;
			case 2:
				toAircraft.incrementOccupiedFirstClass();
				seatConstant = 2.5;
				passenger.connection(2);
				break;
			}
			this.weight -= passenger.getWeight();
			toAircraft.pushToPassengerList(passenger);
			double loadingFee = toAircraft.getOperationFee() * toAircraft.getAircraftTypeMultiplier() * seatConstant;
			return loadingFee;
		}
		return toAircraft.getOperationFee();
	}
	
	public boolean isFull(int seatType) {
		boolean resBool;
		switch(seatType) {
		case 0:
			resBool = occupiedEconomySeats >= economySeats; //Sanity check >=
			return resBool;
		case 1:
			resBool = occupiedBusinessSeats >= businessSeats;
			return resBool;
		case 2:
			resBool = occupiedFirstClassSeats >= firstClassSeats;
			return resBool;
		}
		return false;
	}
	
	public boolean isFull() {
		int totalSeats = economySeats + businessSeats + firstClassSeats;
		int occupiedTotalSeats = occupiedEconomySeats + occupiedBusinessSeats + occupiedFirstClassSeats;
		if (occupiedTotalSeats >= totalSeats) {
			return true;
		}
		else {
		return false;
	}

}
	public boolean isEmpty() {
		int occupiedTotalSeats = occupiedEconomySeats + occupiedBusinessSeats + occupiedFirstClassSeats;
		return occupiedTotalSeats == 0;
	}
	
	public double getAvailableWeight() {
		return this.maxWeight - this.weight;
	}
	
	public double getRemainingArea() {
		double occupiedArea = economySeats * economyArea + businessSeats * businessArea + firstClassSeats * firstClassArea;
		double remainingArea = floorArea - occupiedArea;
		return remainingArea;
	}
	
	public boolean setSeats(int economy, int business, int firstClass) {
		double plannedArea = economy * economyArea + business * businessArea + firstClass * firstClassArea;
		if (plannedArea <= this.getRemainingArea()) {
			economySeats += economy;
			businessSeats += business;
			firstClassSeats += firstClass;
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean setAllEconomy() {
		if (this.isEmpty()) {
			this.businessSeats = 0;
			this.firstClassSeats = 0;
			int seats = (int) floorArea / economyArea;
			economySeats = seats;
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean setAllBusiness() {
		if (this.isEmpty()) {
			this.economySeats = 0;
			this.firstClassSeats = 0;
			int seats = (int) floorArea / businessArea;
			businessSeats = seats;
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean setAllFirstClass() {
		if (this.isEmpty()) {
			this.economySeats = 0;
			this.businessSeats = 0;
			int seats = (int) floorArea / firstClassArea;
			firstClassSeats = seats;
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean setRemainingEconomy() {
		double remainingArea = this.getRemainingArea();
		double seatsDouble = remainingArea / economyArea;
		int settedSeats = (int) Math.floor(seatsDouble);
		economySeats += settedSeats;
		return true;
	}
	
	public boolean setRemainingBusiness() {
		double remainingArea = this.getRemainingArea();
		double seatsDouble = remainingArea / businessArea;
		int settedSeats = (int) Math.floor(seatsDouble);
		businessSeats += settedSeats;
		return true;
	}
	
	public boolean setRemainingFirstClass() {
		double remainingArea = this.getRemainingArea();
		double seatsDouble = remainingArea / firstClassArea;
		int settedSeats = (int) Math.floor(seatsDouble);
		firstClassSeats += settedSeats;
		return true;
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
	
	public void incrementOccupiedEconomy() {
		this.occupiedEconomySeats++;
	}
	
	public void incrementOccupiedBusiness() {
		this.occupiedBusinessSeats++;
	}
	public void incrementOccupiedFirstClass() {
		this.occupiedFirstClassSeats++;
	}
	
	public double getOperationFee() {
		return this.operationFee;
	}
	
	public double getAircraftTypeMultiplier() {
		return this.aircraftTypeMultiplier;
	}
	
	public void pushToPassengerList(Passenger passenger) {
		passengerList.put(passenger.getID(), passenger);
	}

	
}
