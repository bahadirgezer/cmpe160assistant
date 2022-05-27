package project.passenger;
import java.util.*;
import project.airport.Airport;

public abstract class Passenger {
	private final long ID;
	private final double weight;
	private final int baggageCount;
	private double seatMultiplier,connectionMultiplier;
	protected Airport currentAirport;
	protected int currentSeat;
	private ArrayList<Integer> destinations = new ArrayList<Integer>();
	public Passenger(long ID, double weight , int baggageCount, ArrayList<Integer> destinations,Airport currentAirport) {
			this.ID = ID;
			this.weight = weight;
			this.baggageCount = baggageCount;
			this.destinations = destinations;
			this.currentAirport = currentAirport;
	}
	public boolean board(int seatType) {
		connectionMultiplier = 1;
		currentSeat = seatType;
		if (seatType==0) {
			seatMultiplier = 0.6;
		}
		if (seatType==1) {
			seatMultiplier = 1.2;
		}
		if (seatType==2) {
			seatMultiplier = 3.2;
		}
		return true;
	}
	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		if (destinations.contains(airport.getID())){
			double profit = calculateTicketPrice(airport,aircraftTypeMultiplier);
			int shrinkIndex = destinations.indexOf(airport.getID());
			for (int i = 0; i < shrinkIndex; i++) {
				destinations.remove(0);
			}
			connectionMultiplier = 1;
			currentAirport = airport;
			return profit;
		}
		else {
			return 0;
		}
	}
	public boolean connection(int seatType) {
		connectionMultiplier *= 0.8;
		currentSeat = seatType;
		if (seatType==0) {
			seatMultiplier *= 0.6;
		}
		if (seatType==1) {
			seatMultiplier *= 1.2;
		}
		if (seatType==2) {
			seatMultiplier *= 3.2;
		}
		return false;
		
	}
	public long getID() {
		return ID;
	}
	public double getWeight() {
		return weight;
	}
	public ArrayList<Integer> getDestinations(){
		return destinations;
	}
	public int getBaggageCount() {
		return baggageCount;
	}
	public double getConnectionMultiplier() {
		return connectionMultiplier;
	}
	public int getCurrentSeat() {
		return currentSeat;
	}
	public double getSeatMultiplier() {
		return seatMultiplier;
	}
	public void setCurrentSeat(int seatType) {
		currentSeat = seatType;
	}
	abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);
}
