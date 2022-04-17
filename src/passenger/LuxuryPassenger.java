package passenger;

import airport.Airport;

import java.util.ArrayList;

public class LuxuryPassenger extends Passenger {
    public LuxuryPassenger(int ID, double weight, int baggageCount, double budget, ArrayList<Airport> destinations) {
        super(ID, weight, baggageCount, budget, destinations);
        seatAssigned = -1;
    }
}
