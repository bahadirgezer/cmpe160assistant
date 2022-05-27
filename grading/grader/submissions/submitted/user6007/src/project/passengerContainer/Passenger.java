package project.passengerContainer;

import java.util.*;

//import project.airlineContainer.aircraftContainer.Aircraft;
import project.airportContainer.Airport;
import project.airlineContainer.aircraftContainer.*;



public abstract class Passenger {
	protected static double[] seatMultipliers = {0.6, 1.2, 3.2};
	//below, index 0 = hub airport, 1 = major airport, 2 = regional airport for both dimensions
	protected static double[][] airportMultipliers = {{0.5, 0.7, 1},{0.6, 0.8, 1.8},{0.9, 1.6, 3.0}};
	private final long ID;
	private final double weight;
	private final int baggageCount;
	private ArrayList<Airport> destinations;
	protected double seatMultiplier;
	public double latestSeatType;
	protected double connectionMultiplier;
	protected Airport currentAirport;
	protected Airport previousAirport;
	public int passengerType;
	protected double passengerMultiplier;
	
	
	
	public Passenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		this.ID = ID;
		this.weight = weight;
		this.baggageCount = baggageCount;
		this.destinations = destinations;
		
	}
	
	
	public String toString() {
		return "[" + this.ID + "," + this.weight + "," + this.baggageCount + "," + this.currentAirport.toString() + "]";
	}
	
	
	public int getAvailableSeat(Aircraft aircraft) {
		switch(this.passengerType) {
		case 0: return 0;
		case 1: 
			if (aircraft.getAvailableBusiness() == 0)
				return 0;
			else
				return 1;
		case 2:
			if (aircraft.getAvailableFirstClass() == 0) {
				if (aircraft.getAvailableBusiness() == 0)
					return 0;
				else
					return 1;
			}
			else {
				return 2;
			}
		case 3:
			if (aircraft.getAvailableFirstClass() == 0) {
				if (aircraft.getAvailableBusiness() == 0)
					return 0;
				else
					return 1;
			}
			else {
				return 2;
			}
			
		default: break;
		}
		 return -1;
	}
	
	public double getWeight() {
		return this.weight;
	}
	
	protected int getBaggageCount() {
		return this.baggageCount;
	}
	
	/**
	 * Boards the passenger to the specified seat type, and assigns the required multiplier.
	 * Does not check the conditions regarding the seat.
	 * @param seatType
	 * @return true if boarded, false if seatType is invalid
	 */
	public boolean board(int seatType) {
		if (seatType > 2 | seatType < 0)
			return false;
		this.seatMultiplier = seatMultipliers[seatType];
		this.latestSeatType = seatType;
		//at the initial boarding, connection multiplier is set to 1
		this.connectionMultiplier = 1;
		return true;
	}
	
	public boolean canBoard(int seatType, Aircraft toAircraft) {
		if (((PassengerAircraft)toAircraft).getAvailableWeight() < this.weight) {
			return false;
		}
		if (this.getAvailableSeat(toAircraft) < 0) {
			return false;
		}
		if (toAircraft.getCurrentAirportObj().passengers.contains(this) == false) {
			return false;
		}
		return true;
		
	}
	
	/**
	 * Does the necessary disembarking operations. 
	 * @param airport the airport to disembark at
	 * @param aircraftTypeMultiplier Multiplier of the passenger
	 * @return the ticket price
	 */
	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		//check if the airport is a future destination
		if (this.destinations.indexOf(this.currentAirport) > this.destinations.indexOf(this.previousAirport)) {
			//save the disembarking airport to previous airport
			//modify the destinations to remove the past destinations
			//TODO check for list operation edge cases; i.e. valid removal
			for (int i = 0; i < destinations.indexOf(airport) + 1; i++) {
				destinations.remove(i);
			}
			this.connectionMultiplier = 1;
			//calculate the ticket price
			double ticketPrice = calculateTicketPrice(airport, aircraftTypeMultiplier);
			return ticketPrice;
		}
		else
			return 0;
	}
	
	
	/**
	 * Transfers the passenger to another plane. 
	 * @param seatType
	 * @return true if the transfer is successful
	 */
	public boolean connection(int seatType) {
		//check if the seat type is valid
		if (seatType > 2 | seatType < 0)
			return false;
		this.seatMultiplier *= seatMultipliers[seatType];
		this.latestSeatType = seatType;
		this.connectionMultiplier *= 0.8;
		return true;
	}
	abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);
	//public abstract double landAircraft(Aircraft aircraft); what the fuck


	public Airport getCurrentAirport() {
		return currentAirport;
	}


	public void setCurrentAirport(Airport currentAirport) {
		this.currentAirport = currentAirport;
	}
	
	public Airport getPreviousAirport() {
		return currentAirport;
	}


	public void setPreviousAirport(Airport previousAirport) {
		this.previousAirport = previousAirport;
	}


	public long getID() {
		return ID;
	}


	public ArrayList<Airport> getDestinations() {
		return destinations;
	}


	public double getLatestSeatType() {
		return latestSeatType;
	}


	public int getPassengerType() {
		return passengerType;
	}
	
	
	
	
}
