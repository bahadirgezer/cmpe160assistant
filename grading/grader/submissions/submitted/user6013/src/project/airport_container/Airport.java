package project.airport_container;

import java.util.ArrayList;

import project.airline_container.aircraft_container.Aircraft;
import project.passenger_container.Passenger;

public abstract class Airport {
	
	private final int ID;
	private final double x, y;
	protected double fuelCost;
	protected double operationFee;
	protected int aircraftCapacity;
	protected int aircraftNo =0;
	public int getAircraftNo() {
		return aircraftNo;
	}
	public void setAircraftNo(int aircraftNo) {
		this.aircraftNo = aircraftNo;
	}
	public final int type;
	protected ArrayList <Passenger> pass = new ArrayList<>();
		
	public ArrayList<Passenger> getPass() {
		return pass;
	}
	public abstract double departAircraft(Aircraft aircraft);
	public abstract double landAircraft(Aircraft aircraft);
	public abstract double getDepartureCost(Aircraft aircraft);
	public abstract double getLandingCost(Aircraft aircraft);
	
	Airport(int a,double b,double c,double d,double e,int f,int g){
		this.ID = a;
		this.x = b;
		this.y = c;
		this.fuelCost =d;
		this.operationFee=e;
		this.aircraftCapacity =f;
		this.type = g;
	}

	public static double getdistance(Airport currentAirport, Airport toAirport) {
		// TODO Auto-generated method stub
		return Math.pow(Math.pow(currentAirport.getX()-toAirport.getX(), 2)+Math.pow(currentAirport.getY()-toAirport.getY(), 2), 0.5);
	}
	public boolean passenger_check(Passenger passenger) {
		return this.pass.contains(passenger);
	}
	public double getOperationFee() {
		return operationFee;
	}
	public  double getaircraftRatio() {
		return 1.0*aircraftNo/ aircraftCapacity;
	}
	public double getX() {
		return x;
	}
	public double getNo_of_Passengers() {
		return pass.size();
	}
	public double getY() {
		return y;
	}
	public int getID() {
		return this.ID;
	}
	public boolean equals(Airport other) {
		if(other.getID() == getID()) {
			return true;
		}else {
			return false;
		}
	}
	public double getFuelCost() {
		return fuelCost;
	}
	public void addPass(Passenger a) {
		this.pass.add(a);
	}
	public void removePass(Passenger a) {
		this.pass.remove(this.pass.indexOf(a));
	}
	
	
}
