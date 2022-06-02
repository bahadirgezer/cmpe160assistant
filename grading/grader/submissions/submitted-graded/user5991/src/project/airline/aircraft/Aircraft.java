package project.airline.aircraft;
import project.airport.Airport;
import project.interfaces.AircraftInterface;
import project.passenger.BusinessPassenger;
import project.passenger.EconomyPassenger;
import project.passenger.FirstClassPassenger;
import project.passenger.LuxuryPassenger;
import project.passenger.Passenger;

public abstract class Aircraft implements AircraftInterface {
	
	protected Airport currentAirport;
	protected double weight;
	protected double maxWeight;
	protected double fuelWeight = 0.7;
	protected double fuel;
	protected double fuelCapacity;
	protected double fuelConsumption;
	protected double operationFee;
	protected int ID;
	protected double aircraftTypeMultiplier = 0.9;
	
	public Airport getCurrentAirport() {return currentAirport;}
	public void setCurrentAirport(Airport airport) {this.currentAirport=airport;}
	public int getID() {return ID;}
	public void IDSetter(int ID) {this.ID=ID;}
	public double getFuelCapacity() {return fuelCapacity;}
	/**Checks if the given passenger can be loaded to the aircraft, checks the weight capacity and the fullness of relevant seat types.
	 * @param passenger
	 * @return
	 */
	public boolean canload(Passenger passenger) {
		if((weight+passenger.getWeight())<=maxWeight) {
			
			if(passenger instanceof EconomyPassenger) {
				if(isFull(0)) {
					return false;
				}
				return true;
			}
			if(passenger instanceof BusinessPassenger) {
				if(isFull(0)==false||isFull(1)==false) {
					return true;
				}
				return false;
			}
			if(passenger instanceof FirstClassPassenger||passenger instanceof LuxuryPassenger) {
				if(isFull(0)==false||isFull(1)==false||isFull(2)==false) {

					return true;
				}

				return false;
			}
		}

		return false;
	}
	
	
	protected abstract boolean isFull(int i);
	public abstract double loadPassenger(Passenger passenger);
	public abstract double unloadPassenger(Passenger passenger);
	protected abstract double getFuelConsumption(double distance);
	protected abstract double getFlightCost(Airport toAirport);
	public double getAircraftTypeMultiplier() {return aircraftTypeMultiplier;}
	
	/**Checks if the aircraft can fly from the currentAirport to the toAirport.
	 * @param toAirport
	 * @return
	 */
	public boolean canfly(Airport toAirport) {
		if(getFuelConsumption(currentAirport.getDistance(toAirport))>fuel||
				toAirport.isFull()||currentAirport.equals(toAirport)) 
		{return false;}
		
		return true;
	}
	
	public double refuelle(Airport toAirport) {
		if(getFuelConsumption(currentAirport.getDistance(toAirport))<this.fuel) {
			return 0.0;
		}
		double fuelconsumption = 1.12*(getFuelConsumption(currentAirport.getDistance(toAirport))-this.fuel);
		
		weight+=fuelconsumption*fuelWeight;
		this.fuel+=fuelconsumption;
		return fuelconsumption*currentAirport.getFuelCost();
	}
	
	/** Calculates and return the costs of the flight.
	 * @param toAirport
	 *@return
	 */
	public double fly(Airport toAirport) { 
		//System.out.println(currentAirport.getID()+ " " + currentAirport.getClass());
		//double fuelconsumption = getFuelConsumption(currentAirport.getDistance(toAirport));
		double flightcost = getFlightCost(toAirport);
		double departurefee = currentAirport.departAircraft(this);
		
		double fuelconsumption = getFuelConsumption(currentAirport.getDistance(toAirport));
		
		
		currentAirport = toAirport;
		double landingfee = toAirport.landAircraft(this);
		
		fuel-=fuelconsumption;
		weight-=fuelconsumption*fuelWeight;
		
		return flightcost+departurefee+landingfee;

	}
	

	/** Adds fuel to the aircraft, increases the weight, returns the fueling cost.
	 * @param fuel
	 *@return
	 */
	@Override
	public double addFuel(double fuel) {
		double forreturn = currentAirport.getFuelCost()*fuel;
		weight += fuel*fuelWeight;
		this.fuel+=fuel;
		return forreturn;
	}
	
	/** Maxes out the fuel, increases the weight and returns the fueling cost.
	 * @return 
	 */
	@Override
	public double fillUp() {
		double forreturn = currentAirport.getFuelCost()*(fuelCapacity-fuel);
		weight += (fuelCapacity-fuel)*fuelWeight;
		fuel = fuelCapacity;
		return forreturn;
	}

	/**Checks if the airport has enough fuel
	 *@param fuel
	 *@return
	 */
	@Override
	public boolean hasFuel(double fuel) {
		return fuel<=this.fuel;
	}

	/**Returns the weight ratio of the aircraft
	 *@return
	 */
	@Override
	public double getWeightRatio() {
		return weight/maxWeight;
	}
}
