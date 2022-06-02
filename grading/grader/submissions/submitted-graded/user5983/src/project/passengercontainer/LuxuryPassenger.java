package project.passengercontainer;

import java.util.ArrayList;
import java.util.Arrays;

import project.airportcontainer.Airport;

public class LuxuryPassenger extends Passenger {
	
	private double passengerMultiplier=15;
	public ArrayList<Integer> seats= new ArrayList<Integer>(Arrays.asList(1,2,3));


	public LuxuryPassenger(long ID,double weight, int baggageCount,ArrayList<Airport> destinations) {
		super(ID, weight, baggageCount, destinations);
	}

	@Override
	double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier) {
		// TODO Auto-generated method stub
		double airportMultiplier=1;
		if (currentAirport.getType()==1) {
			if (airport.getType()==1) {
				 airportMultiplier=0.5;
			}
			else if (airport.getType()==2) {
				airportMultiplier=0.7;
			}
			else if (airport.getType()==3) {
				 airportMultiplier=1;
			}
		}
		else if (currentAirport.getType()==2) {
			if (airport.getType()==2) {
				 airportMultiplier=0.8;
			}
			else if (airport.getType()==1) {
				 airportMultiplier=0.6;
			}
			else if (airport.getType()==3) {
				 airportMultiplier=1.8;
			}
		}
		else if (currentAirport.getType()==3) {
			if (airport.getType()==3) {
				 airportMultiplier=3;
			}
			else if (airport.getType()==1) {
				 airportMultiplier=0.9;
			}
			else if (airport.getType()==2) {
				 airportMultiplier=1.6;
			}
		}
		double totalPrice= currentAirport.getDistance(airport)*aircraftTypeMultiplier*connectionMultiplier*airportMultiplier*passengerMultiplier;
		//calculates and returns the ticket price
		//airport multiplier at which passenger disembarked, then passenger multiplier=0.6
		return totalPrice+(totalPrice*getBaggageCount()/20);
	}

	public ArrayList<Integer> getAvailableSeats(){
		return this.seats;
	};

}
