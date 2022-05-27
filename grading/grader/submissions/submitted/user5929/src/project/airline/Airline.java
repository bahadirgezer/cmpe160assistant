package project.airline;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

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
	private final boolean DEBUG = false; // WARNING: used for debugging, this should be false for normal execution

	/**
	 * input:
	 */
	private final int maxAircraftCount;

	/**
	 * input:
	 */
	private final double operationalCost;

	/**
	 * input:
	 * stores operational costs of different types of aircraft
	 */
	private final double[] AircraftOperationalCoasts;

	/**
	 * input:
	 * used for logging
	 * passed from main
	 */
	private final FileWriter log;


	private double revenue;
	private double expenses;


	/**
	 * returns revenue less expenses of airline
	 * @return profit
	 */
	public double getProfits() {
		return revenue - expenses;
	}


	/**
	 * aircraft container, indexed by creation order
	 */
	private final ArrayList<PassengerAircraft> aircraft;


	/**
	 * airport container
	 */
	private final HashMap<Integer, Airport> airports;

	public Airline(int maxAircraftCount, double[] operationalCosts, FileWriter log) {
		this.maxAircraftCount = maxAircraftCount;

		// operationalCosts array keeps airline specific cost as last index
		this.operationalCost = operationalCosts[4];
		this.AircraftOperationalCoasts = operationalCosts;


		this.aircraft = new ArrayList<>();
		this.airports = new HashMap<>();
		this.log = log;

		this.expenses = 0;
		this.revenue = 0;
	}



	/**
	 * flies the aircraft at aircraftIndex to toAirport
	 * if flight is cannot happen no change is made
	 * except increase in expenses the by amount of runningCost
	 * @param toAirport airport to fly to
	 * @param aircraftIndex index of aircraft to fly
	 * @return true if flight is successful
	 */
	private boolean canFly(Airport toAirport, int aircraftIndex) {

		return aircraft.get(aircraftIndex).canFly(toAirport);
	}
	private boolean fly(Airport toAirport, int aircraftIndex) {
		double runningCost = this.operationalCost * aircraft.size();
		
		if ( canFly(toAirport,aircraftIndex)) {
			this.expenses += runningCost; //TODO: running cost!!
			double fligtCost = aircraft.get(aircraftIndex).fly(toAirport);
			this.expenses += fligtCost;
			logger(String.format("1 %d %d", toAirport.getID(),aircraftIndex));
			return true;
		}
		else {
//			logger("running cost for invalid flight: ");
			if(DEBUG)System.out.println("fly:cant fly");

		}
		return false;
	}

	/**
	 * Loads passenger at given airport to the aircraft with aircrfatIndex
	 * @param passenger passenger to load
	 * @param airport airport to load from
	 * @param aircraftIndex index of aircraft to load to
	 * @return true if passenger is loaded
	 */
	private boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		boolean isLoadingSuccessfull = false;
		PassengerAircraft currAircraft = aircraft.get(aircraftIndex);

		if (airport.containsPassenger(passenger) && currAircraft.isInAirport(airport) && currAircraft.canLoadPassenger(passenger) ) {
			double loadingCost = currAircraft.loadPassenger(passenger);
			expenses += loadingCost;
			isLoadingSuccessfull = true;
			logger(String.format("4 %d %d %d",passenger.getID(), aircraftIndex,airport.getID())); 

		}

		return isLoadingSuccessfull;

	}

	/**
	 * unload a passenger from a given aircraft
	 * @param passenger passenger to unload
	 * @param aircraftIndex index of aircraft to unload from
	 * @return true if passenger is unloaded
	 */
	private boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		boolean isUnloadSuccessfull = false;
		PassengerAircraft aircraft = this.aircraft.get(aircraftIndex);
		Airport curAirport = aircraft.getCurrentAirport();
		if (passenger == null) {
			return false;
		}

		boolean canDisembark  = passenger.canDisembark(curAirport);
		if(canDisembark) {// unload operation is successful
			double ticketPrice = aircraft.unloadPassenger(passenger);
			if (ticketPrice<0) {
				expenses -= ticketPrice;
				if(DEBUG)System.out.println("You are logging invalid unload!");// TODO: this should never happen
			}else {
			revenue += ticketPrice;
			}
			isUnloadSuccessfull = true; 
			logger(String.format("5 %d %d %d",passenger.getID(), aircraftIndex,curAirport.getID()));
		}else {
			if(DEBUG)System.out.println("unload failed "+ passenger.getID());
		}
		return isUnloadSuccessfull;

	}

