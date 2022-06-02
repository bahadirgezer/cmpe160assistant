package project.passenger;

import project.airline.aircraft.Aircraft;
import project.airport.Airport;

import java.util.ArrayList;

/**
 * @author Emre KILIC
 *
 */
public class EconomyPassenger extends Passenger {

    public EconomyPassenger(long id, double weight, int baggageCount, ArrayList<Airport> destinations) {
        super(id, weight, baggageCount, destinations);
        type = 0;
    }

    @Override
    protected double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier) {
        double priceWithoutBaggage = previousDisembark.getDistance(airport) * aircraftTypeMultiplier *
                seatMultiplier * connectionMultiplier * getAirportMultiplier() * 0.6;
        return priceWithoutBaggage + (priceWithoutBaggage * 0.05 * getBaggageCount());
    }
}
