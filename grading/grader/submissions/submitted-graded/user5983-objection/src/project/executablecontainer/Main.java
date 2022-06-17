package project.executablecontainer;


import project.airlinecontainer.Airline;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//Reads the input and output file names and then reads the input file
		try {
			Scanner file = new Scanner(System.in);
			String inputFile = args[0];
			String outputFile = args[1];
			
			Scanner input = new Scanner(new File(inputFile));
			int maxAircraftCount,airportCount,passengerCount;
			double propFee,widebodyFee,rapidFee,jetFee,operationalCost;


			maxAircraftCount = input.nextInt();
			airportCount = input.nextInt();
			passengerCount = input.nextInt();

			propFee = input.nextDouble();
			widebodyFee = input.nextDouble();
			rapidFee = input.nextDouble();
			jetFee = input.nextDouble();
			operationalCost = input.nextDouble();
			input.nextLine();
			
			//Creates the Airline object with the given values
			Airline airline = new Airline(maxAircraftCount,operationalCost,propFee,widebodyFee,rapidFee,jetFee);
			airline.setOutputFile(outputFile); //Sets the outputFile to write, since it is done in Airline.java

			
			//Creates Airport objects using the given informations
			while(input.hasNext() && airportCount-- > 0){


				String[] splitted = input.nextLine().split(" :");
				String[] data = splitted[1].split(",");

				String type =  splitted[0];
				long id = Long.parseLong(data[0].trim());
				int xCoor =  Integer.parseInt(data[1].trim());
				int yCoor =  Integer.parseInt(data[2].trim());
				double fuelPrice =  Double.parseDouble(data[3].trim());
				double operationFee =  Double.parseDouble(data[4].trim());
				int capacity =  Integer.parseInt(data[5].trim());

				switch (type){
					case "hub":
						airline.createHubAirport(id,xCoor,yCoor,fuelPrice,operationFee,capacity);
						break;
					case "regional":
						airline.createRegionalAirport(id,xCoor,yCoor,fuelPrice,operationFee,capacity);
						break;

					case "major":
						airline.createMajorAirport(id,xCoor,yCoor,fuelPrice,operationFee,capacity);
						break;
				}


			}
			
			
			//Creates Passenger objects using the given informations
			while(input.hasNext() && passengerCount-- > 0){
				String[] splitted = input.nextLine().split(" :");

				String[] raw = splitted[1].split("\\[");

				String[] data = raw[0].split(",");
				String[] rawDestinations = raw[1].split("]")[0].split(",");



				String passengerType =  splitted[0];
				long id =  Long.parseLong(data[0].trim());
				double weight =  Double.parseDouble(data[1].trim());
				int baggageCount =  Integer.parseInt(data[2].trim());
				ArrayList<Long> destinations = new ArrayList<>();

				for (String d : rawDestinations) {
					 destinations.add(Long.parseLong(d.trim()));
				}

				switch (passengerType){
					case "economy":
						airline.createEconomyPassenger(id,weight,baggageCount,destinations);
						break;
					case "business":
						airline.createBusinessPassenger(id,weight,baggageCount,destinations);
						break;
					case "first":
						airline.createFirstClassPassenger(id,weight,baggageCount,destinations);
						break;
					case "luxury":
						airline.createLuxuryPassenger(id,weight,baggageCount,destinations);
						break;
				}

			}
			input.close();

			
			airline.fillAirports();   //Creates Aircraft objects in Airline.java
			airline.distributePassengers();  //Loads passengers to the aircrafts
			airline.flyAllAircrafts();		//Flies loaded aircrafts to target locations
			Double finalProfit= airline.revenues-airline.expenses;
			airline.writeToOutput(" "+finalProfit.toString()+" "); //Prints the finalProfit to the output file


		}


		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		


	}




}
