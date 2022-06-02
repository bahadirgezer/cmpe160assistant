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
    public double getAvailableWeight();
    public void setSeats(int economy, int business, int firstClass);
    public boolean setAllEconomy();
    public boolean setAllBusiness();
    public boolean setAllFirstClass();
    public void setRemainingEconomy();
    public boolean setRemainingBusiness();
    public boolean setRemainingFirstClass();
    public double getFullness();

}
