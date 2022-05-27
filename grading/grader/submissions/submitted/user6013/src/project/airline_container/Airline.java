package project.airline_container;
import project.airline_container.aircraft_container.PassengerAircraft;
import project.airline_container.aircraft_container.concrete_container.*;
import java.util.ArrayList;
import java.util.HashMap;

import project.airport_container.Airport;
import project.airport_container.HubAirport;
import project.airport_container.MajorAirport;
import project.airport_container.RegionalAirport;
import project.passenger_container.BusinessPassenger;
import project.passenger_container.EconomyPassenger;
import project.passenger_container.FirstClassPassenger;
import project.passenger_container.LuxuryPassenger;
import project.passenger_container.Passenger;

public class Airline {
	/**
	 * Contains all airports.
	 */
	public HashMap<Integer, Airport> Airports = new HashMap<Integer, Airport>();
	/**
	 * Contains hypothetical aircrafts used at the calculations. 
	 */
	public ArrayList <PassengerAircraft> notrealaircrafts = new ArrayList<>();
	/**
	 * number of maximum aircrafts for this airline
	 */
	int maxAircraftCount;
	/**
	 * operational cost for this airline
	 */
	double operationalCost;
	/**
	 * revenue for this airline
	 */
	public double revenues;
	/**
	 * expenses for this airline
	 */
	public double expenses;
	/**
	 * is the operationFee of PropPassengerAircraft objects
	 */
	double prop;
	/**
	 * is the operationFee of WidebodyPassengerAircraft objects.
	 */
	double widebody;
	/**
	 * is the operationFee of RapidPassengerAircraft objects.
	 */
	double rapid;
	/**
	 * is the operationFee of JetPassengerAircraft objects.
	 */
	double jet;
	public ArrayList <String> outputlog = new ArrayList<>();
	/**
	 * Contains all real aircrafts
	 */
	public ArrayList <PassengerAircraft> aircrafts = new ArrayList<>();
	/**
	 * Constructs an instance Airline class which includes all functionalities for operations of program.
	 * @param maxAircraftCount number of maximum aircrafts for the airline
	 * @param operational_cost the operationalCost of the airline
	 * @param prop operationFee of PropPassengerAircraft 
	 * @param widebody operationFee of WidebodyPassengerAircraft 
	 * @param rapid operationFee of RapidPassengerAircraft objects
	 * @param jet operationFee of JetPassengerAircraft objects
	 */
	public Airline(int maxAircraftCount,double operational_cost,double prop,double widebody,double rapid,double jet){
		this.maxAircraftCount = maxAircraftCount;
		this.jet=jet;
		this.widebody=widebody;
		this.rapid= rapid;
		this.prop=prop;
		this.operationalCost=operational_cost;
	}
	/**
	 * Returns profit or loss according to the sign of the return value.
	 * @return profit
	 */
	public String getProfit() {
		return String.valueOf(this.revenues-this.expenses);
	}
	/**
	 * Creates passenger.
	 * @param type passenger's seat type
	 * @param a id of the passenger
	 * @param b weight of the passenger
	 * @param c baggage count of the passenger
	 * @param de list of the passanger's destinations
	 */
	public void createPassenger(String type,long a,double b,int c,String[] de) {
			ArrayList <Airport> dest = new ArrayList<>();
			for(String x:de) {
				dest.add(Airports.get(Integer.parseInt(x.strip())));
			}
			switch(type){
			case "economy":
				new EconomyPassenger(1,a,b,c,dest);
				break;
			case "business":
				new BusinessPassenger(2,a,b,c,dest);
				break;
			case "first":
				new FirstClassPassenger(3,a,b,c,dest);
				break;
			case "luxury":
				new LuxuryPassenger(4,a,b,c,dest);
				break;
				
		}
	}
	/**
	 * Sorts all airports according to number of passengers they have.
	 * @return An arrayList of id's of airports in a sorted manner
	 */
	public ArrayList<Integer> AirportsSorted(){
		ArrayList <Integer> sortedids = new ArrayList<>();
		for(int x:Airports.keySet()) {
	    	if(!(sortedids.isEmpty())) {
	    		for(int i =0;i<=sortedids.size();i++) {
	    			if(i == sortedids.size()) {
	    				sortedids.add(x);
	    				break;
	    			}
	    			if(Airports.get(x).getNo_of_Passengers()>Airports.get(sortedids.get(i)).getNo_of_Passengers()) {
	    				sortedids.add(i, x);
	    				break;
	    			}
	    		}
	    	}else {
	    		sortedids.add(x);
	    	}
	    }
		return sortedids;
	}
	/**
	 * Finds possible destinations for aircrafts at the given airport
	 * @param a airport
	 * @return arrayList of possible destinations
	 */
	public ArrayList<Integer> FindRoutes(Airport a){
		ArrayList <Integer> routes = new ArrayList<>();
		HashMap <Integer,Integer> frequency = new HashMap <Integer,Integer>();
		for(Passenger x:a.getPass()) {
			for(Airport y:x.getDestinations()) {
				if(frequency.containsKey(y.getID())) {
					frequency.put(y.getID(), frequency.get(y.getID())+1);
				}else{
					frequency.put(y.getID(), 1);
				}
			}
			
		}
		for(int x:frequency.keySet()) {
	    	if(!(routes.isEmpty())) {
	    		for(int i =0;i<=routes.size();i++) {
	    			if(i == routes.size()) {
	    				routes.add(x);
	    				break;
	    			}
	    			if(frequency.get(x)>frequency.get(routes.get(i))) {
	    				routes.add(i, x);
	    				break;
	    			}
	    		}
	    	}else {
	    		routes.add(x);
	    	}
	    }
		return routes;
	}
	/**
	 * Creates an aircraft of given type at given airport
	 * @param a aircraft type
	 * @param b airport
	 */
	public void createAircraft(int a,Airport b) {
		if(maxAircraftCount>aircrafts.size() && b.getaircraftRatio()<1) {
			b.setAircraftNo(b.getAircraftNo()+1);
			
			switch(a){
			case 1:
				aircrafts.add(new JetPassengerAircraft(this.jet,b));
				outputlog.add(0+" "+b.getID()+" "+3);
				break;
			case 2:
				aircrafts.add(new PropPassengerAircraft(this.prop,b));
				outputlog.add(0+" "+b.getID()+" "+0);
				break;
			case 3:
				aircrafts.add(new RapidPassengerAircraft(this.rapid,b));
				outputlog.add(0+" "+b.getID()+" "+2);
				
				break;
			case 4:
				aircrafts.add(new WidebodyPassengerAircraft(this.widebody,b));
				outputlog.add(0+" "+b.getID()+" "+1);
				break;
				
		}
			}
		}
	/**
	 * Creates airports with given parameters.
	 * @param type type of airport
	 * @param a id of the airport
	 * @param b x coordinate of the airport
	 * @param c y coordinate of the airport
	 * @param d fuel cost of the airport
	 * @param e operation fee of airport
	 * @param f aircraft capacity of the airport
	 */
	public void createAirport(String type,int a,double b,double c,double d,double e,int f) {
		switch(type) {
		case "hub":
			Airports.put(a, new HubAirport(a,b,c,d,e,f));
			break;
		case "major":
			Airports.put(a, new MajorAirport(a,b,c,d,e,f));
			break;
		case "regional":
			Airports.put(a, new RegionalAirport(a,b,c,d,e,f));
			break;
		}
	
	}
	/**
	 * fills up the the given aircrafts fuel to the fuel capacity.
	 * @param aircraftIndex
	 * @return true
	 */
	public boolean fillUp(int aircraftIndex) {
		expenses += aircrafts.get(aircraftIndex).fillUp();
		return true;
	}
	/**
	 * Completes aircrafts fuel to specified amount.
	 * @param aircraftIndex
	 * @param fuel level of fuel that the aircraft will have
	 * @return true;
	 */
	public boolean addFuel(int aircraftIndex,double fuel) {
		double b =fuel-aircrafts.get(aircraftIndex).getFuel();
		double a =aircrafts.get(aircraftIndex).addFuel(fuel);
		//outputlog.add(3+" "+aircraftIndex+" "+b+" = "+(-a));
		outputlog.add(3+" "+aircraftIndex+" "+b);
		expenses += a;
		return true;
	}
	/**
	 * calculates running cost.
	 * @return running cost
	 */
	double getRunningCost() {
		return this.aircrafts.size()*this.operationalCost;
	}
	/**
	 * Gets total number of aircrafts.
	 * @return total number of aircrafts
	 */
    public int getNo_of_Aircrafts() {
    	return this.aircrafts.size();
    }
    /**
     * Gets maximum number of aircrafts.
     * @return maximum number of aircrafts
     */
    public int getMax_Aircraft() {
    	return this.maxAircraftCount;
    }
    /**
     * Flies the aircraft to the airport.
     * Returns true if the operation is successful else false.
     * @param toAirport destination airport
     * @param aircraftIndex 
     * @return true/false
     */
	public boolean fly(Airport toAirport, int aircraftIndex) {
		expenses += getRunningCost();
		if(aircrafts.get(aircraftIndex).fly_check(toAirport)) {
			double a = aircrafts.get(aircraftIndex).fly(toAirport);
			outputlog.add(1+" "+toAirport.getID()+" "+aircraftIndex);
			//outputlog.add(1+" "+toAirport.getID()+" "+aircraftIndex+" = "+(-a-getRunningCost()));
			expenses += a;
			return true;
		}
		else {
			
			return false;
		}
	}
/**
 * Loads passenger at the airport to the aircraft.
 * Returns true if the operation is successful else false.
 * @param passenger 
 * @param airport current airport
 * @param aircraftIndex
 * @return true/false
 */
	public boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		if(aircrafts.get(aircraftIndex).can_loadPassenger(passenger)&&airport.passenger_check(passenger)&&airport.equals(aircrafts.get(aircraftIndex).getCurrentAirport())) {
			passenger.board(passenger.getPasstype());
			double a =aircrafts.get(aircraftIndex).loadPassenger(passenger);
			//outputlog.add(4+" "+passenger.getID()+" "+aircraftIndex+" "+airport.getID()+" "+passenger.getSeatType());
			outputlog.add(4+" "+passenger.getID()+" "+aircraftIndex+" "+airport.getID());
			//outputlog.add(4+" "+passenger.getID()+" "+aircraftIndex+" "+airport.getID()+" = "+(-a));
			expenses += a;
			return true;
		}else {
			expenses +=aircrafts.get(aircraftIndex).loadPassenger(passenger);
			return false;
		}
	}
