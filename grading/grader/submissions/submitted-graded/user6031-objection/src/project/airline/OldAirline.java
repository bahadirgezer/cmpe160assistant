/**
 * 
 */
package project.airline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import project.airline.aircraft.PassengerAircraft;
import project.airline.concrete.PropPassengerAircraft;
import project.airport.Airport;
import project.airport.HubAirport;
import project.airport.MajorAirport;
import project.airport.RegionalAirport;
import project.passenger.BusinessPassenger;
import project.passenger.EconomyPassenger;
import project.passenger.FirstClassPassenger;
import project.passenger.LuxuryPassenger;
import project.passenger.Passenger;

/**
 * 
 * @author Hanaa Zaqout
 * the only class that should be imported to the main.
 * all aircraft objects should be held in this class. 
 * Also, Revenue and expenses will also be calculated inside this class.
 *
 */
public class OldAirline {
	/**
	 * fields from given input
	 */
	private Scanner in;
	/**
	 * Maximum number of aircrafts this airline can hold. Will be given as an input.
	 */
	public static int maxAircraftCount;
	/**
	 * Maximum number of airports this airline can hold. Will be given as an input.
	 */
	public static int nofAirports;
	/**
	 * Maximum number of passengers this airline can hold. Will be given as an input.
	 */
	public static int nofPassengers;
	/**
	 * Operational cost value for this airline. Will be given as an input.
	 */
	public static double airlineOperationalCost;
	/**
	 * Operational cost value for each type of aircraft. Will be given as an input.
	 */
	public static double propOperationFee, wideOperationFee, rapidOperationFee, jetOperationFee;
	
	private static HashMap<Long, Airport> airports = new HashMap<Long, Airport>();
	private static ArrayList<PassengerAircraft> aircrafts = new ArrayList<PassengerAircraft>();
	private static HashMap<Long, HashMap<Long, ArrayList<Passenger>>> flightsTable = new HashMap<Long, HashMap<Long, ArrayList<Passenger>>>();
	private static double expenses, revenue;

	// private static HashMap<Integer, Passenger> passengers = new HashMap<Integer,
	// Passenger>();

	/*
	 * constructor of Airline Class
	 */
	public OldAirline() {
	}

	public OldAirline(Scanner in) {
		this.in = in;
		expenses = 0;
		revenue = 0;
	}

	public Long largestAudience(HashMap<Long, ArrayList<Passenger>> stations) {
		int maxSize = 0;
		Long toAirportId = (long) 0;
		for (Long toAirportIds : stations.keySet()) {
			if (stations.get(toAirportIds).size() > maxSize) {
				maxSize = stations.get(toAirportIds).size();
				toAirportId = toAirportIds;
			}
		}
		if (maxSize > 0) {
			return toAirportId;
		}
		return 0L;

	}

	public boolean flightsAreOver() {
		for (var idair : flightsTable.entrySet()) {
			for (var data : idair.getValue().entrySet()) {
				ArrayList<Passenger> ps = data.getValue();
				if (ps.size() > 0) {
					return false;
				}
			}
		}
		return true;
	}
	/*
	 * put passengers who are in the same airport aiming to read the same
	 * destination in groups
	 */

	public void createFlightsTable() {

		for (Long a : airports.keySet()) {
			HashMap<Long, ArrayList<Passenger>> airportsTable = new HashMap<Long, ArrayList<Passenger>>();
			for (Long da : airports.keySet()) {
				if (a != da) {
					ArrayList<Passenger> passengersGroup = new ArrayList<Passenger>();
					for (Passenger p : airports.get(a).passengers) {
						if (p.getDestinations().get(1).getID() == da) {
							passengersGroup.add(p);
						}
					}
					airportsTable.put(da, passengersGroup);
				}
			}
			flightsTable.put(a, airportsTable);
		}

	}

	public void aircraftCreation(Airport airport, Airport toAirport) {
		if (maxAircraftCount > aircrafts.size()) {
			PassengerAircraft myAircraft = new PropPassengerAircraft(airport);
			aircrafts.add(myAircraft);
		}
	}

	private void createConstants() {

		if (in.hasNext()) {
			String[] limits = in.nextLine().split(" ");
			maxAircraftCount = Integer.valueOf(limits[0]);
			nofAirports = Integer.valueOf(limits[1]);
			nofPassengers = Integer.valueOf(limits[2]);
		}

		if (in.hasNext()) {
			String[] operations = in.nextLine().split(" ");
			propOperationFee = Double.valueOf(operations[0]);
			wideOperationFee = Double.valueOf(operations[1]);
			rapidOperationFee = Double.valueOf(operations[2]);
			jetOperationFee = Double.valueOf(operations[3]);
			airlineOperationalCost = Double.valueOf(operations[4]);
		}
	}

