package project.airline;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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

/**
 * This class aims creating an airline
 * @author Ramazan Onur Acar
 *
 */

public class Airline {

	 int maxAircraftCount;
	 double operationalCost;
	 double revenue = 0;
	 double expenses = 0;
	 int currentAircraftNumber = 0;
	 double runningCost;
	 String filename;
	 public PrintWriter file;
	 
	 /** 
	  * This constructs an airline with MaxAircraftcount & operationalCost
	  * @param maxAircraftCount , maximum no. of aircraft this airline can have
	  * @param operationalCost , operational cost value for this airline
	  * @param filename, name of the file that i will be writing on
	  * @throws IOException 
	  */
	 public Airline(int maxAircraftCount, double operationalCost, String filename) throws IOException {
		 this.maxAircraftCount = maxAircraftCount;
		 this.operationalCost = maxAircraftCount;
		 this.filename = filename;
		 FileWriter fw = new FileWriter(filename);
		 BufferedWriter bw = new BufferedWriter(fw);
		 file = new PrintWriter(bw);

	 }	
	 

	 
	 
	 
	 
	 public ArrayList<Aircraft> aircrafts_list = new ArrayList<Aircraft>();
	 public ArrayList<Airport> airports_list = new ArrayList<Airport>();
	 public ArrayList<Passenger> all_passengers = new ArrayList<Passenger>();
	 
	 
	 
	 /**
	  * This method helps me to create aircrafts in Main.java by importing Airline.java, with following parameters
	  * @param a 
	  * @param located_airport
	  * @param operationFee
	  */
	 public void createAircraft(int a, Airport located_airport, double operationFee) {
		 int ind = airports_list.indexOf(located_airport);
		 
		 if(a ==3) {
			 JetPassengerAircraft asd = new JetPassengerAircraft(located_airport,operationFee);
			 appendAircraft(asd);
			 airports_list.get(ind).current_aircrafts.add(asd);
		 }
		 else if (a==0) {
			 PropPassengerAircraft asd = new PropPassengerAircraft(located_airport,operationFee);
			 appendAircraft(asd);
			 airports_list.get(ind).current_aircrafts.add(asd);
		 }
		 else if (a==2) {
			 RapidPassengerAircraft asd = new RapidPassengerAircraft(located_airport,operationFee);
			 appendAircraft(asd);
			 airports_list.get(ind).current_aircrafts.add(asd);
		 }
		 else {
			 WidebodyPassengerAircraft asd = new WidebodyPassengerAircraft(located_airport,operationFee);
			 appendAircraft(asd);
			 airports_list.get(ind).current_aircrafts.add(asd);
		 }
		 file.println("0 " + located_airport.getID()+ " " + a);
		 
	 }
	 
	 /**
	  * This method helps me to create Passengers and locate them in their initial locations
	  * @param type , helps me to detect the passenger type
	  * @param ID , id of the passenger
	  * @param weight , weight of the passenger
	  * @param baggageCount , number of passenger's baggage
	  * @param destinations , ArrayList of destinations that passenger wants to go
	  * @param aircraftTypeMultiplier, aircraftTypeMultiplier of the passenger
	  */
	 public void createPassenger(int type, long ID, double weight, int baggageCount, ArrayList <Airport> destinations) {

		 if (type ==1 ) {
			 EconomyPassenger bcd = new EconomyPassenger(type,ID,weight,baggageCount,destinations);
			 bcd.initial_airport = destinations.get(0);
			 all_passengers.add(bcd);
		 }
		 else if (type == 2) {
			 BusinessPassenger bcd = new BusinessPassenger(type,ID,weight,baggageCount,destinations);
			 all_passengers.add(bcd);
		 }
		 else if (type == 3) {
			 FirstClassPassenger bcd = new FirstClassPassenger(type,ID,weight,baggageCount,destinations);
			 bcd.initial_airport = destinations.get(0);
			 all_passengers.add(bcd);
		 }
		 else {
			 LuxuryPassenger bcd = new LuxuryPassenger(type,ID,weight,baggageCount,destinations);
			 bcd.initial_airport = destinations.get(0);
			 all_passengers.add(bcd);
		 }
		 
	 }
	 
	 /**
	  * This method helps me to create different type of airports in Main.java
	  * @param c , helps me to detect the type of the airport
	  * @param ID , id of the airport
	  * @param x , x-axis component of the location
	  * @param y , y-axis component of the location
	  * @param fuelCost , fuelCost in that airport
	  * @param operationFee , operationFee in that airport
	  * @param aircraftCapacity , maximum number of aircrafts that can be stored in that airport
	  */
	 public void createAirport(int c,int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		 
		 if (c ==1) {
			 HubAirport qwe = new HubAirport(ID,x,y,fuelCost,operationFee,aircraftCapacity);
			 airports_list.add(qwe);
		 }
		 else if (c ==2) {
			 MajorAirport qwe = new MajorAirport(ID,x,y,fuelCost,operationFee,aircraftCapacity);
			 airports_list.add(qwe);
		 }
		 else {
			 RegionalAirport qwe = new RegionalAirport(ID,x,y,fuelCost,operationFee,aircraftCapacity);
			 airports_list.add(qwe);
		 }
	 }
	 /**
	  * This helps me to add my aircraft to airline's collection of aircrafts
	  * @param Aircraft , the specific aircraft that i will add to the ArrayList of aircrafts
	  */
	 public void appendAircraft(Aircraft Aircraft) {
		 aircrafts_list.add(Aircraft);
		 currentAircraftNumber += 1;
	 }
	 
	 
	
