package project.airline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

import project.airline.aircraft.Aircraft;
import project.airline.aircraft.PassengerAircraft;
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
 * Airline collects all the other classes and creates new methods to be used in main. 
 * 
 * @author Tarýk Can Ozden 2020401126
 *
 */
public class Airline {
	int maxAircraftCount;
	double operationalCost;
	double expenses;
	double revenue;
	ArrayList<PassengerAircraft> aircrafts = new ArrayList<PassengerAircraft>();
	public HashMap<Integer, Airport> airports = new HashMap<Integer, Airport>();
	public ArrayList<Airport> airportsList = new ArrayList<Airport>();
	public ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	
	/**
	 * Constructor method for the airline.
	 * @param maxAircraftCount maximum number of aircrafts
	 * @param operationalCost 
	 */
	public Airline(int maxAircraftCount, double operationalCost) {
		this.maxAircraftCount = maxAircraftCount;
		this.operationalCost = operationalCost;
		this.expenses = 0;
		this.revenue = 0;
	}
	/**
	 * returns the aircraft list
	 * @return the aircraft list.
	 */
	public ArrayList<PassengerAircraft> getAircrafts() {
		return aircrafts;
	}
	/**
	 * sets aircrafts list.
	 * @param aircrafts The list of aircrafts.
	 */
	public void setAircrafts(ArrayList<PassengerAircraft> aircrafts) {
		this.aircrafts = aircrafts;
	}

	
	/**
	 * Creates airports according to the input in main, puts them in an arraylist and hashmap.
	 * @param type Airport type as String. (hub,major or regional) 
	 * @param airportID ID of the airport.
	 * @param aircraftCapacity Maximum number of aircrafts this new airport can hold.
	 * @param X X coordinate of the airport.
	 * @param Y Y coordinate of the airport.
	 * @param fuelCost Cost of the fuel in this airport.
	 * @param operationFee Operation fee in this airport.
	 */
	public void addAirport(String type, int airportID, int aircraftCapacity, int X, int Y, double fuelCost, double operationFee) {
		if(Objects.equals(type, "hub")) {
			HubAirport newPort = new HubAirport(airportID,  X, Y, fuelCost, operationFee, aircraftCapacity);
			airports.put(airportID, newPort);
			airportsList.add(newPort);
		} else if (Objects.equals(type, "major")) {
			MajorAirport newPort = new MajorAirport(airportID,  X, Y, fuelCost, operationFee, aircraftCapacity);
			airports.put(airportID, newPort);
			airportsList.add(newPort);
		} else {
			RegionalAirport newPort = new RegionalAirport(airportID,  X, Y, fuelCost, operationFee, aircraftCapacity);
			airports.put(airportID, newPort);
			airportsList.add(newPort);
		}
		return;
	}
	/**
	 * Creates passengers according to the input in main, puts them in an arraylist.
	 * @param type Passenger type as strings. (economy, business, first, luxury)
	 * @param ID ID of the passenger.
	 * @param weight Weight of the passenger.
	 * @param baggageCount Number of baggages the passenger has.
	 * @param currentAirportInd The index of the passenger's current airport, according to the destinations arraylist.
	 * @param destination Destinations list of the passenger, with the first one being the current destination.
	 */
	public void addPassenger(String type, long ID, double weight, int baggageCount, int currentAirportInd, ArrayList<Integer> destination) {
		if(Objects.equals(type, "economy")) {
			EconomyPassenger newPassenger = new EconomyPassenger(ID, weight, baggageCount, currentAirportInd);
			
			newPassenger.setDestinationIDs(destination);
			ArrayList<Airport> passDests = new ArrayList<Airport>();
			for(int i = 0; i < destination.size(); i++) {
				passDests.add(airports.get(destination.get(i)));
			}
			newPassenger.setDestinations(passDests);
			//System.out.println(passDests);
			newPassenger.setCurrentAirport(passDests.get(0));
			//System.out.println(destination);
			passengers.add(newPassenger);
		} else if(Objects.equals(type, "business")) {
			BusinessPassenger newPassenger = new BusinessPassenger(ID, weight, baggageCount, currentAirportInd);
			newPassenger.setDestinationIDs(destination);
			ArrayList<Airport> passDests = new ArrayList<Airport>();
			for(int i = 0; i < destination.size(); i++) {
				passDests.add(airports.get(destination.get(i)));
			}
			newPassenger.setDestinations(passDests);
			
			newPassenger.setCurrentAirport(passDests.get(0));
			passengers.add(newPassenger);
		} else if (Objects.equals(type, "first")) {
			FirstClassPassenger newPassenger = new FirstClassPassenger(ID, weight, baggageCount, currentAirportInd);
			newPassenger.setDestinationIDs(destination);
			ArrayList<Airport> passDests = new ArrayList<Airport>();
			for(int i = 0; i < destination.size(); i++) {
				passDests.add(airports.get(destination.get(i)));
			}
			newPassenger.setDestinations(passDests);
			newPassenger.setCurrentAirport(passDests.get(0));
			passengers.add(newPassenger);
		} else {
			LuxuryPassenger newPassenger = new LuxuryPassenger(ID, weight, baggageCount, currentAirportInd);
			newPassenger.setDestinationIDs(destination);
			ArrayList<Airport> passDests = new ArrayList<Airport>();
			for(int i = 0; i < destination.size(); i++) {
				passDests.add(airports.get(destination.get(i)));
			}
			newPassenger.setDestinations(passDests);
			newPassenger.setCurrentAirport(passDests.get(0));
			passengers.add(newPassenger);

		}
		return;
	}
	/**
	 * Flies the aircraft with aircraft index to toAirport.
	 * @param toAirport  The airport aircraft will fly to.
	 * @param aircraftIndex  The index of aircraft in the aircrafts arraylist.
	 * @return true if can fly.
	 */
	boolean fly(Airport toAirport, int aircraftIndex) {
		double expensesR = operationalCost * aircrafts.size();
		PassengerAircraft currAircraft = this.aircrafts.get(aircraftIndex);
		expensesR += currAircraft.fly(toAirport);
		System.out.println("1 " + Integer.toString(toAirport.getID())  + " " +  Integer.toString(aircraftIndex) + " = -" + Double.toString(expensesR)+ " ");
		expenses += expensesR;
		return true;
	}
	/**
	 * Checks whether the aircraft can fly, by checking if the airport is full, 
	 * toAirport is the current airport, fuel is enough for the distance, aircraft is empty.
	 * @param toAirport The airport aircraft will fly to.
	 * @param aircraftIndex The index of aircraft in the aircrafts arraylist.
	 * @return true if can fly, false if cannot.
	 */
	boolean canFly(Airport toAirport, int aircraftIndex) {
		if(toAirport.getAircraftCapacity() == toAirport.getNumberOfAircrafts()) {
		//	System.out.println(1);
			return false;
		}
		else if(Objects.equals(aircrafts.get(aircraftIndex).getCurrentAirport(), toAirport)) {
		//	System.out.println(2);
			return false;
		}
		else if (aircrafts.get(aircraftIndex).getFuelConsumption(aircrafts.get(aircraftIndex).getCurrentAirport().distanceToAirport(toAirport)) > aircrafts.get(aircraftIndex).getFuel()) {
		//	System.out.println(3);
			return false;
		}
		else if (aircrafts.get(aircraftIndex).getOccupiedEconomySeats() == 0) {
		//	System.out.println(4);
			return false;
		}
		return true;
	}
	/**
	 * Loads passenger to the aircraft.
	 * @param passenger Passenger that will be loaded.
	 * @param airport  Airport both the passenger and the aircraft is in.
	 * @param aircraftIndex The index of aircraft that the passenger will be loaded in in the aircrafts arraylist.
	 * @return true if can load.
	 */
	boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		double expensesR = this.aircrafts.get(aircraftIndex).loadPassenger(passenger);
		System.out.println("4 " + Long.toString(passenger.getID()) + " " + Integer.toString(aircraftIndex) + " " + Integer.toString(airport.getID()) + " = -" + Double.toString(expensesR)+ " ");
		expenses += expensesR;
		return true;
	}
	
	/**
	 * Unloads passenger from the aircraft.
	 * @param passenger Passenger that will be unloaded.
	 * @param aircraftIndex The index of aircraft that the passenger will be unloaded from in the aircrafts arraylist.
	 * @return true if can unload passenger, false otherwise.
	 */
	boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		PassengerAircraft currAircraft = this.aircrafts.get(aircraftIndex);
		if(passenger.disembarkCheck(currAircraft.getCurrentAirport())) {
			double revenueR = currAircraft.unloadPassenger(passenger);
			System.out.println("5 " + Long.toString(passenger.getID()) + " " + Integer.toString(aircraftIndex) + " " + Integer.toString(currAircraft.getCurrentAirport().getID()) + " = " + Double.toString(revenueR)+ " ");
			revenue += revenueR;
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * Transfers passenger from an aircraft to another.
	 * @param passenger Passenger that will be transferred.
	 * @param aircraftIndex The index of aircraft that the passenger will be transferred from in the aircrafts arraylist.
	 * @param toAircraftIndex The index of aircraft that the passenger will be transferred to in the aircrafts arraylist.
	 * @return true if can transfer passenger, false otherwise.
	 */
	boolean transferPassenger(Passenger passenger, int aircraftIndex, int toAircraftIndex) {
		PassengerAircraft currAircraft = this.aircrafts.get(aircraftIndex);
		PassengerAircraft toAircraft = this.aircrafts.get(toAircraftIndex);
		if(!passenger.disembarkCheck(currAircraft.getCurrentAirport())) {
			double revenueR = currAircraft.unloadPassenger(passenger);
			double expenseR = currAircraft.transferPassenger(passenger, toAircraft);
			System.out.println("6 " + Long.toString(passenger.getID()) + " " + Integer.toString(aircraftIndex) + " " + Integer.toString(toAircraftIndex) + " "+ Integer.toString(currAircraft.getCurrentAirport().getID()) + " = -" + Double.toString(revenueR)+ " ");
			expenses += expenseR;
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * Flies all aircrafts that can go to a location, by traversing over the passengers of aircrafts
	 * and their future destinations.
	 */
	public void flyAll() {
		HashSet<Integer> hs = new HashSet<Integer>();
		for(int i = 0; i < aircrafts.size(); i++) {
			if(fl) break;
			Airport currAir = aircrafts.get(i).getCurrentAirport();
			int indCurrAir = airportsList.indexOf(currAir);
			if(indCurrAir < airportsList.size() - 1) indCurrAir++;
			else indCurrAir = 0;
			ArrayList<Passenger> passList = aircrafts.get(i).getPassengersList();
			int flag = 0;
			for(int j = 0; j < passList.size(); j++) {
				for(int k = passList.get(j).getCurrentAirportInd(); k < passList.get(j).getDestSize(); k++) {
					if(canFly(passList.get(j).getDestinationsWithInd(k),i)) {
						if(hs.contains(i)) break;
						fly(passList.get(j).getDestinationsWithInd(k), i);
						hs.add(i);
						flag = 1;
						break;
					}
					if(flag == 1) break;
				}
			}
			
		}
	}
	/**
	 * Creates as much rapid passengers aircrafts as possible, all having only economy seats,
	 *  while trying to balance the number of aircrafts in each airport. 
	 * @param operationFee operationFee of the aircrafts, will be rapidOpFee since they are all rapid.
	 */
	public void createAllEconomyAircraft(double operationFee) {
		for(int i = 0; i < maxAircraftCount; i++) {
			RapidPassengerAircraft newAircraft = new RapidPassengerAircraft(airportsList.get((i % airportsList.size())), operationFee);
			System.out.println("0 "+ Integer.toString(airportsList.get((i % airportsList.size())).getID()) + " 2 = 0.0");
			newAircraft.setAllEconomy();
			System.out.println("2 " + Integer.toString(i) + " " + Integer.toString((int)newAircraft.getFloorArea()) + " 0 0 = 0.0");
			/*expenses += newAircraft.fillUp();
			System.out.println("3 " + Integer.toString(i) + " " + Double.toString(newAircraft.getFuelCapacity()));
			*/
			aircrafts.add(newAircraft);
			newAircraft.getCurrentAirport().setNumberOfAircrafts(newAircraft.getCurrentAirport().getNumberOfAircrafts()+1);
		}
	}
	/**
	 * Creates as much wb passengers aircrafts as possible, all having only economy seats,
	 *  while trying to balance the number of aircrafts in each airport. 
	 * @param operationFee operationFee of the aircrafts, will be rapidOpFee since they are all rapid.
	 */
	public void createAllEconomyWidebodyPassengerAircraft(double operationFee) {
		for(int i = 0; i < maxAircraftCount; i++) {
			WidebodyPassengerAircraft newAircraft = new WidebodyPassengerAircraft(airportsList.get((i % airportsList.size())), operationFee);
			System.out.println("0 "+ Integer.toString(airportsList.get((i % airportsList.size())).getID()) + " 1 = 0.0");
			newAircraft.setAllEconomy();
			System.out.println("2 " + Integer.toString(i) + " " + Integer.toString((int)newAircraft.getFloorArea()) + " 0 0 = 0.0");
			/*expenses += newAircraft.fillUp();
			System.out.println("3 " + Integer.toString(i) + " " + Double.toString(newAircraft.getFuelCapacity()));
			*/
			aircrafts.add(newAircraft);
			newAircraft.getCurrentAirport().setNumberOfAircrafts(newAircraft.getCurrentAirport().getNumberOfAircrafts()+1);
		}
	}
	/**
	 * Fills the fuels of all aircrafts and increases the expenses.
	 */
	public void fillAllUp() {
		for(int i = 0; i < this.getAircrafts().size(); i++) {
			if((this.getAircrafts().get(i).getFuelCapacity()-this.getAircrafts().get(i).getFuel()) * this.getAircrafts().get(i).getFuelWeight() <= this.getAircrafts().get(i).getAvailableWeight()) {
				System.out.print("3 " + Integer.toString(i) + " " + Double.toString(this.getAircrafts().get(i).getFuelCapacity()-aircrafts.get(i).getFuel()));
				double expensesR = this.getAircrafts().get(i).fillUp();
				expenses += expensesR;
				System.out.println(" = -" + Double.toString(expensesR) + " ");
			}
		
		}
	}
	/**
	 * Loads all passengers to the aircrafts that are in the same airport
	 */
	public void loadAllPassengers() {
		int aircraftInd = 0;
		int passengerInd = 0;
		int totalTraverse = 0;
		while(passengerInd != passengers.size() && (totalTraverse < maxAircraftCount * passengers.size() * 100)) {
			while(aircrafts.get(aircraftInd).getAvailableWeight() >= passengers.get(passengerInd).getWeight()) {
				if(Objects.equals(aircrafts.get(aircraftInd).getCurrentAirport(), passengers.get(passengerInd).getDestinationsWithInd(passengers.get(passengerInd).getCurrentAirportInd())))  {
					//System.out.println("1");
					if(passengers.get(passengerInd).getIsLoaded() == 0) this.loadPassenger(passengers.get(passengerInd), passengers.get(passengerInd).getCurrentAirport(), aircraftInd);
					passengerInd++;
					if(passengerInd == passengers.size()) break;
				} else if(aircraftInd < maxAircraftCount - 1) aircraftInd++;
				else if (totalTraverse < maxAircraftCount * passengers.size() * 100){
					totalTraverse++;
					aircraftInd = 0;
				} else {
					break;
				}
			}
			if (totalTraverse >= maxAircraftCount * passengers.size() * 100) {
				break;
			} else if(aircraftInd < maxAircraftCount - 1) aircraftInd++;
			 else {
				aircraftInd = 0;
			}
		}
	}
	/**
	 * Unloads all passengers if current airport is a future destination. 
	 * Otherwise, transfers them to other aircrafts. 
	 */
	private boolean fl = false;
	public void unloadAllAvailableTransferRest() {
		for(int i = 0; i < aircrafts.size(); i++) {
			ArrayList<Passenger> passengersInA = aircrafts.get(i).getPassengersList();
			ArrayList<Passenger> passengersInADup = new ArrayList<Passenger>();
			for(int j = 0; j < passengersInA.size(); j++) {
				passengersInADup.add(passengersInA.get(j));
			}
			//System.out.println(passengersInADup.size());
			//System.out.println(passengersInA.size());
			for(int j = 0; j < passengersInADup.size(); j++) {
				if(passengersInADup.get(j).disembarkCheck(aircrafts.get(i).getCurrentAirport())) {
					//System.out.println("yey");
					this.unloadPassenger(passengersInADup.get(j), i);
					fl = true;
					//aircrafts.get(i).unloadPassenger(passengersInADup.get(j));
				} else {
					//System.out.println("nay");
				//	this.transferPassenger(passengersInADup.get(j), i, i);
				}
			}
		}
	}
	/**
	 * Prints out the total revenue.
	 */
	public void writeProfit() {
		System.out.println(this.revenue - this.expenses);
	}
	
	
}
