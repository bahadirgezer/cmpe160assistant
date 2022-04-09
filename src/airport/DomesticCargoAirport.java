package airport;

import aircraft.Aircraft;
import interfaces.Cargo;
import interfaces.Domestic;


public class DomesticCargoAirport extends Airport implements Domestic, Cargo {
    public DomesticCargoAirport(int aircraftCapacity, double x, double y, double fuelCost, double fuelCapacity,
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