	/*
	 * Create Airport objects
	 */
	private void createAirportObjects() {
		for (int ai = 0; ai < nofAirports; ai++) {

			if (in.hasNextLine()) {
				String[] line = in.nextLine().split(", ");
				String[] beginLine = line[0].split(" : ");
				String airportType = beginLine[0];
				long ID = Long.valueOf(beginLine[1]);
				double x = Double.valueOf(line[1]);
				double y = Double.valueOf(line[2]);
				double fuelCost = Double.valueOf(line[3]);
				double airportOperationFee = Double.valueOf(line[4]);
				int aircraftCapacity = Integer.valueOf(line[5]);
				ArrayList<Passenger> passengers = new ArrayList<Passenger>();

				switch (airportType) {
				case "hub": {
					airports.put(ID,
							new HubAirport(ID, x, y, fuelCost, airportOperationFee, aircraftCapacity, passengers));
					break;
				}
				case "major": {
					airports.put(ID,
							new MajorAirport(ID, x, y, fuelCost, airportOperationFee, aircraftCapacity, passengers));
					break;
				}

				case "regional": {
					airports.put(ID,
							new RegionalAirport(ID, x, y, fuelCost, airportOperationFee, aircraftCapacity, passengers));
					break;
				}
				}
			}
		}
	}

	/*
	 * Create passenger objects
	 */
	private void createPassengerObjects() {
		for (int pi = 0; pi < nofPassengers; pi++) {

			if (in.hasNextLine()) {
				String[] line = in.nextLine().split(", ");
				String[] beginLine = line[0].split(" : ");
				String passengerType = beginLine[0];
				long ID = Long.valueOf(beginLine[1]);
				int weight = Integer.valueOf(line[1]);
				int baggageCount = Integer.valueOf(line[2]);
				ArrayList<Airport> destinations = new ArrayList<Airport>();

				for (int i = 3; i < line.length; i++) {
					String val = line[i].replaceAll("[^0-9]", ""); // get red of [ ]
					destinations.add(airports.get(Long.valueOf(val)));
				}

				/*
				 * {Airport ID: Airport object} airport object: id x y passengers arraylist
				 * passengers arraylist: passenger passenger passenger passenger: id weight
				 * baggage count distenations distenations: {airport object, airport object,
				 * airport object}
				 */
				switch (passengerType) {
				case "economy": {
					Airport s = airports.get(destinations.get(0).getID());
					s.addPassenger(new EconomyPassenger(ID, weight, baggageCount, destinations));
					airports.put(destinations.get(0).getID(), s);
					break;
				}

				case "business": {
					Airport s = airports.get(destinations.get(0).getID());
					s.addPassenger(new BusinessPassenger(ID, weight, baggageCount, destinations));
					airports.put(destinations.get(0).getID(), s);
					break;
				}
				case "first": {
					Airport s = airports.get(destinations.get(0).getID());
					s.addPassenger(new FirstClassPassenger(ID, weight, baggageCount, destinations));
					airports.put(destinations.get(0).getID(), s);
					break;
				}
				case "luxury": {
					Airport s = airports.get(destinations.get(0).getID());
					s.addPassenger(new LuxuryPassenger(ID, weight, baggageCount, destinations));
					airports.put(destinations.get(0).getID(), s);
					break;
				}
				}
			}
		}

	}

	/*
	 * read input and store given data in fields and data structure
	 */
	public void createObjects() {
		createConstants();
		createAirportObjects();
		createPassengerObjects();
	}

	/*
	 * load a passenger from a given airport to an aircraft of my choice.
	 */
	boolean loadPassenger(Passenger passenger, Airport currentAirport, int aircraftIndex) {

		// weight from passenger class
		// loading cost from aircraft class
		if (aircrafts.get(aircraftIndex).getCurrentAirport() != currentAirport) {
			return false;
		} else if (aircrafts.get(aircraftIndex).checkLoadPassenger(passenger)) {
			expenses += aircrafts.get(aircraftIndex).loadPassenger(passenger);

			return true;
		}
		return false;
	}

	/*
	 * called in the main
	 */
	public void loadPassenger(int aircraftIndex, Airport currentAirport, Airport toAirport) {
		for (Iterator<Passenger> iterator = flightsTable.get(currentAirport.getID()).get(toAirport.getID())
				.iterator(); iterator.hasNext();) {
			Passenger p = iterator.next();
			this.loadPassenger(p, currentAirport, aircraftIndex);
			/*
			 * add to aircraft object remove from airport object
			 */
			aircrafts.get(aircraftIndex).addPassenger(p);
			currentAirport.removePassenger(p);
			airports.put(currentAirport.getID(), currentAirport);
//			System.out.println(">>>>>>>>>>>size of current airport pass" + currentAirport.getPassengers().size());

			/*
			 * changes on FlightsTable
			 */
			iterator.remove();
//			ArrayList<Passenger> passengersf = flightsTable.get(currentAirport.getID()).get(toAirport.getID());
//			passengersf.remove(passenger);
//			flightsTable.get(currentAirport.getID()).put(toAirport.getID(), passengersf);
			int constant;
			if (p instanceof EconomyPassenger) {
				constant = 0;
			} else if (p instanceof BusinessPassenger) {
				constant = 1;
			} else {
				constant = 2;
			}
			System.out
					.println(4 + " " + p.getID() + " " + aircraftIndex + " " + currentAirport.getID() + " " + constant);
		}
	}

