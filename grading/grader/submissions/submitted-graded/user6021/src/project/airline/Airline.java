

package project.airline;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import project.airline.aircraft.concrete.*;
import project.airport.*;
import project.airline.aircraft.*;
import project.passenger.*;

/**
 * 
 * @author Kristina Trajkovski
 *
 */
public class Airline {
	/**
	 * maximum number of aircrafts allowed
	 */
	private int maxAircraftCount;
	
	
	/**
	 * number of aircrafts made currently
	 */
	public static int aircraftCount = 0;
	
	
	/**
	 * operational cost of the Airline
	 */
	private double operationalCost;
	
	
	/**
	 * all aircrafts made
	 */
	public static ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
	
	
	/**
	 * all expenses added up
	 */
	private double expenses=0;
	
	
	/**
	 * all revenue added up
	 */
	private double revenue=0;
	
	
	/**
	 * all airports in a map with their ID as a key
	 */
	public static HashMap<Integer, Airport> airports = new HashMap<Integer, Airport>();
	
	
	/**
	 * all the hub airports available
	 */
	public static ArrayList<Integer> myHubs = new ArrayList<Integer>();

	
	/**
	 * all the regional airports available
	 */
	public static ArrayList<Integer> myRegs = new ArrayList<Integer>();
	
	
	/**
	 * all the major airports available
	 */
	public static ArrayList<Integer> myMajors = new ArrayList<Integer>();
	
	
	/**
	 * operation fees of all aircraft types
	 */
	public static double[] aircraftOperationFees;
	
	/**
	 * this field keeps the whole output log
	 */
	public static String log = "";

	/**
	 * Constructor for the airline
	 * 
	 * @param maxAircraftCount maximum number of aircrafts created given as input
	 */
	public Airline(int maxAircraftCount) {
		this.maxAircraftCount = maxAircraftCount;
	}

