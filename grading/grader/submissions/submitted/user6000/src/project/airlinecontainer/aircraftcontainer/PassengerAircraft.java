package project.airlinecontainer.aircraftcontainer;

import project.interfacescontainer.PassengerInterface;
import project.airlinecontainer.aircraftcontainer.concretecontainer.*;
import project.airportcontainer.Airport;
import project.passengercontainer.BusinessPassenger;
import project.passengercontainer.EconomyPassenger;
import project.passengercontainer.FirstClassPassenger;
import project.passengercontainer.LuxuryPassenger;
import project.passengercontainer.Passenger;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface{
	public PassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport, operationFee);
	
	}
	protected double floorArea;
	private int economySeats, businessSeats, firstClassSeats;
	private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
	protected double aircraftTypeMultiplier;
	
	public int getEconomySeats() {
		return economySeats;
	}
	public void setEconomySeats(int economySeats) {
		this.economySeats = economySeats;
	}
	public int getBusinessSeats() {
		return businessSeats;
	}
	public void setBusinessSeats(int businessSeats) {
		this.businessSeats = businessSeats;
	}
	public int getFirstClassSeats() {
		return firstClassSeats;
	}
	public void setFirstClassSeats(int firstClassSeats) {
		this.firstClassSeats = firstClassSeats;
	}
	public int getOccupiedEconomySeats() {
		return occupiedEconomySeats;
	}
	public void setOccupiedEconomySeats(int occupiedEconomySeats) {
		this.occupiedEconomySeats = occupiedEconomySeats;
	}
	public int getOccupiedBusinessSeats() {
		return occupiedBusinessSeats;
	}
	public void setOccupiedBusinessSeats(int occupiedBusinessSeats) {
		this.occupiedBusinessSeats = occupiedBusinessSeats;
	}
	public int getOccupiedFirstClassSeats() {
		return occupiedFirstClassSeats;
	}
	public void setOccupiedFirstClassSeats(int occupiedFirstClassSeats) {
		this.occupiedFirstClassSeats = occupiedFirstClassSeats;
	}
	public double fullnessofAircraft() {
		return (double)(occupiedEconomySeats+occupiedBusinessSeats+occupiedFirstClassSeats)/(economySeats+businessSeats+firstClassSeats);
	}
	public double loadPassenger(Passenger passenger) {
		double constant=0;
		if(passenger instanceof LuxuryPassenger) {
			if(occupiedFirstClassSeats<firstClassSeats  ) {
			
			occupiedFirstClassSeats++;
			this.listofPassengers.add(passenger);
			passenger.seatType=2;
		}
			else{
				if(occupiedBusinessSeats<businessSeats) {
				occupiedBusinessSeats++;	
				this.listofPassengers.add(passenger);
				passenger.seatType=1;
				}
				else {
					if(occupiedEconomySeats<economySeats) {
						occupiedEconomySeats++;
						this.listofPassengers.add(passenger);
						passenger.seatType=0;
					}
				}
			}
		}
		if(passenger instanceof FirstClassPassenger) {
			if(occupiedFirstClassSeats<firstClassSeats  ) {
			occupiedFirstClassSeats++;
			this.listofPassengers.add(passenger);
			passenger.seatType=2;
		}
			else{
				if(occupiedBusinessSeats<businessSeats) {
				occupiedBusinessSeats++;	
				this.listofPassengers.add(passenger);
				passenger.seatType=1;
				}
				else {
					if(occupiedEconomySeats<economySeats) {
						occupiedEconomySeats++;
						this.listofPassengers.add(passenger);
						passenger.seatType=0;
					}
				}
			}
		}
		
		if(passenger instanceof BusinessPassenger) {
			if(occupiedBusinessSeats<businessSeats) {
				occupiedBusinessSeats++;	
				this.listofPassengers.add(passenger);
				passenger.seatType=1;
				}
			else {
					if(occupiedEconomySeats<economySeats) {
						occupiedEconomySeats++;
						this.listofPassengers.add(passenger);
						passenger.seatType=0;
					}
		}
		}
		if(passenger instanceof EconomyPassenger) {
			if(occupiedEconomySeats<economySeats) {
				occupiedEconomySeats++;
				this.listofPassengers.add(passenger);
				passenger.seatType=0;
		}
		}
		if((passenger instanceof LuxuryPassenger) & (occupiedFirstClassSeats<firstClassSeats  )) {
			occupiedFirstClassSeats++;
			this.listofPassengers.add(passenger);
		}
	
	if(passenger.seatType==0)
		constant=1.2 ;
	if(passenger.seatType==1)
		constant=1.5 ;
	if(passenger.seatType==2)
		constant=2.5 ;
	if(this instanceof JetPassengerAircraft)
		aircraftTypeMultiplier=5;
	if(this instanceof PropPassengerAircraft)
		aircraftTypeMultiplier=0.9;	
	if(this instanceof RapidPassengerAircraft)	
		aircraftTypeMultiplier=1.9;	
	if(this instanceof WidebodyPassengerAircraft)
		aircraftTypeMultiplier=0.7;		
	if(constant!=0)
	return currentAirport.getoperationFee()*constant*this.aircraftTypeMultiplier;
	else
		return currentAirport.getoperationFee();
	}
	public double unloadPassenger(Passenger passenger) {
		double unloadFee=passenger.disembark(currentAirport,aircraftTypeMultiplier);
		if (unloadFee==0)
			return -1*this.operationFee;
		else {
			double seatConstant=0;
		if(passenger.seatType==0) {
			occupiedEconomySeats--;
			this.listofPassengers.remove(passenger);
			seatConstant=1.0;
		}
		if(passenger.seatType==1) {
			occupiedBusinessSeats--;
			this.listofPassengers.remove(passenger);
			seatConstant=2.8;
		}
		if(passenger.seatType==2) {
			occupiedFirstClassSeats--;
			this.listofPassengers.remove(passenger);
			seatConstant=7.5;
		}
		
		return seatConstant*unloadFee;
		}
	}
	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		passenger.connection(passenger.seatType);
		if(passenger.seatType==0) {
			occupiedEconomySeats--;
			this.listofPassengers.remove(passenger);
			
		}
		if(passenger.seatType==1) {
			occupiedBusinessSeats--;
			this.listofPassengers.remove(passenger);
			
		}
		if(passenger.seatType==2) {
			occupiedFirstClassSeats--;
			this.listofPassengers.remove(passenger);
			
		}
		
		passenger.currentAircraft=toAircraft;
		  return toAircraft.loadPassenger(passenger);
		
		
		
	}
	
}
