package interfaces;

public interface Passenger {
    boolean loadPassenger(Passenger passenger);
    boolean unloadPassenger(Passenger passenger);
    boolean loadAllPassenger();
    boolean unloadAllPassenger();
    boolean hasPassenger();
    boolean setSeats(Passenger passenger);

}
