package project.interfacescontainer;

import project.airlinecontainer.aircraftcontainer.PassengerAircraft;
import project.passengercontainer.Passenger;

public interface PassengerInterface {
	double transferPassenger(Passenger passenger, PassengerAircraft toAircraft);
	double loadPassenger(Passenger passenger);
	double unloadPassenger(Passenger passenger);
	boolean isFull(); //Check if the aircraft is full
	boolean isFull(int seatType); //Check if a certain seat type is full or not;
	boolean isEmpty(); //Check if the aircraft is empty
	public double getAvailableWeight(); //Return how much weight capacity is left
	public boolean setSeats(int economy, int business, int firstClass);
	public boolean setAllEconomy(); //Set all seats to economy
	public boolean setAllBusiness(); //Set all seats to business
	public boolean setAllFirstClass(); //Set all seats to first class
	public boolean setRemainingEconomy(); //Set remaining seats to economy
	public boolean setRemainingBusiness(); //Set remaining seats to business
	public boolean setRemainingFirstClass(); //Set remaining seats to first class
	public double getFullness(); //Return the ratio of occupied seats to all seats
}
