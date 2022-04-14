package airline;

import java.rmi.StubNotFoundException;
import java.util.ArrayList;

import aircraft.Aircraft;
import airport.Airport;
import cargo.Cargo;
import passenger.Passenger;

public class Airline {
    ArrayList<Aircraft> aircrafts;
    int maxNumberOfAircrafts;
    double operationalCost;
    double revenue;
    double expense;
    
    public Airline(int maxNumberOfAircrafts) {
        aircrafts = new ArrayList<Aircraft>();
        this.maxNumberOfAircrafts = maxNumberOfAircrafts;
    }

    public boolean addAircraft(Aircraft aircraft) {
        if (maxNumberOfAircrafts > aircrafts.size()) {
            aircrafts.add(aircraft);            
            return true;
        }
        return false;
    }

    public boolean removeAircraft(Aircraft aircraft) {
        if (!aircrafts.contains(aircraft)) {
            return false;
        }
        if (aircraft.isEmpty()) {
            aircrafts.remove(aircraft);
            return true;
        }
        return false;
    }

    public boolean loadPassenger(Airport airport, Aircraft aircraft, Passenger passenger) {
        if (aircraft.getCurrentAirport() != airport) {
            return false;
        }
        if (aircraft.isFull()) {
            return false;
        }
        if (aircraft.loadPassenger(passenger)) { //need to double check the method and what it looks at
            airport.removePassenger(passenger);
            return true;
        }
        return false;
    }

    public boolean fly(Airport toAirport, Aircraft aircraft) {
        double runningCost = operationalCost * aircrafts.size();
        expense += runningCost;

        if (aircraft.getCurrentAirport() != toAirport) {
            return false;
        }
        if (toAirport.isFull(Aircraft aircraft)) { //needs to check if there is enough space for the aircraft
            return false;
        }
        double potentialRevenue = aircraft.getRevenue(Airport airport);
        if (aircraft.fly(toAirport)) {
            expense += aircraft.getFlightCost();
            revenue += potentialRevenue;
            return true;
        }
        return false;
    }

    public boolean transferPassenger(Aircraft fromAircraft, Aircraft toAircraft, Passenger passenger) {
        if (!fromAircraft.getCurrentAirport().equals(toAircraft.getCurrentAirport())) {
            return false;
        }
        if (!fromAircraft.hasPassenger(passenger)) {
            return false;
        }
        if (toAircraft.isFull()) {
            return false;
        }
        if (toAircraft.loadPassenger(passenger)) {
            fromAircraft.removePassenger(passenger);
            passenger.transfer(fromAircraft.getCurrentAirport());
            return true;
        }
        return false;
    }

    public void printAircraft() {
        for (Aircraft aircraft : aircrafts) {
            System.out.println(aircraft.toString());
        }
    }

}
