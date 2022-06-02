package project.airlinecontainer.aircraftcontainer;
import project.airportcontainer.Airport;
import project.interfacescontainer.PassengerInterface;
import project.passengercontainer.BusinessPassenger;
import project.passengercontainer.EconomyPassenger;
import project.passengercontainer.FirstClassPassenger;
import project.passengercontainer.LuxuryPassenger;
import project.passengercontainer.Passenger;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {

	
	public PassengerAircraft(double operationfee, Airport currentAirport, double weigth, double maxWeight, double floorarea, double fuelCapacity, double fuelConsumption, double aircraftTypeMultiplier) {
		super(operationfee, currentAirport, aircraftTypeMultiplier, aircraftTypeMultiplier, aircraftTypeMultiplier, aircraftTypeMultiplier, aircraftTypeMultiplier, aircraftTypeMultiplier);
		this.floorArea=floorarea;
	
	}
	
	private int economySeats, businessSeats, firstClassSeats;
	private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
	protected abstract double getaircraftTypeMultiplier();
	protected abstract double getfloorArea();
	public abstract double getFuelConsumption(double distance);
	//public abstract void set_fuels_value();
	public abstract double getfuelcapacity();
	protected double floorArea;
	
	public double loadPassenger(Passenger passenger) {
		if (passenger.getLast_disembarked_airport()==this.currentAirport) {
		if(this.getAvailableWeight()>passenger.get_passengers_weight()) {
		double seatconstant=1.0;
		if (passenger instanceof EconomyPassenger) {
			
			if(this.occupiedEconomySeats!=this.economySeats) {
			this.occupiedEconomySeats=this.occupiedEconomySeats+1;
			passenger.board(0);
			passenger.set(0);
			seatconstant=seatconstant*1.2;
			
			}
			else {
				return operationfee;
			}
		}
		if (passenger instanceof BusinessPassenger) {
			if (this.occupiedBusinessSeats!=this.businessSeats) {
			this.occupiedBusinessSeats=this.occupiedBusinessSeats+1;
			passenger.board(1);
			passenger.set(1);
			seatconstant=seatconstant*1.5;
			}
			else {
				if (this.occupiedEconomySeats!=this.economySeats) {
				this.occupiedEconomySeats=this.occupiedEconomySeats+1;
				passenger.board(0);
				passenger.set(0);
				seatconstant=seatconstant*1.2;
				}
				else {
					return operationfee;
				}
			}
			
		}
		if (passenger instanceof FirstClassPassenger) {
			if (this.occupiedFirstClassSeats!=firstClassSeats) {
			this.occupiedFirstClassSeats=this.occupiedFirstClassSeats+1;
			passenger.board(2);
			passenger.set(2);
			seatconstant=seatconstant*2.5;
			}
			if (this.occupiedBusinessSeats!=this.businessSeats) {
				this.occupiedBusinessSeats=this.occupiedBusinessSeats+1;
				passenger.board(1);
				passenger.set(1);
				seatconstant=seatconstant*1.5;
				}
				else {
					if (this.occupiedEconomySeats!=this.economySeats) {
					this.occupiedEconomySeats=this.occupiedEconomySeats+1;
					passenger.board(0);
					passenger.set(0);
					seatconstant=seatconstant*1.2;
					}
					else {
						return operationfee;
					}
				}
		}
		if (passenger instanceof LuxuryPassenger) {
			if (this.occupiedFirstClassSeats!=firstClassSeats) {
				this.occupiedFirstClassSeats=this.occupiedFirstClassSeats+1;
				passenger.board(2);
				passenger.set(2);
				seatconstant=seatconstant*2.5;
				}
				if (this.occupiedBusinessSeats!=this.businessSeats) {
					this.occupiedBusinessSeats=this.occupiedBusinessSeats+1;
					passenger.board(1);
					passenger.set(1);
					seatconstant=seatconstant*1.5;
					}
					else {
						if (this.occupiedEconomySeats!=this.economySeats) {
						this.occupiedEconomySeats=this.occupiedEconomySeats+1;
						passenger.board(0);
						passenger.set(0);
						seatconstant=seatconstant*1.2;
						}
						else {
							return operationfee;
						}
					}
			}
		
		this.weight+=passenger.get_passengers_weight();
		this.current_passenger.add(passenger);
		return operationfee*this.getaircraftTypeMultiplier()*seatconstant;
		}
		else {
			return operationfee;
		}
		}
		else {
			return operationfee;
		}
	}
	
	public double unloadPassenger(Passenger passenger) {
		if (passenger.getdestinations().contains(currentAirport)) {
			if (passenger.get_seattype()==0) {
				
				this.occupiedEconomySeats=this.occupiedEconomySeats-1;
				this.weight-=passenger.get_passengers_weight();
				this.current_passenger.remove(passenger);
				return passenger.disembark(currentAirport, this.getaircraftTypeMultiplier())*1.0;
			}
			if (passenger.get_seattype()==1) {
				this.occupiedBusinessSeats=this.occupiedBusinessSeats-1;
				this.weight-=passenger.get_passengers_weight();
				this.current_passenger.remove(passenger);
				return passenger.disembark(currentAirport, this.getaircraftTypeMultiplier())*2.8;
			}
			if (passenger.get_seattype()==2) {
				this.occupiedFirstClassSeats=this.occupiedFirstClassSeats-1;
				this.weight-=passenger.get_passengers_weight();
				this.current_passenger.remove(passenger);
				
				return passenger.disembark(currentAirport, this.getaircraftTypeMultiplier())*7.5;
			}
			
		}
		
		return this.getOperationfee();
	}
	
	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		if (toAircraft.isFull()==false) {
			if (toAircraft.canload(passenger)) {
				if (passenger.get_seattype()==0) {
					passenger.connection(0);
					this.occupiedEconomySeats=this.occupiedEconomySeats-1;
					this.weight-=passenger.get_passengers_weight();
					this.current_passenger.remove(passenger);
				}
				if (passenger.get_seattype()==1) {
					passenger.connection(1);
					this.occupiedBusinessSeats=this.occupiedBusinessSeats-1;
					this.weight-=passenger.get_passengers_weight();
					this.current_passenger.remove(passenger);
				}
				if (passenger.get_seattype()==2) {
					passenger.connection(2);
					this.occupiedFirstClassSeats=this.occupiedFirstClassSeats-1;
					this.weight-=passenger.get_passengers_weight();
					this.current_passenger.remove(passenger);
				}
				
				return toAircraft.loadPassenger(passenger);
		}
		}
		return operationfee;
	}
	
	
	
	public boolean isFull() {
		if (economySeats==occupiedEconomySeats && businessSeats==occupiedBusinessSeats && firstClassSeats==occupiedFirstClassSeats)
			return true;
		else
			return false;
	}
	public boolean isFull(int seatType) {
		if (seatType==0)
			if (economySeats==occupiedEconomySeats)
				return true;
			else
				return false;
		if (seatType==1)
			if(businessSeats==occupiedBusinessSeats)
				return true;
			else
				return false;
		else
			if(firstClassSeats==occupiedFirstClassSeats)
				return true;
			else
				return false;
	}
	public boolean isEmpty() {
		if (occupiedEconomySeats==0 && occupiedBusinessSeats==0 && occupiedFirstClassSeats==0)
			return true;
		else
			return false;
	}
	public double getAvailableWeight() {
		return this.getMaxWeight()-this.get_weight();
	}
	public boolean setSeats(int economy, int business, int firstClass) {
		this.economySeats=economy;
		this.businessSeats=business;
		this.firstClassSeats=firstClass;
		return true;
	}
	public boolean setAllEconomy() {
		int number = (int) (this.getfloorArea()/1);
		this.economySeats=number;
		this.businessSeats=0;
		this.firstClassSeats=0;
		return true;
	}
	

	public boolean setAllBusiness() {
		int number = (int) this.getfloorArea()/3;
		this.businessSeats=number;
		this.firstClassSeats=0;
		this.economySeats=0;
		return true;
	}
	public boolean setAllFirstClass() {
		int number = (int) this.getfloorArea()/8;
		this.firstClassSeats=number;
		this.businessSeats=0;
		this.economySeats=0;
		return true;
	}
	public boolean setRemainingEconomy() {
		int number= (int) this.getfloorArea() - this.economySeats -this.businessSeats-this.firstClassSeats;
		int remaining_number=(int)number/1;
		this.economySeats=remaining_number;
		return true;
	}
	public boolean setRemainingBusiness(){
		int number= (int) this.getfloorArea() - this.economySeats -this.businessSeats-this.firstClassSeats;
		int remaining_number=(int)number/3;
		this.economySeats=remaining_number;
		return true;
	}
	public boolean setRemainingFirstClass() {
		int number= (int) this.getfloorArea() - this.economySeats -this.businessSeats-this.firstClassSeats;
		int remaining_number=(int)number/8;
		this.economySeats=remaining_number;
		return true;
	}
	public double getFullness() {
		double x=(double)(economySeats+businessSeats+firstClassSeats)/(occupiedEconomySeats+occupiedBusinessSeats+occupiedFirstClassSeats);
		return x;
	}
	protected double getseatratio() {
		double x=this.businessSeats+this.firstClassSeats+this.economySeats;
		
		double y=this.occupiedBusinessSeats+this.occupiedEconomySeats+this.occupiedFirstClassSeats;
		return (double) y/x;
	}
	public boolean canload(Passenger passenger) {
		Airport real_last_disembarked_airport = passenger.getLast_disembarked_airport();
		passenger.setLast_disembarked_airport(currentAirport);
		double x =this.loadPassenger(passenger);
		if(x==operationfee) {
			this.unloadPassenger(passenger);
			passenger.setLast_disembarked_airport(real_last_disembarked_airport);
			return false;
		}
		else {
			this.unloadPassenger(passenger);
			return true;
		}
	}
	public double get_fuel() {
		return this.fuel;
	}
	
	public void add_weight_of_fuel() {
		this.weight=this.weight + this.fuelCapacity*0.7; 
	}
}

