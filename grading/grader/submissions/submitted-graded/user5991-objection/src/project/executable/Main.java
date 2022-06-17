package project.executable;
import project.airline.Airline;
import java.io.*;
import java.util.*;


public class Main {
	public static void main(String args[]){
		
		Scanner r = null;
		try {
			File file = new File(args[0]);
		    r = new Scanner(file);
		    r.useLocale(Locale.ENGLISH);
		}
		catch (FileNotFoundException e) {System.out.println("fisrtAn error occurred.");}
		
		int M = r.nextInt();
		int A = r.nextInt();
		int P = r.nextInt();
		
		double prop = r.nextDouble();
		double widebody = r.nextDouble();
		double rapid = r.nextDouble();
		double jet = r.nextDouble();
		double operationalCost = r.nextDouble();
		
		Airline airline = new Airline(M,operationalCost,prop,widebody,rapid,jet, args[1]);
		
		for(int i=0;i<A;i++) {
			String airportType = r.next();
			int airportTypeInt = 0;
			if(airportType.equals("major")) {airportTypeInt=1;}
			else if(airportType.equals("regional")) {airportTypeInt=2;}
			
			r.next();
			String newr = r.nextLine().replaceAll(" ","");
			String[] values = newr.split(",");

			int ID = Integer.parseInt(values[0]);
			int x = Integer.parseInt(values[1]);
			int y = Integer.parseInt(values[2]);
			double fuelCost = Double.parseDouble(values[3]);
			double operationFee = Double.parseDouble(values[4]);
			int aircraftCapacity = Integer.parseInt(values[5]);
			airline.AirportCreation(airportTypeInt, ID, x, y, fuelCost, operationFee, aircraftCapacity);
		}
		

		for(int i = 0;i<P;i++) {
			String passengerType = r.next();
			int passengerTypeInt = 0;
			if(passengerType.equals("business")) {passengerTypeInt=1;}
			else if(passengerType.equals("first")) {passengerTypeInt=2;}
			else if(passengerType.equals("luxury")) {passengerTypeInt=3;}
			r.next();
			String newr = r.nextLine().replaceAll(" ","");
			String[] values = newr.split("\\[");
			String[] valuesbeforedest = values[0].split(",");
			String[] dests = values[1].replaceAll("]","").split(",");
			long ID = Long.parseLong(valuesbeforedest[0]);
			double weight = Double.parseDouble(valuesbeforedest[1]);
			int baggageCount = Integer.parseInt(valuesbeforedest[2]);
			ArrayList<Integer> destinations = new ArrayList<Integer>();
			for(String s: dests) {destinations.add(Integer.parseInt(s));}
			airline.PassengerCreation(passengerTypeInt, ID, weight, baggageCount, destinations);	
		}
		
		airline.setFile(args[1]);
		
		airline.sendOnePassenger();

		airline.destructor();
	}
}
