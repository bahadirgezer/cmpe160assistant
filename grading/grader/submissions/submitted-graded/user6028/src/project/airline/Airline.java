package project.airline;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import project.airline.aircraft.*;
import project.airline.aircraft.concrete.*;
import project.airport.*;
import project.passenger.*;

public class Airline {
	int maxAircraftCount;
	double operationalCost;
	long revenue = 0;
	long expenses = 0;
	
	double jetOperationFee;
	double propOperationFee;
	double rapidOperationFee;
	double widebodyOperationFee;
	
	/**
	 * 
	 * @param maxAircraftCount Maximum number of aircrafts
	 * @param operationalCost 
	 * @param jetOperationFee 
	 * @param propOperationFee 
	 * @param rapidOperationFee 
	 * @param widebodyOperationFee
	 */
	public Airline(int maxAircraftCount, double operationalCost, double jetOperationFee, double propOperationFee, double rapidOperationFee, double widebodyOperationFee) {
		this.maxAircraftCount = maxAircraftCount;
		this.operationalCost = operationalCost;
		this.jetOperationFee = jetOperationFee;
		this.propOperationFee = propOperationFee;
		this.rapidOperationFee = rapidOperationFee;
		this.widebodyOperationFee = widebodyOperationFee;
	}
	
	private ArrayList<PassengerAircraft> aircraftList = new ArrayList<PassengerAircraft>();
	private HashMap<Integer, Airport> airportMap = new HashMap<Integer, Airport>();
	
	public ArrayList<String> logArray = new ArrayList<String>();
	
	
	
	/**
	 * 
	 * @param toAirport airport to fly to
	 * @param aircraftIndex index of the aircraft to fly
	 * @return whether flight happened
	 */
	
	public boolean fly(Airport toAirport, int aircraftIndex) {
		double p1 = this.getProfit();
		expenses += this.getAircraftCount() * operationalCost;
		//double p4 = this.getAircraftCount() * operationalCost;
		if (aircraftList.get(aircraftIndex).canFly(toAirport)) {
			expenses += aircraftList.get(aircraftIndex).fly(toAirport);
			double p2 = this.getProfit();
			double p3 = p2 - p1;
			String log = "1 " + Integer.toString(toAirport.getID()) + " " + Integer.toString(aircraftIndex) + " = " + Double.toString(p3);
			logArray.add(log);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @return count of aircrafts
	 */
	public int getAircraftCount() {
		return aircraftList.size();
	}
	
	/**
	 * 
	 * @param passenger Passenger to load
	 * @param airport Airport to load from
	 * @param aircraftIndex Index of the aircraft to load to
	 * @return whether loading happened
	 */
	public boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		if (!(airport.getPassengerList().containsValue(passenger))) {
			return false;
		}
		else if (aircraftList.get(aircraftIndex).isLoadValid(passenger)) {
			double p1 = this.getProfit();
			expenses += aircraftList.get(aircraftIndex).loadPassenger(passenger);
			double p2 = this.getProfit();
			double p3 = p2 - p1;
			String log = "4 " + Long.toString(passenger.getID()) + " " + Integer.toString(aircraftIndex)  + " " + Integer.toString(airport.getID()) + " = " + Double.toString(p3);
			logArray.add(log);
			return true;
		}
		else {
			expenses += aircraftList.get(aircraftIndex).loadPassenger(passenger);
			return false;
		}
	}
	
	/**
	 * 
	 * @param passenger
	 * @param aircraftIndex
	 * @return whether unload happened
	 */
	public boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		if (passenger.canDisembark(aircraftList.get(aircraftIndex).getCurrentAirport())) {
			double p1 = this.getProfit();
			revenue += aircraftList.get(aircraftIndex).unloadPassenger(passenger);
			double p2 = this.getProfit();
			double p3 = p2 - p1;
			String log = "5 " + Long.toString(passenger.getID()) + " " + Integer.toString(aircraftIndex)  + " " + Integer.toString(aircraftList.get(aircraftIndex).getCurrentAirport().getID()) + " = " + Double.toString(p3);
			logArray.add(log);
			return true;
		}
		else {
			expenses += aircraftList.get(aircraftIndex).unloadPassenger(passenger);
			return false;
		}
	}
	
