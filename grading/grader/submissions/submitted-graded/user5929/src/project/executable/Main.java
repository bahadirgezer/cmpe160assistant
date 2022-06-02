package project.executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import project.airline.Airline;




public class Main {
	static int airportCount ;
	public static Airline readInput(String in,FileWriter log) {

		File inputFile = new File(in);
		Airline airline = null;
		try (Scanner sc = new Scanner(inputFile)) {
			int M,A,P;
			M = sc.nextInt();
			A = sc.nextInt();
			P = sc.nextInt();
			airportCount = A;
			double[] operationalCosts =  new double[5];
			for( int i = 0 ;i< 5; i++) {
				operationalCosts[i] = sc.nextDouble();
			}
			sc.nextLine();
			airline = new Airline(M, operationalCosts, log);
			for (int i =0 ; i< A; i++) {
				String line = sc.nextLine();
				String[] airport = line.split(" *[:,] *");

				String type = airport[0];
				int id = Integer.parseInt(airport[1]);
				double x = Double.parseDouble(airport[2]);
				double y = Double.parseDouble(airport[3]);
				double fuelCost = Double.parseDouble(airport[4]);
				double operationFee = Double.parseDouble(airport[5]);
				int aircraftCapacity = Integer.parseInt(airport[6]);
				airline.createAirport(type, id, x, y, fuelCost, operationFee, aircraftCapacity);
			}
			for (int i =0 ; i< P; i++) {
				String line = sc.nextLine();
				String[] passenger  = line.split(" *[:,] *");
				passenger[4] = passenger[4].substring(1);
				passenger[passenger.length -1] = passenger[passenger.length -1].substring(0,
						passenger[passenger.length -1].length()-1 );
				String type = passenger[0];
				long id = Long.parseLong(passenger[1]);
				double weight = Double.parseDouble(passenger[2]);
				int baggageCount = Integer.parseInt(passenger[3]);
				airline.createPassenger(Arrays.copyOfRange(passenger, 4, passenger.length), type, id, weight, baggageCount);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}
		return airline;

	}

	public static void main(String[] args) {
		FileWriter log = null;
		Airline loungeAirline = null;
		try {
			log = new FileWriter(args[1]);
			loungeAirline = readInput(args[0],log );
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (loungeAirline != null) {
			loungeAirline.run(); 
			
			try {
//				loungeAirline.reproduceLog(args[2]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		try {
			if (log != null) {
				log.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}





