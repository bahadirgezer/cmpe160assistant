package project.executable;
import project.airline.Airline;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class Main {

	public static void main(String[] args) {
		
		PrintStream outputFile = null;
		try {
			outputFile = new PrintStream(args[1]);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.setOut(outputFile);
		
		File inputFile = new File(args[0]);
			//Gets first inputs in first two lines
		try {
			Scanner sc = new Scanner(inputFile);
			String[] firstInpArray = sc.nextLine().split(" ");
			int maxAircraftCount = Integer.parseInt(firstInpArray[0]);
			int numberOfAirports = Integer.parseInt(firstInpArray[1]);
			int numberOfPassengers = Integer.parseInt(firstInpArray[2]);
			String[] secondInpArray = sc.nextLine().split(" ");
			double propOperationFee = Integer.parseInt(secondInpArray[0]);
			double widebodyOperationFee = Double.parseDouble(secondInpArray[1]);
			double rapidOperationFee = Double.parseDouble(secondInpArray[2]);
			double jetOperationFee = Double.parseDouble(secondInpArray[3]);
			double operationalCost = Double.parseDouble(secondInpArray[4]);
			
			Airline airline = new Airline(maxAircraftCount, operationalCost, jetOperationFee, propOperationFee, rapidOperationFee, widebodyOperationFee);
			int hubCounter = 0; //Counter that count how many hub airports are created, used later
			
			//Gets airport inputs
			for (int i = 0; i < numberOfAirports; i++) {
				String[] arr = sc.nextLine().split(" : ");
				String[] ithAirportInpArray = arr[1].split(", ");
				String airportType = arr[0];
				int ID = Integer.parseInt(ithAirportInpArray[0]);
				double x = Double.parseDouble(ithAirportInpArray[1]);
				double y = Double.parseDouble(ithAirportInpArray[2]);
				double fuelCost = Double.parseDouble(ithAirportInpArray[3]);
				double operationFee = Double.parseDouble(ithAirportInpArray[4]);
				int aircraftCapacity = Integer.parseInt(ithAirportInpArray[5]);
				
				if (airportType.equals("hub")) {
					hubCounter++;
					airline.createHubAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
				}
				else if (airportType.equals("major")) {
					airline.createMajorAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
				}
				else if (airportType.equals("regional")) {
					airline.createRegionalAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
				}
			}
			// gets passenger inputs
			for (int i = 0; i < numberOfPassengers; i++) {
				String[] arr = sc.nextLine().split(" : ");
				String[] ithPassengerArray = arr[1].split(", ");
				String passengerType = arr[0];
				long ID = Long.parseLong(ithPassengerArray[0]);
				double weight = Double.parseDouble(ithPassengerArray[1]);
				int baggageCount = Integer.parseInt(ithPassengerArray[2]);
				int sizeOfArray = ithPassengerArray.length;
				ArrayList<Integer> destinationIDs = new ArrayList<Integer>();
				int dest1 = Integer.parseInt(ithPassengerArray[3].replace("[", ""));
				destinationIDs.add(dest1);
				if (sizeOfArray > 5) {
				for (int j = 0; j < sizeOfArray - 5; j++) {
					int destAny = Integer.parseInt(ithPassengerArray[4+j]);
					destinationIDs.add(destAny);
				}
				}
				int destLast = Integer.parseInt(ithPassengerArray[sizeOfArray - 1].replace("]", ""));
				destinationIDs.add(destLast);
				if (passengerType.equals("economy")) {
					airline.createEconomyPassenger(ID, weight, baggageCount, airline.createDestinationsForPassenger(destinationIDs));
				}
				else if (passengerType.equals("business")) {
					airline.createBusinessPassenger(ID, weight, baggageCount, airline.createDestinationsForPassenger(destinationIDs));
				}
				else if (passengerType.equals("first")) {
					airline.createFirstClassPassenger(ID, weight, baggageCount, airline.createDestinationsForPassenger(destinationIDs));
				}
				
			} //Creates a wide body aircraft at a random airport
			sc.close();
			for (int i = 0; i < maxAircraftCount; i++) {
			airline.createWideBodyPassengerAircraft(airline.getRandomAirport());
			}
			airline.autoPilot(hubCounter); //Runner of Airline, flies random airports to closest hubs with a logic that do not come back to visited airports
			for (String str : airline.getLogArray()) {
				System.out.println(str);
			}
			System.out.println(Double.toString(airline.getProfit()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
}
}
