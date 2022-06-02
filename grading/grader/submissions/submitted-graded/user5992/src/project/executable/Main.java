package project.executable;

import project.airline.Airline;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Emre KILIC
 *
 */
public class Main {
	private Airline airline;
	private final FileWriter writer;
	public static double revenue;

	int aircraftCapacity, airportNumber, passengerNumber;

	/**
	 * 			&#9&#9FLIGHTS BASIC STRATEGY: <p>
	 * Find all flights and offer them to the flights deque<p>
	 * Sorts the flights by its firstClass passenger count<p>
	 * Start executing flights<p>
	 * When the arrival airport is full, arrange a flight that departs from the full airport<p>
	 * When there is weight problem, unload some passengers in the lower classes.
	 *<p>
	 * 			&#9&#9REFUEL OPERATIONS:<p>
	 * It is done in Airline class automatically<p>
	 * Add just the necessary fuel to fly<p>
	 *<p>
	 * 			&#9&#9AIRCRAFT TYPE OPERATIONS<p>
	 * distance < 5000 and firstClass passenger > 3 -> Jet<p>
	 * distance < 2000 -> Prop<p>
	 * distance < 7000 -> Rapid<p>
	 * rest -> WideBody<p>
	 *<p>
	 *     		&#9&#9SEAT OPERATIONS<p>
	 * Load the aircraft for all passengers can fit their seat<p>
	 * If aircraft cannot fit all passengers and it isn't possible to use big aircraft<p>
	 * &#9* Priority is given to the higher classes
	 */
	Main(String inputPath, String outputPath) throws IOException {
		String pathString = "";
		File in = new File(pathString + inputPath);
		Scanner input = new Scanner(in);
		writer = new FileWriter(pathString + outputPath);
		initialize(input);
		addAirports(input);
		addPassengers(input);
	}

	/**
	 * Scans the first two line of the input and initializes the airline object
	 * @param input the scanner object
	 */
    void initialize(Scanner input){
		String[] firstln = input.nextLine().split(" ");
		aircraftCapacity = Integer.parseInt(firstln[0]);
		airportNumber = Integer.parseInt(firstln[1]);
		passengerNumber = Integer.parseInt(firstln[2]);

		String[] secondln = input.nextLine().split(" ");
		double propFee = Double.parseDouble(secondln[0]), widebodyFee = Double.parseDouble(secondln[1]),
				rapidFee = Double.parseDouble(secondln[2]), jetFee = Double.parseDouble(secondln[3]),
				operationalCost = Double.parseDouble(secondln[4]);
		airline = new Airline(aircraftCapacity, operationalCost, propFee, widebodyFee, rapidFee, jetFee);
	}

	/**
	 * Scans the input and adds airports to the airline object
	 * @param input the scanner object
	 */
	void addAirports(Scanner input){
		for (int i = 0; i < airportNumber; i++) {
			String type = input.next();
			String[] line = input.nextLine().replace(":", "").replaceAll("\\s+", "").split(",");
			airline.addAirport(Long.parseLong(line[0]), Double.parseDouble(line[1]), Double.parseDouble(line[2]),
					Double.parseDouble(line[3]), Double.parseDouble(line[4]), Integer.parseInt(line[5]), getAirportType(type));
		}
	}

	/**
	 * Scans the input and adds passengers to the airline object
	 * @param input the scanner object
	 */
	void addPassengers(Scanner input){
		for (int i = 0; i < passengerNumber; i++) {
			String type = input.next();
			String[] line = input.nextLine().replace(":", "").replaceAll("\\s+", "").replace("]","").split("\\[");
			String[] firstPart = line[0].split(","), secondPart = line[1].split(",");
			long[] destinations = new long[secondPart.length];
			for (int j = 0; j < secondPart.length; j++) {
				destinations[j] = Long.parseLong(secondPart[j]);
			}
			airline.addPassenger(Long.parseLong(firstPart[0]), Double.parseDouble(firstPart[1]), Integer.parseInt(firstPart[2]),
					destinations, getPassengerType(type));
		}
	}

	/**
	 * @param type string type of the airport
	 * @return integer type of the airport
	 */
	private int getAirportType(String type){
		return switch (type){
			case "hub" -> 0;
			case "major" -> 1;
			case "regional" -> 2;
			default -> throw new IllegalStateException("Unexpected value: " + type);
		};
	}

	/**
	 * @param type string type of the passenger
	 * @return integer type of the passenger
	 */
	private int getPassengerType(String type){
		return switch (type){
			case "economy" -> 0;
			case "business" -> 1;
			case "first" -> 2;
			case "luxury" -> 3;
			default -> throw new IllegalStateException("Unexpected value: " + type);
		};
	}

	/**
	 * Creates flights and runs them<p>
	 */
	private void run() {
		airline.arrangeMajorToRegionalFlights();
		airline.arrangeMajorToMajorFlights();
		airline.arrangeMajorToHubFlights();
		airline.arrangeRegionalToHubFlights();
		airline.arrangeHubToRegionalFlights();
		airline.arrangeHubToHubFlights();
		airline.arrangeHubToMajorFlights();

		airline.executeFlights();
	}

	/**
	 * Takes and writes the output log with the FileWriter object
	 * @throws IOException if there is problem with FileWriter
	 */
	private void printLogs() throws IOException {
		LinkedList<String> logs = airline.getLogs();
		for (String log : logs) {
			writer.write(log + "\n");
		}
		revenue = airline.getProfit();
		writer.write(String.valueOf(revenue));
		writer.close();
	}

	/**
	 * The main executable method of the project
	 * @param args takes the input and output path of the files
	 * @throws IOException if there is problem with FileWriter
	 */
	public static void main(String[] args) throws IOException {
		String input = args[0];
		String output = args[1];
		Main main = new Main(input, output);
		main.run();
		main.printLogs();
	}
}
