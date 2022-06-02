package project.executablecontainer;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import project.airlinecontainer.Airline;
public class Main {

	public static void main(String[] args) throws Exception {
	    File inputFile = new File(args[0]);
	    Scanner input = new Scanner(inputFile);
	    ArrayList<String> inputs = new ArrayList<String>();
	    while (input.hasNextLine()){
	    	inputs.add(input.nextLine());
	    }
	    input.close();
	    String[] firstLine = inputs.get(0).split(" ");
	    int maxNoOfAircrafts = Integer.parseInt(firstLine[0]);
	    int noOfAirports = Integer.parseInt(firstLine[1]);
	    int noOfPassengers = Integer.parseInt(firstLine[2]);
	    String[] secondLine = inputs.get(1).split(" ");

	    double opFeeProp = Double.parseDouble(secondLine[0]);
		double opFeeWide = Double.parseDouble(secondLine[1]);
		double opFeeRapid = Double.parseDouble(secondLine[2]);
		double opFeeJet = Double.parseDouble(secondLine[3]);
		double operationalCost = Double.parseDouble(secondLine[4]);
		
		File output = new File(args[1]);
		output.createNewFile();
		FileWriter outputWriter = new FileWriter(args[1]);
		
		Airline airline = new Airline(maxNoOfAircrafts, operationalCost);
		
		for (int i = 2; i < noOfAirports + 2; i++) {
			String[] line = inputs.get(i).replaceAll(":", "").replaceAll(",", "").split(" ");
			if (line[0].equals("regional")) {
				airline.addRegionalAirport(Long.parseLong(line[2]), Double.parseDouble(line[3]), Double.parseDouble(line[4]), Double.parseDouble(line[5]), Integer.parseInt(line[6]), Integer.parseInt(line[7]));
			}
			else if (line[0].equals("Hub")) {
				airline.addHubAirport(Long.parseLong(line[2]), Double.parseDouble(line[3]), Double.parseDouble(line[4]), Double.parseDouble(line[5]), Integer.parseInt(line[6]), Integer.parseInt(line[7]));
			}
			else if (line[0].equals("Major")) {
				airline.addMajorAirport(Long.parseLong(line[2]), Double.parseDouble(line[3]), Double.parseDouble(line[4]), Double.parseDouble(line[5]), Integer.parseInt(line[6]), Integer.parseInt(line[7]));
			}
		}
		
		for (int i = noOfAirports + 2; i < noOfAirports + 2 + noOfPassengers; i++) {
			String[] line = inputs.get(i).replaceAll(",", "").replace("[", "").replace("]", "").replace(":","").split(" ");
			ArrayList<String> destinations= new ArrayList<String>();
			for (int j = 4; j < line.length ; j++) {
				destinations.add(line[j]);
			}
			if (line[0].equals("economy")) {
				airline.createEconomyPassenger(Long.parseLong(line[2]), Long.parseLong(line[3]), Integer.parseInt(line[4]), destinations);
			}
			else if (line[0].equals("business")) {
				airline.createBusinessPassenger(Long.parseLong(line[2]), Long.parseLong(line[3]), Integer.parseInt(line[4]), destinations);
			}
			else if (line[0].equals("first")) {
				airline.createFirstClassPassenger(Long.parseLong(line[2]), Long.parseLong(line[3]), Integer.parseInt(line[4]), destinations);
			}
			else if (line[0].equals("luxury")) {
				airline.createLuxuryPassenger(Long.parseLong(line[2]), Long.parseLong(line[3]), Integer.parseInt(line[4]), destinations);
			}
		}
		airline.run(opFeeWide, outputWriter);
		
		outputWriter.close();
	}

}
