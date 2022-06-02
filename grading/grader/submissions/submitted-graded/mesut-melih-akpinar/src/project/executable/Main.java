package project.executable;

import project.airline.Airline;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File file = null;

        if(args.length > 0) {
            file = new File(args[0]);
        }

        else {
            file = new File("input.txt");
        }

        try {
            Scanner sc = new Scanner(file);
            int maximum_aircrafts = sc.nextInt();
            int airports = sc.nextInt();
            int passengers = sc.nextInt();

            double propFee = sc.nextDouble();
            double wideFee = sc.nextDouble();
            double rapidFee = sc.nextDouble();
            double jetFee = sc.nextDouble();
            double operationFee = sc.nextDouble();
            sc.nextLine();
            Airline airline = new Airline(maximum_aircrafts, operationFee, propFee, rapidFee, jetFee, wideFee);

            for(int i = 0; i < airports; i++) {
                String s = sc.nextLine();
                String[] airportAttributes = s.split("[ ,:]+");
                airline.createAirport(airportAttributes[0], Long.parseLong(airportAttributes[1]), Double.parseDouble(airportAttributes[2]), Double.parseDouble(airportAttributes[3]), Double.parseDouble(airportAttributes[4]), Double.parseDouble(airportAttributes[5]), Integer.parseInt(airportAttributes[6]));
            }

            String s = sc.nextLine();
            String[] airportAttributes = s.split("[ ,:\\[\\]]+");
            airline.findAirportByIndex(Long.parseLong(airportAttributes[4])).addPassenger(airportAttributes[0], Long.parseLong(airportAttributes[1]), Double.parseDouble(airportAttributes[2]), Integer.parseInt(airportAttributes[3]), Long.parseLong(airportAttributes[5]));

            for(int i = 1; i < passengers; i++) {
                s = sc.nextLine();
                airportAttributes = s.split("[ ,:\\[\\]]+");
                airline.findAirportByIndex(Long.parseLong(airportAttributes[4])).addPassenger(airportAttributes[0], Long.parseLong(airportAttributes[1]), Double.parseDouble(airportAttributes[2]), Integer.parseInt(airportAttributes[3]), Long.parseLong(airportAttributes[5]));
            }

            if(args.length > 1) {
                airline.startProcess(args[1]);
            }
            else {
                airline.startProcess("output.txt");
            }

            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
