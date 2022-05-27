package airline;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import airline.aircraft.Aircraft;
import airline.aircraft.PassengerAircraft;
import airline.aircraft.concrete.JetPassengerAircraft;
import airline.aircraft.concrete.PropPassengerAircraft;
import airline.aircraft.concrete.RapidPassengerAircraft;
import airline.aircraft.concrete.WidebodyPassengerAircraft;
import airport.Airport;
import airport.HubAirport;
import airport.MajorAirport;
import airport.RegionalAirport;
import passenger.BusinessPassenger;
import passenger.EconomyPassenger;
import passenger.FirstClassPassenger;
import passenger.LuxuryPassenger;
import passenger.Passenger;

public class Airline {

	int maxAircraftCount;
	double operationalCost;
	double propFee, widebodyFee, rapidFee, jetFee;
	double profit;
	FileWriter writer;
	ArrayList<PassengerAircraft> aircrafts = new ArrayList<PassengerAircraft>();
	HashMap<Integer, Airport> airports = new HashMap<Integer, Airport>();
	
	
	// cache variables
	int destinationPassengerNum = 0;
	
	/**
	 * 
	 * @param maxAircraftCount
	 * @param operationalCost
	 * @param propFee
	 * @param widebodyFee
	 * @param rapidFee
	 * @param jetFee
	 */
	public Airline(FileWriter writer, int maxAircraftCount, double operationalCost, double propFee, double widebodyFee, double rapidFee, double jetFee){
		this.maxAircraftCount = maxAircraftCount;
		this.operationalCost = operationalCost;
		this.propFee = propFee;
		this.widebodyFee = widebodyFee;
		this.rapidFee = rapidFee;
		this.jetFee = jetFee;
		this.writer = writer;
		profit = 0;
	}
	
	/**
	 * is called in main method to generate profit.
	 * this is main method.
	 */
	public void makeProfit() {
		Airport fromAirport = findMaxPassenger();
		Airport toAirport = findDestinition(fromAirport);
		double distance = fromAirport.getDistance(toAirport);
		System.out.println(distance);
		//System.out.println(destinationPassengerNum);
		createAircraft(fromAirport, 1);
		PassengerAircraft aircraft = fromAirport.getAircrafts().get(0);
		setFlight(fromAirport, toAirport, aircrafts.indexOf(aircraft));
		System.out.println(profit);
	}
	
	/**
	 * takes necessary actions to operate a flight.
	 * configures seats for the flight, loads passengers, add fuels,
	 * and ultimately unloads passengers at the final destination.
	 * @param fromAirport 
	 * @param toAirport destination
	 * @param aircraftIndex ID of aircraft 
 	 */
	public void setFlight(Airport fromAirport, Airport toAirport,int aircraftIndex) {
		ArrayList<Passenger> passengers = new ArrayList<Passenger>();
		PassengerAircraft aircraft = aircrafts.get(aircraftIndex);
		int economy = 0;
		int business = 0;
		int firstclass = 0;
		for(long passengerKey: fromAirport.getPassengers().keySet()) {
			Passenger passenger = fromAirport.getPassengers().get(passengerKey);
			if(passenger.hasDestination(toAirport)){
				if(passenger.getPassengerType().equals("economy")) {
					passengers.add(passenger);
					economy++;
				}
				else if(passenger.getPassengerType().equals("business")) {
					passengers.add(0, passenger);
					business++;
				}
				else {
					passengers.add(0, passenger);
					firstclass++;
				}
			}
		}
		System.out.println(economy + " " + business + " " + firstclass);
		if(economy+business*3+firstclass*8<aircraft.getFloorArea()) {
			aircraft.setSeats(economy, business, firstclass);
			aircraft.setRemainingEconomy();
		}
		else {
			// TODO
			//aircraft.setSeats(27, 11,0);
			aircraft.setAllEconomy();
			System.out.println("all economy");
		}
		configureSeats(aircraftIndex);
		for(Passenger passenger: passengers) {
			loadPassenger(passenger, fromAirport, aircraftIndex);
		}
		refuel(aircraftIndex,aircraft.avaiableFuelCapacity());
		//refuel(aircraftIndex,48000);
		if(fly(toAirport, aircraftIndex)) {
			Set<Long> clone = new HashSet<>(aircraft.getPassengers().keySet());
			for(Long passengerKey: clone) {
			unloadPassenger(aircraft.getPassengers().get(passengerKey), aircraftIndex);
		
			}
		}
	}
	
