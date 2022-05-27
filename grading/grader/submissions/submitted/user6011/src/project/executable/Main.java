package project.executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import project.airline.Airline;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrintStream fileOut = null;
		try {
			fileOut = new PrintStream(args[1]);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.setOut(fileOut);
		try {
			File file = new File(args[0]);
			Scanner reader = new Scanner(file);
			String[] firstLine = reader.nextLine().split(" ");
			int maxAircraftNumber = Integer.parseInt(firstLine[0]);
			int airportNumber = Integer.parseInt(firstLine[1]);
			int passengerNumber = Integer.parseInt(firstLine[2]);
			String[] secondLine = reader.nextLine().split(" ");
			double prop = Double.parseDouble(secondLine[0]);
			double widebody = Double.parseDouble(secondLine[1]);
			double rapid = Double.parseDouble(secondLine[2]);
			double jet = Double.parseDouble(secondLine[3]);
			double operationalCost = Integer.parseInt(secondLine[4]);
			Airline airline = new Airline(maxAircraftNumber, operationalCost);
			for (int i = 0; i < airportNumber; i++) {
				String[] line = reader.nextLine().split(" ");
				if (line[0].equals("hub")) {
					airline.createHubAirport(Integer.parseInt(line[2].split(",")[0]), Integer.parseInt(line[3].split(",")[0]), Integer.parseInt(line[4].split(",")[0]), Double.parseDouble(line[5].split(",")[0]), Double.parseDouble(line[6].split(",")[0]), Integer.parseInt(line[7]));
				}else if (line[0].equals("major")) {
					airline.createMajorAirport(Integer.parseInt(line[2].split(",")[0]), Integer.parseInt(line[3].split(",")[0]), Integer.parseInt(line[4].split(",")[0]), Double.parseDouble(line[5].split(",")[0]), Double.parseDouble(line[6].split(",")[0]), Integer.parseInt(line[7]));
				}else if (line[0].equals("regional")) {
					airline.createRegionalAirport(Integer.parseInt(line[2].split(",")[0]), Integer.parseInt(line[3].split(",")[0]), Integer.parseInt(line[4].split(",")[0]), Double.parseDouble(line[5].split(",")[0]), Double.parseDouble(line[6].split(",")[0]), Integer.parseInt(line[7]));
				}
			}
			for (int j = 0; j < passengerNumber; j++) {
				String[] psng = reader.nextLine().split(" : ");
				String[] line = psng[1].split(", ");
				int destinations[] = new int[line.length - 3];
				for (int k = 0; k < line.length - 3; k++) {
					if (k == 0) {
						destinations[0] = Integer.parseInt(line[3].replace("[", ""));
						continue;
					}else if (k == line.length - 4) {
						destinations[k] = Integer.parseInt(line[k + 3].replace("]", ""));
						break;
					}
					destinations[k] = Integer.parseInt(line[k + 3]);
				}
				switch (psng[0]) {
				case "economy":
					airline.createEconomyPassenger(Long.parseLong(line[0]), Double.parseDouble(line[1]), Integer.parseInt(line[2]), airline.arrayToArrayList(destinations));
					break;
				case "business":
					airline.createBusinessPassenger(Long.parseLong(line[0]), Double.parseDouble(line[1]), Integer.parseInt(line[2]), airline.arrayToArrayList(destinations));
					break;
				case "first":
					airline.createFirstClassPassenger(Long.parseLong(line[0]), Double.parseDouble(line[1]), Integer.parseInt(line[2]), airline.arrayToArrayList(destinations));
					break;
				case "luxury":
					airline.createLuxuryPassenger(Long.parseLong(line[0]), Double.parseDouble(line[1]), Integer.parseInt(line[2]), airline.arrayToArrayList(destinations));
					break;
				}
			}
			
			airline.createWidebodyPassengerAircraft(widebody, airline.getAirportsHashMap().get(0));
			airline.setAllEconomy(0);
			airline.fillUp(0);
			int toAirportID = airline.findClosestAirport(airline.getAirportsHashMap().get(0));
			airline.loadManyPassengers(toAirportID, 0);
			airline.fly(airline.getAirports().get(toAirportID), 0);
			airline.unloadAllPassengers(0);
			System.out.println(airline.getProfit());
			
			reader.close();
		}catch (FileNotFoundException e) {
			System.out.println("File not found.");
		      e.printStackTrace();
		}
	}
}
	
