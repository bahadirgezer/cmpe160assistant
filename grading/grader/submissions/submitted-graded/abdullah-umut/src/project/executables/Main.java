package project.executables;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import project.airlines.Airline;

public class Main {
	// Inputs are translated to meaningful data structures and necessary flight operations are called.
	public static void main(String[] args) {
		int maximumAircrafts = 0;
		int numberOfAirports;
		int numberOfPassengers;
		double[] aircraftFees = new double[4];
		double operationalCost = 0;
		Object[][] AirportInfos = new Object[1][1];
		Object[][] PassengerInfos = new Object[1][1];
		try {
			File file = new File(args[0]);
			Scanner scanner = new Scanner(file);
			String[] first = scanner.nextLine().split(" ");
			maximumAircrafts = Integer.parseInt(first[0].replaceAll("[^0-9]", ""));
			numberOfAirports = Integer.parseInt(first[1].replaceAll("[^0-9]", ""));
			numberOfPassengers = Integer.parseInt(first[2].replaceAll("[^0-9]", ""));
			String[] costs = scanner.nextLine().split(" ");
			aircraftFees[0] = Double.parseDouble(costs[0].replaceAll("[^\\d.]", ""));
			aircraftFees[1] = Double.parseDouble(costs[1].replaceAll("[^\\d.]", ""));
			aircraftFees[2] = Double.parseDouble(costs[2].replaceAll("[^\\d.]", ""));
			aircraftFees[3] = Double.parseDouble(costs[3].replaceAll("[^\\d.]", ""));
			operationalCost = Double.parseDouble(costs[4].replaceAll("[^\\d.]", ""));
			AirportInfos = new Object[numberOfAirports][7];
			for (int i = 0; i<numberOfAirports; i++) {
				Object[] airporti = new Object[7];
				String[] line = scanner.nextLine().split(" ");
				airporti[0] = line[0];
				airporti[1] = Integer.parseInt(line[2].replaceAll("[^0-9]", ""));
				airporti[2] = Double.parseDouble(line[3].replaceAll("[^\\d.]", ""));
				airporti[3] = Double.parseDouble(line[4].replaceAll("[^\\d.]", ""));
				airporti[4] = Double.parseDouble(line[5].replaceAll("[^\\d.]", ""));
				airporti[5] = Double.parseDouble(line[6].replaceAll("[^\\d.]", ""));
				airporti[6] = Integer.parseInt(line[7].replaceAll("[^0-9]", ""));
				AirportInfos[i] = airporti;
			}
			
			PassengerInfos = new Object[numberOfPassengers][5];
			for (int i = 0; i<numberOfPassengers; i++) {
				Object[] passi = new Object[5];
				String[] line = scanner.nextLine().split(" ");
				passi[0] = line[0];
				passi[1] = Long.parseLong(line[2].replaceAll(",",""));
				passi[2] = Double.parseDouble(line[3].replaceAll(",",""));
				passi[3] = Integer.parseInt(line[4].replaceAll(",",""));
				passi[4] = new ArrayList<Integer>();
				for (int j = 5; j<line.length ; j++)
					((ArrayList<Integer>) passi[4]).add(Integer.parseInt(line[j].replaceAll("[^0-9]", "")));
				PassengerInfos[i] = passi;
			}
			scanner.close();
		} catch (FileNotFoundException e) {}
		
		Airline LoungeAirlines = new Airline(maximumAircrafts, operationalCost, AirportInfos, PassengerInfos, aircraftFees);
		Hashtable<Integer, ArrayList<Integer>> h = LoungeAirlines.getSomePassengerInfo(3);
		LoungeAirlines.addAircraft(1,450,0,0, (int) AirportInfos[3][1]);
		for (int i = 0; i < 4; i++) {
			if (h.containsKey(i) && h.get(i).size() != 0) {
					int dist = LoungeAirlines.legalDistance(0, h.get(i));
					LoungeAirlines.flight(i,0,0,dist);
					break;
				}
			}
		LoungeAirlines.addRevenueToLog();
		System.out.println(LoungeAirlines.getLog());
		try {
			FileWriter writer = new FileWriter(args[1]);
			writer.write(LoungeAirlines.getLog());
			writer.close();
		} catch (IOException e) {}
		
	}
	
}