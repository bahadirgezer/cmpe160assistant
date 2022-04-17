package passenger;

import airport.Airport;

import java.util.ArrayList;

public class EconomyPassenger extends Passenger {
    public EconomyPassenger(int ID, double weight, int baggageCount, double budget, ArrayList<Airport> destinations) {
        super(ID, weight, baggageCount, budget, destinations);
        seatPreference = 1;
        seatAssigned = 0;
    }

}
