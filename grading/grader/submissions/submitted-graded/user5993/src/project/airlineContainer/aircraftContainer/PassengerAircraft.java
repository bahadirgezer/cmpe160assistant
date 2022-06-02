package project.airlineContainer.aircraftContainer;

import java.util.ArrayList;

import project.airlineContainer.aircraftContainer.concreteContainer.JetPassengerAircraft;
import project.airlineContainer.aircraftContainer.concreteContainer.PropPassengerAircraft;
import project.airlineContainer.aircraftContainer.concreteContainer.RapidPassengerAircraft;
import project.airlineContainer.aircraftContainer.concreteContainer.WidebodyPassengerAircraft;
import project.airportContainer.Airport;
import project.passengerContainer.BusinessPassenger;
import project.passengerContainer.EconomyPassenger;
import project.passengerContainer.FirstClassPassenger;
import project.passengerContainer.LuxuryPassenger;
import project.passengerContainer.Passenger;

public abstract class PassengerAircraft extends Aircraft {
	protected double floorArea;
	private int economySeats, businessSeats, firstClassSeats;
	private int occupiedEconomySeats, occupiedBusinessSeats,occupiedFirstClassSeats;
	ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	public ArrayList<Passenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(ArrayList<Passenger> passengers) {
		this.passengers = passengers;
	}

	double aircraftTypeMultiplier;
	

	public void setOccupiedEconomySeats(int occupiedEconomySeats) {
		this.occupiedEconomySeats = occupiedEconomySeats;
	}
	public void setOccupiedBusinessSeats(int occupiedBusinessSeats) {
		this.occupiedBusinessSeats = occupiedBusinessSeats;
	}
	public void setOccupiedFirstClassSeats(int occupiedFirstClassSeats) {
		this.occupiedFirstClassSeats = occupiedFirstClassSeats;
	}
	public PassengerAircraft(Airport currentAirport, double weight, double maxWeight, double fuelWeight, double fuel,
			double fuelCapacity,int economySeats,int businessSeats, int firstClassSeats,
			int occupiedEconomySeats,int occupiedBusinessSeats,int occupiedFirstClassSeats, double floorArea,double operationFee)
	{
		super(currentAirport,  weight,  maxWeight,  fuelWeight,  fuel, fuelCapacity,operationFee);
		this.floorArea = floorArea;
		this.economySeats = economySeats;
		this.businessSeats = businessSeats;
		this.firstClassSeats = firstClassSeats;
		this.occupiedEconomySeats = occupiedEconomySeats;
		this.occupiedBusinessSeats = occupiedBusinessSeats;
		this.occupiedFirstClassSeats = occupiedFirstClassSeats;
		this.passengers = passengers;
		
	}
	protected abstract double getFuelConsumption(double distance);
	public abstract double getFlightCost(Airport toAirport);
	public int getOccupiedEconomySeats() {
		return occupiedEconomySeats;
	}
	public int getOccupiedBusinessSeats() {
		return occupiedBusinessSeats;
	}
	public int getOccupiedFirstClassSeats() {
		return occupiedFirstClassSeats;
	}
	
	int totalArea() {
		return economySeats+3*businessSeats+8*firstClassSeats;
		
	}
	public boolean canLoadPassenger(Passenger passenger) {
		double passengerWeight =passenger.getWeight();
		double maxWeight=this.getMaxWeight();
		double aircraftWeight=this.getWeight();
		if ((aircraftWeight+passengerWeight)>maxWeight ) {
			return false;
		}
		if (this.getFullness()==1 && this.totalArea()==this.floorArea) {
			return false;
		}
		
		return true;

		
	}
	
