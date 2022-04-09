package airport;

import aircraft.Aircraft;
import interfaces.Cargo;
import interfaces.Domestic;
import interfaces.International;

public class MilitaryAirport extends Airport implements International, Domestic, Cargo {

    public MilitaryAirport(int aircraftCapacity, double x, double y, double fuelCost, double fuelCapacity,
            double operationFee, double airportTax) {
        super(aircraftCapacity, x, y, fuelCost, fuelCapacity, operationFee, airportTax);
        //TODO Auto-generated constructor stub
    }

    @Override
    boolean arrival(Aircraft plane) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    boolean departure(Aircraft plane) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    void loadFuel(double amount) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public double getArrivalFee(double passengerCount, double weight, int planeType) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getArrivalFee(double weight, int planeType) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getDepartureFee() {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
