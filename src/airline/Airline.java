package airline;

import airline.aircraft.Aircraft;
import airline.aircraft.PassengerAircraft;
import airline.aircraft.concrete.JetPassengerAircraft;
import airline.aircraft.concrete.PropPassengerAircraft;
import airline.aircraft.concrete.RapidPassengerAircraft;
import airline.aircraft.concrete.WidebodyPassengerAircraft;
import airport.Airport;
import passenger.Passenger;

import java.util.ArrayList;

import static executable.Main.out;


public class Airline {
    ArrayList<Aircraft> aircrafts;
    int maxNumberOfAircrafts;
    double operationalCost;
    double revenue;
    double expense;

    public Airline(int maxNumberOfAircrafts, double operationalCost) {
        aircrafts = new ArrayList<Aircraft>();
        this.maxNumberOfAircrafts = maxNumberOfAircrafts;
        this.operationalCost = operationalCost; //some constant ;;;;;
        revenue = 0;
        expense = 0;
        out.print("Airline created with aircraft limit: " + maxNumberOfAircrafts + " aircrafts");
    }

    public boolean fly(Airport toAirport, int aircraftIndex) {
        double runningCost = operationalCost * aircrafts.size(); //operational costs increase as an airline has more aircrafts
        expense -= runningCost;
        if (!checkFlight(toAirport, aircraftIndex)) {
            out.println("Invalid flight. Aircraft index: " + aircraftIndex + ", to Airport: " + toAirport.getID());
            return false;
        }
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        if (aircraft.canFly(toAirport)) {
            double flightCost = aircraft.fly(toAirport); //TODO : write flight cost function
            expense -= flightCost;
            out.println("Successful flight. Aircraft index: " + aircraftIndex + ", to Airport: " + toAirport.getID() + ", cost: " + flightCost);
            return true;
        }
        out.println("Invalid flight. Aircraft index: " + aircraftIndex + ", to Airport: " + toAirport.getID());
        return false;
    }

    public boolean checkFlight(Airport toAirport, int aircraftIndex) {
        if (aircraftIndex < 0 || aircraftIndex >= aircrafts.size()) {
            return false;
        }
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        if (toAirport.equals(aircraft.getCurrentAirport())) {
            return false;
        }
        if (toAirport.isFull(aircraft)) {
            return false;
        }

        return true;
    }

    public boolean transferPassenger(Passenger passenger, int fromAircraftIndex, int toAircraftIndex) {
        if (fromAircraftIndex < 0 || fromAircraftIndex >= aircrafts.size()) {
            return false;
        }
        if (toAircraftIndex < 0 || toAircraftIndex >= aircrafts.size()) {
            return false;
        }
        Aircraft fromAircraft = aircrafts.get(fromAircraftIndex);
        if (!(fromAircraft instanceof PassengerAircraft)) {
            return false;
        }
        Aircraft toAircraft = aircrafts.get(toAircraftIndex);
        if (!(toAircraft instanceof PassengerAircraft)) {
            return false;
        }
        PassengerAircraft toPassengerAircraft = (PassengerAircraft) toAircraft;
        PassengerAircraft fromPassengerAircraft = (PassengerAircraft) fromAircraft;

        if (fromPassengerAircraft.canTransferPassenger(passenger, toPassengerAircraft)) {
            double fee = fromPassengerAircraft.transferPassenger(passenger, toPassengerAircraft); // TODO : must check transfer functions in both Aircraft and Passenger
            expense += fee; //TODO : need to check all of these functions!!!!
            return true;
        }
        return false;
    }

