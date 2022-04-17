package passenger;

import airport.Airport;

import java.util.ArrayList;

public class BusinessPassenger extends Passenger {
    public BusinessPassenger(int ID, double weight, int baggageCount, double budget, ArrayList<Airport> destinations) {
        super(ID, weight, baggageCount, budget, destinations);
        seatPreference = 2;
        seatAssigned = 0;
    }

}
