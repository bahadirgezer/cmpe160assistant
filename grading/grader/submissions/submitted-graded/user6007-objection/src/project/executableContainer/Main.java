package project.executableContainer;

import project.airlineContainer.Airline;
import java.util.Scanner;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileWriter;



public class Main {

	public static void main(String[] args) throws IOException {
		
		// TODO Auto-generated method stub
		
		Airline thy = new Airline();
		
		//inputs are evaluated in this block
		
		FileInputStream file = new FileInputStream(args[0]);
		
		Scanner inputs = new Scanner(file);
		//first line is evaluated
		String[] firstLine = inputs.nextLine().split("\\s");
		int[] firstLineValues = new int[3];
		for (int i = 0; i < firstLineValues.length; i++) {
			firstLineValues[i] = Integer.parseInt(firstLine[i]);
		}
		thy.setMaxAircraftCount(firstLineValues[0]);
		
		//second line is evaluated
		String[] secondLine = inputs.nextLine().split("\\s");
		double[] secondLineValues = new double[5];
		for (int j = 0; j < secondLineValues.length; j++) {
			secondLineValues[j] = Double.parseDouble(secondLine[j]);
		}
		@SuppressWarnings("unused")
		double propOpCost = secondLineValues[0];
		double wideOpCost = secondLineValues[1];
		@SuppressWarnings("unused")
		double rapidOpCost = secondLineValues[2];
		@SuppressWarnings("unused")
		double jetOpCost = secondLineValues[3];
		
		thy.setOperationalCost(secondLineValues[4]);
		
		//next A+P lines are evaluated
		try {
		do {
			String[] lineValues = inputs.nextLine().split("\\s:\\s");
			if(lineValues[0].equals("regional")) {
				String[] secondVals = lineValues[1].split(",\\s");
				thy.createRegionalAirport(Integer.parseInt(secondVals[0]), Double.parseDouble(secondVals[1]), Double.parseDouble(secondVals[2]), Double.parseDouble(secondVals[3]), Double.parseDouble(secondVals[4]), Integer.parseInt(secondVals[5]));
			}
			else if(lineValues[0].equals("major")) {
				String[] secondVals = lineValues[1].split(",\\s");
				thy.createMajorAirport(Integer.parseInt(secondVals[0]), Double.parseDouble(secondVals[1]), Double.parseDouble(secondVals[2]), Double.parseDouble(secondVals[3]), Double.parseDouble(secondVals[4]), Integer.parseInt(secondVals[5]));
			}
			else if(lineValues[0].equals("hub")) {
				String[] secondVals = lineValues[1].split(",\\s");
				thy.createHubAirport(Integer.parseInt(secondVals[0]), Double.parseDouble(secondVals[1]), Double.parseDouble(secondVals[2]), Double.parseDouble(secondVals[3]), Double.parseDouble(secondVals[4]), Integer.parseInt(secondVals[5]));
			}
			else if(lineValues[0].equals("economy")) {
				String[] secondVals = lineValues[1].split(",\\s", 4);
				String[] IDset = secondVals[3].substring(1, secondVals[3].length() - 1).split(",\\s");
				thy.createEconomyPassenger(Long.parseLong(secondVals[0]), Double.parseDouble(secondVals[1]), Integer.parseInt(secondVals[2]), IDset);
			}
			else if(lineValues[0].equals("business")) {
				String[] secondVals = lineValues[1].split(",\\s", 4);
				String[] IDset = secondVals[3].substring(1, secondVals[3].length() - 1).split(",\\s");
				thy.createBusinessPassenger(Long.parseLong(secondVals[0]), Double.parseDouble(secondVals[1]), Integer.parseInt(secondVals[2]), IDset);
			}
			else if(lineValues[0].equals("first")) {
				String[] secondVals = lineValues[1].split(",\\s", 4);
				String[] IDset = secondVals[3].substring(1, secondVals[3].length() - 1).split(",\\s");
				thy.createFirstClassPassenger(Long.parseLong(secondVals[0]), Double.parseDouble(secondVals[1]), Integer.parseInt(secondVals[2]), IDset);
			}
			else if(lineValues[0].equals("luxury")) {
				String[] secondVals = lineValues[1].split(",\\s", 4);
				String[] IDset = secondVals[3].substring(1, secondVals[3].length() - 1).split(",\\s");
				thy.createLuxuryPassenger(Long.parseLong(secondVals[0]), Double.parseDouble(secondVals[1]), Integer.parseInt(secondVals[2]), IDset);
			}
			
		}
		while (inputs.hasNextLine());
		}
		catch (ArrayIndexOutOfBoundsException a) {
		}
		inputs.close();
		thy.arrangePaths();
		
		File outputFile = new File(args[1]);
		FileWriter writer = new FileWriter(outputFile);
		
		
		/**
		 * THE ENTIRE AIRCRAFT CREATION BLOCK
		 */
				
			
		
		
		
		
		
		
		
		
		
		for (int i: thy.flightPaths.keySet()) {
			if (thy.getClosestAirportWithPassengers(thy.getAirport(i)) == 0) {
				for (int j: thy.flightPaths.get(i)) {
					if (thy.getClosestAirportWithPassengers(thy.getAirport(j)) != 0) {
						i = j;
						break;
					}
				}
			}
			if (thy.getAirport(i).passengers.size() > 0) {
				thy.createWidebodyPassengerAircraft(thy.getAirport(i), wideOpCost);
				writer.write("0 " + i + " 1" + "\n");
				int dID = thy.getClosestAirportWithPassengers(thy.getAirport(i));
				int index = thy.aircrafts.indexOf(thy.getAirport(i).aircrafts.get(0));
				
				double betterFuelHuell = thy.getAirport(i).aircrafts.get(0).getFuelCapacity() - thy.getAirport(i).aircrafts.get(0).getFuel();
				thy.fillUp(thy.getAirport(i).aircrafts.get(0));
				writer.write("3 " + index + " " + betterFuelHuell + "\n");
				
				if (thy.getAirport(i).aircrafts.get(0).canFly(thy.getAirport(thy.getClosestAirportWithPassengers(thy.getAirport(i))))) {
					//configure seats
					thy.getAirport(i).aircrafts.get(0).setAllEconomy();
					writer.write("2 " + index + " " + thy.getAirport(i).aircrafts.get(0).getAvailableEconomy() + " 0" + " 0 " + "\n");
					int passengerNumber = thy.getAirport(i).passengers.size();
					for(int k = 0; k < passengerNumber; k++) {
						if (thy.canGoTo(thy.getAirport(i).passengers.get(k),thy.getAirport(thy.getClosestAirportWithPassengers(thy.getAirport(i))))) {
							if (thy.getAirport(i).passengers.get(k).passengerType == 3) {
								long ID = thy.getAirport(i).passengers.get(k).getID();
								thy.loadPassenger(thy.getAirport(i).passengers.get(k), thy.getAirport(i), index);
								writer.write("4 " + ID + " " + index + " " + thy.getAirport(i).getID() + "\n");
							}
							else if (thy.getAirport(i).passengers.get(k).passengerType == 2) {
								long ID = thy.getAirport(i).passengers.get(k).getID();
								thy.loadPassenger(thy.getAirport(i).passengers.get(k), thy.getAirport(i), index);
								writer.write("4 " + ID + " " + index + " " + thy.getAirport(i).getID() + "\n");
							}
							else if (thy.getAirport(i).passengers.get(k).passengerType == 1) {
								long ID = thy.getAirport(i).passengers.get(k).getID();
								thy.loadPassenger(thy.getAirport(i).passengers.get(k), thy.getAirport(i), index);
								writer.write("4 " + ID + " " + index + " " + thy.getAirport(i).getID() + "\n");
							}
							else if (thy.getAirport(i).passengers.get(k).passengerType == 0) {
								long ID = thy.getAirport(i).passengers.get(k).getID();
								thy.loadPassenger(thy.getAirport(i).passengers.get(k), thy.getAirport(i), index);
								writer.write("4 " + ID + " " + index + " " + thy.getAirport(i).getID() + "\n");
							}
						}
					
						
					}
					if (thy.getAirport(i).aircrafts.get(0).canFly(thy.getAirport(thy.getClosestAirportWithPassengers(thy.getAirport(i))))) {
						thy.fly(thy.getAirport(thy.getClosestAirportWithPassengers(thy.getAirport(i))), index);
						writer.write("1 " + dID + " " + index + "\n");
					}
					
					
					int passengerNo = thy.getPassengers(index).size();
					for (int k = 0; k < passengerNo; k++) {
						long ID = thy.getPassengers(index).get(k).getID();
						thy.unloadPassenger(thy.getPassengers(index).get(k), index);
						writer.write("5 " + ID + " "+ index + " " + dID + "\n");
						//THE PASSENGERS ARE NEVER REMOVED FROM THE AIRPLANE, FIND A WAY TO REMOVE THEM AFTER ITERATING THROUGH
					}
					
				}
			}
			else 
				continue;
			break;
		}
		
		
			
		
		writer.write("" + thy.getProfit());
		writer.close();
		
		}
	

			
	}



