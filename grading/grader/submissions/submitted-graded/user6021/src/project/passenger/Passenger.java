package project.passenger;
import java.util.ArrayList;
import project.airport.Airport;

public abstract class Passenger {
	private final Long ID;
	private final double weight;
	private final int baggageCount;
	private ArrayList<Integer> destinations;
	protected int currAirportIndex;
	protected double seatMultiplier=1;
	private int type;//0- economy , 1- business, 2- first class;
	protected double connectionMultiplier = 1;



	public Passenger(Long ID, double weight, int baggageCount, ArrayList<Integer> destinations, int type) {
		this.ID = ID;
		this.weight = weight;
		this.baggageCount = baggageCount;
		this.destinations = destinations;
		this.currAirportIndex=destinations.get(0);
		this.type = type;
	}
	
	
	public boolean board(int seatType) {//0- economy , 1- business, 2- first class
		if (seatType ==1) {
			this.seatMultiplier = 1.2;
		}
		else if (seatType ==0) {
			this.seatMultiplier = 0.6;
		}
		else {
			this.seatMultiplier = 3.2;
		}
		return true;
	}
	
	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		if (!this.isNotDestination(airport.getID())) {
			double ticketPrice = this.calculateTicketPrice(airport, aircraftTypeMultiplier);
			this.currAirportIndex = airport.getID();
			this.connectionMultiplier = 1;
			this.seatMultiplier = 1;
			int lastIndex = this.destinations.indexOf(airport.getID());
			this.destinations.subList(0, lastIndex).clear();
			if (this.destinations.size()<=1) {
				this.destinations.add(-1);
			}
			return ticketPrice;
		}
		else {
			return 0;
		}
	}
	
	public boolean connection(int seatType) {
		this.connectionMultiplier *= 0.8;
		this.board(seatType);
		return true;
	}
	abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);
	
	protected double getAirportMultiplier(Airport myCurrentAirport, Airport toAirport) {
		if (myCurrentAirport.getAirportType() == 1) {
			if (toAirport.getAirportType()==1) {
				return 0.5;
			}
			else if (toAirport.getAirportType()==2) {
				return 0.7;
			}
			else {
				return 1;
			}
		}
		else if (myCurrentAirport.getAirportType() == 2) {
			if (toAirport.getAirportType()==1) {
				return 0.6;
			}
			else if (toAirport.getAirportType()==2) {
				return 0.8;
			}
			else {
				return 1.8;
			}
		}
		else {
			if (toAirport.getAirportType()==1) {
				return 0.9;
			}
			else if (toAirport.getAirportType()==2) {
				return 1.6;
			}
			else {
				return 3.0;
			}
		}
	}
	public double getPassengerWeight() {
		return this.weight;
	}

	protected int getBaggageCount() {
		return this.baggageCount;
	}


	public ArrayList<Integer> getDestinations() {
		return destinations;
	}
	
	public boolean isNotDestination(int ID) {
		if (destinations.contains(ID)) {
			return false;
		}
		else {
			return true;
		}
	}

	public double getSeatMultiplier() {
		return this.seatMultiplier;
	}
	public Long getID() {
		return ID;
	}
	public int getType() {
		return type;
	}


	public int getCurrAirportIndex() {
		return currAirportIndex;
	}


	public void setType(int type) {
		this.type = type;
	}
	

}
