package project.airline.aircraft;

import project.interfaces.PassengerInterface;
import project.passenger.Passenger;

import java.util.LinkedList;

/**
 * @author Emre KILIC
 *
 */
public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {
    protected double floorArea;
    private int economySeats, businessSeats, firstClassSeats;
    private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
    private final LinkedList<Passenger> passengers = new LinkedList<>();

    protected double bathtubCoefficient(double distanceRatio){
        return 25.9324*Math.pow(distanceRatio,4)-50.5633*Math.pow(distanceRatio,3)
                +35.0554*Math.pow(distanceRatio,2)-9.90346*distanceRatio+1.97413;
    }

    @Override
    public double getAvailableWeight() {
        return maxWeight - weight;
    }

    @Override
    public boolean isEmpty() {
        return occupiedFirstClassSeats == 0 && occupiedBusinessSeats == 0 && occupiedEconomySeats == 0;
    }

    @Override
    public boolean isFull() {
        return occupiedFirstClassSeats == firstClassSeats && occupiedBusinessSeats == businessSeats
                && occupiedEconomySeats == economySeats;
    }

    @Override
    public boolean isFull(int seatType) {
        return switch (seatType) {
            case 0 -> occupiedEconomySeats == economySeats;
            case 1 -> occupiedBusinessSeats == businessSeats;
            case 2 -> occupiedFirstClassSeats == firstClassSeats;
            default -> throw new RuntimeException("Seat Type should be 0, 1 or 2");
        };
    }

    @Override
    public boolean setSeats(int economy, int business, int firstClass) {
        if (economy + 3*business + 8*firstClass <= getUnoccupiedFloorArea()){
            economySeats = economy + occupiedEconomySeats;
            businessSeats = business + occupiedBusinessSeats;
            firstClassSeats = firstClass + occupiedFirstClassSeats;
            return true;
        }
        return false;
    }

    @Override
    public boolean setAllBusiness() {
        if (occupiedEconomySeats == 0 && occupiedFirstClassSeats == 0){
            businessSeats += economySeats/3 + (8*firstClassSeats)/3;
            economySeats = 0;
            firstClassSeats = 0;
            setRemainingBusiness();
            return true;
        }
        return false;
    }

    @Override
    public boolean setAllEconomy() {
        if (occupiedBusinessSeats == 0 && occupiedFirstClassSeats == 0){
            economySeats += 3*businessSeats + 8*firstClassSeats;
            businessSeats = 0;
            firstClassSeats = 0;
            setRemainingEconomy();
            return true;
        }
        return false;
    }

    @Override
    public boolean setAllFirstClass() {
        if (occupiedBusinessSeats == 0 && occupiedEconomySeats == 0){
            firstClassSeats += (3*businessSeats)/8 + economySeats/8;
            businessSeats = 0;
            economySeats = 0;
            setRemainingFirstClass();
            return true;
        }
        return false;
    }

    @Override
    public boolean setRemainingEconomy() {
        int empty = getRemainingFloorArea();
        if (empty > 0){
            economySeats += empty;
            return true;
        }
        return false;
    }

    @Override
    public boolean setRemainingBusiness() {
        int empty = getRemainingFloorArea();
        if (empty > 0){
            businessSeats += (empty/3);
            return true;
        }
        return false;
    }

    @Override
    public boolean setRemainingFirstClass() {
        int empty = getRemainingFloorArea();
        if (empty > 0){
            firstClassSeats += (empty/8);
            return true;
        }
        return false;
    }

    public boolean setSeat(int type, int seat){
        switch (type){
            case 0:
                if (seat <= getRemainingFloorArea()){
                    economySeats = seat + occupiedEconomySeats;
                    return true;
                }else {
                    return false;
                }
            case 1:
                if (3*seat <= getRemainingFloorArea()){
                    businessSeats = seat + occupiedBusinessSeats;
                    return true;
                }else {
                    return false;
                }
            case 2:
                if (8*seat <= getRemainingFloorArea()){
                    firstClassSeats = seat + occupiedFirstClassSeats;
                    return true;
                }else {
                    return false;
                }
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public int getUnoccupiedFloorArea(){
        return (int) (floorArea - (occupiedEconomySeats + 3*occupiedBusinessSeats + 8*occupiedFirstClassSeats));
    }

    private int getRemainingFloorArea(){
        return (int) (floorArea - (economySeats + 3*businessSeats + 8*firstClassSeats));
    }

    @Override
    public double loadPassenger(Passenger passenger){
        int seatType = getMaxSeat(this, passenger);
        boolean canLoad = canLoad(passenger) == 0 && passenger.board(seatType);
        assert canLoad;
        passengers.add(passenger);
        weight += passenger.getWeight();
        occupySeat(seatType);
        double loadingFee = operationFee * aircraftTypeMultiplier;
        return loadingFee * getSeatConstantForLoad(seatType);
    }

    @Override
    public double unloadPassenger(Passenger passenger) {
        int seatType = passenger.getSeatType();
        double disembark = passenger.disembark(currentAirport, aircraftTypeMultiplier);
        if (disembark != 0 && passengers.contains(passenger)){
            passengers.remove(passenger);
            weight -= passenger.getWeight();
            emptySeat(seatType);
            return disembark * getSeatConstantForUnload(seatType);
        }
        return operationFee*-1;
    }

    @Override
    public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
        int seatType = getMaxSeat(toAircraft, passenger), pastSeat = passenger.getSeatType();
        double weight = passenger.getWeight();
        if (toAircraft.canLoad(passenger) == 0 && passenger.connection(seatType)){
            passengers.remove(passenger);
            this.weight -= weight;
            emptySeat(pastSeat);
            toAircraft.passengers.add(passenger);
            toAircraft.weight += weight;
            toAircraft.occupySeat(seatType);
            double loadingFee = operationFee * aircraftTypeMultiplier;
            return loadingFee * getSeatConstantForLoad(seatType);
        }
        return operationFee;
    }

    /**
     * @return 0 if passenger can load;<p>
     *         1 if there is weight problem;<p>
     *         2 if there is not enough seat;<p>
     *         3 if passenger is already in aircraft;
     */
    public int canLoad(Passenger passenger){
        int maxSeat = getMaxSeat(this, passenger);
        if (passengers.contains(passenger)){
            return 3;
        }else if (maxSeat < 0){
            return 2;
        }else if ((weight + passenger.getWeight()) > maxWeight){
            return 1;
        }else {
            return 0;
        }
    }

    private int getMaxSeat(PassengerAircraft aircraft, Passenger passenger) {
        int type = passenger.getType();
        if (aircraft.occupiedFirstClassSeats < aircraft.firstClassSeats && type >= 2){
            return 2;
        }else if (aircraft.occupiedBusinessSeats < aircraft.businessSeats && type >= 1){
            return 1;
        }else if (aircraft.occupiedEconomySeats < aircraft.economySeats){
            return 0;
        }else {
            return -1;
        }
    }

    public int[] getFinalSeats(){
        return new int[]{economySeats, businessSeats, firstClassSeats};
    }

    private void occupySeat(int seatType){
        switch (seatType) {
            case 0 -> occupiedEconomySeats++;
            case 1 -> occupiedBusinessSeats++;
            case 2 -> occupiedFirstClassSeats++;
            default -> throw new IllegalStateException("Unexpected value: " + seatType);
        }
    }

    private void emptySeat(int seatType){
        switch (seatType) {
            case 0 -> occupiedEconomySeats--;
            case 1 -> occupiedBusinessSeats--;
            case 2 -> occupiedFirstClassSeats--;
            default -> throw new IllegalStateException("Unexpected value: " + seatType);
        }
    }

    private double getSeatConstantForLoad(int seatType){
        return switch (seatType){
            case 0 -> 1.2;
            case 1 -> 1.5;
            case 2 -> 2.5;
            default -> throw new IllegalStateException("Unexpected value: " + seatType);
        };
    }

    private double getSeatConstantForUnload(int seatType){
        return switch (seatType){
            case 0 -> 1.0;
            case 1 -> 2.8;
            case 2 -> 7.5;
            default -> throw new IllegalStateException("Unexpected value: " + seatType);
        };
    }

    public LinkedList<Passenger> getPassengers() {
        return passengers;
    }

    @Override
    public double getFullness() {
        return (double) (occupiedBusinessSeats+occupiedEconomySeats+occupiedFirstClassSeats)/
                (firstClassSeats+businessSeats+economySeats);
    }

    public boolean hasPassenger(Passenger passenger) {
        return passengers.contains(passenger);
    }
}
