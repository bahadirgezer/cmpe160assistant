package project.airlines;

import java.util.ArrayList;
import java.util.Hashtable;

import project.airlines.aircrafts.*;
import project.airlines.aircrafts.concretes.*;
import project.airports.Airport;
import project.airports.*;
import project.passengers.*;

/**
 * Lounge Airline Implementation
 * @author Abdullah Umut Hamzaogullari
 */

public class Airline {
	/**
     * @param expense variable holds all the costs combined.
     * @param revenue variable holds all the gains.
     * @param airporte variable is a hashtable that holds airport ids in keys and airports in values.
     * @param passengers is a hashtable that has keys as integers for passenger types and values as arraylist of passengers.
     * @param log holds the string that will later become the log file.
     */		
	int maxAircraftCount;
	double operationalCost;
	int maxAircrafts;
	ArrayList<PassengerAircraft> aircrafts = new ArrayList<PassengerAircraft>();
	Hashtable<Integer,ArrayList<Passenger>> passengers = new Hashtable<Integer,ArrayList<Passenger>>();
	Hashtable<Integer,Airport> airporte = new Hashtable<Integer,Airport>();
	double[] aircraftFees;
	double expense = 0;
	double revenue = 0;
	String log = "";
	
	/**
	 * Information from the inputs are assigned within the constructor.
	 * Data list are populated.
     */
	public Airline(int maxAircraftCount, double operationalCost, Object[][] AirportInfos, Object[][] PassengerInfos, double[] aircraftFees) {
		this.maxAircraftCount = maxAircraftCount;
		this.operationalCost = operationalCost;
		this.aircraftFees = aircraftFees;
		
		for (Object[] i : AirportInfos) {
			switch ((String) i[0]) {
				case "hub":
					HubAirport a = new HubAirport((int) i[1], (double) i[2], (double) i[3], (double) i[4], (double) i[5], (int) i[6]);
					airporte.put((int) i[1], a);
				case "major":
					MajorAirport b = new MajorAirport((int) i[1], (double) i[2], (double) i[3], (double) i[4], (double) i[5], (int) i[6]);
					airporte.put((int) i[1], b);
				case "regional":
					RegionalAirport c = new RegionalAirport((int) i[1], (double) i[2], (double) i[3], (double) i[4], (double) i[5], (int) i[6]);
					airporte.put((int) i[1], c);

			}
		}
		for (Object[] i : PassengerInfos) {
			switch ((String) i[0]) {
				case "economy":
					EconomyPassenger psa = new EconomyPassenger((long) i[1], (double) i[2], (int) i[3], keysToValues((ArrayList<Integer>) i[4]));
					if (!passengers.containsKey(0)) {
						continue;
					}
					passengers.get(0).add(psa);
				case "business":
					BusinessPassenger psb = new BusinessPassenger((long) i[1], (double) i[2], (int) i[3], keysToValues((ArrayList<Integer>) i[4]));
					if (!passengers.containsKey(1)) {
						continue;
					}
					passengers.get(1).add(psb);
				case "firstclass":
					FirstClassPassenger psc = new FirstClassPassenger((long) i[1], (double) i[2], (int) i[3], keysToValues((ArrayList<Integer>) i[4]));
					if (!passengers.containsKey(2)) {
						continue;
					}
					passengers.get(2).add(psc);
				case "luxury":
					LuxuryPassenger psd = new LuxuryPassenger((long) i[1], (double) i[2], (int) i[3], keysToValues((ArrayList<Integer>) i[4]));
					if (!passengers.containsKey(3)) {
						continue;
					}
					passengers.get(3).add(psd);
			}
		}
	}
	/**
	 * This method gets all known passengers of the specified type and outputs them as an arraylist.
	 * @return
	 */
	
	public Hashtable<Integer, ArrayList<Integer>> getSomePassengerInfo(int passengerType) {
		Hashtable<Integer,ArrayList<Integer>> bru = new Hashtable<Integer,ArrayList<Integer>>();
		for (int i = 0; i<=passengerType;i++) {
			if (passengers.containsKey(i)) {
				bru.put(i,passengers.get(i).get(0).destie());
				return bru;
			}
		}
		return bru;
	}
	
	public int legalDistance(int AirportID, ArrayList<Integer> a) {
		Airport airport = airporte.get(AirportID);
		for (int i = 0 ; i < a.size() ; i++) {
			Airport toAirport = airporte.get(i);
			double d = airport.getDistance(toAirport);
			if (d != 0 && d<7000) {
				return i;
			}
		}
		return -1;
	}
	
	public int addAircraft(int type, int ec, int bu, int fi, int id) {
		switch (type) {
		case 0:
			return addProp(ec,bu,fi,airporte.get(id));
		case 1:
			return addWidebody(ec,bu,fi,airporte.get(id));
		case 2:
			return addRapid(ec,bu,fi,airporte.get(id));
		case 3:
			return addJet(ec,bu,fi,airporte.get(id));
		}
		return -2;
	}
	
