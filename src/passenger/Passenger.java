package passenger;

import airport.Airport;

public abstract class Passenger {
    ArrayList<Airport> destinations;
    double weight;
    int baggageCount;
    double budget;
    int currentDestination;

    public Passenger(double weight, int baggageCount, double budget, ArrayList<Airport> destinations) {
        this.weight = weight;
        this.baggageCount = baggageCount;
        this.budget = budget;
        currentDestination = 0;
        this.destinations = destinations;
    }


    public boolean isNextDestination(Airport toAirport) {
        return toAirport.equals(destinations.get(currentDestination));
    }

    public boolean isFutureDestination(Airport toAirport) {
        for (int i = currentDestination; i < destinations.size(); i++) {
            if (destinations.get(i).equals(toAirport)) {
                return true;
            }
        }
        return false;
    }

    public boolean disembark(Airport toAirport) {
        if (!isFutureDestination(toAirport)) {
            return false;
        }
        currentDestination++;
        return true;
    }

    public int findAirport(Airport airport) {
        for (Airport destination : destinations) {
            if (airport.equals(destination)) {
                return destinations.indexOf(destination);
            }
        }
    }

    public void transfer(Airport currentAirport) {
        currentDestination = findAirport(currentAirport);
    }
}
