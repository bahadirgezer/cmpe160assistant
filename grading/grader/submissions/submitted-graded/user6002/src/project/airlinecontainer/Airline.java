package project.airlinecontainer;

import java.util.ArrayList;

import project.airlinecontainer.aircraftcontainer.*;
import project.airlinecontainer.aircraftcontainer.concretecontainer.*;
import project.airportcontainer.*;
import project.passengercontainer.*;

/**
 * @author Yunus Emre Özdemir
 */

public class Airline {
	//seatType = 0 -> Economy Seat
	//seatType = 1 -> Business Seat
	//seatType = 2 -> First Class Seat
	//airportType = 0 -> Hub Airport
	//airportType = 1 -> Major Airport
	//airportType = 2 -> Regional Airport
	
	/**
	 * revenue and expenses variables are created to keep track
	 */
	private double revenue;
	private double expenses;
	/**
	 * maxAircraftCount is the maximum number of aircrafts this airline can have
	 * operationalCost is the operation cost for this airline
	 * both are given in the input
	 */
	int maxAircraftCount;
	double operationalCost;
	/**
	 * A StringBuffer is created to keep track of the logs which will be appended here,
	 * then StringBuffer will be converted to string and written in the file at the main class
	 */
	public StringBuffer sb = new StringBuffer();
	/**
	 * ArrayLists are created to keep track of the aircrafts of this airline and airports they can travel
	 */
	ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
	ArrayList<Airport> airports = new ArrayList<Airport>();
	
	/**
	 * Operation Fees of different kinds of airplanes
	 */
	double propOperationFee;
	double widebodyOperationFee;
	double rapidOperationFee;
	double jetOperationFee;
	/**
	 * Number of aircrafts is calculated from the length of aircrafts ArrayList
	 */
	double numberOfAircrafts = aircrafts.size();
	/**
	 * Running Cost is calculated from the operational cost of this airline and the number of aircrafts it has
	 */
	double runningCost = operationalCost * numberOfAircrafts;
	
	/**
	 * Construct0r for the Airline class
	 * 
	 * @param maxAircraftCount Number of aircrafts an airline can have
	 * @param operationalCost Operation cost of the airline
	 * @param propOperationFee Operation fee of Prop Passenger Aircraft
	 * @param widebodyOperationFee Operation fee of Widebody Passenger Aircraft
	 * @param rapidOperationFee Operation fee of Rapid Passenger Aircraft
	 * @param jetOperationFee Operation fee of Jet Passenger Aircraft
	 */
	public Airline(int maxAircraftCount, double operationalCost, double propOperationFee, double widebodyOperationFee, double rapidOperationFee, double jetOperationFee) {
		this.maxAircraftCount = maxAircraftCount;
		this.operationalCost = operationalCost;
		this.propOperationFee = propOperationFee;
		this.widebodyOperationFee = widebodyOperationFee;
		this.rapidOperationFee = rapidOperationFee;
		this.jetOperationFee = jetOperationFee;
	}
		
	/**
	 * A getter method
	 * @return ArrayList of Aircrafts an airline has
	 */
	public ArrayList<Aircraft> getAircrafts() {
		return aircrafts;
	}
	
	/**
	 * A getter method
	 * @return ArrayList of Airports an airline's planes can travel
	 */
	public ArrayList<Airport> getAirports() {
		return airports;
	}
	