	public double loadPassenger(Passenger passenger) {
		double constant=0;
		passengers=this.passengers;
		if (passenger instanceof EconomyPassenger) {
	
			if (floorArea-this.totalArea()>=1) {

				this.occupiedEconomySeats+=1;
				this.passengers.add(passenger);
				constant=1.2;
				passenger.setSeatConstant(1);
				ArrayList<Passenger> currentPassengers=passenger.lastAirport.getPassengers();
				currentPassengers.remove(passenger);
				passenger.lastAirport.setPassengers(currentPassengers);
			}
			
		}
		else if (passenger instanceof BusinessPassenger) {
			if (floorArea-this.totalArea()>=3) {
				this.occupiedBusinessSeats+=1;
				this.passengers.add(passenger);
				constant=1.5;
				passenger.setSeatConstant(2.8);
				ArrayList<Passenger> currentPassengers=passenger.lastAirport.getPassengers();
				currentPassengers.remove(passenger);
				passenger.lastAirport.setPassengers(currentPassengers);
			}
			else if (floorArea-this.totalArea()>=1) {
				this.occupiedEconomySeats+=1;
				this.passengers.add(passenger);
				constant=1.2;
				passenger.setSeatConstant(1);
				ArrayList<Passenger> currentPassengers=passenger.lastAirport.getPassengers();
				currentPassengers.remove(passenger);
				passenger.lastAirport.setPassengers(currentPassengers);
			}
			}
		else if (passenger instanceof FirstClassPassenger) {
			if (floorArea-this.totalArea()>=8) {
				this.occupiedFirstClassSeats+=1;
				this.passengers.add(passenger);
				constant=2.5;
				passenger.setSeatConstant(7.5);
				ArrayList<Passenger> currentPassengers=passenger.lastAirport.getPassengers();
				currentPassengers.remove(passenger);
				passenger.lastAirport.setPassengers(currentPassengers);
			}
			else if (floorArea-this.totalArea()>=3){
				this.occupiedBusinessSeats+=1;
				this.passengers.add(passenger);
				constant=1.5;
				passenger.setSeatConstant(2.8);
				ArrayList<Passenger> currentPassengers=passenger.lastAirport.getPassengers();
				currentPassengers.remove(passenger);
				passenger.lastAirport.setPassengers(currentPassengers);
			}
			else if (floorArea-this.totalArea()>=1) {
				this.occupiedEconomySeats+=1;
				this.passengers.add(passenger);
				constant=1.2;
				passenger.setSeatConstant(1);
				ArrayList<Passenger> currentPassengers=passenger.lastAirport.getPassengers();
				currentPassengers.remove(passenger);
				passenger.lastAirport.setPassengers(currentPassengers);
			}
		}

		else if (passenger instanceof LuxuryPassenger) {
			if (floorArea-this.totalArea()>=8) {
				
				this.occupiedFirstClassSeats+=1;
				this.passengers.add(passenger);
				constant=2.5;
				passenger.setSeatConstant(7.5);
				ArrayList<Passenger> currentPassengers=passenger.lastAirport.getPassengers();
				currentPassengers.remove(passenger);
				passenger.lastAirport.setPassengers(currentPassengers);
			}
			else if (floorArea-this.totalArea()>=3){
				this.businessSeats+=1;
				this.occupiedBusinessSeats+=1;
				this.passengers.add(passenger);
				constant=1.5;
				passenger.setSeatConstant(2.8);
				ArrayList<Passenger> currentPassengers=passenger.lastAirport.getPassengers();
				currentPassengers.remove(passenger);
				passenger.lastAirport.setPassengers(currentPassengers);
			}
			else if (floorArea-this.totalArea()>=1) {
				
				this.occupiedEconomySeats+=1;
				this.passengers.add(passenger);
				constant=1.2;
				passenger.setSeatConstant(1);
				ArrayList<Passenger> currentPassengers=passenger.lastAirport.getPassengers();
				currentPassengers.remove(passenger);
				passenger.lastAirport.setPassengers(currentPassengers);
			}			
		}

		double operationFee=this.operationFee;

		double aircraftTypeMultiplier=this.getAircraftTypeMultiplier();
		
		if(this instanceof JetPassengerAircraft)
			aircraftTypeMultiplier=5;
		if(this instanceof PropPassengerAircraft)
			aircraftTypeMultiplier=0.9;	
		if(this instanceof RapidPassengerAircraft)	
			aircraftTypeMultiplier=1.9;	
		if(this instanceof WidebodyPassengerAircraft)
			aircraftTypeMultiplier=0.7;

		
		if (this.canLoadPassenger( passenger)) {

			return operationFee*constant*aircraftTypeMultiplier;
			
		}
		else {
			
			return operationFee;
		}	
	}
	
