package project.airline.aircraft;
import java.util.ArrayList;
import project.airline.*;
import project.airport.Airport;
import project.interfaces.PassengerInterface;
import project.passenger.Passenger;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {
	protected double floorArea;
	private int economySeats, businessSeats, firstClassSeats;
	private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
	private ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	private double aircraftTypeMultiplier;
	private double operationFee;
	
	public PassengerAircraft(double weight, double maxWeight, double floorArea, double fuelCapacity, double aircraftTypeMultiplier, Airport ID, int operationFeeId) {
		super(weight, maxWeight, fuelCapacity, ID, aircraftTypeMultiplier);
		this.floorArea = floorArea;
		this.aircraftTypeMultiplier = aircraftTypeMultiplier;
		this.operationFee = Airline.aircraftOperationFees[operationFeeId];
	}
	
	
	public double loadPassenger(Passenger passenger) {
		this.passengers.add(passenger);
		Airport currentAirport = this.getCurrentAirport();
		double loadingFee = this.operationFee;
		double seatConstant=1;
		
		
		if ((this.occupiedFirstClassSeats<this.firstClassSeats)&&(passenger.getType()==2)) {
			seatConstant = 2.5;
			passenger.board(2);
			Airline.log+= String.format("4 %d %d %d\n", passenger.getID(), Airline.aircrafts.indexOf(this), currentAirport.getID());

			this.occupiedFirstClassSeats+=1;
			
		}
		else if ((this.occupiedBusinessSeats<this.businessSeats)&&(passenger.getType()>=1)) {
			seatConstant = 1.5;
			passenger.board(1);
			Airline.log+= String.format("4 %d %d %d\n", passenger.getID(), Airline.aircrafts.indexOf(this), currentAirport.getID());

			this.occupiedBusinessSeats+=1;
		}
		else if (this.occupiedEconomySeats<this.economySeats) {
			seatConstant = 1.2;
			passenger.board(0);
			Airline.log+= String.format("4 %d %d %d\n", passenger.getID(), Airline.aircrafts.indexOf(this), currentAirport.getID());

			this.occupiedEconomySeats+=1;
		}
		if (seatConstant>1) {
			loadingFee = this.operationFee*this.aircraftTypeMultiplier*seatConstant;
			this.currentWeight+=passenger.getPassengerWeight();
		}

		return loadingFee;
		
		
	}
	
	public double unloadPassenger(Passenger passenger) {
		Airline.log+= String.format("5 %d %d %d\n", passenger.getID(), Airline.aircrafts.indexOf(this), this.getCurrentAirport().getID());

		this.passengers.remove(passenger);
		this.currentAirport.addPassenger(passenger);
		double seatConstant; 
		if (passenger.getSeatMultiplier() == 1.2) {
			seatConstant = 2.8;
		}
		else if (passenger.getSeatMultiplier() == 0.6) {
			seatConstant = 1.0;
		}
		else {
			seatConstant = 7.5;
		}
		if (passenger.getDestinations().contains(this.currentAirport.getID())) {
			double revenue = seatConstant*passenger.disembark(this.currentAirport, this.aircraftTypeMultiplier);
		return revenue; 
		}
		else {
			return (this.operationFee*(-1));
		}
	}
	
	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		double loadingFee = toAircraft.loadPassenger(passenger);
		if (loadingFee!=this.getCurrentAirport().getOperationFee()) {
			Airline.log+= String.format("6 %d %d %d\n", passenger.getID(), Airline.aircrafts.indexOf(this), currentAirport.getID());

			return loadingFee;
		}
		else {
			return (this.getCurrentAirport().getOperationFee())*-1;
		}
	}
	
	public double getFullness() {
		return(this.occupiedBusinessSeats+this.occupiedEconomySeats+this.occupiedFirstClassSeats)/(this.businessSeats+this.economySeats+this.firstClassSeats);
	}
	@Override
	public boolean isFull() {
		if ((this.occupiedBusinessSeats==this.businessSeats)&&(this.occupiedEconomySeats==this.economySeats)&&(this.firstClassSeats==this.occupiedFirstClassSeats)) {
			return true;
		}
		else {
		return false;
		}
	}

	@Override
	public boolean isFull(int seatType) {
		if (seatType==1) {
			if (this.occupiedBusinessSeats==this.businessSeats) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (seatType==2) {
			if (this.occupiedEconomySeats==this.economySeats) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if (this.occupiedFirstClassSeats==this.firstClassSeats) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	@Override
	public boolean isEmpty() {
		if (this.occupiedBusinessSeats+this.occupiedEconomySeats+this.occupiedFirstClassSeats==0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public double getAvailableWeight() {
		return this.maxWeight-this.getCurrentWeight();
	}

	@Override
	public boolean setSeats(int economy, int business, int firstClass) {
			double available = this.floorArea;
			if (firstClass*8<=available) {
				this.firstClassSeats = firstClass;
				available-=8*firstClass;
			}
			else {
				this.firstClassSeats = (int)available/8;
				available -= this.firstClassSeats*8;
			}
			if (business*3<=available) {
				this.businessSeats = business;
				available -= 3*business;
			}
			else {
				this.businessSeats = (int)available/3;
				available -= this.businessSeats*3;
			}
			if (economy<=available) {
				this.economySeats = economy;
			}
			else {
				this.economySeats = (int)available;
			}
			Airline.log+= String.format("2 %d %d %d %d\n", Airline.aircrafts.indexOf(this), this.economySeats, this.businessSeats, this.firstClassSeats);

			return true;
	}

	@Override
	public boolean setAllEconomy() {
		this.economySeats = (int)this.floorArea;
		this.businessSeats = 0;
		this.firstClassSeats = 0;
		Airline.log+= String.format("2 %d %d %d %d\n", Airline.aircrafts.indexOf(this), this.economySeats, this.businessSeats, this.firstClassSeats);


		return true;
	}

	@Override
	public boolean setAllBusiness() {
		this.businessSeats = (int)(this.floorArea/3);
		this.economySeats = 0;
		this.firstClassSeats = 0;
		Airline.log+= String.format("2 %d %d %d %d\n", Airline.aircrafts.indexOf(this), this.economySeats, this.businessSeats, this.firstClassSeats);


		return true;
	}

	@Override
	public boolean setAllFirstClass() {
		this.firstClassSeats = (int)(this.floorArea/8);
		this.businessSeats = 0;
		this.economySeats= 0;
		Airline.log+= String.format("2 %d %d %d %d\n", Airline.aircrafts.indexOf(this), this.economySeats, this.businessSeats, this.firstClassSeats);


		return true;
	}

	@Override
	public boolean setRemainingEconomy() {
		this.economySeats = (int)(this.floorArea - this.businessSeats*3- this.firstClassSeats*8);
		Airline.log+= String.format("2 %d %d %d %d\n", Airline.aircrafts.indexOf(this), this.economySeats, this.businessSeats, this.firstClassSeats);

		return true;
	}

	@Override
	public boolean setRemainingBusiness() {
		this.businessSeats = (int)((this.floorArea-this.firstClassSeats*8-this.economySeats)/3);
		Airline.log+= String.format("2 %d %d %d %d\n", Airline.aircrafts.indexOf(this), this.economySeats, this.businessSeats, this.firstClassSeats);

		return true;
	}

	@Override
	public boolean setRemainingFirstClass() {
		this.firstClassSeats = (int)((this.floorArea-this.economySeats-this.businessSeats*3)/8);
		Airline.log+= String.format("2 %d %d %d %d\n", Airline.aircrafts.indexOf(this), this.economySeats, this.businessSeats, this.firstClassSeats);

		return true;
	}
	
	public boolean hasEconomySeat() {
		if (this.occupiedEconomySeats<this.economySeats) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean hasBusinessSeat() {
		if (this.occupiedBusinessSeats<this.businessSeats) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean hasFirstClassSeat() {
		if (this.occupiedFirstClassSeats<this.firstClassSeats) {
			return true;
		}
		else {
			return false;
		}
	}
	public ArrayList<Passenger> getPassengers() {
		return this.passengers;
	}

}
