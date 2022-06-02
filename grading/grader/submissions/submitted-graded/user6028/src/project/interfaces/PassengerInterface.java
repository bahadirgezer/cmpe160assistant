package project.interfaces;
import project.airline.aircraft.PassengerAircraft;
import project.passenger.Passenger;

public interface PassengerInterface {
	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft);
	public double loadPassenger(Passenger passenger);
	public double unloadPassenger(Passenger passenger);
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
