package project.executable_container;

import project.airline_container.Airline;

public class Main {

	public static void main(String[] args) {
		Airline airline = new Airline(0, 0);
		airline.init(args);
		airline.run();
	}
	
}
