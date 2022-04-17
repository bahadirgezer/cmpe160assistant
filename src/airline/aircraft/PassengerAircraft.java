package airline.aircraft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import airport.Airport;
import interfaces.AircraftInterface;
import interfaces.PassengerInterface;
import passenger.BusinessPassenger;
import passenger.EconomyPassenger;
import passenger.LuxuryPassenger;
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
        //economyPassengerIDs = new HashSet<Integer>();
        //businessPassengerIDs = new HashSet<Integer>();
        //firstClassPassengerIDs = new HashSet<Integer>();
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

    public boolean canLoadPassenger(Passenger passenger) {
        if (passengers.containsValue(passenger)) {
            return false;
        }
        if (weight + passenger.getWeight() > maxWeight) {
            return false;
        }

        if (passenger instanceof LuxuryPassenger) {
            if (occupiedFirstClassSeats < firstClassSeats || occupiedBusinessSeats < businessSeats || occupiedEconomySeats < economySeats) {
                return true;
            } else {
                return false;
            }
        } else if (passenger instanceof BusinessPassenger) {
            if (occupiedBusinessSeats < businessSeats || occupiedEconomySeats < economySeats){
                return true;
            } else {
                return false;
            }
        } else if (passenger instanceof EconomyPassenger) {
            if (occupiedEconomySeats < economySeats) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public double loadPassenger(Passenger passenger) {
        if (passengers.containsValue(passenger)) {
            return -operationFee;
        }
        if (weight + passenger.getWeight() > maxWeight) {
            return -operationFee;
        }

        if (passenger instanceof LuxuryPassenger) {
            if (occupiedFirstClassSeats < firstClassSeats) {
                passenger.board(currentAirport,3);
                passengers.put(passenger.getID(), passenger);
                occupiedFirstClassSeats++;
                return -operationFee * aircraftTypeMultiplier * 2.5;

            } else if (occupiedBusinessSeats < businessSeats) {
                passenger.board(currentAirport,2);
                passengers.put(passenger.getID(), passenger);
                occupiedBusinessSeats++;
                return -operationFee * aircraftTypeMultiplier * 1.5;

            } else if (occupiedEconomySeats < economySeats) {
                passenger.board(currentAirport, 1);
                passengers.put(passenger.getID(), passenger);
                occupiedEconomySeats++;
                return -operationFee * aircraftTypeMultiplier * 1.2;

            } else {
                return -operationFee;
            }

        } else if (passenger instanceof BusinessPassenger) {
            if (occupiedBusinessSeats < businessSeats) {
                passenger.board(currentAirport, 2);
                passengers.put(passenger.getID(), passenger);
                occupiedBusinessSeats++;
                return -operationFee * aircraftTypeMultiplier * 1.5;

            } else if (occupiedEconomySeats < economySeats){
                passenger.board(currentAirport, 1); // TODO : should return operation fee if it cannot board
                passengers.put(passenger.getID(), passenger);
                occupiedEconomySeats++;
                return -operationFee * aircraftTypeMultiplier * 1.2;

            } else {
                return -operationFee;
            }
        } else if (passenger instanceof EconomyPassenger) {
            if (occupiedEconomySeats < economySeats) {
                passenger.board(currentAirport, 1);
                passengers.put(passenger.getID(), passenger);
                occupiedEconomySeats++;
                return -operationFee *  aircraftTypeMultiplier * 1.2;

            } else {
                return -operationFee;
            }
        }

        return -operationFee;
    }


    //passenger can disembark in the same airport
    public double unloadPassenger(Passenger passenger) {
        if (!passenger.canDisembark(currentAirport)) {
            return -operationFee;
        }

        double ticketPrice = 0;
        if (passenger.getSeat() == 1) {
            occupiedEconomySeats--;
            ticketPrice = 1.0;
        } else if (passenger.getSeat() == 2) {
            occupiedBusinessSeats--;
            ticketPrice = 2.8;
        } else if (passenger.getSeat() == 3) {
            occupiedFirstClassSeats--;
            ticketPrice = 7.5;
        }
        ticketPrice = ticketPrice * passenger.disembark(currentAirport, aircraftTypeMultiplier);
        passengers.remove(passenger.getID());
        return ticketPrice;
    }




    public boolean loadPassenger(Passenger passenger, int seatClass) { //seatClass: 0 = economy, 1 = business, 2 = first class
        if (seatClass == 0 && (occupiedEconomySeats < economySeats)) {
            occupiedEconomySeats += 1;
        } else if (seatClass == 1 && (occupiedBusinessSeats < businessSeats)) {
            occupiedBusinessSeats += 1;
        } else if (seatClass == 2 && (occupiedFirstClassSeats < firstClassSeats)) {
            occupiedFirstClassSeats += 1;
        } else {
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
                passengers.remove(passenger.getID());
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
        economySeats = (int) Math.floor((floorArea - (businessSeats * businessSeatArea + firstClassSeats * firstClassSeatArea)/ economySeatArea));
        return true;
    }

    public boolean setRemainingBusiness() {
        if (!isEmpty()) {
            return false;
        }
        businessSeats = (int) Math.floor((floorArea - (economySeats * economySeatArea + firstClassSeats * firstClassSeatArea)/ businessSeatArea));
        return true;
    }

    public boolean setRemainingFirstClass() {
        if (!isEmpty()) {
            return false;
        }
        firstClassSeats = (int) Math.floor((floorArea - (economySeats * economySeatArea + businessSeats * businessSeatArea)/ firstClassSeatArea));
        return true;
    }

}
