package project.passengercontainer;

import java.util.ArrayList;
import project.airlinecontainer.aircraftcontainer.PassengerAircraft;
import project.airlinecontainer.aircraftcontainer.Aircraft;
import project.airportcontainer.Airport;

public abstract class Passenger {
	private final long ID;
	private final double weight;
	private final int baggageCount;
	private ArrayList<Airport> destinations;
	public Airport currentAirport;
	double connectionMultiplier=1;
	public int seatType;
	public double seatMultiplier;
	public Aircraft currentAircraft;
	
	public ArrayList<Airport> publicDestinations() {
		return destinations;
	}
	
	
	public int countofBaggage() {
		return baggageCount;
	
	
	}
	public Passenger(long ID, double weight, int baggageCount, ArrayList destinations) {
		this.ID=ID;
		this.weight=weight;
		this.baggageCount=baggageCount;
		this.destinations=destinations;
	}
	public boolean board(int seatType) {
		if(seatType==0 )
			seatMultiplier=0.6;
		if(seatType==1)
			seatMultiplier=1.2;
		if(seatType==2)
			seatMultiplier=3.2;
		return true;
	}
	
	
	
	abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier) ;
	public double resultofTicketPrice(Airport currentairport, double aircraftTypeMultiplier) {
		
		
		return calculateTicketPrice(currentAirport,aircraftTypeMultiplier);
	}
	
	
	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		if(this.destinations.contains(airport)) {
			int index=destinations.indexOf(airport);
			for(int i=0 ;i<=index;i++) {
				destinations.remove(0);
			}
			
			
			double ticketPrice=this.calculateTicketPrice(airport,aircraftTypeMultiplier);
			return ticketPrice;
		}
		else
			return 0;
		
	}
	public boolean connection(int seatType) {
		
	
		if(seatType==0 )
			seatMultiplier=0.6;
		if(seatType==1)
			seatMultiplier=1.2;
		if(seatType==2)
			seatMultiplier=3.2;
		 
		connectionMultiplier*=0.8;
		return true;
	}
	
		
	
	
}