// TODO: should there be a method like this??
//	private boolean transferPassenger(Passenger passenger, int fromAircraftIndex, int toAircraftIndex) {
//		return false;
//	}
	/*
	 * Refueling functions:
	 */
	
	
	/**
	 * fills up aircraft fuel to its max fuel capacity
	 * @param aircraftIndex aircraft to fuel
	 * @return if fuel loading is successful
	 */
	private boolean fulle(int aircraftIndex) {
		PassengerAircraft aircraft = this.aircraft.get(aircraftIndex);
		double fuelCost = aircraft.fillUp();
		if (fuelCost != 0) {
			this.expenses += fuelCost;
			double fuel_amount = fuelCost/ this.aircraft.get(aircraftIndex).getCurrentAirport().getFuelCost();
			logger(String.format("3 %d %f", aircraftIndex, fuel_amount));
			return true;

		}else {
			if(DEBUG)System.out.println("cant fuel");
		}
		return false;

	}

	
	/**
	 * loads specified amount of fuel to aircraft
	 * @param aircraftIndex aircraft to fuel
	 * @param fuel amount
	 * @return if fuel loading is successful
	 */
	private boolean loadFuel(int aircraftIndex, double fuel) {
		PassengerAircraft aircraft = this.aircraft.get(aircraftIndex);
		double fuelCost = aircraft.addFuel(fuel);
		if (fuelCost != 0) {
			this.expenses += fuelCost;
			logger(String.format("3 %d %f", aircraftIndex, fuel));
			return true;

		}else {
			
//			if(DEBUG)System.out.println("cant fuel loadFuel");
		}
		return false;
	}

	
	/**
	 * using distance and extra passenger weight calculates optimum fuel amount
	 * to fly a given distance
	 * @param aircraftIndex aircraft to fuel
	 * @param distance to travel
	 * @param passangerWeight extra weight coming from passengers
	 * @return if fuel loading is successful
	 */
	private boolean fillOptimally(int aircraftIndex,double distance,double passangerWeight) {
		PassengerAircraft a = aircraft.get(aircraftIndex);
		double fuelAmount = a.getOptimalFuel(distance,passangerWeight);
		return loadFuel(aircraftIndex, fuelAmount);
	}



	/*
	 * Aircraft creation functions:
	 */

	/**
	 * creates a PropPassengerAircraft
	 * @param airport airport to create the aircraft at
	 * @return true if aircraft is created
	 */
	private boolean addPropPassengerAircraft(Airport airport) {
		double operationalCost = AircraftOperationalCoasts[0];
		if (aircraft.size()>= maxAircraftCount)
			return false;
		if (!airport.isFull()) {
			aircraft.add(new PropPassengerAircraft(airport,operationalCost));
			logger(String.format("0 %d %d",airport.getID(), 0));
			return true;
		}
		return false;
	}
	/**
	 * creates a WidebodyPassengerAircraft
	 * @param airport airport to create the aircraft at
	 * @return true if aircraft is created
	 */
	private boolean addWidebodyPassengerAircraft(Airport airport) {
		double operationalCost = AircraftOperationalCoasts[1];
		if (aircraft.size()>= maxAircraftCount)
			return false;
		if (!airport.isFull() ) {
			aircraft.add(new WidebodyPassengerAircraft(airport,operationalCost));
			logger(String.format("0 %d %d",airport.getID(), 1));
			return true;
		}
		return false;
	}
	/**
	 * creates a RapidPassengerAircraft
	 * @param airport airport to create the aircraft at
	 * @return true if aircraft is created
	 */
	private boolean addRapidPassengerAircraft(Airport airport) {
		double operationalCost = AircraftOperationalCoasts[2];
		if (aircraft.size()>= maxAircraftCount)
			return false;
		if (!airport.isFull()) {
			aircraft.add(new RapidPassengerAircraft(airport,operationalCost));
			logger(String.format("0 %d %d",airport.getID(), 2));
			return true;
		}

		return false;
	}
	/**
	 * creates a JetPassengerAircraft
	 * @param airport airport to create the aircraft at
	 * @return true if aircraft is created
	 */
	private boolean addJetPassengerAircraft(Airport airport) {
		double operationalCost = AircraftOperationalCoasts[3];
		if (aircraft.size()>= maxAircraftCount)
			return false;
		if (!airport.isFull()) {
			aircraft.add(new JetPassengerAircraft(airport,operationalCost));
			logger(String.format("0 %d %d",airport.getID(), 3));
			return true;
		}

		return false;
	}

	/*
	 * Seat setting functions:
	 */
	private boolean setSeats(int aircraftIndex, int e, int b, int f) {
		PassengerAircraft aircraft = this.aircraft.get(aircraftIndex);
		boolean isSuccessful = aircraft.setSeats(e, b, f);
		if( isSuccessful)
			logger(String.format("2 %d %d %d %d",aircraftIndex,
					aircraft.getEconomySeats(),aircraft.getBusinessSeats(),aircraft.getFirstClassSeats() ));
		else {
			if(DEBUG) System.out.println("invalid seat setting "+ aircraft.getFloorArea()+ " "+ e +  " " + b + " "+ f);
				
				
		}
		return isSuccessful;


	}

	/**
	 * creates an Airport of given description
	 * @param type type of airport
	 * @param id id of airport
	 * @param x x coordinate of airport
	 * @param y y coordinate of airport
	 * @param fuelCost fuel cost of airport
	 * @param operationFee operation fee of airport
	 * @param aircraftCapacity capacity of airport
	 */
	public void createAirport(String type, int id, double x, double y, double fuelCost, double operationFee,int aircraftCapacity) {
		switch (type) {
			case "hub" -> airports.put(id, new HubAirport(id, x, y, fuelCost, operationFee, aircraftCapacity));
			case "major" -> airports.put(id, new MajorAirport(id, x, y, fuelCost, operationFee, aircraftCapacity));
			case "regional" -> airports.put(id, new RegionalAirport(id, x, y, fuelCost, operationFee, aircraftCapacity));
		}

	}

	/**
	 * creates a passenger
	 * @param passengerDestinations destinations of passenger
	 * @param type type of passenger
	 * @param id id of passenger
	 * @param weight weight of passenger
	 * @param baggageCount number of baggage of passenger
	 */
	public void createPassenger(String[] passengerDestinations, String type, long id, double weight, int baggageCount) {

		ArrayList<Airport> dests =new ArrayList<>();

		for (String passengerDestination : passengerDestinations) {
			dests.add(airports.get(Integer.parseInt(passengerDestination)));
		}
		switch (type) {
			case "first" -> dests.get(0).addPassenger(new FirstClassPassenger(id, weight, baggageCount, dests));
			case "business" -> dests.get(0).addPassenger(new BusinessPassenger(id, weight, baggageCount, dests));
			case "economy" -> dests.get(0).addPassenger(new EconomyPassenger(id, weight, baggageCount, dests));
			case "luxury" -> dests.get(0).addPassenger(new LuxuryPassenger(id, weight, baggageCount, dests));
		}
	}

	/**
	 * prints log string to log-file
	 * @param logString log string
	 */
	private double prevProfit = 0; // TODO: comment this out
	private void logger(String logString) {
		try {
//			if(DEBUG)System.out.println(":" + (getProfits()-prevProfit));// TODO: comment this out
			if(DEBUG)log.write(logString+" = "+ (getProfits()-prevProfit) + '\n');// TODO: comment this out
			else log.write(logString+'\n');
			if(DEBUG)prevProfit = getProfits();// TODO: comment this out
			log.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *  keeps necessary parameters for a possible flight
	 */
	static private class Flight implements Comparable<Flight>{
		int[] seatCounts = {0,0,0};
		double[] seatArea = {1,3,8,8};
		int range;
		List<Long> toFly;
		Airport fromAirport;
		Airport toAirport;
		double passangerWeight = 0;

		public Flight(int range,ArrayList<ArrayList<Long>> pl , double area,Airport fromAirport, Airport toAirport) {
			this.range = range;
			this.fromAirport = fromAirport;
			this.toAirport = toAirport;
			toFly = new ArrayList<>();
			ArrayList<Long> L = pl.get(3);
			L.addAll(pl.get(2));
			L.addAll(pl.get(1));
			L.addAll(pl.get(0));
			
			/*
			PriorityQueue<Passenger> pQueue = new PriorityQueue<>();
			for(long id : L) {
				pQueue.add(fromAirport.getPassengers().get(id));
			}
			 //TODO: this is surprisingly unprofitable, why?
			boolean addedPassanger = false;
			while (!pQueue.isEmpty()) {
				var p = pQueue.poll();
				int type = p.getPassengerType();
				if(seatArea[type]<=area) {
					if(type == 3 || type == 2){
						seatCounts[2]++;
						area -= seatArea[type];
						toFly.add(p.getID());
						addedPassanger =true;
						
					}else if (!addedPassanger ||( type == 1 && p.getBaggageCount()>5)){
						seatCounts[type]++;
						area -= seatArea[type];
						toFly.add(p.getID());
						addedPassanger =true;
					}

				}else {
					break;
				}
				if(type == 0) break;
				
			}
			*/
 
			 
			
			for (long p : L) {
				int type = fromAirport.getPassengers().get(p).getPassengerType();
				if(seatArea[type]<=area) {
					if(type == 3){
						seatCounts[2]++;
						area -= seatArea[type];
					}else {
						seatCounts[type]++;
						area -= seatArea[type];
					}
					
				}
			}
			if(seatCounts[2]+ seatCounts[1]>0) {//Heuristic
				toFly =  L.subList(0, seatCounts[2]+ seatCounts[1]);
			}else if (seatCounts[0]!= 0)  {
				toFly.add(L.get(0));
			}
			
			passangerWeight = 0;
			for(long pid: toFly)
				passangerWeight += fromAirport.getPassengers().get(pid).getWeight();

		}
		
		
		// Heuristic function:
		Double profitbality() {
//			return (int) ( (-seatCounts[0] + seatCounts[1]*2 + seatCounts[2]*10)/(Math.pow(Math.E, -toAirport.getDistance(fromAirport)/3169)));
//			return (int) ( (-seatCounts[0] + seatCounts[1]*2 + seatCounts[2]*10)/(Math.pow(31, (range-1)*(range-2)*(range) ) ));
			return  ((fromAirport.getDistance(toAirport)/(3169))* (-seatCounts[0] + seatCounts[1]*3 + seatCounts[2]*8)/(Math.pow(1000,(range-2)* (range-1)*(range))));
		}

		@Override
		public int compareTo(Flight o) {
			return -profitbality().compareTo(o.profitbality());
		}

		@Override
		public String toString() {
			return  Arrays.toString(seatCounts);
		}

	}

	/**
	 * execute flights generated by heuristics
	 * if no heuristic flight is found execute emergencyProtocol
	 */
	public void run() {
		boolean hasUnloaded = false;
		ArrayList<Airport> ap = new ArrayList<>(airports.values());
		PriorityQueue<Flight> flights = new PriorityQueue<>();
		Flight goldenFlight = generateFlights(ap, flights);
		double prevProfit =-1;
		int ai = -1;
		int sd = 2;

		while(getProfits()>prevProfit || sd>0) {
			if (ai == maxAircraftCount-1)
				break;
			if (getProfits()<prevProfit)sd--;
			Flight aFlight = flights.poll();
			if(aFlight == null) break;
			if (aFlight.fromAirport.isFull() || aFlight.toAirport.isFull())
				continue;
			boolean flag = false;
			

			switch (aFlight.range) {
				case 0:
					if(addPropPassengerAircraft(aFlight.fromAirport)) {
						ai++;
						flag= true;
					}
					break;
				case 1:
					if(addJetPassengerAircraft(aFlight.fromAirport)){
						ai++;
						flag= true;
					}break;
				case 2:
					if(addRapidPassengerAircraft(aFlight.fromAirport)){
						ai++;
						flag= true;
					}break;
				case 3:
					if(addWidebodyPassengerAircraft(aFlight.fromAirport)){
						ai++;
						flag= true;
					}break;
			}
			if(!flag) break;
			setSeats(ai, aFlight.seatCounts[0], aFlight.seatCounts[1], aFlight.seatCounts[2]);

					
			if (!fillOptimally(ai, aFlight.fromAirport.getDistance(aFlight.toAirport),aFlight.passangerWeight))continue;

			if(!canFly(aFlight.toAirport, ai)) {
				if(DEBUG)System.out.println("cant fly "+ ai);
				continue;
			}
			for(long p: aFlight.toFly) {
				if(!loadPassenger(aFlight.fromAirport.getPassengers().get(p), aFlight.fromAirport, ai)) {
					if(DEBUG)System.out.println("can't load "+ p);
				}
			}
			
			if(fly(aFlight.toAirport, ai)) {
				for(long p: aFlight.toFly) {
					if(unloadPassenger(aircraft.get(ai).getPassengers().get(p), ai)){
						hasUnloaded = true;
					}else{
						if(DEBUG)System.out.println("can't unload "+ p);
					}
				}
			}
			prevProfit = getProfits();
		}
		if (!hasUnloaded && goldenFlight != null ) {
			try {
				emergencyProtocol( goldenFlight );
			} catch (Exception e) {
				if(DEBUG)System.out.println("sad"); // unlucky, graph was not connected :(
			}
			
		}
		if(DEBUG)System.out.printf("%f\n",getProfits());
		logger(String.format("%f", getProfits()));
	}

	/**
	 * generates the flights according to heuristics:
	 * 	   - prioritize passengers according to their class and destinations
	 * 	   - execute the flights according to their priority
	 * 	   - if two successive flights incur loss stop operation (this guarantees one flight)
	 * 	   if the above algorithm fails to fly any passenger, this means path-finding is needed,
	 * 	   emergency flight goldenFlight is carried
	 * @param ap the airports
	 * @param flights the flights
	 * @return the golden flight
	 */
	private Flight generateFlights(ArrayList<Airport> ap, PriorityQueue<Flight> flights) {
		Flight goldenFlight = null;
		for (Airport fa : ap) {
			if (fa.isFull()) continue;

			HashMap<Integer, ArrayList<ArrayList<Long>>> pl = fa.prioritizePassengers();

			for (int aid : pl.keySet()) {
				Airport to = airports.get(aid);

				if (to.isFull()) continue;

				if (fa.getDistance(to) < 2000) { // prop

					flights.add(new Flight(0, pl.get(aid), 60, fa, to));

				} else if (fa.getDistance(to) < 5000) {// jet
					flights.add(new Flight(1, pl.get(aid), 30, fa, to));

				} else if (fa.getDistance(to) < 7000) {

					flights.add(new Flight(2, pl.get(aid), 120, fa, to));


				} else if (fa.getDistance(to) < 14000) {
					flights.add(new Flight(3, pl.get(aid), 450, fa, to));

				} else {
					Flight tmpFlight = new Flight(4, pl.get(aid), 450, fa, to);
					if(goldenFlight == null)
						goldenFlight = tmpFlight;
					else if (goldenFlight.toFly.size()==0&&pl.get(aid).size()!=0)
						goldenFlight = tmpFlight;
					else if (goldenFlight.fromAirport.getDistance(goldenFlight.toAirport) >fa.getDistance(to))
						goldenFlight = tmpFlight;
					
				}

			}


		}
		return goldenFlight;
	}

	/**
	 * emergency protocol: guarantees that at least one passenger is unloaded
	 * @param goldenFlight the golden flight
	 */
	private void emergencyProtocol(Flight goldenFlight) {
		Airport toA = goldenFlight.toAirport;
		Airport fromA = goldenFlight.fromAirport;
		Stack<Airport> pathList;
		try {
			pathList = disjktra(fromA,toA);
		}catch (Exception e) {
			pathList = pathFinding(fromA, toA);
			if(DEBUG)System.out.println("disjktra failed,sad");
		}
		if (addWidebodyPassengerAircraft(fromA)){
			int ai = aircraft.size()-1;
			
			setSeats(ai, goldenFlight.seatCounts[0], goldenFlight.seatCounts[1], goldenFlight.seatCounts[2]);
			boolean loadedOne = false;
			int i =0;
			for(long p: goldenFlight.toFly) {
				if(!loadPassenger(goldenFlight.fromAirport.getPassengers().get(p), goldenFlight.fromAirport, ai)) {
					if(DEBUG)System.out.println("can't load "+ p);
				}else {
					loadedOne = true;i++;
				}
				if(i== 13) 
					break;
			}
			
			if(loadedOne) {
				while (!pathList.isEmpty()) {
					Airport next = pathList.pop();
					fulle(ai);
					if(!fly(next, ai)){
						if(DEBUG)System.out.println("can't fly");
						if(DEBUG)System.out.println(next);
					}

				}
				int k = 0;
				for(long p:goldenFlight.toFly ) {
					if(unloadPassenger(aircraft.get(ai).getPassengers().get(p), ai)){
						k++;
					}else{
						if(DEBUG)System.out.println("can't unload "+ p);
					}
					if(k==13) 
						break;
				}
			}
		}

	}

	/**
	 * finds the path from the fromA to the toA using bfs or djikstra  TODO: implement A*?
	 * @param toA the airport to go to
	 * @param fromA the airport to go from
	 * @return the path
	 */
	private Stack<Airport> pathFinding( Airport fromA,Airport toA) {
		ArrayList<Airport> ap = new ArrayList<>(airports.values());
		HashMap<Airport, ArrayList<Airport>> adjl = new HashMap<>();
		for(int i = 0; i< ap.size(); i++) {
			Airport fa = ap.get(i);
			for (Airport to : ap) {
				if (!to.equals(fa) && fa.getDistance(to) <= 14000) {
					if (!adjl.containsKey(fa)) {
						adjl.put(fa, new ArrayList<>());
					}
					adjl.get(fa).add(to);
				}
			}
		}
		HashSet<Airport> visited = new HashSet<>();
		HashMap<Airport, Airport> path = new HashMap<>();
		Queue<Airport> q = new LinkedList<>();
		q.add(fromA);
		while(!q.isEmpty()) {
			Airport fa = q.poll();

			if(visited.contains(fa)) continue;
			visited.add(fa);
			if(fa.equals(toA)) {
				break;
			}
			if(adjl.containsKey(fa)) {
				for(Airport next: adjl.get(fa)) {
					if(!visited.contains(next)) {
						q.add(next);
						path.put(next, fa);
					}
				}
			}
		}

		Stack<Airport> pathList = new Stack<>();
		Airport cur = toA;
		while(!cur.equals(fromA)) {
			pathList.add(cur);
			cur = path.get(cur);
		}
		return pathList;
	}
	class Edge {
		Airport orig,dest;
		double cost;

	    public Edge(Airport orig, Airport dest, double cost) {
	        this.orig = orig;
	        this.dest = dest;
	        this.cost = cost;
	    }
	    @Override
	    public String toString() {
	    	
	    	return "("+orig+"->"+dest+")";
	    }

	}

	// class used to compare edges in the priority queue
	class EdgeCmp implements Comparator<Edge> {

		HashMap<Airport, Double> dist;

	    public EdgeCmp(HashMap<Airport, Double> dist) {
	        this.dist = dist;
	    }

	    public int compare(Edge e1, Edge e2) {
	        double v1 = dist.get(e1.orig) + e1.cost;
	        double v2 =dist.get(e2.orig)  + e2.cost;
	        return Double.compare(v1, v2);
	    }
	    

	}

	private Stack<Airport> disjktra( Airport s, Airport d) {
		HashMap<Airport, ArrayList<Edge>> g = new HashMap<>();
		ArrayList<Airport> ap = new ArrayList<>(airports.values());
		for(int i = 0; i< ap.size(); i++) {
			Airport fa = ap.get(i);
			for (Airport to : ap) {
				if (!to.equals(fa) && fa.getDistance(to) <= 14000) {
					if (!g.containsKey(fa)) {
						g.put(fa, new ArrayList<>());
					}
					g.get(fa).add(new Edge(fa, to, fa.getDistance(to)));
				}
			}
		}
	    // initialize distances
		HashMap<Airport, Double> dist = new HashMap<>();
		HashMap<Airport, Airport> prev = new HashMap<>();
		HashMap<Airport, Boolean> visited = new HashMap<>();
		for(Airport tmp : g.keySet()) {
			dist.put(tmp, Double.POSITIVE_INFINITY);
			visited.put(tmp, false);
		}
			
		dist.put(s, 0d);
		visited.put(s, true);

	    // initialize PQ
	    PriorityQueue<Edge> Q = new PriorityQueue<>(new EdgeCmp(dist));
	    
	    for(Edge e : g.get(s)) Q.add(e);
	    while(!Q.isEmpty()) {
	        Edge mine = Q.poll();
	        
	        if (dist.get(mine.orig) + mine.cost < dist.get(mine.dest))
	       {
	        	dist.put(mine.dest, dist.get(mine.orig) + mine.cost);
	        	prev.put(mine.dest, mine.orig);
	            for(Edge e : g.get(mine.dest)) {
	            	if(!visited.get(e.dest))
	            		Q.add(e);
	            }
	        }
	        
	    }
	    Stack<Airport> path = new Stack<>();
	    for(Airport t = d; prev.get(t)!= null; t = prev.get(t) )
	    	path.push(t);

	    return path;
	}


	/**
	 * WARNING: this is for debugging purposes, don't use externally!
	 * @param fileName path of log file to be reproduced
	 */
	public void reproduceLog(String fileName) {
		if(!DEBUG) return;

		File inputFile = new File(fileName);
		try (Scanner sc = new Scanner(inputFile)) {
			while(sc.hasNext()) {

			String operationString  = sc.nextLine();
			try (Scanner ls = new Scanner(operationString)) {
				if(!ls.hasNextInt()) {
					break;
				}
				int optype = ls.nextInt();

				switch (optype) {
					case 0 -> {//"0 %d %d",airport.getID(), 3)
						int airportId0 = ls.nextInt();
						int aircraftType = ls.nextInt();
						if(airports.get(airportId0).isFull()) System.out.println("Failed to create aircraft due to aircraft size limit");
						switch (aircraftType) {
							case 0 : if(!addPropPassengerAircraft(airports.get(airportId0)) ) System.out.println("couldn't create aircraft at "+airportId0);break;
							case 1 : if(! addWidebodyPassengerAircraft(airports.get(airportId0)))System.out.println("couldn't create aircraft at "+airportId0);break;
							case 2 : if(!addRapidPassengerAircraft(airports.get(airportId0)))System.out.println("couldn't create aircraft at "+airportId0);break;
							case 3 : if(!addJetPassengerAircraft(airports.get(airportId0)))System.out.println("couldn't create aircraft at "+airportId0);break;
						}
						
						
					}
					case 1 -> {//"1 %d %d", toAirport.getID(),aircraftIndex)
						int airportId1 = ls.nextInt();
						int aircraftIndex = ls.nextInt();
						fly(airports.get(airportId1), aircraftIndex);
					}
					case 2 -> {// "2 %d %d %d %d",aircraftIndex,
						//aircraft.getEconomySeats(),aircraft.getBusinessSeats(),aircraft.getFirstClassSeats() )
						int aircraftIndex2 = ls.nextInt();
						int e = ls.nextInt();
						int b = ls.nextInt();
						int f = ls.nextInt();
						setSeats(aircraftIndex2, e, b, f);
					}
					case 3 -> {//"3 %d %f", aircraftIndex, fuel_amount)
						int aircraftIndex3 = ls.nextInt();
						double fuel_amount = ls.nextDouble();
//						fulle(aircraftIndex3);
						loadFuel(aircraftIndex3, fuel_amount);
					}
					case 4 -> {//"4 %d %d %d %d",passenger.getID(), aircraftIndex,airport.getID(),passenger.getSeatType())
						long passId = ls.nextLong();
						int aircraftInd = ls.nextInt();
						int airpId = ls.nextInt();
//						ls.nextInt();//int passSeatType =
						loadPassenger(airports.get(airpId).getPassengers().get(passId), airports.get(airpId), aircraftInd);
					}
					case 5 -> {//"5 %d %d %d",passenger.getID(), aircraftIndex,curAirport.getID())
						long passId5 = ls.nextLong();
						int aircraftInd5 = ls.nextInt();
						unloadPassenger(aircraft.get(aircraftInd5).getPassengers().get(passId5), aircraftInd5);
					}
					default -> System.out.println("debugger can't handle this input");
				}
				}

			}
		}
		catch (Exception e) {
				e.printStackTrace();
			}
		logger(String.format("%f", getProfits()));
		if(DEBUG)System.out.printf("%f\n",getProfits());

	}

}



