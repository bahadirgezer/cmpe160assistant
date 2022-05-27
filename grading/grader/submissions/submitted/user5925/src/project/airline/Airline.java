package project.airline;

import java.util.ArrayList;

import project.airline.aircraft.Aircraft;
import project.airline.aircraft.PassengerAircraft;
import project.airline.aircraft.concrete.JetPassengerAircraft;
import project.airline.aircraft.concrete.PropPassengerAircraft;
import project.airline.aircraft.concrete.RapidPassengerAircraft;
import project.airline.aircraft.concrete.WidebodyPassengerAircraft;
import project.airport.Airport;
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
	 int currentAircraftNumber = 0;
	 double runningCost;
	 
	 public Airline(int maxAircraftCount, double operationalCost) {
		 this.maxAircraftCount = maxAircraftCount;
		 this.operationalCost = maxAircraftCount;
	 }
	 
	 
	 
	 
	 ArrayList<Aircraft> aircrafts_list = new ArrayList<Aircraft>();
	 
	 public void createAircraft(int a, Airport located_airport) {
		 if(a ==1) {
			 JetPassengerAircraft asd = new JetPassengerAircraft(located_airport);
		 }
		 else if (a==2) {
			 PropPassengerAircraft asd = new PropPassengerAircraft(located_airport);
		 }
		 else if (a==3) {
			 RapidPassengerAircraft asd = new RapidPassengerAircraft(located_airport);
		 }
		 else {
			 WidebodyPassengerAircraft asd = new WidebodyPassengerAircraft(located_airport);
		 }
		 aircrafts_list.add(asd);
		 
		 appendAircraft(asd);
	 }
	 
	 public void createPassenger()
	 
	 public void appendAircraft(Aircraft Aircraft) {
		 aircrafts_list.add(Aircraft);
		 currentAircraftNumber += 1;
	 }
	 
	 
	
	 
	 double profit() {
		 return revenue - expenses;
	 }
	 
	 
	 public void fulle(Aircraft aircraft) {
		 expenses += (aircraft.getFuelCapacity() - aircraft.current_fuel) * aircraft.located_airport.getFuelCost();
		 aircraft.current_fuel = aircraft.getFuelCapacity();
		 
	 }
	 
	 boolean canFly(Airport toAirport, int aircraftIndex) {
		 PassengerAircraft aircraft2 = (PassengerAircraft) aircrafts_list.get(aircraftIndex);
		 double distance1 = aircraft2.getDistance(aircraft2.located_airport, toAirport);
		 if (aircraft2.getFuelCapacity() > aircraft2.getFuelConsumption(distance1) && toAirport.current_aircrafts.size() < toAirport.getAircraftCapacity()) {
		 	return true;
		 }
		 else {
			 return false;
		 }
		 
	 }
	 
	 boolean fly(Airport toAirport, int aircraftIndex) {
		runningCost = operationalCost * currentAircraftNumber;
		PassengerAircraft aircraft3 = (PassengerAircraft) aircrafts_list.get(aircraftIndex);
		aircraft3.fly(toAirport);
		return true;
		 
	 }
	 
	 
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
	 boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		 PassengerAircraft aircraft = (PassengerAircraft) aircrafts_list.get(aircraftIndex);
		 expenses += aircraft.loadPassenger(passenger);
		 airport.passenger_list.remove(passenger);
		 aircraft.passengers_airplane.add(passenger);
		 aircraft.max_weight += passenger.getWeight();
		 return true;
		 
	 }
	 
	 boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		 PassengerAircraft aircraft = (PassengerAircraft) aircrafts_list.get(aircraftIndex);
		 revenue += aircraft.unloadPassenger(passenger);
		 Airport airport = passenger.last_disembarkation;
		 airport.passenger_list.add(passenger);
		 aircraft.passengers_airplane.remove(passenger);
		 aircraft.max_weight -= passenger.getWeight();
		 return true;
		 
	 }
}
