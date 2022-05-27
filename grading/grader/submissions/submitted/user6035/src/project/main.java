package project;

import java.util.ArrayList;
import java.util.Scanner;

import project.airlines.Airline;

public class main {

	public static void main(String[] args) {

		int maxNumOfAircrafts, numOfAirports, numOfPassengers;
		double propOpFee, wideBodyOpFee, rapidOpFee, jetOpFee, operationalCost;
		
		Scanner scan = new Scanner(System.in);
		maxNumOfAircrafts = scan.nextInt();
		numOfAirports = scan.nextInt();
		numOfPassengers = scan.nextInt();
		propOpFee = scan.nextDouble();
		wideBodyOpFee = scan.nextDouble();
		rapidOpFee = scan.nextDouble();
		jetOpFee = scan.nextDouble();
		operationalCost = scan.nextDouble();
		
		Airline ourAirline= new Airline(maxNumOfAircrafts, operationalCost);
		
		for(int i = 0; i < numOfAirports; i++) { //getting the airports' inputs
			int airportID, aircraftCapacity, X, Y;
			double fuelCost, operationFee;
			String line = scan.nextLine();
			String[] arrStr = line.split(" ", -1);
			airportID = Integer.parseInt(arrStr[2]);
			aircraftCapacity = Integer.parseInt(arrStr[3]);
			X = Integer.parseInt(arrStr[4]);
			Y = Integer.parseInt(arrStr[5]);
			fuelCost = Double.parseDouble(arrStr[6]);
			operationFee = Double.parseDouble(arrStr[7]);
			
			ourAirline.addAirport(arrStr[0], airportID, aircraftCapacity, X, Y, fuelCost, operationFee);
		}
		for(int i = 0; i < numOfPassengers; i++) { //getting the passengers' input
			String passengerType;
			int ID, baggageCount;
			double weight;
			String line = scan.nextLine();
			String[] arrStr = line.split(" ", -1);
			passengerType = arrStr[0];
			ID = Integer.parseInt(arrStr[2]);
			weight = Double.parseDouble(arrStr[3]);
			baggageCount = Integer.parseInt(arrStr[4]);
			String initialLoc = arrStr[5];
			String initialLoc2 = "";
			for(int j = 1; j < initialLoc.length() - 1; j++) {
				initialLoc2 = initialLoc2 + initialLoc.charAt(j);
			}
			int initialLocation = Integer.parseInt(initialLoc2);
			
			
			ArrayList<Integer> destinations = new ArrayList<Integer>();
			
			for(int k = 6; k < arrStr.length; k++) {
				String loc = arrStr[k];
				String loc2 = "";
				for(int j = 0; j < loc.length() - 1; j++) {
					loc2 = loc2 + loc.charAt(j);
				}
				int location = Integer.parseInt(loc2);
				destinations.add(location);
			}
			
			ourAirline.addPassenger(passengerType, ID, weight, baggageCount, initialLocation, destinations);
		}
		
		scan.close();
		
		
		ourAirline.createAllEconomyAircraft(rapidOpFee);
		for(int i = 0; i < 50; i++) {
			ourAirline.loadAllPassengers();
			ourAirline.flyAll();
			ourAirline.unloadAllAvailableTransferRest();
		}
		ourAirline.writeRevenue();
	}

}
