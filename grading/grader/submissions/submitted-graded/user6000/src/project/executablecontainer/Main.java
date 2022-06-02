package project.executablecontainer;

import java.util.ArrayList;
import java.util.Scanner;
import project.airlinecontainer.Airline;

public class Main {
	public static void main(String[]args) {
		Scanner input=new Scanner(System.in);
		int maximumNumberofAircrafts=input.nextInt();
		int numberofAirports=input.nextInt();
		int numberofPassengers=input.nextInt();
		double operationFeeProp=input.nextDouble();
		double operationFeeWide=input.nextDouble();
		double operationFeeRapid=input.nextDouble();
		double operationFeeJet=input.nextDouble();
		double operationalCost=input.nextDouble();
		String inputAirport=input.nextLine();
		Airline loungeAirline= new Airline();
		
		
//long ID,double x,double y,double fuelCost,double operationFee,int aircraftCapacity
for(int i=0;i<numberofAirports+1;i++) {
	
			inputAirport=input.nextLine();
			String[] splittedAirport=inputAirport.split(" ");
			if(splittedAirport[0].equals("hub")) {
				long IDairport=Long.parseLong(splittedAirport[2].split(",")[0]);
				double x=Double.parseDouble(splittedAirport[3].split(",")[0]);
				double y=Double.parseDouble(splittedAirport[4].split(",")[0]);
				double fuelCost=Double.parseDouble(splittedAirport[5].split(",")[0]);
				double operationFee=Double.parseDouble(splittedAirport[6].split(",")[0]);
				int aircraftCapacity=Integer.parseInt(splittedAirport[7]);
				loungeAirline.createHubAirport(IDairport,x,y,fuelCost,operationFee,aircraftCapacity);
			}
			
			
			if(splittedAirport[0].equals("major")) {
				long IDairport=Long.parseLong(splittedAirport[2].split(",")[0]);
				double x=Double.parseDouble(splittedAirport[3].split(",")[0]);
				double y=Double.parseDouble(splittedAirport[4].split(",")[0]);
				double fuelCost=Double.parseDouble(splittedAirport[5].split(",")[0]);
				double operationFee=Double.parseDouble(splittedAirport[6].split(",")[0]);
				int aircraftCapacity=Integer.parseInt(splittedAirport[7]);
				loungeAirline.createMajorAirport(IDairport,x,y,fuelCost,operationFee,aircraftCapacity);
			}
			
			
			if(splittedAirport[0].equals("regional")) {
				long IDairport=Long.parseLong(splittedAirport[2].split(",")[0]);
				double x=Double.parseDouble(splittedAirport[3].split(",")[0]);
				double y=Double.parseDouble(splittedAirport[4].split(",")[0]);
				double fuelCost=Double.parseDouble(splittedAirport[5].split(",")[0]);
				double operationFee=Double.parseDouble(splittedAirport[6].split(",")[0]);
				int aircraftCapacity=Integer.parseInt(splittedAirport[7]);
				loungeAirline.createRegionalAirport(IDairport,x,y,fuelCost,operationFee,aircraftCapacity);
			}
			
		}
for(int j=0;j<numberofPassengers;j++) {
	String inputPassenger=input.nextLine();
	String[] splittedAirport=inputAirport.split(" ");
	ArrayList<Long>listofDestinationIDs = new ArrayList<Long>();
	
		long IDpassenger=Long.parseLong(splittedAirport[2].split(",")[0]);
		double weight=Double.parseDouble(splittedAirport[3].split(",")[0]);
		int baggageCount=Integer.parseInt(splittedAirport[4].split(",")[0]);
		long destinationID;

		
		destinationID=Long.parseLong(splittedAirport[5].split(",")[0].split("\\[")[1]);
		listofDestinationIDs.add(destinationID);
		for(int k=6;k<splittedAirport.length-1;k++) {
			destinationID=Long.parseLong(splittedAirport[k].split(",")[0]);
			listofDestinationIDs.add(destinationID);
		}
		
		
		destinationID=Long.parseLong(splittedAirport[splittedAirport.length-1].split(",")[0].split("\\]")[0]);
		listofDestinationIDs.add(destinationID);

	if(splittedAirport[0].equals("economy")) {	
		loungeAirline.createEconomyPassenger(IDpassenger, weight, baggageCount, listofDestinationIDs);
	}
	if(splittedAirport[0].equals("business")) {
		loungeAirline.createBusinessPassenger(IDpassenger, weight, baggageCount, listofDestinationIDs);
	}
	if(splittedAirport[0].equals("first")) {
		loungeAirline.createFirstClassPassenger(IDpassenger, weight, baggageCount, listofDestinationIDs);
	}
	if(splittedAirport[0].equals("luxury")) {
		loungeAirline.createLuxuryPassenger(IDpassenger, weight, baggageCount, listofDestinationIDs);
	}

}
System.out.println(loungeAirline.passengers.size());		
System.out.println(loungeAirline.airports.size());
	}
}
