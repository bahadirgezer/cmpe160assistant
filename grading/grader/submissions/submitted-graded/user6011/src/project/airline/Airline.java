package project.airline;

import java.util.ArrayList;
import java.util.HashMap;

import project.airline.aircraft.PassengerAircraft;
import project.airline.aircraft.concrete.JetPassengerAircraft;
import project.airline.aircraft.concrete.PropPassengerAircraft;
import project.airline.aircraft.concrete.RapidPassengerAircraft;
import project.airline.aircraft.concrete.WidebodyPassengerAircraft;
import project.airport.Airport;
import project.airport.HubAirport;
import project.airport.MajorAirport;
import project.airport.RegionalAirport;
import project.passenger.BusinessPassenger;
import project.passenger.EconomyPassenger;
import project.passenger.FirstClassPassenger;
import project.passenger.LuxuryPassenger;
import project.passenger.Passenger;

public class Airline {
	
	int maxAircraftCount;
	double operationalCost;
	double revenue = 0;
	double expenses = 0;
	protected ArrayList<PassengerAircraft> aircrafts = new ArrayList<PassengerAircraft>();
	protected ArrayList<Airport> airportsArrayList = new ArrayList<Airport>();
	protected HashMap<Integer, Airport> airports = new HashMap<Integer, Airport>();
	protected HashMap<Long, Passenger> passengers = new HashMap<Long, Passenger>();
	
	/**
	 * 
	 * @param maxAircraftCount Maximum aircraft number airline can operate
	 * @param operationalCost Given operational cost of airline
	 */
	public Airline(int maxAircraftCount, double operationalCost){
		this.maxAircraftCount = maxAircraftCount;
		this.operationalCost = operationalCost;
	}
	
	/**
	 * 
	 * @param toAirport Airport that aircraft will land
	 * @param aircraftIndex Index of aircraft in aircrafts Array List
	 * @return If a flight operation is possible or not
	 */
	public boolean fly(Airport toAirport, int aircraftIndex) {
		expenses += aircrafts.size()*operationalCost;
		if (aircrafts.get(aircraftIndex).canFly(toAirport)) {
			expenses += aircrafts.get(aircraftIndex).fly(toAirport);
			System.out.println("1 " + toAirport.getID() + " " + aircraftIndex);
			return true;
		}
		return false;
	}
	
	 /**
	  * 
	  * @param airport The airport where passenger and aircraft is located
	  * @param passenger The passenger that will be loaded to aircraft
	  * @param aircraftIndex Index of aircraft in aircrafts Array List
	  * @return If the load operation can happen or not
	  */
	public boolean loadPassenger(Airport airport, Passenger passenger, int aircraftIndex) {
		if (!(airport.getPassengers().containsKey(passenger.getID()) && aircrafts.get(aircraftIndex).getCurrentAirport().equals(airport))) {
			return false;
		}
		if (aircrafts.get(aircraftIndex).getAvailableWeight() + passenger.getWeight() > aircrafts.get(aircraftIndex).getMaxWeight()) {
			expenses += aircrafts.get(aircraftIndex).loadPassenger(passenger);
			return false;
		}
		for (int i = passenger.getPassengerType(); i >= 0; i--) {
			if (aircrafts.get(aircraftIndex).isFull(i)) {
				continue;
			}
			expenses += aircrafts.get(aircraftIndex).loadPassenger(passenger);
			System.out.println("4 " + passenger.getID() + " " + aircraftIndex + " " + airport.getID());
			return true;
		}
		expenses += aircrafts.get(aircraftIndex).loadPassenger(passenger);
		return false;
	}
	
