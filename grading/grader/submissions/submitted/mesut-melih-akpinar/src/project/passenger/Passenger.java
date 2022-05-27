package project.passenger;

import project.airline.aircraft.Aircraft;
import project.airport.Airport;

import java.util.ArrayList;

public abstract class Passenger {
    private final int type;
    private final long ID;
    private final double weight;
    private final int baggageCount;
    private int seatType;
    public double connectionMultiplier;
    public boolean onAirport;
    public Airport lastEmbark;
    public Aircraft aircraft;
    private ArrayList<Airport> destinations;

    public Passenger(int type, long id, double weight, int baggageCount, ArrayList<Airport> destinations) {
        this.type = type;
        this.ID = id;
        this.weight = weight;
        this.baggageCount = baggageCount;
        this.destinations = destinations;
        this.onAirport = true;
        if(type == 1) this.connectionMultiplier = 0.6;
        else if(type == 2) this.connectionMultiplier = 1.2;
        else if(type == 3) this.connectionMultiplier = 3.2;
        else if(type == 4) this.connectionMultiplier = 3.2;
    }

    public double disembark(Airport airport, double aircraftTypeMultiplier) {
        if(!hasDestination(airport)) return 0.0;
        for(int i = 0; i < destinations.indexOf(airport) + 1; i++) {
            destinations.remove(0);
        }
        return calculateTicketPrice(airport, aircraftTypeMultiplier);
    }

    public boolean connection(int seatType) {
        connectionMultiplier *= 0.8;
        return true;
    }

    private boolean hasDestination(Airport airport) {
        for(Airport destination : destinations) {
            if(destination.getID() == airport.getID()) return true;
        }
        return false;
    }

    abstract double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier);

    public boolean canBeLoaded(double v) {
        return weight <= v;
    }

    public int getBaggageCount() {
        return baggageCount;
    }

    public double getAirportMultiplier(Airport toAirport) {
        if(lastEmbark.getType() == 1 && toAirport.getType() == 1) return 0.5;
        if(lastEmbark.getType() == 1 && toAirport.getType() == 2) return 0.7;
        if(lastEmbark.getType() == 1 && toAirport.getType() == 3) return 1;
        if(lastEmbark.getType() == 2 && toAirport.getType() == 1) return 0.6;
        if(lastEmbark.getType() == 2 && toAirport.getType() == 2) return 0.8;
        if(lastEmbark.getType() == 2 && toAirport.getType() == 3) return 1.8;
        if(lastEmbark.getType() == 3 && toAirport.getType() == 1) return 0.9;
        if(lastEmbark.getType() == 3 && toAirport.getType() == 2) return 1.6;
        if(lastEmbark.getType() == 3 && toAirport.getType() == 3) return 3;
        return 0;
    }

    public double getSeatConstant() {
        if(type == 1) return 1.0;
        if(type == 2) return 2.8;
        if(type == 3) return 7.5;
        if(type == 4) return 7.5;
        return 0;
    }

    public double getWeight() {
        return weight;
    }

    public long getID() {
        return ID;
    }

    public Airport getFirstDestination() {
        return destinations.get(0);
    }

    public int getType() {
        return type;
    }
}