	public boolean flight(int keyofpass, int indexofpassenger, int aircraftID, int airportID) {
		Passenger a = passengers.get(keyofpass).get(indexofpassenger);
		Airport b = airporte.get(airportID);
		refuel(aircraftID);
		loadPassenger(a,b,aircraftID);
		fly(b,aircraftID);
		unloadPassenger(a,aircraftID);
		return true;
	}
	
	public void addRevenueToLog() {
		log = getLog() + String.valueOf(revenue-expense);
	}
	
	/**
	 * This method inputs key ArrayList which are airport IDs and outputs value ArrayList, which are airport objects.
	 * @return is an ArrayList of Airport objects.
     */
	private ArrayList<Airport> keysToValues(ArrayList<Integer> a){
		ArrayList<Airport> b = new ArrayList<Airport>();
		for (int i = 0 ; i < a.size() ; i++) {
			b.add(airporte.get(a.get(i)));
		}
		return b;
	}
	/**
	 * fly() operation is flying an aircraft to an airport.
	 * It considers fuel limitations and adds the flight cost to expenses.
	 * Before it tries to fly, it checks whether or not it can.
	 * @return is a boolean that says if it was successful.
     */
	
	boolean fly(Airport toAirport, int aircraftIndex) {
		expense += operationalCost*numberOfAircrafts();
		PassengerAircraft a = aircrafts.get(aircraftIndex);
		if (a.canFly(toAirport)) {
			log = getLog() + "1 " + String.valueOf(toAirport.getID()) + " " + String.valueOf(aircraftIndex) + "\n";
			expense += a.fly(toAirport);
			return true;
		}
		return false;
	}
	
