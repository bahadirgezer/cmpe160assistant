package interfaces;

public interface Aircraft {
    boolean fly(Airport toAirport);
    void printContents();
    boolean isEmpty();
    boolean isFull();
    boolean getCurrentAirport();
    boolean hasFuel();
    boolean hasFuel(Double fuel);
    boolean addFuel(int fuel); //add fuel to the aircraft, checks weight and fuel capacity
    
}
