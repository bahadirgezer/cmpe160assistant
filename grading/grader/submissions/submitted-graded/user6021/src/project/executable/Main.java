package project.executable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import project.airline.Airline;

/**
 * @author Kristina Trajkovski
 * 
 * In my project, all of the calculations and logs are calculated and set in the Airline class
 * 
 * The main class reads the input file - the variables read are accordingly assigned in the Airline class,
 * calls main method of the Airline class which will do all of the flight operations,
 * and writes out the logs collected by the Airline.
 * 
 * If no flights were made in the regular flight selection, the main method calls the emergency flight method;
 * 
 * The names of the input and output files are given as arguments.
 *
 */
public class Main {
	public static void main(String[] args) throws IOException {
		File file = new File(args[0]);
		Scanner sc;
		@SuppressWarnings("resource") FileWriter myWriter = new FileWriter(args[1]);
		sc = new Scanner(file);

		int maxAircraftCount = sc.nextInt();
		int nofAirports = sc.nextInt();
		int nofPassengers = sc.nextInt();
		Airline myAirline = new Airline(maxAircraftCount);
		
		double[] operationFees = new double[4];
		operationFees[0] = sc.nextDouble();
		operationFees[1] = sc.nextDouble();
		operationFees[2] = sc.nextDouble();
		operationFees[3] = sc.nextDouble();
		myAirline.setOperationFees(operationFees);
		double OperationalCost = sc.nextDouble();
		myAirline.setOperationalCost(OperationalCost);
		sc.nextLine();
		for (int i=0; i<nofAirports; i++) {
			String line = sc.nextLine().replaceAll(",", "").replaceAll(":", "");
			Scanner sc1 = new Scanner(line);
			String type = sc1.next();
			int ID = sc1.nextInt();
			double x = sc1.nextDouble();
			double y = sc1.nextDouble();
			double fuelCost = sc1.nextDouble();
			double operationFee = sc1.nextDouble();
			int aircraftCapacity = sc1.nextInt();

			myAirline.createAirport(type, ID, x, y, fuelCost, operationFee, aircraftCapacity);
			sc1.close();
		}

		for (int i=0; i<nofPassengers; i++) {
			String line = sc.nextLine().replaceAll(",", "").replaceAll(": ", "").replace("[", "").replace("]", "");
			Scanner sc1 = new Scanner(line);
			String type = sc1.next();
			Long ID = Long.parseUnsignedLong(sc1.next());
			double weight = sc1.nextDouble();
			int baggageCount = sc1.nextInt();
			String allDestinations = sc1.nextLine();
			Scanner sc2 = new Scanner(allDestinations);
			ArrayList<Integer> destinations = new ArrayList<Integer>();
			while(sc2.hasNextInt()) {
				destinations.add(sc2.nextInt());
			}
			myAirline.addPassenger(type , ID, weight, baggageCount, destinations);
			sc2.close();
			sc1.close();
		}
		myAirline.typeFlights(Airline.myRegs,"major");
		myAirline.typeFlights(Airline.myRegs,"hub");
		myAirline.typeFlights(Airline.myMajors,"hub");
		myAirline.typeFlights(Airline.myHubs,"hub");
		myAirline.typeFlights(Airline.myHubs,"major");
		myAirline.typeFlights(Airline.myHubs,"regional");
		myAirline.typeFlights(Airline.myMajors,"regional");
		double profit = myAirline.getProfit();
		if (profit == 0) {
			myAirline.extraFlight();
		}
		myWriter.write(Airline.log);
		profit = myAirline.getProfit();
		myWriter.write(profit+"");
		sc.close();
		myWriter.close();
	}
}
