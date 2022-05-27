package project.passenger;

import project.airline.aircraft.Aircraft;
import project.airport.Airport;

import java.util.ArrayList;

/**
 * @author Emre KILIC
 *
 */
public class LuxuryPassenger extends Passenger{

    public LuxuryPassenger(long id, double weight, int baggageCount, ArrayList<Airport> destinations) {
        super(id, weight, baggageCount, destinations);
        type = 3;
    }

    @Override
    protected double calculateTicketPrice(Airport airport, double aircraftTypeMultiplier) {
        double priceWithoutBaggage = previousDisembark.getDistance(airport) * aircraftTypeMultiplier *
                seatMultiplier * connectionMultiplier * getAirportMultiplier() * 15;
        return priceWithoutBaggage + (priceWithoutBaggage * 0.05 * getBaggageCount());
    }
}
