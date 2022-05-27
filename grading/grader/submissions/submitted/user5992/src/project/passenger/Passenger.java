package project.passenger;

import project.airport.Airport;
import project.airport.HubAirport;
import project.airport.MajorAirport;

import java.util.ArrayList;

/**
 * @author Emre KILIC
 *
 */
public abstract class Passenger {
	private final long ID;
	private final double weight;
	private final int baggageCount;
	private final ArrayList<Airport> destinations;
	protected Airport previousDisembark;
	private Airport currentAirport;
	private boolean isInAircraft;
	protected double seatMultiplier;
	protected double connectionMultiplier;
	protected int type;
	private int seatType;

	protected Passenger(long id, double weight, int baggageCount, ArrayList<Airport> destinations) {
		ID = id;
		this.weight = weight;
		this.baggageCount = baggageCount;
		this.destinations = destinations;
		currentAirport = destinations.get(0);
	}

	public boolean board(int seatType) {
		boolean canBoard = canBoard(seatType);
		if (canBoard) {
			currentAirport.removePassenger(this);
			this.seatType = seatType;
			seatMultiplier = getSeatMultiplier(seatType);
			connectionMultiplier = 1.0;
			isInAircraft = true;
		}
		return canBoard;
	}
	
	public double disembark(Airport airport, double aircraftTypeMultiplier) {
		if (canDisembark(airport)){
			airport.addPassenger(this);
			previousDisembark = currentAirport;
			currentAirport = airport;
			isInAircraft = false;
			if (previousDisembark.equals(currentAirport)){
				return 0;
			}
			return calculateTicketPrice(airport, aircraftTypeMultiplier);
		}
		return 0;
	}

	public boolean connection(int seatType) {
		boolean canConnect = canConnect(seatType);
		if (canConnect){
			this.seatType = seatType;
			seatMultiplier *= getSeatMultiplier(seatType);
			connectionMultiplier *= 0.8;
		}
		return canConnect;
	}

	private double getSeatMultiplier(int seatType){
		return switch (seatType){
			case 0 -> 0.6;
			case 1 -> 1.2;
			case 2 -> 3.2;
			default -> throw new IllegalStateException("Unexpected value: " + type);
		};
	}

	protected double getAirportMultiplier(){
		if (previousDisembark instanceof HubAirport){
			if (currentAirport instanceof HubAirport) {
				return 0.5;
			}else if (currentAirport instanceof MajorAirport){
				return 0.7;
			}else {
				return 1;
			}
		}else if (previousDisembark instanceof MajorAirport){
			if (currentAirport instanceof HubAirport) {
				return 0.6;
			}else if (currentAirport instanceof MajorAirport){
				return 0.8;
			}else {
				return 1.8;
			}
		}else {
			if (currentAirport instanceof HubAirport) {
				return 0.9;
			}else if (currentAirport instanceof MajorAirport){
				return 1.6;
			}else {
				return 3;
			}
		}
	}

	private boolean canBoard(int seatType){
		return type >= seatType && !isInAircraft;
	}

	public boolean canDisembark(Airport airport) {
		return isInAircraft && destinations.indexOf(airport) > destinations.indexOf(currentAirport);
	}

	private boolean canConnect(int seatType){
		return type >= seatType && isInAircraft;
	}

	/**
	 * @return 0 for economy;<p>
	 *     	   1 for business;<p>
	 *     	   2 for first class;<p>
	 *     	   3 for luxury;<p>
	 */
	public Integer getType(){
		return type;
	}

	public long getID() {
		return ID;
	}

	public int getSeatType() {
		return seatType;
	}

	public double getWeight() {
		return weight;
	}

	public Airport getCurrentAirport() {
		return currentAirport;
	}

	public Airport nextHub(){
		int current = destinations.indexOf(currentAirport);
		for (int i = current+1; i < destinations.size(); i++) {
			Airport airport = destinations.get(i);
			if (airport.getType() == 0){
				return airport;
			}
		}
		return null;
	}

	public Airport nextMajor(){
		int current = destinations.indexOf(currentAirport);
		for (int i = current+1; i < destinations.size(); i++) {
			Airport airport = destinations.get(i);
			if (airport.getType() == 1){
				return airport;
			}
		}
		return null;
	}

	public Airport nextRegional(){
		int current = destinations.indexOf(currentAirport);
		for (int i = current+1; i < destinations.size(); i++) {
			Airport airport = destinations.get(i);
			if (airport.getType() == 2){
				return airport;
			}
		}
		return null;
	}

	protected int getBaggageCount(){
		return baggageCount;
	}

	protected abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);

}
