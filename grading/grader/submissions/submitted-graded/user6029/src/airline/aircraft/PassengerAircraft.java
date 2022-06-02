package airline.aircraft;

import java.util.HashMap;

import airport.Airport;
import interfaces.PassengerInterface;
import passenger.Passenger;

public abstract class PassengerAircraft extends Aircraft implements PassengerInterface {

	protected PassengerAircraft(Airport currentAirport, double operationFee) {
		super(currentAirport);
		this.operationFee = operationFee;
		occupiedEconomySeats = 0; 
		occupiedBusinessSeats = 0;
		occupiedFirstClassSeats = 0;
		// TODO Auto-generated constructor stub
	}

	protected double floorArea;
	private int economySeats, businessSeats, firstClassSeats;
	private int occupiedEconomySeats, occupiedBusinessSeats, occupiedFirstClassSeats;
	
	protected double operationFee;
	protected double aircraftTypeMultiplier;
	protected HashMap<Long, Passenger> passengers = new HashMap<Long, Passenger>();
	
	public double loadPassenger(Passenger passenger) {
		double loadingFee = operationFee * aircraftTypeMultiplier;
		//System.out.println(operationFee + " " + aircraftTypeMultiplier + " " + loadingFee);
		if(passenger.getPassengerType().equals("economy")) {
			if(isFull(1)) {
				return operationFee;
			}
			else {
				passenger.board(1);
				currentAirport.removePassenger(passenger);
				addPassenger(passenger);
				occupiedEconomySeats++;
				weight = weight + passenger.getWeight();
				return (loadingFee * 1.2);
			}
		}
		else if(passenger.getPassengerType().equals("business")) {
			if(isFull(2)) {
				if(isFull(1)) {
					return operationFee;
				}
				else {
					passenger.board(1);
					currentAirport.removePassenger(passenger);
					addPassenger(passenger);
					occupiedEconomySeats++;
					weight = weight + passenger.getWeight();
					return loadingFee * 1.2;
				}
			}
			else {
				passenger.board(2);
				currentAirport.removePassenger(passenger);
				addPassenger(passenger);
				occupiedBusinessSeats++;
				weight = weight + passenger.getWeight();
				return loadingFee * 1.5;
			}
		}
		else{
			if(isFull(3)) {
				if(isFull(2)) {
					if(isFull(1)) {
						return operationFee;
					}
					else {
						passenger.board(1);
						currentAirport.removePassenger(passenger);
						addPassenger(passenger);
						occupiedEconomySeats++;
						weight = weight + passenger.getWeight();
						return loadingFee * 1.2;
					}
				}
				else {
					passenger.board(2);
					currentAirport.removePassenger(passenger);
					addPassenger(passenger);
					occupiedBusinessSeats++;
					weight = weight + passenger.getWeight();
					return loadingFee * 1.5;
				}
			}
			else {
				passenger.board(3);
				currentAirport.removePassenger(passenger);
				addPassenger(passenger);
				occupiedFirstClassSeats++;
				weight = weight + passenger.getWeight();
				return loadingFee * 2.5;
			}
		}
		
	}
	
	public double unloadPassenger(Passenger passenger) {
		double disembarkationRevenue = passenger.disembark(currentAirport, aircraftTypeMultiplier);
		double seatConstant;
		//
		//System.out.println(disembarkationRevenue);
		if(disembarkationRevenue > 0) {
			removePassenger(passenger);
			//passenger.
			if(passenger.getCurrentSeatType() == 1) {
				seatConstant = 1;
				occupiedEconomySeats--;
			}
			else if(passenger.getCurrentSeatType() == 2) {
				seatConstant = 2.8;
				occupiedBusinessSeats--;
			}
			else if(passenger.getCurrentSeatType() == 3) {
				seatConstant = 7.5;
				occupiedFirstClassSeats--;
			}
			else {
				seatConstant = 1;
				System.out.println("error in unloadpassenger method");
			}
			currentAirport.addPassenger(passenger);
			weight = weight - passenger.getWeight();
			return (disembarkationRevenue * seatConstant);
		}
		else {
			return operationFee * -1;
		}
	}
	
