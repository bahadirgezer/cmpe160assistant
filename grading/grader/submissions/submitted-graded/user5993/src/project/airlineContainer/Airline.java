package project.airlineContainer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import project.airlineContainer.aircraftContainer.Aircraft;
import project.airlineContainer.aircraftContainer.PassengerAircraft;
import project.airlineContainer.aircraftContainer.concreteContainer.JetPassengerAircraft;
import project.airlineContainer.aircraftContainer.concreteContainer.PropPassengerAircraft;
import project.airlineContainer.aircraftContainer.concreteContainer.RapidPassengerAircraft;
import project.airlineContainer.aircraftContainer.concreteContainer.WidebodyPassengerAircraft;
import project.airportContainer.Airport;
import project.airportContainer.HubAirport;
import project.airportContainer.MajorAirport;
import project.airportContainer.RegionalAirport;
import project.executableContainer.Main;
import project.passengerContainer.BusinessPassenger;
import project.passengerContainer.EconomyPassenger;
import project.passengerContainer.FirstClassPassenger;
import project.passengerContainer.LuxuryPassenger;
import project.passengerContainer.Passenger;



public class Airline {
	static int maxAircraftCount;
	
	
	/** gets  maxAircraftCount
	 * @return maxAircraftCount
	 */
	public int getMaxAircraftCount() {
		return maxAircraftCount;
	}

	/** gets airports
	 * @return airports
	 * 
	 */
	public ArrayList<Airport> getAirports() {
		return airports;
	}


	/** gets airports
	 * @param airports
	 * @return airports
	 * 
	 */
	public void setAirports(ArrayList<Airport> airports) {
		this.airports = airports;
	}


	/** sets maxAircraftCount to maxAircraftCount
	 * @param maxAircraftCount
	 * 
	 */
	public static void setMaxAircraftCount(int maxAircraftCount) {
		Airline.maxAircraftCount = maxAircraftCount;
	}
	static double operationalCost;
	
	/** gets operationalCost
	 * @return operationalCost
	 */
	public static double getOperationalCost() {
		return operationalCost;
	}

	
	/** sets operationalCost to operationalCost
	 * @param operationalCost
	 */
	public static void setOperationalCost(double operationalCost) {
		Airline.operationalCost = operationalCost;
	}
	
	ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
	ArrayList<Airport> airports = new ArrayList<Airport>();
	static double revenue=0;
	static double expenses=0;
	static double lastCalculatedProfit=0;
	double runningCost=operationalCost*aircrafts.size();
	

	/**
	 * @param maxAircraftCount
	 * @param operationalCost
	 * 
	 */
	public Airline(int maxAircraftCount,double operationalCost ){
	Airline.maxAircraftCount=maxAircraftCount;
	Airline.operationalCost=operationalCost;
	}

	
	/** adds  JetPassengerAircraft
	 * @param currentAirport
	 * @param operationFeeofJet
	 * @return true if operation is valid false otherwise 
	 */
	public boolean addJetPassengerAircraft(Airport currentAirport,double operationFeeofJet) {
		if (aircrafts.size()<maxAircraftCount) {
			JetPassengerAircraft aircraft= new JetPassengerAircraft(currentAirport,10000,18000,0.7,0,10000,0,0,0,0,0,0,30,operationFeeofJet);
			aircrafts.add(aircraft);
			runningCost=operationalCost*aircrafts.size();
			return true;
		}
		else {
			return false;
		}
	}
	/** adds PropPassengerAircraft
	 * @param currentAirport
	 * @param operationFeeofProp
	 * @return true if operation is valid false otherwise 
	 */
	public boolean addPropPassengerAircraft(Airport currentAirport, double operationFeeofProp) {
		if (aircrafts.size()<maxAircraftCount) {
			PropPassengerAircraft aircraft= new PropPassengerAircraft(currentAirport,14000,23000,0.7,0,6000,0,0,0,0,0,0,60,operationFeeofProp);
			aircrafts.add(aircraft);
			runningCost=operationalCost*aircrafts.size();
			return true;
		}
		else {
			return false;
		}
	}
	/** adds RapidPassengerAircraft
	 * @param currentAirport
	 * @param operationFeeofRapid
	 * @return true if operation is valid false otherwise 
	 */
	public boolean addRapidPassengerAircraft(Airport currentAirport, double operationFeeofRapid) {
		if (aircrafts.size()<maxAircraftCount) {
			RapidPassengerAircraft aircraft= new RapidPassengerAircraft(currentAirport,80000,185000,0.7,0,120000,0,0,0,0,0,0,120,operationFeeofRapid);
			aircrafts.add(aircraft);
			runningCost=operationalCost*aircrafts.size();
			return true;
		}
		else {
			return false;
		}
	}
	/** adds WidebodyPassengerAircraft
	 * @param currentAirport
	 * @param operationFeeofWidebody
	 * @return true if operation is valid false otherwise 
	 */
	public boolean addWidebodyPassengerAircraft(Airport currentAirport,double operationFeeofWidebody) {
		if (aircrafts.size()<maxAircraftCount) {
			WidebodyPassengerAircraft aircraft= new WidebodyPassengerAircraft(currentAirport,135000,250000,0.7,0,140000,0,0,0,0,0,0,450,operationFeeofWidebody);
			aircrafts.add(aircraft);
			runningCost=operationalCost*aircrafts.size();
			return true;
		}
		else {
			return false;
		}
	}
	 
