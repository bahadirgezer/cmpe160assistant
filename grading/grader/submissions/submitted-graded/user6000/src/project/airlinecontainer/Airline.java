package project.airlinecontainer;

import java.util.ArrayList;
import java.util.HashMap;

import project.airlinecontainer.aircraftcontainer.Aircraft;
import project.airportcontainer.Airport;
import project.airportcontainer.HubAirport;
import project.airportcontainer.MajorAirport;
import project.airportcontainer.RegionalAirport;
import project.passengercontainer.BusinessPassenger;
import project.passengercontainer.EconomyPassenger;
import project.passengercontainer.FirstClassPassenger;
import project.passengercontainer.LuxuryPassenger;
import project.passengercontainer.Passenger;
import project.airlinecontainer.aircraftcontainer.PassengerAircraft;
import project.airlinecontainer.aircraftcontainer.concretecontainer.JetPassengerAircraft;
import project.airlinecontainer.aircraftcontainer.concretecontainer.PropPassengerAircraft;
import project.airlinecontainer.aircraftcontainer.concretecontainer.RapidPassengerAircraft;
import project.airlinecontainer.aircraftcontainer.concretecontainer.WidebodyPassengerAircraft;

public class Airline {
	HashMap<Airport,ArrayList<Airport>> map=new HashMap<Airport,ArrayList<Airport>>();
	int maxAircraftCount;
	double operationalCost;
	double runningCost=operationalCost*maxAircraftCount;
	public static double expenses=0;
	public static double revenue=0;
	public ArrayList<Aircraft>aircraftofAirline = new ArrayList<Aircraft>();
	public ArrayList<Airport>airports=new ArrayList<Airport>();
	public ArrayList<Passenger>passengers=new ArrayList<Passenger>();
	public ArrayList<Aircraft>aircrafts=new ArrayList<Aircraft>();
	boolean fly(Airport toAirport, int aircraftIndex) {
		Aircraft aircraft=aircraftofAirline.get(aircraftIndex);
		if(aircraft.isFlyPossible(toAirport)==true) {
		expenses+=aircraft.fly(toAirport);
		  
		  return true;
		}
		else
			expenses+=runningCost;
			return false;
	}
	
	public boolean newJetPassengerAircraft(Airport currentAirport,double operationFee) {
		if (aircrafts.size()<maxAircraftCount) {
			JetPassengerAircraft aircraft= new JetPassengerAircraft(currentAirport,operationFee);
			aircrafts.add(aircraft);
			runningCost=operationalCost*aircrafts.size();
			return true;
		}
		else {
			return false;
		}
	}
	public boolean newPropPassengerAircraft(Airport currentAirport,double operationFee) {
		if (aircrafts.size()<maxAircraftCount) {
			PropPassengerAircraft aircraft= new PropPassengerAircraft(currentAirport,operationFee);
			aircrafts.add(aircraft);
			runningCost=operationalCost*aircrafts.size();
			return true;
		}
		else {
			return false;
		}
	}
	public boolean newRapidPassengerAircraft(Airport currentAirport,double operationFee) {
		if (aircrafts.size()<maxAircraftCount) {
			RapidPassengerAircraft aircraft= new RapidPassengerAircraft(currentAirport,operationFee);
			aircrafts.add(aircraft);
			runningCost=operationalCost*aircrafts.size();
			return true;
		}
		else {
			return false;
		}
	}
	public boolean newWidebodyPassengerAircraft(Airport currentAirport,double operationFee) {
		if (aircrafts.size()<maxAircraftCount) {
			WidebodyPassengerAircraft aircraft= new WidebodyPassengerAircraft(currentAirport,operationFee);
			aircrafts.add(aircraft);
			runningCost=operationalCost*aircrafts.size();
			return true;
		}
		else {
			return false;
		}
	}
	
	public void mapFiller() {
		for(int i=0;i<airports.size();i++) {
			ArrayList<Airport>empty=new ArrayList<Airport>();
			map.put(airports.get(i),empty);
			for(int j=0;j<airports.size();j++) {			
				
				double distance= Math.pow(( Math.pow((airports.get(j).getxPosition()-airports.get(i).getxPosition()),2)+Math.pow((airports.get(j).getyPosition()-airports.get(i).getyPosition()),2)),0.5);
				if(distance<=14000) {
					map.get(i).add(airports.get(j));
				}
			}
		}
	}
	
	
	public void createEconomyPassenger(long ID, double weight, int baggageCount, ArrayList<Long> destinations) {
		ArrayList<Airport>realDestinations=new ArrayList();
		for(int i=0; i<destinations.size();i++) {
			for(int j=0; j<airports.size();j++) {
				if(airports.get(j).getID()==destinations.get(i)) {
					realDestinations.add(airports.get(j));
					continue;
			}
		}
	}
		EconomyPassenger economyPassenger=new EconomyPassenger(ID,weight,baggageCount,destinations);
		passengers.add(economyPassenger);
	}
	
	public void createBusinessPassenger(long ID, double weight, int baggageCount, ArrayList<Long> destinations) {
		ArrayList<Airport>realDestinations=new ArrayList();
		for(int i=0; i<destinations.size();i++) {
			for(int j=0; j<airports.size();j++) {
				if(airports.get(j).getID()==destinations.get(i)) {
					realDestinations.add(airports.get(j));
					continue;
			}
		}
	}
		BusinessPassenger businessPassenger=new BusinessPassenger(ID,weight,baggageCount,destinations);
		passengers.add(businessPassenger);
	}
	
