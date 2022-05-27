package project.interfaces_container;

import project.airline_container.aircraft_container.PassengerAircraft;
import project.passenger_container.Passenger;

public interface PassengerInterface {

	double loadPassenger(Passenger passenger);
	double unloadPassenger(Passenger passenger);

	double transferPassenger(Passenger passenger, PassengerAircraft toAircraft);
	
	//Checks whether the aircraft is full or not
	boolean isFull();
	// Checks whether a certain seat type is full or not.
	boolean isFull(int seatType);
	
	// Checks whether the aircraft is empty or not.
	boolean isEmpty();

	// Returns the leftover weight capacity of the aircraft.
	public double getAvailableWeight();
	
	// Returns the ratio of occupied seats to all seats.
	public double getFullness();
	
	public boolean setSeats(int economy, int business, int firstClass);
	
	// Sets every seat to economy. 
	public boolean setAllEconomy();
	
	// Sets every seat to business. 
	public boolean setAllBusiness();
	
	// Sets every seat to first class.
	public boolean setAllFirstClass();
	
	// Does not change previously set seats, sets the remaining to economy.
	public boolean setRemainingEconomy();
	
	// Does not change previously set seats, sets the remaining to business.
	public boolean setRemainingBusiness();

	// Does not change previously set seats, sets the remaining to first class.
	public boolean setRemainingFirstClass();

}