	public double getAircraftTypeMultiplier() {
		return aircraftTypeMultiplier;
	}
	public void setAircraftTypeMultiplier(double aircraftTypeMultiplier) {
		this.aircraftTypeMultiplier = aircraftTypeMultiplier;
	}
	public double unloadPassenger(Passenger passenger) {
		if(this instanceof JetPassengerAircraft)
			aircraftTypeMultiplier=5;
		if(this instanceof PropPassengerAircraft)
			aircraftTypeMultiplier=0.9;	
		if(this instanceof RapidPassengerAircraft)	
			aircraftTypeMultiplier=1.9;	
		if(this instanceof WidebodyPassengerAircraft)
			aircraftTypeMultiplier=0.7;
		
		double disembarkationRevenue=passenger.getTicketPrice(currentAirport,aircraftTypeMultiplier);
		Airport airport= this.currentAirport;
		double seatConstant=passenger.getSeatConstant();
		if (passenger.canDisembark(this.currentAirport)){
			if (passenger.getSeatConstant()==1) {
				occupiedEconomySeats-=1;
				economySeats-=1;
			}
			else if (passenger.getSeatConstant()==2.8) {
				occupiedBusinessSeats-=1;
				businessSeats-=1;
			}
			else if (passenger.getSeatConstant()==7.5) {
				occupiedFirstClassSeats-=1;
				firstClassSeats-=1;
			}
		ArrayList<Passenger> currentPassengers=airport.getPassengers();
		currentPassengers.add(passenger);
		airport.setPassengers(currentPassengers);


		return seatConstant*disembarkationRevenue;
		}
		else {
			return this.getOperationFee();
			
		}
	}
	
	double transferPassenger(Passenger passenger, PassengerAircraft	toAircraft) {
		double constant=0;
		passengers=this.passengers;
		
		if (passenger instanceof EconomyPassenger) {
			if (floorArea-this.totalArea()>=1) {
				
				toAircraft.occupiedEconomySeats+=1;
				toAircraft.passengers.add(passenger);
				constant=1.2;
				passenger.setSeatConstant(1);
				
			}
			
		}
		else if (passenger instanceof BusinessPassenger) {
			if (floorArea-this.totalArea()>=3) {
				
				toAircraft.occupiedBusinessSeats+=1;
				toAircraft.passengers.add(passenger);
				constant=1.5;
				passenger.setSeatConstant(2.8);
				
			}
			else if (floorArea-this.totalArea()>=1) {
				
				toAircraft.occupiedEconomySeats+=1;
				toAircraft.passengers.add(passenger);
				constant=1.2;
				passenger.setSeatConstant(1);
				
			}
			}
		else if (passenger instanceof FirstClassPassenger) {
			if (floorArea-this.totalArea()>=8) {
				
				toAircraft.occupiedFirstClassSeats+=1;
				toAircraft.passengers.add(passenger);
				constant=2.5;
				passenger.setSeatConstant(7.5);
				
			}
			else if (floorArea-this.totalArea()>=3){
				
				toAircraft.occupiedBusinessSeats+=1;
				toAircraft.passengers.add(passenger);
				constant=1.5;
				passenger.setSeatConstant(2.8);
				
			}
			else if (floorArea-this.totalArea()>=1) {
				
				toAircraft.occupiedEconomySeats+=1;
				toAircraft.passengers.add(passenger);
				constant=1.2;
				passenger.setSeatConstant(1);
			
			}
		}

		else if (passenger instanceof LuxuryPassenger) {
			if (floorArea-this.totalArea()>=8) {
				
				toAircraft.occupiedFirstClassSeats+=1;
				toAircraft.passengers.add(passenger);
				constant=2.5;
				passenger.setSeatConstant(7.5);

			}
			else if (floorArea-this.totalArea()>=3){
				
				toAircraft.occupiedBusinessSeats+=1;
				toAircraft.passengers.add(passenger);
				constant=1.5;
				passenger.setSeatConstant(2.8);
				
			}
			else if (floorArea-this.totalArea()>=1) {
				
				toAircraft.occupiedEconomySeats+=1;
				toAircraft.passengers.add(passenger);
				constant=1.2;
				passenger.setSeatConstant(1);
	
			}			
		}
		double operationFee=this.getOperationFee();
		double aircraftTypeMultiplier=toAircraft.aircraftTypeMultiplier;
		if (constant==0) {
			
			return operationFee;
		}
		else {
			
			if (passenger.getSeatConstant()==1) {
				this.occupiedEconomySeats-=1;
				this.economySeats-=1;
			}
			else if (passenger.getSeatConstant()==2.8) {
				this.occupiedBusinessSeats-=1;
				this.businessSeats-=1;
			}
			else if (passenger.getSeatConstant()==7.5) {
				this.occupiedFirstClassSeats-=1;
				this.firstClassSeats-=1;
			}
			
			return operationFee*constant*aircraftTypeMultiplier;
		}	
		
		
		
	}
	boolean isFull() {
		if( (this.economySeats==this.occupiedEconomySeats) && (this.businessSeats==this.occupiedBusinessSeats) && (this.firstClassSeats==this.occupiedFirstClassSeats) && (this.totalArea()==this.floorArea))
		{
			return true;
		}
		else {
			return false;
		}
	}
	
