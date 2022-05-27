package project.airline.aircraft;

import project.airport.Airport;
import project.interfaces.PassengerInterface;
import project.passenger.*;

import java.util.HashSet;
import java.util.Set;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {

    protected double floorArea;
    private int economySeats, businessSeats, firstClassSeats;
    private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
    Set<Passenger> passengers;

    public PassengerAircraft(Airport currentAirport, double weight, double maxWeight, double fuelCapacity, double floorArea, double fuelConsumption, double aircraftTypeMultiplier, double operationFee, int ID) {
        super(currentAirport, weight, maxWeight, fuelCapacity, fuelConsumption, aircraftTypeMultiplier, operationFee, ID);
        this.floorArea = floorArea;
        this.passengers = new HashSet<>();
    }

    public int getEconomySeats(){ return economySeats; }

    public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
        int seatType = passenger.getSeatType();
        double transferFee = toAircraft.loadPassenger(passenger);
        if(transferFee != toAircraft.getOperationFee()){
            if (seatType == 1) occupiedEconomySeats--;
            else if (seatType == 2) occupiedBusinessSeats--;
            else if (seatType == 3) occupiedFirstClassSeats--;
            passengers.remove(passenger);
            passenger.multiplyConnectionMultiplier(0.8);
        }
        return transferFee;
    }

    public double loadPassenger(Passenger passenger) {
        double loadingFee = operationFee * aircraftTypeMultiplier;
        if(passenger instanceof EconomyPassenger) {
            if (hasEmptySeat(1)) {
                passenger.board(1);
                passengers.add(passenger);
                occupiedEconomySeats++;
                loadingFee *= 1.2;
                return loadingFee;
            }
            else return operationFee;
        }
        else if(passenger instanceof BusinessPassenger) {
            if(hasEmptySeat(2)) {
                passenger.board(2);
                passengers.add(passenger);
                loadingFee *= 1.5;
                occupiedBusinessSeats++;
                return loadingFee;
            }
            else if(hasEmptySeat(1)) {
                passenger.board(1);
                passengers.add(passenger);
                occupiedEconomySeats++;
                loadingFee *= 1.2;
                return loadingFee;
            }
            else return operationFee;
        }
        else if(passenger instanceof FirstClassPassenger || passenger instanceof LuxuryPassenger) {
            if(hasEmptySeat(3)) {
                passenger.board(3);
                passengers.add(passenger);
                occupiedFirstClassSeats++;
                loadingFee *= 2.5;
                return loadingFee;
            }
            else if(hasEmptySeat(2)) {
                passenger.board(2);
                passengers.add(passenger);
                occupiedBusinessSeats++;
                loadingFee *= 1.5;
                return loadingFee;
            }
            else if(hasEmptySeat(1)) {
                passenger.board(1);
                passengers.add(passenger);
                occupiedEconomySeats++;
                loadingFee *= 1.2;
                return loadingFee;
            }
            else return operationFee;
        }
        return operationFee;
    }

    public double unloadPassenger(Passenger passenger) {
        double ticketPrice = passenger.disembark(getCurrentAirport(), getAircraftTypeMultiplier());
        if(ticketPrice != 0){
            passengers.remove(passenger);
            if(passenger.getSeatType() == 1) occupiedEconomySeats--;
            else if(passenger.getSeatType() == 2) occupiedBusinessSeats--;
            else if(passenger.getSeatType() == 3) occupiedFirstClassSeats--;
            return ticketPrice;
        }
        return -operationFee;
    }

    public boolean isFull() {
        int occupied = occupiedEconomySeats + occupiedBusinessSeats + occupiedFirstClassSeats;
        int total = economySeats + businessSeats + firstClassSeats;
        return occupied == total;
    }

    public boolean isFull(int seatType) {
        if(seatType == 1) return occupiedEconomySeats == economySeats;
        if(seatType == 2) return occupiedBusinessSeats == businessSeats;
        if(seatType == 3) return occupiedFirstClassSeats == firstClassSeats;
        return false;
    }

    public boolean isEmpty() {
        int occupied = occupiedEconomySeats + occupiedBusinessSeats + occupiedFirstClassSeats;
        return occupied == 0;
    }

    public boolean hasEmptySeat(int seatType) {
        if(seatType == 1) return occupiedEconomySeats < economySeats;
        if(seatType == 2) return occupiedBusinessSeats < businessSeats;
        if(seatType == 3) return occupiedFirstClassSeats < firstClassSeats;
        return false;
    }

    public double getAvailableWeight() {
        return maxWeight - weight;
    }

    public void setSeats(int economy, int business, int firstClass) {
        economySeats = economy;
        businessSeats = business;
        firstClassSeats = firstClass;
    }

    public boolean setAllEconomy() {
        businessSeats = 0;
        firstClassSeats = 0;
        economySeats = (int) floorArea;
        return true;
    }

    public boolean setAllBusiness() {
        economySeats = 0;
        firstClassSeats = 0;
        businessSeats = (int) floorArea / 3;
        return true;
    }

    public boolean setAllFirstClass() {
        economySeats = 0;
        businessSeats = 0;
        firstClassSeats = (int) floorArea / 8;
        return true;
    }

    public void setRemainingEconomy() {
        economySeats = (int) floorArea - 3 * businessSeats - 8 * firstClassSeats;
    }

    public boolean setRemainingBusiness() {
        economySeats = occupiedEconomySeats;
        firstClassSeats = occupiedFirstClassSeats;
        businessSeats = ((int) floorArea - 8 * firstClassSeats - economySeats) / 3;
        return true;
    }

    public boolean setRemainingFirstClass() {
        economySeats = occupiedEconomySeats;
        businessSeats = occupiedBusinessSeats;
        firstClassSeats = ((int) floorArea - 3 * businessSeats - economySeats) / 8;
        return false;
    }

    public double remainingFloor() {
        return floorArea - (double)occupiedEconomySeats - 3.0 * occupiedBusinessSeats - 8.0 * occupiedFirstClassSeats;
    }

    public boolean addEconomySeat() {
        if(remainingFloor() >= 1.0) {
            economySeats++;
            return true;
        }
        return false;
    }

    public boolean addBusinessSeat() {
        if(remainingFloor() >= 3.0) {
            businessSeats++;
            return true;
        }
        return false;
    }

    public boolean addFirstClassSeat() {
        if(remainingFloor() >= 8.0) {
            firstClassSeats++;
            return true;
        }
        return false;
    }

    public double getFullness() {
        int occupied = occupiedBusinessSeats + occupiedEconomySeats + occupiedFirstClassSeats;
        int total = economySeats + businessSeats + firstClassSeats;
        return (double) occupied / total;
    }
}
