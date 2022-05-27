package project.airline_container.aircraft_container;

import java.util.ArrayList;


import project.interfaces_container.PassengerInterface;
import project.passenger_container.Passenger;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {
	protected double floorArea;
	protected double seatArea=0;
	protected double seatArea2=0;
	private int[] seat_types = new int[4];
	private int[] occupiedSeats = new int[4];
	private int totalseats= seat_types[0]+seat_types[1]+seat_types[2]+seat_types[3];
	private int totaloccupiedseats = occupiedSeats[0]+occupiedSeats[1]+occupiedSeats[2]+occupiedSeats[3];
	private int ec_area=1,bus_area=3,first_area=8;
	private ArrayList <Passenger> onboard_pas = new ArrayList<>();
	public ArrayList<Passenger> getOnboard_pas() {
		return onboard_pas;
	}
	protected double operationfee;
	public int getTotalseats() {
		return totalseats;
	}
	public int getTotaloccupiedseats() {
		return totaloccupiedseats;
	}
	@Override
	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		// TODO Auto-generated method stub
		if(this.currentAirport.equals(toAircraft.currentAirport)&&toAircraft.seat_types[passenger.getSeatType()]>toAircraft.occupiedSeats[passenger.getSeatType()] && toAircraft.maxWeight-toAircraft.weight>passenger.getWeight()) {
			toAircraft.onboard_pas.add(passenger);
			this.onboard_pas.remove(passenger);
			toAircraft.occupiedSeats[passenger.getSeatType()] += 1;
			occupiedSeats[passenger.getSeatType()] -= 1;
			this.weight -= passenger.getWeight();
			toAircraft.weight += passenger.getWeight();
			double constant = 1.0;
			switch(passenger.getSeatType()) {
			case 1:
				constant =1.2;
				break;
			case 2:
				constant =1.5;
				break;
			case 3:
				constant =2.5;
				break;
			case 4:
				constant =2.5;
				break;
			}
			return this.getOpFee()*aircraftTypeMultiplier*constant;
		}
		else {
			return this.getOpFee();
		}
	}
	@Override
	public double loadPassenger(Passenger passenger) {
		// TODO Auto-generated method stub
		if(seat_types[passenger.getSeatType()]>occupiedSeats[passenger.getSeatType()] && this.getAvailableWeight()>passenger.getWeight()) {
			this.onboard_pas.add(passenger);
			currentAirport.removePass(passenger);
			occupiedSeats[passenger.getSeatType()] += 1;
			this.setWeight(this.getWeight()+passenger.getWeight());
			double constant = 1.0;
			switch(passenger.getSeatType()) {
			case 0:
				constant =1.2;
				break;
			case 1:
				constant =1.5;
				break;
			case 2:
				constant =2.5;
				break;
			case 3:
				constant =2.5;
				break;
			}
			totaloccupiedseats += 1;
			return this.getOpFee()*this.getAircraftTypeMultiplier()*constant;
		}else {
			
			return this.getOpFee();
		}
	}
	public boolean can_loadPassenger(Passenger passenger) {
		if(seat_types[passenger.getPasstype()-1]>occupiedSeats[passenger.getPasstype()-1]&& this.getAvailableWeight()>passenger.getWeight()) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public double unloadPassenger(Passenger passenger) {
		// TODO Auto-generated method stub
		if(passenger.can_disembark(currentAirport)) {
			currentAirport.addPass(passenger);
			onboard_pas.remove(passenger);
			occupiedSeats[passenger.getSeatType()] -= 1;
			totaloccupiedseats -=1;
			this.setWeight(this.getWeight()-passenger.getWeight());
			double constant = 1.0;
			switch(passenger.getSeatType()+1) {
			case 1:
				constant =0.6;
				break;
			case 2:
				constant =2.8*1.2;
				break;
			case 3:
				constant =7.5;
				break;
			case 4:
				constant =7.5;
				break;
			}
			//System.out.println(constant);
			return passenger.disembark(currentAirport, this.getAircraftTypeMultiplier())*0.6;
		}else {
			return this.getOpFee();
		}
		
	}
	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		if(this.totaloccupiedseats == 0) {
			return false;
		}else {
		return true;
		}
		}
	
	@Override
	public boolean isFull(int seatType) {
		// TODO Auto-generated method stub
		if(this.seat_types[seatType]-this.occupiedSeats[0] == 0) {
	 		return true;
	 	}else {
	 		return false;
	 	}		
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		if(this.totaloccupiedseats == 0) {
			return true;
		}else {
		return false;
		}}
	@Override
	public double getAvailableWeight() {
		// TODO Auto-generated method stub
		return maxWeight-weight;
	}
	public double getFloorArea() {
		return getFloorArea();
	}
	@Override
	public boolean setSeats(int economy, int business, int firstClass) {
		// TODO Auto-generated method stub
		if((getFloorArea()-seatArea)-(economy*ec_area+business*bus_area+firstClass*first_area)>=0) {
			
			this.seat_types[0] += economy;
			this.seat_types[1] += business; 
			this.seat_types[2] += firstClass;
			this.totalseats += economy + business+firstClass;
			this.seatArea += economy*ec_area+business*bus_area+firstClass*first_area;
			return true;
		}
		else{
			return false;
		}
	}
	public boolean canSetSeats(int economy, int business, int firstClass) {
		// TODO Auto-generated method stub
			if((getFloorArea()-seatArea2)-(economy*ec_area+business*bus_area+firstClass*first_area)>=0) {
				this.seatArea2 += economy*ec_area+business*bus_area+firstClass*first_area;
				return true;
			}
			else{
				return false;
			}
		
	}
	@Override
	public boolean setAllEconomy() {
		// TODO Auto-generated method stub
		if(isEmpty()) {
			double m = getFloorArea()%ec_area;
			this.seatArea = getFloorArea()/ec_area-m;
			this.seat_types[0]= (int) (seatArea/ec_area);
			this.seat_types[1]=0; 
			this.seat_types[2]=0;
			this.seat_types[3]=0;
			this.totalseats = (int) (getFloorArea()/ec_area);
			return true;
		}else {
			return false;
		}
	}
	@Override
	public boolean setAllBusiness() {
		// TODO Auto-generated method stub
		if(isEmpty()) {
			double m = getFloorArea()%bus_area;
			this.seatArea = getFloorArea()/bus_area-m;
			this.seat_types[0]= 0;
			this.seat_types[1]=(int) (seatArea/bus_area); 
			this.seat_types[2]=0;
			this.seat_types[3]=0;
			this.totalseats = (int) (getFloorArea()/bus_area);
			return true;
		}else {
			return false;
		}
	}
	@Override
	public boolean setAllFirstClass() {
		// TODO Auto-generated method stub
		if(isEmpty()) {
			double m = getFloorArea()%first_area;
			this.seatArea = getFloorArea()/first_area-m;
			this.seat_types[0]= 0;
			this.seat_types[1]=0; 
			this.seat_types[2]=(int) (seatArea/first_area);
			this.seat_types[3]=0;
			this.totalseats = (int) (getFloorArea()/first_area);
			return true;
		}else {
			return false;
		}
	}
	@Override
	public boolean setRemainingEconomy() {
		// TODO Auto-generated method stub
		this.seat_types[0] += ((int)(getFloorArea()-seatArea))/ec_area;
		this.totalseats += ((int)(getFloorArea()-seatArea))/ec_area;
		this.seatArea += ((int)(getFloorArea()-seatArea));
		return true;
	}
	@Override
	public boolean setRemainingBusiness() {
		// TODO Auto-generated method stub
		this.seat_types[1] += ((int)(getFloorArea()-seatArea))/bus_area;
		this.totalseats += ((int)(getFloorArea()-seatArea))/bus_area;
		this.seatArea += ((int)(getFloorArea()-seatArea));
		return true;
	}
	@Override
	public boolean setRemainingFirstClass() {
		// TODO Auto-generated method stub
		this.seat_types[2] += ((int)(getFloorArea()-seatArea))/first_area;
		this.totalseats += ((int)(getFloorArea()-seatArea))/first_area;
		this.seatArea += ((int)(getFloorArea()-seatArea));
		return true;
	}
	@Override
	public double getFullness() {
		// TODO Auto-generated method stub
		return this.totaloccupiedseats/this.totalseats;
	}
	
	@Override
	public double getWeightRatio() {
		// TODO Auto-generated method stub
		return this.getWeight()/this.getMaxWeight();
	}
	public boolean setAllEmpty() {
		this.seat_types[0]= 0;
		this.seat_types[1]=0; 
		this.seat_types[2]=0;
		this.seat_types[3]=0;
		this.totalseats =0;
		return true;
	}
	public abstract double getOpFee();

}
