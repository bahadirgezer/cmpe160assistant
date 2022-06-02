package project.executableContainer;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import project.airlineContainer.Airline;


public class Main {
	 
	public static ArrayList<String> separate(String string, String separator) {
		int lastIndex=-1;
		ArrayList<String> separatedList= new ArrayList<String>() ;
		string+=" ";
		for (int i=0;i<string.length();i++) {
			String newStr= "";
			
			char currentChar= string.charAt(i);
			if (separator.contains(String.valueOf(currentChar))) {
				for (int j=lastIndex+1;j<i;j++) {
					newStr+=string.charAt(j);
				}
				if (i!=lastIndex+1) {
					separatedList.add(newStr);
				}
				lastIndex=i;
			}
			
			
		}
		return separatedList;
		
	}
	
	
	public static void main(String[] args) throws Exception {
		
		/*
		Scanner scanner = new Scanner(System.in);
        String inputFileName = scanner.nextLine();
        String outputFileName=scanner.nextLine();
        scanner.close();
   		*/
		
		ArrayList<String> inputlines = new ArrayList<String>();
		// File file = new File("C:\\Users\\Samsung\\eclipse-workspace\\teachingcodes\\user5993\\semesters\\2022 Spring\\Cmpe160.02\\takeHomes\\question1223\\TestCases\\circletype1\\input1.txt");
		File file = new File(args[0]);
		Scanner scanner= new Scanner(file);
		while (scanner.hasNextLine()) {
			String currentLine= scanner.nextLine();
			inputlines.add(currentLine);
		}
		scanner.close();
		
		// File myObj = new File("output.txt");
		File myObj = new File(args[1]);
		myObj.createNewFile();
		// FileWriter myWriter = new FileWriter("output.txt");
		FileWriter myWriter = new FileWriter(args[1]);
	
		
		String[] firstLine=inputlines.get(0).split(" ");
		int airportNumber= Integer.parseInt(firstLine[1]);
		int passengerNumber=Integer.parseInt(firstLine[2]);
		String[] secondLine=inputlines.get(1).split(" ");
		double operationFeeofProp= Double.parseDouble(secondLine[0]);
		double operationFeeofWidebody= Double.parseDouble(secondLine[1]);
		double operationFeeofRapid= Double.parseDouble(secondLine[2]);
		double operationFeeofJet= Double.parseDouble(secondLine[3]);
		Airline LoungeAirlines= new Airline(Integer.parseInt(firstLine[0]),Double.parseDouble(secondLine[4]));
		
		for(int i=0; i<airportNumber;i++) {
			ArrayList<String> currentLine =Main.separate(inputlines.get(i+2),": ,[]");
			LoungeAirlines.addAirport(currentLine.get(0), Integer.parseInt(currentLine.get(1)), Double.parseDouble(currentLine.get(2)),Double.parseDouble(currentLine.get(3)),Double.parseDouble(currentLine.get(4)),Double.parseDouble(currentLine.get(5)),Integer.parseInt(currentLine.get(6)));	
		}

		LoungeAirlines.execute(passengerNumber,airportNumber,inputlines, operationFeeofWidebody,myWriter);
		myWriter.close();

	}
	
}
