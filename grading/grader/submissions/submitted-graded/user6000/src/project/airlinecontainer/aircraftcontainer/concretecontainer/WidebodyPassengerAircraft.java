package project.airlinecontainer.aircraftcontainer.concretecontainer;

import project.airlinecontainer.aircraftcontainer.PassengerAircraft;
import project.airportcontainer.Airport;

public class WidebodyPassengerAircraft extends PassengerAircraft {
	public WidebodyPassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport, operationFee);
		// TODO Auto-generated constructor stub
	}
	protected double weight=135000;
	protected double maxWeight=250000;
	protected double floorArea=450;
	protected double fuelCapacity=140000;
	protected double fuelConsumption=3.0;
	protected double aircraftTypeMultiplier=0.7;
	public double fuelExpenses=0;
	protected double getFlightCost(Airport toAirport) {
		double distance= Math.pow(( Math.pow((toAirport.getxPosition()-currentAirport.getxPosition()),2)+Math.pow((toAirport.getyPosition()-currentAirport.getyPosition()),2)),0.5);

		return fullnessofAircraft()*distance*0.15+this.currentAirport.departAircraft(this)+this.currentAirport.landAircraft(this);
	}
	protected double getFuelConsumption(double distance) {
		double distanceRatio=distance/14000;
		double bathtubCoefficient=25.9324*Math.pow(distanceRatio, 4) - 50.5633*Math.pow(distanceRatio, 3)  + 35.0554*Math.pow(distanceRatio, 2) - 9.90346*distanceRatio + 1.97413;
		double constant=0.1;
		double takeoffFuelConsumption=weight*constant/fuelWeight;
		double cruiseFuelConsumption=fuelConsumption*bathtubCoefficient*distance;
		return takeoffFuelConsumption+cruiseFuelConsumption;
	}
	@Override
	public boolean isFull() {
		if(this.fullnessofAircraft()==1)
		return true;
		else
			return false;
	}
	@Override
	public boolean isFull(int seatType) {
		if(seatType==0) {
			if(this.getOccupiedEconomySeats()==this.getEconomySeats())
			return true;
			else
				return false;
		}
		if(seatType==1) {
		if(this.getOccupiedBusinessSeats()==this.getBusinessSeats())
			return true;
		else
			return false;
		}
		if(seatType==2) {
			if(this.getOccupiedFirstClassSeats()==this.getFirstClassSeats())
				return true;
			else
				return false;
			}
		return true;
		
	}
	@Override
	public boolean isEmpty() {
		if(this.fullnessofAircraft()==0)
		return true;
		else
			return false;
	}
	@Override
	public double getAvailableWeight() {
		
		return 0;
	}
	@Override
	public boolean setSeats(int economy, int business, int firstClass) {
		if((this.getEconomySeats()-this.getOccupiedEconomySeats()-economy>=0) &(this.getBusinessSeats()-this.getOccupiedBusinessSeats()-business>=0)&(this.getFirstClassSeats()-this.getOccupiedFirstClassSeats()-firstClass>=0)) {
			this.setOccupiedEconomySeats(this.getOccupiedEconomySeats()+economy);
			this.setOccupiedBusinessSeats(this.getOccupiedBusinessSeats()+business);
			this.setOccupiedFirstClassSeats(this.getOccupiedFirstClassSeats()+firstClass);
			return true;
		}
		else	
			return false;
	}
	@Override
	public boolean setAllEconomy() {
		int totalNumberofSeats=3*this.getBusinessSeats()+this.getEconomySeats()+8*this.getFirstClassSeats();
	this.setBusinessSeats(0);
	this.setFirstClassSeats(0);
	this.setEconomySeats(totalNumberofSeats);
		return true;
	}
	@Override
	public boolean setAllBusiness() {
		int totalNumberofSeats=3*this.getBusinessSeats()+this.getEconomySeats()+8*this.getFirstClassSeats();
		this.setBusinessSeats(totalNumberofSeats/3);
		this.setFirstClassSeats(0);
		this.setEconomySeats(0);
			return true;
		
	}
	@Override
	public boolean setAllFirstClass() {
		int totalNumberofSeats=3*this.getBusinessSeats()+this.getEconomySeats()+8*this.getFirstClassSeats();
		this.setBusinessSeats(0);
		this.setFirstClassSeats(totalNumberofSeats/8);
		this.setEconomySeats(0);
		return false;
	}
	@Override
	public boolean setRemainingEconomy() {
		int totalNumberofRemainingSeats=3*this.getBusinessSeats()+this.getEconomySeats()+8*this.getFirstClassSeats()-3*this.getOccupiedBusinessSeats()-this.getOccupiedEconomySeats()-8*this.getOccupiedFirstClassSeats();
		this.setBusinessSeats(this.getOccupiedBusinessSeats());
		this.setFirstClassSeats(this.getOccupiedFirstClassSeats());
		this.setEconomySeats(totalNumberofRemainingSeats+this.getOccupiedEconomySeats());
		return true;
	}
	@Override
	public boolean setRemainingBusiness() {
		int totalNumberofRemainingSeats=3*this.getBusinessSeats()+this.getEconomySeats()+8*this.getFirstClassSeats()-3*this.getOccupiedBusinessSeats()-this.getOccupiedEconomySeats()-8*this.getOccupiedFirstClassSeats();
		this.setBusinessSeats(totalNumberofRemainingSeats/3+this.getOccupiedBusinessSeats());
		this.setFirstClassSeats(this.getOccupiedFirstClassSeats());
		this.setEconomySeats(this.getOccupiedEconomySeats());
		return true;
	}
	@Override
	public boolean setRemainingFirstClass() {
		int totalNumberofRemainingSeats=3*this.getBusinessSeats()+this.getEconomySeats()+8*this.getFirstClassSeats()-3*this.getOccupiedBusinessSeats()-this.getOccupiedEconomySeats()-8*this.getOccupiedFirstClassSeats();
		this.setBusinessSeats(this.getOccupiedBusinessSeats());
		this.setFirstClassSeats(totalNumberofRemainingSeats/8+this.getOccupiedFirstClassSeats());
		this.setEconomySeats(this.getOccupiedEconomySeats());
		return true;
	}
	@Override
	public double getFullness() {
		return this.fullnessofAircraft();
	
	}
	@Override
	public boolean addFuel(double fuel) {
		if((this.weight+fuel*this.fuelWeight<=this.maxWeight)&&(fuel<fuelCapacity) ) {
			this.fuel+=fuel;
			fuelExpenses+=fuel*this.currentAirport.getFuelCost();
			return true;
		}
		else
			return false;
		
	}
	@Override
	public boolean fulle() {
		if(this.weight+(this.fuelCapacity-this.fuel)*this.fuelWeight<this.maxWeight) {
			fuelExpenses+=(this.fuelCapacity-this.fuel)*this.currentAirport.getFuelCost();
			this.fuel=this.fuelCapacity;
			return true;
		}
			
		return false;
	}
	@Override
	public boolean hasFuel(double fuel) {
		if (this.fuel>=fuel)
			return true;
		else	
			return false;
	}
	@Override
	public double getWeightRatio() {
		double weightRatio=this.weight/this.maxWeight;
		return weightRatio;
	}
}
