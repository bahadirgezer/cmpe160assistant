package project.airlinecontainer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import project.airlinecontainer.aircraftcontainer.PassengerAircraft;
import project.airlinecontainer.aircraftcontainer.concretecontainer.JetPassengerAircraft;
import project.airlinecontainer.aircraftcontainer.concretecontainer.PropPassengerAircraft;
import project.airlinecontainer.aircraftcontainer.concretecontainer.RapidPassengerAircraft;
import project.airlinecontainer.aircraftcontainer.concretecontainer.WidebodyPassengerAircraft;
import project.airportcontainer.Airport;
import project.airportcontainer.HubAirport;
import project.airportcontainer.MajorAirport;
import project.airportcontainer.RegionalAirport;
import project.passengercontainer.BusinessPassenger;
import project.passengercontainer.EconomyPassenger;
import project.passengercontainer.FirstClassPassenger;
import project.passengercontainer.LuxuryPassenger;
import project.passengercontainer.Passenger;

public class Airline {
	/**
	 * creating an airline object with parameters below
	 * @param maxAircraftCount
	 * @param operationalCost
	 */
	public Airline(int maxAircraftCount, double operationalCost) {
		this.maxAircraftCount = maxAircraftCount;
		this.operationalCost = operationalCost;
	}
	protected int maxAircraftCount;
	protected int totalAircraftNo;
	protected double operationalCost;
	protected double totalRevenue = 0;
	protected double lastRevenue = 0;
	protected ArrayList<Airport> airports = new ArrayList<Airport>();
	protected ArrayList<PassengerAircraft> aircrafts = new ArrayList<PassengerAircraft>();
	protected ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	protected Map<Airport, ArrayList<Airport>> canGo = new HashMap<Airport, ArrayList<Airport>>();
	
	/**
	 * flies our aircraft to the airport and subtract expansions from the total revenue
	 * flying is checked whether it can happen or not
	 * @param toAirport
	 * @param aircraftIndex
	 * @return
	 */
	public boolean fly(Airport toAirport, int aircraftIndex) {
		if (this.aircrafts.get(aircraftIndex).canFly(toAirport)) {
			this.totalRevenue -= this.operationalCost*this.totalAircraftNo;
			
			this.totalRevenue -= this.aircrafts.get(aircraftIndex).fly(toAirport);
			return true;
		}
		else return false;
	}
	
	/**
	 * getter method for our airports
	 * @return
	 */
	public ArrayList<Airport> getAirports() {
		return airports;
	}

	/**
	 * setter method for our airports
	 * @param airports
	 */
	public void setAirports(ArrayList<Airport> airports) {
		this.airports = airports;
	}

	/**
	 * getter method for our aircrafts
	 * @return
	 */
	public ArrayList<PassengerAircraft> getAircrafts() {
		return aircrafts;
	}

	/**
	 * setter method for our aircrafts
	 * @param aircrafts
	 */
	public void setAircrafts(ArrayList<PassengerAircraft> aircrafts) {
		this.aircrafts = aircrafts;
	}

	/**
	 * getter method for passengers
	 * @return
	 */
	public ArrayList<Passenger> getPassengers() {
		return passengers;
	}

	/**
	 * setter method for passengers
	 * @param passengers
	 */
	public void setPassengers(ArrayList<Passenger> passengers) {
		this.passengers = passengers;
	}

