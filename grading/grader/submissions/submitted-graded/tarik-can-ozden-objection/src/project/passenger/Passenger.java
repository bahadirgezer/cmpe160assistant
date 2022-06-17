package project.passenger;

import java.util.ArrayList;
import java.util.Objects;

import project.airline.aircraft.Aircraft;
import project.airport.Airport;

public abstract class Passenger {

	private final long ID;
	private final double weight;
	private final int baggageCount;
	private ArrayList<Airport> destinations;
	private ArrayList<Integer> destinationIDs;
	private int currentAirportInd;
	private Airport currentAirport;
	private double connectionMultiplier = 1;
	private double seatMultiplier = 0.6;
	private int passengerType;
	private int isLoaded = 0;
	


	public Passenger(long ID, double weight, int baggageCount, int currentAirportInd) {
		this.ID = ID;
		this.weight = weight;
		this.baggageCount = baggageCount;
		this.currentAirportInd = currentAirportInd;
		destinations = new ArrayList<Airport>();
		destinationIDs = new ArrayList<Integer>();
	}
	
	
	public ArrayList<Integer> getDestinationIDs() {
		return destinationIDs;
	}


	public void setDestinationIDs(ArrayList<Integer> destinationIDs) {
		this.destinationIDs = destinationIDs;
	}

	public int getIsLoaded() {
		return isLoaded;
	}


	public void setIsLoaded(int isLoaded) {
		this.isLoaded = isLoaded;
	}


	public int getDestSize() {
		return this.getDestinationIDs().size();
	}

	public Airport getCurrentAirport() {
		return currentAirport;
	}


	public void setCurrentAirport(Airport currentAirport) {
		this.currentAirport = currentAirport;
	}


	public int getPassengerType() {
		return passengerType;
	}


	public void setPassengerType(int passengerType) {
		this.passengerType = passengerType;
	}


	public ArrayList<Airport> getDestinations() {
		return destinations;
	}

	public double getConnectionMultiplier() {
		return connectionMultiplier;
	}

	public void setDestinations(ArrayList<Airport> destinations) {
		this.destinations = destinations;
	}
	public Airport getDestinationsWithInd(int i) {
		return destinations.get(i);
	}


	public void setDestinationsWithInd(int i, Airport arp) {
		destinations.set(i, arp);
	}

	public int getDestinationIDsWithInd(int i) {
		return destinationIDs.get(i);
	}


	public void setDestinationIDsWithInd(int i, int ID) {
		destinationIDs.set(i, ID);
	}
	
	public int getCurrentAirportInd() {
		return currentAirportInd;
	}


	public void setCurrentAirportInd(int currentAirportInd) {
		this.currentAirportInd = currentAirportInd;
	}


	public long getID() {
		return ID;
	}


	public double getWeight() {
		return weight;
	}
	public int getBaggageCount() {
		return baggageCount;
	}


	public boolean board(int seatType) {
		if (seatType == 1) seatMultiplier = 0.6; // 1 econ, 2 bus, 3 first-class
		else if (seatType == 2) seatMultiplier = 1.2;
		else seatMultiplier = 0.6;
		
		return true;
	}
	public boolean disembarkCheck(Airport airport) {
		boolean flag = false;
		for(int i = this.getCurrentAirportInd() + 1; i < this.getDestinations().size(); i++)  {
			if(Objects.equals(this.getDestinationsWithInd(i),airport)) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	public boolean loadCheck(Aircraft aircraft) {
		if(this.getWeight() + aircraft.getWeight() <= aircraft.getMaxWeight()) return true;
		else return false;
	}
	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		boolean flag = false;
		int nextInd = 0;
		for(int i = this.getCurrentAirportInd() + 1; i < this.getDestinations().size(); i++)  {
			if(Objects.equals(this.getDestinationsWithInd(i) ,airport)) {
				flag = true;
				nextInd = i;
				break;
			}
		}
		if(flag == false) {
			return 0;
		} else {
			
			double ticketPrice = calculateTicketPrice(airport,aircraftTypeMultiplier) * seatMultiplier;
			seatMultiplier = 0.6;
			connectionMultiplier = 1;
			this.setCurrentAirportInd(nextInd);
			this.setCurrentAirport(this.getDestinationsWithInd(nextInd));
			return ticketPrice; 
		}

	}
	public boolean connection(int seatType) {
		connectionMultiplier *= 0.8;
		if(seatType == 1) seatMultiplier *= 0.6;
		else if (seatType == 2) seatMultiplier *= 1.2;
		else seatMultiplier *= 3.2;
		
		return true;
	}
	
	protected double calculateAirportMultiplier(Airport currentAirport, Airport toAirport) {
		double multiplier = 0.01; // for check purposes.
		int portType1 = currentAirport.getPortType(), portType2= toAirport.getPortType(); // 0 for h, 1 for m, 2 for r 
		if(portType1 == 1 && portType2 == 1) multiplier = 0.5;
		else if (portType2 == 2 && portType1 == 1) multiplier = 0.7;
		else if (portType2 == 3 && portType1 == 1) multiplier = 1.0;
		else if (portType2 == 1 && portType1 == 2) multiplier = 0.6;
		else if (portType2 == 2 && portType1 == 2) multiplier = 0.8;
		else if (portType2 == 3 && portType1 == 2) multiplier = 1.8;
		else if (portType2 == 1 && portType1 == 3) multiplier = 0.9;
		else if (portType2 == 2 && portType1 == 3) multiplier = 1.6;
		else if (portType2 == 3 && portType1 == 3) multiplier = 3.0;
		return multiplier;
	}
	
	public abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);

	

}