	 /**
	  * 
	  * @param passenger The passenger that will be unloaded from aircraft
	  * @param aircraftIndex Index of aircraft in aircrafts Array List
	  * @return If the unload operation can happen or not
	  */
	public boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		if (passenger.canDisembark(aircrafts.get(aircraftIndex).getCurrentAirport())) {
			revenue += aircrafts.get(aircraftIndex).unloadPassenger(passenger);
			System.out.println("5 " + passenger.getID() + " " + aircraftIndex + " " + aircrafts.get(aircraftIndex).getCurrentAirport().getID());
			return true;
		}
		expenses += aircrafts.get(aircraftIndex).unloadPassenger(passenger);
		return false;
	}
	
	/**
	 * 
	 * @param passenger The passenger that will be transferred to another aircraft
	 * @param aircraftIndex Index of the aircraft passenger will transfer from
	 * @param toAircraftIndex Index of the aircraft passenger will transfer to
	 * @return If the transfer operation can happen or not
	 */
	public boolean transferPassenger(Passenger passenger, int aircraftIndex, int toAircraftIndex) {
		double expense = aircrafts.get(aircraftIndex).transferPassenger(passenger, aircrafts.get(toAircraftIndex));
		expenses += expense;
		if (expense == aircrafts.get(toAircraftIndex).getOperationFee()) {
			return false;
		}
		System.out.println("6 " + passenger.getID() + " " + aircraftIndex + " " + toAircraftIndex + " " + aircrafts.get(aircraftIndex).getCurrentAirport().getID());
		return true;
	}
	
	/**
	 * 
	 * @param fuel Fuel amount that will be added to aircraft
	 * @param aircraftIndex Index of aircraft in aircrafts Array List
	 * @return If fueling operation can happen or not
	 */
	public boolean addFuel(double fuel, int aircraftIndex) {
		if (aircrafts.get(aircraftIndex).canAddFuel(fuel)) {
			if (fuel < 0) {
				aircrafts.get(aircraftIndex).addFuel(fuel);
				System.out.println("3 " + aircraftIndex + " " + fuel);
				return true;
			}
			expenses += aircrafts.get(aircraftIndex).addFuel(fuel);
			System.out.println("3 " + aircraftIndex + " " + fuel);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param toAirport Airport that aircraft will fly to
	 * @param aircraftIndex Index of aircraft in aircrafts Array List
	 * @return If fueling operation can happen or not
	 */
	public boolean addNeededFuel(Airport toAirport, int aircraftIndex) {
		return addFuel(aircrafts.get(aircraftIndex).neededFuel(toAirport, 0, aircrafts.get(aircraftIndex).getFuelCapacity(), aircrafts.get(aircraftIndex).getFuel(), 5, true), aircraftIndex);
	}
	
	/**
	 * 
	 * @param aircraftIndex Index of aircraft that will fill the fuel tank up
	 * @return If fueling operation can happen or not
	 */
	public boolean fillUp(int aircraftIndex) {
		if (aircrafts.get(aircraftIndex).canFillUp()) {
			double initialFuel = aircrafts.get(aircraftIndex).getFuel();
			expenses += aircrafts.get(aircraftIndex).fillUp();
			System.out.println("3 " + aircraftIndex + " " + (aircrafts.get(aircraftIndex).getFuelCapacity()-initialFuel));
			return true;
		}
		return false;
	}
	 /**
	  * 
	  * @return If a new aircraft can be created or not
	  */
	boolean canCreateAircraft() {
		if (aircrafts.size() == maxAircraftCount) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param operationFee Operation fee of the this type of aircraft
	 * @param airport Airport where the aircraft will be created
	 */
	public void createPropPassengerAircraft(double operationFee, Airport airport) {
		PassengerAircraft prop = new PropPassengerAircraft(operationFee, airport);
		aircrafts.add(prop);
		String log = "0 " + airport.getID() + " 0";
		System.out.println(log);
	}
	
	/**
	 * 
	 * @param operationFee operationFee Operation fee of the this type of aircraft
	 * @param airport Airport where the aircraft will be created
	 */
	public void createWidebodyPassengerAircraft(double operationFee, Airport airport) {
		PassengerAircraft widebody = new WidebodyPassengerAircraft(operationFee, airport);
		aircrafts.add(widebody);
		String log = "0 " + airport.getID() + " 1";
		System.out.println(log);
	}
	
	/**
	 * 
	 * @param operationFee operationFee Operation fee of the this type of aircraft
	 * @param airport Airport where the aircraft will be created
	 */
	public void createRapidPassengerAircraft(double operationFee, Airport airport) {
		PassengerAircraft rapid = new RapidPassengerAircraft(operationFee, airport);
		aircrafts.add(rapid);
		String log = "0 " + airport.getID() + " 2";
		System.out.println(log);
	}
	
	/**
	 * 
	 * @param operationFee operationFee Operation fee of the this type of aircraft
	 * @param airport Airport where the aircraft will be created
	 */
	public void createJetPassengerAircraft(double operationFee, Airport airport) {
		PassengerAircraft jet = new JetPassengerAircraft(operationFee, airport);
		aircrafts.add(jet);
		String log = "0 " + airport.getID() + " 3";
		System.out.println(log);
	}
	
	/**
	 * 
	 * @param ID ID number of the passenger created
	 * @param weight Weight of the passenger created
	 * @param baggageCount Number of baggage passenger has
	 * @param destinations Array List of destinations that passenger wants to fly
	 */
	public void createEconomyPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		Passenger economy = new EconomyPassenger(ID, weight, baggageCount, destinations);
		economy.getDestinations().get(0).addPassenger(economy);
		passengers.put(ID, economy);
	}
	
	/**
	 * 
	 * @param ID ID number of the passenger created
	 * @param weight Weight of the passenger created
	 * @param baggageCount Number of baggage passenger has
	 * @param destinations Array List of destinations that passenger wants to fly
	 */
	public void createBusinessPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		Passenger business = new BusinessPassenger(ID, weight, baggageCount, destinations);
		business.getDestinations().get(0).addPassenger(business);
		passengers.put(ID, business);
	}
	
	/**
	 * 
	 * @param ID ID number of the passenger created
	 * @param weight Weight of the passenger created
	 * @param baggageCount Number of baggage passenger has
	 * @param destinations Array List of destinations that passenger wants to fly
	 */
	public void createFirstClassPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		Passenger firstClass = new FirstClassPassenger(ID, weight, baggageCount, destinations);
		firstClass.getDestinations().get(0).addPassenger(firstClass);
		passengers.put(ID, firstClass);
	}
	
	/**
	 * 
	 * @param ID ID number of the passenger created
	 * @param weight Weight of the passenger created
	 * @param baggageCount Number of baggage passenger has
	 * @param destinations Array List of destinations that passenger wants to fly
	 */
	public void createLuxuryPassenger(long ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
		Passenger luxury = new LuxuryPassenger(ID, weight, baggageCount, destinations);
		luxury.getDestinations().get(0).addPassenger(luxury);
		passengers.put(ID, luxury);
	}
	
	/**
	 * 
	 * @param ID ID number of the airport
	 * @param x X coordinate of the airport
	 * @param y Y coordinate of the airport
	 * @param fuelCost Cost of fuel at the created airport
	 * @param operationFee Operation fee of the airport
	 * @param aircraftCapacity Maximum number of aircrafts that airport can have
	 */
	public void createHubAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		Airport hub = new HubAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
		airports.put(ID, hub);
		airportsArrayList.add(hub);
	}
	
	/**
	 * 
	 * @param ID ID number of the airport
	 * @param x X coordinate of the airport
	 * @param y Y coordinate of the airport
	 * @param fuelCost Cost of fuel at the created airport
	 * @param operationFee Operation fee of the airport
	 * @param aircraftCapacity Maximum number of aircrafts that airport can have
	 */
	public void createMajorAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		Airport major = new MajorAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
		airports.put(ID, major);
		airportsArrayList.add(major);
	}
	
	/**
	 * 
	 * @param ID ID number of the airport
	 * @param x X coordinate of the airport
	 * @param y Y coordinate of the airport
	 * @param fuelCost Cost of fuel at the created airport
	 * @param operationFee Operation fee of the airport
	 * @param aircraftCapacity Maximum number of aircrafts that airport can have
	 */
	public void createRegionalAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		Airport regional = new RegionalAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
		airports.put(ID, regional);
		airportsArrayList.add(regional);
	}
	
	/**
	 * 
	 * @param aircraftIndex Index of aircraft in aircrafts Array List
	 * @param economy Economy seats the aircraft will have
	 * @param business Business seats that aircraft will have
	 * @param firstClass First Class seats that aircraft will have
	 */
	public void setSeats(int aircraftIndex, int economy, int business, int firstClass) {
		aircrafts.get(aircraftIndex).setSeats(economy, business, firstClass);
	}
	
	/**
	 * 
	 * @param aircraftIndex Index of the aircraft that will be setted to completely have economy seats
	 */
	public void setAllEconomy(int aircraftIndex) {
		aircrafts.get(aircraftIndex).setAllEconomy();
		System.out.println("2 " + aircraftIndex + " " + aircrafts.get(aircraftIndex).getEconomySeats() + " " + aircrafts.get(aircraftIndex).getBusinessSeats() + " " + aircrafts.get(aircraftIndex).getFirstClassSeats());
	}
	
	/**
	 * 
	 * @param aircraftIndex Index of the aircraft that will be setted to completely have business seats
	 */
	public void setAllBusiness(int aircraftIndex) {
		aircrafts.get(aircraftIndex).setAllBusiness();
		System.out.println("2 " + aircraftIndex + " " + aircrafts.get(aircraftIndex).getEconomySeats() + " " + aircrafts.get(aircraftIndex).getBusinessSeats() + " " + aircrafts.get(aircraftIndex).getFirstClassSeats());
	}
	
	/**
	 * 
	 * @param aircraftIndex Index of the aircraft that will be setted to completely have first class seats
	 */
	public void setAllFirstClass(int aircraftIndex) {
		aircrafts.get(aircraftIndex).setAllFirstClass();
		System.out.println("2 " + aircraftIndex + " " + aircrafts.get(aircraftIndex).getEconomySeats() + " " + aircrafts.get(aircraftIndex).getBusinessSeats() + " " + aircrafts.get(aircraftIndex).getFirstClassSeats());
	}
	
	/**
	 * 
	 * @param aircraftIndex Index of the aircraft that will be setted economy seats to its remaining floor area
	 */
	public void setRemainingEconomy(int aircraftIndex) {
		aircrafts.get(aircraftIndex).setRemainingEconomy();
		System.out.println("2 " + aircraftIndex + " " + aircrafts.get(aircraftIndex).getEconomySeats() + " " + aircrafts.get(aircraftIndex).getBusinessSeats() + " " + aircrafts.get(aircraftIndex).getFirstClassSeats());
	}
	
	/**
	 * 
	 * @param aircraftIndex Index of the aircraft that will be setted business seats to its remaining floor area
	 */
	public void setRemainingBusiness(int aircraftIndex) {
		aircrafts.get(aircraftIndex).setRemainingBusiness();
		System.out.println("2 " + aircraftIndex + " " + aircrafts.get(aircraftIndex).getEconomySeats() + " " + aircrafts.get(aircraftIndex).getBusinessSeats() + " " + aircrafts.get(aircraftIndex).getFirstClassSeats());
	}
	
	/**
	 * 
	 * @param aircraftIndex Index of the aircraft that will be setted first class seats to its remaining floor area
	 */
	public void setRemainingFirstClassint (int aircraftIndex) {
		aircrafts.get(aircraftIndex).setRemainingFirstClass();
		System.out.println("2 " + aircraftIndex + " " + aircrafts.get(aircraftIndex).getEconomySeats() + " " + aircrafts.get(aircraftIndex).getBusinessSeats() + " " + aircrafts.get(aircraftIndex).getFirstClassSeats());
	}
	
	/**
	 * 
	 * @param ids Array of airport IDs of a passenger's destinations
	 * @return An Array List of Airports of a passenger's destinations
	 */
	public ArrayList<Airport> arrayToArrayList(int[] ids){
		ArrayList<Airport> destinations = new ArrayList<Airport>();
		for (int id : ids) {
			destinations.add(airports.get(id));
		}
		return destinations;
	}
	
	/**
	 * 
	 * @param toAirportID Final destination of the aircraft that a few passengers will be loaded
	 * @param aircraftIndex ID number of the aircraft that passengers will be loaded
	 */
	public void loadManyPassengers(int toAirportID, int aircraftIndex) {
		ArrayList<Passenger> loadingPassengers = new ArrayList<Passenger>();
		for (Passenger passenger : aircrafts.get(aircraftIndex).getCurrentAirport().getPassengers().values()) {
			for (int i = 1; i < passenger.getDestinations().size(); i++) {
				if (passenger.getDestinations().get(i).equals(airports.get(toAirportID))) {
					loadingPassengers.add(passenger);
				}
			}
		}
		for (Passenger passenger : loadingPassengers) {
			loadPassenger(aircrafts.get(aircraftIndex).getCurrentAirport(), passenger, 0);
		}
	}
	
	/**
	 * 
	 * @param aircraftIndex ID number of the aircraft that all the passengers will be unloaded
	 */
	public void unloadAllPassengers(int aircraftIndex) {
		ArrayList<Passenger> unloadingPassengers = new ArrayList<Passenger>();
		for (Passenger passenger : aircrafts.get(aircraftIndex).getPassengers().values()) {
			unloadingPassengers.add(passenger);
		}
		for (Passenger passenger : unloadingPassengers) {
			unloadPassenger(passenger, aircraftIndex);
		}
		
	}

	/**
	 * 
	 * @return A Hash Map of airports
	 */
	public HashMap<Integer, Airport> getAirports() {
		return airports;
	}

	/**
	 * 
	 * @return A Hash Map of passengers
	 */
	public HashMap<Long, Passenger> getPassengers() {
		return passengers;
	}
	
	/**
	 * 
	 * @return Profit of airline
	 */
	public double getProfit() {
		return revenue - expenses;
	}

	/**
	 * 
	 * @return An Array List of aircrafts
	 */
	public ArrayList<PassengerAircraft> getAircrafts() {
		return aircrafts;
	}
	
	/**
	 * 
	 * @param aircraftIndex ID of the aircraft
	 * @return Number of passengers the aircraft has
	 */
	public int getAircraftPassengers(int aircraftIndex) {
		return aircrafts.get(aircraftIndex).getPassengers().size();
	}
	
	/**
	 * 
	 * @param airportID ID of the airport
	 * @return Number of passengers that the airport has
	 */
	public int getAirportPassengers(int airportID) {
		return airports.get(airportID).getPassengers().size();
	}

	/**
	 * 
	 * @return An Array List of airports
	 */
	public ArrayList<Airport> getAirportsHashMap() {
		return airportsArrayList;
	}
	
	/**
	 * 
	 * @param airport Airport the flight operation will start
	 * @return Closest airport to the airport flight operation will start
	 */
	public int findClosestAirport(Airport airport) {
		double shortestDistance = 0;
		int closestAirportID = 0;
		for (int i = 0; i < airportsArrayList.size(); i++) {
			if (airport.getDistance(airportsArrayList.get(i)) == 0) {
				continue;
			}else if (shortestDistance == 0 || airport.getDistance(airportsArrayList.get(i)) < shortestDistance) {
				shortestDistance = airport.getDistance(airportsArrayList.get(i));
				closestAirportID = airportsArrayList.get(i).getID();
			}
		}
		return closestAirportID;
	}
}
