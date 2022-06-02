package project.passenger;

import project.airport.Airport;
import project.airline.Airline;

import java.util.ArrayList;

public class BusinessPassenger extends Passenger {
    public BusinessPassenger(long id, double weight, int baggageCount, ArrayList<Airport> destinations) {
        super(2, id, weight, baggageCount, destinations);
    }

    @Override
    double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
        double distance = Airline.findDistance(lastEmbark, toAirport);
        return (1 + 0.05 * getBaggageCount()) * (distance * aircraftTypeMultiplier * connectionMultiplier * getAirportMultiplier(toAirport) * 1.2);
    }
}