	/**
	 * 
	 * @param passenger Passenger to transfer to
	 * @param aircraftIndex Transferring aircraft
	 * @param toAircraftIndex Aircraft to transfer
	 * @return whether transfer happened
	 */
	public boolean transferPassenger(Passenger passenger, int aircraftIndex, int toAircraftIndex) {
		if (aircraftList.get(toAircraftIndex).isLoadValid(passenger)){
			expenses += aircraftList.get(aircraftIndex).transferPassenger(passenger, aircraftList.get(toAircraftIndex));
			String log = "6 " + Long.toString(passenger.getID()) + " " + Integer.toString(aircraftIndex)  + " " + Integer.toString(aircraftList.get(toAircraftIndex).getCurrentAirport().getID());
			logArray.add(log);
			return true;
		}
		else {
			expenses += aircraftList.get(aircraftIndex).transferPassenger(passenger, aircraftList.get(toAircraftIndex));
			return false;
		}
	}
	
	/**
	 * 
	 * @param aircraftIndex Aircraft to refuel
	 * @param amount Amount of fuel to refuel
	 * @return Whether refuel happened
	 */
	public boolean refuel(int aircraftIndex, double amount) {
		if (aircraftList.get(aircraftIndex).canAddFuel(amount)) {
			expenses += aircraftList.get(aircraftIndex).addFuel(amount);
			String log = "3 " + Integer.toString(aircraftIndex) + " " +  Double.toString(amount);
			logArray.add(log);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param aircraftIndex Aircraft to fill up
	 * @return Whether filling up happened
	 */
	public boolean fillUp(int aircraftIndex) {
		if (aircraftList.get(aircraftIndex).canFillUp()) {
			double prevFuel = aircraftList.get(aircraftIndex).getFuel();
			double p1 = this.getProfit();
			expenses += aircraftList.get(aircraftIndex).fillUp();
			double p2 = this.getProfit();
			double p3 = p2- p1;
			double afterFuel = aircraftList.get(aircraftIndex).getFuel();
			double amount = afterFuel - prevFuel;
			String log = "3 " + Integer.toString(aircraftIndex) + " " +  Double.toString(amount) + " = " + Double.toString(p3);
			logArray.add(log);
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * 
	 * @param airport Airport to create aircraft in
	 * @return Whether creation was successful
	 */
	public boolean createJetPassengerAircraft(Airport airport) {
		if (getAircraftCount() + 1 <= maxAircraftCount) {
			JetPassengerAircraft aircraft = new JetPassengerAircraft(airport, this.jetOperationFee);
			this.aircraftList.add(aircraft);
			String log = "0 "  + Integer.toString(airport.getID())  + " 3";
			logArray.add(log);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param Airport to create the aircraft
	 * @return Whether creation was successful
	 */
	public boolean createPropPassengerAircraft(Airport airport) {
		if (getAircraftCount() + 1 <= maxAircraftCount) {
			PropPassengerAircraft aircraft = new PropPassengerAircraft(airport, this.propOperationFee);
			this.aircraftList.add(aircraft);
			String log = "0 " +  Integer.toString(airport.getID())  + " 0";
			logArray.add(log);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param Airport to create the aircraft
	 * @return Whether creation was successful
	 */
	public boolean createRapidPassengerAircraft(Airport airport) {
		if (getAircraftCount() + 1 <= maxAircraftCount) {
			RapidPassengerAircraft aircraft = new RapidPassengerAircraft(airport, this.rapidOperationFee);
			this.aircraftList.add(aircraft);
			String log = "0 "  + Integer.toString(airport.getID())  + " 2";
			logArray.add(log);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param Airport to create the aircraft
	 * @return Whether creation was successful
	 */
	public boolean createWideBodyPassengerAircraft(Airport airport) {
		if (getAircraftCount() + 1 <= maxAircraftCount && !airport.isFull()) {
			airport.incrementAircraftCount();
			WidebodyPassengerAircraft aircraft = new WidebodyPassengerAircraft(airport, this.widebodyOperationFee);
			this.aircraftList.add(aircraft);
			String log = "0 " + Integer.toString(airport.getID()) + " 1" + " = " + "0.0";
			logArray.add(log);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param ID ID of Airport
	 * @param x X coordinate of Airport
	 * @param y Y coordinate of Airport
	 * @param fuelCost Fuel cost of Airport
	 * @param operationFee Operation fee of Airport
	 * @param aircraftCapacity Aircraft Capacity of Airport
	 */
	public void createHubAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		HubAirport airport = new HubAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
		airportMap.put(ID, airport);
	}
	
	/**
	 * 
	 * @param ID ID of Airport
	 * @param x X coordinate of Airport
	 * @param y Y coordinate of Airport
	 * @param fuelCost Fuel cost of Airport
	 * @param operationFee Operation fee of Airport
	 * @param aircraftCapacity Aircraft Capacity of Airport
	 */
	public void createMajorAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		MajorAirport airport = new MajorAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
		airportMap.put(ID, airport);
	}
	
	/**
	 * 
	 * @param ID ID of Airport
	 * @param x X coordinate of Airport
	 * @param y Y coordinate of Airport
	 * @param fuelCost Fuel cost of Airport
	 * @param operationFee Operation fee of Airport
	 * @param aircraftCapacity Aircraft Capacity of Airport
	 */
	public void createRegionalAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		RegionalAirport airport = new RegionalAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
		airportMap.put(ID, airport);
	}
	
	/**
	 * 
	 * @param ID ID of Passenger
	 * @param weight Weight of Passenger
	 * @param baggageCount Baggage amount the Passenger has 
	 * @param destinations Destinations of Passenger
	 */
	public void createEconomyPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		EconomyPassenger passenger = new EconomyPassenger(ID, weight, baggageCount, destinations);
		destinations.get(0).addPassenger(passenger);
	}
	
	/**
	 * 
	 * @param ID ID of Passenger
	 * @param weight Weight of Passenger
	 * @param baggageCount Baggage amount the Passenger has 
	 * @param destinations Destinations of Passenger
	 */
	public void createBusinessPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		BusinessPassenger passenger = new BusinessPassenger(ID, weight, baggageCount, destinations);
		destinations.get(0).addPassenger(passenger);
	}
	
	/**
	 * 
	 * @param ID ID of Passenger
	 * @param weight Weight of Passenger
	 * @param baggageCount Baggage amount the Passenger has 
	 * @param destinations Destinations of Passenger
	 */
	public void createFirstClassPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		FirstClassPassenger passenger = new FirstClassPassenger(ID, weight, baggageCount, destinations);
		destinations.get(0).addPassenger(passenger);
	}
	
	/**
	 * 
	 * @param aircraftIndex index of the aircraft to add seats
	 * @param economy economy seats to add
	 * @param business business seats to add
	 * @param firstClass firstclass seats to add
	 * @return If seating is successful
	 */
	public boolean setSeats(int aircraftIndex, int economy, int business, int firstClass) {
			boolean resBool = aircraftList.get(aircraftIndex).setSeats(economy, business, firstClass);
			if (resBool) {
				String log = "2 " + Integer.toString(aircraftIndex) + " " +  Integer.toString(aircraftList.get(aircraftIndex).getEconomySeats()) + " " +  Integer.toString(aircraftList.get(aircraftIndex).getBusinessSeats()) + " " + Integer.toString(aircraftList.get(aircraftIndex).getFirstClassSeats()) + " = " + "0.0";
				logArray.add(log);
				return true;
			}
			else {
				return false;
			}
	}
	
	/**
	 * 
	 * @param aircraftIndex index of the aircraft to set seats
	 * @return If seating is successful
	 */
	public boolean setAllEconomy(int aircraftIndex) {
		boolean resBool = aircraftList.get(aircraftIndex).setAllEconomy();
		if (resBool) {
			String log = "2 " + Integer.toString(aircraftIndex) + " " +  Integer.toString(aircraftList.get(aircraftIndex).getEconomySeats()) + " " +  Integer.toString(aircraftList.get(aircraftIndex).getBusinessSeats()) + " " + Integer.toString(aircraftList.get(aircraftIndex).getFirstClassSeats());
			logArray.add(log);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param aircraftIndex index of the aircraft to set seats
	 * @return If seating is successful
	 */
	public boolean setAllBusiness(int aircraftIndex) {
		boolean resBool = aircraftList.get(aircraftIndex).setAllBusiness();
		if (resBool) {
			String log = "2 " + Integer.toString(aircraftIndex) + " " +  Integer.toString(aircraftList.get(aircraftIndex).getEconomySeats()) + " " +  Integer.toString(aircraftList.get(aircraftIndex).getBusinessSeats()) + " " + Integer.toString(aircraftList.get(aircraftIndex).getFirstClassSeats());
			logArray.add(log);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param aircraftIndex index of the aircraft to set seats
	 * @return If seating is successful
	 */
	public boolean setAllFirstClass(int aircraftIndex) {
		boolean resBool = aircraftList.get(aircraftIndex).setAllFirstClass();
		if (resBool) {
			String log = "2 " + Integer.toString(aircraftIndex) + " " +  Integer.toString(aircraftList.get(aircraftIndex).getEconomySeats()) + " " +  Integer.toString(aircraftList.get(aircraftIndex).getBusinessSeats()) + " " + Integer.toString(aircraftList.get(aircraftIndex).getFirstClassSeats());
			logArray.add(log);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param aircraftIndex index of the aircraft to set seats
	 * @return If seating is successful
	 */
	public boolean setRemainingEconomy(int aircraftIndex) {
		boolean resBool = aircraftList.get(aircraftIndex).setRemainingEconomy();
		if (resBool) {
			String log = "2 " + Integer.toString(aircraftIndex) + " " +  Integer.toString(aircraftList.get(aircraftIndex).getEconomySeats()) + " " +  Integer.toString(aircraftList.get(aircraftIndex).getBusinessSeats()) + " " + Integer.toString(aircraftList.get(aircraftIndex).getFirstClassSeats());
			logArray.add(log);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param aircraftIndex index of the aircraft to set seats
	 * @return If seating is successful
	 */
	public boolean setRemainingBusiness(int aircraftIndex) {
		boolean resBool = aircraftList.get(aircraftIndex).setRemainingBusiness();
		if (resBool) {
			String log = "2 " + Integer.toString(aircraftIndex) + " " +  Integer.toString(aircraftList.get(aircraftIndex).getEconomySeats()) + " " +  Integer.toString(aircraftList.get(aircraftIndex).getBusinessSeats()) + " " + Integer.toString(aircraftList.get(aircraftIndex).getFirstClassSeats()) + " = 0.0";
			logArray.add(log);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param aircraftIndex index of the aircraft to set seats
	 * @return If seating is successful
	 */
	public boolean setRemainingFirstClass(int aircraftIndex) {
		boolean resBool = aircraftList.get(aircraftIndex).setRemainingFirstClass();
		if (resBool) {
			String log = "2 " + Integer.toString(aircraftIndex) + " " +  Integer.toString(aircraftList.get(aircraftIndex).getEconomySeats()) + " " +  Integer.toString(aircraftList.get(aircraftIndex).getBusinessSeats()) + " " + Integer.toString(aircraftList.get(aircraftIndex).getFirstClassSeats()) + " = 0.0";
			logArray.add(log);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Getter for airportMap
	 * @return airportMap, the list of airports
	 */
	public HashMap<Integer, Airport> getAirportMap() {
		return airportMap;
	}
	
	/**
	 * Getter for aircraftList
	 * @return aircraftList, the list of aircrafts
	 */
	public ArrayList<PassengerAircraft> getAircraftList() {
		return aircraftList;
	}
	
	public HashMap<Long, Passenger> getPassengerListFromAirport(int ID) {
		return airportMap.get(ID).getPassengerList();
	}
	
	/**
	 * 
	 * @param destinationIDs Array of IDs of Airports to go
	 * @return Array of Airport objects to go
	 */
	public ArrayList<Airport> createDestinationsForPassenger(ArrayList<Integer> destinationIDs) {
		ArrayList<Airport> newAirportList = new ArrayList<Airport>();
		for (int i = 0; i < destinationIDs.size(); i++) {
			Airport airport = airportMap.get(destinationIDs.get(i));
			newAirportList.add(airport);
		}
		return newAirportList;
	}
	
	/**
	 * 
	 * @return A random airport with some security checks
	 */
	public Airport getRandomAirport() {
		Random rand = new Random();
		int randInt = rand.nextInt(this.airportMap.size());
		Airport dummy = null;
		Airport secondAirport = null;
		int i = 0;
		for (Airport airport : airportMap.values()) {
			if (i == randInt && airport.getAirportType() != 0) {
				dummy = airport;
				break;
			}
			else {
				i++;
			}
			if (i == 1) {
				secondAirport = airport;
			}
		}
		if (dummy != null) {
		return dummy;
		}
		else {
			return secondAirport;
		}
	}
	
	
	/**
	 * 
	 * @param aircraftIndex Index of the aircraft to load the passengers to
	 * @param destination The destination that the plane will fly 
	 */
	public void loadPassengersWithSameDestination(int aircraftIndex, Airport destination) {
		Airport currentAirport = aircraftList.get(aircraftIndex).getCurrentAirport();
		HashMap <Long, Passenger> passengerList = currentAirport.getPassengerList();
		ArrayList<Passenger> passengerArray = new ArrayList<Passenger>();
 		for (Passenger passenger : passengerList.values()) {
			if (passenger.getDestinations().contains(destination)) {
				passengerArray.add(passenger);
			}
		}
		for (Passenger passenger : passengerArray) {
			if (passenger.getDestinations().contains(destination)) {
				this.loadPassenger(passenger, currentAirport, aircraftIndex);
			}
		}
	}
	

	/**
	 * Unloads all people who can disembark at a certain airport
	 * @param aircraftIndex The aircraft to unload 
	 * @return Whether at least 1 passenger unloaded
	 */
	public boolean batchUnload(int aircraftIndex) {
		Airport currentAirport = aircraftList.get(aircraftIndex).getCurrentAirport();
		HashMap <Long, Passenger> passengerList = aircraftList.get(aircraftIndex).getPassengerMap();
		boolean resBool = false;
		ArrayList<Passenger> passengerArray = new ArrayList<Passenger>();
 		for (Passenger passenger : passengerList.values()) {
		if (passenger.getDestinations().contains(currentAirport)) {
				passengerArray.add(passenger);
			}
		}
		for (Passenger passenger : passengerArray) {
			if (passenger.canDisembark(currentAirport)) {
			boolean unloadBool = this.unloadPassenger(passenger, aircraftIndex);
			resBool = resBool || unloadBool;
		}
		}
		return resBool;
	}
		
	public double getProfit() {
		return revenue - expenses;
	}
	
	/**
	 * Finds nearby airports to an airport within a range
	 * @param airportInit Airport to find neighbors for
	 * @param range Range to scan
	 * @return
	 */
	public HashMap<Integer, Airport> scanNearbyAirports(Airport airportInit, double range) {
		HashMap<Integer, Airport> closeAirports = new HashMap<Integer, Airport>();
		for (Airport airport : airportMap.values()) {
			if (airport.getDistance(airportInit) < range) {
				closeAirports.put(airport.getID(), airport);
			}
		}
		if (closeAirports.containsKey(airportInit.getID())) {
			closeAirports.remove(airportInit.getID(),airportInit);
		}
		return closeAirports;
	}
	
	/**
	 * Gets the closest hub airport for a certain aircraft
	 * @param aircraftIndex Index of aircraft
	 * @return Closest Hub airport
	 */
	public Airport closestHubAirporttoAircraft(int aircraftIndex) {
		HashMap<Integer, Airport> closeAirports = this.scanNearbyAirports(this.aircraftList.get(aircraftIndex).getCurrentAirport(), 14500);
		Airport currentAirport = this.aircraftList.get(aircraftIndex).getCurrentAirport();
		Airport mostClose = new HubAirport(1,1,1,1,1,1);
		double minDistance = Double.MAX_VALUE;
		for (Airport airportDest : airportMap.values()) {
			if (currentAirport.getDistance(airportDest) < minDistance && airportDest.getAirportType() == 0 && !(this.aircraftList.get(aircraftIndex).getVisited().contains(airportDest))) {
				minDistance = currentAirport.getDistance(airportDest);
				mostClose = airportDest;
			}
		}
		return mostClose;
	}
	
	/**
	 * Flies a certain aircraft to nearest hub airport
	 * @param aircraftIndex Index of aircraft
	 * @param hubCounter Counter of how many hub airports are in the simulation, security check
	 * @return Whether flights happened and at least 1 person unloaded
	 */
	public boolean flyToClosestHub(int aircraftIndex, int hubCounter) {
		if (hubCounter > 1 || aircraftList.get(aircraftIndex).getCurrentAirport().getAirportType() != 0) {
		Aircraft aircraft = this.aircraftList.get(aircraftIndex);
		Airport mostCloseHub = this.closestHubAirporttoAircraft(aircraftIndex);
		if (mostCloseHub == null) return false;
		this.fillUp(aircraftIndex);
		if (!aircraft.canFly(mostCloseHub)) return false;
		this.setSeats(aircraftIndex, 200, 50, 0);
		this.setRemainingFirstClass(aircraftIndex);
		this.loadPassengersWithSameDestination(aircraftIndex, mostCloseHub);
		boolean b2 = this.fly(mostCloseHub, aircraftIndex);
		boolean b3 = this.batchUnload(aircraftIndex);
		return b2 && b3;
		}
		return false;
	}
	
	/**
	 * Runner function, does all the thing; randomly spawn aircrafts and fly them to nearest hubs, continue till at least one person unloads
	 * @param hubCounter, Counter of how many hub airports are in the simulation, security check
	 */
	public void autoPilot(int hubCounter) {
		boolean resBool = false;
		for (int j = 0; j < airportMap.size(); j++) {
		for (int i = 0; i < maxAircraftCount; i++) {		
			boolean bo = this.flyToClosestHub(i, hubCounter); 
			resBool = bo || resBool;
			if (resBool) {
				break;
			}
		}
			if (resBool) {
				break;
			}
		}
		}
		
	
	/**
	 * Getter for log Array
	 * @return logArray
	 */
	public ArrayList<String> getLogArray() {
		return this.logArray;
	}
		
}
