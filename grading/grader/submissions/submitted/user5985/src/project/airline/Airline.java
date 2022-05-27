package project.airline;

import java.util.ArrayList;

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


/**
 * Airline class that arranges all airline operations
 * @author Halil
 */

public class Airline {
	int maxAircarftCount;
	private double operationalCost;
	private double allCosts;
	private double allRevenues;
	
	private ArrayList<PassengerAircraft> aircrafts = new ArrayList<PassengerAircraft>();
	private ArrayList<Airport> airports = new ArrayList<Airport>();
	
	/**
	 * Constructor of the class
	 * @param maxAircraftCount -> holds maximum number of aircrafts
	 * @param operationalCost -> operational cost of airline company
	 */
	
	
	public Airline(int maxAircraftCount, double operationalCost) {
		this.operationalCost = operationalCost;
		this.maxAircarftCount = maxAircraftCount;
	}
	
	/**
	 * all expenses of airline
	 * @return expenses
	 */
	
	public double getExpenses() {
		return this.allCosts;
	}
	
	/**
	 * all revenues of airline
	 * @return revenue
	 */
	
	public double getRevenues() {
		return this.allRevenues;
	}
	
	/**
	 * calculates profit when requested
	 * @returns profit
	 */
	
	public double returnProfit() {
		return this.allRevenues - this.allCosts;
	}
	
	/**
	 * how many aircraft the airline company currently has
	 * @return aircraft numbers
	 */
	
	public int numberOFAircrafts() {
		return this.aircrafts.size();
	}
	
	/**
	 * All airports that are registered to the airline
	 * @return the airport list requested
	 */
	
	public ArrayList<Airport> getAirports(){
		return this.airports;
	}
	
	/**
	 * All aircrafts that were created for airline company so far
	 * @return all 
	 */
	
	public ArrayList<PassengerAircraft> getAircrafts(){
		return this.aircrafts;
	}
	
	/**
	 * 
	 * @param toAirport -> ending airport of the fly
	 * @param aircraftIndex -> which aircraft to fly to toAirport
	 * @return If there is no error flying occurs
	 */
	
	public boolean fly(Airport toAirport, int aircraftIndex) {
		double runningCost = operationalCost * aircrafts.size();
		PassengerAircraft plane = aircrafts.get(aircraftIndex);
		
		double fuelConsumption = plane.getFuelConsumption(plane.getCurrentAirport().getDistance(toAirport));
		
		// check range and fullness of toAirport
		if (fuelConsumption > plane.getFuelCapacity() || toAirport.isFull() == true) { 
			return false;
		}
		
		// check fuel of aircraft
		else if (fuelConsumption < plane.getFuelCapacity() && plane.getFuel() < fuelConsumption) {
			return false;
		}
		
		// If all checks are passed, fly operation starts
		allCosts += runningCost;
		allCosts += plane.getFlightCost(toAirport);
	
		// airport update of plane
		plane.setCurrentAirport(toAirport);
		System.out.println("1 " + toAirport.getID() + " " + aircraftIndex);
		return true;
	}
	
	/**
	 * Does all loading operations
	 * @param passenger -> which passenger to load
	 * @param airport -> where the passenger currently is
	 * @param aircraftIndex -> to which aircraft that the passenger will be load
	 * @return true if operation is successful
	 */
	
	public boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		PassengerAircraft plane = aircrafts.get(aircraftIndex);
		
		// Last checks before loading
		if (plane.getCurrentAirport() != airport || plane.getAvailableWeight() < passenger.getWeight())
			return false;
		