	/**
	 * loads our passenger to an aircraft in an airport if proper and changes the total revenue
	 * @param passenger
	 * @param airport
	 * @param aircraftIndex
	 */
	public boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		if (passenger.getAirport().equals(airport) && this.aircrafts.get(aircraftIndex).canLoad(passenger)) {
			this.totalRevenue -= this.aircrafts.get(aircraftIndex).loadPassenger(passenger);
			return true;
		}
		else return false;
	}
	/**
	 * creates a JetAircrafts and adds it
	 * @param currentAirport
	 * @param operaitonFee
	 */
	public void addJetAircraft(Airport currentAirport, double operaitonFee) {
		if (totalAircraftNo<maxAircraftCount) {
			PassengerAircraft aircraft = new JetPassengerAircraft(currentAirport, operaitonFee);
			this.aircrafts.add(aircraft);
			this.totalAircraftNo+=1;
			currentAirport.setTotalNoofAircraft(currentAirport.getTotalNoofAircraft()+1);

		}
	}
	/**
	 * creates a PropAircrafts and adds it
	 * @param currentAirport
	 * @param operaitonFee
	 */
	public void addPropAircraft(Airport currentAirport, double operaitonFee) {
		if (totalAircraftNo<maxAircraftCount) {
			PassengerAircraft aircraft = new PropPassengerAircraft(currentAirport, operaitonFee);
			this.aircrafts.add(aircraft);
			this.totalAircraftNo+=1;
			currentAirport.setTotalNoofAircraft(currentAirport.getTotalNoofAircraft()+1);

		}
	}
	/**
	 * creates a WidebodyAircrafts and adds it
	 * @param currentAirport
	 * @param operaitonFee
	 */
	public void addWidebodyAircraft(Airport currentAirport, double operaitonFee) {
		if (totalAircraftNo<maxAircraftCount) {
			PassengerAircraft aircraft = new WidebodyPassengerAircraft(currentAirport, operaitonFee);
			this.aircrafts.add(aircraft);
			this.totalAircraftNo+=1;
			currentAirport.setTotalNoofAircraft(currentAirport.getTotalNoofAircraft()+1);
		}
	}
	/**
	 * creates a RapidAircrafts and adds it
	 * @param currentAirport
	 * @param operaitonFee
	 */
	public void addRapidAircraft(Airport currentAirport, double operaitonFee) {
		if (totalAircraftNo<maxAircraftCount) {
			PassengerAircraft aircraft = new RapidPassengerAircraft(currentAirport, operaitonFee);
			this.aircrafts.add(aircraft);
			this.totalAircraftNo+=1;
			currentAirport.setTotalNoofAircraft(currentAirport.getTotalNoofAircraft()+1);

		}
	}
	
	/**
	 * creates a HubAirport and adds it 
	 * @param ID
	 * @param x
	 * @param y
	 * @param fuelCost
	 * @param operationFee
	 * @param aircraftCapacity
	 */
	public void addHubAirport(long ID, double x, double y, double fuelCost, int operationFee, int aircraftCapacity) {
		Airport airport = new HubAirport(ID, x, y);
		airport.setAircraftCapacity(aircraftCapacity);
		airport.setFuelCost(fuelCost);
		airport.setOperationFee(operationFee);
		this.airports.add(airport);
	}
	/**
	 * creates a MajorAirport and adds it
	 * @param ID
	 * @param x
	 * @param y
	 * @param fuelCost
	 * @param operationFee
	 * @param aircraftCapacity
	 */
	public void addMajorAirport(long ID, double x, double y, double fuelCost, int operationFee, int aircraftCapacity) {
		Airport airport = new MajorAirport(ID, x, y);
		airport.setAircraftCapacity(aircraftCapacity);
		airport.setFuelCost(fuelCost);
		airport.setOperationFee(operationFee);
		this.airports.add(airport);
	}
	/**
	 * creates a RegionalAirport and adds it
	 * @param ID
	 * @param x
	 * @param y
	 * @param fuelCost
	 * @param operationFee
	 * @param aircraftCapacity
	 */
	public void addRegionalAirport(long ID, double x, double y, double fuelCost, int operationFee, int aircraftCapacity) {
		Airport airport = new RegionalAirport(ID, x, y);
		airport.setAircraftCapacity(aircraftCapacity);
		airport.setFuelCost(fuelCost);
		airport.setOperationFee(operationFee);
		this.airports.add(airport);
	}
	/**
	 * creates an EconomyPassenger and adds it
	 * @param ID
	 * @param weight
	 * @param baggageCount
	 * @param destinations
	 */
	public void createEconomyPassenger(long ID, double weight, int baggageCount, ArrayList<String> destinations) {
		ArrayList<Airport> passengerDestinations = new ArrayList<Airport>();
		for (String i: destinations) {
			long airportID = Long.parseLong(i);
			for (Airport x : airports) {
				if (x.getID() == airportID)	passengerDestinations.add(x);
			}
		}
		Passenger passenger = new EconomyPassenger(ID, weight, baggageCount, passengerDestinations);
		passengers.add(passenger);
	}
	/**
	 * creates a BusinessPassenger and adds it
	 * @param ID
	 * @param weight
	 * @param baggageCount
	 * @param destinations
	 */
	public void createBusinessPassenger(long ID, double weight, int baggageCount, ArrayList<String> destinations) {
		ArrayList<Airport> passengerDestinations = new ArrayList<Airport>();
		for (String i: destinations) {
			long airportID = Long.parseLong(i);
			for (Airport x : airports) {
				if (x.getID() == airportID)	passengerDestinations.add(x);
			}
		}
		Passenger passenger = new BusinessPassenger(ID, weight, baggageCount, passengerDestinations);
		passengers.add(passenger);
	}
	/**
	 * creates a FirstClassPassenger and adds it
	 * @param ID
	 * @param weight
	 * @param baggageCount
	 * @param destinations
	 */
	public void createFirstClassPassenger(long ID, double weight, int baggageCount, ArrayList<String> destinations) {
		ArrayList<Airport> passengerDestinations = new ArrayList<Airport>();
		for (String i: destinations) {
			long airportID = Long.parseLong(i);
			for (Airport x : airports) {
				if (x.getID() == airportID)	passengerDestinations.add(x);
			}
		}
		Passenger passenger = new FirstClassPassenger(ID, weight, baggageCount, passengerDestinations);
		passengers.add(passenger);
	}
	/**
	 * creates a LuxuryPassenger and adds it
	 * @param ID
	 * @param weight
	 * @param baggageCount
	 * @param destinations
	 */
	public void createLuxuryPassenger(long ID, double weight, int baggageCount, ArrayList<String> destinations) {
		ArrayList<Airport> passengerDestinations = new ArrayList<Airport>();
		for (String i: destinations) {
			long airportID = Long.parseLong(i);
			for (Airport x : airports) {
				if (x.getID() == airportID)	passengerDestinations.add(x);
			}
		}
		Passenger passenger = new LuxuryPassenger(ID, weight, baggageCount, passengerDestinations);
		passengers.add(passenger);
	}
	/**
	 * a method returns us the distance between two airports
	 * @param currentAirport
	 * @param toAirport
	 * @return
	 */
	public double getdistance(Airport currentAirport, Airport toAirport) {
		double c1[] = currentAirport.getCoordinates();
		double c2[] = toAirport.getCoordinates();
		return Math.pow((Math.pow(c1[0]-c2[0], 2) + Math.pow(c1[1]-c2[1],2)), 0.5);
	}
	
	/**
	 * the method to create our map of reachable distances from each airport (range is considered for widebodyaircrafts)
	 * @param airports
	 */
	protected void canGoMap(ArrayList<Airport> airports) {
		for (Airport airport1 : airports) {
			ArrayList<Airport> canGo = new ArrayList<Airport>(); 
			for (Airport airport2 : airports) {
				if (airport1.equals(airport2)) continue;
				else {
					if (this.getdistance(airport1, airport2) < 14000) {
						canGo.add(airport2);
					}
				}
			}
			this.canGo.put(airport1, canGo);
		}
	}
	/**
	 * the method to find an available route to go from an airport1 to an airport 2
	 * returns empty arraylist if no such route exists
	 * @param airport1
	 * @param airport2
	 * @param path
	 * @param loopBlock
	 * @return
	 */
	protected ArrayList<Airport> findPath(Airport airport1, Airport airport2, ArrayList<Airport> path, ArrayList<Airport> loopBlock){
		boolean hamleVarMý = false;
		for (Airport airport: this.canGo.get(airport1)) {
			if (loopBlock.contains(airport)) continue;
			else {
				hamleVarMý = true;
				break;
			}
		}
		if (hamleVarMý) {
			if (this.canGo.get(airport1).contains(airport2)) {
				path.add(airport2);
				return path;
			}
			else {
				for (Airport airport3 : this.canGo.get(airport1)) {
					if (loopBlock.contains(airport3)) {
						continue;
					}
					else {
						ArrayList<Airport> newLoopBlock = loopBlock;
						newLoopBlock.add(airport3);
						ArrayList<Airport> newPath = path;
						newPath.add(airport3);
						return this.findPath(airport3, airport2, newPath, newLoopBlock);
					}
				}
			}
		}
		else {
			ArrayList<Airport> noPath = new ArrayList<Airport>();
			return noPath;
		}
		return path;
		
	}

	/**
	 * getter method for total revenue
	 * @return
	 */
	public double getTotalRevenue() {
		return totalRevenue;
	}
	/**
	 * setter method for total revenue
	 * @param totalRevenue
	 */
	protected void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	
	/**
	 * the method to use in main.java
	 * our method finds exactly 1 suitable passenger to fly him/her in a suitable route for him/her and does the necessary operations 
	 * while flying
	 * @param opFeeWide
	 * @param outputWriter
	 * @throws IOException
	 */
	public void run(double opFeeWide, FileWriter outputWriter) throws IOException {
		this.canGoMap(this.airports);
		boolean continueSearch = false;
		for (Passenger passenger : this.passengers) {
			for (int i = 1; i<passenger.getDestinations().size();i++) {
				ArrayList<Airport> path = new ArrayList<Airport>();
				ArrayList<Airport> loopBlock = new ArrayList<Airport>();
				path.add(passenger.getDestinations().get(0));
				loopBlock.add(passenger.getDestinations().get(0));
				ArrayList<Airport> realDestination = this.findPath(passenger.getDestinations().get(0), passenger.getDestinations().get(i), path, loopBlock);
				if (realDestination.size() != 0) {
					this.addWidebodyAircraft(passenger.getDestinations().get(0), opFeeWide);
					outputWriter.write("0 " + passenger.getDestinations().get(0).getID() + " 1\n");
					this.lastRevenue = this.totalRevenue;
					totalRevenue -= this.aircrafts.get(0).fillUp();
					outputWriter.write("3 0 140000\n");
					this.lastRevenue = this.totalRevenue;
					if (passenger instanceof EconomyPassenger) {
						this.aircrafts.get(0).setSeats(1, 0, 0);
						outputWriter.write("2 0 1 0 0\n");
						
						this.lastRevenue = this.totalRevenue;
					}
					else if (passenger instanceof BusinessPassenger) {
						this.aircrafts.get(0).setSeats(0, 1, 0);
						outputWriter.write("2 0 0 1 0\n");
						
						this.lastRevenue = this.totalRevenue;
					}
					else {
						this.aircrafts.get(0).setSeats(0, 0, 1);
						outputWriter.write("2 0 0 0 1\n");
						
						this.lastRevenue = this.totalRevenue;
					}
					this.totalRevenue -= this.aircrafts.get(0).loadPassenger(passenger);
					outputWriter.write("4 " + passenger.getID() + " 0 " + realDestination.get(0).getID() + "\n");
					
					this.lastRevenue = this.totalRevenue;
					for (int j = 1; j < realDestination.size() - 1;j++) {
						if (this.aircrafts.get(0).canFly(realDestination.get(j))) {
							this.fly(realDestination.get(j), 0);
							outputWriter.write("1 " +realDestination.get(j).getID() + " 0\n" );
							
							this.lastRevenue = this.totalRevenue;
						}
						else {
							double neededFuel = this.aircrafts.get(0).getFuelCapacity() - this.aircrafts.get(0).getFuel();
							this.totalRevenue -= this.aircrafts.get(0).fillUp();
							outputWriter.write("3 0 " + neededFuel +"\n");
							
							this.lastRevenue = this.totalRevenue;
							this.fly(realDestination.get(j), 0);
							outputWriter.write("1 " + realDestination.get(j).getID() + " 0\n" );
							
							this.lastRevenue = this.totalRevenue;
						}
						
					}
					int lastTravel = realDestination.size()-1;
					if (this.aircrafts.get(0).canFly(realDestination.get(lastTravel))) {
						this.fly(realDestination.get(lastTravel), 0);
						outputWriter.write("1 " + realDestination.get(lastTravel).getID() + " 0\n");
						
						this.lastRevenue = this.totalRevenue;
					}
					else {
						double neededFuel = this.aircrafts.get(0).getFuelCapacity() - this.aircrafts.get(0).getFuel();
						this.totalRevenue -= this.aircrafts.get(0).fillUp();
						outputWriter.write("3 0 " + neededFuel +"\n");
						
						this.lastRevenue = this.totalRevenue;
						this.fly(realDestination.get(lastTravel), 0);
						outputWriter.write("1 " + realDestination.get(lastTravel).getID() + " 0\n");
						
						this.lastRevenue = this.totalRevenue;
					}
					this.totalRevenue += this.aircrafts.get(0).unloadPassenger(passenger);
					outputWriter.write("5 " + passenger.getID() +" 0"+ realDestination.get(lastTravel).getID()+ "\n");
					
					outputWriter.write("" + this.getTotalRevenue());
					continueSearch = true;
					break;
					
				}
			}
			if (continueSearch) break;
		}
	}
}
