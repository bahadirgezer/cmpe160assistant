package interfaces;

import passenger.Passenger;

public interface PassengerInterface {
    boolean loadPassenger(Passenger passenger);
    boolean unloadPassenger(Passenger passenger);
    boolean loadAllPassenger();
    boolean unloadAllPassenger();
    boolean hasPassenger();
    boolean setSeats(Passenger passenger);

}
