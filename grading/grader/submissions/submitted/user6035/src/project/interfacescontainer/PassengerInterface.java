package project.interfacescontainer;

import project.PassengerContainer.Passenger;
import project.airlines.aircraftcontainer.PassengerAircraft;

public interface PassengerInterface {
	double transferPassenger(Passenger passenger, PassengerAircraft toAircraft);
	double loadPassenger(Passenger passenger);
	double unloadPassenger(Passenger passenger);
	boolean isFull();
	boolean isFull(int seatType);
	boolean isEmpty();
	public double getAvailableWeight();
	public boolean setSeats(int economy, int business, int firstClass);
	public boolean setAllEconomy();
	public boolean setAllBusiness();
	public boolean setAllFirstClass();
	public boolean setRemainingEconomy();
	public boolean setRemainingBusiness();
	public boolean setRemainingFirstClass();
	public double getFullness();
}
