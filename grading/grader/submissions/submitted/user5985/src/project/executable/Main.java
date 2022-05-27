package project.executable;

import java.io.File;


import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

// ONLY IMPORTED CLASS IS AIRLINE
import project.airline.Airline;


public class Main {		
	public static void main(String[] args) {
		
		PrintStream fileOut = null;
		try {
			fileOut = new PrintStream(args[1]);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.setOut(fileOut);
		
		File file = new File(args[0]);
		Scanner data = null;
		try {
			data = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<String> datas = new ArrayList<>();
		while (data.hasNextLine()) {
			datas.add(data.nextLine());
		}
		
		int M, A, P;
		M = Integer.parseInt(datas.get(0).split(" ")[0]);
		A = Integer.parseInt(datas.get(0).split(" ")[1]);
		P = Integer.parseInt(datas.get(0).split(" ")[2]);
		
		double prop, widebody, rapid, jet, operational_cost;
		
		prop = Double.parseDouble(datas.get(1).split(" ")[0]);
		widebody = Double.parseDouble(datas.get(1).split(" ")[1]);
		rapid = Double.parseDouble(datas.get(1).split(" ")[2]);
		jet = Double.parseDouble(datas.get(1).split(" ")[3]);
		operational_cost = Double.parseDouble(datas.get(1).split(" ")[4]);
		
		Airline airline = new Airline(M, operational_cost);
		
		for (int b = 2; b < 2 + A; b++) {
			String[] temp = datas.get(b).replace(',', ' ').split(" ");
						
			String airportType = temp[0];
			int ID = Integer.parseInt(temp[2]);
			double x = Double.parseDouble(temp[4]);
			double y = Double.parseDouble(temp[6]);
			double fuelCost = Double.parseDouble(temp[8]);
			int operationFee = Integer.parseInt(temp[10]);
			int aircraftCapacity = Integer.parseInt(temp[12]);	

			if (airportType.equals("regional") == true) {
				airline.addRegionalAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
			}
			else if (airportType.equals("major") == true)
				airline.addMajorAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
			
			else
				airline.addHubAirport(ID, x, y, fuelCost, operationFee, aircraftCapacity);
		}
		for (int c = 2 + A; c < 2 + A + P; ++c) {
			ArrayList<String> passangerData = new ArrayList<>();
			
			String temp[] = datas.get(c).replace(':', ' ').replace(',', ' ').replace('[', ' ').replace(']', ' ').split(" ");
			for (int b = 0; b < temp.length; b++) {
				if (temp[b].equals("") == false) 
					passangerData.add(temp[b]);	
			}
			
			long ID;
			double weight;
			int baggageCount;
			ArrayList<Integer> destinationsInt = new ArrayList<>();
			
			if (passangerData.get(0).equals("economy")) {
				ID = Long.parseLong(passangerData.get(1));
				weight = Double.parseDouble(passangerData.get(2));
				baggageCount = Integer.parseInt(passangerData.get(3));
				
				for (int k = 4; k < passangerData.size(); k++) {
					destinationsInt.add(Integer.parseInt(passangerData.get(k)));
				}
				airline.addEconomyPassenger(ID, weight, baggageCount, destinationsInt);
			}
			else if (passangerData.get(0).equals("business")) {
				ID = Long.parseLong(passangerData.get(1));
				weight = Double.parseDouble(passangerData.get(2));
				baggageCount = Integer.parseInt(passangerData.get(3));
				
				for (int k = 4; k < passangerData.size(); k++) {
					destinationsInt.add(Integer.parseInt(passangerData.get(k)));
				}
				airline.addBusinessPassenger(ID, weight, baggageCount, destinationsInt);
			}
			
			else if (passangerData.get(0).equals("first")) {
				ID = Long.parseLong(passangerData.get(1));
				weight = Double.parseDouble(passangerData.get(2));
				baggageCount = Integer.parseInt(passangerData.get(3));
				
				for (int k = 4; k < passangerData.size(); k++) {
					destinationsInt.add(Integer.parseInt(passangerData.get(k)));
				}
				airline.addFirstPassenger(ID, weight, baggageCount, destinationsInt);
			}
			
			else if (passangerData.get(0).equals("luxury")) {
				ID = Long.parseLong(passangerData.get(1));
				weight = Double.parseDouble(passangerData.get(2));
				baggageCount = Integer.parseInt(passangerData.get(3));
				
				for (int k = 4; k < passangerData.size(); k++) {
					destinationsInt.add(Integer.parseInt(passangerData.get(k)));
				}
				airline.addLuxuryPassenger(ID, weight, baggageCount, destinationsInt);
			}
		}
		
		// FLIGH DECISION
		
		int[] flightData = airline.flightDecision();
		// 0 -> from which airport
		// 1 -> to which airport
		// 2 -> with what kind of aircraft
		// 3 -> economy :: seat type 0
		// 4 -> business :: seat type 1
		// 5 -> first + luxury :: seat type 2

		// AIRCRAFT CREATION
		
		airline.addWideBodyPassengerAircraft(airline.getAirports().get(flightData[0]), widebody, 450, 0, 0);
			
		// FUEL THE AIRCRAFT
			
		airline.fillUp(airline.numberOFAircrafts() - 1);
				
		// BOARD PASSENGERS TO THE AIRCRAFT
			
		airline.boardAllPassangers(airline.getAirports().get(flightData[0]), airline.getAirports().get(flightData[1]), airline.numberOFAircrafts() - 1);
			
		// FLYING OPERATION
			
		airline.fly(airline.getAirports().get(flightData[1]), airline.numberOFAircrafts() - 1);
			
		// TIME TO GET REVENUE, DISEMBARKATION
			
		airline.unloadAllPassengers(airline.getAirports().get(flightData[1]), airline.numberOFAircrafts() - 1);				
		// RESULT OF THE FLIGHT
		
		double result = airline.returnProfit();
		System.out.println(result);
		
	}
			
}
