package project.airline;

import project.airline.aircraft.Aircraft;
import project.airline.aircraft.PassengerAircraft;
import project.airline.aircraft.concrete.JetPassengerAircraft;
import project.airline.aircraft.concrete.PropPassengerAircraft;
import project.airline.aircraft.concrete.RapidPassengerAircraft;
import project.airline.aircraft.concrete.WideBodyPassengerAircraft;
import project.airport.Airport;
import project.airport.HubAirport;
import project.airport.MajorAirport;
import project.airport.RegionalAirport;
import project.passenger.*;

import java.util.*;

/**
 * @author Emre KILIC
 *
 * The ultimate class of the project. Connects the Airport, Aircraft and Passenger classes and makes the whole airline operation.
 * The class has some fields;<p>
 *     * maxAircraftCount : holds the maximum aircraft number airline can create<p>
 *     * aircrafts : holds the aircraft objects<p>
 *     * airports : holds the airport objects<p>
 *     * passengers : holds the passenger objects<p>
 *     * flights : holds the flight objects<p>
 *     * operationalCost : the operational cost of the airline<p>
 *     * propFee, widebodyFee, rapidFee, jetFee : the operationFees of the aircraft types<p>
 *     * totalExpenses-totalRevenues, profit-previousProfit-respectiveCost : holds the necessary values to calculate profit<p>
 *     * alarm : the boolean will cast to false after the logs are valid<p>
 *     * logs : holds the logs<p>
 *     * currentFlight : holds the current flight object to operate
 */
public class Airline {
    /**
     * This inner class allows saving the arranged flights to memory by creating an instance of this class.
     * The class has some fields;<p>
     *     * flightCode : the unique code of the flight and can be used in comparisons<p>
     *     * departure-arrival : the airports that the flight is executed between<p>
     *     * passengers-flightPassengers : stores the passengers that are in the flight and if they are successfully loaded, they are also in flightPassengers<p>
     *     * aircraft : stores the aircraft that is used in the flight
     */
    private class Flight implements Comparable<Flight>{
        final int flightCode;
        static int flightCount = 0;
        final Airport departure;
        final Airport arrival;
        final LinkedList<Passenger> passengers;
        final LinkedList<Passenger> flightPassengers;
        PassengerAircraft aircraft;

        /**
         *
         * @param departure the initial airport in the flight
         * @param arrival the final destination of the flight
         */
        Flight(Airport departure, Airport arrival) {
            this.departure = departure;
            this.arrival = arrival;
            passengers = new LinkedList<>();
            flightPassengers = new LinkedList<>();
            flightCode = flightCount;
            flightCount++;
        }

        /**
         *
         * @param departure the initial airport in the flight
         * @param arrival the final destination of the flight
         * @param passengers the passengers of the flight
         */
        Flight(Airport departure, Airport arrival, LinkedList<Passenger> passengers) {
            this.departure = departure;
            this.arrival = arrival;
            this.passengers = passengers;
            flightPassengers = new LinkedList<>();
            flightCode = flightCount;
            flightCount++;
        }

        /**
         * Sets the aircraft that is used in
         * @param aircraft the relevant aircraft
         */
        void setAircraft(PassengerAircraft aircraft){
            if (aircraft != null){
                this.aircraft = aircraft;
            }else {
                throw new NullPointerException("Aircraft cannot be null. Flight code: " + flightCode);
            }
        }

        /**
         * Finds the economy passenger count and returns
         * @return economy passenger count
         */
        int getEconomyPassengers(){
            int count = 0;
            for (Passenger passenger : passengers) {
                if (passenger.getType() == 0) {
                    count++;
                }
            }
            return count;
        }

        /**
         * Finds the business passenger count and returns
         * @return business passenger count
         */
        int getBusinessPassengers(){
            int count = 0;
            for (Passenger passenger : passengers) {
                if (passenger.getType() == 1) {
                    count++;
                }
            }
            return count;
        }