	/** adds airport
	 * @param type
	 * @param ID
	 * @param x
	 * @param y
	 * @param fuelCost
	 * @param operationFee
	 * @param aircraftCapacity
	 * @return true if operation is valid false otherwise 
	 */
	public boolean addAirport(String type, int ID,double x,double y,double fuelCost,double operationFee, int aircraftCapacity) {
		if (type.equals("hub")) {
			HubAirport airport= new HubAirport( ID, x, y, fuelCost, operationFee,  aircraftCapacity);
			airports.add(airport);
		}
		else if(type.equals("major")) {
			MajorAirport airport= new MajorAirport( ID, x, y, fuelCost, operationFee,  aircraftCapacity);
			airports.add(airport);
		}
		else if (type.equals("regional")) {
			RegionalAirport airport= new RegionalAirport( ID, x, y, fuelCost, operationFee,  aircraftCapacity);
			airports.add(airport);
		}
		else {
			return false;
		}
		return true;
		
	}
	
	/** adds passenger
	 * @param type
	 * @param ID
	 * @param weight
	 * @param baggageCount
	 * @param destinations
	 * @return true if operation is valid false otherwise 
	 */
	public boolean addPassenger(String type,long ID,double weight, int baggageCount,ArrayList<Airport> destinations) {
		if(type.equals("economy")) {
			EconomyPassenger passenger= new EconomyPassenger(ID,weight,baggageCount,destinations);
			passengers.add(passenger);
		}
		else if (type.equals("business") ) {
			BusinessPassenger passenger= new BusinessPassenger(ID,weight,baggageCount,destinations);
			passengers.add(passenger);
		}
		else if (type.equals("first")) {
			FirstClassPassenger passenger= new FirstClassPassenger(ID,weight,baggageCount,destinations);
			passengers.add(passenger);
		}
		else if (type.equals("luxury")) {
			LuxuryPassenger passenger= new LuxuryPassenger(ID,weight,baggageCount,destinations);
			passengers.add(passenger);
		}
		else {
			return false;
		}
		return true;
	
	}
	
	/** gets passsenger
	 * @return passengers
	 */
	public ArrayList<Passenger> getPassengers() {
		return passengers;
	}


	/** sets passengers
	 * @param passengers
	 */
	public void setPassengers(ArrayList<Passenger> passengers) {
		this.passengers = passengers;
	}


	/**gets aircrafts
	 * @return
	 */
	public ArrayList<Aircraft> getAircrafts() {
		return aircrafts;
	}


	/** sets aircrafts to aircrafts
	 * @param aircrafts
	 */
	public void setAircrafts(ArrayList<Aircraft> aircrafts) {
		this.aircrafts = aircrafts;
	}


	/** fly toAirport with aircraft with aircraftIndex
	 * @param toAirport
	 * @param aircraftIndex
	 * @return true if operation is valid false otherwise 
	 */
	public boolean fly(Airport toAirport, int aircraftIndex) {
		expenses+=runningCost;
		PassengerAircraft aircraft=(PassengerAircraft) aircrafts.get(aircraftIndex);
		if (aircraft.canFly(toAirport)) {
			expenses+= aircraft.fly(toAirport);
			return true;
		}
		else {
			return false;
		}
	}