	/**
	 * 
	 * This method makes sure that Airline stores all of the airports from the input ;
	 * classifies them by their type;
	 * 
	 * @param type hub, regional or major
	 * @param ID unique integer value for each airport
	 * @param x coordinate on the x axis
	 * @param y coordinate on the y axis
	 * @param fuelCost different prices of fuel appear at each airport
	 * @param operationFee operation fee unique for the airport, used in landing and departing calculations
	 * @param aircraftCapacity maximum number of aircrafts an aircraft can store
	 */
	public void createAirport(String type, int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		if (type.equals("hub")) {
			airports.put(ID, new HubAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity));
			Airline.myHubs.add(ID);
		}
		else if (type.equals("major")) {
			airports.put(ID, new MajorAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity));
			Airline.myMajors.add(ID);
		}
		else {
			airports.put(ID, new RegionalAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity));
			Airline.myRegs.add(ID);
		}
	}

	/**
	 * This method makes sure all of the passengers are correctly created;
	 * assigns them the right type;
	 * stores them in their initial airports.
	 * 
	 * @param type specifies a type of passenger (economy, business, luxury/first class)
	 * @param ID unique ID of passenger
	 * @param weight  weight of the passenger
	 * @param baggageCount  number of bags a passenger has
	 * @param destinations  array list of all airports at which a passenger can disembark
	 */
	public void addPassenger(String type, Long ID, double weight, int baggageCount, ArrayList<Integer> destinations) {
		Passenger passenger;
		if (type.equals("business")) {
			passenger=new BusinessPassenger(ID, weight, baggageCount, destinations); 
		}
		else if (type.equals("economy")) {
			passenger=new EconomyPassenger(ID, weight, baggageCount, destinations); 
		}
		else if (type.equals("first")) {
			passenger=new FirstClassPassenger(ID, weight, baggageCount, destinations); 
		}
		else {
			passenger=new LuxuryPassenger(ID, weight, baggageCount, destinations); 
		}
		int firstId = destinations.get(0);
		airports.get(firstId).addPassenger(passenger);

	}

	/**
	 * This boolean method checks if the flight is valid. 
	 * False if: 
	 * targeted airport is already full,
	 * aircraft not at the right airport, or
	 * fuel operations are impossible;
	 * 
	 * REMARK: It is enough to check if the fuel capacity of the aircraft is larger than the needed fuel, because the flight method
	 * in this implementation will only be called with a full tank;
	 * 
	 * Adds running cost to expenses;
	 * 
	 * @param toAirport target airport to which a flight is planned
	 * @param aircraftIndex  index of aircraft with which the flight shall be made
	 * @return  true if flight is valid, false otherwise
	 */
	public boolean fly(Airport toAirport, int aircraftIndex) {
		Aircraft aircraft = aircrafts.get(aircraftIndex);

		double distance = aircraft.getCurrentAirport().getDistance(toAirport);
		double neededFuel = aircraft.getFuelConsumption(distance);
		if (aircraft.getCurrentAirport()==toAirport) {
			return false;
		}
		else if (toAirport.getNoAircrafts()==toAirport.getAircraftCapacity()) {
			return false;
		}
		else if (aircraft.getFuelCapacity()<=neededFuel) {
			return false;
		}
		else {
			return true;}
	}

	/**
	 * This method will be executed once the passengers are already loaded and the validness of the flight has been checked
	 * 
	 * launches the aircraft's flight operations and add the flight cost to expenses
	 * 
	 * @param toAirport target Airport, next destination
	 * @param aircraftIndex index of aircraft with which the flight will be executed
	 */
	public void readyFly(Airport toAirport, int aircraftIndex) {
		this.expenses += getRunningCost();
		Aircraft aircraft = aircrafts.get(aircraftIndex);
		double expense = aircraft.fly(toAirport);
		this.expenses+= expense;
	}


	/**
	 * This method makes sure that loading is valid
	 * False if the passenger cannot be seated to an appropriate seat
	 * 
	 * Adds loading fees to expenses
	 * 
	 * @param Passenger current observed passenger
	 * @param airport current location, before the flight
	 * @param aircraftIndex  index of aircraft to which the passenger will be loaded
	 * @return  true if loading is valid, false otherwise
	 */
	public boolean loadPassenger(Passenger Passenger, Airport airport, int aircraftIndex) {
		Aircraft aircraft = aircrafts.get(aircraftIndex);
		if (Passenger.getCurrAirportIndex() != airport.getID()) {
			return false;

		}
		else if (Passenger.getPassengerWeight() > aircraft.getAvailableWeight()) {
			return false;
		}
		else if ((Passenger.getType()==0)&&(!aircraft.hasEconomySeat())) {
			return false;
		}
		else if ((Passenger.getType()==1)&&(!aircraft.hasEconomySeat())&&(!aircraft.hasBusinessSeat())) {

			return false;
		}
		else if ((Passenger.getType()==2)&&(!aircraft.hasEconomySeat())&&(!aircraft.hasBusinessSeat())&&(!aircraft.hasFirstClassSeat())) {
			return false;
		}
		else {
			double expense = aircraft.loadPassenger(Passenger);
			this.expenses += expense;
			return true;
		}
	}

	/**
	 * This method collects the revenue or cost  after landing from all the passengers who were aboard
	 * 
	 * If a passenger could disembark, there is a revenue, otherwise there is a cost
	 * 
	 * @param Passenger  passenger currently observed
	 * @param aircraftIndex  index of the aircraft from which a passenger should unload
	 * @return true if passenger could disembark, false otherwise
	 */
	public boolean unloadPassenger(Passenger Passenger, int aircraftIndex) {
		Aircraft aircraft = aircrafts.get(aircraftIndex);
		double revenueOrCost = aircraft.unloadPassenger(Passenger);
		this.revenue += revenueOrCost;
		return true;
	}

	/**
	 * This method makes sure that the methods refueling are called and the costs added to general expenses
	 * 
	 * 
	 * @param aircraftIndex  index of the aircraft to be refueled
	 * @param fuel  amount of fuel that should be added
	 */
	public void refuel(int aircraftIndex, double fuel) {
		Aircraft aircraft = aircrafts.get(aircraftIndex);
		this.expenses+=aircraft.addFuel(fuel);
		aircrafts.set(aircraftIndex, aircraft);
	}

	/**
	 * This method makes sure that the expenses of maximum refueling are called and returns added to general expenses
	 * @param aircraftIndex index of the aircraft that should be filled up (to maximum capacity)
	 */
	public void maxRefuel (int aircraftIndex) {
		Aircraft aircraft = aircrafts.get(aircraftIndex);
		double fuelExpense = aircraft.fillUp();
		this.expenses+=fuelExpense;
		aircrafts.set(aircraftIndex, aircraft);
	}

	/**
	 * This method lets an aircraft get rid of excess fuel, no costs but no refunds
	 * 
	 * @param aircraftIndex  index of aircraft that should dump fuel
	 * @param fuel  amount of fuel to be dumped
	 */
	public void dumpfuel(int aircraftIndex, double fuel) {
		Aircraft aircraft = aircrafts.get(aircraftIndex);
		this.expenses+=aircraft.dumpFuel(fuel);
		aircrafts.set(aircraftIndex, aircraft);
	}

	/**
	 * this method creates a new Aircraft and adds it to the ArrayList of Aircrafts in Airline
	 * 
	 * the if clauses were decided according to approximate effects from aircraft type multipliers, passenger multipliers etc.
	 * 
	 * False if there is no point in creating an aircraft for this relation
	 * 
	 * 
	 * @param airport initial airport of the aircraft
	 * @param distance distance between the airports of the relation
	 * @param economy number of potential economy passengers on this relation
	 * @param business number of potential business passengers on this relation
	 * @param first number of potential first class or luxury passengers on this relation
	 * @param toAirport targeted airport, next potential destination
	 * @return true if an aircraft was created, false otherwise
	 */
	public boolean createAircraft(Airport airport, Airport toAirport, double distance, int economy, int business, int first) {
		Aircraft newAircraft;
		if ((2000<distance)&&(distance<4000)&&(first>=3)) {
			newAircraft = new JetPassengerAircraft(airport);
			Airline.aircrafts.add(newAircraft);
			Airline.log += String.format("0 %d 3\n", airport.getID());
			Airline.aircraftCount+=1;
			newAircraft.setSeats(economy, business, first);
			airports.get(airport.getID()).addAircraft(toAirport, Airline.aircraftCount-1);
			return true;
		}
		else if (distance<2000) {
			newAircraft = new PropPassengerAircraft(airport);
			Airline.log += String.format("0 %d 0\n", airport.getID());
			Airline.aircrafts.add(newAircraft);
			Airline.aircraftCount+=1;
			newAircraft.setSeats(economy, business, first);
			airports.get(airport.getID()).addAircraft(toAirport, Airline.aircraftCount-1);
			return true;
		}
		else if ((distance<7000)&&(economy+business+first>=100)) {
			newAircraft = new RapidPassengerAircraft(airport);
			Airline.log += String.format("0 %d 2\n", airport.getID());
			Airline.aircrafts.add(newAircraft);
			Airline.aircraftCount+=1;
			newAircraft.setSeats(economy, business, first);
			airports.get(airport.getID()).addAircraft(toAirport, Airline.aircraftCount-1);
			return true;
		}
		else if ((distance<14000)&&(economy+business+first>=200)){
			newAircraft = new WidebodyPassengerAircraft(airport);
			Airline.log += String.format("0 %d 1\n", airport.getID());
			Airline.aircrafts.add(newAircraft);
			Airline.aircraftCount+=1;
			newAircraft.setSeats(economy, business, first);
			airports.get(airport.getID()).addAircraft(toAirport, Airline.aircraftCount-1);
			return true;
		}
		else {
			return false;
		}

	}
	/**
	 * 
	 * @return running cost, used when checking validity of flights
	 */
	public double getRunningCost() {
		return this.operationalCost*aircraftCount;
	}


	/**
	 * @return the profit at the end
	 */
	public double getProfit() {
		return this.revenue-this.expenses;
	}


	/**
	 * @return total number of aircrafts currently in the system
	 */
	public int getAircraftCount() {
		return Airline.aircraftCount;
	}


	/**
	 * Sets the operationfees of all aircraft types from input to the field
	 * They will be called and assigned accordingly in aircraft classes
	 * @param operationFees : list of all aircraft operation fees from the input
	 */
	public void setOperationFees(double[] operationFees) {
		Airline.aircraftOperationFees = operationFees;
	}


	/**returns the operational cost called when checking for the validity of a flight
	 * @param operationalCost: constant given in input
	 */
	public void setOperationalCost(double operationalCost) {
		this.operationalCost = operationalCost;
	}

	
	/**
	 * This is the method called from the main which launches all of the flight operations and calculations;
	 * if there are no potential flights to a special airport type, none of the upcoming operations are executed;
	 * potential passengers of a flight will be chosen and classified by their type;
	 * a new aircraft will be made if possible/profitable;
	 * all flight checks, refuels, loading, and unloading will be launched from here;
	 * 
	 * My strategy is to go through the potential airport relations, 
	 * assign an aircraft (according to the distance and type and number of potential passengers) to a relation, 
	 * assign the seats according to the passengers, and try to load and disembark as many passengers for each line;
	 * 
	 * This method will be called 7 times in total, looking for possible and profitable airport flight combinations:
	 * regional-major, regional-hub, major-hub, hub-hub, hub-major, hub-regional, major-regional.
	 * With this combination of flights, many passengers will be gathered at larger airports in the middle which accordingly makes more expensive flights possible
	 * 
	 * 
	 * @param from gives the ArrayList of the airport type from which the flights will be starting
	 * @param to gives the airport type to which the flights will be going
	 */
	public void typeFlights(ArrayList<Integer> from, String to) {
		for (int i:from) {
			Airport currAirport = Airline.airports.get(i);
			Set<Integer> potAirports = this.getAirportSet(to, currAirport);
			if (potAirports.isEmpty()) { 
				continue;
			}
			else {
				for (int j:potAirports) {
					Airport toAirport = Airline.airports.get(j);
					double distance = currAirport.getDistance(toAirport);
					ArrayList<Passenger> currentPassengers = this.getCurrentPassengers(currAirport, toAirport);

					ArrayList<Passenger> FLPassengers = new ArrayList<Passenger>();
					ArrayList<Passenger> BPassengers = new ArrayList<Passenger>();
					ArrayList<Passenger> EPassengers = new ArrayList<Passenger>();

					for (Passenger p:currentPassengers) { /** Classifies the potential passengers by their type*/
						if((p instanceof FirstClassPassenger)||(p instanceof LuxuryPassenger)) {
							FLPassengers.add(p);
						}
						else if (p instanceof BusinessPassenger) {
							BPassengers.add(p);
						}
						else {
							EPassengers.add(p);
						}
					}


					Aircraft aircraft=null;
					if (!currAirport.getLinkedWith().contains(toAirport.getID())) { /** If the two chosen airports don't already have an aircraft between them, a new one shall be created if possible/profitable*/
						if (this.getAircraftCount()<this.maxAircraftCount) {
							if(this.createAircraft(currAirport, toAirport, distance, EPassengers.size(), BPassengers.size(), FLPassengers.size())) {
								aircraft = Airline.aircrafts.get(aircraftCount-1);
								currAirport.getLinkedWith().add(toAirport.getID());
								toAirport.getLinkedWith().add(currAirport.getID());}
						}
					}
					if (aircraft!=null) {
						boolean canFly = this.fly(toAirport, Airline.aircrafts.indexOf(aircraft)); /** Calls the method which will check the validity of a flight*/
						if (canFly) { /** if flight is valid...*/
							this.maxRefuel(Airline.aircrafts.indexOf(aircraft)); /**fill up the aircraft*/
							
							/** The following loops attempt to load all of the potential passengers
							 * 
							 * Passengers of higher class have priority
							 * 
							 * */
				
							
							ArrayList<Passenger> passengersOnBoard = new ArrayList<Passenger>();
							for (Passenger passenger:FLPassengers) {
								if (passenger.getDestinations().contains(toAirport.getID())) {
									if(this.loadPassenger(passenger, currAirport, Airline.aircrafts.indexOf(aircraft))) {
										passengersOnBoard.add(passenger); /** If a passenger is successfully loaded, it will be stored in this ArrayList*/
									}
								}
							}
							for (Passenger passenger:BPassengers) {
								if (passenger.getDestinations().contains(toAirport.getID())) {
									if(this.loadPassenger(passenger, currAirport, Airline.aircrafts.indexOf(aircraft))) {
										passengersOnBoard.add(passenger);
									}
								}
							}
							for (Passenger passenger:EPassengers) {
								if (passenger.getDestinations().contains(toAirport.getID())) {
									if(this.loadPassenger(passenger, currAirport, Airline.aircrafts.indexOf(aircraft))) {
										passengersOnBoard.add(passenger);
									}
								}
							}
							/** once all of the passengers are loaded, the real fly method containing all of the operations is executed*/
							this.readyFly(toAirport, Airline.aircrafts.indexOf(aircraft));
							
							
							/**the following makes sure all passengers aboard at least attempt to unload*/
							for (Passenger passenger:passengersOnBoard) {
								this.unloadPassenger(passenger, Airline.aircrafts.indexOf(aircraft));
							}
						}
					}
				}
			}

		}


		

	}
	
	
	/**
	 * This method chooses the right airport type set for all of the current flight operations;
	 * getPotentialHubs(), getPotentialRegs() and getPotentialMajors() in airport return a set of a specific type of airports to which passengers will want to fly for sure;
	 * 
	 * @param to  type of the wanted airport
	 * @param currAirport  the initial destination
	 * @return a set of airports of a desired type to which a flight could be made
	 */
	private Set<Integer> getAirportSet(String to, Airport currAirport) {
		Set<Integer>potAirports = null;
		if (to.equals("hub")) {
			potAirports = currAirport.getPotentialHubs().keySet();
		}
		else if (to.equals("regional")) {
			potAirports = currAirport.getPotentialRegs().keySet(); 
		}
		else {
			potAirports = currAirport.getPotentialMajors().keySet(); 
		}
		return potAirports;
	}
	
	/**
	 * This method returns an ArrayList of all potential passengers of a specific flight
	 * @param currAirport  current location of passengers
	 * @param toAirport  final destination
	 * @return  list of passengers who have the final destination in their destination list
	 */
	private ArrayList<Passenger> getCurrentPassengers(Airport currAirport, Airport toAirport) {
		@SuppressWarnings("unchecked")
		ArrayList<Passenger> allPassengers = (ArrayList<Passenger>) currAirport.getPassengers().clone();
		ArrayList<Passenger> currentPassengers = new ArrayList<Passenger>();
		for (Passenger pas:allPassengers) {
			if (pas.getDestinations().contains(toAirport.getID())) {
				currentPassengers.add(pas);
			}
		}
		return currentPassengers;
	}

	/**
	 * This method will be called if no flights were made previously
	 * it will take a passenger and take it to its next destination with a widebody aircraft
	 * 
	 */
	public void extraFlight() {
		for (Airport i: Airline.airports.values()) {
			if (!i.getPassengers().isEmpty()) {
				Passenger passenger = i.getPassengers().get(0);
				Airport toAirport = Airline.airports.get(passenger.getDestinations().get(1));
				Aircraft newAircraft = new WidebodyPassengerAircraft(i);
				Airline.log += String.format("0 %d 1\n", i.getID());
				Airline.aircrafts.add(newAircraft);
				Airline.aircraftCount+=1;
				newAircraft.setSeats(1, 0, 0);
				this.loadPassenger(passenger, i, 0);
				this.maxRefuel(0);
				i.addAircraft(toAirport, Airline.aircraftCount-1);
				ArrayList<Integer> airportPath = new ArrayList<Integer>();

				airportPath.add(i.getID());
				airportPath.add(toAirport.getID());
				this.emergencyFlight(i, toAirport, newAircraft, airportPath);
				this.unloadPassenger(passenger, 0);
				break;
			}
		}
		
	}
	
	

	/** 
	 * 
	 * This method will find the right emergency flight combination;
	 * It will make sure that the aircraft makes necessary landings in order to reach the target by using a recursive call;
	 * It will arrange a flight if it finds an airport which is reachable but also closer to the target;
	 * 
	 * @param currAirport  subject to change, starts as the initial position of passenger, changes as transfers are needed
	 * @param toAirport  the last targeted destination
	 * @param newAircraft  aircraft with which the passenger is traveling
	 * @param path  list of airports already visited (prevents infinite recursion)
	 */
	public void emergencyFlight(Airport currAirport, Airport toAirport, Aircraft newAircraft, ArrayList<Integer> path) {
		Airport transfer = currAirport;
		double distance = 10000000;
		if (currAirport.getDistance(toAirport)<14000) {
			this.readyFly(toAirport, Airline.aircrafts.indexOf(newAircraft));
		}
		else {
			for (Airport i:Airline.airports.values()) {
				if ((!path.contains(i.getID()))&&(currAirport.getDistance(i)<14000)&&(i.getDistance(toAirport)<distance)) {
					distance = i.getDistance(toAirport);
					transfer= i;

				}
			}
			path.add(transfer.getID());
			this.readyFly(transfer, Airline.aircrafts.indexOf(newAircraft));
			this.maxRefuel(Airline.aircrafts.indexOf(newAircraft));
			emergencyFlight(transfer, toAirport, newAircraft, path);

		}

	}
}