        /**
         * Finds the First Class and Luxury passenger count and returns
         * @return First Class and Luxury passenger count
         */
        int getFirstClassPassengers(){
            int count = 0;
            for (Passenger passenger : passengers) {
                if (passenger.getType() >= 2) {
                    count++;
                }
            }
            return count;
        }

        /**
         * Adds the passenger to the flight object
         * @param passenger the relevant passenger
         */
        void addPassenger(Passenger passenger){
            passengers.add(passenger);
        }

        /**
         * Executes the flight operation
         * Firstly loads the passengers then refuels aircraft and flies, after that unloads the passengers
         */
        void fly(){
            assert aircraft != null;
            loadSequence();
            refuel();
            if (Airline.this.fly(arrival, aircrafts.indexOf(aircraft))){
                unloadSequence();
            }else {
                cancelFlight(this);
            }
        }

        /**
         * Refuels the aircraft and prepares for the flight<p>
         * If there is a weight problem, unloads some passengers and tries again
         */
        void refuel() {
            double fuel = aircraft.getPredictedConsumption(departure.getDistance(arrival)) + 100;
            int aircraftIndex = aircrafts.indexOf(aircraft);
            if (!refuelAircraft(aircraftIndex, fuel)){
                cancelFlight(this);
            }
        }


        /**
         * Firstly sorts the passenger by their type and loads them to the aircraft<p>
         * When loading is successful, adds the passenger to the flightPassengers field
         */
        void loadSequence() {
            passengers.sort(this::compare);
            Collections.reverse(passengers);
            for (Passenger passenger : passengers) {
                boolean isLoaded = (passenger.getCurrentAirport().equals(departure) && loadPassenger(passenger, departure, aircrafts.indexOf(aircraft)));
                if (isLoaded){
                    flightPassengers.add(passenger);
                }
            }
        }

        /**
         * Unloads the passengers in the flightPassengers list
         */
        void unloadSequence() {
            for (Passenger passenger : flightPassengers) {
                unloadPassenger(passenger, aircrafts.indexOf(aircraft));
            }
        }

        /**
         * Checks the flight from its departure and arrival
         * @param departure the departure field
         * @param arrival the arrival field
         * @return true if the fields match with the params
         */
        boolean equals(Airport departure, Airport arrival){
            return this.departure.equals(departure) && this.arrival.equals(arrival);
        }

        /**
         * Compares two passenger object
         * @param o1 object1
         * @param o2 object2
         * @return the comparing result of the type field of the passengers
         */
        int compare(Passenger o1, Passenger o2) {
            if (o1.getType() == null || o2.getType() == null) {
                return 0;
            }
            return o1.getType().compareTo(o2.getType());
        }

        /**
         * Compares two flight object
         * @param o the object to be compared.
         * @return the descending comparing result of the first class passenger count
         */
        @Override
        public int compareTo(Flight o) {
            Integer op1 = this.getFirstClassPassengers();
            Integer op2 = o.getFirstClassPassengers();
            return -op1.compareTo(op2);
        }
    }

    private final int maxAircraftCount;
    private final ArrayList<Aircraft> aircrafts;
    private final HashMap<Long, Airport> airports;
    private final HashMap<Long, Passenger> passengers;
    private final Deque<Flight> flights;
    private final double operationalCost;
    private double totalExpenses;
    private double totalRevenues;
    private double profit;
    private double previousProfit;
    private double respectiveCost;
    private boolean alarm = true;
    private final LinkedList<String> logs;
    private final double propFee, widebodyFee, rapidFee, jetFee;
    private Flight currentFlight;

