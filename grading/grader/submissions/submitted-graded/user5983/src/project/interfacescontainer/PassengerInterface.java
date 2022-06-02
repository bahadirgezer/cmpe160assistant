package project.interfacescontainer;

import project.aircraftcontainer.PassengerAircraft;
import project.passengercontainer.Passenger;

public interface PassengerInterface {
	double transferPassenger(Passenger passenger,PassengerAircraft toAircraft);
	double loadPassenger(Passenger passenger);
	double unloadPassenger(Passenger passenger);
	boolean isFull(); // checks whether the aircraft is full or not
	boolean isFull(int seatType); // checks whether a certain seat type is full or not
	boolean isEmpty(); //checks whether the aircraft is empty or not
	public double getAvailableWeight(); 
	public boolean setSeats(int economy,int business,int firstClass);
	public boolean setAllEconomy();
	public boolean setAllBusiness();
	public boolean setAllFirstClass();
	public boolean setRemainingEconomy();
	public boolean setRemainingBusiness();
	public boolean setRemainingFirstClass();
	public double getFullness(); //Returns the ratio of occupied seats to all seats
	
}