	/*
	 * unload a passenger from a given aircraft
	 */
	boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		if (passenger.isDestination(aircrafts.get(aircraftIndex).getCurrentAirport())) {
			revenue += aircrafts.get(aircraftIndex).unloadPassenger(passenger);

			/*
			 * passenger's current airport
			 */
			Airport nowAirport = passenger.getDestinations().get(0);
			/*
			 * add passenger to airport
			 */
			airports.get(nowAirport.getID()).addPassenger(passenger);
			/*
			 * update the location of the aircraft
			 */
			aircrafts.get(aircraftIndex).setCurrentAirport(nowAirport);
			/*
			 * if needed make update on the flightTable
			 */
			if (passenger.getDestinations().size() > 1) {
				flightsTable.get(nowAirport.getID()).get(passenger.getDestinations().get(1).getID()).add(passenger);
			}
			return true;
		}
		return false;
	}

	/*
	 * called in the main
	 */
	public void unloadPassenger(int aircraftIndex, Airport currentAirport, Airport toAirport) {
		aircrafts.get(aircraftIndex).setCurrentAirport(toAirport);
//		System.out.println(aircrafts.get(aircraftIndex).getPassengers().toString());
		for (Iterator<Passenger> iterator = aircrafts.get(aircraftIndex).getPassengers().iterator(); iterator
				.hasNext();) {
			Passenger p = iterator.next();
			this.unloadPassenger(p, aircraftIndex);
			/*
			 * remove passenger from aircraft
			 */
			ArrayList<Passenger> passengers = aircrafts.get(aircraftIndex).getPassengers();
			iterator.remove();
			aircrafts.get(aircraftIndex).setPassengers(passengers);
			/*
			 * print to screen
			 */
			System.out.println(5 + " " + p.getID() + " " + aircraftIndex + " " + currentAirport.getID());
		}
//		for (Passenger p : aircrafts.get(aircraftIndex).getPassengers()) {
//			this.unloadPassenger(p, aircraftIndex);
//			System.out.println(5 + " " + p.getID() + " " + aircraftIndex + " "
//					+ aircrafts.get(aircraftIndex).getCurrentAirport().getID());
//		}

	}

	/*
	 * refuel aircraft in the current airport
	 */

	public void fueling(int aircraftIndex, Airport currentAirport, Airport toAirport) {
		double prefuel = aircrafts.get(aircraftIndex).getFuel();

		expenses += aircrafts.get(aircraftIndex).refueling(toAirport);

		double afterfuel = aircrafts.get(aircraftIndex).getFuel();
		System.out.println(3 + " " + aircraftIndex + " " + (afterfuel - prefuel));
	}

	
	
	/*
	 * fly the aircraft from its current airport to toAirport
	 */

	public void fly(int aircraftIndex) {
		Airport toAirport = aircrafts.get(aircraftIndex).getPassengers().get(0).getDestinations().get(1);
		if (aircrafts.get(aircraftIndex).checkFly(toAirport)) {
			double runningCost = airlineOperationalCost * aircrafts.size();
			expenses += runningCost;
			expenses += aircrafts.get(aircraftIndex).fly(toAirport);

			System.out.println(1 + " " + toAirport.getID() + " " + aircraftIndex);
		}
	}

	/*
	 * specify the seat configuration you want on that aircraft
	 */
	public void seatSetting(int aircraftIndex, Airport currentAirport, Airport toAirport) {
		int economy = 0, business = 0, firstClass = 0;

		for (Passenger p : flightsTable.get(currentAirport.getID()).get(toAirport.getID())) {
			if (p instanceof EconomyPassenger) {
				economy++;
			} else if (p instanceof BusinessPassenger) {
				business++;
			} else if (p instanceof FirstClassPassenger || p instanceof LuxuryPassenger) {
				firstClass++;
			}
		}

		// details will be given in aircraft classes
		aircrafts.get(aircraftIndex).setSeats(economy, business, firstClass);
		System.out.println(2 + " " + aircraftIndex + " " + economy + " " + business + " " + firstClass);
	}

	/*
	 * getters and setters
	 */
	public static HashMap<Long, Airport> getAirports() {
		return airports;
	}

	public static ArrayList<PassengerAircraft> getAircrafts() {
		return aircrafts;
	}

	public double getExpenses() {
		return expenses;
	}

	public void setExpenses(double expenses) {
		OldAirline.expenses = expenses;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		OldAirline.revenue = revenue;
	}

	public double getProfit() {
		return revenue - expenses;
	}

	public static void setAirports(HashMap<Long, Airport> airports) {
		OldAirline.airports = airports;
	}

	public HashMap<Long, HashMap<Long, ArrayList<Passenger>>> getFlightsTable() {
		return flightsTable;
	}

	public void setFlightsTable(HashMap<Long, HashMap<Long, ArrayList<Passenger>>> flightsTable) {
		OldAirline.flightsTable = flightsTable;
	}

}
