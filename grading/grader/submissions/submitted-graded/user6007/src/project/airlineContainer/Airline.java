/**
 * Represents the airline.
 * Does the necessary flight and passenger operations.
 * @author Yigit Kagan Poyrazoglu
 * @author yigit.poyrazoglu@boun.edu.tr
 */


package project.airlineContainer;

import project.airportContainer.Airport;
import project.airlineContainer.aircraftContainer.*;
import project.airlineContainer.concreteContainer.*;
import project.passengerContainer.*;
import project.airportContainer.*;
import java.util.HashMap;
import java.util.ArrayList;

public class Airline {
	int maxAircraftCount;
	double operationalCost;
	double expenses;
	double revenue;
	
	/**
	 * Represents the locations of each aircraft.
	 * HashMap keys store the airport IDs, and values are ArrayLists of aircrafts within them.
	 */
	public HashMap<Airport, ArrayList<Aircraft>> airports = new HashMap<Airport, ArrayList<Aircraft>>();
	//ArrayLists or arrays?
	public HashMap<Integer, ArrayList<Integer>> flightPaths = new HashMap<Integer, ArrayList<Integer>>();
	
	/**
	 * The list of aircrafts owned by the airline. Stored in an ArrayList.
	 */
	public ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>(maxAircraftCount);
	
	
	public Airline() {
		
	}
	
	
	
	//getters and setters
	public int getMaxAircraftCount() {
		return maxAircraftCount;
	}

	public void setMaxAircraftCount(int maxAircraftCount) {
		this.maxAircraftCount = maxAircraftCount;
	}

	public double getOperationalCost() {
		return operationalCost;
	}

	public void setOperationalCost(double operationalCost) {
		this.operationalCost = operationalCost;
	}

	public double getExpenses() {
		return expenses;
	}


	public double getRevenue() {
		return revenue;
	}
	
	public double getProfit() {
		return revenue - expenses;
	}
	

	/**
	 * Flies the aircraft from current airport to toAirport.
	 * Aircraft object changes its airport in this method.
	 * @param toAirport destination of the aircraft
	 * @param aircraftIndex the index of the aircraft in the aircrafts ArrayList
	 * @return
	 */
	public boolean fly(Airport toAirport, int aircraftIndex) {
		//retrieve aircraft
		Aircraft aircraftToFly = aircrafts.get(aircraftIndex);
		Airport currentAirport = aircraftToFly.getCurrentAirportObj();
		//depart the plane from the current airport
		for (Passenger i: ((PassengerAircraft) aircraftToFly).passengers) {
			currentAirport.passengers.remove(i);
		}
		airports.get(currentAirport).remove(aircraftToFly);
		currentAirport.aircrafts.remove(aircraftToFly);
		//fly the plane
		double flight = aircraftToFly.fly(toAirport);
		double runningCost = this.operationalCost * this.aircrafts.size();
		this.expenses += flight + runningCost;
		//land the plane to the toAirport
		Airport destination = toAirport;
		airports.get(destination).add(aircraftToFly);
		aircraftToFly.setCurrentAirport(toAirport);
		toAirport.aircrafts.add(aircraftToFly);
		
		//transfer passenger objects
		for (Passenger p: ((PassengerAircraft) aircraftToFly).passengers) {
			p.setPreviousAirport(currentAirport);
			p.setCurrentAirport(toAirport);
		}
		return true;
	}
	
	/**
	 * Loads the Passenger to the specified aircraft. Adds the operation cost 
	 * to the expenses.
	 * @param passenger The passenger to be loaded
	 * @param airport The airport at which the passenger is boarding the plane	
	 * @param aircraftIndex The index of the aircraft in the aircrafts ArrayList
	 * @return true if successful, false if not
	 */
	public boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		//retrieve aircraft
		Aircraft aircraft = aircrafts.get(aircraftIndex);
		//passenger will board()
		//aircraft will lose one seat accordingly
		passenger.board(passenger.getAvailableSeat(aircraft));
		double loss = ((PassengerAircraft) aircraft).loadPassenger(passenger);
		this.expenses += loss;
		return true;
		
		
	}
	
	/**
	 * Unloads the passenger from the specified aircraft. Adds the ticket price 
	 * to the revenue.
	 * @param passenger The passenger to be unloaded
	 * @param aircraftIndex The aircraft on which the operation is done
	 * @return true if successful, false if not
	 */
	public boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		//retrieve aircraft
		Aircraft aircraft = aircrafts.get(aircraftIndex);
		//passenger will disembark()
		Airport currentAirport = aircraft.getCurrentAirportObj();
		double gain = passenger.disembark(currentAirport, aircraft.getAircraftMultiplier());
		passenger.getCurrentAirport().passengers.add(passenger);