	public double transferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		double loadingFee = operationFee * aircraftTypeMultiplier;
		if(passenger.getPassengerType().equals("economy")) {
			if(toAircraft.isFull(1)) {
				return operationFee;
			}
			else {
				passenger.connection(1);
				removePassenger(passenger);
				toAircraft.addPassenger(passenger);
				weight = weight - passenger.getWeight();
				toAircraft.weight = weight + passenger.getWeight();
				return loadingFee * 1.2;
			}
		}
		else if(passenger.getPassengerType().equals("business")) {
			if(toAircraft.isFull(2)) {
				if(toAircraft.isFull(1)) {
					return operationFee;
				}
				else {
					passenger.connection(1);
					removePassenger(passenger);
					toAircraft.addPassenger(passenger);
					weight = weight - passenger.getWeight();
					toAircraft.weight = weight + passenger.getWeight();
					return loadingFee * 1.2;
				}
			}
			else {
				passenger.connection(2);
				removePassenger(passenger);
				toAircraft.addPassenger(passenger);
				weight = weight - passenger.getWeight();
				toAircraft.weight = weight + passenger.getWeight();
				return loadingFee * 1.5;
			}
		}
		else{
			if(toAircraft.isFull(3)) {
				if(toAircraft.isFull(2)) {
					if(toAircraft.isFull(1)) {
						return operationFee;
					}
					else {
						passenger.connection(1);
						removePassenger(passenger);
						toAircraft.addPassenger(passenger);
						weight = weight - passenger.getWeight();
						toAircraft.weight = weight + passenger.getWeight();
						return loadingFee * 1.2;
					}
				}
				else {
					passenger.connection(2);
					removePassenger(passenger);
					toAircraft.addPassenger(passenger);
					weight = weight - passenger.getWeight();
					toAircraft.weight = weight + passenger.getWeight();
					return loadingFee * 1.5;
				}
			}
			else {
				passenger.connection(3);
				removePassenger(passenger);
				toAircraft.addPassenger(passenger);
				weight = weight - passenger.getWeight();
				toAircraft.weight = weight + passenger.getWeight();
				return loadingFee * 2.5;
			}
		}
	}
	
	public boolean canLoadPassenger(Passenger passenger) {
		if(currentAirport != passenger.getCurrentAirport())
			return false;
		//if(passenger.getCurrentSeatType() != 0)
		//	return false;
		if(maxWeight <(weight + passenger.getWeight()))
			return false;
		if(isFull())
			return false;
		if(passenger.getPassengerType().equals("economy")) {
			if(isFull(1)) {
				return false;
			}
		}
		if(passenger.getPassengerType().equals("business")) {
			if(isFull(1)) {
				if(isFull(2)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean canUnloadPassenger(Passenger passenger) {
		if(passenger.getDestinations().contains(currentAirport))
			return true;
		else if(passenger.getCurrentAirport() == currentAirport)
			return true;
		else
			return false;
	}
	
	public boolean canTransferPassenger(Passenger passenger, PassengerAircraft toAircraft) {
		if(this.currentAirport != toAircraft.currentAirport)
			return false;
		if(toAircraft.maxWeight <(toAircraft.weight + passenger.getWeight()))
			return false;
		if(toAircraft.isFull())
			return false;
		if(passenger.getPassengerType().equals("economy")) {
			if(toAircraft.isFull(1)) {
				return false;
			}
		}
		if(passenger.getPassengerType().equals("business")) {
			if(toAircraft.isFull(1)) {
				if(toAircraft.isFull(2)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public HashMap<Long, Passenger> getPassengers() {
		return passengers;
	}

	private void addPassenger(Passenger passenger) {
		passengers.put(passenger.getID(), passenger);
	}
	
	private void removePassenger(Passenger passenger) {
		passengers.remove(passenger.getID());
	}
	
	public boolean isFull() {
		if((economySeats+businessSeats+firstClassSeats)-(occupiedBusinessSeats+occupiedEconomySeats+occupiedFirstClassSeats)<=0)
			return true;
		else
			return false;
	}

	public boolean isFull(int seatType) {
		if(seatType == 1){
			if((economySeats - occupiedEconomySeats) == 0)
				return true;
		}
		else if(seatType == 2){
			if((businessSeats - occupiedBusinessSeats) == 0)
				return true;
		}
		else if(seatType == 3) {
			if((firstClassSeats - occupiedFirstClassSeats) == 0)
				return true;
		}
		return false;
	}

	public boolean isEmpty() {
		if((economySeats+businessSeats+firstClassSeats)-(occupiedBusinessSeats+occupiedEconomySeats+occupiedFirstClassSeats)>0)
			return true;
		else
			return false;
	}

	public double getAvailableWeight() {
		return maxWeight - weight;
	}

	public double getAvailableFloorArea() {
		return (floorArea - (1*economySeats) - (3*businessSeats) - (8*firstClassSeats));
	}
	
	public double getFloorArea() {
		return floorArea;
	}

	public int getEconomySeats() {
		return economySeats;
	}

	public int getBusinessSeats() {
		return businessSeats;
	}

	public int getFirstClassSeats() {
		return firstClassSeats;
	}

	public int getOccupiedEconomySeats() {
		return occupiedEconomySeats;
	}

	public int getOccupiedBusinessSeats() {
		return occupiedBusinessSeats;
	}

	public int getOccupiedFirstClassSeats() {
		return occupiedFirstClassSeats;
	}

	public boolean setSeats(int economy, int business, int firstClass) {
		economySeats = economy;
		businessSeats = business;
		firstClassSeats = firstClass;
		return true;
	}

	public boolean setAllEconomy() {
		int seats = (int) floorArea;
		setSeats(seats, 0, 0);
		return true;
	}

	public boolean setAllBusiness() {
		int seats = (int) (floorArea/3);
		setSeats(0, seats, 0);
		return true;
	}

	public boolean setAllFirstClass() {
		int seats = (int) (floorArea/8);
		setSeats(0, 0, seats);
		return true;
	}

	public boolean setRemainingEconomy() {
		int seats = (int) getAvailableFloorArea();
		setSeats(seats+economySeats, businessSeats, firstClassSeats);
		return true;
	}

	public boolean setRemainingBusiness() {
		int seats = (int) (getAvailableFloorArea()/3);
		setSeats(economySeats,seats+ businessSeats, firstClassSeats);
		return true;
	}

	public boolean setRemainingFirstClass() {
		int seats = (int) (getAvailableFloorArea()/8);
		setSeats(economySeats, businessSeats, seats+firstClassSeats);
		return true;
	}
	
	public double getFullness() {
		double allSeats = economySeats+businessSeats+firstClassSeats;
		double occupiedAllSeats = occupiedBusinessSeats+occupiedEconomySeats+occupiedFirstClassSeats;
		//System.out.println(occupiedAllSeats + " " + allSeats);
		return occupiedAllSeats/allSeats;
	}

	public double getFlightCostHelper(Airport toAirport,double constant){
		double landingCost, departureCost, flightOperationCost, distance, fullness, flightCost;
		distance = currentAirport.getDistance(toAirport);
		fullness = getFullness();
		flightOperationCost = distance * fullness * constant;
		departureCost = currentAirport.departAircraft(this);
		double fuelConsumption = getFuelConsumption(currentAirport.getDistance(toAirport));
		fuel = fuel - fuelConsumption;
		weight = weight - fuel*fuelWeight;
		landingCost = toAirport.landAircraft(this);
		flightCost = landingCost + departureCost + flightOperationCost;
		return flightCost;
	}
	
	public double getFuelConsumptionHelper(double distance, double ratioConstant, double constant, double fuelConsumption){
		double distanceRatio, bathtubCoefficient ,takeOffConsumption, cruiseConsumption;
		distanceRatio = distance/ratioConstant;
		bathtubCoefficient = Math.pow(distanceRatio, 4)*25.9324 - Math.pow(distanceRatio, 3)*50.5633 + Math.pow(distanceRatio, 2)*35.0554 - distanceRatio * 9.90346 + 1.97413; 
		takeOffConsumption = weight * constant / fuelWeight;
		cruiseConsumption = distance * bathtubCoefficient * fuelConsumption;
		return (takeOffConsumption + cruiseConsumption);
	}
}