	/**
	 * flies the aircraft from its current airport to toAirport
	 * @param toAirport destination
	 * @param aircraftIndex ID of aircraft
	 * @return returns true if the flight operation is successful and returns false if the flight operation cannot be completed.
	 */
	boolean fly(Airport toAirport, int aircraftIndex) {
		double runningCost = operationalCost * aircrafts.size();
		addExpense(runningCost);
		if(aircrafts.get(aircraftIndex).canFly(toAirport)) {
			//aircrafts.get(aircraftIndex).getCurrentAirport().removeAircraft(aircrafts.get(aircraftIndex));
			//toAirport.addAircraft(aircrafts.get(aircraftIndex));
			double flightCost = aircrafts.get(aircraftIndex).fly(toAirport);
			addExpense(flightCost);
			try {
				writer.write("1 " + toAirport.getID() + " " + aircraftIndex+ "\n");
				//String relativeProfit = Double.toString((flightCost+runningCost) * -1);
				//writer.write("1 " + toAirport.getID() + " " + aircraftIndex+ " = " + relativeProfit +"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		else
			return false;
	}
	
	/**
	 * loads a passenger from a given airport to a given aircraft.
	 * if a passenger can be loaded, the loading cost is calculated and added to expenses.
	 * @param passenger the passenger to load
	 * @param airport the airport where the passenger is
	 * @param aircraftIndex the index of the aircraft to which the passenger will be loaded
	 * @return returns true if the load operation is successful and returns false if the load operation cannot be completed.
	 */
	boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		if(aircrafts.get(aircraftIndex).canLoadPassenger(passenger)) {
			double loadingFee = aircrafts.get(aircraftIndex).loadPassenger(passenger);
			addExpense(loadingFee);
			try {
				// log
				writer.write("4 " + passenger.getID() + " " + aircraftIndex + " " + airport.getID()+ "\n");
				//String relativeProfit = Double.toString(loadingFee * -1);
				//writer.write("4 " + passenger.getID() + " " + aircraftIndex + " " + airport.getID()+ " = " + relativeProfit + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		else	
			return false;
	}
	
	/**
	 * unloads a passenger from a given aircraft.
	 * if a passenger can disembark, the ticket revenue is calculated and added to revenue.
	 * @param passenger the passenger to unload
	 * @param aircraftIndex the index of the aircraft which the passenger will be unloaded
	 * @return returns true if the unload operation is successful and returns false if the unload operation could not be completed.
	 */
	boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		if(aircrafts.get(aircraftIndex).canUnloadPassenger(passenger)) {
			double ticketRevenue = aircrafts.get(aircraftIndex).unloadPassenger(passenger);
			//System.out.println(ticketRevenue);
			addRevenue(ticketRevenue);
			try {
				// log
				writer.write("5 " + passenger.getID() + " " + aircraftIndex + " " + aircrafts.get(aircraftIndex).getCurrentAirport().getID()+ "\n");
				//String relativeProfit = Double.toString(ticketRevenue);
				//writer.write("5 " + passenger.getID() + " " + aircraftIndex + " " + aircrafts.get(aircraftIndex).getCurrentAirport().getID()+ " = " + relativeProfit + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		else
			return false;
	}
	
	/**
	 * adds specified amount of fuel to given aircraft.
	 * adds fuel cost to expenses.
	 * @param aircraftIndex the index of the aircraft which the passenger will be unloaded
	 * @param fuel amount of fuel to be added
	 */
	void refuel(int aircraftIndex, double fuel) {
		double cost = aircrafts.get(aircraftIndex).refuel(fuel);
		addExpense(cost);
		try {
			// log
			writer.write("3 " + aircraftIndex + " " + fuel + "\n");
			//String relativeProfit = Double.toString(cost * -1);
			//writer.write("3 " + aircraftIndex + " " + fuel + " = " + relativeProfit + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * creates a new aircraft and adds to the aircraft arraylist.
	 * @param airport which airport to create aircraft on
	 * @param aircrafttype type of aircraft
	 */
	void createAircraft(Airport airport, int aircrafttype) {
		try {
			// log
			writer.write("0 " + airport.getID() + " " + aircrafttype + "\n");
			//String relativeProfit = Double.toString(0.0);
			//writer.write("0 " + airport.getID() + " " + aircrafttype + " = " + relativeProfit + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(aircrafttype == 0) {
			PassengerAircraft aircraft = new PropPassengerAircraft(airport, propFee);
			aircrafts.add(aircraft);
			airport.addAircraft(aircraft);
		}
		else if(aircrafttype == 1) {
			PassengerAircraft aircraft = new WidebodyPassengerAircraft(airport, widebodyFee);
			aircrafts.add(aircraft);
			airport.addAircraft(aircraft);
		}
		else if(aircrafttype == 2) {
			PassengerAircraft aircraft = new RapidPassengerAircraft(airport, rapidFee);
			aircrafts.add(aircraft);
			airport.addAircraft(aircraft);
		}
		else if(aircrafttype == 3) {
			PassengerAircraft aircraft = new JetPassengerAircraft(airport, jetFee);
			aircrafts.add(aircraft);
			airport.addAircraft(aircraft);
		}
	}
	
	/**
	 * logs seat configuration of given aircraft.
	 * @param aircraftIndex
	 */
	void configureSeats(int aircraftIndex) {
		String economy = Integer.toString(aircrafts.get(aircraftIndex).getEconomySeats());
		String business = Integer.toString(aircrafts.get(aircraftIndex).getBusinessSeats());
		String firstclass = Integer.toString(aircrafts.get(aircraftIndex).getFirstClassSeats());
		try {
			// log
			writer.write("2 " + aircraftIndex + " " + economy + " " + business + " " + firstclass+ "\n");
			//String relativeProfit = Double.toString(0.0);
			//writer.write("2 " + aircraftIndex + " " + economy + " " + business + " " + firstclass+ " = " + relativeProfit + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * creates objects of Airport class.
	 * puts airport objects to a hashmap.
	 * @param airportType
	 * @param airportID
	 * @param x
	 * @param y
	 * @param aircraftCapacity
	 * @param operationFee
	 * @param fuel_cost
	 */
	public void createAirport(String airportType,	int airportID, double x, double y, int aircraftCapacity, double operationFee,	double fuel_cost) {
		if(airportType.equals("hub")) {
			airports.put(airportID, new HubAirport(airportID,x,y, aircraftCapacity, operationFee, fuel_cost));
		}
		else if(airportType.equals("major")){
			airports.put(airportID, new MajorAirport(airportID,x,y, aircraftCapacity, operationFee, fuel_cost));
		}
		else if(airportType.equals("regional")){
			airports.put(airportID, new RegionalAirport(airportID,x,y, aircraftCapacity, operationFee, fuel_cost));
		}
	}
	
	/**
	 * creates objects of Passenger class.
	 * adds passengers objects to collections at specified airport objects.
	 * @param passengerType
	 * @param passengerID
	 * @param weight
	 * @param baggageCount
	 * @param destinationsInt
	 */
	public void createPassenger(String passengerType, long passengerID,	double weight, int baggageCount, ArrayList<Integer> destinationsInt) {
		ArrayList<Airport> destinations = new ArrayList<Airport>();
		for(int i = 1; i < destinationsInt.size(); i++) {
			destinations.add(airports.get(destinationsInt.get(i)));
		}
		Airport currentAirport = airports.get(destinationsInt.get(0));
		if(passengerType.equals("economy")) {
			currentAirport.addPassenger(new EconomyPassenger(passengerID, weight, baggageCount, destinations, currentAirport));
		}
		else if(passengerType.equals("business")) {
			currentAirport.addPassenger(new BusinessPassenger(passengerID, weight, baggageCount, destinations, currentAirport));
		}
		else if(passengerType.equals("first class")) {
			currentAirport.addPassenger(new FirstClassPassenger(passengerID, weight, baggageCount, destinations, currentAirport));
		}
		else if(passengerType.equals("luxury")) {
			currentAirport.addPassenger(new LuxuryPassenger(passengerID, weight, baggageCount, destinations, currentAirport));
		}
	}
	
	/**
	 * returns the airport object with the highest number of passengers.
	 * @return the airport object which has highest number of passengers
	 */
	private Airport findMaxPassenger() {
		int keyOfAirport = 0;
		int maxPassengers = 0;
		for(Integer airportKey: airports.keySet()) {
			if(airports.get(airportKey).numberOfPassengers()>maxPassengers) {
				keyOfAirport = airportKey;
				maxPassengers = airports.get(airportKey).numberOfPassengers();
			}
		}
		return airports.get(keyOfAirport);
	}
	
	/**
	 * finds a destination which has highest number of passenger wishing to go 
	 * @param fromAirport 
	 * @return destination
	 */
	private Airport findDestinition(Airport fromAirport) {
		HashMap<Airport,Integer> destinations = new HashMap<Airport,Integer>();
		int a;
		for(long passengerKey: fromAirport.getPassengers().keySet()) {
			for(Airport destination : fromAirport.getPassengers().get(passengerKey).getDestinations()){
				if(destination.getDistance(fromAirport)<20000) {
					if(destinations.containsKey(destination)) {
						a = destinations.get(destination);
						destinations.replace(destination, a+1);
					}
					else {
						destinations.put(destination, 1);
					}
				}
			}
		}
		Airport newDestination = null;
		int maxPassengers = 0;
		for(Airport airport: destinations.keySet()) {
			if(destinations.get(airport)>maxPassengers) {
				newDestination = airport;
				maxPassengers = destinations.get(airport);
			}
		}
		destinationPassengerNum = maxPassengers;
		return newDestination;
	}
	
	/**
	 * a getter
	 * @return profit
	 */
	public double getProfit() {
		return profit;
	}

	/**
	 * substracts given expense from the profit
	 * @param expense
	 */
	private void addExpense(double expense) {
		profit = profit - expense;
	}
	
	/**
	 * adds given revenue to the profit
	 * @param revenue
	 */
	private void addRevenue(double revenue) {
		profit = profit + revenue;
	}
}
