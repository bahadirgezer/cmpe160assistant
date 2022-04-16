package passenger;

import airport.Airport;

public abstract class Passenger {
    ArrayList<Airport> destinations;
    double weight;
    int baggageCount;
    double budget;
    int currentDestination;
    int previousDisembark;
    final int ID;

    public Passenger(int ID, double weight, int baggageCount, double budget, ArrayList<Airport> destinations) {
        this.ID = ID;
        this.weight = weight;
        this.baggageCount = baggageCount;
        this.budget = budget;
        currentDestination = 0;
        this.destinations = destinations;
        previousDisembark = -1;
    }


    public boolean isNextDestination(Airport toAirport) {
        return toAirport.equals(destinations.get(currentDestination));
    }

    private boolean isFutureDestination(Airport toAirport) {
        for (int i = currentDestination; i < destinations.size(); i++) {
            if (destinations.get(i).equals(toAirport)) {
                return true;
            }
        }
        return false;
    }

    public boolean canDisembark(Airport airport) {
        if (!isFutureDestination(airport)) {
            return false;
        }
        return true;
    }

    public abstract double disembark(Airport toAirport) 

    public int findAirport(Airport airport) {
        for (Airport destination : destinations) {
            if (airport.equals(destination)) {
                return destinations.indexOf(destination);
            }
        }
    }

    public boolean transfer(Airport currentAirport) { //returns false if the passenger has reached its final destination
        currentDestination = findAirport(currentAirport);
        if (currentDestination.equals(destinations.size() -1 )) {
            return false;
        }
        return true;
    }

    public int getID() {
        return ID;
    }
}
