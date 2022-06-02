package project.airlinecontainer;

import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import project.aircraftcontainer.PassengerAircraft;
import project.airportcontainer.Airport;
import project.airportcontainer.HubAirport;
import project.airportcontainer.MajorAirport;
import project.airportcontainer.RegionalAirport;
import project.concretecontainer.JetPassengerAircraft;
import project.concretecontainer.PropPassengerAircraft;
import project.concretecontainer.RapidPassengerAircraft;
import project.concretecontainer.WidebodyPassengerAircraft;
import project.passengercontainer.BusinessPassenger;
import project.passengercontainer.EconomyPassenger;
import project.passengercontainer.FirstClassPassenger;
import project.passengercontainer.LuxuryPassenger;
import project.passengercontainer.Passenger;

public class Airline {
    int maxAircraftCount;
    double operationalCost;
    public ArrayList<PassengerAircraft> aircrafts = new ArrayList<>();
    public double expenses=0;
    public double revenues=0;
    public double change;
    public HashMap<Long, Airport> airportMap;
    public String outputFile;
    

    double propFee,widebodyFee,rapidFee,jetFee;

    public Airline(int maxAircraftCount, double operationalCost,double propFee,double widebodyFee,double rapidFee,double jetFee) {
        this.maxAircraftCount = maxAircraftCount;
        this.operationalCost = operationalCost;
        this.propFee=propFee;
        this.widebodyFee=widebodyFee;
        this.rapidFee=rapidFee;
        this.jetFee=jetFee;
        this.airportMap = new HashMap<Long, Airport>();
    }

