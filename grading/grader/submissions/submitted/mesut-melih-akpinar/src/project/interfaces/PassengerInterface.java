package project.interfaces;

import project.airline.aircraft.PassengerAircraft;
import project.passenger.Passenger;

public interface PassengerInterface {
    double transferPassenger(Passenger passenger, PassengerAircraft toAircraft);
    double loadPassenger(Passenger passenger);
    double unloadPassenger(Passenger passenger);
    boolean isFull();
    boolean isFull(int seatType);
    boolean isEmpty();
    double getAvailableWeight();
    boolean setSeats(int economy, int business, int firstClass);
    boolean setAllEconomy();
    boolean setAllBusiness();
    boolean setAllFirstClass();
    boolean setRemainingEconomy();
    boolean setRemainingBusiness();
    boolean setRemainingFirstClass();
    double getFullness();
}
