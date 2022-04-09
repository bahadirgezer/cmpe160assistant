package airport;

import aircraft.Aircraft;
import interfaces.Domestic;

public class DomesticAirport extends Airport implements Domestic {

    public DomesticAirport(int aircraftCapacity, double x, double y, double fuelCost, double fuelCapacity,
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
