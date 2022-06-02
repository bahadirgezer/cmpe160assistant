package project.airline;
import java.util.*;

import project.airline.aircraft.Aircraft;
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
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
/**
 * 
 * @author bora_
 * The class that imports and holds all the other classes
 * 
 * This class imports all the other classes, calls their methods and creates output
 */
public class Airline {
	public int maxAircraftCount;
	public double operationalCost;
	public double expenses = 0;
	public double profits = 0;
	public double propOperationFee;
	public double rapidOperationFee;
	public double widebodyOperationFee;
	public double jetOperationFee;
	public HashMap<Integer,Airport> airportDictionary = new HashMap<Integer,Airport>();
	private LinkedList<Integer> adj[];
	ArrayList<Airport> airports = new ArrayList<Airport>();
	ArrayList<Integer>path = new ArrayList<Integer>();
	ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
	File file;
	FileWriter writer = null;
	BufferedWriter out = null;
/**
 * Construct the airline company with correct parameters
 * @param maxAircraftCount max aircraft count the company can have
 * @param operationalCost operational cost for flying
 * @param propOperationFee operational fee for prop type airplanes
 * @param widebodyOperationFee operational fee for widebody type airplanes
 * @param rapidOperationFee operational fee for rapid type airplanes
 * @param jetOperationFee operational fee for jet type airplanes
 */
	public Airline(int maxAircraftCount, double operationalCost,double propOperationFee,double widebodyOperationFee, double rapidOperationFee, double jetOperationFee,String file,int airportCount) {
		this.maxAircraftCount = maxAircraftCount;
		this.operationalCost = operationalCost;
		this.propOperationFee = propOperationFee;
		this.widebodyOperationFee = widebodyOperationFee;
		this.rapidOperationFee = rapidOperationFee;
		this.jetOperationFee = jetOperationFee;
		this.file = new File(file);
		adj = new LinkedList[airportCount];
		for (int i=0; i<airportCount; ++i)
            adj[i] = new LinkedList();
	}
	/**
	 * Creates output log
	 * @param output the string to be outputted
	 */
	public void createOutput(String output) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			if (writer == null) {
				writer = new FileWriter(file);
				out = new BufferedWriter(writer, 32768);
			}
			out.write(output + "\n");
			}
		catch(IOException e) {
			System.out.println("Error occurred");
			}
	}
	/**
	 * Saves and closes the output log file
	 */
	public void end() {
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Checks every airport to find if a suitable flight operation exists or not
	 * @param airportDictionary the hashmap that holds the airports
	 * @return the best route available
	 */
	public int[] calculateBestDestinations(HashMap<Integer,Airport> airportDictionary) {
		for(Airport airport:airportDictionary.values()) {
			int[] destValue = airport.calculateBestDestination(airportDictionary);
			if (destValue[1]>119){
				Airport targetAirport = airportDictionary.get(destValue[0]);
				if (!targetAirport.isFull()) {
					if (aircrafts.size() == maxAircraftCount && airport.getAircrafts().size() == 0) {
						continue;
					}
					else {
						int[] route = {airport.getID(),destValue[0]};
						return route;
					}
				}
			}
		}
		return null;
	}
	/**
	 * bfs method to find shortest path to destination.
	 * @param start starting node
	 * @param end target node
	 * @param q queue that holds the nodes
	 * @return the shortest path to target node
	 */
	public ArrayList<Integer> bfs(int start, int end,Queue<HashMap<Integer,ArrayList<Integer>>> q) {
		ArrayList<Integer> visited = new ArrayList<Integer>();
		while (q.size() != 0) {
			int vert = 0;
			HashMap<Integer,ArrayList<Integer>> asd = q.poll();
			for (Integer i : asd.keySet()) {
				vert = i;
			}
			ArrayList<Integer> pathx = asd.get(vert);
			visited.add(vert);
			for (Integer j:adj[vert]) {
				if (j == end) {
					pathx.add(j);
					path = (ArrayList<Integer>) pathx.clone();
					return path;
				}
				else {
					if (!visited.contains(j)) {
						visited.add(j);
						HashMap<Integer,ArrayList<Integer>> d = new HashMap<Integer,ArrayList<Integer>>();
						pathx.add(j);
						d.put(j,(ArrayList<Integer>) pathx.clone());
						pathx.remove(j);
						q.add(d);
					}
				}
			}
		}
		return null;
	}
	/**
	 * Carries the first passenger to their destination
	 * 
	 * This method runs only if there are no passengers in short range
	 */
	public void calculateLongRangeDestinations() {
		for(Airport airport:airportDictionary.values()) {
			for (Airport airport2:airportDictionary.values()) {
				if (airport.getDistance(airport2) < 14000 && !airport.equals(airport2)) {
					adj[airport.getOrder()].add(airport2.getOrder());
					adj[airport2.getOrder()].add(airport.getOrder());
				}
			}
		}
		//boolean[] visited = new boolean[adj.length];
		//LinkedList<Integer> queue = new LinkedList<Integer>();
		Airport chosenAirport = null;
		for(Airport airport:airportDictionary.values()) {
			if (airport.getPassengers().size() != 0) {
				chosenAirport = airport;
				break;
			}
		}
		Passenger passenger = chosenAirport.getPassengers().get(0);
		int destination = passenger.getDestinations().get(0);
		Queue<HashMap<Integer,ArrayList<Integer>>> q = new ArrayDeque<>();
		HashMap<Integer,ArrayList<Integer>> s = new HashMap<Integer,ArrayList<Integer>>();
		ArrayList<Integer> visiteds = new ArrayList<Integer>();
		visiteds.add(chosenAirport.getOrder());
		s.put(chosenAirport.getOrder(), visiteds);
		q.add(s);
		bfs(chosenAirport.getOrder(),airportDictionary.get(destination).getOrder(),q);
		System.out.println(path);
		//visited[chosenAirport.getOrder()] = true;
		//queue.add(chosenAirport.getOrder());
		createAircraft(1,chosenAirport.getID());
		PassengerAircraft aircraft = (PassengerAircraft) aircrafts.get(0);
		setSeats(0,5, 5, 15);
		loadPassenger(passenger,chosenAirport,0);
		loadAllPassengers(aircraft,chosenAirport.getID(),destination);
		path.remove(0);
		getToAirport(destination);		
	}
	/**
	 * Recursive method that pathfinds to a distant airport
	 * @param finalDestination desired destination
	 */
	public void getToAirport(int finalDestination) {
		Aircraft aircraft = aircrafts.get(0);
		Airport targetAirport = airports.get(path.get(0));
		path.remove(0);
		fillForAirport(0,targetAirport);
		//System.out.println(aircraft.getCurrentAirport().getID());
		if (canFly(targetAirport,0)) {
			fly(targetAirport,0);
			//System.out.println(aircraft.getCurrentAirport().getDistance(airportDictionary.get(finalDestination)));
			loadAllPassengers(aircraft,targetAirport.getID(),targetAirport.getID());
			if (path.size() == 0) {
				unloadAllPassengers((PassengerAircraft)aircrafts.get(0));
				return;
			}
			getToAirport(finalDestination);
		}
	}
	/**
	 * arranges the flight for a given destination
	 * @param initialAirportID initial airport's id
	 * @param targetAirportID target airport's id
	 */
	public void arrangeFlight(int initialAirportID, int targetAirportID) {
		Airport initialAirport = airportDictionary.get(initialAirportID);
		Airport targetAirport = airportDictionary.get(targetAirportID);
		if (initialAirport.getAircrafts().size() == 0) {
			createAircraft(3,initialAirportID);
			PassengerAircraft aircraft = (PassengerAircraft) initialAirport.getAircrafts().get(0);
			setAllFirstClass(aircrafts.indexOf(aircraft));
		}
		PassengerAircraft aircraft = (PassengerAircraft) initialAirport.getAircrafts().get(0);
		loadAllPassengers(aircraft,initialAirportID,targetAirportID);
		fillForAirport(aircrafts.indexOf(aircraft),targetAirport);
		if (canFly(targetAirport,aircrafts.indexOf(aircraft))) {
			fly(targetAirport,aircrafts.indexOf(aircraft));
			unloadAllPassengers(aircraft);
		}
		
	}
	/**
	 * unload all the passengers in a specific aircraft
	 * @param aircraft 
	 */
	public void unloadAllPassengers(PassengerAircraft aircraft) {
		ArrayList<Passenger> copyPassengerList = (ArrayList<Passenger>)aircraft.getPassengers().clone();
		for (Passenger i:copyPassengerList) {
			unloadPassenger(i,aircrafts.indexOf(aircraft));
		}
	}
	/**
	 * loads the passengers for given route
	 * @param aircraft 
	 * @param initialAirportID
	 * @param targetAirportID
	 */
	public void loadAllPassengers(Aircraft aircraft,int initialAirportID,int targetAirportID) {
		Airport initialAirport = airportDictionary.get(initialAirportID);
		for (int i = 0; i< initialAirport.getPassengers().size();i++) {
			Passenger passenger = initialAirport.getPassengers().get(i);
			if (passenger instanceof LuxuryPassenger && passenger.getDestinations().contains(targetAirportID)) {
				if (canLoadPassenger(passenger,initialAirport,aircrafts.indexOf(aircraft))) {
					loadPassenger(passenger,initialAirport,aircrafts.indexOf(aircraft));
				}
				else {
					break;
				}
			}
		}
		for (int i = 0; i< initialAirport.getPassengers().size();i++) {
			Passenger passenger = initialAirport.getPassengers().get(i);
			if (passenger instanceof FirstClassPassenger && passenger.getDestinations().contains(targetAirportID)) {
				if (canLoadPassenger(passenger,initialAirport,aircrafts.indexOf(aircraft))) {
					loadPassenger(passenger,initialAirport,aircrafts.indexOf(aircraft));
				}
				else {
					break;
				}
			}
		}
	}
	/**
	 * checks if the aircraft is able to carry out the flight or not
	 * @param toAirport target airport
	 * @param aircraftIndex aircraft that will be used in the flight
	 * @return whether the flight can happen or not
	 */
	public boolean canFly(Airport toAirport,int aircraftIndex) {
		Aircraft aircraft = aircrafts.get(aircraftIndex);
		if (aircraft.canFly(toAirport)) {
			if (!toAirport.isFull()) {
				return true;
			}
		}
		return false;
	}
	/**
	 * flies the aircraft to target airport
	 * @param toAirport target airport
	 * @param aircraftIndex id of aircraft that will be used
	 * @return whether the flight is successful or not
	 */
	public boolean fly(Airport toAirport, int aircraftIndex) {
		Aircraft aircraft = aircrafts.get(aircraftIndex);
		double cost1 = aircraft.fly(toAirport);
		double cost2 = aircrafts.size() * operationalCost;
		expenses += cost1+cost2;
		//createOutput("1 "+toAirport.getID()+" "+ aircraftIndex + " = " + -(cost1+cost2));
		createOutput("1 "+toAirport.getID()+" "+ aircraftIndex);
		return true;
	}
	/**
	 * checks if the passenger can be loaded to the aircraft or not
	 * @param passenger the passenger that will be loaded
	 * @param airport
	 * @param aircraftIndex
	 * @return
	 */
	boolean canLoadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		PassengerAircraft aircraft = (PassengerAircraft) aircrafts.get(aircraftIndex);
		if (aircraft.getCurrentAirport().equals(airport)) {
			if (aircraft.canLoadPassenger(passenger)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * checks if the passenger can be transferred to the aircraft or not
	 * @param passenger
	 * @param toAircraftIndex
	 * @return whether the transfer is successful or not
	 */
	boolean canTransferPassenger(Passenger passenger, int toAircraftIndex) {
		PassengerAircraft aircraft = (PassengerAircraft) aircrafts.get(toAircraftIndex);
		if (aircraft.canTransferPassenger(passenger,aircraft)) {
			return true;
		}
		return false;
	}
	/**
	 * loads the passenger to an aircraft
	 * @param passenger
	 * @param airport
	 * @param aircraftIndex
	 * @return
	 */
	boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		PassengerAircraft passengeraircraft = (PassengerAircraft) aircrafts.get(aircraftIndex);
		double cost = passengeraircraft.loadPassenger(passenger);
		expenses += cost;
		//createOutput("4 " + passenger.getID() + " " + aircrafts.indexOf(passengeraircraft) + " " + airport.getID() + " = " + -cost);
		createOutput("4 " + passenger.getID() + " " + aircrafts.indexOf(passengeraircraft) + " " + airport.getID());
		return true;
	}
	/**
	 * transfer the passenger from one aircraft to another
	 * @param passenger
	 * @param aircraftIndex
	 * @return
	 */
	boolean transferPassenger(Passenger passenger, int aircraftIndex) {
		PassengerAircraft aircraft = (PassengerAircraft) aircrafts.get(aircraftIndex);
		expenses += aircraft.transferPassenger(passenger, aircraft);
		return true;
	}
	/** 
	 * unloads the passenger from the aircraft
	 * @param passenger
	 * @param aircraftIndex
	 * @return
	 */
	boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		PassengerAircraft aircraft = (PassengerAircraft) aircrafts.get(aircraftIndex);
		double profit = aircraft.unloadPassenger(passenger);
		if (profit < 0){
			expenses -= profit;
			return false;
		}
		profits += profit;
		//createOutput("5 " + passenger.getID() + " " + aircrafts.indexOf(aircraft) + " " + aircraft.getCurrentAirport().getID() + " = " + profit);
		createOutput("5 " + passenger.getID() + " " + aircrafts.indexOf(aircraft) + " " + aircraft.getCurrentAirport().getID());
		return true;
	}
	/**
	 * refuels the aircraft for the given amount
	 * @param aircraftIndex
	 * @param fuel fuel amount
	 */
	void refuel(int aircraftIndex, double fuel) {
		Aircraft aircraft = aircrafts.get(aircraftIndex);
		double cost = aircraft.addFuel(fuel);
		//createOutput("3 " + aircraftIndex + " " + cost/aircraft.getCurrentAirport().getFuelCost() + " = " + -cost);
		createOutput("3 " + aircraftIndex + " " + cost/aircraft.getCurrentAirport().getFuelCost());
		expenses += cost;
	}
	/**
	 * refuels the aircraft to its max capacity
	 * @param aircraftIndex 
	 */
	void fillUp(int aircraftIndex) {
		Aircraft aircraft = aircrafts.get(aircraftIndex);
		double cost = aircraft.fillUp();
		createOutput("3 " + aircraftIndex + " " + cost/aircraft.getCurrentAirport().getFuelCost());
		expenses += cost;
	}
	/**
	 * refuels the aircraft just enough to get to a specific airport
	 * @param aircraftIndex
	 * @param toAirport
	 */
	void fillForAirport(int aircraftIndex,Airport toAirport) {
		Aircraft aircraft = aircrafts.get(aircraftIndex);
		double cost = aircraft.fillForAirport(toAirport);
		//createOutput("3 " + aircraftIndex + " " + cost/aircraft.getCurrentAirport().getFuelCost() + " = " + -cost);
		createOutput("3 " + aircraftIndex + " " + cost/aircraft.getCurrentAirport().getFuelCost());
		expenses += cost;
	}
	/**
	 * creates and initializes new aircraft object
	 * @param aircraftType type of aircraft that is going to be initialized
	 * @param initialAirportID
	 */
	void createAircraft(int aircraftType,int initialAirportID) {
		if (maxAircraftCount != aircrafts.size()) {
			//createOutput("0 "+initialAirportID+" "+aircraftType + " = " + "0");
			createOutput("0 "+initialAirportID+" "+aircraftType);
			Airport initialAirport = airportDictionary.get(initialAirportID);
			Aircraft aircraft = null;
			if (aircraftType == 0) {
				aircraft = new PropPassengerAircraft(initialAirport,propOperationFee);
			}
			if (aircraftType == 1) {
				aircraft = new WidebodyPassengerAircraft(initialAirport,widebodyOperationFee);
			}
			if (aircraftType == 2) {
				aircraft = new RapidPassengerAircraft(initialAirport,rapidOperationFee);
			}
			if (aircraftType == 3) {
				aircraft = new JetPassengerAircraft(initialAirport,jetOperationFee);
			}
			initialAirport.addAircraft(aircraft);
			aircrafts.add(aircraft);
		}
	}
	/**
	 * creates and initializes new airport object
	 * @param ID id of airport
	 * @param airportType type of airport that is going to be initialized
	 * @param x x coordinate of the airport
	 * @param y y coordinate of the airport
	 * @param fuelCost the price of fuel at this airport
	 * @param operationFee the operationFee that is used in depart and land methods
	 * @param aircraftCapacity max amount of aircrafts this airport can hold
	 */
	public void createAirport(int ID,int airportType, int x, int y,double fuelCost,double operationFee,int aircraftCapacity,int order) {
		Airport airport = null;
		if (airportType == 0) {
			airport = new HubAirport(ID,x,y,fuelCost,operationFee,aircraftCapacity,order);
		}
		if (airportType == 1) {
			airport = new MajorAirport(ID,x,y,fuelCost,operationFee,aircraftCapacity,order);
		}
		if (airportType == 2) {
			airport = new RegionalAirport(ID,x,y,fuelCost,operationFee,aircraftCapacity,order);
		}
		//airports.add(airport);
		airportDictionary.put(ID, airport);
		airports.add(airport);
		//System.out.println("Airport created fuelCost:" + fuelCost);
	}
	/**
	 * creates and initializes new passenger object
	 * @param ID
	 * @param weight weight of passenger
	 * @param baggageCount baggage count of passenger
	 * @param destinations list of destinations that passenger can disembark at
	 * @param type type of passenger
	 */
	public void createPassenger(long ID,double weight,int baggageCount,ArrayList<Integer> destinations,int type) {
		Passenger passenger = null;
		int initialAirportID = destinations.get(0);
		destinations.remove(0);
		Airport initialAirport = airportDictionary.get(initialAirportID);
		if (type == 0) {
			passenger = new EconomyPassenger(ID,weight,baggageCount,destinations,initialAirport);
		}
		if (type == 1) {
			passenger = new BusinessPassenger(ID,weight,baggageCount,destinations,initialAirport);
		}
		if (type == 2) {
			passenger = new FirstClassPassenger(ID,weight,baggageCount,destinations,initialAirport);
		}
		if (type == 3) {
			passenger = new LuxuryPassenger(ID,weight,baggageCount,destinations,initialAirport);
		}
		initialAirport.addPassenger(passenger);
		
	}
	/**
	 * sets the seats of given aircraft
	 * @param aircraftIndex
	 * @param economy number of economy seats
	 * @param business number of business seats
	 * @param firstClass number of firstClass seats
	 * @return 
	 */
	public boolean setSeats(int aircraftIndex, int economy,int business,int firstClass) {
		PassengerAircraft aircraft = (PassengerAircraft) aircrafts.get(aircraftIndex);
		//createOutput("2 " + aircraftIndex + " " + economy + " " + business + " " + firstClass + " = " + "0");
		createOutput("2 " + aircraftIndex + " " + economy + " " + business + " " + firstClass);
		return aircraft.setSeats(economy, business, firstClass);
	}
	/**
	 * set all the seats in an aircraft to economy
	 * @param aircraftIndex
	 * @return
	 */
	public boolean setAllEconomy(int aircraftIndex) { 
		PassengerAircraft aircraft = (PassengerAircraft) aircrafts.get(aircraftIndex);
		if (aircraft instanceof JetPassengerAircraft ) {
			setSeats(aircraftIndex,30,0,0);
		}
		if (aircraft instanceof PropPassengerAircraft ) {
			setSeats(aircraftIndex,60,0,0);
		}
		if (aircraft instanceof RapidPassengerAircraft ) {
			setSeats(aircraftIndex,120,0,0);
		}
		if (aircraft instanceof WidebodyPassengerAircraft ) {
			setSeats(aircraftIndex,450,0,0);
		}
		return aircraft.setAllEconomy();
	}
	/**
	 * set all the seats in an aircraft to business
	 * @param aircraftIndex
	 * @return
	 */
	public boolean setAllBusiness(int aircraftIndex) {
		PassengerAircraft aircraft = (PassengerAircraft) aircrafts.get(aircraftIndex);
		if (aircraft instanceof JetPassengerAircraft ) {
			setSeats(aircraftIndex,0,10,0);
		}
		if (aircraft instanceof PropPassengerAircraft ) {
			setSeats(aircraftIndex,0,20,0);
		}
		if (aircraft instanceof RapidPassengerAircraft ) {
			setSeats(aircraftIndex,0,40,0);
		}
		if (aircraft instanceof WidebodyPassengerAircraft ) {
			setSeats(aircraftIndex,0,150,0);
		}
		return aircraft.setAllBusiness();
	}
	/**
	 * sets all to the seats in an aircraft to firstclass
	 * @param aircraftIndex
	 * @return
	 */
	public boolean setAllFirstClass(int aircraftIndex) { 
		PassengerAircraft aircraft = (PassengerAircraft) aircrafts.get(aircraftIndex);
		if (aircraft instanceof JetPassengerAircraft ) {
			createOutput("2 " + aircraftIndex + " " + 0 + " " + 0 + " " + 3);
		}
		if (aircraft instanceof PropPassengerAircraft ) {
			createOutput("2 " + aircraftIndex + " " + 0 + " " + 0 + " " + 7);
		}
		if (aircraft instanceof RapidPassengerAircraft ) {
			createOutput("2 " + aircraftIndex + " " + 0 + " " + 0 + " " + 15);
		}
		if (aircraft instanceof WidebodyPassengerAircraft ) {
			createOutput("2 " + aircraftIndex + " " + 0 + " " + 0 + " " + 56);
		}
		return aircraft.setAllFirstClass();
	}
	/**
	 * adds economy seats to aircraft based on remaining area
	 * @param aircraftIndex
	 * @return
	 */
	public boolean setRemainingEconomy(int aircraftIndex) {
		PassengerAircraft aircraft = (PassengerAircraft) aircrafts.get(aircraftIndex);
		return aircraft.setRemainingEconomy();
	}
	/**
	 * adds business seats to aircraft based on remaining area
	 * @param aircraftIndex
	 * @return
	 */
	public boolean setRemainingBusiness(int aircraftIndex) {
		PassengerAircraft aircraft = (PassengerAircraft) aircrafts.get(aircraftIndex);
		return aircraft.setRemainingBusiness();
	}
	/**
	 * adds firstclass seats to aircraft based on remaining area
	 * @param aircraftIndex
	 * @return
	 */
	public boolean setRemainingFirstClass(int aircraftIndex) {
		PassengerAircraft aircraft = (PassengerAircraft) aircrafts.get(aircraftIndex);
		return aircraft.setRemainingFirstClass();
	}
}
