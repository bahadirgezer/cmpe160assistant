package project.passengercontainer;

import java.util.ArrayList;
import java.util.Set;

import project.airportcontainer.Airport;

public abstract class Passenger{
	private final long ID;
	private final double weight;
	private final int baggageCount;
	private ArrayList<Airport> destinations;   //used to see if the passenger can disembark
	protected Airport currentAirport;
	protected double connectionMultiplier=1;
	protected double seatMultiplier;

	public int boardedSeat;

	
	
	public Passenger(long ID,double weight, int baggageCount,ArrayList<Airport> destinations) {
		this.ID=ID;
		this.weight=weight;
		this.baggageCount=baggageCount;
		this.destinations=destinations;
		this.currentAirport = destinations.get(0);
	}
	
	
	public boolean board(int seatType) {
		if (getAvailableSeats().contains(seatType)){
			if (seatType==1) {
				this.seatMultiplier=0.6;
				boardedSeat = 1;
				return true;
			}
			if (seatType==2) {
				this.seatMultiplier=1.2;
				boardedSeat = 2;

				return true;
			}
			if (seatType==3) {
				this.seatMultiplier=3.2;
				boardedSeat = 3;
				return true;
			}
		}

		return false;

	}
	
	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		//define seatMultiplier
		//disembarks the passenger returns the ticket price
		//ticket money is calculated and returned
		//return 0 if airport is not a future destination if not, call calculateTicketPrice() and return it
		if (destinations.contains(airport) && destinations.indexOf(currentAirport) < destinations.indexOf(airport)) {
			double ticketPrice=calculateTicketPrice(airport,aircraftTypeMultiplier) * this.seatMultiplier;
			currentAirport = airport;
			destinations.remove(airport);
			return ticketPrice;
		}
		return 0;

	}
	
	public boolean connection(int seatType) {
		//transfers passengers to another plane
		//seat multiplier is multiplied again
		//define connectionMultiplier
		return false;
	}
	
	abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);
		//used in the disembark method
	
	public long getID() {
		return ID;
	}
	
	public int getBaggageCount() {
		return baggageCount;
	}
	
	public double getWeight() {
		return weight;
	}
	public Airport getCurrentAirport() {
		return currentAirport;
	}


	public abstract ArrayList<Integer> getAvailableSeats();
	
	
}