	/**
	 * loadPassenger() puts the passenger inside the aircraft.
	 * It considers the expenses to load a passenger to an aircraft.
	 * Passenger is loaded to the maximum quality available seat that they can legally sit.
	 * Before trying to load, it checks whether it is possible to do so in order to not lose money.
	 * @return is a boolean that says if it was successful.
     */
	boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		PassengerAircraft a = aircrafts.get(aircraftIndex);
		if (a.loadingIsValid(passenger)) {
			expense += a.loadPassenger(passenger);
			log = getLog() + "4 " + String.valueOf(passenger.getID()) + " " + String.valueOf(aircraftIndex) + " " + String.valueOf(airport.getID()) + "\n";
			return true;
		}
		return false;
	}
	
	/**
	 * unloadPassenger() takes the passenger outside of the aircraft.
	 * if it is possible to unload, passenger disembarks and revenue is added.
	 * @return is a boolean that says if it was successful.
     */
	boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		PassengerAircraft a = aircrafts.get(aircraftIndex);
		if (passenger.canDisembark(a.getCurrentAirport())) {
			revenue += a.unloadPassenger(passenger);
			log = getLog() + "5 " + String.valueOf(passenger.getID()) + " " + String.valueOf(aircraftIndex) + " " + String.valueOf(passenger.getPreviousAirport().getID()) + "\n";
			return true;
		}
		return false;
	}
	
	/**
	 * transferPassenger() transfer passengers between different aircrafts.
	 * It considers the expenses to transfer a passenger to an aircraft.
	 * Passenger is loaded to the maximum quality available seat that they can legally sit.
	 * @return is a boolean that says if it was successful.
     */
	boolean transferPassenger(Passenger passenger, int aircraftIndex, int toAircraftIndex) {
		PassengerAircraft a = aircrafts.get(aircraftIndex);
		PassengerAircraft b = aircrafts.get(aircraftIndex);
		if (a.canTransferPassenger(passenger, b)){
			expense += a.transferPassenger(passenger, b);
			log = getLog() + "6 " + String.valueOf(passenger.getID()) + " " + String.valueOf(aircraftIndex) + " " + String.valueOf(toAircraftIndex)+ " " + String.valueOf(passenger.getPreviousAirport().getID()) + "\n";
			return true;
		}
		return false;
	}
	
	/**
	 * refuel() refuels the aircraft.
	 * Before it tries to refuel, it checks whether or not it can.
	 * It considers the fuel cost.
	 * @return is a boolean that says if it was successful.
     */
	boolean refuel(int aircraftIndex, double fuel) {
		PassengerAircraft ucak = aircrafts.get(aircraftIndex);
		if (ucak.canAddFuel(fuel)) {
			ucak.addFuel(fuel);
			expense += fuel;
			log = getLog() + "3 " + String.valueOf(aircraftIndex) + " " + String.valueOf(fuel) + "\n";
			return true;
		}
		return false;
	}
	
	boolean refuel(int aircraftIndex) {
		PassengerAircraft ucak = aircrafts.get(aircraftIndex);
		double fuel = ucak.fillUpFuel();
		if (ucak.canAddFuel(fuel)) {
			ucak.addFuel(fuel);
			expense += fuel;
			log = getLog() + "3 " + String.valueOf(aircraftIndex) + " " + String.valueOf(fuel) + "\n";
			return true;
		}
		return false;
	}
	
	/**
	 * dumpFuel() dumps fuel out of the aircraft.
	 * Before it tries to dump, it checks whether or not it can.
	 * Its mission is to decrease the weight.
	 * @return is a boolean that says if it was successful.
     */
	boolean dumpFuel(int aircraftIndex, double fuel) {
		PassengerAircraft ucak = aircrafts.get(aircraftIndex);
		if (ucak.hasFuel(fuel)) {
			ucak.addFuel(-fuel);
			log = getLog() + "3 " + String.valueOf(aircraftIndex) + " " + String.valueOf(-fuel) + "\n";
			return true;
		}
		return false;
	}
	
	/**
	 * addProp() adds PropPassengerAircrafts to our Airline.
	 * @param firstAirport is where it will appear first.
	 * @param econ,busin,firstc is for setting up the seat configurations.
	 * @return is an int which states how successful this whole operation went.
     */
	protected int addProp(int econ, int busin, int firstc, Airport firstAirport) {
		if (airlineHasSpace()) {
			PassengerAircraft newbie = new PropPassengerAircraft(aircraftFees[0], firstAirport);
			aircrafts.add(newbie);
			log = getLog() + "0 " + String.valueOf(firstAirport.getID()) + " 0" + "\n";
			if (newbie.canSetSeats(econ, busin, firstc)){
				log = getLog() + "2 " + String.valueOf(numberOfAircrafts()-1) + " " + String.valueOf(econ) 
				+ " " + String.valueOf(busin) + " " + String.valueOf(firstc) + "\n";
				newbie.setSeats(econ,busin,firstc);
				return 1;
			}
			return 0;
		}
		return -1;
	}

	/**
	 * addWidebody() adds WidebodyPassengerAircrafts to our Airline.
	 * @param firstAirport is where it will appear first.
	 * @param econ,busin,firstc is for setting up the seat configurations.
	 * @return is an int which states how successful this whole operation went.
     */
	protected int addWidebody(int econ, int busin, int firstc, Airport firstAirport) {
		if (airlineHasSpace()) {
			PassengerAircraft newbie = new WidebodyPassengerAircraft(aircraftFees[1], firstAirport);
			aircrafts.add(newbie);
			log = getLog() + "0 " + String.valueOf(firstAirport.getID()) + " 1" + "\n";
			if (newbie.canSetSeats(econ, busin, firstc)){
				log = getLog() + "2 " + String.valueOf(numberOfAircrafts()-1) + " " + String.valueOf(econ) 
				+ " " + String.valueOf(busin) + " " + String.valueOf(firstc) + "\n";
				newbie.setSeats(econ,busin,firstc);
				return 1;
			}
			return 0;
		}
		return -1;
	}
	/**
	 * addRapid() adds RapidPassengerAircrafts to our Airline.
	 * @param firstAirport is where it will appear first.
	 * @param econ,busin,firstc is for setting up the seat configurations.
	 * @return is an int which states how successful this whole operation went.
     */
	protected int addRapid(int econ, int busin, int firstc, Airport firstAirport) {
		if (airlineHasSpace()) {
			PassengerAircraft newbie = new RapidPassengerAircraft(aircraftFees[2], firstAirport);
			aircrafts.add(newbie);
			log = getLog() + "0 " + String.valueOf(firstAirport.getID()) + " 2" + "\n";
			if (newbie.canSetSeats(econ, busin, firstc)){
				log = getLog() + "2 " + String.valueOf(numberOfAircrafts()-1) + " " + String.valueOf(econ) 
				+ " " + String.valueOf(busin) + " " + String.valueOf(firstc) + "\n";
				newbie.setSeats(econ,busin,firstc);
				return 1;
			}
			return 0;
		}
		return -1;
	}
	
	/**
	 * addJet() adds JetPassengerAircrafts to our Airline.
	 * @param firstAirport is where it will appear first.
	 * @param econ,busin,firstc is for setting up the seat configurations.
	 * @return is an int which states how successful this whole operation went.
     */
	protected int addJet(int econ, int busin, int firstc, Airport firstAirport) {
		if (airlineHasSpace()) {
			PassengerAircraft newbie = new JetPassengerAircraft(aircraftFees[3], firstAirport);
			aircrafts.add(newbie);
			log = getLog() + "0 " + String.valueOf(firstAirport.getID()) + " 3" + "\n";
			if (newbie.canSetSeats(econ, busin, firstc)){
				log = getLog() + "2 " + String.valueOf(numberOfAircrafts()-1) + " " + String.valueOf(econ) 
				+ " " + String.valueOf(busin) + " " + String.valueOf(firstc) + "\n";
				newbie.setSeats(econ,busin,firstc);
				return 1;
			}
			return 0;
		}
		return -1;
	}
	
	
	/**
	 * This method checks whether there is still room for more aircrafts since we have a limit.
	 * @return is boolean of what I've described.
     */
	private boolean airlineHasSpace() {
		return (numberOfAircrafts() <= maxAircrafts);
	}
	
	/**
	 * @return is how many aircrafts we have.
     */
	protected int numberOfAircrafts() {
			return aircrafts.size();
	}
	public String getLog() {
		return log;
	}

}
