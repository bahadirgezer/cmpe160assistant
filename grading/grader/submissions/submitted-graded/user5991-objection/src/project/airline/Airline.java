package project.airline;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class Airline {
	public int maxAircraftCount; //public? private?
	public double operationalCost;
	public double prop;
	public double widebody;
	public double rapid;
	public double jet;
	ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
	HashMap<Integer,Airport> airports = new HashMap<Integer,Airport>();
	public double expenses = 0;
	public double revenue = 0;
	
	String filename;
	FileWriter myWriter;

	/**Constructor of Airline class, creates the output file.
	 * @param maxAircraftCount maximum aircraft capacity in the airline
	 * @param operationalCost 
	 * @param prop
	 * @param widebody
	 * @param rapid
	 * @param jet
	 */
	public Airline(int maxAircraftCount, double operationalCost, double prop, double widebody, double rapid, double jet, String filename) {
		this.maxAircraftCount=maxAircraftCount;
		this.operationalCost=operationalCost;
		this.prop=prop;
		this.widebody=widebody;
		this.rapid=rapid;
		this.jet=jet;

		this.filename = filename;
		File file= new File(filename); //THE NAME WILL CHANGE


	    try {file.createNewFile();}
	    catch(Exception e) {
	    	System.out.println("File couldn't be created");
	    }
	    try {
	        myWriter = new FileWriter(filename);
	    }
	    catch(IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	    
	    
	    
	}
	
	public void setFile(String filename) {
		 this.filename=filename;
	}
	/**Helper function to quickly write to a file.
	 * @param text
	 */
	public void writeToFile(String text) {
		try {
	        myWriter.write(text+"\n");
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	}
	
	/**
	 * At the end of the whole program, closes the file.
	 */
	public void destructor() {
		try {
			String profit = Double.toString(revenue-expenses);
			writeToFile(profit);
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**Sets the seats for the given aircraft.
	 * @param aircraft
	 * @param firstClass
	 * @param business
	 * @param economy
	 * @return
	 */
	public void setSeats(PassengerAircraft aircraft,int economy,int business,int firstClass) {
		String text = "2 " +aircraft.getID() +" "+economy+" "+business+" "+firstClass;
		writeToFile(text);
		aircraft.setSeats(economy, business, firstClass);
	}
	
	

	/*
	public boolean callAirport() {
		
		if(maxAircraftCount!=1) {
			for(Airport airport:airports.values()) {
				boolean a = false;
				boolean b = false;
				
				Airport toAirport = null;
				int[] nums = airport.getPassengersTypeCounts();
				ArrayList<Passenger> flyingPassengers = airport.flyPassengers();
				ArrayList<Passenger> loadedPassengers = new ArrayList<Passenger>();
				if(flyingPassengers.size()!=0) {
					Airport isAirport = flyingPassengers.get(0).getDestinations().get(1);
					if(isAirport.getDistance(airport)>4500) {continue;}
					createJetIfAbsent(airport);
					PassengerAircraft aircraft = null;
					try {
						aircraft = (PassengerAircraft)(airport.getAircrafts().get(0));
					}
					catch(Exception e) {break;}
					
					for(Passenger passenger:flyingPassengers) {
						if(passenger!=null) {
							if(passenger.getDestinations().size()!=1) {
								toAirport = passenger.getDestinations().get(1);
								
								if(loadPassenger(passenger,airport,aircrafts.size()-1)) {
								loadedPassengers.add(passenger);
								}	
								}
							}
							}
					fuelAircraft(aircraft,toAirport);
					if(aircraft.canfly(toAirport)) {b=true;
					fly(toAirport,aircrafts.size()-1);}
					
					for(Passenger passenger:loadedPassengers) {
						if(b==true) {
							if(unloadPassenger(passenger,aircrafts.size()-1)) {continue;}
								}
						
					}
					}
					}
					

			}
		
		return false;
	}
	*/
			
	
	
	
	
	
	/** it flies the first passenger in the first airport that has a passenger and was not full, using widebodyPassengerAircraft.
	 * @return
	 */
	public boolean sendOnePassenger() {
		Airport initialAirport = airports.get(airports.keySet().toArray()[0]);
		int j = 1;
		while(initialAirport.getPassengers().size()==0||initialAirport.isFull()==true) {
			initialAirport = airports.get(airports.keySet().toArray()[j]);
			j++;
		}
		Passenger passenger = initialAirport.getPassengers().get(0);
		ArrayList<Airport> destinations = passenger.getDestinations(); 
		Airport finalAirport = destinations.get(1); 
		PassengerAircraft selectedAircraft = createWideBodyIfAbsent(initialAirport);
		ArrayList<Airport> path = pathFinder(destinations,selectedAircraft);
		
		for(int i =0;i< path.size();i++) {
			Airport airport = path.get(i);
			if(airport.equals(initialAirport)) {
				loadPassenger(passenger,airport,aircrafts.indexOf(selectedAircraft));
			}
			else if(airport.equals(finalAirport)) {
				unloadPassenger(passenger,aircrafts.indexOf(selectedAircraft));
				break;
			}
			fuelAircraft(selectedAircraft,path.get(i+1));
			fly(path.get(i+1),aircrafts.indexOf(selectedAircraft));
			
			
		}
		
		
		return false;
	}
	
	
	/**Finds an appropriate path for the given destination, returns all the airports that must be visited as an ArrayList.
	 * @param destinations
	 * @return
	 */
	ArrayList<Airport> realPath = null;
	public ArrayList<Airport> pathFinder(ArrayList<Airport> destinations,PassengerAircraft selectedAircraft) {
		ArrayList<Airport> path = new ArrayList<Airport>();
		Airport initialAirport = destinations.get(0);
		Airport finalAirport = destinations.get(1);
		path.add(initialAirport);

		HashMap<Airport,ArrayList<Airport>> graph = createGraph();
		
		
		bfs(graph,initialAirport,finalAirport);

		
		return realPath;
	}
	
	
	/**Creates a graph which has the airports as keys and adjacent airports to it as values.
	 * @return
	 */
	public HashMap<Airport,ArrayList<Airport>> createGraph() {
		HashMap<Airport,ArrayList<Airport>> graph = new HashMap<Airport,ArrayList<Airport>>();
		for(Airport airport : airports.values()) {
			for(Airport airport2:airports.values()) {
				if(airport.equals(airport2)) {continue;}
				if(airport.getDistance(airport2)<14000) {
					if(graph.containsKey(airport)) {
						ArrayList<Airport> news = graph.get(airport);
						news.add(airport2);
						graph.put(airport, (ArrayList<Airport>) news.clone());
					}
					else {
						ArrayList<Airport> news = new ArrayList<Airport>();
						news.add(airport2);
						graph.put(airport, (ArrayList<Airport>) news.clone());
					}
				}
			}
		}
		return graph;
	}
	
	
	/**Breadth first search algorithm to find a path from the starting Airport to the final Airport.
	 * @param graph
	 * @param start
	 * @param end
	 */
	public void bfs(HashMap<Airport,ArrayList<Airport>> graph, Airport start, Airport end) {
		HashSet<Airport> visited = new HashSet<Airport>();
		Queue<HashMap<Airport,ArrayList<Airport>>> queue = new ArrayDeque<>();
		
		HashMap<Airport,ArrayList<Airport>> first = new HashMap<Airport,ArrayList<Airport>>();
		ArrayList<Airport> frst = new ArrayList<Airport>();
		frst.add(start);
		first.put(start,frst);
		queue.add(first);
		
		while((queue.size()!=0)) {
			HashMap<Airport, ArrayList<Airport>> something = queue.poll();
			Airport vertex = something.keySet().stream().findFirst().get();
			ArrayList<Airport> path = something.values().stream().findFirst().get();
			visited.add(vertex);
			for(Airport node : graph.get(vertex)) {
				if(node.equals(end)) {
					path.add(end);
					realPath = (ArrayList<Airport>) path.clone();
					return;
				}
				else {
					if(!(visited.contains(node))) {
						visited.add(node);
						path.add(node);
						HashMap<Airport,ArrayList<Airport>> nothing = new HashMap<Airport,ArrayList<Airport>>();
						nothing.put(node, (ArrayList<Airport>) path.clone());
						path.remove(node);
						queue.add(nothing);
					}
				}
			}
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	/**checks if it can fly to the next Airport.
	 * @param airport
	 * @param nextAirport
	 * @return
	 */
	public boolean canflyforpath(Airport airport,Airport nextAirport) {
		if(airport.getDistance(nextAirport)<14000) {
			return true;
		}
		return false;
	}
	
	
	/**Creates a WidebodyPassengerAircraft if it doesn't exist in the given airport.
	 * @param airport
	 * @return
	 */
	public PassengerAircraft createWideBodyIfAbsent(Airport airport) {
		PassengerAircraft selectedAircraft=null;
		ArrayList<Aircraft> aircraftsOfAirport = airport.getAircrafts();
		for(Aircraft aircraft:aircraftsOfAirport) {
			PassengerAircraft passengeraircraft = (PassengerAircraft)(aircraft);
			if(aircraft instanceof WidebodyPassengerAircraft) {selectedAircraft = passengeraircraft;}
		}
		if(selectedAircraft==null) {
			
			AircraftCreation(1,airport,true);
			ArrayList<Aircraft> aircraftsOfAirport2 = airport.getAircrafts();
			for(Aircraft aircraft:aircraftsOfAirport2) {
				PassengerAircraft passengeraircraft = (PassengerAircraft)(aircraft);
				if(aircraft instanceof WidebodyPassengerAircraft) {selectedAircraft = passengeraircraft;
				setSeats(selectedAircraft,5,5,5);}
			}
		}
		
		return selectedAircraft;
	}
	
	
	/**
	 * @param airport
	 * @return
	 */
	public PassengerAircraft createJetIfAbsent(Airport airport) {
		PassengerAircraft selectedAircraft=null;
		ArrayList<Aircraft> aircraftsOfAirport = airport.getAircrafts();
		for(Aircraft aircraft:aircraftsOfAirport) {
			PassengerAircraft passengeraircraft = (PassengerAircraft)(aircraft);
			if(aircraft instanceof JetPassengerAircraft) {selectedAircraft = passengeraircraft;}
		}
		if(selectedAircraft==null) {
			
			AircraftCreation(3,airport,false);
			ArrayList<Aircraft> aircraftsOfAirport2 = airport.getAircrafts();
			for(Aircraft aircraft:aircraftsOfAirport2) {
				PassengerAircraft passengeraircraft = (PassengerAircraft)(aircraft);
				if(aircraft instanceof JetPassengerAircraft) {selectedAircraft = passengeraircraft;
				setSeats(selectedAircraft,0,0,3);}
				
			}
		}
		
		return selectedAircraft;
	}
	

	
	/** Fills Up the aircrarft
	 * @param aircraft
	 * @param airport
	 */
	public void fuelAircraft(Aircraft aircraft,Airport airport) {
		Airport currentAirport = aircraft.getCurrentAirport();
		double fuelcost = aircraft.fillUp();
		double fuelcostperunit = currentAirport.getFuelCost();
		double fuelconsumption = fuelcost/fuelcostperunit;
		
		String text = "3 "+aircraft.getID()+" "+fuelconsumption;
		writeToFile(text);
		//System.out.println("3 "+aircrafts.get(aircraftIndex).getID()+" "+fuelconsumption);
		expenses+=fuelcost; //IM NOT SURE 'BOUT HERE!
	}
	
	/**Flies the aircraft to the given airport, adds the flight cost and running costs to the expenses.
	 * @param airport
	 * @param aircraftIndex
	 * @return
	 */
	public boolean fly(Airport airport, int aircraftIndex) {
		
		Aircraft aircraft = aircrafts.get(aircraftIndex);
		Airport currentAirport = aircraft.getCurrentAirport();
		
		
		double flyreturn = aircraft.fly(airport);

		expenses += flyreturn;
		
		
		double runningcost = operationalCost*aircrafts.size();
		expenses += runningcost;
		
		String text = "1 "+airport.getID()+" "+aircraftIndex;
		writeToFile(text);
		
		//System.out.println("1 "+airport.getID()+" "+aircraftIndex);
		return true;

	}
	
	
	
	/** Loads the given passenger from the airport to the aircraft at aircraftIndex index at aircrafts ArrayList.
	 * @param passenger
	 * @param airport
	 * @param aircraftIndex
	 * @return
	 */
	public boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		
		if(!airport.equals(aircrafts.get(aircraftIndex).getCurrentAirport())) {return false;}
		if(aircrafts.get(aircraftIndex).canload(passenger)==false) {return false;}
		
		//System.out.println("4 "+passenger.getID()+" "+aircrafts.get(aircraftIndex).getID()+" "+passenger.getCurrentAirport().getID());
		airport.removePassenger(passenger);
		double loadingcost = aircrafts.get(aircraftIndex).loadPassenger(passenger);
		expenses += loadingcost;
		String text = "4 "+passenger.getID()+" "+aircrafts.get(aircraftIndex).getID()+" "+passenger.getCurrentAirport().getID();
		writeToFile(text);
		return true;
	}
	
	
	/** Unloads the given passenger from the aircraft at aircraftIndex index at aircrafts ArrayList.
	 * @param passenger
	 * @param aircraftIndex
	 * @return
	 */
	public boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		double disembarkingprice = aircrafts.get(aircraftIndex).unloadPassenger(passenger);
		//System.out.println("5 "+passenger.getID()+" "+aircrafts.get(aircraftIndex).getID()+" "+passenger.getCurrentAirport().getID());
		String text = "5 "+passenger.getID()+" "+aircrafts.get(aircraftIndex).getID()+" "+passenger.getCurrentAirport().getID();
		writeToFile(text);
		revenue+=disembarkingprice;
		return true;
	}
	
	
	

	/** Creates a new aircraft for the given type and at the given airport.
	 * @param aircrafttype
	 * @param airport
	 * @param onlyone gives if the airline flies only one passenger or not.
	 * @return
	 */
	public boolean AircraftCreation(int aircrafttype, Airport airport,boolean onlyone) {
		boolean toCreate=false;
		if(onlyone==false&&aircrafts.size()<(maxAircraftCount-1)&&airport.isFull()==false) {
			toCreate=true;
		}
		if(onlyone==true) {
			toCreate=true;
		}
		if(toCreate) {
			String text = "0 "+airport.getID()+" "+aircrafttype;
			writeToFile(text);
			//System.out.println("0 "+airport.getID()+" "+aircrafttype);
			switch(aircrafttype) {
			case 0:
				PropPassengerAircraft newaircraft = new PropPassengerAircraft(airport, prop);
				aircrafts.add(newaircraft);
				airport.AddAircraft(newaircraft);
				newaircraft.setCurrentAirport(airport);
				newaircraft.IDSetter(aircrafts.size()-1);
				break;
			case 1:
				WidebodyPassengerAircraft newaircraft2 = new WidebodyPassengerAircraft(airport, widebody);
				aircrafts.add(newaircraft2);
				airport.AddAircraft(newaircraft2);
				newaircraft2.setCurrentAirport(airport);
				newaircraft2.IDSetter(aircrafts.size()-1);
				break;
			case 2:
				RapidPassengerAircraft newaircraft3 = new RapidPassengerAircraft(airport, rapid);
				aircrafts.add(newaircraft3);
				airport.AddAircraft(newaircraft3);
				newaircraft3.setCurrentAirport(airport);
				newaircraft3.IDSetter(aircrafts.size()-1);
				break;
			case 3:
				JetPassengerAircraft newaircraft4 = new JetPassengerAircraft(airport, jet);
				aircrafts.add(newaircraft4);
				airport.AddAircraft(newaircraft4);
				newaircraft4.setCurrentAirport(airport);
				newaircraft4.IDSetter(aircrafts.size()-1);
				break;
			}
		
			
			return true;
		}
		return false;
		
	}
	
	/**Creates an airport according to given parameters. Adds the new airport to the airports HashMap.
	 * @param airporttype
	 * @param ID
	 * @param x
	 * @param y
	 * @param fuelCost
	 * @param operationFee
	 * @param aircraftCapacity
	 */
	public void AirportCreation(int airporttype, int ID,int x,int y,double fuelCost,double operationFee,int aircraftCapacity) {
		Airport airport;
		switch(airporttype) {
		case 0:
			airport = new HubAirport(ID,x,y,fuelCost,operationFee,aircraftCapacity);
			airports.put(ID, airport);
			break;
		case 1:
			airport = new MajorAirport(ID,x,y,fuelCost,operationFee,aircraftCapacity);
			airports.put(ID, airport);
			break;
		case 2:
			airport = new RegionalAirport(ID,x,y,fuelCost,operationFee,aircraftCapacity);
			airports.put(ID, airport);
			break;
		}		
	}
	
	/** Creates a new passenger according to given parameters, sets it current airport as the first element in destinationsID parameter. Adds the passenger to the current airport.
	 * @param passengerType
	 * @param ID
	 * @param weight
	 * @param baggageCount
	 * @param destinationsID
	 */
	public void PassengerCreation(int passengerType, long ID, double weight, int baggageCount, ArrayList<Integer> destinationsID) {
		Passenger passenger;
		ArrayList<Airport> destinations = new ArrayList<Airport>();
		for(int i:destinationsID) {destinations.add(airports.get(i));}
		if(passengerType==0) {passenger = new EconomyPassenger(ID, weight, baggageCount, destinations);}
		else if(passengerType==1) {passenger = new BusinessPassenger(ID, weight, baggageCount, destinations);}
		else if(passengerType==2) {passenger = new FirstClassPassenger(ID, weight, baggageCount, destinations);}
		else{passenger = new LuxuryPassenger(ID, weight, baggageCount, destinations);}
		passenger.setCurrentAirport(destinations.get(0));
		destinations.get(0).addPassenger(passenger);
	}
	
	
	/** Adds fuel to the aircraft and adds the cost to the expenses.
	 * @param aircraft
	 * @param fuel
	 */
	public void refuel(Aircraft aircraft,double fuel) {
		
		double cost = aircraft.addFuel(fuel);
		String text = "3 "+aircraft.getID()+" "+fuel;
		writeToFile(text);
		//System.out.println("3 "+aircraft.getID()+" "+fuel);
		expenses += cost;
	}
	
	/** Maxes out the fuel of the aircraft and adds the cost to the expenses.
	 * @param aircraft
	 */
	public void fillUp(Aircraft aircraft) {
		double cost = aircraft.fillUp();
		double costperunit = aircraft.getCurrentAirport().getFuelCost();
		double fuel = cost/costperunit;
		//System.out.println("3 "+aircraft.getID()+" "+fuel);
		String text = "3 "+aircraft.getID()+" "+fuel;
		writeToFile(text);
		expenses += cost;
	}
	
	


}