	 /**
	  * this helps me to calculate profit of my airline
	  * @return the profit by substracting expenses from revenue
	  */
	 double profit() {
		 return revenue - expenses;
	 }
	 
	 /**
	  * This methods help me to fill up the fuel of a specific aircraft
	  * @param aircraft
	  */
	 public void fulle(Aircraft aircraft) {
		 expenses += aircraft.fillUp();
		 
	 }
	  /**
	   * this method help me to understand whether an aircraft can fly to an airport
	   * @param toAirport , the airport that we want to know if our aircraft can fly
	   * @param aircraftIndex , the index of the aircraft in my aircrafts_list
	   * @return , returns true if the fly can take place
	   */
	 public boolean canFly(Airport toAirport, int aircraftIndex) {
		 PassengerAircraft aircraft2 = (PassengerAircraft) aircrafts_list.get(aircraftIndex);
		 double distance1 = aircraft2.getDistance(aircraft2.located_airport, toAirport);
		 if (aircraft2.getFuelCapacity() > aircraft2.getFuelConsumption(distance1)) {
		 	return true;
		 }
		 else {
			 return false;
		 }
		 
	 }
	 /**
	  * this methods flies the aircraft to the airport
	  * @param toAirport , the airport that our aircraft flies
	  * @param aircraftIndex , the index of our aircraft
	  * @return , returns true when flight take place
	  */
	 public boolean fly(Airport toAirport, int aircraftIndex) {
		runningCost = operationalCost * currentAircraftNumber;
		PassengerAircraft aircraft3 = (PassengerAircraft) aircrafts_list.get(aircraftIndex);
		aircraft3.fly(toAirport);
		file.println("1 " + toAirport.getID() + " " + aircraftIndex);
		return true;
		 
	 }
	 
	 
	 /**
	  * this helps me to see if i can load a passenger
	  * @param passenger , the passenger i want to see if i can load
	  * @param airport , the airport where our passenger is currently in. However not used in this method
	  * @param aircraftIndex , the index of our aircraft
	  * @return , returns true if we can load the passenger to the aircraft
	  */
	 boolean canLoad(Passenger passenger, Airport airport, int aircraftIndex) {
		 PassengerAircraft aircraft1 = (PassengerAircraft) aircrafts_list.get(aircraftIndex);
		 int c = 0;
		 if (aircraft1.weight + aircraft1.current_fuel * aircraft1.getFuelWeight() + passenger.getWeight() <= aircraft1.max_weight) {
			 if(passenger instanceof EconomyPassenger) {
				 if(aircraft1.isFull(1) == false) {
					 c = 1;
				 }	 
			 }
			 else if (passenger instanceof BusinessPassenger) {
				 if(aircraft1.isFull(2) == false) {
					 c =1;
				 }
				 else if (aircraft1.isFull(1) == false) {
					 c =1;
				 }
			 }
			 else if (passenger instanceof FirstClassPassenger || passenger instanceof LuxuryPassenger) {
				 if(aircraft1.isFull(3) == false) {
					 c = 1;
				 }
				 else if (aircraft1.isFull(2) == false) {
					 c=1;
				 }
				 else if (aircraft1.isFull(1)==false) {
					 c =1;
				 }
			 }
			 
		 }
		 
		 if (c==1) {
			 return true;
		 }
		 else {
			 return false;
		 }
	 }
	 
	 /**
	  * after checking whether we can load or not, this method makes the loading
	  * @param passenger , the passenger we are loading
	  * @param airport , the airport where our passenger is currently in
	  * @param aircraftIndex , the index of our aircraft
	  * @return , returns true after the loading operation is done
	  */
	 public boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		 PassengerAircraft aircraft = (PassengerAircraft) aircrafts_list.get(aircraftIndex);
		 expenses += aircraft.loadPassenger(passenger);
		 airport.passenger_list.remove(passenger);
		 aircraft.passengers_airplane.add(passenger);
		 aircraft.max_weight += passenger.getWeight();
		 file.println("4 " + passenger.getID() + " " + aircraftIndex + " " + airport.getID());
		 return true;
		 
	 }
	 /**
	  * unloads the passenger from the the aircraft
	  * @param passenger , the passenger we are unloading
	  * @param aircraftIndex , the index of our aircraft
	  * @return , returns true after the unloading operation is done
	  */
	 public boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		 PassengerAircraft aircraft = (PassengerAircraft) aircrafts_list.get(aircraftIndex);
		 revenue += aircraft.unloadPassenger(passenger);
		 Airport airport = passenger.last_disembarkation;
		 airport.passenger_list.add(passenger);
		 aircraft.passengers_airplane.remove(passenger);
		 aircraft.max_weight -= passenger.getWeight();
		 file.println("5 " + passenger.getID() +" " + aircraftIndex + " " + airport.getID());
		 return true;
		 
	 }
	/**
	 *  
	 * @return , helps me get the profit
	 */
	public double getProfit() {
		return profit();
	}
}