    /**
     *
     * @param maxAircraftCount the maximum aircraft that is allowed
     * @param operationalCost the operational cost of the airline
     * @param propFee operation fee of the prop aircraft
     * @param widebodyFee operation fee of the widebody aircraft
     * @param rapidFee operation fee of the rapid aircraft
     * @param jetFee operation fee of the jet aircraft
     */
    public Airline(int maxAircraftCount, double operationalCost, double propFee, double widebodyFee, double rapidFee, double jetFee) {
        this.maxAircraftCount = maxAircraftCount;
        this.operationalCost = operationalCost;
        this.propFee = propFee;
        this.widebodyFee = widebodyFee;
        this.rapidFee = rapidFee;
        this.jetFee = jetFee;
        airports = new HashMap<>();
        passengers = new HashMap<>();
        logs = new LinkedList<>();
        flights = new LinkedList<>();
        aircrafts = new ArrayList<>();
    }

    /**
     *
     * @return the net profit of the airline
     */
    public double getProfit() {
        return profit;
    }

    /**
     * Creates log entries for the airline
     * @param args the arguments that is supposed to log in the output
     */
    private void log(long... args){
        previousProfit = profit;
        profit = totalRevenues - totalExpenses;
        respectiveCost = profit - previousProfit;
        String log = "";
        int i = 0;
        log = log.concat(String.valueOf(args[i])); i++;
        while (i < args.length){
            log = log.concat(" "+args[i]);
            i++;
        }
//        log = log.concat(" = "+respectiveCost);
        logs.add(log);
    }

    private void log(String... args){
        previousProfit = profit;
        profit = totalRevenues - totalExpenses;
        respectiveCost = profit - previousProfit;
        String log = "";
        int i = 0;
        log = log.concat(args[i]); i++;
        while (i < args.length){
            log = log.concat(" "+args[i]);
            i++;
        }
//        log = log.concat(" = "+respectiveCost);
        logs.add(log);
    }

