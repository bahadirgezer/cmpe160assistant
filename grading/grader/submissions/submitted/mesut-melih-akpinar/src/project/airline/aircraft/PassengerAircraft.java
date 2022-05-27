package project.airline.aircraft;

import project.airport.Airport;
import project.interfaces.PassengerInterface;
import project.passenger.Passenger;

import java.util.ArrayList;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {
    private ArrayList<Passenger> passengers;
    private int economySeats,businessSeats,firstClassSeats;
    private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;


    public PassengerAircraft(int type, Airport currentAirport, double weight, double maxWeight, double floorArea, double fuelCapacity, double fuelConsumption, double aircraftTypeMultiplier, double aircraftOperationFee) {
        super(type, currentAirport, weight, maxWeight, floorArea, fuelCapacity, fuelConsumption, aircraftTypeMultiplier, aircraftOperationFee);
        passengers = new ArrayList<>();
    }

    public boolean isFull() {
        return passengers.size() >= economySeats + businessSeats + firstClassSeats;
    }

    public boolean isFull(int seatType) {
        if(seatType == 1) return occupiedEconomySeats >= economySeats;
        if(seatType == 2) return occupiedBusinessSeats >= businessSeats;
        if(seatType == 3) return occupiedFirstClassSeats >= firstClassSeats;
        return true;
    }

    @Override
    public double getFullness() {
        return Double.valueOf(passengers.size())/Double.valueOf(economySeats + businessSeats + firstClassSeats);
    }

    @Override
    public boolean canBeLoaded(Passenger passenger) {
        return passenger.canBeLoaded(maxWeight - weight);
    }

    public boolean isEmpty() { return this.getFullness() == 0;}

    public boolean boardPassenger(Passenger passenger) {
        if (isFull()) {
            return false;
        }
        weight += passenger.getWeight();
        passengers.add(passenger);
        passenger.aircraft = this;
        return true;
    }

    public boolean disembarkPassenger(Passenger passenger) {
        if (passengers.contains(passenger)) {
            weight -= passenger.getWeight();
            passengers.remove(passenger);
            passenger.lastEmbark = currentAirport;
            return true;
        }
        return false;
    }

    public double getAvailableWeight() {
        return maxWeight - weight;
    }

    public boolean setSeats(int economy, int business, int firstClass) {
        if(economy + business * 3 + firstClass * 8 > floorArea) return false;
        economySeats = economy;
        businessSeats = business;
        firstClassSeats = firstClass;
        return true;
    }

    public boolean setAllEconomy() {
        economySeats = (int) floorArea;
        businessSeats = 0;
        firstClassSeats = 0;
        return true;
    }

    public boolean setAllBusiness() {
        economySeats = 0;
        businessSeats = (int) floorArea / 3;
        firstClassSeats = 0;
        return true;
    }

    public boolean setAllFirstClass() {
        economySeats = 0;
        businessSeats = 0;
        firstClassSeats = (int) floorArea / 8;
        return true;
    }

    public boolean setRemainingEconomy() {
        double remainingArea = floorArea - economySeats - businessSeats * 3 - firstClassSeats * 8;
        economySeats += remainingArea;
        return false;
    }

    public boolean setRemainingBusiness() {
        double remainingArea = floorArea - economySeats - businessSeats * 3 - firstClassSeats * 8;
        businessSeats += remainingArea / 3;
        return false;
    }

    public boolean setRemainingFirstClass() {
        double remainingArea = floorArea - economySeats - businessSeats * 3 - firstClassSeats * 8;
        firstClassSeats += remainingArea / 8;
        return false;
    }

    boolean isTransferable(Passenger passenger, PassengerAircraft toAircraft) {
        return true;
    }

    public double loadPassenger(Passenger passenger) {
        if (isTransferable(passenger, this)) {
            this.boardPassenger(passenger);
            double seatConstant;
            if (passenger.getType() == 1) seatConstant = 1.2;
            else if (passenger.getType() == 2) seatConstant = 1.5;
            else if (passenger.getType() == 3) seatConstant = 2.5;
            else if (passenger.getType() == 4) seatConstant = 2.5;
            else {
                System.out.println("Invalid aircraft type");
                return 0;
            }
            return -this.aircraftOperationFee * seatConstant * this.aircraftTypeMultiplier;
        }
        return -this.currentAirport.getOperationFee();
    }

    public double unloadPassenger(Passenger passenger) {
        double disembarkationRevenue = passenger.disembark(this.currentAirport, this.aircraftTypeMultiplier);
        if(disembarkationRevenue != 0.0) {
            return disembarkationRevenue * passenger.getSeatConstant();
        }
        return -this.currentAirport.getOperationFee();
    }

    public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
        if (isTransferable(passenger, this)) {
            this.disembarkPassenger(passenger);
            passenger.aircraft = toAircraft;
            toAircraft.boardPassenger(passenger);
            double aircraftMultiplier;
            if (this.getType() == 1) aircraftMultiplier = 1.2;
            else if (this.getType() == 2) aircraftMultiplier = 1.5;
            else if (this.getType() == 3) aircraftMultiplier = 2.5;
            else if (this.getType() == 4) aircraftMultiplier = 2.5;
            else {
                System.out.println("Invalid aircraft type");
                return 0;
            }
            return this.currentAirport.getOperationFee() * aircraftMultiplier;
        }
        return this.currentAirport.getOperationFee();
    }

    protected double fuelCurve(double distanceRatio) {
        return 25.9324 * Math.pow(distanceRatio, 4) - 50.5633 * Math.pow(distanceRatio, 3) + 35.0554 * Math.pow(distanceRatio, 2) - 9.90346 * distanceRatio + 1.97413;
    }
}
