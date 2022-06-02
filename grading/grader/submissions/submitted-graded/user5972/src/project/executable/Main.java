package project.executable;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


import project.airline.Airline;




public class Main {

	public static void main(String[] args) {
		// No aircraft classes
		String fileName = args[0];
		String outputFileName = args[1];
		try {
		      File file = new File(outputFileName);
		      if (file.createNewFile()) {
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		Scanner fileScanner = new Scanner(fileName);
		int maxAircraftsAllowed = fileScanner.nextInt();
		int numberOfAirports = fileScanner.nextInt();
		int numberOfPassenger = fileScanner.nextInt();
		double[] aircraftOFArray = new double[4]; 
		for (int i=0; i<4; i++) {
			aircraftOFArray[i] = fileScanner.nextDouble();
		}
		double operationalCost = fileScanner.nextDouble();
		Airline loungeAirline = new Airline(maxAircraftsAllowed, operationalCost, aircraftOFArray, outputFileName);
		for (int i=0; i<numberOfAirports;i++) {
			String line = fileScanner.nextLine();
			String[] line1 = line.split(" : ");
			String type = line1[0];
			String[] line2 = line1[1].split(", ");
			int ID = Integer.parseInt(line2[0]);
			double x = Double.parseDouble(line2[1]);
			double y = Double.parseDouble(line2[2]);
			double fuelCost = Double.parseDouble(line2[3]);
			double operationFee = Double.parseDouble(line2[4]);
			int aircraftCapacity = Integer.parseInt(line2[5]);
			loungeAirline.newAirport(type, ID, x, y, fuelCost, operationFee, aircraftCapacity);
		}
		for (int i=0; i<numberOfPassenger;i++) {
			String line = fileScanner.nextLine();
			String[] line1 = line.split(" : ");
			String type = line1[0];
			String[] line2 = line1[1].split(", ");
			int ID = Integer.parseInt(line2[0]);
			double weight = Double.parseDouble(line2[1]);
			int baggageCount = Integer.parseInt(line2[2]);
			ArrayList<Integer> destinationList = new ArrayList<Integer>();
			for (int j = 3; j<line2.length; j++) {
				destinationList.add(Integer.parseInt(line2[j].replaceAll("[^a-zA-Z0-9]","")));
			}
			loungeAirline.newPassenger(type, ID, weight, baggageCount, destinationList);
		}
		fileScanner.close();
		loungeAirline.tranferOnePerson();
	}
}