/**
 * Checks if passenger can be loaded or not.
 * Returns true if passenger can be loaded else false.
 * @param passenger
 * @param airport current airport
 * @param aircraftIndex
 * @return
 */
	public boolean canLoadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		if(aircrafts.get(aircraftIndex).can_loadPassenger(passenger)&&airport.passenger_check(passenger)&&airport.equals(aircrafts.get(aircraftIndex).getCurrentAirport())) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * Calculates distance between airports.
	 * @param a 1. Airport
	 * @param b 2. Airport
	 * @return distance
	 */
	public static double getDistance(Airport a,Airport b) {
		return Airport.getdistance(a, b);
	}
	/**
	 * Unloads the passenger.Returns true if the operation is successful else returns false.
	 * Updates expenses and revenues accordingly. 
	 * @param passenger
	 * @param aircraftIndex
	 * @return true/false
	 */
	public boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		if(passenger.can_disembark(aircrafts.get(aircraftIndex).getCurrentAirport())) {
			double a =aircrafts.get(aircraftIndex).unloadPassenger(passenger);
			//outputlog.add(5+" "+passenger.getID()+" "+aircraftIndex+" "+aircrafts.get(aircraftIndex).getCurrentAirport().getID()+" = "+a);
			outputlog.add(5+" "+passenger.getID()+" "+aircraftIndex+" "+aircrafts.get(aircraftIndex).getCurrentAirport().getID());
			this.revenues += a;
			return true;
		}else {
			this.expenses +=aircrafts.get(aircraftIndex).unloadPassenger(passenger);
			return false;
		}
	}
	/**
	 * Checks whether the aircraft has any empty seats.
	 * Returns true if the aircraft is full else false.
	 * @param aircraftIndex
	 * @return true/false
	 */
	public boolean isFull(int aircraftIndex) {
		return aircrafts.get(aircraftIndex).isFull();
		}
	/**
	 * Checks whether the aircraft has any empty seats of given type.
	 * Returns true if the specified seat type of the aircraft is full else false.
	 * @param aircraftIndex
	 * @param seatType
	 * @return true/false
	 */
	public boolean isFull(int aircraftIndex,int seatType) {
		// TODO Auto-generated method stub
		return aircrafts.get(aircraftIndex).isFull(seatType);	
	}
	/**
	 * Checks whether is there anybody on the aircraft.
	 * Returns true if the aircraft is empty else false.
	 * @param aircraftIndex
	 * @param seatType
	 * @return true false
	 */
	public boolean isEmpty(int aircraftIndex) {
		// TODO Auto-generated method stub
		return aircrafts.get(aircraftIndex).isEmpty();
	}
	/**
	 * Creates the given numbers of seats for the aircraft.
	 * Returns true if the operation is successful else false.
	 * @param economy number of economy seats
	 * @param business number of business seats
	 * @param firstClass number of first-class seats
	 * @return true/false
	 */
	public boolean setSeats(int economy, int business, int firstClass,int aircraftIndex) {
		// TODO Auto-generated method stub
		outputlog.add(2+" "+aircraftIndex+" "+economy+" "+business+" "+firstClass);
		return aircrafts.get(aircraftIndex).setSeats(economy, business, firstClass);
	}
	/**
	 * Creates hypothetical aircraft for calculations before actually creating real aircrafts. 
	 * @param aircraft_Type
	 */
	public void notrealaircraftcreation(int aircraft_Type){
		notrealaircrafts.clear();
		switch(aircraft_Type) {
		case 1:
			notrealaircrafts.add(new JetPassengerAircraft());
			break;
		case 2:
			notrealaircrafts.add(new PropPassengerAircraft());
			break;
		case 3:
			notrealaircrafts.add(new RapidPassengerAircraft());
			break;
		case 4:
			notrealaircrafts.add(new WidebodyPassengerAircraft());
			break;
		}
		
	}	
	/**
	 * Checks if the given numbers of seats can be created for an aircraft.
	 * Returns true if the operation is successful else false.
	 * @param economy number of economy seats
	 * @param business number of business seats
	 * @param firstClass number of first-class seats
	 * @return
	 */
	public boolean canSetSeats(int economy, int business, int firstClass) {
		// TODO Auto-generated method stub

		return notrealaircrafts.get(0).canSetSeats(economy, business, firstClass);
	}
	/**
	 * Fills the given aircraft with solely economy seats.
	 * Returns true if the operation is successful else false.
	 * @param aircraftIndex
	 * @return true/false
	 */
	public boolean setAllEconomy(int aircraftIndex) {
		// TODO Auto-generated method stub
		return aircrafts.get(aircraftIndex).setAllEconomy();
	}
	/**
	 * Fills the given aircraft with solely business seats.
	 * Returns true if the operation is successful else false.
	 * @param aircraftIndex
	 * @return true/false
	 */
	public boolean setAllBusiness(int aircraftIndex) {
		// TODO Auto-generated method stub
		return aircrafts.get(aircraftIndex).setAllBusiness();
	}
	/**
	 * Fills the given aircraft with solely first-class seats.
	 * Returns true if the operation is successful else false.
	 * @param aircraftIndex
	 * @return true/false
	 */
	public boolean setAllFirstClass(int aircraftIndex) {
		// TODO Auto-generated method stub
		return aircrafts.get(aircraftIndex).setAllFirstClass();
	}
	/**
	 * Creates Economy seats until there is no available space on given aircraft without interfering with existing seats.
	 * @param aircraftIndex
	 * @return true
	 */
	public boolean setRemainingEconomy(int aircraftIndex) {
		// TODO Auto-generated method stub
		return aircrafts.get(aircraftIndex).setRemainingEconomy();
	}

	/**
	 * Creates Business seats until there is no available space on given aircraft without interfering with existing seats.
	 * @param aircraftIndex
	 * @return true
	 */
	public boolean setRemainingBusiness(int aircraftIndex) {
		// TODO Auto-generated method stub
		return aircrafts.get(aircraftIndex).setRemainingBusiness();
	}
	/**
	 * Creates First Class seats until there is no available space on given aircraft without interfering with existing seats.
	 * @param aircraftIndex
	 * @return true
	 */
	public boolean setRemainingFirstClass(int aircraftIndex) {
		// TODO Auto-generated method stub
		return aircrafts.get(aircraftIndex).setRemainingFirstClass();
	}
	/**
	 * Removes all seats configured from given aircraft if no passenger is on the aircraft.
	 * Returns true if the operation is successful else false.
	 * @param aircraftIndex
	 * @return true/false
	 */
	public boolean setAllEmpty(int aircraftIndex) {
		if(aircrafts.get(aircraftIndex).getTotaloccupiedseats()==0) {
			return aircrafts.get(aircraftIndex).setAllEmpty();
		}else {
			return false;
		}
		
	}
	/**
	 * This method calculates fuel needed for the given specific type of fully loaded aircraft to reach the destination airport which is "distance" away.
	 * @param aircraft_Type
	 * @param distance
	 * @return fuel_needed
	 */
	public double CalculateWeightofFuelNeeded(int aircraft_Type,double distance) {
		double fuel_needed=0;
		switch(aircraft_Type) {
		case 1:
			fuel_needed = JetPassengerAircraft.CalculateWeightofFuelNeeded(distance);
			break;
		case 2:
			fuel_needed = PropPassengerAircraft.CalculateWeightofFuelNeeded(distance);
			break;
		case 3:
			fuel_needed = RapidPassengerAircraft.CalculateWeightofFuelNeeded(distance);
			break;
		case 4:
			fuel_needed =  WidebodyPassengerAircraft.CalculateWeightofFuelNeeded(distance);
			break;
		}
		return fuel_needed;
	}
	/**
	 * This method returns available weight for each type of aircraft when they are empty.
	 * @param aircraft_Type
	 * @return available_weight
	 */
	public double getAvWeight(int aircraft_Type) {
		double available_weight =0;
		switch(aircraft_Type) {
		case 1:
			available_weight = JetPassengerAircraft.getAvWeight();
			break;
		case 2:
			available_weight = PropPassengerAircraft.getAvWeight();
			break;
		case 3:
			available_weight = RapidPassengerAircraft.getAvWeight();
			break;
		case 4:
			available_weight =  WidebodyPassengerAircraft.getAvWeight();
			break;
		}
		return available_weight;
	}
}
