package project.interfaces_container;

import project.airline_container.aircraft_container.PassengerAircraft;
import project.passenger_container.Passenger;

public interface PassengerInterface {
	double transferPassenger(Passenger passenger, PassengerAircraft toAircraft);
	double loadPassenger(Passenger passenger);
	double unloadPassenger(Passenger passenger);
	boolean isFull();//	Checks whether the aircraft is full or not

	boolean isFull(int seatType); //Checks whether a certain seat type is full or not.

	boolean isEmpty(); // Checks whether the aircraft is empty or not.

	public double getAvailableWeight();// Returns the leftover weight capacity of the aircraft.

	public boolean setSeats(int economy, int business, int firstClass);
	public boolean setAllEconomy();//Sets every seat to economy.

	public boolean setAllBusiness();//Sets every seat to business.

	public boolean setAllFirstClass();//Sets every seat to first class.

	public boolean setAllEmpty();
	
	public boolean setRemainingEconomy();//Does not change previously set seats, sets the remaining to economy.

	public boolean setRemainingBusiness();//Does not change previously set seats, sets the remaining to business.


	public boolean setRemainingFirstClass();//Does not change previously set seats, sets the remaining to first class.
	
	public double getFullness();//Returns the ratio of occupied seats to all seats.
}
