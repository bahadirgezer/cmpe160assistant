package interfaces;

public interface AircraftInterface {
    double addFuel(double fuel);
    double fillUp();
    boolean hasFuel(double fuel);
    double fly(Airport toAirport);
    double getWeightRatio();

//    boolean fly(Airport toAirport);
//    void printContents();
//    boolean isEmpty();
//    boolean isFull();
//    Airport getCurrentAirport();
//    boolean hasFuel();
//    boolean hasFuel(Double fuel);
//    boolean canAddFuel(double fuel);
//    double addFuel(double fuel); //add fuel to the airline.aircraft, checks weight and fuel capacity
//    double getFlightCost();
}
