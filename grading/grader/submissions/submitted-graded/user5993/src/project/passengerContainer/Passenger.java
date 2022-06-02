package project.passengerContainer;

import java.util.ArrayList;

import project.airlineContainer.aircraftContainer.Aircraft;
import project.airlineContainer.aircraftContainer.PassengerAircraft;
import project.airportContainer.Airport;
import project.airportContainer.HubAirport;
import project.airportContainer.MajorAirport;
import project.airportContainer.RegionalAirport;

public abstract class Passenger {
	private final long ID;
	private final double weight;
	private final int baggageCount;
	private ArrayList<Airport> destinations;
	
	public Airport lastAirport;
	public ArrayList<Airport> getDestinations() {
		return destinations;
	}
	public void setDestinations(ArrayList<Airport> destinations) {
		this.destinations = destinations;
	}
	public double connectionMultiplier=1;
	private double seatConstant;
	private int seatType;
	PassengerAircraft aircraftOfPassenger;
	private double seatMultiplier;
	
	public int getSeatType() {
		return seatType;
	}
	public void setSeatType(int seatType) {
		this.seatType = seatType;
	}
	public double getSeatConstant() {
		return seatConstant;
	}
	public void setSeatConstant(double seatConstant) {
		this.seatConstant = seatConstant;
	}
	public Passenger(long ID,double weight, int baggageCount,ArrayList<Airport> destinations ) {
		this.ID=ID;
		this.weight=weight;
		this.baggageCount=baggageCount;
		this.destinations=destinations;
	}
	public double getWeight() {
		return weight;
	}
	public int getBaggageCount() {
		return baggageCount;
	}
	public double calculateAirportMultiplier(Airport toAirport) {
		if (lastAirport instanceof HubAirport) {
			if (toAirport instanceof HubAirport ) {
				return 0.5;
			}
			else if(toAirport instanceof MajorAirport) {
				return 0.7;
			}
			else if(toAirport instanceof RegionalAirport) {
				return 1.0;
			}		
		}
		else if (lastAirport instanceof MajorAirport) {
			if (toAirport instanceof HubAirport ) {
				return 0.6;
			}
			else if(toAirport instanceof MajorAirport) {
				return 0.8;
			}
			else if(toAirport instanceof RegionalAirport) {
				return 1.8;
			}	
		}
		else if (lastAirport instanceof RegionalAirport) {
				if (toAirport instanceof HubAirport ) {
					return 0.9;
				}
				else if(toAirport instanceof MajorAirport) {
					return 1.6;
				}
				else if(toAirport instanceof RegionalAirport) {
					return 3.0;
				}		
														}
		return 0.0;
	}
	
	public boolean canDisembark(Airport airport){
		if(this.destinations.indexOf(airport)>this.destinations.indexOf(this.lastAirport)) {
			return true;
		} 
		else {
			return false;
		}	
	}
	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		if (this.canDisembark(airport)) {
			for (Aircraft aircraft : airport.aircrafts) {
				if (aircraft.getPassengers().contains(this)) {
					this.aircraftOfPassenger= (PassengerAircraft) aircraft;
				}
			}
			airport.getPassengers().add(this);
			aircraftOfPassenger.setWeight(aircraftOfPassenger.getWeight()-this.weight);
			ArrayList<Passenger> currentPassengers=aircraftOfPassenger.getPassengers();
			currentPassengers.remove(this);
			aircraftOfPassenger.setPassengers(currentPassengers);
			if (this.getSeatType()==0) {
				aircraftOfPassenger.setEconomySeats(aircraftOfPassenger.getEconomySeats()-1);
				aircraftOfPassenger.setOccupiedEconomySeats(aircraftOfPassenger.getOccupiedEconomySeats()-1);
			}
			if (this.getSeatType()==1) {
				aircraftOfPassenger.setBusinessSeats(aircraftOfPassenger.getBusinessSeats()-1);
				aircraftOfPassenger.setOccupiedBusinessSeats(aircraftOfPassenger.getOccupiedBusinessSeats()-1);
			}
			if (this.getSeatType()==2) {
				aircraftOfPassenger.setFirstClassSeats(aircraftOfPassenger.getFirstClassSeats()-1);
				aircraftOfPassenger.setOccupiedFirstClassSeats(aircraftOfPassenger.getOccupiedFirstClassSeats()-1);
			}
			connectionMultiplier=1;
			return calculateTicketPrice(airport,aircraftTypeMultiplier);
			}
			
		else {
			return 0;
		}
	}
	public boolean board(int seatType) {
		if (this.getSeatType()==0) {
			this.setSeatMultiplier(0.6);
		}
		if (this.getSeatType()==1) {
			this.setSeatMultiplier(1.2);
			
		}
		if (this.getSeatType()==2) {
			this.setSeatMultiplier(3.2);
		}
		return true;
		
		
	}
	public boolean connection(int seatType) {
		if (this.getSeatType()==0) {
			this.setSeatMultiplier(this.getSeatMultiplier()*0.6);
		}
		if (this.getSeatType()==1) {
			this.setSeatMultiplier(this.getSeatMultiplier()*1.2);
			
		}
		if (this.getSeatType()==2) {
			this.setSeatMultiplier(this.getSeatMultiplier()*3.2);
		}
		connectionMultiplier*=0.8;
		return true;
	}
	
	
	abstract double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier);
	public double getSeatMultiplier() {
		return seatMultiplier;
	}
	public void setSeatMultiplier(double seatMultiplier) {
		this.seatMultiplier = seatMultiplier;
	}
	public int getPassengerType() {
		if (this instanceof EconomyPassenger ) {
			return 0;
		}
		if (this instanceof BusinessPassenger ) {
			return 1;
		}
		if (this instanceof FirstClassPassenger ) {
			return 2;
		}
		if (this instanceof LuxuryPassenger ) {
			return 3;
		}
		return 0;
		
		
	}
	public long getID() {
		return ID;
	}
	public double getTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		return this.calculateTicketPrice( toAirport,  aircraftTypeMultiplier);
	}
	
	
	
	

}
