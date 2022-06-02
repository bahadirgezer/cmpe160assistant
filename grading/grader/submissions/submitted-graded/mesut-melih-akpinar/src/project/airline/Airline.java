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
import project.passenger.Passenger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The main class of the project.airline.
 * All the passengers and aircrafts are operated by the Airline class.
 */

public class Airline {
    private int maxAircraftCount;
    private double operationCost;
    private double propOperationFee, rapidOperationFee, jetOperationFee, widebodyOperationFee;
    private double profit = 0;
    private ArrayList<Aircraft> aircraftArrayList;
    private static ArrayList<Airport> airportArrayList;

    /**
     * Constructor of the Airline class.
     * @param maxAircraftCount the maximum number of aircrafts that can be operated by the project.airline.
     * @param operationCost the cost of operating an aircraft.
     * @param propOperationFee the fee of operating a propeller aircraft.
     * @param rapidOperationFee the fee of operating a rapid aircraft.
     * @param jetOperationFee the fee of operating a jet aircraft.
     * @param widebodyOperationFee the fee of operating a widebody aircraft.
     */
    public Airline(int maxAircraftCount, double operationCost, double propOperationFee, double rapidOperationFee, double jetOperationFee, double widebodyOperationFee) {
        this.maxAircraftCount = maxAircraftCount;
        this.operationCost = operationCost;
        this.propOperationFee = propOperationFee;
        this.rapidOperationFee = rapidOperationFee;
        this.jetOperationFee = jetOperationFee;
        this.widebodyOperationFee = widebodyOperationFee;
        aircraftArrayList = new ArrayList<>();
        airportArrayList = new ArrayList<>();
    }

    /**
     * Fly an aircraft to a destination.
     */
    public boolean fly(Airport toAirport, int aircraftIndex) {
        if(toAirport.isFull()) return false;
        Aircraft currentAircraft = aircraftArrayList.get(aircraftIndex);
        return currentAircraft.isFuelEnough(toAirport);
    }

    /**
     * Load a project.passenger to an aircraft.
     * @param passenger the project.passenger to be loaded.
     * @param aircraftIndex the index of the aircraft.
     * @param airport the project.airport where the project.passenger is loaded.
     */
    public boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
        Aircraft currentAircraft = aircraftArrayList.get(aircraftIndex);
        if(!currentAircraft.isIn(airport)) return false;
        return currentAircraft.canBeLoaded(passenger);
    }

    /**
     * Unload a project.passenger from an aircraft.
     * @param passenger the project.passenger to be unloaded.
     * @param aircraftIndex the index of the aircraft.
     */
    public boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
        return passenger.disembark(passenger.aircraft.getCurrentAirport(), passenger.aircraft.getType()) != 0.0;
    }

    /**
     * Refuel an aircraft by a certain amount.
     */
    public void refuel(int aircraftIndex, double fuel) {
        aircraftArrayList.get(aircraftIndex).refuel(fuel);
    }

    /**
     * Create a new aircraft.
     */
    public void createAircraft(int type, Airport initialAirport) {
        Aircraft newAircraft;
        if(type == 1) newAircraft = new JetPassengerAircraft(initialAirport, jetOperationFee);
        else if(type == 2) newAircraft = new PropPassengerAircraft(initialAirport, propOperationFee);
        else if(type == 3) newAircraft = new RapidPassengerAircraft(initialAirport, rapidOperationFee);
        else if(type == 4) newAircraft = new WidebodyPassengerAircraft(initialAirport, widebodyOperationFee);
        else {
            System.err.println("Invalid aircraft type");
            return;
        }
        aircraftArrayList.add(newAircraft);
    }

    /**
     * Create a new project.airport.
     */
    public void createAirport(String name, long ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity) {
        if(Objects.equals(name, "hub")) airportArrayList.add(new HubAirport(1, ID, x, y, fuelCost, operationFee, aircraftCapacity));
        else if(Objects.equals(name, "major")) airportArrayList.add(new MajorAirport(2, ID, x, y, fuelCost, operationFee, aircraftCapacity));
        else if(Objects.equals(name, "regional")) airportArrayList.add(new RegionalAirport(3, ID, x, y, fuelCost, operationFee, aircraftCapacity));
        else {
            System.err.println("Invalid project.airport type");
            return;
        }
    }

    /**
     * Get the current running cost of the project.airline.
     */
    public double getRunningCost() {
        return -operationCost * aircraftArrayList.size();
    }

    /**
     * A utility method to find the Pythagorean distance between two airports.
     */
    public static double findDistance(Airport firstAirport, Airport secondAirport) {
        return Math.sqrt(Math.pow(firstAirport.getX() - secondAirport.getX(), 2) + Math.pow(firstAirport.getY() - secondAirport.getY(), 2));
    }

    /**
     * A lazy method to search Airport in the airportArrayList.
     */
    public static Airport findAirportByIndex(long id) {
        for(int i = 0; i < airportArrayList.size(); i++) {
            if(airportArrayList.get(i).getID() == id) return airportArrayList.get(i);
        }
        System.err.println("Invalid project.airport ID");
        return null;
    }

    /**
     * The method to bring a project.passenger to a destination, and then unload him.
     */
    public void startProcess(String outputFile) {
        Passenger currentPassenger = null;
        Airport currentAirport = null;
        double cost = 0;

        for(int i = 0; i < airportArrayList.size(); i++) {
            if(airportArrayList.get(i).getFirstPassenger() == null) continue;
            currentPassenger = airportArrayList.get(i).getFirstPassenger();
            currentAirport = airportArrayList.get(i);
            break;
        }

        createAircraft(4, currentAirport);
        PassengerAircraft currentAircraft = (PassengerAircraft) aircraftArrayList.get(0);
        currentAirport.addAircraft(currentAircraft);

        try {
            File tmp = new File(outputFile);

            if(!tmp.exists()) {
                tmp.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(tmp.getName());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write("0 " + String.valueOf(currentAirport.getID()) + " 1\n");
            currentAircraft.setAllEconomy();
            bw.write("2 0 450 0 0\n");
            cost = currentAircraft.loadPassenger(currentPassenger);
            profit += cost;
            bw.write("4 " + currentPassenger.getID() + " 0 " + currentAirport.getID()+ "\n");
            cost = currentAirport.getFuelCost(currentAircraft.getMaxFuelFillable());
            profit += cost;
            bw.write("3 0 " + currentAircraft.getMaxFuelFillable() + "\n");
            currentAircraft.fillUp();
            Airport nextAirport = currentPassenger.getFirstDestination();
            cost = currentAircraft.fly(nextAirport);
            cost += getRunningCost();
            profit += cost;
            bw.write("1 " + nextAirport.getID() + " 0\n");
            cost = currentAircraft.unloadPassenger(currentPassenger);
            profit += cost;
            bw.write("5 " + currentPassenger.getID() + " 0 " + nextAirport.getID() + "\n");
            bw.write(String.valueOf(profit));
            bw.close();
            fileWriter.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}