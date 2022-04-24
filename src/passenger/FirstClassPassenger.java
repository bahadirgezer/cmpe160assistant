package passenger;

import airport.Airport;
import airport.HubAirport;
import airport.MajorAirport;
import airport.RegionalAirport;

import java.util.ArrayList;

public class FirstClassPassenger extends Passenger {
    public FirstClassPassenger(int ID, double weight, int baggageCount, ArrayList<Airport> destinations) {
        super(ID, weight, baggageCount, destinations);
        seatAssigned = -1;
    }


    protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
        double ticketPrice = previousDestination.getDistance(toAirport) * aircraftTypeMultiplier * connectionMultiplier * seatMultiplier;

        if (previousDestination instanceof HubAirport) {
            if (toAirport instanceof HubAirport) {
                ticketPrice = ticketPrice * 0.5;

            } else if (toAirport instanceof MajorAirport) {
                ticketPrice = ticketPrice * 0.7;

            } else if (toAirport instanceof RegionalAirport) {
                ticketPrice = ticketPrice * 1.0;

            }
        } else if (previousDestination instanceof MajorAirport) {
            if (toAirport instanceof HubAirport) {
                ticketPrice = ticketPrice * 0.6;

            } else if (toAirport instanceof MajorAirport) {
                ticketPrice = ticketPrice * 0.8;

            } else if (toAirport instanceof RegionalAirport) {
                ticketPrice = ticketPrice * 1.8;

            }

        } else if (previousDestination instanceof RegionalAirport) {
            if (toAirport instanceof HubAirport) {
                ticketPrice = ticketPrice * 0.9;

            } else if (toAirport instanceof MajorAirport) {
                ticketPrice = ticketPrice * 1.6;

            } else if (toAirport instanceof RegionalAirport) {
                ticketPrice = ticketPrice * 3.0;

            }
        }

        ticketPrice *= 3.2;
        ticketPrice += (ticketPrice * 0.05 * baggageCount);
        return ticketPrice;
    }

}