//		((PassengerAircraft) aircraft).passengers.remove(passenger);
		this.revenue += gain;
		return true;
		
	}
	
	//refueling
	/**
	 * Refuels the aircraft by the specified amount. Adds the fuel price to the expenses.
	 * @param aircraft
	 * @param fuelAmount
	 * @return true if and only if the refueling operation is valid
	 */
	public boolean refuel(Aircraft aircraft, double fuelAmount) {
		if (aircraft.canAddFuel(fuelAmount)) {
			expenses += aircraft.addFuel(fuelAmount);
			return true;
		}
		return false;
	}
	/**
	 * Fuels the aircraft to the maximum capacity. 
	 * @param aircraft
	 * @return true 
	 */
	public boolean fillUp(Aircraft aircraft) {
		double cost = aircraft.fillUp();
		expenses += cost;
		
		return true;
	}
	
	/**Creates a JetPassengerAircraft object and stores it in the aircrafts ArrayList.
	 * @return JetPassengerAircraft aircraft
	 */
	public void createJetPassengerAircraft(Airport airport, double operationCost) {
		JetPassengerAircraft aircraft = new JetPassengerAircraft();
		aircraft.setOperationCost(operationCost);
		aircraft.setCurrentAirport(airport);
		airport.addAircraft(1);
		airport.aircrafts.add(aircraft);
		aircrafts.add(aircraft);
		return;
	}
	
	/**Creates a PropPassengerAircraft object and stores it in the aircrafts ArrayList.
	 * @return
	 */
	public void createPropPassengerAircraft(Airport airport, double operationCost) {
		PropPassengerAircraft aircraft = new PropPassengerAircraft();
		aircraft.setOperationCost(operationCost);
		aircraft.setCurrentAirport(airport);
		airport.addAircraft(1);
		airport.aircrafts.add(aircraft);
		aircrafts.add(aircraft);
		return;
	}
	
	/**
	 * Creates a RapidPassengerAircraft object.
	 * @return RapidPassengerAircraft aircraft
	 */
	public void createRapidPassengerAircraft(Airport airport, double operationCost) {
		RapidPassengerAircraft aircraft = new RapidPassengerAircraft();
		aircraft.setOperationCost(operationCost);
		aircraft.setCurrentAirport(airport);
		airport.addAircraft(1);
		airport.aircrafts.add(aircraft);
		aircrafts.add(aircraft);
		return;
	}
	
	/**
	 * Creates a WidebodyPassengerAircraft object.
	 * @return WidebodyPassengerAircraft aircraft
	 */
	public void createWidebodyPassengerAircraft(Airport airport, double operationCost) {
		WidebodyPassengerAircraft aircraft = new WidebodyPassengerAircraft();
		aircraft.setOperationCost(operationCost);
		aircraft.setCurrentAirport(airport);
		airport.addAircraft(1);
		airport.aircrafts.add(aircraft);
		aircrafts.add(aircraft);
		return;
	}
	
	/**
	 * Creates a Hub Airport.
	 * @param ID the ID of the airport
	 * @param x the x-coordinate of the airport.
	 * @param y the y-coordinate of the airport.
	 * @param fuelCost the fuel cost in this airport.
	 * @param operationFee the operation fee of the airport.
	 * @param aircraftCapacity the aircraft capacity of the airport.
	 */
	public void createHubAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		HubAirport airport = new HubAirport(ID, x, y);
		airport.setAircraftCapacity(aircraftCapacity);
		this.airports.put(airport, new ArrayList<Aircraft>(aircraftCapacity));
		airport.setFuelCost(fuelCost);
		airport.setOperationFee(operationFee);
		return;
	}
	
	/**
	 * Creates a Major Airport.
	 * @param ID the ID of the airport
	 * @param x the x-coordinate of the airport.
	 * @param y the y-coordinate of the airport.
	 * @param fuelCost the fuel cost in this airport.
	 * @param operationFee the operation fee of the airport.
	 * @param aircraftCapacity the aircraft capacity of the airport.
	 */
	public void createMajorAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		MajorAirport airport = new MajorAirport(ID, x, y);
		airport.setAircraftCapacity(aircraftCapacity);
		this.airports.put(airport, new ArrayList<Aircraft>(aircraftCapacity));
		airport.setFuelCost(fuelCost);
		airport.setOperationFee(operationFee);
		return;
	}
	
	/**
	 * Creates a Regional Airport.
	 * @param ID the ID of the airport
	 * @param x the x-coordinate of the airport.
	 * @param y the y-coordinate of the airport.
	 * @param fuelCost the fuel cost in this airport.
	 * @param operationFee the operation fee of the airport.
	 * @param aircraftCapacity the aircraft capacity of the airport.
	 */
	public void createRegionalAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		RegionalAirport airport = new RegionalAirport(ID, x, y);
		airport.setAircraftCapacity(aircraftCapacity);
		this.airports.put(airport, new ArrayList<Aircraft>(aircraftCapacity));
		airport.setFuelCost(fuelCost);
		airport.setOperationFee(operationFee);
		return;
	}
	
	
	/**
	 * Converts an array of IDs to an arraylist of airports.
	 * @param array
	 * @return airports the ArrayList of the airport objects.
	 */
	public ArrayList<Airport> convertToAirport(String[] array){
		ArrayList<Airport> airports = new ArrayList<Airport>(array.length);
		for (int i = 0; i < array.length; i++) {
			int ID = Integer.parseInt(array[i]);
			for(Airport m: this.airports.keySet()) {
				if (m.getID() == ID) {
					airports.add(m);
					break;
				}
			}
		}
		return airports;
	}
	
	/**
	 * Creates an economy passenger.
	 * @param ID the ID of the passenger.	
	 * @param weight the weight of the passenger.
	 * @param baggageCount the baggage count of the passenger.
	 * @param strdests A String[] array of the destinations of the passenger.
	 */
	public void createEconomyPassenger(long ID, double weight, int baggageCount, String[] strdests) {
		ArrayList<Airport> destinations = this.convertToAirport(strdests);
		EconomyPassenger passenger = new EconomyPassenger(ID, weight, baggageCount, destinations);
		passenger.setCurrentAirport(destinations.get(0));
		destinations.get(0).passengers.add(passenger);
		return;
		
	}
	/**
	 * Creates a business passenger.
	 * @param ID the ID of the passenger.	
	 * @param weight the weight of the passenger.
	 * @param baggageCount the baggage count of the passenger.
	 * @param strdests A String[] array of the destinations of the passenger.
	 */
	public void createBusinessPassenger(long ID, double weight, int baggageCount, String[] strdests) {
		ArrayList<Airport> destinations = this.convertToAirport(strdests);
		BusinessPassenger passenger = new BusinessPassenger(ID, weight, baggageCount, destinations);
		passenger.setCurrentAirport(destinations.get(0));
		destinations.get(0).passengers.add(passenger);
		return;
		
	}
	/**
	 * Creates a first class passenger.
	 * @param ID the ID of the passenger.	
	 * @param weight the weight of the passenger.
	 * @param baggageCount the baggage count of the passenger.
	 * @param strdests A String[] array of the destinations of the passenger.
	 */
	public void createFirstClassPassenger(long ID, double weight, int baggageCount, String[] strdests) {
		ArrayList<Airport> destinations = this.convertToAirport(strdests);
		FirstClassPassenger passenger = new FirstClassPassenger(ID, weight, baggageCount, destinations);
		passenger.setCurrentAirport(destinations.get(0));
		destinations.get(0).passengers.add(passenger);
		return;
		
	}
	/**
	 * Creates a luxury passenger.
	 * @param ID the ID of the passenger.	
	 * @param weight the weight of the passenger.
	 * @param baggageCount the baggage count of the passenger.
	 * @param strdests A String[] array of the destinations of the passenger.
	 */
	public void createLuxuryPassenger(long ID, double weight, int baggageCount, String[] strdests) {
		ArrayList<Airport> destinations = this.convertToAirport(strdests);
		LuxuryPassenger passenger = new LuxuryPassenger(ID, weight, baggageCount, destinations);
		passenger.setCurrentAirport(destinations.get(0));
		destinations.get(0).passengers.add(passenger);
		return;
		
	}
	
	/**
	 * Arranges the flight paths between airports.
	 * Stores the paths in the flightPaths HashMap.
	 * 
	 */
	public void arrangePaths() {
		ArrayList<Airport> hubs = new ArrayList<Airport>();
		ArrayList<Airport> majors = new ArrayList<Airport>();
		ArrayList<Airport> regionals = new ArrayList<Airport>();
		for (Airport i:this.airports.keySet()) {
			if (i.airportType == 0) {
				hubs.add(i);
			}
			else if (i.airportType == 1) {
				majors.add(i);
			}
			else {
				regionals.add(i);
			}
		}
		
		for (Airport i:hubs) {
			this.flightPaths.put(i.getID(), new ArrayList<Integer>());
			for (Airport j:majors) {
				if (this.getClosestHub(j) == i.getID()) {
					this.flightPaths.get(i.getID()).add(j.getID());
				}
			}
			
		}
		for (Airport i:hubs) {
			for (Airport j:regionals) {
				if (this.getClosestHub(j) == i.getID()) {
					this.flightPaths.get(i.getID()).add(j.getID());
				}
			}
		}
		
		
	}
	
	
	
	
	
	/**
	 * Gets the closest hub airport to the airport.
	 * @deprecated No longer in use.
	 * @param airport
	 * @return
	 */
	public int getClosestHub(Airport airport) {
		double minimalDistance = Double.MAX_VALUE;
		int ID = 0;
		for (Airport j:airports.keySet()) {
			if (j.getAirportType() == 0 & j.getID() != airport.getID()) {
				double distance = j.getDistance(airport);
				if (distance <= minimalDistance) {
					minimalDistance = distance;
					ID = j.getID();
				}
			}
		}
		return ID;
		
	}
	
	
	/**
	 * Gets the closest major airport to the airport.
	 * @deprecated No longer in use.
	 * @param airport
	 * @return
	 */
	public int getClosestMajor(Airport airport) {
		double minimalDistance = Double.MAX_VALUE;
		int ID = 0;
		for (Airport j:airports.keySet()) {
			if (j.getAirportType() == 1) {
				double distance = j.getDistance(airport);
				if (distance <= minimalDistance) {
					minimalDistance = distance;
					ID = j.getID();
				}
			}
		}
		return ID;
		
	}
	
	public int getClosestAirport(Airport airport) {
		double minimalDistance = Double.MAX_VALUE;
		int ID = 0;
		for (Airport j:airports.keySet()) {
			if (j.getID() != airport.getID()) {
				double distance = j.getDistance(airport);
				if (distance <= minimalDistance) {
					minimalDistance = distance;
					ID = j.getID();
				}
			}
		}
		return ID;
		
	}
	
	/**
	 * Returns the closest airport with passengers in it.
	 * @param airport the base airport that the search is made from
	 * @return the ID of the airport.
	 */
	public int getClosestAirportWithPassengers(Airport airport) {
		double minimalDistance = Double.MAX_VALUE;
		int ID = 0;
		for (Airport j:airports.keySet()) {
			boolean passThis = true;
			if (j.getID() != airport.getID()) {
				for (Passenger p: airport.passengers) {
					if (p.getDestinations().indexOf(j) > p.getDestinations().indexOf(airport))
						passThis = false;
						break;
				}
				if (passThis == true)
					continue;
				double distance = j.getDistance(airport);
				if (distance <= minimalDistance) {
					minimalDistance = distance;
					ID = j.getID();
				}
			}
		}
		return ID;
	}
	
	
	/**
	 * A method that returns the ArrayList of the IDs of all airports within the airline.
	 * @return ArrayList<Integer> array the ArrayList of IDs
	 */
	public ArrayList<Integer> getAllAirports() {
		ArrayList<Integer> array = new ArrayList<Integer>(this.airports.keySet().size());
		for (Airport k: this.airports.keySet()) {
			array.add(k.getID());
		}
		return array;
	}
	
	
	
	
	/**
	 * returns the airport object given the ID.
	 * @param ID
	 * @return  aa the corresponding airport object.
	 */
	public Airport getAirport(int ID) {
		Airport aa = null;
		for (Airport i:this.airports.keySet()) {
			if (i.getID() == ID) {
				aa = i;
				break;
			}
		}
		return aa;
	}
	
	
	
	public ArrayList<Passenger> getPassengers(int index){
		return ((PassengerAircraft) this.aircrafts.get(index)).passengers;
	}
	
	/**
	 * Checks if an airport is a future destination for a passenger.
	 * @param passenger
	 * @param airport
	 * @return true if and only if airport is at higher index at passenger's destinations list than its current airport.
	 */
	public boolean canGoTo(Passenger passenger, Airport airport) {
		if (passenger.getDestinations().contains(airport)) {
			if (passenger.getDestinations().indexOf(airport) > passenger.getDestinations().indexOf(passenger.getCurrentAirport()))
				return true;
			else
				return false;
		}
		return false;
	}
	
	public double getDistance(int ID1, int ID2) {
		Airport airport = this.getAirport(ID2);
		return airport.getDistance(this.getAirport(ID1));
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