    public boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex) {
        if (aircraftIndex < 0 || aircraftIndex >= aircrafts.size()) {
            return false;
        }
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        if (!(aircraft instanceof PassengerAircraft)) {
            return false;
        }
        PassengerAircraft passengerAircraft = (PassengerAircraft) aircraft;
        if (!airport.equals(passengerAircraft.getCurrentAirport())) {
            return false;
        }
        if (passengerAircraft.canLoadPassenger(passenger)) {
            airport.removePassenger(passenger);
            double fee = passengerAircraft.loadPassenger(passenger);
            expense += fee; //TODO : need to check all of these functions!!!!
            return true;
        }
        return false;
    }

    public boolean unloadPassenger(Passenger passenger, int aircraftIndex) {
        if (aircraftIndex < 0 || aircraftIndex >= aircrafts.size()) {
            return false;
        }
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        if (!(aircraft instanceof PassengerAircraft)) {
            return false;
        }
        PassengerAircraft passengerAircraft = (PassengerAircraft) aircraft;
        if (passengerAircraft.canUnloadPassenger(passenger)) {
            passengerAircraft.getCurrentAirport().addPassenger(passenger);
            double ticketPrice = passengerAircraft.unloadPassenger(passenger);
            revenue += ticketPrice; //TODO : need to check all of these functions!!!!
            return true;
        }
        return false;
    }

    public void unloadPassengers(int aircraftIndex) {
        if (aircraftIndex < 0 || aircraftIndex >= aircrafts.size()) {
            return;
        }
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        if (!(aircraft instanceof PassengerAircraft)) {
            return;
        }
        PassengerAircraft passengerAircraft = (PassengerAircraft) aircraft;

        double ticketPrice = passengerAircraft.unloadPassengers();
        revenue += ticketPrice; //TODO : need to check all of these functions!!!!
    }


    //TODO: add fueling functions;
    public boolean refuel(int aircraftIndex, double fuel) {
        if (aircraftIndex < 0 || aircraftIndex >= aircrafts.size()) {
            return false;
        }
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        if (aircraft.canAddFuel(fuel)) {
            double fuelCost = aircraft.addFuel(fuel);
            expense += fuelCost;
            return true;
        }
        return false;
    }

    public boolean fillUp(int aircraftIndex) {
        if (aircraftIndex < 0 || aircraftIndex >= aircrafts.size()) {
            return false;
        }
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        double fuelCost = aircraft.fillUpFuel();
        expense += fuelCost;
        return true;
    }

    public boolean dumpFuel(int aircraftIndex, double fuel) {
        if (aircraftIndex < 0 || aircraftIndex >= aircrafts.size()) {
            return false;
        }
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        return aircraft.dumpFuel(fuel);
    }

    public int addJetPassengerAircraft(Airport initialAirport) {
        if (maxNumberOfAircrafts <= aircrafts.size()) {
            return -1;
        }
        Aircraft aircraft = new JetPassengerAircraft(initialAirport);
        int index = aircrafts.size();
        aircrafts.add(aircraft);
        return index;
    }

    //returns -1 if there is no space, returns the index of the airport, if it can be inserted
    public int addRapidPassengerAircraft(Airport initialAirport) {
        if (maxNumberOfAircrafts <= aircrafts.size()) {
            return -1;
        }
        Aircraft aircraft = new RapidPassengerAircraft(initialAirport);
        int index = aircrafts.size();
        aircrafts.add(aircraft);
        return index;
    }

    public int addWidebodyPassengerAircraft(Airport initialAirport) {
        if (maxNumberOfAircrafts <= aircrafts.size()) {
            return -1;
        }
        Aircraft aircraft = new WidebodyPassengerAircraft(initialAirport);
        int index = aircrafts.size();
        aircrafts.add(aircraft);
        return index;
    }

    public int addPropPassengerAircraft(Airport initialAirport) {
        if (maxNumberOfAircrafts <= aircrafts.size()) {
            return -1;
        }
        Aircraft aircraft = new PropPassengerAircraft(initialAirport);
        int index = aircrafts.size();
        aircrafts.add(aircraft);
        return index;
    }

    public boolean setSeats(int aircraftIndex, int economySeats, int businessSeats, int firstClassSeats) {
        if (aircraftIndex < 0 || aircraftIndex >= aircrafts.size()) {
            return false;
        }
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        if (aircraft instanceof PassengerAircraft) {
            return ((PassengerAircraft) aircraft).setSeats(economySeats, businessSeats, firstClassSeats);
        }
        return false;
    }

    public boolean setAllEconomy(int aircraftIndex) {
        if (aircraftIndex < 0 || aircraftIndex >= aircrafts.size()) {
            return false;
        }
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        if (aircraft instanceof PassengerAircraft) {
            return ((PassengerAircraft) aircraft).setAllEconomy();
        }
        return false;
    }

    public boolean setAllBusiness(int aircraftIndex) {
        if (aircraftIndex < 0 || aircraftIndex >= aircrafts.size()) {
            return false;
        }
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        if (aircraft instanceof PassengerAircraft) {
            return ((PassengerAircraft) aircraft).setAllBusiness();
        }
        return false;
    }

    public boolean setAllFirstClass(int aircraftIndex) {
        if (aircraftIndex < 0 || aircraftIndex >= aircrafts.size()) {
            return false;
        }
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        if (aircraft instanceof PassengerAircraft) {
            return ((PassengerAircraft) aircraft).setAllFirstClass();
        }
        return false;
    }

    public boolean setRemainingEconomy(int aircraftIndex) {
        if (aircraftIndex < 0 || aircraftIndex >= aircrafts.size()) {
            return false;
        }
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        if (aircraft instanceof PassengerAircraft) {
            return ((PassengerAircraft) aircraft).setRemainingEconomy();
        }
        return false;
    }

    public boolean setRemainingBusiness(int aircraftIndex) {
        if (aircraftIndex < 0 || aircraftIndex >= aircrafts.size()) {
            return false;
        }
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        if (aircraft instanceof PassengerAircraft) {
            return ((PassengerAircraft) aircraft).setRemainingBusiness();
        }
        return false;
    }

    public boolean setRemainingFirstClass(int aircraftIndex) {
        if (aircraftIndex < 0 || aircraftIndex >= aircrafts.size()) {
            return false;
        }
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        if (aircraft instanceof PassengerAircraft) {
            return ((PassengerAircraft) aircraft).setRemainingFirstClass();
        }
        return false;
    }
}
