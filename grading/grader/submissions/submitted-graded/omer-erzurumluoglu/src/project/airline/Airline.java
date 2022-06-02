package project.airline;

import java.util.ArrayList;
import java.util.HashMap;

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


/**
* Airline is a class with much functionality. We will use this for our airline.
* 
* @author Omer Faruk 
* 
*/
public class Airline {
	/**
	 * The maximum number of aircrafts that the airline has.
	 */
	private int maxAircraftCount;
	/**
	 * The additional cost for fly operations.
	 */
	private double operationalCost;
	/**
	 * The data of which passenger is in which airport.
	 */
	public HashMap<Passenger, Airport> passengersAirport = new HashMap<Passenger, Airport>();
	/**
	 * The data of which passenger is in which aircraft.
	 */
	public HashMap<Passenger, Aircraft> passengersAircraft = new HashMap<Passenger, Aircraft>();
	/**
	 * The list of aircrafts which the airline has.
	 */
	public ArrayList<Aircraft> Aircrafts = new ArrayList<Aircraft>();
	/**
	 * The list of airports which the airline operates through.
	 */
	public ArrayList<Airport> Airports = new ArrayList<Airport>();
	/**
	 * The list of passengers who have a potential to use this airline.
	 */
	public ArrayList<Passenger> Passengers = new ArrayList<Passenger>();
	/**
	 * The total profit of the airline.
	 */
	private double totalProfit;
	/**
	 * The list of operation fees for every aircraft type.
	 */
	public double[] aircraftOFArray = new double[4];
	/**
	 * The logs of the airline.
	 */
	public String logs;

	public Airline(int maxAircraftCount, double operationalCost, double[] aircraftOFArray, String outputFileName) {
		this.operationalCost = operationalCost;
		this.maxAircraftCount = maxAircraftCount;
		this.aircraftOFArray = aircraftOFArray;
		this.logs = "";
	}
	
	/**
	 * <p>This method allow us to fly an aircraft.
	 * </p>
	 * @param the airport which the aircraft will land
	 * @param the index of the aircraft which will fly
	 * @return succession of the operation
	 */

	boolean fly(Airport toAirport, int aircraftIndex) {
		Aircraft theAircraft = Aircrafts.get(aircraftIndex);
		double x = theAircraft.fly(toAirport);
		totalProfit -= x;
		totalProfit -= operationalCost * Aircrafts.size();
		write1("1 " + toAirport.getID() + " " + aircraftIndex + "\n");
		return !(x == 0);
	}
	
	/**
	 * <p>This method allow us to load a passenger to an aircraft.
	 * </p>
	 * @param the passenger who will be loaded
	 * @param the airport which the loading operation will take place
	 * @param the index of the aircraft which the passenger will be loaded
	 * @return succession of the operation
	 */

	boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		if (passengersAirport.get(passenger) != airport) {
			return false;
		}
		double x = Aircrafts.get(aircraftIndex).loadPassenger(passenger);
		totalProfit -= x;
		if (x == Aircrafts.get(aircraftIndex).getOperationFee()) {
			return false;
		}
		write1("4 " + passenger.getID() + " " + aircraftIndex + " " + airport.getID()  + "\n");
		return true;
	}
	
	/**
	 * <p>This method allow us to unload a passenger from an aircraft.
	 * </p>
	 * @param the passenger who will be unloaded
	 * @param the index of the aircraft which the passenger will be unloaded
	 * @return succession of the operation
	 */

	boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		if (passengersAircraft.get(passenger) != Aircrafts.get(aircraftIndex)) {
			return false;
		}
		double x = Aircrafts.get(aircraftIndex).unloadPassenger(passenger);
		this.write1("5 " + passenger.getID() + " " + aircraftIndex + " " + passengersAirport.get(passenger).getID() + "\n");
		totalProfit += x;
		if (x == -Aircrafts.get(aircraftIndex).getOperationFee()) {
			return false;
		}
		return true;
	}
	
	/**
	 * <p>This method allow us to buy a new aircraft.
	 * </p>
	 * @param the airport where aircraft will be put
	 * @param the type of the aircraft which will be bought
	 * @return succession of the operation
	 */

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
		write1(0 + " " + startingAirport.getID() + " " + typeOfAircraft + "\n");
		aircraft.setAirline(this);
		Aircrafts.add(aircraft);
		startingAirport.placeNewAircraft();
		return true;
	}
	
	/**
	 * <p>This method allow us to identify a new airport.
	 * </p>
	 * @param the type of the airport
	 * @param the ID of the airport
	 * @param the x-coordinate of the airport
	 * @param the y-coordinate of the airport
	 * @param the cost of fuel per unit in the airport
	 * @param the operation fee of the airport
	 */

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
	
	/**
	 * <p>This method allow us to identify a new passenger.
	 * </p>
	 * @param the type of the passenger
	 * @param the ID of the passenger
	 * @param the weight of the passenger
	 * @param the number of baggage of the passenger
	 * @param the destination list of the passenger
	 */

	public void newPassenger(String type, long ID, double weight, int baggageCount, ArrayList<Integer> destinations) {
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
		Passengers.add(passenger);
	}
	
	/**
	 * <p>This method allow us to identify a airport from its ID.
	 * </p>
	 * @param the ID of the airport
	 * @return the airport itself
	 */

	private Airport findAirport(int id) {
		for (int i = 0; i < Airports.size(); i++) {
			if (Airports.get(i).getID() == id) {
				return Airports.get(i);
			}
		}
		return Airports.get(0);
	}

	/**
	 * <p>This method allow us to stack up the logs.
	 * </p>
	 * @param the log line
	 */
	
	public void write1(String string) {
		logs = logs.concat(string);
	}
	
	/**
	 * <p>This method allow us to fill up an aircraft.
	 * </p>
	 * @param the aircraft which will be filled up
	 */

	public void fillUp(Aircraft aircraft) {
		totalProfit -= aircraft.fillUp();
	}
	
	/**
	 * <p>This method transfers one passenger to another airport in his list.
	 * </p>
	 */

	public void tranferOnePerson() {
		Passenger passenger = Passengers.get(0);
		Airport airport = Airports.get(0);
		for (Passenger i : passengersAirport.keySet()) {
			ArrayList<Airport> idk = i.getDestinations();
			for (int j = 1; j < idk.size(); j++) {
				if (idk.get(0).getDistance(idk.get(j)) < 14000) {
					passenger = i;
					airport = idk.get(j);
					break;
				}
			}
		}
		ArrayList<Airport> idk = passenger.getDestinations();
		buyAircraft(idk.get(0), 1);
		Aircrafts.get(0).setSeats(1, 1, 1);
		fillUp(Aircrafts.get(0));
		loadPassenger(passenger, idk.get(0), 0);
		fly(airport, 0);
		unloadPassenger(passenger, 0);
		Double TP = totalProfit;
		write1(TP.toString());

	}
}
