package project.airport;
import project.airline.aircraft.Aircraft;
import project.passenger.BusinessPassenger;
import project.passenger.EconomyPassenger;
import project.passenger.FirstClassPassenger;
import project.passenger.LuxuryPassenger;
import project.passenger.Passenger;
import java.util.*;

public abstract class Airport {
	public abstract double departAircraft(Aircraft aircraft);
	public abstract double landAircraft(Aircraft aircraft);
	
	private final int ID;
	private final double x, y;
	protected double fuelCost;
	protected double operationFee;
	protected int aircraftCapacity;
	protected ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
	protected ArrayList<Passenger> Passengers = new ArrayList<Passenger>();
	
	
	
	public ArrayList<Passenger> getPassengers() {return Passengers;}
	public ArrayList<Aircraft> getAircrafts() {return aircrafts;}
	
	
	
	/** Returns the count of all luxury, first class, business and economy passengers in the airport.
	 * @return
	 */
	public int[] getPassengersTypeCounts() {
		int luxury=0, firstClass =0, business=0, economy=0;
		for(Passenger p:Passengers) {
			if(p instanceof LuxuryPassenger) {luxury++;}
			else if(p instanceof FirstClassPassenger) {firstClass++;}
			else if(p instanceof BusinessPassenger) {business++;}
			else {economy++;}
		}
		int[] numbers = {luxury,firstClass,business,economy};
		return numbers;
	}
	
	
	/** Constructor of the Airport
	 * @param ID
	 * @param x
	 * @param y
	 * @param fuelCost
	 * @param operationFee
	 * @param aircraftCapacity
	 */
	public Airport(int ID,double x,double y,double fuelCost,double operationFee,int aircraftCapacity) {
		this.ID=ID;
		this.x=x;
		this.y=y;
		this.fuelCost=fuelCost;
		this.operationFee=operationFee;
		this.aircraftCapacity=aircraftCapacity;
	}
	
	
	/** Puts the airports in a hashmap as keys and gives integer values to how many times they are the destinations of the passengers, the value increases
	 * as the passenger level increases(economy to luxury). Selects the max valued airport as the airport to be traveled to and returns the passengers that want to fly there.
	 * @return
	 */
	public ArrayList<Passenger> flyPassengers() {
		
		ArrayList<Passenger> flyingPassengers = new ArrayList<Passenger>();
		HashMap<Airport,Integer> toAirportsNumbers = new HashMap<Airport,Integer>();
		Airport finalAirport=null;
		if(Passengers.size()!=0) {
			for(Passenger passenger: Passengers) {
				if(passenger.getDestinations().size()>=2) {
					Airport toAirport = passenger.getDestinations().get(1);
					
					if(passenger instanceof LuxuryPassenger) {
						toAirportsNumbers.computeIfPresent(toAirport, (key, value) -> value+5);
						toAirportsNumbers.putIfAbsent(toAirport, 1);
					}
					else if(passenger instanceof FirstClassPassenger) {
						toAirportsNumbers.computeIfPresent(toAirport, (key, value) -> value+1);
						toAirportsNumbers.putIfAbsent(toAirport, 1);
					}

					
					}
			}
			int maxValue = 0;
			try {maxValue = Collections.max(toAirportsNumbers.values());} catch(Exception e) {return flyingPassengers;}
			for(Airport airports : toAirportsNumbers.keySet()) {
				if(toAirportsNumbers.get(airports)==maxValue) {
					finalAirport = airports;
				}
			}
			
			for(Passenger passenger: Passengers) {
				if(passenger.getDestinations().size()>=2) {
					if(passenger.getDestinations().get(1).equals(finalAirport)) {
						if(passenger instanceof LuxuryPassenger||passenger instanceof FirstClassPassenger) {
							flyingPassengers.add(passenger);
						}
					}
				}
			}
		}
		
		return flyingPassengers;
	}
	
	
	
	
	/** Adds Aircraft to the airport
	 * @param aircraft
	 */
	public void AddAircraft(Aircraft aircraft) {
		aircrafts.add(aircraft);
	}
	
	public double getFuelCost() {return fuelCost;}
	
	
	public int getID() {
		return ID;
	}
	
	/** Returns if the aircraft capacity is maxed out in the airport.
	 * @return
	 */
	public boolean isFull() {
		return aircrafts.size()==aircraftCapacity;
	}
	
	/** Calculates the distance between this airport and the airport given in the parameters.
	 * @param airport
	 * @return
	 */
	public double getDistance(Airport airport) {
		return Math.sqrt((this.x-airport.x)*(this.x-airport.x) +(this.y-airport.y)*(this.y-airport.y));
	}
	
	/**Adds a passenger to the airport.
	 * @param passenger
	 */
	public void addPassenger(Passenger passenger) {
		Passengers.add(passenger);
	}
	
	/** Removes a passenger from the airport.
	 * @param passenger
	 */
	public void removePassenger(Passenger passenger) {
		Passengers.remove(passenger);
	}
	
}
