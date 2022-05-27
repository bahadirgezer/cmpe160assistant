package project.airlinecontainer.aircraftcontainer;

import project.airportcontainer.Airport;
import project.interfacescontainer.PassengerInterface;
import project.passengercontainer.Passenger;
import java.util.ArrayList;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface{

	protected double floorArea;
	private int economySeats;
	private int businessSeats;
	private int firstClassSeats;
	private int occupiedEconomySeats;
	private int occupiedBusinessSeats;
	private int occupiedFirstClassSeats;
	ArrayList<Passenger> passengersAtAircraft = new ArrayList<Passenger>();
	
	public PassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport, operationFee);
	}
	
	public ArrayList<Passenger> getPassengersAtAircraft() {
		return passengersAtAircraft;
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
	
	int whichSeat(int classNumber) {
		if (classNumber == 2 || classNumber == 3) {
			if (firstClassSeats - occupiedFirstClassSeats > 0) {
				return 2;
			} if (businessSeats - occupiedBusinessSeats > 0) {
				return 1;
			} else if (economySeats - occupiedEconomySeats > 0) {
				return 0;
			} else {
				return -1;
			}
		} else if (classNumber == 1) {
			if (businessSeats - occupiedBusinessSeats > 0) {
				return 1;
			} else if (economySeats - occupiedEconomySeats > 0) {
				return 0;
			} else {
				return -1;
			}
		} else {
			if (economySeats - occupiedEconomySeats > 0) {
				return 0;
			} else {
				return -1;
			}
		}
	}
	
	public boolean isValidOperation(Passenger passenger) {
		boolean isValid = true;
		if (whichSeat(passenger.getClassNumber()) == -1) {
			isValid = false;
		} else if (!this.currentAirport.isPassengerAtAirport(passenger) ) { 
			isValid = false;
		} else if (this.weight + passenger.getWeight() > this.maxWeight) {
			isValid = false;
		}
		return isValid;
	}
	
	public double loadPassenger(Passenger passenger) {
		double loadingFee;
		if (isValidOperation(passenger)) {
			double seatTypeConstant;
			int seatType = whichSeat(passenger.getClassNumber());
			if (seatType == 0) {
				seatTypeConstant = 1.2;
			} else if (seatType == 1) {
				seatTypeConstant = 1.5;
			} else {
				seatTypeConstant = 2.5;
			}
			loadingFee = operationFee * this.aircraftTypeMultiplier * seatTypeConstant; 
			passenger.board(seatType);
			weight += passenger.getWeight();
			currentAirport.removePassenger(passenger);
			passengersAtAircraft.add(passenger);
			if (seatType == 2) {
				occupiedFirstClassSeats += 1;
			} else if (seatType == 1) {
				occupiedBusinessSeats += 1;
			} else {
				occupiedEconomySeats += 1;
			}
			passenger.lastSeatType = seatType;
			passenger.boarded = currentAirport;
			return loadingFee;
		} else {
			return this.currentAirport.getOperationFee();
		}
	}
	
	public boolean canDisembark(Passenger passenger) {
		return passenger.getDestinations().contains(this.currentAirport);
	}
	
	public double unloadPassenger(Passenger passenger) {
		if (canDisembark(passenger)) {
			double ticketPrice;
			double seatConstant;
			if (passenger.lastSeatType == 2) {
				seatConstant = 7.5;
			} else if (passenger.lastSeatType == 1) {
				seatConstant = 2.8;
			} else {
				seatConstant = 1.0;
			}
			double disembarkationRevenue = passenger.disembark(currentAirport, aircraftTypeMultiplier);
			ticketPrice = disembarkationRevenue * seatConstant;
			weight -= passenger.getWeight();
			passengersAtAircraft.remove(passenger);
			currentAirport.addPassenger(passenger);
			if (passenger.lastSeatType == 2) {
				occupiedFirstClassSeats -= 1;
			} else if (passenger.lastSeatType == 1) {
				occupiedBusinessSeats -= 1;
			} else {
				occupiedEconomySeats -= 1;
			}
			return ticketPrice;
		} else {
			return operationFee;
		}
	}
	
	int whichSeatTransfer(int classNumber, PassengerAircraft toAircraft) {
		if (classNumber == 2 || classNumber == 3) {
			if (toAircraft.firstClassSeats - toAircraft.occupiedFirstClassSeats > 0) {
				return 2;
			} if (toAircraft.businessSeats - toAircraft.occupiedBusinessSeats > 0) {
				return 1;
			} else if (toAircraft.economySeats - toAircraft.occupiedEconomySeats > 0) {
				return 0;
			} else {
				return -1;
			}
		} else if (classNumber == 1) {
			if (toAircraft.businessSeats - toAircraft.occupiedBusinessSeats > 0) {
				return 1;
			} else if (toAircraft.economySeats - toAircraft.occupiedEconomySeats > 0) {
				return 0;
			} else {
				return -1;
			}
		} else {
			if (toAircraft.economySeats - toAircraft.occupiedEconomySeats > 0) {
				return 0;
			} else {
				return -1;
			}
		}
	}
	
	public boolean canTransfer(Passenger passenger, PassengerAircraft toAircraft) {
		boolean itCan = true;
		if (whichSeatTransfer(passenger.getClassNumber(), toAircraft) == -1) {
			itCan = false;
		} else if (this.currentAirport != toAircraft.currentAirport) { 
			itCan = false;
		} else if (toAircraft.weight + passenger.getWeight() > toAircraft.maxWeight) {
			itCan = false;
		}
		return itCan;
	}
	
	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		double transferFee;
		if (canTransfer(passenger, toAircraft)) {
			double seatTypeConstant;
			int seatType = whichSeatTransfer(passenger.getClassNumber(), toAircraft);
			if (seatType == 0) {
				seatTypeConstant = 1.2;
			} else if (seatType == 1) {
				seatTypeConstant = 1.5;
			} else {
				seatTypeConstant = 2.5;
			}
			transferFee = operationFee * this.aircraftTypeMultiplier * seatTypeConstant; 
			passenger.connection(seatType);
			weight -= passenger.getWeight();
			toAircraft.weight += passenger.getWeight();
			passengersAtAircraft.remove(passenger);
			toAircraft.passengersAtAircraft.add(passenger);
			if (seatType == 2) {
				occupiedFirstClassSeats -= 1;
				toAircraft.occupiedFirstClassSeats += 1;
			} else if (seatType == 1) {
				occupiedBusinessSeats -= 1;
				toAircraft.occupiedBusinessSeats += 1;
			} else {
				occupiedEconomySeats -= 1;
				toAircraft.occupiedEconomySeats += 1;
			}
			passenger.lastSeatType = seatType;
			return transferFee;
		} else {
			return operationFee;
		}
	}
	
	public double getFullness() {
		return (occupiedEconomySeats + occupiedBusinessSeats + occupiedFirstClassSeats) / (economySeats + businessSeats + firstClassSeats);
	}
	
	public boolean canSetSeats(int economy, int business, int firstClass) {
		boolean condition = economySeats + (businessSeats * 3) + (firstClassSeats * 8) + economy + (business * 3) + (firstClass * 8) <= floorArea;
		if (condition) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean setSeats(int economy, int business, int firstClass) {
		if (canSetSeats(economy, business, firstClass)) {
			economySeats += economy;
			businessSeats += business;
			firstClassSeats += firstClass;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isEmpty() {
		return passengersAtAircraft.isEmpty();
	}
	
	public boolean setAllEconomy() {
		if (isEmpty()) {
			economySeats = (int)floorArea / 1;
			businessSeats = 0;
			firstClassSeats = 0;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean setAllBusiness() {
		if (isEmpty()) {
			economySeats = 0;
			businessSeats = (int)floorArea / 3;
			firstClassSeats = 0;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean setAllFirstClass() {
		if (isEmpty()) {
			economySeats = 0;
			businessSeats = 0;
			firstClassSeats = (int)floorArea / 8;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean setRemainingEconomy() {
		int leftoverSpace = (int)floorArea - (economySeats + (businessSeats * 3) + (firstClassSeats * 8));
		if (leftoverSpace >= 1) {
			economySeats += leftoverSpace / 1;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean setRemainingBusiness() {
		int leftoverSpace = (int)floorArea - (economySeats + (businessSeats * 3) + (firstClassSeats * 8));
		if (leftoverSpace >= 3) {
			businessSeats += leftoverSpace / 3;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean setRemainingFirstClass() {
		int leftoverSpace = (int)floorArea - (economySeats + (businessSeats * 3) + (firstClassSeats * 8));
		if (leftoverSpace >= 8) {
			firstClassSeats += leftoverSpace / 8;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isFull() {
		if (occupiedEconomySeats + occupiedBusinessSeats + occupiedFirstClassSeats == economySeats + businessSeats + firstClassSeats) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isFull(int seatType) {
		if (seatType == 0) {
			if (occupiedEconomySeats == economySeats) {
				return true;
			} else {
				return false;
			}
		} else if (seatType == 1) {
			if (occupiedBusinessSeats == businessSeats) {
				return true;
			} else {
				return false;
			}
		} else {
			if (occupiedFirstClassSeats == firstClassSeats) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public double getAvailableWeight() {
		return maxWeight - weight;
	}
}
