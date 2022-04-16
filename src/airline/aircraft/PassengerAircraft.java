package airline.aircraft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import airport.Airport;
import interfaces.AircraftInterface;
import interfaces.PassengerInterface;
import passenger.Passenger;

public abstract class PassengerAircraft extends Aircraft implements AircraftInterface, PassengerInterface {
    HashMap<Integer, Passenger> passengers;
    HashSet<Integer> economyPassengerIDs, businessPassengerIDs, firstClassPassengerIDs;
    double economySeatArea, businessSeatArea, firstClassSeatArea;
    int economySeats, businessSeats, firstClassSeats;
    int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
    double floorArea;

    protected PassengerAircraft(Airport initialAirport) {
        super(initialAirport);
        passengers = new HashMap<Integer, Passenger>();
        economyPassengerIDs = new HashSet<Integer>();
        businessPassengerIDs = new HashSet<Integer>();
        firstClassPassengerIDs = new HashSet<Integer>();
        economySeatArea = 1.0;
        businessSeatArea = 2.5; //subject to change
        firstClassSeatArea = 5.0;
        economySeats = 0;
        businessSeats = 0;
        firstClassSeats = 0;
        occupiedEconomySeats = 0;
        occupiedBusinessSeats = 0;
        occupiedFirstClassSeats = 0;

    }

    public Collection<Passenger> getPassengers() {
        return (ArrayList<Passenger>) passengers.values(); //won't work
    }

    public boolean loadPassenger(Passenger passenger, int seatClass) { //seatClass: 0 = economy, 1 = business, 2 = first class
        if (seatClass == 0 && !(occupiedEconomySeats < economySeats)) {
            return false;
        } else if (seatClass == 1 && !(occupiedBusinessSeats < businessSeats)) {
            return false;
        } else if (seatClass == 2 && !(occupiedFirstClassSeats < firstClassSeats)) {
            return false;
        }

        if (weight + passenger.getWeight() > maxWeight) {
            return false;
        }

        //need to come back to this
        if (passengers.containsValue(passenger)) {
            return false;
        }
        
        passengers.put(passenger.getID(), passenger);
        return true;
    }

    public boolean disembark(Passenger passenger) {
        return false;
    }

    public double disembarkPassengers(Airport airport) {
        double totalTicketPrice = 0.0;
        for (Passenger passenger : passengers.values()) {
            if (passenger.canDisembark(airport)) {
                double ticketPrice = passenger.disembark(airport);
                passengers.remove(passenger.getId());
                airport.addPassenger(passenger);
                totalTicketPrice += ticketPrice;

            }
        }
        return totalTicketPrice;
    }


    public void printContents() {
        for (Passenger passenger : passengers.values()) {
            System.out.println(passenger.toString());
        }    
    }

    public boolean isFull() {
        return (occupiedEconomySeats == economySeats && occupiedBusinessSeats == businessSeats && occupiedFirstClassSeats == firstClassSeats) ? true : false;
    }

    public boolean isEmpty() {
        return passengers.isEmpty();
    }

    public double getAvailableFloorSpace() {
        return floorArea - (economySeats * economySeatArea + businessSeats * businessSeatArea + firstClassSeats * firstClassSeatArea);
    }

    public boolean setSeats(int economySeats, int businessSeats, int firstClassSeats) {
        if (!isEmpty()) {
            return false;
        }
        if (economySeats * economySeatArea + businessSeats * businessSeatArea + firstClassSeats * firstClassSeatArea > floorArea) {
            return false;
        }
        this.economySeats = economySeats;
        this.businessSeats = businessSeats;
        this.firstClassSeats = firstClassSeats;
        return true;
    }

    public boolean setAllEconomy() {
        if (!isEmpty()) {
            return false;
        }
        economySeats = (int) Math.floor(floorArea / economySeatArea);
        businessSeats = 0;
        firstClassSeats = 0;
        return true;
    }

    public boolean setAllBusiness() {
        if (!isEmpty()) {
            return false;
        }
        businessSeats = (int) Math.floor(floorArea / businessSeatArea);
        economySeats = 0;
        firstClassSeats = 0;
        return true;
    }

    public boolean setAllFirstClass() {
        if (!isEmpty()) {
            return false;
        }
        firstClassSeats = (int) Math.floor(floorArea / firstClassSeatArea);
        economySeats = 0;
        businessSeats = 0;
        return true;
    }

    public boolean setRemainingEconomy() {
        if (!isEmpty()) {
            return false;
        }
        economySeats = (int) Math.floor((floorArea - (businessSeats * businessSeatArea + firstClassSeats * firstClassSeatArea)/ economySeatArea);
        return true;
    }

    public boolean setRemainingBusiness() {
        if (!isEmpty()) {
            return false;
        }
        businessSeats = (int) Math.floor((floorArea - (economySeats * economySeatArea + firstClassSeats * firstClassSeatArea)/ businessSeatArea);
        return true;
    }

    public boolean setRemainingFirstClass() {
        if (!isEmpty()) {
            return false;
        }
        firstClassSeats = (int) Math.floor((floorArea - (economySeats * economySeatArea + businessSeats * businessSeatArea)/ firstClassSeatArea);
        return true;
    }

}
