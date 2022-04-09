package airline;

import aircraft.Aircraft;
import airport.Airport;
import cargo.Cargo;
import passenger.Passenger;

public class Airline {
    
    boolean fly(Aircraft aircraft, Airport Destination) {

        return false;
    }

    boolean loadPassenger(Aircraft aircraft, Passenger passenger, int classType) {
        
        return false;
    }

    /**
     * Loads passengers until the plane is full or the airport is empty
     * @param aircraft
     * @return
     */
    boolean loadPassengers(Aircraft aircraft) {

        return false;
    }

    boolean unloadPassenger(Aircraft aircraft, Passenger passenger) {
        
        return false;
    } 

    boolean unloadPassengers(Aircraft aircraft) {

        return false;
    }

    boolean loadCargo(Aircraft aircraft, Cargo cargo) {

        return false;
    }

    boolean unloadCargo(Aircraft aircraft, Cargo cargo) {
        
        return false;
    }
}