		allCosts += plane.loadPassenger(passenger);
		System.out.println("4 " + passenger.getPassengerID() + " " + (this.aircrafts.size() -1)  + " " + airport.getID());
		return true;
	}
	
	/**
	 * Does all unloading operations
	 * @param passenger to unload
	 * @param aircraftIndex index of the aircraft that passenger will be removed from
	 * @return true if unloading is successful
	 */
	
	boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		PassengerAircraft aircraft = this.aircrafts.get(aircraftIndex);
		Airport currentAirport = aircraft.getCurrentAirport();
		allRevenues += aircraft.unloadPassenger(passenger);	
		passenger.setFlightIndex(passenger.getFlightIndex() + 1); // this indicates that passenger moved to another airport
		System.out.println("5 " + passenger.getPassengerID() + " " + (this.aircrafts.size() - 1) + " " + currentAirport.getID());
		return true;
	}
	
	/**
	 * getting airport from Long ID
	 * @param airportID -> ID of a given airport
	 * @return the airport whose id is airportID
	 */
	
	public Airport getAirportFromID(int airportID) {
		for (int b = 0; b < airports.size(); b++) {
			if (airports.get(b).getID() == airportID)
				return airports.get(b);
		}
		return airports.get(0);
	}
	
	/**
	 * Adds hub airport to airline
	 * @param ID -> ID of airport
	 * @param x -> x coordinate of airport
	 * @param y -> y coordinate of airport
	 * @param fuelCost -> fuelCost at that airport 
	 * @param operationFee -> operationFee of airport
	 * @param aircraftCapacity -> aircraft capacity of airport
	 */
	
	public void addHubAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		Airport newHub = new HubAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
		airports.add(newHub);
	}
	
	
	/**
	 * Adds major airport to airline
	 * @param ID -> ID of airport
	 * @param x -> x coordinate of airport
	 * @param y -> y coordinate of airport
	 * @param fuelCost -> fuelCost at that airport 
	 * @param operationFee -> operationFee of airport
	 * @param aircraftCapacity -> aircraft capacity of airport
	 */
	
	public void addMajorAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		Airport newMajor = new MajorAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
		airports.add(newMajor);
	}
	
	
	/**
	 * Adds regional airport to airline
	 * @param ID -> ID of airport
	 * @param x -> x coordinate of airport
	 * @param y -> y coordinate of airport
	 * @param fuelCost -> fuelCost at that airport 
	 * @param operationFee -> operationFee of airport
	 * @param aircraftCapacity -> aircraft capacity of airport
	 */
	
	public void addRegionalAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		Airport newRegional = new RegionalAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
		airports.add(newRegional);
	}
	
	/**
	 * checks whether all qutoa for aircraft is filled
	 * @return returns true if new can be added to the airline
	 */
	
	public boolean canNewAircarftBeAdded() {
		if (this.aircrafts.size() < this.maxAircarftCount) {
			return true;
		}
		return false;
	}
	
	/**
	 * add jet passenger to aircraft group
	 * @param initalAirport -> Initial airport of the aircraft
	 * @param operationalCost -> operational cost of the aircraft
	 * @param economy -> number of economy seats
	 * @param business -> number of business seats
	 * @param firstClass -> number of first class seats
	 */

	public void addJetPassengerAircraft(Airport initalAirport, double operationalCost, int economy, int business, int firstClass) {
		int economySeats = economy, businessSeats = business, firstClassSeats = firstClass;
		PassengerAircraft jetAircraft = new JetPassengerAircraft(initalAirport, operationalCost);
		jetAircraft.setID(this.aircrafts.size());
		jetAircraft.setSeats(economySeats, businessSeats, firstClassSeats);
		aircrafts.add(jetAircraft);
		initalAirport.addAircraft(jetAircraft);
		System.out.println("0 " + initalAirport.getID() + " 3");
		System.out.println("2 " + (aircrafts.size() - 1) + " " + economySeats + " " + businessSeats + " " + firstClassSeats);
	}
	
	/**
	 * add prop passenger to aircraft group
	 * @param initalAirport -> Initial airport of the aircraft
	 * @param operationalCost -> operational cost of the aircraft
	 * @param economy -> number of economy seats
	 * @param business -> number of business seats
	 * @param firstClass -> number of first class seats
	 */
	
	public void addPropPassengerAircraft(Airport initalAirport, double operationalCost, int economy, int business, int firstClass) {
		int economySeats = economy, businessSeats = business, firstClassSeats = firstClass;
		PassengerAircraft propAircraft = new PropPassengerAircraft(initalAirport, operationalCost);
		propAircraft.setID(this.aircrafts.size());
		propAircraft.setSeats(economySeats, businessSeats, firstClassSeats);
		aircrafts.add(propAircraft);
		initalAirport.addAircraft(propAircraft);
		System.out.println("0 " + initalAirport.getID() + " 0");
		System.out.println("2 " + (aircrafts.size() - 1) + " " + economySeats + " " + businessSeats + " " + firstClassSeats);	
		}
	
	/**
	 * add rapid passenger to aircraft group
	 * @param initalAirport -> Initial airport of the aircraft
	 * @param operationalCost -> operational cost of the aircraft
	 * @param economy -> number of economy seats
	 * @param business -> number of business seats
	 * @param firstClass -> number of first class seats
	 */
	
	public void addRapidPassengerAircraft(Airport initalAirport, double operationalCost, int economy, int business, int firstClass) {
		int economySeats = economy, businessSeats = business, firstClassSeats = firstClass;
		PassengerAircraft rapidAircraft = new RapidPassengerAircraft(initalAirport, operationalCost);
		rapidAircraft.setSeats(economySeats, businessSeats, firstClassSeats);
		rapidAircraft.setID(this.aircrafts.size());
		aircrafts.add(rapidAircraft);
		initalAirport.addAircraft(rapidAircraft);
		System.out.println("0 " + initalAirport.getID() + " 2");
		System.out.println("2 " + (aircrafts.size() - 1) + " " + economySeats + " " + businessSeats + " " + firstClassSeats);	
		}
	
	/**
	 * add wide passenger to aircraft group
	 * @param initalAirport -> Initial airport of the aircraft
	 * @param operationalCost -> operational cost of the aircraft
	 * @param economy -> number of economy seats
	 * @param business -> number of business seats
	 * @param firstClass -> number of first class seats
	 */
	
	public void addWideBodyPassengerAircraft(Airport initalAirport, double operationalCost, int economy, int business, int firstClass) {
		int economySeats = economy, businessSeats = business, firstClassSeats = firstClass;
		PassengerAircraft wideBodyAircraft = new WidebodyPassengerAircraft(initalAirport, operationalCost);
		if(wideBodyAircraft.setSeats(economySeats, businessSeats, firstClassSeats) == false) {
			System.out.println("ERROR IN SEAT CONFIGURATION");
			return;
		}
		wideBodyAircraft.setID(this.aircrafts.size());
		aircrafts.add(wideBodyAircraft);
		initalAirport.addAircraft(wideBodyAircraft);
		System.out.println("0 " + initalAirport.getID() + " 1");
		System.out.println("2 " + (aircrafts.size() - 1) + " " + economySeats + " " + businessSeats + " " + firstClassSeats);
	}	
		
	/**
	 * adds business passenger to airline
	 * @param ID -> ID of the passenger
	 * @param weight -> Weight of the passenger
	 * @param baggageCount -> number of baggage passenger got
	 * @param destinationIds -> destination airport ID but in Integer type which is converted to <Airport> using getAirportFromID() method
	 */
	
	public void  addBusinessPassenger(long ID, double weight, int baggageCount, ArrayList<Integer> destinationIDs) {
		ArrayList<Airport> destinations = new ArrayList<>();
		for (int v = 0; v < destinationIDs.size(); v++) {
			destinations.add(getAirportFromID(destinationIDs.get(v)));
		}
		Passenger businessPassenger = new BusinessPassenger(ID, weight, baggageCount, destinations);
		businessPassenger.getCurrentAirport().addPassenger(businessPassenger);
	}
	
	/**
	 * adds economy passenger to airline
	 * @param ID -> ID of the passenger
	 * @param weight -> Weight of the passenger
	 * @param baggageCount -> number of baggage passenger got
	 * @param destinationIds -> destination airport ID but in Integer type which is converted to <Airport> using getAirportFromID() method
	 */
	
	public void  addEconomyPassenger(long ID, double weight, int baggageCount, ArrayList<Integer> destinationsIDs) {
		ArrayList<Airport> destinations = new ArrayList<>();
		for (int v = 0; v < destinationsIDs.size(); v++) {
			destinations.add(getAirportFromID(destinationsIDs.get(v)));
		}
		Passenger economyPassenger = new EconomyPassenger(ID, weight, baggageCount, destinations);
		economyPassenger.getCurrentAirport().addPassenger(economyPassenger);
	}
	
	/**
	 * adds luxury passenger to airline
	 * @param ID -> ID of the passenger
	 * @param weight -> Weight of the passenger
	 * @param baggageCount -> number of baggage passenger got
	 * @param destinationIds -> destination airport ID but in Integer type which is converted to <Airport> using getAirportFromID() method
	 */
	
	public void addLuxuryPassenger(long ID, double weight, int baggageCount, ArrayList<Integer> destinationIDs) {
		ArrayList<Airport> destinations = new ArrayList<>();
		for (int v = 0; v < destinationIDs.size(); v++) {
			destinations.add(getAirportFromID(destinationIDs.get(v)));
		}
		Passenger luxuryPassenger = new LuxuryPassenger(ID, weight, baggageCount, destinations);
		luxuryPassenger.getCurrentAirport().addPassenger(luxuryPassenger);
	}
	
	/**
	 * adds first passenger to airline
	 * @param ID -> ID of the passenger
	 * @param weight -> Weight of the passenger
	 * @param baggageCount -> number of baggage passenger got
	 * @param destinationIds -> destination airport ID but in Integer type which is converted to <Airport> using getAirportFromID() method
	 */
	
	public void addFirstPassenger(long ID, double weight, int baggageCount, ArrayList<Integer> destinationIDs){
		ArrayList<Airport> destinations = new ArrayList<>();
		for (int v = 0; v < destinationIDs.size(); v++) {
			destinations.add(getAirportFromID(destinationIDs.get(v)));
		}
		Passenger firstPassenger = new FirstClassPassenger(ID, weight, baggageCount, destinations);
		firstPassenger.getCurrentAirport().addPassenger(firstPassenger);
	}
	
	// REFUELING
	
	/**
	 * adds fuel to given aircraft
	 * @param aircraftIndex -> which aircraft to refuel
	 * @param fuel -> the amount of fuel we add
	 * @return cost of refueling
	 */
	
	public double refuel(int aircraftIndex, double fuel) {
		PassengerAircraft plane = this.aircrafts.get(aircraftIndex);
		System.out.println("3 " +  aircraftIndex + " " + fuel);
		return plane.addFuel(fuel);
	}
	
	/**
	 * fills up to given aircraft
	 * @param aircraftIndex -> which aircraft to fill up
	 * @return
	 */
	
	public boolean fillUp(int aircraftIndex) {
		PassengerAircraft plane = this.aircrafts.get(aircraftIndex);
		if (plane.hasFuel(plane.getFuelCapacity())) { // already full
			allCosts += operationalCost;
			return false;
		}
		double amount = this.aircrafts.get(aircraftIndex).getFuelCapacity() - this.aircrafts.get(aircraftIndex).getFuel();
		allCosts += this.aircrafts.get(aircraftIndex).addFuel(amount);		
		System.out.println("3 " +  aircraftIndex + " " + amount);
		return true;
	}
	
	
	/**
	 * 
	 * FLIGHT DECISION FUNCTION
	 * It determines 2 closest airports which has some passengers that go the other, then sets the paremeter.
	 * 
	 * 	0 -> from which airport
	 *	1 -> to which airport
	 *	2 -> with what kind of aircraft
	 *	3 -> # of economy seats :: seat type 0
	 *	4 -> # of business seats business :: seat type 1
	 *	5 -> # of first + luxury seats :: seat type 2
	 * 
	 */
	
	
	
	public int[] flightDecision() {
		int[] output = new int[6];

		int airportToIndexResult = 0;
		int airportFromIndexResult = 0;
		
		double minDistance = 100000000;
		
		for (int initialIndex = 0; initialIndex < this.airports.size(); initialIndex++) {
			for (int lastIndex = 0; lastIndex < this.airports.size(); lastIndex++) {
				if (lastIndex != initialIndex) {
					if (minDistance > this.airports.get(lastIndex).getDistance(this.getAirports().get(initialIndex))) {
						for (int b = 0; b < this.airports.get(initialIndex).getPassengers().size(); b++) {
							Passenger pas = this.airports.get(initialIndex).getPassengers().get(b);
							
							if (pas.getDestinations().contains(this.airports.get(lastIndex)) == true) {
								minDistance = this.airports.get(initialIndex).getDistance(this.airports.get(lastIndex));
								airportFromIndexResult = initialIndex;
								airportToIndexResult = lastIndex;
							}				
						}
					}
				}
			}
		}
	
		output[0] = airportFromIndexResult;
		output[1] = airportToIndexResult;
		output[2] = -1; // wide body but all seats are economy
		output[3] = 450;
		output[4] = 0;
		output[5] = 0;
		return output;
	}
	
	/**
	 * For testing I only board one passenger which can be changed by removing return
	 * boarding a passenger in <fromWhichAirport> who goes to <toAirport>
	 * @param fromWhichAirport 
	 * @param toAirport
	 * @param aircraftIndex which aircraft to use
	 */
	
	// LOADING ALL PASENGERS TO AIRCRAFT
	
	public void boardAllPassangers(Airport fromWhichAirport, Airport toAirport, int aircraftIndex) {
		ArrayList<Passenger> passangers = fromWhichAirport.getPassengers();
		
		for (int b = 0; b < passangers.size(); b++) {
			Passenger passanger = passangers.get(b);
			if (passanger.getDestinations().contains(toAirport)) {
				this.loadPassenger(passanger, fromWhichAirport, aircraftIndex);
				return;
			}
		}
	}

	/**
	 * Unloads all passenger
	 * @param toAirport unloads the passengers to <toAirport>
	 * @param airccraftIndex
	 */
	
	public void unloadAllPassengers(Airport toAirport, int airccraftIndex) {
		ArrayList<Passenger> passangers = this.aircrafts.get(airccraftIndex).getPassengers();

		for (int b = 0; b < (int)passangers.size(); b++) {
			this.unloadPassenger(passangers.get(b), airccraftIndex);
		}
		
		// Removing passengers from passenger ArrayList must be done at the end. Otherwise,
		// the code may output some unexpected results
		while (passangers.size() > 0)
			this.aircrafts.get(airccraftIndex).getPassengers().remove(0);
		
	}	
}
