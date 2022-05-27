package project.passenger;

import project.airline.aircraft.Aircraft;
import project.airport.Airport;

import java.util.ArrayList;

/**
 * @author Emre KILIC
 *
 */
public class BusinessPassenger extends Passenger {

    public BusinessPassenger(long id, double weight, int baggageCount, ArrayList<Airport> destinations) {
        super(id, weight, baggageCount, destinations);
        type = 1;
    }

    @Override
    protected double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier) {
        double priceWithoutBaggage = previousDisembark.getDistance(airport) * aircraftTypeMultiplier *
                seatMultiplier * connectionMultiplier * getAirportMultiplier() * 1.2;
        return priceWithoutBaggage + (priceWithoutBaggage * 0.05 * getBaggageCount());
    }
}
