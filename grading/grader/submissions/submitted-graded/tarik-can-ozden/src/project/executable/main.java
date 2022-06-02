package project.executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import project.airline.Airline;

public class main {

	public static void main(String[] args) {

		int maxNumOfAircrafts, numOfAirports, numOfPassengers;
		double propOpFee, wideBodyOpFee, rapidOpFee, jetOpFee, operationalCost;
		File file = new File(args[0]);
		File file2 = new File("src\\inputsoutputs\\o.txt");
		
		/*try {
			PrintStream fileOut = new PrintStream(args[0]);
			System.setOut(fileOut);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		Scanner scan = null;
		try { 
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			PrintStream stream = new PrintStream(args[1]);
			System.setOut(stream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		maxNumOfAircrafts = scan.nextInt();
		//System.out.println(maxNumOfAircrafts);
		numOfAirports = scan.nextInt();
		//System.out.println(numOfAirports);
		numOfPassengers = scan.nextInt();
		//System.out.println(numOfPassengers);
		String empty = scan.nextLine();
		String s = scan.nextLine();
		String ar[] = s.split(" ");
		propOpFee = Double.parseDouble(ar[0]);
		//System.out.println(propOpFee);
		wideBodyOpFee = Double.parseDouble(ar[1]);
		rapidOpFee = Double.parseDouble(ar[2]);;
		jetOpFee = Double.parseDouble(ar[3]);;
		operationalCost = Double.parseDouble(ar[4]);
		
		 //for reading empty line.
		
		Airline ourAirline= new Airline(maxNumOfAircrafts, operationalCost);
		
		for(int i = 0; i < numOfAirports; i++) { //getting the airports' inputs
			int airportID, aircraftCapacity, X, Y;
			double fuelCost, operationFee;
			String line = scan.nextLine();
			String[] arrStr = line.split(",", -1);
			//System.out.println(line);
			String[] typeAndID = arrStr[0].split(" : ");
			airportID = Integer.parseInt(typeAndID[1].strip());
			X = Integer.parseInt(arrStr[1].strip());
			Y = Integer.parseInt(arrStr[2].strip());
			fuelCost = Double.parseDouble(arrStr[3].strip());
			operationFee = Double.parseDouble(arrStr[4].strip());
			aircraftCapacity = Integer.parseInt(arrStr[5].strip());
			ourAirline.addAirport(typeAndID[0], airportID, aircraftCapacity, X, Y, fuelCost, operationFee);
			/*System.out.println(typeAndID[0]);
			System.out.println(airportID);
			System.out.println(X);
			System.out.println(Y);
			System.out.println(fuelCost);
			System.out.println(operationFee);
			System.out.println(aircraftCapacity);*/
		}
		
		for(int i = 0; i < numOfPassengers; i++) { //getting the passengers' input
			String passengerType;
			long ID; 
			int baggageCount;
			double weight;
			String line = scan.nextLine();
			//System.out.println(line);
			String[] arrStr = line.split(",", -1);
			String[] passTypeAndID = arrStr[0].split(" : ");
			passengerType = passTypeAndID[0];
			ID = Long.parseLong(passTypeAndID[1]);
			weight = Double.parseDouble(arrStr[1].strip());
			baggageCount = Integer.parseInt(arrStr[2].strip());
			String initialLoc = arrStr[3].strip();
			String initialLoc2 = "";
			for(int j = 1; j < initialLoc.length(); j++) {
				initialLoc2 = initialLoc2 + initialLoc.charAt(j);
			}
			int initialLocation = Integer.parseInt(initialLoc2);
			
			
			ArrayList<Integer> destinations = new ArrayList<Integer>();
			destinations.add(initialLocation);
			for(int k = 4; k < arrStr.length - 1; k++) {
				String loc = arrStr[k].strip();
				String loc2 = "";
				for(int j = 0; j < loc.length(); j++) {
					loc2 = loc2 + loc.charAt(j);
				}
				int location = Integer.parseInt(loc2);
				destinations.add(location);
			}
			String loc = arrStr[arrStr.length - 1].strip();
			String loc2 = "";
			for(int j = 0; j < loc.length() - 1; j++) {
				loc2 = loc2 + loc.charAt(j);
			}
			int location = Integer.parseInt(loc2);
			destinations.add(location);
			/*System.out.println(passengerType);
			System.out.println(ID);
			System.out.println(weight);
			System.out.println(baggageCount);
			System.out.println(destinations);*/
			//System.out.println(destinations);
			ourAirline.addPassenger(passengerType, ID, weight, baggageCount, 0, destinations);
		}
		
		scan.close();
		//Creates all rapid, fully economy seated aircrafts.
		//ourAirline.createAllEconomyAircraft(rapidOpFee);
		ourAirline.createAllEconomyWidebodyPassengerAircraft(wideBodyOpFee);
		//Fills the fuel in all of the aircrafts.
		ourAirline.fillAllUp();
		for(int i = 0; i < 10; i++) {
			ourAirline.loadAllPassengers();  //Loads all passengers.
			ourAirline.flyAll();			//Flies all the aircrafts
			ourAirline.unloadAllAvailableTransferRest(); //Unloads if passengers can be unloaded, transfers otherwise.
			ourAirline.fillAllUp();			//Fills the fuels again.
		}
		ourAirline.writeProfit();
	}

}