    /**
     * Operates the fly process for the airline
     * @param toAirport the destination airport
     * @param aircraftIndex the index of the aircraft in the aircrafts field
     * @return true if the operation is successful
     */
    private boolean fly(Airport toAirport, int aircraftIndex){
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        int can = aircraft.canFly(toAirport);
        switch (can){
            case 0:
                totalExpenses += aircraft.fly(toAirport) + operationalCost*aircrafts.size();
                log(1,toAirport.getID(),aircraftIndex);
                alarm = false;
                return true;
            case 3:
                waitForAirport(toAirport);
                break;
            case 2:
                if (alarm){
                    makeConnectionFlight();
                }
                break;
            case 1,4:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + can);
        }
        return false;
    }

    /**
     * Runs when there is a full airport and fly operation is invalid<p>
     * Arranges a flight that the departure is toAirport so the airport can be empty again<p>
     * Pushes the invalid flight to the Deque again after the arranged flight
     * @param toAirport the destination airport
     */
    private void waitForAirport(Airport toAirport) {
        PassengerAircraft aircraft = (PassengerAircraft) toAirport.getAircrafts().get(0);
        if(flightFromAirport(toAirport, aircraft)){
            Flight newFlight = flights.pollLast();
            flights.push(currentFlight);
            flights.push(newFlight);
        }
    }

    private void makeConnectionFlight(){
        ArrayList<Airport> destinationList = new ArrayList<>();
        destinationList.add(currentFlight.departure);
        destinationList.add(currentFlight.arrival);

        while (currentFlight.arrival.getDistance(destinationList.get(destinationList.size()-2)) > 14000){
            double minDistance = Integer.MAX_VALUE;
            Airport appendingAirport = null;
            for (Airport airport : airports.values()) {
                if (!destinationList.contains(airport)){
                    double validDistance = destinationList.get(destinationList.size()-2).getDistance(airport);
                    double newDistance = airport.getDistance(currentFlight.arrival);
                    if (validDistance <= 14000 && newDistance < minDistance) {
                        appendingAirport = airport;
                        minDistance = newDistance;
                    }
                }
            }
            if (appendingAirport != null) {
                destinationList.add(destinationList.size() - 1, appendingAirport);
            }
        }

        for (int i = 0; i < destinationList.size()-1; i++) {
            Flight flight = new Flight(destinationList.get(i), destinationList.get(i+1), currentFlight.flightPassengers);
            flight.setAircraft(currentFlight.aircraft);
            flights.offer(flight);
        }
        makeValidFlights(currentFlight.arrival);
    }

    /**
     * Checks if there is any space for adding aircraft and adds<p>
     * If the operation is invalid cancels the flight
     * @param flight the relevant flight
     * @return true if the aircraft is assigned
     */
    private boolean assignAircraftForFlight(Flight flight){
        if (!flight.departure.isFull() && canAddAircraft()){
            PassengerAircraft pa;
            try{
                pa = (PassengerAircraft) addAircraft(flight.departure.getID(), chooseType(flight));
                if (alarm){
                    fillUp(aircrafts.indexOf(pa));
                }
            }catch (Exception e) {
                return false;
            }
            assignSeatForFlight(flight, pa);
            flight.setAircraft(pa);
        }else {
            cancelFlight(flight);
            return false;
        }
        return true;
    }

    /**
     * Cancels the flight
     * @param flight the relevant flight
     */
    private void cancelFlight(Flight flight) {
    }

    /**
     * Operates the seat arrangement process for flight<p>
     * Gives priority to the higher seat types
     * @param flight the relevant flight
     * @param pa the aircraft whose seat is arranged
     */
    private void assignSeatForFlight(Flight flight, PassengerAircraft pa) {
        int fcPassengers = flight.getFirstClassPassengers(),
        bPassengers = flight.getBusinessPassengers(),
        ePassengers = flight.getEconomyPassengers();
        if (pa.setSeat(2, fcPassengers)){
            if (pa.setSeat(1, bPassengers)){
                if (!pa.setSeat(0, ePassengers)){
                    pa.setRemainingEconomy();
                }
            }else {
                pa.setRemainingBusiness();
            }
        }else {
            pa.setRemainingFirstClass();
        }
        int[] seats = pa.getFinalSeats();
        log(2, aircrafts.indexOf(pa), seats[0], seats[1], seats[2]);
    }

    /**
     * Checks if it is possible to add aircraft
     * @return true if possible
     */
    private boolean canAddAircraft() {
        return aircrafts.size() < maxAircraftCount;
    }

    /**
     * Chooses the most profitable aircraft for the flight
     * @param flight the relevant flight
     * @return the type of aircraft
     */
    private int chooseType(Flight flight) {
        double distance = flight.arrival.getDistance(flight.departure);
        if (distance < 5000 && flight.getFirstClassPassengers() > 3){
            return 3;
        }else if (distance < 2000){
            return 0;
        }else if (distance < 7000){
            return 2;
        }else if ((alarm && flights.size() == 0) || distance < 14000){
            return 1;
        }else {
            throw new RuntimeException();
        }
    }

    /**
     * Firstly sorts the flights and make the necessary operations for the flight<p>
     * Uses the Deque object for the flights and polls them one by one
     */
    public void executeFlights(){
        clipAndSortFlights();

        currentFlight = flights.poll();
        while (currentFlight != null){
            if (currentFlight.aircraft == null){
                if (assignAircraftForFlight(currentFlight)){
                    currentFlight.fly();
                }
            }else {
                currentFlight.fly();
            }
            currentFlight = flights.poll();
        }
    }

    /**
     * This method runs only when there is no valid flight executed
     * Makes an unprofitable valid flight to fill logs
     */
    private void makeValidFlights(Airport arrival) {
        currentFlight = flights.poll();
        assert currentFlight != null;
        PassengerAircraft aircraft = currentFlight.aircraft;
        int aircraftIndex = aircrafts.indexOf(aircraft);
        while (currentFlight != null){
            if (aircraft.canAddFuel(1)){
                fillUp(aircraftIndex);
            }
            currentFlight.flightPassengers.addAll(currentFlight.aircraft.getPassengers());
            fly(currentFlight.arrival, aircraftIndex);
            if (currentFlight.arrival.equals(arrival)){
                currentFlight.unloadSequence();
            }
            currentFlight = flights.poll();
        }
    }

    /**
     * Clips and sorts the flight Deque<p>
     * Firstly checks the flights and if passenger count is not enough, cancels them<p>
     * Then sorts them with the profitable manner<p>
     * Uses the compareTo method in the Flight class
     */
    private void clipAndSortFlights() {
        ArrayList<Flight> flights = new ArrayList<>();
        for (Flight flight : this.flights){
            if (flight.passengers.size() > 5){
                flights.add(flight);
            }else {
                cancelFlight(flight);
            }
        }
        flights.sort(Flight::compareTo);
        this.flights.clear();
        for (Flight flight : flights) {
            this.flights.offer(flight);
        }
    }

    /**
     * Makes the necessary loading operations
     * @param passenger the passenger that is loading
     * @param airport the airport where the loading operation is running
     * @param aircraftIndex the index of the aircraft
     * @return true if the process is successful
     */
    private boolean loadPassenger(Passenger passenger, Airport airport, int aircraftIndex){
        PassengerAircraft pA = (PassengerAircraft) aircrafts.get(aircraftIndex);
        if (pA.canLoad(passenger) == 0) {
            totalExpenses += pA.loadPassenger(passenger);
            log(4, passenger.getID(), aircraftIndex, airport.getID());
            return true;
        }
        return false;
    }

    /**
     * Makes the necessary unloading operations
     * @param passenger the passenger that is unloading
     * @param aircraftIndex the index of the aircraft
     * @return true if the process is successful
     */
    private boolean unloadPassenger(Passenger passenger, int aircraftIndex){
        PassengerAircraft pA = (PassengerAircraft) aircrafts.get(aircraftIndex);
        Airport airport = pA.getCurrentAirport();
        if (passenger.canDisembark(airport)){
            totalRevenues += pA.unloadPassenger(passenger);
            log(5,passenger.getID(),aircraftIndex,airport.getID());
            return true;
        }
        return false;
    }

    /**
     * Makes the necessary transfer operations
     * @param passenger the passenger that is transferring
     * @param aircraftIndex the index of the current aircraft
     * @param toAircraftIndex the index of the aircraft that is transferring to
     * @return true if the process is successful
     */
    private boolean transferPassenger(Passenger passenger, int aircraftIndex, int toAircraftIndex){
        PassengerAircraft pA = (PassengerAircraft) aircrafts.get(aircraftIndex);
        if (pA.hasPassenger(passenger)){
            PassengerAircraft toAircraft = (PassengerAircraft) aircrafts.get(toAircraftIndex);
            Airport airport = pA.getCurrentAirport();
            if (airport.equals(toAircraft.getCurrentAirport()) && toAircraft.canLoad(passenger) == 0) {
                totalExpenses += pA.transferPassenger(passenger, toAircraft);
                log(6, aircraftIndex, toAircraftIndex, airport.getID());
                return true;
            }
        }
        return false;
    }

    /**
     * Iterates over the major airports and find passengers that is going to major airport<p>
     * Creates a flight and appends the passenger to the flight
     */
    public void arrangeMajorToMajorFlights(){
        for (Airport airport : airports.values()){
            if (airport.getType() == 1){
                for (Passenger passenger : airport.getPassengers()) {
                    Airport toAirport = passenger.nextMajor();
                    if (toAirport != null){
                        createFlight(airport, toAirport, passenger);
                    }
                }
            }
        }
    }

    /**
     * Iterates over the major airports and find passengers that is going to regional airport<p>
     * Creates a flight and appends the passenger to the flight
     */
    public void arrangeMajorToRegionalFlights(){
        for (Airport airport : airports.values()){
            if (airport.getType() == 1){
                for (Passenger passenger : airport.getPassengers()) {
                    Airport toAirport = passenger.nextRegional();
                    if (toAirport != null){
                        createFlight(airport, toAirport, passenger);
                    }
                }
            }
        }
    }

    /**
     * Iterates over the major airports and find passengers that is going to hub airport<p>
     * Creates a flight and appends the passenger to the flight
     */
    public void arrangeMajorToHubFlights(){
        for (Airport airport : airports.values()){
            if (airport.getType() == 1){
                for (Passenger passenger : airport.getPassengers()) {
                    Airport toAirport = passenger.nextHub();
                    if (toAirport != null){
                        createFlight(airport, toAirport, passenger);
                    }
                }
            }
        }
    }

    /**
     * Iterates over the regional airports and find passengers that is going to hub airport<p>
     * Creates a flight and appends the passenger to the flight
     */
    public void arrangeRegionalToHubFlights(){
        for (Airport airport : airports.values()){
            if (airport.getType() == 2){
                for (Passenger passenger : airport.getPassengers()) {
                    Airport toAirport = passenger.nextHub(); // closestHub(airport)
                    if (toAirport != null){
                        createFlight(airport, toAirport, passenger);
                    }
                }
            }
        }
    }

    /**
     * Iterates over the hub airports and find passengers that is going to major airport<p>
     * Creates a flight and appends the passenger to the flight
     */
    public void arrangeHubToMajorFlights(){
        for (Airport airport : airports.values()){
            if (airport.getType() == 0){
                for (Passenger passenger : airport.getPassengers()) {
                    Airport toAirport = passenger.nextMajor();
                    if (toAirport != null){
                        createFlight(airport, toAirport, passenger);
                    }
                }
            }
        }
    }

    /**
     * Iterates over the hub airports and find passengers that is going to hub airport<p>
     * Creates a flight and appends the passenger to the flight
     */
    public void arrangeHubToHubFlights(){
        for (Airport airport : airports.values()){
            if (airport.getType() == 0){
                for (Passenger passenger : airport.getPassengers()) {
                    Airport toAirport = passenger.nextHub();
                    if (toAirport != null){
                        createFlight(airport, toAirport, passenger);
                    }
                }
            }
        }
    }

    /**
     * Iterates over the hub airports and find passengers that is going to regional airport<p>
     * Creates a flight and appends the passenger to the flight
     */
    public void arrangeHubToRegionalFlights(){
        for (Airport airport : airports.values()){
            if (airport.getType() == 0){
                for (Passenger passenger : airport.getPassengers()) {
                    Airport toAirport = passenger.nextRegional();
                    if (toAirport != null){
                        createFlight(airport, toAirport, passenger);
                    }
                }
            }
        }
    }

    /**
     * Finds a flight that the departure is given and sets its aircraft and offers is to the flights Deque
     * @param airport the given airport
     * @param aircraft the set aircraft
     * @return true if it is possible
     */
    private boolean flightFromAirport(Airport airport, PassengerAircraft aircraft){
        Flight flightAhead = null;
        for (Flight flight : flights) {
            if (flight.departure.equals(airport) && !flight.arrival.isFull()){
                flight.setAircraft(aircraft);
                flightAhead = flight;
                flights.remove(flight);
                break;
            }
        }
        if (flightAhead != null){
            flights.offer(flightAhead);
            return true;
        }else {
            return false;
        }
    }

    /**
     * Checks if there is a flight from departure to arrival<p>
     * If there is, appends the passenger to that flight<p>
     * If not creates new one and appends the passenger to the flight
     * @param departure the departure airport
     * @param arrival the arrival airport
     * @param passenger the relevant passenger
     */
    private void createFlight(Airport departure, Airport arrival, Passenger passenger){
        for (Flight flight : flights) {
            if (flight.equals(departure, arrival)){
                flight.addPassenger(passenger);
                return;
            }
        }
        Flight flight = new Flight(departure, arrival);
        flight.addPassenger(passenger);
        flights.offer(flight);
    }

    /**
     * Creates an airport object for the airline
     * @param id id of airport
     * @param x x coordinate of the airport
     * @param y y coordinate of the airport
     * @param fuelCost cost of fuel in that airport
     * @param operationFee operation fees in that airport
     * @param aircraftCapacity capacity of airport
     * @param type type of airport
     */
    public void addAirport(long id, double x, double y, double fuelCost, double operationFee, int aircraftCapacity, int type){
        switch (type){
            case 0 -> airports.put(id, new HubAirport(id, x, y, aircraftCapacity, fuelCost, operationFee, type));
            case 1 -> airports.put(id, new MajorAirport(id, x, y, aircraftCapacity, fuelCost, operationFee, type));
            case 2 -> airports.put(id, new RegionalAirport(id, x, y, aircraftCapacity, fuelCost, operationFee, type));
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    /**
     * Creates an aircraft for the airline
     * @param airportId initial location of the aircraft
     * @param type type of aircraft
     * @return the created aircraft
     */
    public Aircraft addAircraft(long airportId, int type){
        Airport airport = airports.get(airportId);
        if (aircrafts.size() < maxAircraftCount) {
            switch (type) {
                case 0 -> aircrafts.add(new PropPassengerAircraft(propFee, airport));
                case 1 -> aircrafts.add(new WideBodyPassengerAircraft(widebodyFee, airport));
                case 2 -> aircrafts.add(new RapidPassengerAircraft(rapidFee, airport));
                case 3 -> aircrafts.add(new JetPassengerAircraft(jetFee, airport));
                default -> throw new IllegalStateException("Unexpected value: " + type);
            }
            log(0, airportId, type);
        }else {
            throw new RuntimeException("You cannot add aircraft more than the limit");
        }
        Aircraft aircraft = aircrafts.get(aircrafts.size()-1);
        airport.addAircraft(aircraft);
        return aircraft;
    }

    /**
     * Creates a new passenger object for the airline
     * @param id id of the passenger
     * @param weight weight of the passenger
     * @param baggageCount number of baggage of the passenger
     * @param destinations destination airport list of the passenger
     * @param type type of passenger
     */
    public void addPassenger(long id, double weight, int baggageCount, long[] destinations, int type){
        ArrayList<Airport> airports = new ArrayList<>();
        for (long index : destinations) {
            airports.add(this.airports.get(index));
        }
        switch (type){
            case 0 -> passengers.put(id, new EconomyPassenger(id, weight, baggageCount, airports));
            case 1 -> passengers.put(id, new BusinessPassenger(id, weight, baggageCount, airports));
            case 2 -> passengers.put(id, new FirstClassPassenger(id, weight, baggageCount, airports));
            case 3 -> passengers.put(id, new LuxuryPassenger(id, weight, baggageCount, airports));
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
        airports.get(0).addPassenger(passengers.get(id));
    }

    /**
     * Fills up the aircraft
     * @param aircraftIndex the index of the aircraft
     */
    private void fillUp(int aircraftIndex){
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        double fuel = aircraft.getFuel();
        totalExpenses += aircraft.fillUp();
        fuel = aircraft.getFuel() - fuel;
        log("3", String.valueOf(aircrafts.indexOf(aircraft)), String.valueOf(fuel));
    }

    /**
     * Makes the refuel operation for the aircraft
     * @param aircraftIndex the index of the aircraft
     * @param fuel amount of fuel
     * @return true if the process is successful
     */
    private boolean refuelAircraft(int aircraftIndex, double fuel){
        Aircraft aircraft = aircrafts.get(aircraftIndex);
        if (!aircraft.hasFuel(fuel)){
            if (aircraft.canAddFuel(fuel)) {
                totalExpenses += aircraft.addFuel(fuel);
                log("3", String.valueOf(aircrafts.indexOf(aircraft)), String.valueOf(fuel));
                return true;
            }
        }
        return false;
    }

    /**
     * @return the log list of the airline
     */
    public LinkedList<String> getLogs() {
        return logs;
    }
}