package project.airline.aircraft;
import project.airport.Airport;
import project.interfaces.PassengerInterface;
import project.passenger.BusinessPassenger;
import project.passenger.EconomyPassenger;
import project.passenger.FirstClassPassenger;
import project.passenger.Passenger;
import java.util.*;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface{
	
	protected double floorArea;
	private int economySeats, businessSeats, firstClassSeats;
	private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
	protected double operationFee;
	protected double aircraftTypeMultiplier;
	protected ArrayList<Passenger> Passengers = new ArrayList<Passenger>();
	
	
	public double getFloorArea() {return floorArea;}
		
	/**Constructor of PassengerAircraft class
	 * @param currentAirport
	 * @param weight
	 * @param maxWeight
	 * @param floorArea
	 * @param fuelCapacity
	 * @param fuelConsumption
	 * @param aircraftTypeMultiplier
	 * @param operationFee
	 */
	public PassengerAircraft(Airport currentAirport,double weight,
			double maxWeight,double floorArea,double fuelCapacity,
			double fuelConsumption,double aircraftTypeMultiplier,double operationFee) {
		this.currentAirport=currentAirport;
		this.weight=weight;
		this.maxWeight=maxWeight;
		this.floorArea=floorArea;
		this.fuelCapacity=fuelCapacity;
		this.fuelConsumption=fuelConsumption;
		this.aircraftTypeMultiplier=aircraftTypeMultiplier;
		this.operationFee=operationFee;
	
	}



	/**
	 *
	 */
	public double loadPassenger(Passenger passenger) {
		if(canload(passenger)) {
			Passengers.add(passenger);
			currentAirport.removePassenger(passenger);
			if(passenger instanceof EconomyPassenger) {
				occupiedEconomySeats++;
				passenger.board(0);
			}
			else if(passenger instanceof BusinessPassenger) {
				if(!isFull(1)) {
					occupiedBusinessSeats++;
					passenger.board(1);
				}
				else {
					occupiedEconomySeats++;
					passenger.board(0);
				}
			}
			else if(passenger instanceof FirstClassPassenger) {
				if(!isFull(2)) {
					occupiedFirstClassSeats++;
					passenger.board(2);
				}
				else {
					if(!isFull(1)) {
						occupiedBusinessSeats++;
						passenger.board(1);
					}
					else {
						occupiedEconomySeats++;
						passenger.board(0);
					}
				}
			}
			else {
				if(!isFull(2)) {
					occupiedFirstClassSeats++;
					passenger.board(2);
				}
				else {
					if(!isFull(1)) {
						occupiedBusinessSeats++;
						passenger.board(1);
					}
					else {
						occupiedEconomySeats++;
						passenger.board(0);
					}
				}
			}
		
			weight+=passenger.getWeight();
			double[] seatmultis = {1.2,1.5,2.5};
			return operationFee*aircraftTypeMultiplier*(seatmultis[passenger.getSeatType()]);
		}
		return operationFee;
	}
	
	
	
	
	public double unloadPassenger(Passenger passenger) {
		double a = passenger.disembark(currentAirport, aircraftTypeMultiplier);
		if(a!=0) {
			weight-=passenger.getWeight();
			Passengers.remove(passenger);
			currentAirport.addPassenger(passenger);
			switch(passenger.getSeatType()) {
			case 0:
				occupiedEconomySeats--;
				break;
			case 1:
				occupiedBusinessSeats--;
				break;
			case 2:
				occupiedFirstClassSeats--;
				break;
			}
			double[] seatmultis = {1.0,2.8,7.5};
			
			return a*seatmultis[passenger.getSeatType()];
		}
		return operationFee;
	}
	
	
	
	
	
	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		//System.out.println("6 "+passenger.getID()+" "+ID+" "+toAircraft.ID+" "+currentAirport.getID());
		if(toAircraft.canload(passenger)) {
			if(passenger.disembark(currentAirport, aircraftTypeMultiplier)!=0) {
				switch(passenger.getSeatType()) {
				case 0:
					toAircraft.occupiedEconomySeats++;
					break;
				case 1:
					toAircraft.occupiedBusinessSeats++;
					break;
				case 2:
					toAircraft.occupiedFirstClassSeats++;
					break;
				}
				double[] seatmultis = {1.2,1.5,2.5};
				return operationFee*aircraftTypeMultiplier*(seatmultis[passenger.getSeatType()]);
			}
		}
		return operationFee;
	}

	
	@Override
	public boolean isFull() {
		return (occupiedEconomySeats+occupiedBusinessSeats+occupiedFirstClassSeats)==(economySeats+businessSeats+firstClassSeats);
	}

	@Override
	public boolean isFull(int seatType) {
		switch(seatType) {
		case 0:
			return (occupiedEconomySeats)==(economySeats);
		case 1:
			return (occupiedBusinessSeats)==(businessSeats);
		case 2:
			return (occupiedFirstClassSeats)==(firstClassSeats);
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return getFullness()==0;
	}

	@Override
	public double getAvailableWeight() {
		return maxWeight-weight;
	}

	@Override
	public boolean setSeats(int economy, int business, int firstClass) {
		double totalarea = economy + business*3 + firstClass*8;
		if(totalarea<=floorArea) {
			economySeats = economy;
			businessSeats = business;
			firstClassSeats = firstClass;
			return true;
		}
		return false;
	}

	@Override
	public boolean setAllEconomy() {
		if(isEmpty()) {
			economySeats = (int) floorArea;
			System.out.println("2 " +ID +" "+economySeats+" "+0+" "+0);
			businessSeats = 0;
			firstClassSeats = 0;
			return true;
		}
		return false;
	}

	@Override
	public boolean setAllBusiness() {
		if(isEmpty()) {
			businessSeats = (int) floorArea/3;
			System.out.println("2 " +ID +" "+0+" "+businessSeats+" "+0);
			economySeats = 0;
			firstClassSeats = 0;
			return true;
		}
		return false;
	}

	@Override
	public boolean setAllFirstClass() {
		if(isEmpty()) {
			firstClassSeats = (int) floorArea/8;
			System.out.println("2 " +ID +" "+0+" "+0+" "+firstClassSeats);
			businessSeats = 0;
			economySeats = 0;
			return true;
		}
		return false;
	}
	

	@Override
	public boolean setRemainingEconomy() {
		int occupiedarea = economySeats+businessSeats*3+firstClassSeats*8;
		int seatstoset = (int)(floorArea-occupiedarea);
		economySeats += seatstoset;
		System.out.println("2 " +ID +" "+seatstoset+" "+0+" "+0);
		return false;
	}

	@Override
	public boolean setRemainingBusiness() {
		int occupiedarea =  economySeats+businessSeats*3+firstClassSeats*8;
		int seatstoset = (int)((floorArea-occupiedarea)/3);
		businessSeats += seatstoset;
		System.out.println("2 " +ID +" "+0+" "+seatstoset+" "+0);
		return false;
	}

	@Override
	public boolean setRemainingFirstClass() {
		int occupiedarea =  economySeats+businessSeats*3+firstClassSeats*8;
		int seatstoset = (int)((floorArea-occupiedarea)/8);
		firstClassSeats += seatstoset;
		System.out.println("2 " +ID +" "+0+" "+0+" "+seatstoset);
		return false;
	}

	@Override
	public double getFullness() {
		try {
			return (double)(occupiedEconomySeats+occupiedBusinessSeats+occupiedFirstClassSeats)/(double)(economySeats+businessSeats+firstClassSeats);}
		catch(Exception e) {return 0;}
	}
}
