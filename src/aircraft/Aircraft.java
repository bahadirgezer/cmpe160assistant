package aircraft;

public abstract class Aircraft implements Aircraft {
    ArrayList<Passenger> passengers;
    Airport currentAirport;
    boolean seats;
    int floorSpace;
    double weight, emptyWeight, weightCapacity;
    final int speed;
    double fuel;
    double fuelCapacity;

    Public Aircraft(int speed, double emptyWeight, double maxWeight) {
        seats = false;
        this.speed = speed;
        this.emptyWeight = emptyWeight;
        this.maxWeight = maxWeight;
    }

    boolean fly(Airport toAirport) {
        if (toAirport.getAirportName() == currentAirport.getAirportName()) {
            return false;
        }
        double fuelNeeded = (toAirport.getDistance() / speed) * fuelConsumption;
        
        if (fuelNeeded > fuel) {
            return false;
        }
        fuel -= fuelNeeded;

        currentAirport = toAirport;
        
        return true;
    }
}
