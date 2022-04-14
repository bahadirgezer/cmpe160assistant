package aircraft;

import java.util.HashMap;

import interfaces.Passenger;

public abstract class PassengerAircraft extends Aircraft implements Aircraft, Passenger {
    HashMap<Integer, Passenger> passengers;

    boolean isEmpty() {
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

    void printContents() {
        for (Passenger passenger : passengers.values()) {
            System.out.println(passenger.toString());
        }    
    }
}
