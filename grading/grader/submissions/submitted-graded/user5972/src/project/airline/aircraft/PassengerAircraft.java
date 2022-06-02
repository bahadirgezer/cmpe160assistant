package project.airline.aircraft;

import project.airport.Airport;
import project.passenger.Passenger;
import project.interfaces.PassengerInterface;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {
	protected double floorArea;
	private int economySeats, businessSeats, firstClassSeats;
	private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;

	public PassengerAircraft(Airport currentAirport, double weight, double maxWeight, double fuelCapacity,
			double fuelConsumption, double aircraftTypeMultiplier) {
		super(currentAirport, weight, maxWeight, fuelCapacity, fuelConsumption, aircraftTypeMultiplier);

	}

	public double loadPassenger(Passenger passenger) {
		if (isFull() || passenger.getWeight() + weight > maxWeight
				|| (currentAirport != theAirline.passengersAirport.get(passenger))) {
			return getOperationFee();
		}
		for (int i = passenger.getPassengerType(); i >= 0; i--) {
			if (isFull(i) || !passenger.board(i) || i > 2) {
				continue;
			}
			theAirline.passengersAirport.remove(passenger);
			theAirline.passengersAircraft.put(passenger, this);
			weight += passenger.getWeight();
			switch (i) {
			case 0:
				occupiedEconomySeats += 1;
				return getOperationFee() * aircraftTypeMultiplier * 1.2;
			case 1:
				occupiedBusinessSeats += 1;
				return getOperationFee() * aircraftTypeMultiplier * 1.5;
			case 2:
				occupiedFirstClassSeats += 1;
				return getOperationFee() * aircraftTypeMultiplier * 2.5;
			default:
				return getOperationFee();
			}
		}
		return getOperationFee();
	}

	public double unloadPassenger(Passenger passenger) {
		int seatType = passenger.getSeatType();
		if (theAirline.passengersAircraft.get(passenger) == null) {
			return -getOperationFee();
		}
		double ticketPrize = passenger.disembark(currentAirport, aircraftTypeMultiplier);
		if (ticketPrize == 0 || seatType > 2) {
			return -getOperationFee();
		}
		theAirline.passengersAircraft.remove(passenger);
		theAirline.passengersAirport.put(passenger, currentAirport);
		switch (seatType) {
		case 0:
			return ticketPrize;
		case 1:
			return ticketPrize * 2.8;
		case 2:
			return ticketPrize * 7.5;
		default:
			return -getOperationFee();
		}

	}

	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		theAirline.write("6 "+ passenger.getID()+ " "+ theAirline.passengersAircraft.get(passenger)+ " "+ toAircraft+ " "+ "\n");
		theAirline.passengersAircraft.remove(passenger);
		return toAircraft.loadPassenger(passenger); // hata var bastan bak connection methodu kullanilmasi lazim

	}

	public boolean isFull() {
		if ((occupiedBusinessSeats + occupiedEconomySeats + occupiedFirstClassSeats) == (businessSeats + economySeats
				+ firstClassSeats)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isFull(int seatType) {
		switch (seatType) {
		case 0:
			return (economySeats == occupiedEconomySeats);
		case 1:
			return (businessSeats == occupiedBusinessSeats);
		case 2:
			return (firstClassSeats == occupiedFirstClassSeats);
		default:
			return false;
		}
	}

	public boolean isEmpty() {
		if ((occupiedBusinessSeats + occupiedEconomySeats + occupiedFirstClassSeats) == 0) {
			return true;
		} else {
			return false;
		}
	}

	public double getAvailableWeight() {
		return (maxWeight - weight);
	}

	public boolean setSeats(int economy, int business, int firstClass) {
		if ((economy + business * 3 + firstClass * 8) <= floorArea) {
			economySeats = economy;
			businessSeats = business;
			firstClassSeats = firstClass;
			writeSeats();
			return true;
		} else {
			return false;
		}
	}

	public boolean setAllEconomy() {
		economySeats = (int) (floorArea);
		firstClassSeats = 0;
		businessSeats = 0;
		writeSeats();
		return true;
	}

	public boolean setAllBusiness() {
		economySeats = 0;
		firstClassSeats = 0;
		businessSeats = (int) (floorArea / 3);
		writeSeats();
		return true;
	}

	public boolean setAllFirstClass() {
		economySeats = 0;
		firstClassSeats = (int) (floorArea / 8);
		businessSeats = 0;
		writeSeats();
		return true;
	}

	public boolean setRemainingEconomy() {
		economySeats += (int) ((floorArea - (economySeats + businessSeats * 3 + firstClassSeats * 8)));
		writeSeats();
		return true;
	}

	public boolean setRemainingBusiness() {
		businessSeats += (int) ((floorArea - (economySeats + businessSeats * 3 + firstClassSeats * 8)) / 3);
		writeSeats();
		return true;
	}

	public boolean setRemainingFirstClass() {
		firstClassSeats += (int) ((floorArea - (economySeats + businessSeats * 3 + firstClassSeats * 8)) / 8);
		writeSeats();
		return true;
	}

	public double getFullness() {
		return ((occupiedEconomySeats + occupiedBusinessSeats + occupiedFirstClassSeats)
				/ (economySeats + businessSeats + firstClassSeats));
	}

	private void writeSeats() {
		theAirline.write("2 " + economySeats + " " + businessSeats + " " + firstClassSeats + "\n");
	}

}