	/**
	 * A method to create Airports from main, it takes airportType as parameter,
	 * then calls the constructor of the appropriate airport type, then adds the new airport to airports ArrayList
	 * 
	 * @param airportType An integer that represents the type of the airport
	 * @param ID Airport's ID number, every airport has a unique ID
	 * @param x Airport's x coordinates
	 * @param y Airport's y coordinates
	 * @param fuelCost Cost of fuel in that airport
	 * @param operationFee Operation fee in that airport
	 * @param aircraftCapacity Maximum number of aircrafts that airport can hold
	 */
	public void createAirport(int airportType, int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		if (airportType == 0) {
			Airport airport = new HubAirport(airportType, ID, x, y, fuelCost, operationFee, aircraftCapacity);
			airports.add(airport);
		} else if (airportType == 1) {
			Airport airport = new MajorAirport(airportType, ID, x, y, fuelCost, operationFee, aircraftCapacity);
			airports.add(airport);
		} else {
			Airport airport = new RegionalAirport(airportType, ID, x, y, fuelCost, operationFee, aircraftCapacity);
			airports.add(airport);
		}
	}
	/**
	 * A method to create Passengers from main, it takes passengerType as a parameter,
	 * then calls the constructor of the appropriate passenger type, 
	 * then adds the passenger to the passengers ArrayList of the airport which they are in
	 * 
	 * @param passengerType An integer that represents the type of the passenger
	 * @param ID Passenger's ID number, every passenger has a unique ID
	 * @param weight Passenger's weight, will be used in the weight calculations for the airplane
	 * @param baggageCount Number of baggage passenger has, will be used in the calculation of the ticket price
	 * @param destinationsString A String that contains the airport ID's of the destinations of the passenger, it will be converted to an ArrayList of Airports
	 */
	public void createPassenger(int passengerType, long ID, double weight, int baggageCount, String destinationsString) {
		ArrayList<Airport> destinations = new ArrayList<Airport>();
		ArrayList<Integer> destinationsIDList = new ArrayList<Integer>();
		for (String s : destinationsString.split(", ")) {
			destinationsIDList.add(Integer.parseInt(s));
		}
		for (int findID : destinationsIDList) {
			for (Airport airport : airports) {
				if (airport.getID() == findID) {
					destinations.add(airport);
				}
			}
		}
		if (passengerType == 0) {
			Passenger passenger = new EconomyPassenger(passengerType, ID, weight, baggageCount, destinations);
			passenger.getDestinations().get(0).addPassenger(passenger);
		} else if (passengerType == 1) {
			Passenger passenger = new BusinessPassenger(passengerType, ID, weight, baggageCount, destinations);
			passenger.getDestinations().get(0).addPassenger(passenger);
		} else if (passengerType == 2) {
			Passenger passenger = new FirstClassPassenger(passengerType, ID, weight, baggageCount, destinations);
			passenger.getDestinations().get(0).addPassenger(passenger);
		} else {
			Passenger passenger = new LuxuryPassenger(passengerType, ID, weight, baggageCount, destinations);
			passenger.getDestinations().get(0).addPassenger(passenger);
		}
	}
	
	/**
	 * A method to create Aircrafts from main, it takes aircraftType as a parameter,
	 * then calls the constructor of the appropriate aircraft type, 
	 * then adds the aircraft to the aircrafts ArrayList of the airline
	 * logs the operation
	 * 
	 * @param currentAirport The airport at which the aircraft will be at first
	 * @param aircraftType An integer that represents the type of the aircraft
	 */
	public void createAircraft(Airport currentAirport, int aircraftType) {
		if (aircraftType == 0) {
			Aircraft aircraft = new PropPassengerAircraft(currentAirport, propOperationFee);
			aircrafts.add(aircraft);
		} else if (aircraftType == 1) {
			Aircraft aircraft = new WidebodyPassengerAircraft(currentAirport, widebodyOperationFee);
			aircrafts.add(aircraft);
		} else if (aircraftType == 2) {
			Aircraft aircraft = new RapidPassengerAircraft(currentAirport, rapidOperationFee);
			aircrafts.add(aircraft);
		} else {
			Aircraft aircraft = new JetPassengerAircraft(currentAirport, jetOperationFee);
			aircrafts.add(aircraft);
		}
		sb.append("0 " + Integer.toString(currentAirport.getID()) + " " + Integer.toString(aircraftType) + "\n");
	}
	
