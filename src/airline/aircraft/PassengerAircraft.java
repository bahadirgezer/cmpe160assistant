package airline.aircraft;

import java.util.HashMap;

import interfaces.Passenger;

public abstract class PassengerAircraft extends Aircraft implements Aircraft, Passenger {
    HashMap<Integer, Passenger> passengers;


    public boolean isEmpty() {
        return passengers.isEmpty();
    }

    public boolean loadPassenger(Passenger passenger) {
        if (weight + passenger.getWeight() > maxWeight) {
            return false;
        }
        //need to come back to this
        passengers.put(passenger.getId(), passenger);
        if (passengers.containsValue(passenger)) {
            return false;
        }
        passengers.put(passenger.getId(), passenger);
        return true;
    }

    public boolean disembark(Passenger passenger) {
        return false;
    }

    public double disembarkPassengers(Airport airport) {
        double totalTicketPrice = 0.0;
        for (Passenger passenger : passengers.values()) {
            if (passenger.canDisembark(airport)) {
                double ticketPrice = passenger.disembark(airport);
                passengers.remove(passenger.getId());
                airport.addPassenger(passenger);
                totalTicketPrice += ticketPrice;

            }
        }
        return totalTicketPrice;
    }


    void printContents() {
        for (Passenger passenger : passengers.values()) {
            System.out.println(passenger.toString());
        }    
    }
}
