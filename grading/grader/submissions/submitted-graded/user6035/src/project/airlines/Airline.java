package project.airlines;

import java.util.ArrayList;
import java.util.HashMap;

import project.PassengerContainer.BusinessPassenger;
import project.PassengerContainer.EconomyPassenger;
import project.PassengerContainer.FirstClassPassenger;
import project.PassengerContainer.LuxuryPassenger;
import project.PassengerContainer.Passenger;
import project.airlines.aircraftcontainer.Aircraft;
import project.airlines.aircraftcontainer.PassengerAircraft;
import project.airlines.aircraftcontainer.concretecontainer.RapidPassengerAircraft;
import project.airportContainer.Airport;
import project.airportContainer.HubAirport;
import project.airportContainer.MajorAirport;
import project.airportContainer.RegionalAirport;

public class Airline {
	int maxAircraftCount;
	double operationalCost;
	double expenses;
	double revenue;
	ArrayList<PassengerAircraft> aircrafts = new ArrayList<PassengerAircraft>();
	public HashMap<Integer, Airport> airports = new HashMap<Integer, Airport>();
	public ArrayList<Airport> airportsList = new ArrayList<Airport>();
	public ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	public Airline(int maxAircraftCount, double operationalCost) {
		this.maxAircraftCount = maxAircraftCount;
		this.operationalCost = operationalCost;
		this.expenses = 0;
		this.revenue = 0;
	}
	
	public void addAirport(String type, int airportID, int aircraftCapacity, int X, int Y, double fuelCost, double operationFee) {
		if(type == "hub") {
			HubAirport newPort = new HubAirport(airportID,  X, Y, fuelCost, operationFee, aircraftCapacity);
			airports.put(airportID, newPort);
			airportsList.add(newPort);
		} else if (type == "major") {
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
	public void addPassenger(String type, int ID, double weight, int baggageCount, int currentAirport, ArrayList<Integer> destination) {
		if(type == "economy") {
			EconomyPassenger newPassenger = new EconomyPassenger(ID, weight, baggageCount, currentAirport);
			
			newPassenger.setDestinationIDs(destination);
			ArrayList<Airport> passDests = new ArrayList<Airport>();
			for(int i = 0; i < destination.size(); i++) {
				passDests.add(airports.get(destination.get(i)));
			}
			newPassenger.setDestinations(passDests);
			passengers.add(newPassenger);
		} else if(type == "business") {
			BusinessPassenger newPassenger = new BusinessPassenger(ID, weight, baggageCount, currentAirport);
			newPassenger.setDestinationIDs(destination);
			ArrayList<Airport> passDests = new ArrayList<Airport>();
			for(int i = 0; i < destination.size(); i++) {
				passDests.add(airports.get(destination.get(i)));
			}
			newPassenger.setDestinations(passDests);
			passengers.add(newPassenger);
		} else if (type == "first") {
			FirstClassPassenger newPassenger = new FirstClassPassenger(ID, weight, baggageCount, currentAirport);
			newPassenger.setDestinationIDs(destination);
			ArrayList<Airport> passDests = new ArrayList<Airport>();
			for(int i = 0; i < destination.size(); i++) {
				passDests.add(airports.get(destination.get(i)));
			}
			newPassenger.setDestinations(passDests);
			passengers.add(newPassenger);
		} else {
			LuxuryPassenger newPassenger = new LuxuryPassenger(ID, weight, baggageCount, currentAirport);
			newPassenger.setDestinationIDs(destination);
			ArrayList<Airport> passDests = new ArrayList<Airport>();
			for(int i = 0; i < destination.size(); i++) {
				passDests.add(airports.get(destination.get(i)));
			}
			newPassenger.setDestinations(passDests);
			passengers.add(newPassenger);
		}
		
		return;
	}
	
	boolean fly(Airport toAirport, int aircraftIndex) {
		double expensesR = operationalCost * aircrafts.size();
		PassengerAircraft currAircraft = this.aircrafts.get(aircraftIndex);
		expensesR += currAircraft.fly(toAirport);
		System.out.println("1 " + Integer.toString(toAirport.getID())  + " " +  Integer.toString(aircraftIndex) + " = -" + Double.toString(expensesR));
		expenses += expensesR;
		return true;
	}
	
	boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		double expensesR = this.aircrafts.get(aircraftIndex).loadPassenger(passenger);
		System.out.println("4 " + Integer.toString(passenger.getID()) + " " + Integer.toString(aircraftIndex) + " " + Integer.toString(airport.getID()) + " = -" + Double.toString(expensesR));
		expenses += expensesR;
		return true;
	}
	
	boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		PassengerAircraft currAircraft = this.aircrafts.get(aircraftIndex);
		if(passenger.disembarkCheck(currAircraft.getCurrentAirport())) {
			double revenueR = currAircraft.unloadPassenger(passenger);
			System.out.println("5 " + Integer.toString(passenger.getID()) + " " + Integer.toString(aircraftIndex) + " " + Integer.toString(currAircraft.getCurrentAirport().getID()) + " = -" + Double.toString(revenueR));
			revenue += revenueR;
			return true;
		}
		else {
			return false;
		}
	}
	
