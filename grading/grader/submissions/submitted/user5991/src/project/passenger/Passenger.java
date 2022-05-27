package project.passenger;
import project.airport.Airport;
import project.airline.aircraft.Aircraft;
import java.util.*;
public abstract class Passenger {
	
	private double connectionMultiplier = 1; //write a getter for this
	private final long ID;
	private final double weight;
	private final int baggageCount;
	private ArrayList<Airport> destinations;
	private double seatMultiplier = 1;
	protected int seatType;
	protected Airport currentAirport;
	
	/** Calculates and returns the ticket price to fly from the currentAirport to the airport given in parameters.
	 * @param airport
	 * @param aircraftTypeMultiplier
	 * @return
	 */
	abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);
	
	
	/** Constructor of Passenger Class
	 * @param ID
	 * @param weight
	 * @param baggageCount
	 * @param destinations
	 */
	public Passenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		this.ID=ID;
		this.weight=weight;
		this.baggageCount=baggageCount;
		this.destinations=destinations;
	}
	
	public Airport getCurrentAirport() {return currentAirport;}
	public double getWeight() {return weight;}
	public double getSeatMultiplier() {return seatMultiplier;}
	public double getConnectionMultiplier() {return connectionMultiplier;}
	public void setCurrentAirport(Airport currentAirport) {this.currentAirport=currentAirport;}
	public int getBaggageCount() {return baggageCount;}
	public long getID() {return ID;}
	public int getSeatType() {return seatType;}
	public ArrayList<Airport> getDestinations() {return destinations;}
	
	
	/**Arranges the seatMultiplier and sets the seatType correctly.
	 * @param seatType
	 * @return
	 */
	public boolean board(int seatType) {
		this.seatType=seatType;
		switch(seatType) {
		case 0:
			seatMultiplier = 0.6;
			break;
		case 1:
			seatMultiplier = 1.2;
			break;
		case 2:
			seatMultiplier = 3.2;
			break;
		}
		return true;
	}
	
	/** Disembarks the passenger if the airport is in the passenger's destinations. Removes all previous destinations. Returns the ticketprice and sets the currentAirport.
	 * @param airport
	 * @param aircraftTypeMultiplier
	 * @return
	 */
	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		
		if(destinations.contains(airport)) {
			
			for(int j=0;j<destinations.size();j++) {
				if(destinations.get(0).equals(airport)) {
					destinations.remove(0);
					break;
				}
				
			}
			destinations.remove(0);
			double ticketprice = calculateTicketPrice(airport,aircraftTypeMultiplier); 
			currentAirport = airport;
			return ticketprice;
		}
		return 0;
	}
	
	/** Does the multiplications of connectionMultiplier and seatMultiplier.
	 * @param seatType
	 * @return
	 */
	public boolean connection(int seatType) {
		connectionMultiplier*=0.8;
		this.seatType=seatType;
		switch(seatType) {
		case 0:
			seatMultiplier *= 0.6;
			break;
		case 1:
			seatMultiplier *= 1.2;
			break;
		case 2:
			seatMultiplier *= 3.2;
			break;
		}
		return true;
	}
	
}
