package project.airline;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

import project.airline.aircraft.Aircraft;
import project.airline.aircraft.concrete.JetPassengerAircraft;
import project.airline.aircraft.concrete.PropPassengerAircraft;
import project.airline.aircraft.concrete.RapidPassengerAircraft;
import project.airline.aircraft.concrete.WidebodyPassengerAircraft;
import project.airport.Airport;
import project.airport.HubAirport;
import project.airport.RegionalAirport;
import project.airport.MajorAirport;
import project.passenger.BusinessPassenger;
import project.passenger.EconomyPassenger;
import project.passenger.FirstClassPassenger;
import project.passenger.LuxuryPassenger;
import project.passenger.Passenger;

public class Airline {
	private int maxAircraftCount;
	private double operationalCost;
	public HashMap<Passenger, Airport> passengersAirport = new HashMap<Passenger, Airport>();
	public HashMap<Passenger, Aircraft> passengersAircraft = new HashMap<Passenger, Aircraft>();
	public ArrayList<Aircraft> Aircrafts = new ArrayList<Aircraft>();
	public ArrayList<Airport> Airports = new ArrayList<Airport>();
	private double totalProfit;
	public double[] aircraftOFArray = new double[4];
	private FileWriter myWriter;

	public Airline(int maxAircraftCount, double operationalCost, double[] aircraftOFArray, String outputFileName) {
		this.operationalCost = operationalCost;
		this.maxAircraftCount = maxAircraftCount;
		this.aircraftOFArray = aircraftOFArray;
		try {
			myWriter = new FileWriter(outputFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	boolean fly(Airport toAirport, int aircraftIndex) {
		Aircraft theAircraft = Aircrafts.get(aircraftIndex);
		double x = theAircraft.fly(toAirport);
		totalProfit -= x;
		totalProfit -= operationalCost * Aircrafts.size();
		write("1 "+toAirport.getID()+ " "+ aircraftIndex+ "\n");
		return !(x == 0);
	}

	boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		if (passengersAirport.get(passenger) != airport) {
			return false;
		}
		double x = Aircrafts.get(aircraftIndex).loadPassenger(passenger);
		totalProfit -= x;
		if (x == Aircrafts.get(aircraftIndex).getOperationFee()) {
			return false;
		}
		write("4 "+ passenger.getID()+ " "+ aircraftIndex+ " "+ airport.getID()+ "\n");
		return true;
	}

	boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		if (passengersAircraft.get(passenger) != Aircrafts.get(aircraftIndex)) {
			return false;
		}
		double x = Aircrafts.get(aircraftIndex).unloadPassenger(passenger);
		write("5 "+ passenger.getID()+ " "+ aircraftIndex+ " "+ passengersAirport.get(passenger)+ "\n");
		totalProfit += x;
		if (x == -Aircrafts.get(aircraftIndex).getOperationFee()) {
			return false;
		}
		return true;
	}

	public boolean buyAircraft(Airport startingAirport, int typeOfAircraft) {
		if (maxAircraftCount <= Aircrafts.size()) {
			return false;
		}
		Aircraft aircraft;
		switch (typeOfAircraft) {
		case 0:
			aircraft = new PropPassengerAircraft(startingAirport);
			aircraft.setOperationFee(aircraftOFArray[0]);
			break;
		case 1:
			aircraft = new WidebodyPassengerAircraft(startingAirport);
			aircraft.setOperationFee(aircraftOFArray[1]);
			break;
		case 2:
			aircraft = new RapidPassengerAircraft(startingAirport);
			aircraft.setOperationFee(aircraftOFArray[2]);
			break;
		case 3:
			aircraft = new JetPassengerAircraft(startingAirport);
			aircraft.setOperationFee(aircraftOFArray[3]);
			break;
		default:
			return false;
		}
		write(0 + " " + startingAirport.getID() + " " + typeOfAircraft + "\n");
		aircraft.setAirline(this);
		Aircrafts.add(aircraft);
		return true;
	}

	public void newAirport(String type, int ID, double x, double y, double fuelCost, double operationFee,
			int aircraftCapacity) {
		Airport airport;
		switch (type) {
		case "hub":
			airport = new HubAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
			break;
		case "regional":
			airport = new RegionalAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
			break;
		case "major":
			airport = new MajorAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
			break;
		default:
			return;
		}
		Airports.add(airport);
	}

	public void newPassenger(String type, int ID, double weight, int baggageCount, ArrayList<Integer> destinations) {
		ArrayList<Airport> destinations1 = new ArrayList<Airport>();
		for (int i = 0; i < destinations.size(); i++) {
			destinations1.add(findAirport(destinations.get(i)));
		}
		Passenger passenger;
		switch (type) {
		case "economy":
			passenger = new EconomyPassenger(ID, weight, baggageCount, destinations1);
			break;
		case "business":
			passenger = new BusinessPassenger(ID, weight, baggageCount, destinations1);
			break;
		case "first":
			passenger = new FirstClassPassenger(ID, weight, baggageCount, destinations1);
			break;
		case "luxury":
			passenger = new LuxuryPassenger(ID, weight, baggageCount, destinations1);
			break;
		default:
			passenger = new EconomyPassenger(ID, weight, baggageCount, destinations1);
		}
		passengersAirport.put(passenger, destinations1.get(0));
	}

	public Airport findAirport(int id) {
		for (int i = 0; i < Airports.size(); i++) {
			if (Airports.get(i).getID() == id) {
				return Airports.get(i);
			}
		}
		return Airports.get(0);
	}

	public void write(String string) {
		try {
			myWriter.write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fillUp(Aircraft aircraft) {
		totalProfit -= aircraft.fillUp();
	}
	public void tranferOnePerson() {
		for (Passenger i : passengersAirport.keySet()) {
			ArrayList<Airport> idk = i.getDestinations();
			for (int j =1; j<idk.size();j++) {
				if (idk.get(0).getDistance(idk.get(j)) < 14000) {
					buyAircraft(idk.get(0), 1);
					fillUp(Aircrafts.get(0));
					loadPassenger(i, idk.get(0), 0);
					fly(idk.get(j), 0);
					unloadPassenger(i, 0);
					Double TP = totalProfit;
					write(TP.toString());
				}
			}
		}
	}
	

//	public void removePassengerFromAirport(Passenger thePassenger) {
//		passengersAirport.remove(thePassenger);
//	}
//	
//	public void removePassengerFromAircraft(Passenger thePassenger) {
//		passengersAircraft.remove(thePassenger);
//	}
//	
//	public void addPassengerToAirport(Passenger thePassenger, Airport theAirport) {
//		passengersAirport.put(thePassenger, theAirport);
//	}
//	
//	public void addPassengerToAircraft(Passenger thePassenger, Aircraft theAircraft) {
//		passengersAircraft.put(thePassenger, theAircraft);
//	}

}
