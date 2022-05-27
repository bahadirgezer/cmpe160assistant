package executable;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import airline.Airline;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner line;
		String[] list;
		File input = new File(args[0]);
		FileWriter f_writer = new FileWriter(args[1]);
		Scanner reader = new Scanner(input);
		line = new Scanner(reader.nextLine());
		int maxAircrafts = line.nextInt();
		int a = line.nextInt();
		int p = line.nextInt();
		list = reader.nextLine().split(" ");
		double propFee = Double.valueOf(list[0].strip());
		double widebodyFee = Double.valueOf(list[1].strip());
		double rapidFee = Double.valueOf(list[2].strip());
		double jetFee = Double.valueOf(list[3].strip());
		double operationalCost = Double.valueOf(list[4].strip());
		
		Airline myAirline = new Airline(f_writer, maxAircrafts,operationalCost, propFee, widebodyFee, rapidFee, jetFee);
		
		String airportType;
		int airportID, aircraftCapacity;
		double x, y, fuel_cost, operationFee;
		for(int airportNo = 1;  airportNo <= a; airportNo++ ){
			list = reader.nextLine().split(":");
			airportType = list[0].strip();
			list = list[1].split(","); 
			airportID = Integer.valueOf(list[0].strip());
			x = Double.valueOf(list[1].strip());
			y = Double.valueOf(list[2].strip());
			fuel_cost = Double.valueOf(list[3].strip());
			operationFee = Double.valueOf(list[4].strip());
			aircraftCapacity = Integer.valueOf(list[5].strip());
			myAirline.createAirport(airportType, airportID, x, y, aircraftCapacity, operationFee, fuel_cost);
		}
		
		String passengerType;
		long passengerID;
		int baggageCount;
		double weight;
		for(int passengerNo = 1;  passengerNo <= p; passengerNo++ ){
			ArrayList<Integer> destinations = new ArrayList<Integer>();
			list = reader.nextLine().split(":");
			passengerType = list[0].strip();
			list = list[1].split(","); 
			passengerID = Long.valueOf(list[0].strip());
			weight = Double.valueOf(list[1].strip());
			baggageCount = Integer.valueOf(list[2].strip());
			for(int b = 3; b < list.length; b++) {
				destinations.add(Integer.valueOf(list[b].replaceAll("[\\[\\]]", "").strip()));
			}
			myAirline.createPassenger(passengerType, passengerID, weight, baggageCount, destinations);
		}
		
		myAirline.makeProfit();
		
		System.out.println("total profit: " + myAirline.getProfit());
		
		f_writer.write(Double.toString(myAirline.getProfit()));
		f_writer.close();		
	}
}
