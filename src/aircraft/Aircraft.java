package aircraft;

public abstract class Aircraft implements Aircraft {
    Airport currentAirport;
    double weight, maxWeight;
    double fuel, fuelCapacity;
    statuc double fuelWeight;
    

    int floorSpace;
    boolean seats;
    final int speed;

    Public Aircraft(int speed, double emptyWeight, double maxWeight) {
        seats = false;
        this.speed = speed;
        this.emptyWeight = emptyWeight;
        this.maxWeight = maxWeight;
    }

    boolean addFuel(double fuel) {
        if (fuelCapacity < fuel + this.fuel) {
            return false;
        }
        if (maxWeight < weight + fuel * fuelWeight) {
            return false;
        }
        this.fuel += fuel;
        this.weight += fuel * fuelWeight;b
        return true;
    }

    boolean hasFuel(double fuel) {
        return this.fuel >= fuel ? true : false;
    }

    boolean hasFuel() {
        return this.fuel > 0 ? true : false;
    }

    boolean fly(Airport toAirport) {
        //fuel consumption needs to drop with weight loss, differential equation?
        double fuelNeeded = (toAirport.getDistance(currentAirport) / speed) * fuelConsumption;
        
        if (fuelNeeded > fuel) {
            return false;
        }
        fuel -= fuelNeeded;

        currentAirport = toAirport;
        
        return true;
    }
}