	/** loads passenger to aircraft with aircraftIndex at airport
	 * @param passenger
	 * @param airport
	 * @param aircraftIndex
	 * @return true if operation is valid false otherwise  
	 */
	public boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		PassengerAircraft aircraft=(PassengerAircraft) aircrafts.get(aircraftIndex);
		if ( (aircraft.canLoadPassenger(passenger)) && airport.equals(passenger.lastAirport)) {
			double loadingExpense =aircraft.loadPassenger(passenger);
			expenses+=loadingExpense;
			return true;
		}
		else {
			double loadingExpense =aircraft.loadPassenger(passenger);
			expenses+=loadingExpense;
			return false;
		}
		
		
	}
	/** unloads passenger from aircraft with aircraftIndex
	 * @param passenger
	 * @param aircraftIndex
	 * @return true if operation is valid false otherwise 
	 */
	public boolean unloadPassenger(Passenger passenger, int aircraftIndex){
		PassengerAircraft aircraft=(PassengerAircraft) aircrafts.get(aircraftIndex);
		Airport airport = aircraft.getCurrentAirport();
		if( passenger.canDisembark(airport)) {
			double ticketRevenue =aircraft.unloadPassenger(passenger);
			revenue+=ticketRevenue;
			return true;
		}
		else {
			double unloadingExpense=aircraft.unloadPassenger(passenger);
			expenses+=unloadingExpense;
			
			return false;
		}
		
	}
	
	
	
	/** adds fuel
	 * @param aircraftIndex
	 * @param fuel
	 * @return fuelExpense
	 */
	double refuel(int aircraftIndex, double fuel) {
		 double fuelExpense=aircrafts.get(aircraftIndex).addFuel(fuel);
		 expenses+=fuelExpense;
		 return fuelExpense;
	}
	/** fills Up
	 * @param aircraftIndex
	 * @return fuelExpense
	 */
	public double fillUp(int aircraftIndex) {
		double fuelExpense= aircrafts.get(aircraftIndex).fillUp();	
		 expenses+=fuelExpense;
		 return fuelExpense;
	}
	/** dumbs fuel
	 * @param aircraftIndex
	 * @param fuel
	 */
	void dumbFuel(int aircraftIndex, double fuel) {
		aircrafts.get(aircraftIndex).addFuel(-fuel);
	}
	/** finds path from fromAirport to fromAirport 
	 * @param map
	 * @param fromAirport
	 * @param toAirport
	 * @param prohibtedAirports
	 * @return 
	 */
	public Object[] findPath(Map <Airport,ArrayList<Airport>> map, Airport fromAirport, Airport toAirport, ArrayList<Airport> prohibtedAirports ){
		prohibtedAirports.add(fromAirport);
		if (fromAirport.equals(toAirport)) {
			Object[] myArray= new Object[2];
			myArray[0]=true;
			ArrayList<Airport> airports=new ArrayList<Airport>();
			airports.add(fromAirport);
			myArray[1]=airports; 
			return myArray;
		}
		ArrayList<Airport> neighbours= map.get(fromAirport);
		for (Airport neighbour: neighbours ) {
			if(prohibtedAirports.contains(neighbour)) {
				continue;
			}
			Object[] array=findPath(map,neighbour , toAirport,prohibtedAirports );
			if((boolean) array[0]) {
				ArrayList<Airport> list= (ArrayList<Airport>) array[1];
				list.add(0,fromAirport);
				Object[] myArray= new Object[2];
				myArray[0]=true;
				myArray[1]=list; 
				return myArray;
			}
			
		}
		Object[] myArray= new Object[1];
		myArray[0]=false;
		return myArray;
	}
	
	/** finds neighbours
	 * @param airports
	 * @return neighbours of nodes in graph
	 */
	public Map <Airport,ArrayList<Airport>> findGraph(ArrayList<Airport> airports){
		Map <Airport,ArrayList<Airport>> map= new HashMap <Airport,ArrayList<Airport>>();
		for(Airport airport1:airports ) {
			ArrayList<Airport> neighbours= new ArrayList<Airport>();
			for (Airport airport2:airports ) {
				if(airport1.getDistance(airport2)<14000 && airport1.getDistance(airport2)!=0) {
					neighbours.add(airport2);
				}	
			}
			map.put(airport1,neighbours);
		}
		return map;
		
	}
	
	/** take a passenger and fly to a Airport which is in the destination list of passenger and disembarks passenger
	 * @param passengerNumber
	 * @param airportNumber
	 * @param inputlines
	 * @param operationFeeofWidebody
	 * @param myWriter
	 */
	public void execute(int passengerNumber, int airportNumber, ArrayList<String> inputlines, double operationFeeofWidebody, FileWriter myWriter) {
		
		for(int i=0; i<passengerNumber;i++) {
			ArrayList<String> currentLine =Main.separate(inputlines.get(i+airportNumber+2),": ,[]");
			ArrayList <Airport> destinations= new ArrayList<Airport>();
			for(int j=4;j<currentLine.size();j++) {
				int id=Integer.parseInt(currentLine.get(j));
				ArrayList<Airport> airports= this.getAirports();
				for (Airport airport: airports) {
					if (airport.getId()==id) {
						destinations.add(airport);
						break;
					}
				}
			}
			
			this.addPassenger(currentLine.get(0),Long.parseLong(currentLine.get(1)),Double.parseDouble(currentLine.get(2)),Integer.parseInt(currentLine.get(3)),destinations );
			this.getPassengers().get(i).lastAirport=this.getPassengers().get(i).getDestinations().get(0);
		}
		
		
		
		
		ArrayList<Airport> airports= this.getAirports();
		Map <Airport,ArrayList<Airport>> map= this.findGraph(this.getAirports());
		ArrayList<Airport> nullAirports= new ArrayList<Airport>();
		
		boolean canReach=false;
		
		Passenger myPassenger= this.getPassengers().get(0);
		ArrayList<Airport> destinations= myPassenger.getDestinations();
		Airport secondDestination=destinations.get(1);
		ArrayList<Airport> path= new ArrayList<Airport>();
		for (Passenger passenger: this.passengers) {
			for (Airport destination: passenger.getDestinations()) {
				if (destination.equals(passenger.getDestinations().get(0))) {
					continue;
				}
				Object[] array1 =findPath(map,passenger.lastAirport,destination, nullAirports);
				if((boolean) array1[0]) {
					path= (ArrayList<Airport>) array1[1];
					myPassenger=passenger;
					secondDestination=destination;
					canReach=true;
					break;
				}
			}
			if (canReach) {
				break;
			}
			
		}
		int PassengerType= myPassenger.getPassengerType();
		
		this.addWidebodyPassengerAircraft(myPassenger.lastAirport, operationFeeofWidebody);
		try {
			myWriter.write("0 "+Integer.toString(myPassenger.lastAirport.getId())+" 1"+"\n");
			lastCalculatedProfit=Airline.revenue-Airline.expenses;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WidebodyPassengerAircraft myAircraft= (WidebodyPassengerAircraft) this.getAircrafts().get(0);
		this.fillUp(0);
		try {
			myWriter.write("3 0 140000"+"\n");
			lastCalculatedProfit=Airline.revenue-Airline.expenses;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		if(PassengerType==0) {
			myAircraft.setSeats(1,0,0);
			try {
				myWriter.write("2 0 "+Integer.toString(myAircraft.getEconomySeats())+" "+Integer.toString(myAircraft.getBusinessSeats())+" "+Integer.toString(myAircraft.getFirstClassSeats())+"\n");
				lastCalculatedProfit=Airline.revenue-Airline.expenses;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(PassengerType==1) {
			myAircraft.setSeats(0,1,0);
			try {
				myWriter.write("2 0 "+Integer.toString(myAircraft.getEconomySeats())+" "+Integer.toString(myAircraft.getBusinessSeats())+" "+Integer.toString(myAircraft.getFirstClassSeats())+"\n");
				lastCalculatedProfit=Airline.revenue-Airline.expenses;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(PassengerType==2) {
			myAircraft.setSeats(0,0,1);
			try {
				myWriter.write("2 0 "+Integer.toString(myAircraft.getEconomySeats())+"\n");
				lastCalculatedProfit=Airline.revenue-Airline.expenses;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(PassengerType==3) {
			myAircraft.setSeats(0,0,1);
			try {
				myWriter.write("2 0 "+Integer.toString(myAircraft.getEconomySeats())+" "+ Integer.toString(myAircraft.getBusinessSeats())+" "+Integer.toString(myAircraft.getFirstClassSeats())+"\n");
				lastCalculatedProfit=Airline.revenue-Airline.expenses;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		this.loadPassenger(myPassenger,myPassenger.lastAirport,0);
		try {
			myWriter.write("4 "+Long.toString(myPassenger.getID())+" 0 "+Integer.toString(myPassenger.lastAirport.getId())+"\n");
			lastCalculatedProfit=Airline.revenue-Airline.expenses;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=1;i<path.size();i++) {
			this.fly(path.get(i),0);
			try {
				myWriter.write("1 "+Integer.toString(path.get(i).getId())+" 0"+"\n");
				lastCalculatedProfit=Airline.revenue-Airline.expenses;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.fillUp(0);
			try {
				myWriter.write("3 0 140000"+"\n");
				lastCalculatedProfit=Airline.revenue-Airline.expenses;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			
		}
		this.unloadPassenger(myPassenger,0);
		try {
			myWriter.write("5 "+Long.toString(myPassenger.getID())+" 0 "+Integer.toString(myAircraft.getCurrentAirport().getId())+"\n");
			lastCalculatedProfit=Airline.revenue-Airline.expenses;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			myWriter.write(Double.toString(Airline.revenue-Airline.expenses));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	}
	
	

	

