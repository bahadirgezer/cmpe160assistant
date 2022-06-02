package project.passenger_container;

import java.util.ArrayList;

import project.airport_container.Airport;
import project.airport_container.HubAirport;
import project.airport_container.MajorAirport;

public abstract class Passenger {
	private final long ID;
	private final double weight;
	private final int baggageCount;
	private ArrayList<Airport> destinations;
	public ArrayList<Airport> getDestinations() {
		return destinations;
	}
	private double seatMultiplier=1;
	private int seatType;
	private double connectionMultiplier=1;
	private Airport prevlocation;
	private int passtype;
	Passenger(int p,long a,double b,int c,ArrayList<Airport> d){
		this.passtype = p;
		this.ID=a;
		this.weight =b;
		this.baggageCount =c;
		this.destinations = d;
		this.prevlocation = destinations.get(0);
		destinations.get(0).addPass(this);
		destinations.remove(0);
	}
	public boolean board(int seatType) {
		this.seatType=seatType-1;
		this.setSeatMultiplier(seatType);
		return true;
	}
	public boolean connection(int seatType) {
		board(seatType);
		setConnectionMultiplier(getConnectionMultiplier() * 0.8);
		return true;
	}
	public boolean can_disembark(Airport airport) {
		if(destinations.contains(airport)) {
			return true;
		}else {
			return false;
		}}
	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		if(destinations.contains(airport)) {
//			ArrayList<Airport> dest = new ArrayList<Airport>();
//			for(int i=destinations.indexOf(airport);i<destinations.size();i++) {
//				dest.add(destinations.get(i));
//			}
		double price = calculateTicketPrice(airport,aircraftTypeMultiplier);
		ArrayList<Airport> new_destinations = new ArrayList<>();
		for(int i =destinations.indexOf(airport);i<destinations.size();i++) {
			new_destinations.add(destinations.get(i));
			
		}
		destinations = new_destinations;
		this.prevlocation = airport;
		return price;
		}else {
		return 0;
		}
	}
	abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);
	protected double calculateAirportMultiplier(Airport toAirport) {
		if(getPrevlocation() instanceof HubAirport) {
			if(toAirport instanceof HubAirport) {
				return 0.5;
			}
			else if(toAirport instanceof MajorAirport) {
				return 0.7;
			}
			else{
				return 1.0;
			}
			
		}
		else if(getPrevlocation() instanceof MajorAirport) {
			if(toAirport instanceof HubAirport) {
				return 0.6;
			}
			else if(toAirport instanceof MajorAirport) {
				return 0.8;
			}
			else{
				return 1.8;
			}
		}
		else{
			if(toAirport instanceof HubAirport) {
				return 0.9;
			}
			else if(toAirport instanceof MajorAirport) {
				return 1.6;
			}
			else{
				return 3.0;
			}
		
		}
		
	}
	public double getSeatMultiplier() {
		return seatMultiplier;
	}
	public void setSeatMultiplier(double seatMultiplier) {
		if(seatMultiplier == 1) {
		this.seatMultiplier *= 0.6;
		}
		if(seatMultiplier == 2) {
			this.seatMultiplier *= 1.2;
			}
		if(seatMultiplier == 3) {
			this.seatMultiplier *= 3.2;
			}
		
	}
	public Airport getPrevlocation() {
		return prevlocation;
	}
	public double getConnectionMultiplier() {
		return connectionMultiplier;
	}
	public void setConnectionMultiplier(double connectionMultiplier) {
		this.connectionMultiplier = connectionMultiplier;
	}
	public int getBaggageCount() {
		return baggageCount;
	}
	public double getWeight() {
		return weight;
	}
	public long getID() {
		return ID;
	}
	public int getSeatType() {
		return seatType;
	}
	public int getPasstype() {
		return passtype;
	}
}
