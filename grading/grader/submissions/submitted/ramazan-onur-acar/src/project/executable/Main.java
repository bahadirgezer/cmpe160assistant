package project.executable;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import project.airline.Airline;
import project.airport.Airport;


public class Main {
	
	
	public static void main(String[] args) throws IOException {
		File file = new File(args[0]);
		Scanner scan = new Scanner(file);
		String[] firstline = scan.nextLine().split(" ");
		int M = Integer.parseInt(firstline[0]);
		int A = Integer.parseInt(firstline[1]);
		int P = Integer.parseInt(firstline[2]);
		String[] secondline = scan.nextLine().split(" ");
		int sl0 = Integer.parseInt(secondline[0]);
		double sl1 = Double.valueOf(secondline[1]);
		int sl2 = Integer.parseInt(secondline[2]);
		int sl3 = Integer.parseInt(secondline[3]);
		int sl4 = Integer.parseInt(secondline[4]);
		int maximum_aircraft = M;
		int number_of_airports = A;
		int number_of_passengers = P;
		
		Airline LoungeAirline = new Airline(maximum_aircraft,sl4, args[1]);
		
		for (int q = 0; q<A; q++) {
			String[] airports_str = scan.nextLine().split("[ ,:]+");
			int c = 3;
			if(airports_str[0].equals("hub")) {
				c = 1;
			}
			else if(airports_str[0].equals("major")) {
				c =2;
			}

			LoungeAirline.createAirport(c, Integer.parseInt(airports_str[1]), Integer.parseInt(airports_str[2]), Integer.parseInt(airports_str[3]),Double.valueOf(airports_str[4]), Integer.parseInt(airports_str[5]),Integer.parseInt(airports_str[6]));
		}
		
		for (int e = 0; e < P; e++) {
			String[] psg_str = scan.nextLine().split("[ ,:\\[\\]]+");		
			int d = 4;
			if (psg_str[0].equals("economy")) {
				d = 1;
			}
			else if (psg_str[0].equals("business")) {
				d = 2;
			}
			else if (psg_str[0].equals("first")) {
				d = 3;
			}
			ArrayList <Airport> destinations = new ArrayList<Airport>();
			for (int z = 4; z < psg_str.length ; z ++) {
				int port_ind = -1;
				for (int j = 0; j < LoungeAirline.airports_list.size(); j++) {
					if (LoungeAirline.airports_list.get(j).getID() == Integer.parseInt(psg_str[z])) {
						port_ind = j;
						break;
					}
				}
				destinations.add(LoungeAirline.airports_list.get(port_ind));
			}
			LoungeAirline.createPassenger(d, Long.parseLong(psg_str[1]), Double.valueOf(psg_str[2]), Integer.parseInt(psg_str[3]), destinations);
			
		
		}
		
		for (int k = 0; k < maximum_aircraft ; k++) {
			LoungeAirline.createAircraft(1, LoungeAirline.all_passengers.get(k).initial_airport, sl1);
			LoungeAirline.fulle(LoungeAirline.aircrafts_list.get(k));
			LoungeAirline.file.println("3 " + k + LoungeAirline.aircrafts_list.get(k).getFuelCapacity());
			LoungeAirline.aircrafts_list.get(k).setAllEconomy();
			LoungeAirline.file.println("2 " + k + " " + LoungeAirline.aircrafts_list.get(k).getEconomySeats());
			
			for (int l = 1; l < LoungeAirline.all_passengers.get(k).getDestinationSize(); l++) {
				if (LoungeAirline.canFly(LoungeAirline.all_passengers.get(k).getDestinations().get(l), k)) {
					LoungeAirline.loadPassenger(LoungeAirline.all_passengers.get(k), LoungeAirline.all_passengers.get(k).initial_airport,k);
					LoungeAirline.fly(LoungeAirline.all_passengers.get(k).getDestinations().get(l), k);
					LoungeAirline.unloadPassenger(LoungeAirline.all_passengers.get(k), k);
					LoungeAirline.file.close();
					System.exit(0);

				}
			}
			 if (k ==maximum_aircraft -1) {
				 LoungeAirline.file.close();
			 }
		
			
		}
		
	}
}
