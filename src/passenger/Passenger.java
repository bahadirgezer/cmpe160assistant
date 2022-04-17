package passenger;

import airport.Airport;
import airport.HubAirport;
import airport.MajorAirport;
import airport.RegionalAirport;

import java.util.ArrayList;

public abstract class Passenger {
    private ArrayList<Airport> destinations; //list of airports to visit, starts with the initial airport
    private double weight;
    private int baggageCount;
    private double budget;
    private Airport previousDestination;
    //private Airport previousDisembark;
    private final int ID;

    protected int seatAssigned;

    public Passenger(int ID, double weight, int baggageCount, double budget, ArrayList<Airport> destinations) {
        destinations = new ArrayList<Airport>();
        this.ID = ID;
        this.weight = weight;
        this.baggageCount = baggageCount;
        this.budget = budget;
        this.destinations = destinations;
        previousDestination = destinations.get(0);
        //previousDisembark = destinations.get(0);
        seatAssigned = -1;
    }


    public boolean board(Airport airport, int seatType) {
        if (seatAssigned == -1) {
            return false;
        }
        seatAssigned = seatType;
        previousDestination = airport; //TODO : if already at the airport,

        return true;
    }

    public boolean canDisembark(Airport airport) {
        if (!isFutureDestination(airport)) {
            return false;
        }
        return true;
    }

    public int getSeat() {
        return seatAssigned;
    }

    public double disembark(Airport airport, double aircraftTypeMultiplier) {
        if (!isFutureDestination(airport)) {
            return 0.0;
        }

        double ticketPrice = 0.0;
        if (previousDestination instanceof HubAirport) {
            if (airport instanceof HubAirport) {
                ticketPrice = airport.getDistance(previousDestination) * 0.5 * aircraftTypeMultiplier;

            } else if (airport instanceof MajorAirport) {
                ticketPrice = airport.getDistance(previousDestination) * 0.7 * aircraftTypeMultiplier;

            } else if (airport instanceof RegionalAirport) {
                ticketPrice = airport.getDistance(previousDestination) * 1.0 * aircraftTypeMultiplier;

            }
        } else if (previousDestination instanceof MajorAirport) {
            if (airport instanceof HubAirport) {
                ticketPrice = airport.getDistance(previousDestination) * 0.6 * aircraftTypeMultiplier;

            } else if (airport instanceof MajorAirport) {
                ticketPrice = airport.getDistance(previousDestination) * 0.8 * aircraftTypeMultiplier;

            } else if (airport instanceof RegionalAirport) {
                ticketPrice = airport.getDistance(previousDestination) * 1.8 * aircraftTypeMultiplier;

            }

        } else if (previousDestination instanceof RegionalAirport) {
            if (airport instanceof HubAirport) {
                ticketPrice = airport.getDistance(previousDestination) * 0.9 * aircraftTypeMultiplier;

            } else if (airport instanceof MajorAirport) {
                ticketPrice = airport.getDistance(previousDestination) * 1.6 * aircraftTypeMultiplier;

            } else if (airport instanceof RegionalAirport) {
                ticketPrice = airport.getDistance(previousDestination) * 3.0 * aircraftTypeMultiplier;

            }
        }

        previousDestination = airport;
        seatAssigned = -1;
        return ticketPrice;
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

    //check this
    public boolean transfer(Airport currentAirport) { //returns false if the passenger has reached its final destination
        currentDestination = findAirport(currentAirport);
        if (currentDestination == destinations.size() -1) {
            return false;
        }
        return true;
    }

    public int getID() {
        return ID;
    }

    public double getWeight() {
        return weight;
    }
}
