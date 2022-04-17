package passenger;

import airport.Airport;

import java.util.ArrayList;

public class FirstClassPassenger extends Passenger {
    public FirstClassPassenger(int ID, double weight, int baggageCount, double budget, ArrayList<Airport> destinations) {
        super(ID, weight, baggageCount, budget, destinations);
        seatPreference = 3;
        seatAssigned = 0;
    }

}
