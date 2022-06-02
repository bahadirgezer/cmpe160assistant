package project.airline_container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import project.airline_container.aircraft_container.PassengerAircraft;
import project.airline_container.aircraft_container.concrete_container.JetPassengerAircraft;
import project.airline_container.aircraft_container.concrete_container.PropPassengerAircraft;
import project.airline_container.aircraft_container.concrete_container.RapidPassengerAircraft;
import project.airline_container.aircraft_container.concrete_container.WidebodyPassengerAircraft;
import project.airport_container.Airport;
import project.airport_container.HubAirport;
import project.airport_container.MajorAirport;
import project.airport_container.RegionalAirport;
import project.utils.DataReader;
import project.utils.LogWriter;
import project.utils.Util;

public class Airline {
	
	private int maxAircraftCount;
	private double operationalCost;

	private ArrayList<Airport> airports = new ArrayList<>();
	private ArrayList<PassengerAircraft> aircrafts = new ArrayList<>();
	
	private Map<Long, Integer> destinationFrequenciesUnsorted = new HashMap<>();
	protected Map<Long, Integer> destinationFrequencies;
	protected Map<Long, Integer> airportCrowdedness;
	
	private static Airline airline;
	
	public Airline(int maxAircraftCount, double operationalCost) {
		super();
		this.maxAircraftCount = maxAircraftCount;
		this.operationalCost = operationalCost;
		airline = this;
	}	

	public void init(String[] args) {
		DataReader.readAll(args[0]);
		LogWriter.set(args[1]);
		setAirportCrowdedness();
		destinationFrequencies = Util.sortByValue(destinationFrequenciesUnsorted);
	}
	
	public void run() {
		for (Entry<Long, Integer> entry: airportCrowdedness.entrySet()) {
			
		}
		LogWriter.finish();
	}
	
	public boolean fly(Airport toAirport, int aircraftIndex) {
		double expense = operationalCost * aircrafts.size();
		LogWriter.write("1 " + toAirport.getId() + " " + aircrafts.get(aircraftIndex) + " = -" + (expense + aircrafts.get(aircraftIndex).fly(toAirport)));
		return true;
	}
	
	public void createAircraft(int type, Airport airport) {
		if (maxAircraftCount < aircrafts.size()) {
			switch (type) {
				case 0:
					aircrafts.add(new JetPassengerAircraft());
					LogWriter.write("0 " + airport.getId() + " 3 = 0.0" );
					break;
				case 1:
					aircrafts.add(new PropPassengerAircraft());
					LogWriter.write("0 " + airport.getId() + " 0 = 0.0" );
					break;
				case 2:
					aircrafts.add(new RapidPassengerAircraft());
					LogWriter.write("0 " + airport.getId() + " 2 = 0.0" );
					break;
				case 3:
					aircrafts.add(new WidebodyPassengerAircraft());
					LogWriter.write("0 " + airport.getId() + " 1 = 0.0" );
					break;
				default:
					System.out.println("Wrong aircraft type");
			}
		}
	}
	
	public void setOperationalCost(String operationalCost) {
		this.operationalCost = Double.parseDouble(operationalCost);
	}
	
	public void setMaxAircraftCount(int maxAircraftCount) {
		this.maxAircraftCount = maxAircraftCount;
	}
	
	public void addAirport(String type, int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
		switch (type) {
			case "hub":
				airports.add(new HubAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity));
				break;
			case "major":
				airports.add(new MajorAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity));
				break;
			case "regional":
				airports.add(new RegionalAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity));
				break;
		}
		System.out.println("Wrong input : invalid airport type = " + type);
	}
	
	public ArrayList<Airport> getAirports() {
		return airports;
	}
	
	public void addDestination(Long id) {
		if (destinationFrequenciesUnsorted.get(id) == null) {
			destinationFrequenciesUnsorted.put(id, 1);
		}
		else {
			destinationFrequenciesUnsorted.put(id, destinationFrequenciesUnsorted.get(id) + 1);
		}
	}
	
	public void setAirportCrowdedness() {
		Map<Long, Integer> airportCrowdedness = new HashMap<>();
		for (Airport airport : airports) {
			airportCrowdedness.put(airport.getId(), airport.getPassengers().size());
		}
		this.airportCrowdedness = Util.sortByValue(airportCrowdedness);
	}
	
	public static Airline getInstance() {
		return airline;
	}
	
}