    /**
     * Helper method to print logs to output file
     * @param txt line to write to output file
     */
    public void writeToOutput(String txt){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile,true));
            writer.write(txt);
            writer.newLine();

            writer.close();

        }

        catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * Flies given aircraft to target airport considering required conditions
     *
     * @param toAirport target airport to fly
     * @param aircraftIndex creation index of the aircraft
     * @return whether fly operation is successful
     */
    public boolean fly(Airport toAirport, int aircraftIndex) {

        if (aircrafts.get(aircraftIndex).getCurrentAirport().equals(toAirport)) {
            return false;
        }
        if (toAirport.isFull()) {
            return false;
        }

        double currFuel = aircrafts.get(aircraftIndex).getFuel();
        double requiredFuel = aircrafts.get(aircraftIndex).getFuelConsumption(aircrafts.get(aircraftIndex).getCurrentAirport().getDistance(toAirport));
        if (currFuel < requiredFuel) {
            return false;
        } else {
        	change = -aircrafts.get(aircraftIndex).fly(toAirport)-operationalCost*aircrafts.size();
            expenses -= change;
            writeToOutput("1 "+ toAirport.getID()+ " "+ aircraftIndex);
            return true;
        }
    }


    /**
     * Loads passenger at the current airport to the target aircraft by checking requirements
     * @param passenger Passenger object to load to the aircraft
     * @param airport Current location of the aircraft and the passenger
     * @param aircraftIndex Creation index of the target aircraft
     * @return Whether the load operation is successful
     */
    boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
        PassengerAircraft currAircraft = aircrafts.get(aircraftIndex);


        if (!currAircraft.getCurrentAirport().equals(passenger.getCurrentAirport())) {
            return false;
        } else if (currAircraft.getWeight() + passenger.getWeight() > currAircraft.getMaxWeight()) {
            return false;
        } else if (currAircraft.isFull()) {
            return false;
        }else if (!passenger.getCurrentAirport().equals(airport)){
            return false;
        }

        change=-currAircraft.loadPassenger(passenger);
        expenses -=change;


        return true;

    }

    /**
     * Distributes all passengers in all airports to proper aircrafts
     */
    public void distributePassengers(){



        for (Airport airport: airportMap.values()){
            ArrayList<Passenger> toRemove = new ArrayList<>();
            for (Passenger passenger: airport.passengers){
                int aircraftIdx = 0;
                boolean loaded = loadPassenger(passenger,airport,aircraftIdx);

                while (!loaded && aircraftIdx < aircrafts.size()){
                    aircraftIdx++;
                    loaded = loadPassenger(passenger,airport,aircraftIdx);

                }
                if (loaded){
                    writeToOutput("4 "+ passenger.getID() + " "+ aircraftIdx + " "+ airport.getID());
                    toRemove.add(passenger);
                }


            }

            for(Passenger passenger:toRemove){
                airport.passengers.remove(passenger);
            }


        }
    }

    /**
     * Refuels and flies all flyable aircrafts to target locations with minimum cost to increase profit
     */
    public void flyAllAircrafts(){
        List<Airport> airports = new ArrayList<Airport>(airportMap.values());

        for (int i = 0; i< aircrafts.size(); i++){
            PassengerAircraft currAircraft = aircrafts.get(i);


            Airport minCostAirport =airports.get(0);
            double minCost = Double.MAX_VALUE;

            for (Airport curr: airports){
                if (!curr.equals(currAircraft.getCurrentAirport()) && currAircraft.getFlightCost(currAircraft.getCurrentAirport()) < minCost){
                    minCost = currAircraft.getFlightCost(currAircraft.getCurrentAirport());
                    minCostAirport = curr;

                }

            }

            currAircraft.fillUp();
            change=-currAircraft.getFuel()*currAircraft.getCurrentAirport().getFuelCost();
            expenses-=change;
            writeToOutput("3 "+ i + " "+ currAircraft.getFuel());


            if (fly(minCostAirport, i)){
            	Iterator<Passenger> itr= currAircraft.passengers.iterator();
            	while (itr.hasNext()) {
					Passenger passenger = (Passenger) itr.next();
					if (unloadPassenger(passenger,i)){
						itr.remove();
                        writeToOutput("5 "+ passenger.getID() + " "+ i + " "+ minCostAirport.getID());

                    }
					
				}
                //for(Passenger passenger: currAircraft.passengers){
                //    if (unloadPassenger(passenger,i)){
                //        writeToOutput("5 "+ passenger.getID() + " "+ i + " "+ minCostAirport.getID());

                //    }

                //}
            }


        }

    }


    /**
     * @param passenger Passenger object to unload from aircraft
     * @param aircraftIndex Creation index of the aircraft
     * @return Whether the unload operation is successful
     */
    boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
        // unloads from the aircraft
        //cannot happen if passenger cannot disembark at that airport.
        PassengerAircraft currAircraft = aircrafts.get(aircraftIndex);

        if (!currAircraft.passengers.contains(passenger)) {
            return false;
        }  else if (currAircraft.isEmpty()) {
            return false;
        }

        change= currAircraft.unloadPassenger(passenger);
        revenues += change;

        return true;
    }

    /**
     * @param aircraftIndex Creation index of the aircraft
     * @param fuel Amount to add as fuel
     * @return  Whether the fueling operation is successful
     */
    boolean refuel(int aircraftIndex, double fuel) {
        //add weight to the aircraft
        //write amount of the fuel bought, from which airport , to which aircraft
        if (aircrafts.get(aircraftIndex).addFuel(fuel)) {
            expenses += fuel * aircrafts.get(aircraftIndex).getCurrentAirport().getFuelCost();
            return true;
        } else {
            return false;
        }

    }

    /**
     * Fill all capable airports with WidebodyAircrafts as default
     */
    public void fillAirports(){

        for (Airport airport: airportMap.values()){
            if (aircrafts.size() == maxAircraftCount){
                break;
            }
            createWidebodyAircraft(airport,widebodyFee);
        }

    }


    /**
     * Creates Widebody aircraft and sets all seats to economy as default
     * @param airport Initial creation airport
     * @param fee Operational cost for this specific aircraft
     */
    public void createWidebodyAircraft(Airport airport,double fee) {

        PassengerAircraft aircraft = new WidebodyPassengerAircraft(airport,fee);
        change=0;
        writeToOutput("0 "+airport.getID()+" 1");
        aircraft.setAllEconomy();
        writeToOutput("2 " + aircrafts.size() + " " + aircraft.getEconomySeats() + " " + aircraft.getBusinessSeats() + " "+ aircraft.getFirstClassSeats());
        aircrafts.add(aircraft);

    }

    /**
     * Creates Rapid aircraft
     * @param airport Initial creation airport
     * @param fee Operational cost for this specific aircraft
     */
    public void createRapidAircraft(Airport airport, double fee) {

        aircrafts.add(new RapidPassengerAircraft(airport,fee));
    }

    /**
     * Creates Prop aircraft
     * @param airport Initial creation airport
     * @param fee Operational cost for this specific aircraft
     */
    public void createPropAircraft(Airport airport, double fee) {

        aircrafts.add(new PropPassengerAircraft(airport,fee));
    }

    /**
     * Creates Jet aircraft
     * @param airport Initial creation airport
     * @param fee Operational cost for this specific aircraft
     */
    public void createJetAircraft(Airport airport, double fee) {

        aircrafts.add(new JetPassengerAircraft(airport,fee));
    }


    /**
     * @param ID ID of the passenger
     * @param weight Weight of the passenger
     * @param baggageCount Baggage count of the passenger
     * @param destinations Desired destinations
     */
    public void createEconomyPassenger(long ID, double weight, int baggageCount, ArrayList<Long> destinations) {
        ArrayList<Airport> airportDestinations = new ArrayList<>();
        Airport initialAirport = airportMap.get(destinations.get(0));

        for (long d : destinations) {
            airportDestinations.add(airportMap.get(d));
        }

        initialAirport.passengers.add(new EconomyPassenger(ID, weight, baggageCount, airportDestinations));
    }

    /**
     * @param ID ID of the passenger
     * @param weight Weight of the passenger
     * @param baggageCount Baggage count of the passenger
     * @param destinations Desired destinations
     */
    public void createBusinessPassenger(long ID, double weight, int baggageCount, ArrayList<Long> destinations) {
        ArrayList<Airport> airportDestinations = new ArrayList<>();
        Airport initialAirport = airportMap.get(destinations.get(0));

        for (long d : destinations) {
            airportDestinations.add(airportMap.get(d));
        }


        initialAirport.passengers.add(new BusinessPassenger(ID, weight, baggageCount, airportDestinations));
    }

    /**
     * @param ID ID of the passenger
     * @param weight Weight of the passenger
     * @param baggageCount Baggage count of the passenger
     * @param destinations Desired destinations
     */
    public void createFirstClassPassenger(long ID, double weight, int baggageCount, ArrayList<Long> destinations) {
        ArrayList<Airport> airportDestinations = new ArrayList<>();
        Airport initialAirport = airportMap.get(destinations.get(0));

        for (long d : destinations) {
            airportDestinations.add(airportMap.get(d));
        }

        initialAirport.passengers.add(new FirstClassPassenger(ID, weight, baggageCount, airportDestinations));
    }

    /**
     * @param ID ID of the passenger
     * @param weight Weight of the passenger
     * @param baggageCount Baggage count of the passenger
     * @param destinations Desired destinations
     */
    public void createLuxuryPassenger(long ID, double weight, int baggageCount, ArrayList<Long> destinations) {
        ArrayList<Airport> airportDestinations = new ArrayList<>();
        Airport initialAirport = airportMap.get(destinations.get(0));

        for (long d : destinations) {
            airportDestinations.add(airportMap.get(d));
        }

        initialAirport.passengers.add(new LuxuryPassenger(ID, weight, baggageCount, airportDestinations));
    }

    /**
     * @param iD ID of the airport
     * @param x x coordinate
     * @param y y coordinate
     * @param fuelCost Refueling cost for unit
     * @param operationFee Operation fee
     * @param aircraftCapacity Max number of aircrafts
     */
    public void createRegionalAirport(long iD, double x, double y, double fuelCost, double operationFee,
                                      int aircraftCapacity) {
        this.airportMap.put(iD, new RegionalAirport(iD, x, y, fuelCost, operationFee, aircraftCapacity));

    }

    /**
     * @param iD ID of the airport
     * @param x x coordinate
     * @param y y coordinate
     * @param fuelCost Refueling cost for unit
     * @param operationFee Operation fee
     * @param aircraftCapacity Max number of aircrafts
     */
    public void createHubAirport(long iD, double x, double y, double fuelCost, double operationFee,
                                 int aircraftCapacity) {
        this.airportMap.put(iD, new HubAirport(iD, x, y, fuelCost, operationFee, aircraftCapacity));
    }

    /**
     * @param iD ID of the airport
     * @param x x coordinate
     * @param y y coordinate
     * @param fuelCost Refueling cost for unit
     * @param operationFee Operation fee
     * @param aircraftCapacity Max number of aircrafts
     */
    public void createMajorAirport(long iD, double x, double y, double fuelCost, double operationFee,
                                   int aircraftCapacity) {
        this.airportMap.put(iD, new MajorAirport(iD, x, y, fuelCost, operationFee, aircraftCapacity));
    }
    
    public void setOutputFile(String name) {
    	this.outputFile=name;
    }


}
