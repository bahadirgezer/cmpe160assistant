package project.airlines.aircrafts;

import project.airports.Airport;
import project.interfaces.PassengerInterface;
import project.passengers.Passenger;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {
	
	protected double floorArea;
	private int economySeats, businessSeats, firstClassSeats;
	private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
	
	
	protected PassengerAircraft(double operationFee, Airport currentAirport) {
		super(operationFee, currentAirport);
	}

	public boolean loadingIsValid(Passenger passenger) {
		return (weight+passenger.getWeight()<=maxWeight) && hasAvailableSeat(passenger) 
				&& passenger.getPreviousAirport().equals(getCurrentAirport());
	}
	
	public double loadPassenger(Passenger passenger) {
		if (loadingIsValid(passenger)) {
			int s = bestAvailableSeat(passenger);
			assignSeat(passenger, s);
			passenger.board(s);
			return operationFee*aircraftTypeMultiplier;
		}
		return operationFee;
	}
	public double unloadPassenger(Passenger passenger) {
		if (passenger.canDisembark(getCurrentAirport())) {
			double seatConstant = 1;
			switch (passenger.getSeatType()) {
				case 0:
					seatConstant = 1;
				case 1:
					seatConstant = 2.8;
				case 2:
					seatConstant = 7.5;
			}
			disassignSeat(passenger);
			return passenger.disembark(getCurrentAirport(), aircraftTypeMultiplier)*seatConstant;
		}
		return operationFee;
	}

	public boolean canTransferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		return (weight+passenger.getWeight()<=toAircraft.maxWeight) && toAircraft.hasAvailableSeat(passenger)
				&& getCurrentAirport().equals(toAircraft.getCurrentAirport());
	}
	
	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		if (canTransferPassenger(passenger, toAircraft)) {
			disassignSeat(passenger);
			int s = bestAvailableSeat(passenger);
			assignSeat(passenger, s);
			passenger.connection(s);
			return operationFee*aircraftTypeMultiplier;
		}
		return operationFee;
	}

	private int bestAvailableSeat(Passenger passenger) {
		for (int i = passenger.getPassengerType(); i>=0; i--) {
			if (!isFull(i)) {
				return i;
			}
		}
		return -1;
	}
	
	private boolean hasAvailableSeat(Passenger passenger) {
		if (bestAvailableSeat(passenger) != -1) {
			return true;
		}
		return false;
	}
	
	private void assignSeat(Passenger passenger, int seatType) {
		weight += passenger.getWeight();
		passenger.setSeatType(seatType);
		setOccupiedSeats(seatType, 1);
	}
	
	private void disassignSeat(Passenger passenger) {
		
		weight -= passenger.getWeight();
		passenger.setSeatType(-1);
		setOccupiedSeats(passenger.getSeatType(), -1);
	}
	
	

	@Override
	public boolean isFull() {
		return (totalOccupiedSeats() == totalSeats());
	}

	@Override
	public boolean isFull(int seatType) {
		return (getOccupiedSeats(seatType) == getSeats(seatType));
	}

	@Override
	public boolean isEmpty() {
		return (totalOccupiedSeats() == 0);
	}

	@Override
	public double getAvailableWeight() {
		return maxWeight-weight;
	}

	@Override
	public boolean setSeats(int economy, int business, int firstClass) {
		economySeats = economy;
		businessSeats = business;
		firstClassSeats = firstClass;
		return true;
	}
	
	public boolean canSetSeats(int economy, int business, int firstClass) {
		double potentialOccupiedArea = economy + 3*business + 8*firstClass;
		return (potentialOccupiedArea <= floorArea);
	}

	@Override
	public boolean setAllEconomy() {
		setSeats((int)floorArea, 0, 0);
		return true;
	}

	@Override
	public boolean setAllBusiness() {
		setSeats(0, (int) floorArea/3, 0);
		return true;
	}

	@Override
	public boolean setAllFirstClass() {
		setSeats(0, 0, (int) floorArea/8);
		return true;
	}

	@Override
	public boolean setRemainingEconomy() {
		setSeats((int)getfreeArea(), 0, 0);
		return true;
	}

	@Override
	public boolean setRemainingBusiness() {
		setSeats(0, (int)getfreeArea()/3, 0);
		return true;
	}

	@Override
	public boolean setRemainingFirstClass() {
		setSeats(0, 0, (int)getfreeArea()/8);
		return true;
	}

	private double getfreeArea() {
		return floorArea-getOccupiedArea();
	}
	
	@Override
	public double getFullness() {
		return ((double) totalOccupiedSeats())/totalSeats();
	}

	private int totalSeats() {
		return economySeats + businessSeats + firstClassSeats;
	}
	
	private int totalOccupiedSeats() {
		return occupiedEconomySeats + occupiedBusinessSeats + occupiedFirstClassSeats;
	}
	
	private double getOccupiedArea() {
		return economySeats + 3*businessSeats + 8*firstClassSeats;
	}
	
	private int getSeats(int i) {
		switch (i) {
			case 0:
				return economySeats;
			case 1:
				return businessSeats;
			case 2:
				return firstClassSeats;
		}
		return 0;
	}
	
	private int getOccupiedSeats(int i) {
		switch (i) {
			case 0:
				return occupiedEconomySeats;
			case 1:
				return occupiedBusinessSeats;
			case 2:
				return occupiedFirstClassSeats;
		}
		return 0;
	}
	
	private void setOccupiedSeats(int i, int j) {
		switch (i) {
			case 0:
				occupiedEconomySeats += j;
			case 1:
				occupiedBusinessSeats += j;
			case 2:
				occupiedFirstClassSeats += j;
		}
	}
}

