package project.airline.aircraft;

import java.util.ArrayList;

import project.airline.Airline;
import project.airport.Airport;
import project.interfaces.PassengerInterface;
import project.passenger.BusinessPassenger;
import project.passenger.EconomyPassenger;
import project.passenger.FirstClassPassenger;
import project.passenger.LuxuryPassenger;
import project.passenger.Passenger;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface{
	protected double floorArea;
	protected double occupiedArea = 0;
	private int economySeats, businessSeats, firstClassSeats;
	private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
	protected double operationFee;
	public ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	public PassengerAircraft(Airport currentAirport,double weight,double maxWeight,double fuelCapacity,double fuelConsumption,double aircraftTypeMultiplier,double operationFee,double floorArea) {
		super(currentAirport,weight,maxWeight,fuelCapacity,fuelConsumption,aircraftTypeMultiplier);
		this.operationFee = operationFee;
		this.floorArea = floorArea;
	}
	public boolean canLoadPassenger(Passenger passenger) {
		if ((weight + passenger.getWeight()) < maxWeight) {
			if ((passenger instanceof LuxuryPassenger) || (passenger instanceof FirstClassPassenger)){
				if (!isFull()){
					return true;
				}
			}
			if (passenger instanceof BusinessPassenger) {
				if (!isFull(0) || !isFull(1)) {
					return true;
				}
			}
			if (passenger instanceof EconomyPassenger) {
				if (!isFull(0)) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean canTransferPassenger(Passenger passenger,PassengerAircraft toAircraft) {
		if ((weight + passenger.getWeight()) < maxWeight) {
			if (currentAirport.equals(toAircraft.currentAirport)){
				if ((passenger instanceof LuxuryPassenger) || (passenger instanceof FirstClassPassenger)){
					if (!isFull()){
						return true;
					}
				}
				if (passenger instanceof BusinessPassenger) {
					if (!isFull(0) || !isFull(1)) {
						return true;
					}
				}
				if (passenger instanceof EconomyPassenger) {
					if (!isFull(0)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public double loadPassenger(Passenger passenger) {
		double loadingFee;
		Airport airport = currentAirport;
		airport.removePassenger(passenger);
		passengers.add(passenger);
		weight += passenger.getWeight();
		if (passenger instanceof LuxuryPassenger || passenger instanceof FirstClassPassenger) {
			if (!isFull(2)) {
				passenger.board(2);
				loadingFee = operationFee * aircraftTypeMultiplier * 2.5;
				occupiedFirstClassSeats += 1;
			}
			else if (!isFull(1)) {
				passenger.board(1);
				loadingFee = operationFee * aircraftTypeMultiplier * 1.5;
				occupiedBusinessSeats += 1;
			}
			else {
				passenger.board(0);
				loadingFee = operationFee * aircraftTypeMultiplier * 1.2;
				occupiedEconomySeats += 1;
			}
		}
		else if (passenger instanceof BusinessPassenger) {
			if (!isFull(1)) {
				passenger.board(1);
				loadingFee = operationFee * aircraftTypeMultiplier * 1.5;
				occupiedBusinessSeats += 1;
			}
			else {
				passenger.board(0);
				loadingFee = operationFee * aircraftTypeMultiplier * 1.2;
				occupiedEconomySeats += 1;
			}
		}
		else {
			passenger.board(0);
			loadingFee = operationFee * aircraftTypeMultiplier * 1.2;
			occupiedEconomySeats += 1;
		}
		return loadingFee;
	}
	public double unloadPassenger(Passenger passenger) {
		double profit = passenger.disembark(currentAirport, aircraftTypeMultiplier);
		Airport airport = currentAirport;
		if (profit == 0) {
			return -operationFee;
		}
		passengers.remove(passenger);
		airport.addPassenger(passenger);
		weight -= passenger.getWeight();
		if (passenger.getCurrentSeat() == 0) {
			occupiedEconomySeats --;
			return profit * 1;
		}
		if (passenger.getCurrentSeat() == 1) {
			occupiedBusinessSeats --;
			return profit * 2.8;
		}
		if (passenger.getCurrentSeat() == 2) {
			occupiedFirstClassSeats --;
			return profit * 7.5;
		}
		return 0;
	}
	@Override
	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		double loadingFee;
		passengers.remove(passenger);
		toAircraft.passengers.add(passenger);
		weight -= passenger.getWeight();
		toAircraft.weight += passenger.getWeight();
		if (passenger.getCurrentSeat() == 0) {
			occupiedEconomySeats --;
		}
		if (passenger.getCurrentSeat() == 1) {
			occupiedBusinessSeats --;
		}
		if (passenger.getCurrentSeat() == 2) {
			occupiedFirstClassSeats --;
		}
		if (passenger instanceof LuxuryPassenger || passenger instanceof FirstClassPassenger) {
			if (!isFull(2)) {
				passenger.connection(2);
				loadingFee = operationFee * aircraftTypeMultiplier * 2.5;
				toAircraft.occupiedFirstClassSeats ++;
			}
			else if (!isFull(1)) {
				passenger.connection(1);
				loadingFee = operationFee * aircraftTypeMultiplier * 1.5;
				toAircraft.occupiedBusinessSeats ++;
			}
			else {
				passenger.connection(0);
				loadingFee = operationFee * aircraftTypeMultiplier * 1.2;
				toAircraft.occupiedEconomySeats ++;
			}
		}
		if (passenger instanceof BusinessPassenger) {
			if (!isFull(1)) {
				passenger.connection(1);
				loadingFee = operationFee * aircraftTypeMultiplier * 1.5;
				toAircraft.occupiedBusinessSeats += 1;
			}
			else {
				passenger.connection(0);
				loadingFee = operationFee * aircraftTypeMultiplier * 1.2;
				toAircraft.occupiedEconomySeats += 1;
			}
		}
		else {
			passenger.connection(0);
			loadingFee = operationFee * aircraftTypeMultiplier * 1.2;
			toAircraft.occupiedEconomySeats += 1;
		}
		System.out.println("6 " + passenger.getID() + " " + ID + " " + toAircraft.ID + " " + currentAirport.getID());
		return loadingFee;
	}
	@Override
	public boolean isFull() {
		if (getFullness() == 1) {
			return true;
		}
		return false;
	}
	@Override
	public boolean isFull(int seatType) {
		if (seatType == 0) {
			if (occupiedEconomySeats == economySeats) {
				return true;
			}
			return false;
		}
		if (seatType == 1) {
			if (occupiedBusinessSeats == businessSeats) {
				return true;
			}
			return false;
		}
		if (seatType == 2) {
			if (occupiedFirstClassSeats == firstClassSeats) {
				return true;
			}
			return false;
		}
		return false;
	}
	@Override
	public boolean isEmpty() {
		if (getFullness() == 0) {
			return true;
		}
		return false;
	}
	@Override
	public double getAvailableWeight() {
		return maxWeight - weight;
	}
	@Override
	public boolean setSeats(int economy, int business, int firstClass) {
		if ((economy + business * 3 + firstClass * 8)>floorArea) {
			return false;
		}
		economySeats = economy;
		businessSeats = business;
		firstClassSeats = firstClass;
		occupiedArea = economy + business * 3 + firstClass * 8;
		return true;
	}
	@Override
	public boolean setAllEconomy() { //economy 1 area
		if (isEmpty()) {
			resetLayout();
			economySeats = (int) floorArea;
			occupiedArea = floorArea;
			return true;
		}
		return false;
	}
	@Override
	public boolean setAllBusiness() { // business 3 area
		if (isEmpty()) {
			resetLayout();
			int seatCount = (int) floorArea/3;
			businessSeats = seatCount;
			occupiedArea += seatCount * 3;
			return true;
		}
		return false;
	}
	@Override
	public boolean setAllFirstClass() { // firstClass 8 area
		if (isEmpty()) {
			resetLayout();
			int seatCount = (int) floorArea/8;
			firstClassSeats = seatCount;
			occupiedArea += seatCount * 8;
			return true;
		}
		return false;
	}
	@Override
	public boolean setRemainingEconomy() {
		double emptyArea = floorArea - occupiedArea;
		int seatCount = (int) emptyArea;
		economySeats += seatCount;
		occupiedArea = floorArea;
		return true;
	}
	@Override
	public boolean setRemainingBusiness() {
		double emptyArea = floorArea - occupiedArea;
		int seatCount = (int) emptyArea/3;
		businessSeats += seatCount;
		occupiedArea += seatCount * 3;
		return true;
	}
	@Override
	public boolean setRemainingFirstClass() {
		double emptyArea = floorArea - occupiedArea;
		int seatCount = (int) emptyArea/8;
		firstClassSeats += seatCount;
		occupiedArea += seatCount * 8;
		return true;
	}
	public void resetLayout() {
		economySeats = 0;
		businessSeats = 0;
		firstClassSeats = 0;
		occupiedArea = 0;
	}
	@Override
	public double getFullness() {
			double occupied = occupiedEconomySeats + occupiedBusinessSeats + occupiedFirstClassSeats;
			double seat = economySeats + businessSeats + firstClassSeats;
			if (seat == 0) {
				return 0;
			}
			return occupied/seat;
	}
	public ArrayList<Passenger> getPassengers(){
		return passengers;
	}
}

	