	boolean isFull(int seatType) {
		if (seatType==0 && this.economySeats==this.occupiedEconomySeats) {
			return true;
		}
		if (seatType==1 && this.businessSeats==this.occupiedBusinessSeats) {
			return true;
		}
		if (seatType==2 && this.firstClassSeats==this.occupiedFirstClassSeats) {
			return true;
		}
		return false;
	}
	boolean isEmpty() {
		return ((this.occupiedEconomySeats+this.occupiedBusinessSeats+this.occupiedFirstClassSeats)==0);
		
	}
	public double getAvailableWeight() {
		double aircraftWeight=this.weight;
		double aircraftMaxWeight=this.maxWeight;
		double totalPassengerWeight=0;
		for (Passenger passenger :passengers) {
			totalPassengerWeight+=passenger.getWeight();
		}
		return aircraftMaxWeight-aircraftWeight-totalPassengerWeight;
	}
	public boolean setSeats(int economy, int business, int firstClass) {
		if (economy+3*business+8*firstClass<=this.getFloorArea()) {
			this.setEconomySeats(economy);
			this.setBusinessSeats(business);
			this.setFirstClassSeats(firstClass);
			return true;
		}
		return false;
	}
	public int getFloorArea() {
		return (int) floorArea;
	}
	public void setFloorArea(double floorArea) {
		this.floorArea = floorArea;
	}
	public boolean setAllEconomy() {
		boolean bool= this.setSeats(this.getFloorArea(), 0, 0) ;
		return bool;
	}
	public boolean setAllBusiness() {
		boolean bool= this.setSeats(0, this.getFloorArea()/3, 0);
		return bool;
	}
	public boolean setAllFirstClass() {
		boolean bool= this.setSeats(0, 0, this.getFloorArea()/8);
		return bool;
	}
	public boolean setRemainingEconomy() {
		boolean bool= this.setSeats(this.getFloorArea()-3*this.getBusinessSeats()-8*this.getFirstClassSeats(),this.getBusinessSeats(), this.getFirstClassSeats());
		return bool;
		
	}
	public boolean setRemainingBusiness() {
		boolean bool= this.setSeats(this.getEconomySeats(),this.getFloorArea()-this.getEconomySeats()-this.getFirstClassSeats()*8, this.getFirstClassSeats());
		return bool;
		
	}
	public boolean setRemainingFirstClass() {
		boolean bool= this.setSeats(this.getBusinessSeats(),this.getBusinessSeats(), this.getFloorArea()-this.getEconomySeats()-3*this.getBusinessSeats());
		return bool;
	}
	public double getFullness() {
		return (double) (this.getOccupiedEconomySeats()+this.getOccupiedBusinessSeats()+this.getOccupiedFirstClassSeats())/(this.getEconomySeats()+this.getBusinessSeats()+this.getFirstClassSeats());
	}
	
	

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
	
	
	
	
	

	
	
	

}
