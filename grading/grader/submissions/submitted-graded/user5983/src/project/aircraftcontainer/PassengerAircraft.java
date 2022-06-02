package project.aircraftcontainer;

import java.util.ArrayList;

import project.airportcontainer.Airport;
import project.interfacescontainer.PassengerInterface;
import project.passengercontainer.Passenger;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {
    protected double floorArea;
    private int economySeats, businessSeats, firstClassSeats;
    private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
    private int economyIndex = 1;
    private int businessIndex = 2;
    private int firstClassIndex = 3;
    private int economyArea = 1;
    private int businessArea = 3;
    private int firstClassArea = 8;
    private double remainingArea = floorArea - economyArea * economySeats - businessArea * businessSeats - firstClassArea * firstClassSeats;
    public ArrayList<Passenger> passengers = new ArrayList<>();

    


    public PassengerAircraft(Airport currentAirport, double operationFee) {
        // TODO Auto-generated constructor stub
        super(currentAirport, operationFee);
    }

    @Override
    public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double loadPassenger(Passenger passenger) {
        // TODO Auto-generated method stub


        int seatIndex = 0;

        while (seatIndex < passenger.getAvailableSeats().size() && isFull(passenger.getAvailableSeats().get(seatIndex))) {
            seatIndex++;
        }

        int targetSeatType = passenger.getAvailableSeats().get(seatIndex);

        if (passenger.board(targetSeatType)) {
            passengers.add(passenger);
            setWeight(getWeight() + passenger.getWeight());
            occupySeat(targetSeatType);

            return operationFee * aircraftTypeMultiplier * getLoadingSeatConstant(targetSeatType);
        } else {
            return operationFee;
        }


    }

    @Override
    public double unloadPassenger(Passenger passenger) {
        // TODO Auto-generated method stub

        double revenue = passenger.disembark(currentAirport, aircraftTypeMultiplier);

        if (revenue != 0) {
            //passengers.remove(passenger);
            setWeight(getWeight() - passenger.getWeight());
            freeSeat(passenger.boardedSeat);
            currentAirport.addPassenger(passenger);

            return revenue * getUnloadingSeatConstant(passenger.boardedSeat);
        }


        return -operationFee;
    }

    @Override
    public boolean isFull() {
        // TODO Auto-generated method stub
        if (economySeats == occupiedEconomySeats && businessSeats == occupiedBusinessSeats && firstClassSeats == occupiedFirstClassSeats) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isFull(int seatType) {
        // TODO Auto-generated method stub
        if (seatType == economyIndex) {
            if (economySeats == occupiedEconomySeats) {
                return true;
            } else {
                return false;
            }
        } else if (seatType == businessIndex) {
            if (businessSeats == occupiedBusinessSeats) {
                return true;
            } else {
                return false;
            }
        } else if (seatType == firstClassIndex) {
            if (firstClassSeats == occupiedFirstClassSeats) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }


    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        if (occupiedEconomySeats == 0 && occupiedBusinessSeats == 0 && occupiedFirstClassSeats == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double getAvailableWeight() {
        // TODO Auto-generated method stub
        return maxWeight - weight;
    }

    @Override
    public boolean setSeats(int economy, int business, int firstClass) {
        // TODO Auto-generated method stub
        if (economy * economyArea + business * businessArea * firstClass * firstClassArea > floorArea) {
            return false;
        } else {
            economySeats = economy;
            businessSeats = business;
            firstClassSeats = firstClass;
            return true;
        }

    }

    @Override
    public boolean setAllEconomy() {
        // TODO Auto-generated method stub
        int seatAmount = (int) (floorArea / economyArea);
        economySeats = seatAmount;
        businessSeats = 0;
        firstClassSeats = 0;
        return true;
    }


    @Override
    public boolean setAllBusiness() {
        // TODO Auto-generated method stub
        int seatAmount = (int) (floorArea / businessArea);
        businessSeats = seatAmount;
        economySeats = 0;
        firstClassSeats = 0;
        return true;
    }

    @Override
    public boolean setAllFirstClass() {
        // TODO Auto-generated method stub
        int seatAmount = (int) (floorArea / firstClassArea);
        firstClassSeats = seatAmount;
        economySeats = 0;
        businessSeats = 0;
        return true;
    }

    public int getEconomySeats(){
        return economySeats;
    }

    public int getBusinessSeats(){
        return businessSeats;
    }

    public int getFirstClassSeats(){
        return firstClassSeats;
    }



    @Override
    public boolean setRemainingEconomy() {
        // TODO Auto-generated method stub
        int seatAmount = (int) (remainingArea / economyArea);
        economySeats += seatAmount;
        return true;
    }

    @Override
    public boolean setRemainingBusiness() {
        // TODO Auto-generated method stub
        int seatAmount = (int) (remainingArea / businessArea);
        businessSeats += seatAmount;
        return true;

    }

    @Override
    public boolean setRemainingFirstClass() {
        // TODO Auto-generated method stub
        int seatAmount = (int) (remainingArea / firstClassArea);
        firstClassSeats += seatAmount;
        return true;
    }

    @Override
    public double getFullness() {
        // TODO Auto-generated method stub
        return (double)(((double) (occupiedEconomySeats) +(double) (occupiedBusinessSeats) + (double)(occupiedFirstClassSeats)) / ((double)(economySeats) + (double)(businessSeats) + (double)(firstClassSeats)));
    }

    public double getOperationFee() {
        return operationFee;
    }

    private void occupySeat(int seatType) {
        if (seatType == 1) {
            occupiedEconomySeats += 1;
        } else if (seatType == 2) {
            occupiedBusinessSeats += 1;
        } else if (seatType == 3) {
            occupiedFirstClassSeats += 1;
        }
    }

    private void freeSeat(int seatType) {
        if (seatType == 1) {
            occupiedEconomySeats -= 1;
        } else if (seatType == 2) {
            occupiedBusinessSeats -= 1;
        } else if (seatType == 3) {
            occupiedFirstClassSeats -= 1;
        }
    }

    private double getLoadingSeatConstant(int seatType) {
        if (seatType == 1) {
            return 1.2;
        } else if (seatType == 2) {
            return 1.5;
        } else if (seatType == 3) {
            return 2.5;
        }

        return 0;

    }

    private double getUnloadingSeatConstant(int seatType) {
        if (seatType == 1) {
            return 1;
        } else if (seatType == 2) {
            return 2.8;
        } else if (seatType == 3) {
            return 7.5;
        }

        return 0;

    }
}