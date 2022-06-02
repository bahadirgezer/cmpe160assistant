package project.passengercontainer;
import java.util.ArrayList;

import project.airportcontainer.Airport;

public abstract class Passenger {
	public 	Passenger(long id,double weight,int baggageCount, ArrayList<Airport> destinations) {
		this.ID=id;
		this.weight=weight;
		this.baggageCount=baggageCount;
		this.destinations=destinations;
		this.setLast_disembarked_airport(destinations.get(0));
	}
	private final long ID;
	private final double weight;
	private final int baggageCount;
	private ArrayList<Airport> destinations;
	protected Airport next_destination;
	protected Airport last_disembarked_airport;
	protected double connectionMultiplier=1.0;
	protected double seatMultiplier=1.0;
	abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);
	protected int current_seat_type;
	

	public boolean board(int seatType) {
		if (seatType==0) {
			seatMultiplier=seatMultiplier*0.6;
		}
		if (seatType==1) {
			seatMultiplier=seatMultiplier*1.2;
		}
		if (seatType==2) {
			seatMultiplier=seatMultiplier*3.2;
		}
		return true;
	}
	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		if (destinations.contains(airport)) {
			int x=destinations.indexOf((Object)airport);
			for (int i=0;i<x;i++) {
				destinations.remove(i);	
			}
		return calculateTicketPrice(airport,aircraftTypeMultiplier);
		}
		else {
			return 0;
		}
	}
	
	public boolean connection(int seatType) {
		if (seatType==0) {
			seatMultiplier=seatMultiplier*0.6;
		}
		if (seatType==1) {
			seatMultiplier=seatMultiplier*1.2;
		}
		if (seatType==2) {
			seatMultiplier=seatMultiplier*3.2;
		}
		connectionMultiplier=connectionMultiplier*0.8;
		return true;
	}
	public Airport getLast_disembarked_airport() {
		return last_disembarked_airport;
	}
	public void setLast_disembarked_airport(Airport last_disembarked_airport) {
		this.last_disembarked_airport = last_disembarked_airport;
	}
	protected int getbaggageCount() {
		return this.baggageCount;
	}
	public ArrayList<Airport> getdestinations() {
		return this.destinations;	
	}
	public void set(int current_seat_type) {
		this.current_seat_type=current_seat_type;
	}
	public int get_seattype() {
		return this.current_seat_type;
	}
	public double get_passengers_weight() {
		return this.weight;
	}
	public long get_id() {
		return this.ID;
	}
}