	/**
	 * A function to place new seats in an aircraft, it takes the number of seats wanted at each type as parameter,
	 * then checks if the new seats don't exceed the floorArea of the plane using a method from PassengerAircraft class,
	 * returns if the seating operation is complete or not
	 * logs the operation
	 * 
	 * @param aircraft The aircraft at which the seating operation will take place
	 * @param economy Number of economy seats wanted to be placed
	 * @param business Number of business seats wanted to be placed
	 * @param firstClass Number of first class seats wanted to be placed
	 * @return returns if the seating operation is complete or not
	 */
	boolean setSeats(PassengerAircraft aircraft, int economy, int business, int firstClass) {
		if (aircraft.canSetSeats(economy, business, firstClass)) {
			aircraft.setSeats(economy, business, firstClass);
			sb.append("2 " + Integer.toString(aircrafts.indexOf(aircraft)) + " " + Integer.toString(economy) + " " + Integer.toString(business) + " " + Integer.toString(firstClass) + "\n");
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * Checks if there is anybody on the plane, if there is, returns false,
	 * If there is nobody on the plane, it resets all the seats and adds as much as
	 * economy seats that can be added without exceeding the floorArea
	 * logs the operation
	 * 
	 * @param aircraft The aircraft at which the seating operation will take place
	 * @return returns if the operation took place
	 */
	public boolean setAllEconomy(Aircraft aircraft) {
		if (((PassengerAircraft)aircraft).isEmpty()) {
			((PassengerAircraft)aircraft).setAllEconomy();
			sb.append("2 " + Integer.toString(aircrafts.indexOf(aircraft)) + " " + Integer.toString(aircraft.getEconomySeats()) + " " + Integer.toString(0) + " " + Integer.toString(0) + "\n");
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks if there is anybody on the plane, if there is, returns false,
	 * If there is nobody on the plane, it resets all the seats and adds as much as
	 * business seats that can be added without exceeding the floorArea
	 * logs the operation
	 * 
	 * @param aircraft The aircraft at which the seating operation will take place
	 * @return returns if the operation took place
	 */
	public boolean setAllBusiness(Aircraft aircraft) {
		if (((PassengerAircraft)aircraft).isEmpty()) {
			((PassengerAircraft)aircraft).setAllBusiness();
			sb.append("2 " + Integer.toString(aircrafts.indexOf(aircraft)) + " " + Integer.toString(0) + " " + Integer.toString(aircraft.getBusinessSeats()) + " " + Integer.toString(0) + "\n");
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks if there is anybody on the plane, if there is, returns false,
	 * If there is nobody on the plane, it resets all the seats and adds as much as
	 * first class seats that can be added without exceeding the floorArea
	 * logs the operation
	 * 
	 * @param aircraft The aircraft at which the seating operation will take place
	 * @return returns if the operation took place
	 */
	public boolean setAllFirstClass(Aircraft aircraft) {
		if (((PassengerAircraft)aircraft).isEmpty()) {
			((PassengerAircraft)aircraft).setAllFirstClass();
			sb.append("2 " + Integer.toString(aircrafts.indexOf(aircraft)) + " " + Integer.toString(0) + " " + Integer.toString(0) + " " + Integer.toString(aircraft.getFirstClassSeats()) + "\n");
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method adds as much as economy seats that can be added without exceeding the floorArea
	 * 
	 * @param aircraft The aircraft at which the seating operation will take place
	 */
	public void setRemainingEconomy(Aircraft aircraft) {
		((PassengerAircraft)aircraft).setRemainingEconomy();
	}
	
	/**
	 * This method adds as much as business seats that can be added without exceeding the floorArea
	 * 
	 * @param aircraft The aircraft at which the seating operation will take place
	 */
	public void setRemainingBusiness(Aircraft aircraft) {
		((PassengerAircraft)aircraft).setRemainingBusiness();
	}
	
	/**
	 * This method adds as much as first class seats that can be added without exceeding the floorArea
	 * 
	 * @param aircraft The aircraft at which the seating operation will take place
	 */
	public void setRemainingFirstClass(Aircraft aircraft) {
		((PassengerAircraft)aircraft).setRemainingFirstClass();
	}
	
	/**
	 * This methods flies the aircraft from one airport to airport
	 * First it checks the validity of the flight, that is if it is already in the wanted
	 * destination, or if that destination is full, or if there is enough fuel to get there.
	 * It adds running cost to the expenses
	 * If the flight can take place, it calls the fly method of aircraft class, assigns
	 * the returned value to flightCost and add it to the expenses
	 * logs the operation
	 * 
	 * @param toAirport Destination of the aircraft
	 * @param aircraftIndex Index of the aircraft in the aircrafts ArrayList
	 * @return returns if the flight took place
	 */
	public boolean fly(Airport toAirport, int aircraftIndex) {
		expenses += runningCost;
		Aircraft currentAircraft = aircrafts.get(aircraftIndex);
		if (currentAircraft.getCurrentAirport() == toAirport) {
			return false;
		} else if (toAirport.isFull()) {
			return false;
		} else if (currentAircraft.canFly(toAirport)) {
			double flightCost = currentAircraft.fly(toAirport);
			expenses += flightCost;
			sb.append("1 " + Integer.toString(toAirport.getID()) + " " + Integer.toString(aircraftIndex) + "\n");
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method checks if a passenger can be loaded to an aircraft,
	 * if it can, necessary operations are made through a method of aircraft class,
	 * loadingFee is assigned to a variable and added to the expenses
	 * logs the operation
	 * if it can't, this method returns false 
	 *
	 * @param passenger Passenger to be loaded to the aircraft
	 * @param airport Airport at which the passenger gets in the aircraft
	 * @param aircraftIndex Index of the aircraft that passenger will be loaded to
	 * @return returns if the operation took place
	 */
	public boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		PassengerAircraft currentAircraft = (PassengerAircraft)aircrafts.get(aircraftIndex);
		if (currentAircraft.isValidOperation(passenger)) {
			double loadingFee = currentAircraft.loadPassenger(passenger);
			expenses += loadingFee;
			sb.append("4 " + Long.toString(passenger.getID()) + " " + Integer.toString(aircraftIndex) + " " + Integer.toString(airport.getID()) + "\n");
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method checks if this airport is a future destination of the passenger,
	 * If it is, passenger can disembark, necessary disembarkation operations are made through
	 * a method from aircraft class, ticketPrice is assigned to a variable and added to the revenue, returns true
	 * logs the operation
	 * If the passenger cannot disembark, operationFee is calculated and added to the expenses, returns false
	 * 
	 * @param passenger Passenger to be unloaded from the aircraft
	 * @param aircraftIndex Index of the aircraft that passenger will be unloaded from
	 * @return returns if the operation took place
	 */
	public boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		PassengerAircraft currentAircraft = (PassengerAircraft)aircrafts.get(aircraftIndex);
		if (currentAircraft.canDisembark(passenger)) {
			double ticketPrice = currentAircraft.unloadPassenger(passenger);
			revenue += ticketPrice;
			sb.append("5 " + Long.toString(passenger.getID()) + " " + Integer.toString(aircraftIndex) + " " + Integer.toString(aircrafts.get(aircraftIndex).getCurrentAirport().getID()) + "\n");
			return true;
		} else {
			double operationFee = currentAircraft.unloadPassenger(passenger);
			expenses += operationFee;
			return false;
		}
	}
	
	/**
	 * This method checks first checks if a passenger can transfer from an aircraft to another aircraft
	 * If it can, it makes the necessary transfer operations using a method from aircraft class,
	 * assigns transferFee to a variable and adds it to the expenses, returns true
	 * logs the operation
	 * If it can't, calculates the operationFee, adds it to the expenses and returns false
	 * 
	 * @param passenger Passenger that will transfer between aircrafts
	 * @param fromAircraftIndex Index of the aircraft that passenger will transfer from
	 * @param toAircraftIndex Index of the aircraft that passenger will transfer to
	 * @return returns if the operation took place
	 */
	public boolean transferPassenger(Passenger passenger, int fromAircraftIndex, int toAircraftIndex) {
		PassengerAircraft fromAircraft = (PassengerAircraft)aircrafts.get(fromAircraftIndex);
		PassengerAircraft toAircraft = (PassengerAircraft)aircrafts.get(toAircraftIndex);
		if(fromAircraft.canTransfer(passenger, toAircraft)) {
			double transferFee = fromAircraft.transferPassenger(passenger, toAircraft);
			expenses += transferFee;
			sb.append("6 " + Long.toString(passenger.getID()) + " " + Integer.toString(fromAircraftIndex) + " " + Integer.toString(toAircraftIndex) + " " + Integer.toString(aircrafts.get(toAircraftIndex).getCurrentAirport().getID()) + "\n");
			return true;
		} else {
			double operationFee = fromAircraft.transferPassenger(passenger, toAircraft);
			expenses += operationFee;
			return false;
		}
	}
	
	/**
	 * A method to load a specific amount of fuel to an aircraft
	 * First it checks if there is enough space to add fuel
	 * Calculates and adds the fuelCost to the expenses
	 * logs the operation
	 * 
	 * @param aircraftIndex Index of the aircraft at which the fueling operation will take place
	 * @param fuelAmount Amount of fuel that will be loaded to the aircraft
	 */
	public void refuel(int aircraftIndex, double fuelAmount) {
		if (aircrafts.get(aircraftIndex).addFuel(fuelAmount)) {
			double fuelCost = aircrafts.get(aircraftIndex).refuel(fuelAmount);
			expenses += fuelCost;
			sb.append("3 " + Integer.toString(aircraftIndex) + " " + Double.toString(fuelAmount) + "\n");
		}
	}
	
	/**
	 * A method to load the maximum amount of fuel to an aircraft
	 * Calculates and adds the fuelCost to the expenses
	 * logs the operation
	 * 
	 * @param aircraftIndex Index of the aircraft at which the fueling operation will take place
	 */
	public void maxRefuel(int aircraftIndex) {
		double fuelCost = aircrafts.get(aircraftIndex).maxRefuel();
		expenses += fuelCost;
		sb.append("3 " + Integer.toString(aircraftIndex) + " " + Double.toString(aircrafts.get(aircraftIndex).getFuelCapacity() - aircrafts.get(aircraftIndex).getFuel()) + "\n");
	}
	
	/**
	 * Writes the total revenue of the airline at the end of the operations to the log
	 */
	public void writeTotalRevenue() {
		sb.append(Double.toString(revenue) + "\n");
	}
}
