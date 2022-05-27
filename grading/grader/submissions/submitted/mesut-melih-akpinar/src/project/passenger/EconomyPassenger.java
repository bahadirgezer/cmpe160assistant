package project.passenger;

import project.airline.Airline;
import project.airport.Airport;

import java.util.ArrayList;

public class EconomyPassenger extends Passenger {
    public EconomyPassenger(long id, double weight, int baggageCount, ArrayList<Airport> destinations) {
        super(1, id, weight, baggageCount, destinations);
    }

    @Override
    double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
        double distance = Airline.findDistance(lastEmbark, toAirport);
        return (1 + 0.05 * getBaggageCount()) * (distance * aircraftTypeMultiplier * connectionMultiplier * getAirportMultiplier(toAirport) * 0.6);
    }
}