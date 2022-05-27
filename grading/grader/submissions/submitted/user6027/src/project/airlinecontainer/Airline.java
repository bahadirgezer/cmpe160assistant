package project.airlinecontainer;

import java.util.ArrayList;
import java.util.HashMap;

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

	protected int maxAircraftCount;
	protected double operationalCost;
	protected ArrayList<PassengerAircraft> aircrafts_list=new ArrayList<PassengerAircraft>();
	protected ArrayList<Airport> airports_list= new ArrayList<Airport>();
	protected ArrayList<Passenger> passenger_list=new ArrayList<Passenger>();
	protected static double total_revenue;
	protected ArrayList<String> to_write = new ArrayList<String>();
	public static double old_revenue;
	
	
	
	/** Finding distance between two airports.
	 * @param airport1 
	 * @param airport2
	 * @return the distance
	 */
	public double finding_distance(Airport airport1,Airport airport2) {
		double distancex = Math.pow(airport1.get_coordinatesx()-airport2.get_coordinatesx(),2);
		double distancey = Math.pow(airport1.get_coordinatesy()-airport2.get_coordinatesy(),2);
		return Math.sqrt(distancex+distancey);
	}
	

	/**
	 * @return If we say distance between 2 airport<140000 I said they are neighbor then I found the hashmap. 
	 */
	public HashMap<Airport, ArrayList<Airport>> finding_hashmap_of_airports(){
		HashMap<Airport, ArrayList<Airport>> airportshashmap = new HashMap<Airport, ArrayList<Airport>>();
		for (Airport airport1:this.airports_list) {
			ArrayList<Airport> airport_neighbour_list= new ArrayList<Airport>();
			for (Airport airport2:this.airports_list) {
				if (this.finding_distance(airport1, airport2)<14000) {
					if (airport2.equals(airport1)) {
						continue;
					}
					else {
						airport_neighbour_list.add(airport2);
					}
				}
			}
			airportshashmap.put(airport1, airport_neighbour_list);
		}
		return airportshashmap;
	}
	
	
	/**
	 * @param i it is for the deciding which passenger we will load fly and then unload. First decide the path then fly.
	 * This is the main method which I called for main.java 
	 */
	public void check_passenger_cangoanydestionation(int i) {
		Passenger passenger = this.passenger_list.get(i);
		ArrayList<Airport> null_list= new ArrayList<Airport>();
		ArrayList<Object> null_arraylist = new ArrayList<Object>();
		if (passenger instanceof EconomyPassenger) {
			this.aircrafts_list.get(0).setAllEconomy();
			this.to_write.add("2 " + " 0 " + "450 " + "0 " + " 0");
			
		if (this.finding_path(passenger.getdestinations().get(0), passenger.getdestinations().get(1), null_arraylist, null_list).size()==1) {
			
			this.loadPassenger(this.aircrafts_list.get(0), passenger);
			
			this.fly(passenger.getdestinations().get(1),0);
			
			this.unloadPassenger(passenger, 0);	
		}
		}
		
		else if (passenger instanceof BusinessPassenger) {
			this.aircrafts_list.get(0).setAllBusiness();
			this.to_write.add("2 " + " 0 " + "0 " + "150 " + " 0" );
		if (this.finding_path(passenger.getdestinations().get(0), passenger.getdestinations().get(1), null_arraylist, null_list).size()==1);
			this.loadPassenger(this.aircrafts_list.get(0), passenger);
			this.fly(passenger.getdestinations().get(1),0);
			this.unloadPassenger(passenger, 0);
		}
		else {
			this.aircrafts_list.get(0).setAllFirstClass();
			this.to_write.add("2 " + " 0 " + "0 " + "0 " + " 56");
		if (this.finding_path(passenger.getdestinations().get(0), passenger.getdestinations().get(1), null_arraylist, null_list).size()==1);
			this.loadPassenger(this.aircrafts_list.get(0), passenger);
			this.fly(passenger.getdestinations().get(1),0);
			this.unloadPassenger(passenger, 0);
		}
		String string_total_revenue = String.valueOf(this.total_revenue);
		this.to_write.add(string_total_revenue);
	}
		
	/**
	 * @param airport1 Initial airport I am in
	 * @param airport2 Final airport I want to go
	 * @param arraylist the list of the airports if I could go there otherwise null
	 * @param prohibited_airports airports we passed while going to airport2
	 * @return the arrayList of airports which I pass to go airport2
	 */
	public ArrayList<Object> finding_path(Airport airport1,Airport airport2 , ArrayList<Object> arraylist,ArrayList<Airport> prohibited_airports){
		ArrayList<Airport> arraylist_of_neighbor=this.finding_hashmap_of_airports().get(airport1);
		
		if (arraylist_of_neighbor.size()==0) {
			ArrayList<Object> null_arraylist = new ArrayList<Object>();
			return null_arraylist;
		}
		
		if(arraylist_of_neighbor.contains(airport2)) {
			
			arraylist.add(airport2);
			return arraylist;
		}
		else {
			prohibited_airports.add(airport1);
			for (Airport airport3:arraylist_of_neighbor) {
				if (prohibited_airports.contains(airport3)==false) {
					continue;
				}
				else {
					ArrayList<Object> arraylist_for_airport3=this.finding_path(airport3, airport2, arraylist,prohibited_airports);
					if (arraylist_for_airport3.contains(airport2)) {
						arraylist_for_airport3.add(0,airport3);
						return arraylist_for_airport3;
					}
				}
				
			}
			ArrayList<Object> null_arraylist = new ArrayList<Object>();
			return null_arraylist;
			
		}

	}
	
	/** I fly to toAirport with this operation just after fill up the aircraft.
	 * When I add 1 to Math.exp() in land_aircraft method in airports, my error will be less then %0.008.
	 * @param toAirport The airport which I wanted to fly
	 * @param aircraftIndex the aircraft which I use for flying
	 * @return true if I can fly false if I wont
	 */
	public boolean fly(Airport toAirport, int aircraftIndex) {
		this.old_revenue=this.total_revenue;
		double distance = this.finding_distance(toAirport, this.aircrafts_list.get(0).getCurrentAirport());
		double added_fuel = this.aircrafts_list.get(0).getfuelcapacity();
		String added_fuels = String.valueOf(added_fuel);
		this.total_revenue-=this.aircrafts_list.get(0).fillUp();
		this.aircrafts_list.get(0).add_weight_of_fuel();
		String revenue = String.valueOf(this.total_revenue-this.old_revenue);
		this.to_write.add("3 0 " + added_fuels);
		this.old_revenue=this.total_revenue;
		
		
		this.total_revenue-=aircrafts_list.size()*this.operationalCost;
		if (toAirport.get_current_aircrafts().size()==toAirport.get_aircraftCapacity()) {
			return false;
		}
		else if (aircrafts_list.get(aircraftIndex).can_fly_happen(toAirport,distance)==false) {
			return false;
		}
		else {
			total_revenue-= aircrafts_list.get(aircraftIndex).fly(toAirport);
			
			String string_id_of_airport=String.valueOf(toAirport.getID());
			String string_id_of_aircraft=String.valueOf(aircraftIndex);
			String string_total_revenue =String.valueOf(this.total_revenue-this.old_revenue);
			this.to_write.add("1 "+ string_id_of_airport + " 0 ");
			return true;
		}
	}
	

	/** unloads the passengers if they can unload in that airport. we check first then return true if we can unload. 
	 * change total_revenue with costs
	 * @param passenger which passenger we decide to unload
	 * @param aircraftIndex to define aircraft 
	 * @return true if we can unload otherwise false
	 */
	public boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		this.old_revenue=this.total_revenue;
		double x =aircrafts_list.get(aircraftIndex).unloadPassenger(passenger);
		if (x==aircrafts_list.get(aircraftIndex).getOperationfee()) {
			this.total_revenue-=x;
		return true;
		}
		else {
			String passengers_id=String.valueOf(passenger.get_id());
			String aircrafts_id=String.valueOf(aircraftIndex);
			String airports_id=String.valueOf(aircrafts_list.get(aircraftIndex).getCurrentAirport().getID());
			this.total_revenue+=x;
			String string_total_revenue = String.valueOf(this.total_revenue-this.old_revenue);
			
			this.to_write.add("5 " + passengers_id + " " + aircrafts_id + " " + airports_id);
			return false;
		}
	}
	/** adding fuel to specific aircraft
	 * @param aircraftIndex the aircraft
	 * @param fuel amount of fuel
	 * @return if we can add true if it exceed the total weigth false
	 */
	public boolean refuel(int aircraftIndex, double fuel) {
		if (fuel*0.7<=aircrafts_list.get(aircraftIndex).getAvailableWeight()) {
		aircrafts_list.get(aircraftIndex).addFuel(fuel);
		this.total_revenue-=aircrafts_list.get(aircraftIndex).getCurrentAirport().getfuelCost();
		return true;
		}
		else {
			return false;
		}
	}
	
	/**For creating aircraft and add it to the aircraft list
	 * @param firstAirportsid in which airport we creat the aircraft
	 * @param operationfee aircrafts special operation fee
	 * @param AircraftType which type of aircraft is it like 0 is prop 1 is widebody etc.
	 */
	public void aircraft_creations(long firstAirportsid,double operationfee,int AircraftType) {
		int index = 0;
			for (Airport airport:airports_list) {
				if(airport.getID()==firstAirportsid) {
					index = airports_list.indexOf(airport);
				}
			}
		
		if (AircraftType==0) {
			PassengerAircraft new_aircraft= new PropPassengerAircraft(operationfee,airports_list.get(index),14000,23000,60,600,0.6,0.9);
			this.aircrafts_list.add(new_aircraft);
			airports_list.get(index).add_aircraft(new_aircraft);
			new_aircraft.set_fuels_value();
			String log = String.valueOf(firstAirportsid);
			String string_total_revenue =String.valueOf(this.total_revenue);
			this.to_write.add("0" + log + "0" );
		}
		if (AircraftType==1) {
			PassengerAircraft new_aircraft= new WidebodyPassengerAircraft(operationfee,airports_list.get(index),135000,250000,450,140000,3.0,0.7);
			this.aircrafts_list.add(new_aircraft);	
			airports_list.get(index).add_aircraft(new_aircraft);
			new_aircraft.set_fuels_value();
			String log = String.valueOf(firstAirportsid);
			String string_total_revenue =String.valueOf(this.total_revenue);
			this.to_write.add("0 " + log + " 1 ");
		}
		if (AircraftType==2) {
			PassengerAircraft new_aircraft= new RapidPassengerAircraft(operationfee,airports_list.get(index),80000,185000,120,120000,5.3,1.9);
			this.aircrafts_list.add(new_aircraft);	
			airports_list.get(index).add_aircraft(new_aircraft);
			new_aircraft.set_fuels_value();
			String log = String.valueOf(firstAirportsid);
			String string_total_revenue =String.valueOf(this.total_revenue);
			this.to_write.add("0 " + log + "2 ");
		}
		if (AircraftType==3) {
			PassengerAircraft new_aircraft= new JetPassengerAircraft(operationfee,airports_list.get(index),10000,18000,30,10000,0.7,5);
			this.aircrafts_list.add(new_aircraft);	
			airports_list.get(index).add_aircraft(new_aircraft);
			new_aircraft.set_fuels_value();
			String log = String.valueOf(firstAirportsid);
			String string_total_revenue =String.valueOf(this.total_revenue);
			this.to_write.add("0" + log + "2");
		}
	}
	
	/** creating airport and add to the arrayList of airports
	 * @param airport_type major hub etc. 
	 * @param id specific id
	 * @param x coordinate x
	 * @param y coordinate y
	 * @param fuelcost fuel cost in that airport
	 * @param operationfee operation fee of that airport
	 * @param aircraftCapacity maximum number of aircrafts that airport can lift
	 */
	public void airport_creations(String airport_type, long id, double x, double y,double fuelcost,double operationfee,int aircraftCapacity) {
		if (airport_type.equals("hub ")) {
			Airport new_airport= new HubAirport(id,x,y,fuelcost,operationfee,aircraftCapacity);
			airports_list.add(new_airport);	
			
		}
		if (airport_type.equals("major ")) {
			Airport new_airport= new MajorAirport(id,x,y,fuelcost,operationfee,aircraftCapacity);
			airports_list.add(new_airport);
		}
		if (airport_type.equals("regional ")) {			
			Airport new_airport= new RegionalAirport(id,x,y,fuelcost,operationfee,aircraftCapacity);
			airports_list.add(new_airport);	
		}		
	}
	
	/** creating passenger type 
	 * @param passenger_type is it economy business first or luxury type.
	 * @param id specific id of an airport
	 * @param weight the passengers weight 
	 * @param baggageCount baggage count of that passenger
	 * @param destination passengers want to go list
	 */
	public void passengercreation(String passenger_type,Long id, double weight, int baggageCount, ArrayList<Long> destination) {
		ArrayList<Airport> destinations = new ArrayList();
		for (Long elem:destination) {
			for (Airport airport:airports_list) {
				if(airport.getID()==elem) {
					destinations.add(airport);
				}
			}
		}
		if (passenger_type.equals("economy ")) {
		Passenger new_passenger = new EconomyPassenger(id, weight, baggageCount, destinations) ;
		new_passenger.setLast_disembarked_airport(destinations.get(0));
		this.passenger_list.add(new_passenger);
		
		}
		if (passenger_type.equals("business ")) {
			Passenger new_passenger = new BusinessPassenger(id, weight, baggageCount, destinations) ;
			new_passenger.setLast_disembarked_airport(destinations.get(0));
			this.passenger_list.add(new_passenger);
		}
		if (passenger_type.equals("first ")) {
			Passenger new_passenger = new FirstClassPassenger(id, weight, baggageCount, destinations) ;
			new_passenger.setLast_disembarked_airport(destinations.get(0));
			this.passenger_list.add(new_passenger);
		}
		if (passenger_type.equals("luxury ")) {
			Passenger new_passenger = new LuxuryPassenger(id, weight, baggageCount, destinations) ;
			new_passenger.setLast_disembarked_airport(destinations.get(0));
			this.passenger_list.add(new_passenger);
		}
		
	}
	
	
	
	/** to getting total revenue
	 * @return
	 */
	public double getTotal_revenue() {
		return this.total_revenue;
	}
	/** to getting airport lists that created
	 * @return
	 */
	public ArrayList<Airport> getairports_list(){
		return this.airports_list;
	}
	/**to getting passenger lists that created
	 * @return
	 */
	public ArrayList<Passenger> getpassenger_list(){
		return this.passenger_list;
	}
	/** for setting operational costs
	 * @param operational_cost
	 */
	public void set_operational_cost(double operational_cost) {
		this.operationalCost=operational_cost;
	}

	/** getting id of a passengers first place they want to go
	 * @param i
	 * @return
	 */
	public long get_id_of_passengers_first_destination(int i) {
		return this.passenger_list.get(i).getdestinations().get(0).getID();						
	}
	
	
	/** Is aircraft full?
	 * @param aircraft
	 * @return
	 */
	public boolean isfull(PassengerAircraft aircraft) {
		return aircraft.isFull();
	}
	
	/** is aircraft empty?
	 * @param aircraft
	 * @return
	 */
	public boolean isempty(PassengerAircraft aircraft) {
		return aircraft.isEmpty();
	}	
	/** number of passengers in aircrafts
	 * @return
	 */
	public int number_of_seats_occupied_in_aircraft() {
		return this.aircrafts_list.get(0).get_current_passenger().size();
	}
	/** for loading passenger to specific aircraft, arrange total revenue with costs and we use it by calling 
	 * load passenger from aircraft.java
	 * @param aircraft specific aircraft which we want to load passenger
	 * @param passenger that passenger
	 * @return true 
	 */
	public boolean loadPassenger(PassengerAircraft aircraft,Passenger passenger) {
		this.total_revenue=this.old_revenue;
		this.total_revenue-=aircraft.loadPassenger(passenger);
		String string_old_revenue = String.valueOf(total_revenue-old_revenue);
		String passengers_id=String.valueOf(passenger.get_id());
		String aircrafts_id="0";
		String airports_id=String.valueOf(aircrafts_list.get(0).getCurrentAirport().getID());
		to_write.add("4 " + passengers_id + " 0 " +  airports_id);
		return true;
	}
	
	/** the output file which I want to write at the end
	 * @return
	 */
	public ArrayList<String> whole_file_to_write(){
		return this.to_write;
	}

}

