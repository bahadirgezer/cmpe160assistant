package project.executable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

import project.airline.Airline;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		//File file = new File("src/project/input3.txt");
		File file = new File(args[0]);
		Scanner scan = new Scanner(file);
		scan.useLocale(Locale.ENGLISH);
		int maxAircrafts = scan.nextInt();
		int numberOfAirports = scan.nextInt();
		int numberOfPassengers = scan.nextInt();
		double propOperationFee = scan.nextDouble();
		double widebodyOperationFee = scan.nextDouble();
		double rapidOperationFee = scan.nextDouble();
		double jetOperationFee = scan.nextDouble();
		double operationalCost = scan.nextDouble();
		Airline airline = new Airline(maxAircrafts,operationalCost,propOperationFee,widebodyOperationFee,rapidOperationFee,jetOperationFee,args[1],numberOfAirports);
		for (int i = 0;i<numberOfAirports;i++) { // reading airport input
			String airportType = scan.next();
			scan.next();
			String line = scan.nextLine();
			String[] words = line.split(",");
			int airportID = Integer.parseInt(words[0].replaceAll("\\s+",""));
			int x = Integer.parseInt(words[1].replaceAll("\\s+",""));
			int y = Integer.parseInt(words[2].replaceAll("\\s+",""));
			double fuelCost = Double.parseDouble(words[3].replaceAll("\\s+",""));
			double operationFee = Double.parseDouble(words[4].replaceAll("\\s+",""));
			int aircraftCapacity = Integer.parseInt(words[5].replaceAll("\\s+",""));
			if (airportType.equalsIgnoreCase("hub")) {
				airline.createAirport(airportID,0,x,y,fuelCost,operationFee,aircraftCapacity,i);
			}
			if (airportType.equalsIgnoreCase("major")) {
				airline.createAirport(airportID,1,x,y,fuelCost,operationFee,aircraftCapacity,i);
			}
			if (airportType.equalsIgnoreCase("regional")) {
				airline.createAirport(airportID,2,x,y,fuelCost,operationFee,aircraftCapacity,i);
			}
			
		}
		for (int j = 0;j<numberOfPassengers;j++) { //reading passenger input
			ArrayList<Integer> destinations = new ArrayList<Integer>();
			String passengerType = scan.next();
			scan.next();
			String line = scan.nextLine();
			String words[] = line.split(",");
			long passengerID = Long.parseLong(words[0].replaceAll("\\s+","")); // i defined this as long instead of int because some passengers have ids bigger than int limit
			double weight = Double.parseDouble(words[1].replaceAll("\\s+",""));    
			int baggageCount = Integer.parseInt(words[2].replaceAll("\\s+",""));
			for (int k = 0;k<words.length-3;k++) {
				destinations.add(Integer.parseInt(words[k+3].replaceAll("\\s+","").replaceAll("\\[", "").replaceAll("\\]","")));
			}
			if (passengerType.equalsIgnoreCase("economy")){
				airline.createPassenger(passengerID,weight,baggageCount,destinations,0);
			}
			if (passengerType.equalsIgnoreCase("business")){
				airline.createPassenger(passengerID,weight,baggageCount,destinations,1);
			}
			if (passengerType.equalsIgnoreCase("first")){
				airline.createPassenger(passengerID,weight,baggageCount,destinations,2);
			}
			if (passengerType.equalsIgnoreCase("luxury")){
				airline.createPassenger(passengerID,weight,baggageCount,destinations,3);
			}
		}
		for (int i = 0;i<1000;i++) { //trying to do 1000 efficient flights
			int[] route = airline.calculateBestDestinations(airline.airportDictionary);
			try {
				airline.arrangeFlight(route[0], route[1]);
			}
			catch(Exception e) { //exception happens when there is no suitable flight
				//System.out.println(i);
				break;
			}
		}
		if (airline.profits == 0) { // activates bfs if there were no flights
			airline.calculateLongRangeDestinations();
		}
		airline.createOutput(Double.toString(airline.profits-airline.expenses));
		airline.end(); // saves and closes the output file
	}

}
