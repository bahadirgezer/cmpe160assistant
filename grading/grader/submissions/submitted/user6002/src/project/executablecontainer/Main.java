package project.executablecontainer;

import project.airlinecontainer.Airline;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		String inputFileName = args[0]; //Filenames from command line arguments are assigned to variables
		String outputFileName = args[1];
		
		File inputFile = new File(inputFileName); //Input file is assigned to a variable
		Scanner reader;
		try {
		      reader = new Scanner(inputFile); //New scanner is created to read lines from inputFile
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		      reader = null;
		    }
		
		int maxAircraftCount; //Maximum number of aircrafts
		int numberOfAirports; //Number of airports
		int numberOfPassengers; //Number of passengers
		
		String firstLineString = reader.nextLine(); //First line is read, split and assigned to proper variables
		String[] firstLineArray = firstLineString.split(" ");
		maxAircraftCount = Integer.parseInt(firstLineArray[0]);
		numberOfAirports = Integer.parseInt(firstLineArray[1]);
		numberOfPassengers = Integer.parseInt(firstLineArray[2]);
		
		double propOperationFee;
		double widebodyOperationFee;
		double rapidOperationFee;
		double jetOperationFee;
		double operationalCost;
		
		String[] secondLineArray = reader.nextLine().split(" "); //Second line is read, split and assigned to proper variables
		propOperationFee = Double.parseDouble(secondLineArray[0]);
		widebodyOperationFee = Double.parseDouble(secondLineArray[1]);
		rapidOperationFee = Double.parseDouble(secondLineArray[2]);
		jetOperationFee = Double.parseDouble(secondLineArray[3]);
		operationalCost = Double.parseDouble(secondLineArray[4]);
		
		//Airline object is created
		Airline loungeAirlines = new Airline(maxAircraftCount, operationalCost, propOperationFee, widebodyOperationFee, rapidOperationFee, jetOperationFee);
		
		for (int i = 0; i < numberOfAirports; i++) { //This loop will go through lines that contain airport information and create airport objects
			int airportType;
			int ID;
			double x;
			double y;
			double fuelCost;
			double operationFee;
			int aircraftCapacity;
			
			String[] airportLineArray = reader.nextLine().split(" : ");
			String airportTypeString = airportLineArray[0];
			if (airportTypeString.equals("hub")) {
				airportType = 0;
			} else if (airportTypeString.equals("major")) {
				airportType = 1;
			} else {
				airportType = 2;
			}
			String[] airportLineArray2 = airportLineArray[1].split(", ");
			ID = Integer.parseInt(airportLineArray2[0]);
			x = Double.parseDouble(airportLineArray2[1]);
			y = Double.parseDouble(airportLineArray2[2]);
			fuelCost = Double.parseDouble(airportLineArray2[3]);
			operationFee = Double.parseDouble(airportLineArray2[4]);
			aircraftCapacity = Integer.parseInt(airportLineArray2[5]);
			loungeAirlines.createAirport(airportType, ID, x, y, fuelCost, operationFee, aircraftCapacity); //Airport object is created using given information from input
			
		}
		
		for (int j = 0; j < numberOfPassengers; j++) {
			int passengerType; 
			long ID;
			double weight;
			int baggageCount;
			String destinationsString;
			
			String[] passengerLineArray = reader.nextLine().split(" : ");
			String passengerTypeString = passengerLineArray[0];
			if (passengerTypeString.equals("economy")) {
				passengerType = 0;
			} else if (passengerTypeString.equals("business")) {
				passengerType = 1;
			} else if (passengerTypeString.equals("first")) {
				passengerType = 2;
			} else {
				passengerType = 3;
			}
			String[] passengerLineArray2 = passengerLineArray[1].split(", \\[");
			String[] passengerLineArray3 = passengerLineArray2[0].split(", ");
			ID = Long.parseLong(passengerLineArray3[0]);
			weight = Double.parseDouble(passengerLineArray3[1]);
			baggageCount = Integer.parseInt(passengerLineArray3[2]);
			destinationsString = passengerLineArray2[1].replaceAll("]", "");
			
			loungeAirlines.createPassenger(passengerType, ID, weight, baggageCount, destinationsString);
		}
		
		for (int k = 0; k < maxAircraftCount; k++) {
			loungeAirlines.createAircraft(loungeAirlines.getAirports().get(k % loungeAirlines.getAirports().size()), 1);
			loungeAirlines.setAllEconomy(loungeAirlines.getAircrafts().get(k));
			for (int l = 1; l < loungeAirlines.getAirports().size(); l++) {				
				for (int m = 0; m < loungeAirlines.getAircrafts().get(k).getCurrentAirport().getPassengersAtAirport().size(); m++) {
					loungeAirlines.loadPassenger(loungeAirlines.getAircrafts().get(k).getCurrentAirport().getPassengersAtAirport().get(m), loungeAirlines.getAircrafts().get(k).getCurrentAirport(), k);
				}
				
				loungeAirlines.maxRefuel(k);
				
				loungeAirlines.fly(loungeAirlines.getAirports().get(((k % loungeAirlines.getAirports().size()) + l) % loungeAirlines.getAirports().size()), k);
				
				
				for (int n = 0; n < loungeAirlines.getAircrafts().get(k).getPassengersAtAircraft().size(); n++) {
					loungeAirlines.unloadPassenger(loungeAirlines.getAircrafts().get(k).getPassengersAtAircraft().get(n), k);
				}
			}
		}
		
		loungeAirlines.writeTotalRevenue();
		
		try {
		      FileWriter writer = new FileWriter(outputFileName);
		      writer.write(loungeAirlines.sb.toString());
		      writer.close();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }		
	}

}