	public void flyAll() {
		for(int i = 0; i < aircrafts.size(); i++) {
			Airport currAir = aircrafts.get(i).getCurrentAirport();
			int indCurrAir = airportsList.indexOf(currAir);
			if(indCurrAir < airportsList.size() - 1) indCurrAir++;
			else indCurrAir = 0;
			while(aircrafts.get(i).getFuelConsumption(aircrafts.get(i).getCurrentAirport().distanceToAirport(airportsList.get(indCurrAir))) > aircrafts.get(i).getFuel()) {
				if(indCurrAir < airportsList.size() - 1) indCurrAir++;
				else indCurrAir = 0;
			}
			fly(airportsList.get(indCurrAir), i);
		}
	}
	
	public void createAllEconomyAircraft(double operationFee) {
		for(int i = 0; i < maxAircraftCount; i++) {
			RapidPassengerAircraft newAircraft = new RapidPassengerAircraft(airportsList.get((i % airportsList.size())), operationFee);
			System.out.println("0 "+ Integer.toString(airportsList.get((i % airportsList.size())).getID()) + " 3");
			newAircraft.setAllEconomy();
			System.out.println("2 " + Integer.toString(i) + " " + Integer.toString((int)newAircraft.getFloorArea()) + " 0 0");
			expenses += newAircraft.fillUp();
			System.out.println("3 " + Integer.toString(i) + " " + Double.toString(newAircraft.getFuelCapacity()));
			aircrafts.add(newAircraft);
		}
	}
	
	public void loadAllPassengers() {
		int aircraftInd = 0;
		int passengerInd = 0;
		while(passengerInd != passengers.size()) {
			while(aircrafts.get(aircraftInd).getAvailableWeight() >= passengers.get(passengerInd).getWeight()) {
				if(aircrafts.get(aircraftInd).getCurrentAirport() != aircrafts.get(passengerInd).getCurrentAirport()) {
					aircrafts.get(aircraftInd).loadPassenger(passengers.get(passengerInd));
					passengerInd++;
					if(passengerInd == passengers.size()) break;
				} else if(aircraftInd < maxAircraftCount) aircraftInd++;
				else {
					aircraftInd = 0;
				}
			}
			if(aircraftInd < maxAircraftCount - 1) aircraftInd++;
			else {
				aircraftInd = 0;
			}
		}
	}
	public void unloadAllAvailableTransferRest() {
		for(int i = 0; i < aircrafts.size(); i++) {
			ArrayList<Passenger> passengersInA = aircrafts.get(i).getPassengersList();
			ArrayList<Passenger> passengersInADup = new ArrayList<Passenger>();
			for(int j = 0; j < passengersInA.size(); j++) {
				passengersInADup.add(passengersInA.get(j));
			}
			
			for(int j = 0; j < passengersInADup.size(); j++) {
				if(passengersInADup.get(j).disembarkCheck(aircrafts.get(i).getCurrentAirport())) {
					aircrafts.get(i).unloadPassenger(passengersInA.get(j));
				} else {
					aircrafts.get(i).transferPassenger(passengersInA.get(j), aircrafts.get(i));
				}
			}
		}
	}
	public void writeRevenue() {
		System.out.println(this.revenue);
	}
	
	
}