	public void createFirstClassPassenger(long ID, double weight, int baggageCount, ArrayList<Long> destinations) {
		ArrayList<Airport>realDestinations=new ArrayList();
		for(int i=0; i<destinations.size();i++) {
			for(int j=0; j<airports.size();j++) {
				if(airports.get(j).getID()==destinations.get(i)) {
					realDestinations.add(airports.get(j));
					continue;
			}
		}
	}
		FirstClassPassenger firstClassPassenger=new FirstClassPassenger(ID,weight,baggageCount,destinations);
		passengers.add(firstClassPassenger);
	}
	
	public void createLuxuryPassenger(long ID, double weight, int baggageCount, ArrayList<Long> destinations) {
		ArrayList<Airport>realDestinations=new ArrayList();
		for(int i=0; i<destinations.size();i++) {
			for(int j=0; j<airports.size();j++) {
				if(airports.get(j).getID()==destinations.get(i)) {
					realDestinations.add(airports.get(j));
					continue;
			}
		}
	}
		LuxuryPassenger luxuryPassenger=new LuxuryPassenger(ID,weight,baggageCount,destinations);
		passengers.add(luxuryPassenger);
	}
	
	
	
	
	public void createHubAirport(long ID,double x,double y,double fuelCost,double operationFee,int aircraftCapacity) {
		HubAirport airport = new HubAirport(ID,x,y,fuelCost,operationFee,aircraftCapacity);
		airports.add(airport);
	}
	public void createMajorAirport(long ID,double x,double y,double fuelCost,double operationFee,int aircraftCapacity) {
		MajorAirport airport = new MajorAirport(ID,x,y,fuelCost,operationFee,aircraftCapacity);
		airports.add(airport);
	}
	
	public void createRegionalAirport(long ID,double x,double y,double fuelCost,double operationFee,int aircraftCapacity) {
		RegionalAirport airport = new RegionalAirport(ID,x,y,fuelCost,operationFee,aircraftCapacity);
		airports.add(airport);
	}
	
	boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
		
		Aircraft aircraft=aircraftofAirline.get(aircraftIndex);
		double fullnessStart=aircraft.getFullness();
		expenses+=aircraft.loadPassenger(passenger);
		double fullnessEnd=aircraft.getFullness();
		if(fullnessStart==fullnessEnd)
			return false;
		else
			return true;
	}
	boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
		Aircraft aircraft=aircraftofAirline.get(aircraftIndex);
		double fullnessStart=aircraft.getFullness();
		revenue+=aircraft.unloadPassenger(passenger);
		double fullnessEnd=aircraft.getFullness();
		if(fullnessStart==fullnessEnd)
			return false;
		else
			return true;
		
	}
	
	ArrayList<Airport> pathFinder(Airport beginning,Airport end,Passenger passenger,Aircraft aircraft ,ArrayList<Airport>path ,ArrayList<Airport>banned,HashMap<Airport,ArrayList<Airport>> map){
		
		banned.add(beginning);	
		if(map.get(beginning).contains(end)) {
			path.add(end);
			return path;
		}
			
			
		
		for(Airport j: map.get(beginning)) {
			if(banned.contains(j))
				continue;
			else {			
				ArrayList <Airport> newpath=new ArrayList<Airport>();
				newpath=path;
				newpath.add(j);
				ArrayList <Airport> newbanned=new ArrayList<Airport>();
				newbanned=banned;
				pathFinder(j,end,passenger,aircraft,newpath,newbanned,map);
		}
		
	}
		ArrayList<Airport>emptyness=new ArrayList<Airport>();
		return emptyness;
	}
	ArrayList<Airport> pather(Passenger passenger,Aircraft aircraft ,ArrayList<Airport>path ,ArrayList<Airport>banned,HashMap<Airport,ArrayList<Airport>> map){
		
		for(Airport end : passenger.publicDestinations() ) {
			ArrayList<Airport>result=new ArrayList<Airport>();
			
			result=pathFinder(passenger.currentAirport,end,passenger,passenger.currentAircraft,path,banned,map);
			if(result.size()!=0)
				return result;
		}
		ArrayList<Airport>emptyness=new ArrayList<Airport>();
		return emptyness;
	
	}
	ArrayList<Airport> flythePath(ArrayList<Passenger>passengers,Aircraft aircraft ,HashMap<Airport,ArrayList<Airport>> map){		
	for(Passenger i:passengers) {	
		ArrayList<Airport>path=new ArrayList<Airport>();
		ArrayList<Airport>banned=new ArrayList<Airport>();
		ArrayList<Airport>result=new ArrayList<Airport>();
		result=pather(i,i.currentAircraft,path,banned,map);
		if(result.size()!=0) {
			for(Airport j:result) {
			this.fly(j, 0);
			
			i.currentAircraft.fulle();
			
			
			return result;
		}	
	}
	ArrayList<Airport>emptyness=new ArrayList<Airport>();
	return emptyness;
	}
	}
	//for(int i=0; i<passenger.publicDestinations().size();i++) {
}
	

