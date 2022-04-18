package airline.aircraft;

import java.util.HashMap;
import java.util.HashSet;

import airport.Airport;
import interfaces.AircraftInterface;
import interfaces.PassengerInterface;
import passenger.BusinessPassenger;
import passenger.EconomyPassenger;
import passenger.LuxuryPassenger;
import passenger.Passenger;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {
    private HashMap<Integer, Passenger> passengers;
    private double economySeatArea, businessSeatArea, firstClassSeatArea;
    private int economySeats, businessSeats, firstClassSeats;
    private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
    protected double floorArea;


    protected PassengerAircraft(Airport initialAirport) {
        super(initialAirport);
        passengers = new HashMap<Integer, Passenger>();
        //economyPassengerIDs = new HashSet<Integer>();
        //businessPassengerIDs = new HashSet<Integer>();
        //firstClassPassengerIDs = new HashSet<Integer>();
        economySeatArea = 1.0;
        businessSeatArea = 3.0; //subject to change
        firstClassSeatArea = 8.0;
        economySeats = 0;
        businessSeats = 0;
        firstClassSeats = 0;
        occupiedEconomySeats = 0;
        occupiedBusinessSeats = 0;
        occupiedFirstClassSeats = 0;

    }

    public boolean canTransferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
        if (!toAircraft.getCurrentAirport().equals(currentAirport)) {
            return false;
        }
        if (toAircraft.canLoadPassenger(passenger)) {
            passengers.remove(passenger.getID());
            toAircraft.loadPassenger(passenger);
            return true;
        }

        return false;
    }

    public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
        if (!toAircraft.getCurrentAirport().equals(currentAirport)) {
            return -operationFee;
        }
        if (toAircraft.canLoadPassenger(passenger)) {
            passengers.remove(passenger.getID());
            double loadingFee = toAircraft.loadPassenger(passenger);
            return loadingFee;
        }
        return -operationFee;
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

    public boolean canUnloadPassenger(Passenger passenger) {
        if (!passenger.canDisembark(currentAirport)) {
            return false;
        }
        return true;
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

    public double unloadPassengers() {
        double totalTicketPrice = 0.0;
        for (Passenger passenger : passengers.values()) {
            if (canUnloadPassenger(passenger)) {
                totalTicketPrice += this.unloadPassenger(passenger);
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

    public double getAircraftTypeMultiplier() {
        return aircraftTypeMultiplier;
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
