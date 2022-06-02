package project.utils;

import java.util.Arrays;
import java.util.ArrayList;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import project.passenger_container.BusinessPassenger;
import project.passenger_container.EconomyPassenger;
import project.passenger_container.FirstClassPassenger;
import project.passenger_container.LuxuryPassenger;
import project.passenger_container.Passenger;
import project.airline_container.Airline;
import project.airline_container.aircraft_container.Aircraft;
import project.airport_container.Airport;

public class DataReader {

	public static void readAll(String inputFile) {
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			
			String[] line = reader.readLine().split(" ");
			Airline.getInstance().setMaxAircraftCount(Integer.parseInt(line[0]));
			int A = Integer.parseInt(line[1]), P = Integer.parseInt(line[2]);
			
			line = reader.readLine().split(" ");
			Aircraft.setOperationFeeList(line);
			Airline.getInstance().setOperationalCost(line[4]);			
			
			if (A == 0 || P == 0) {
				System.out.println("Empty input");
				reader.close();
				return;
			}
			
			String[] tokens = null;
			while(A-- > 0) {
				
				tokens = reader.readLine().replaceAll(",|:", "").split("\\s+");
				if (tokens.length != 7) {
					System.out.println("Invalid input (wrong number of airport tokens) : " + Arrays.toString(tokens) + " (expected 7 - given " + tokens.length + ")");
					reader.close();
					return;
				}
				
				Airline.getInstance().addAirport(tokens[0], Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2]),
						Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]), Double.parseDouble(tokens[5]), Integer.parseInt(tokens[6]));
				
			}
			
			while(P-- > 0) {
				
				tokens = reader.readLine().replaceAll("[,:\\[\\]]", "").split("\\s+");
				if (tokens.length < 6) {
					System.out.println("Invalid input (wrong number of passenger tokens) : " + Arrays.toString(tokens) + " (expected at least 6 - given " + tokens.length + ")");
					reader.close();
					return;
				}
				
				Passenger passenger = createPassenger(tokens);
				findAirport(Long.parseLong(tokens[3])).addPassenger(passenger);
				ArrayList<Airport> destinations = new ArrayList<>();
				for (int i = 5; i < tokens.length; i++) {
					long id = Long.parseLong(tokens[i]);
					destinations.add(findAirport(id));
					Airline.getInstance().addDestination(id);
				}
				passenger.setDestinations(destinations);
				
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Invalid input");
			e.printStackTrace();
		}
	}
	
	private static Airport findAirport(long ID) {
		for(Airport airport: Airline.getInstance().getAirports()) {
			if (airport.equals(ID)) {
				return airport;
			}
		}
		System.out.println("Airport could not be found");
		return null;
	}
	
	private static Passenger createPassenger(String[] tokens) {
		switch (tokens[0]) {
			case "economy":
				return new EconomyPassenger(Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2]), Integer.parseInt(tokens[3]), null);
			case "business":
				return new BusinessPassenger(Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2]), Integer.parseInt(tokens[3]), null);
			case "first":
				return new FirstClassPassenger(Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2]), Integer.parseInt(tokens[3]), null);
			case "luxury":
				return new LuxuryPassenger(Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2]), Integer.parseInt(tokens[3]), null);
		}
		System.out.println("Wrong input : invalid type of passenger = " + tokens[0]);
		return null;
	}

}
