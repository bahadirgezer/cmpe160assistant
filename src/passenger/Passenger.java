package passenger;

import airport.Airport;

import java.util.ArrayList;

public abstract class Passenger {
    private ArrayList<Airport> destinations; //list of airports to visit, starts with the initial airport
    private double weight;
    protected int baggageCount;
    protected Airport previousDestination;
    //private Airport previousDisembark;
    private final int ID;

    protected int seatAssigned;
    protected double connectionMultiplier;
    protected double seatMultiplier;

    public Passenger(int ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
        destinations = new ArrayList<Airport>();
        this.ID = ID;
        this.weight = weight;
        this.baggageCount = baggageCount;
        this.destinations = destinations;
        previousDestination = destinations.get(0);
        //previousDisembark = destinations.get(0);
        seatAssigned = -1;
        connectionMultiplier = 1.0;
        seatMultiplier = 1.0;
    }

    public boolean connection(Airport airport, int seatType) {
        if (seatAssigned == -1) {
            return false;
        }

        if (seatAssigned == 1) {
            seatMultiplier *= 0.6;
        } else if (seatAssigned == 2) {
                seatMultiplier *= 1.2;
        } else if (seatAssigned == 3) {
                seatMultiplier *= 3.2;
        }

        seatAssigned = seatType;
        connectionMultiplier *= 0.8;

        return true;
    }

    public boolean board(Airport airport, int seatType) {
        if (seatAssigned != -1) { //TODO check this
            return false;
        }
        seatAssigned = seatType;
        if (seatAssigned == 1) {
            seatMultiplier = 0.6;
        } else if (seatAssigned == 2) {
            seatMultiplier = 1.2;
        } else if (seatAssigned == 3) {
            seatMultiplier = 3.2;
        }

        previousDestination = airport; //TODO : if already at the airport,

        return true;
    }

    public boolean canDisembark(Airport airport) {
        if (!isFutureDestination(airport)) {
            return false;
        }
        return true;
    }


    abstract double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier);

    public double disembark(Airport airport, double aircraftTypeMultiplier) {
        if (!isFutureDestination(airport)) {
            return 0.0;
        }

        double ticketPrice = calculateTicketPrice(airport, aircraftTypeMultiplier);
        previousDestination = airport;
        seatAssigned = -1;
        seatMultiplier = 1.0;
        return ticketPrice;
    }

    public int getSeat() {
        return seatAssigned;
    }

    public boolean isFutureDestination(Airport airport) {
        boolean after = false;
        for (Airport destination : destinations) {
            if (destination.equals(previousDestination)) {
                after = true;
            }
            if (destination.equals(airport)) {
                return after; // TODO : check correctness!!
            }
        }
        return false;
    }

    public int findAirport(Airport airport) { //checkThis
        for (Airport destination : destinations) {
            if (airport.equals(destination)) {
                return destinations.indexOf(destination);
            }
        }
        return -1;
    }

    public int getID() {
        return ID;
    }

    public double getWeight() {
        return weight;
    }
}
