package executable;


import airline.Airline;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static PrintStream out;
    public static void main(String[] args) throws IOException, FileNotFoundException {
        out = new PrintStream(new File(args[1]));
        /*
        BufferedReader input = new BufferedReader(new FileReader(new File(args[0])));
        Pattern passengerPattern = Pattern.compile("(\\d+)");
        Pattern cargoPattern = Pattern.compile("([a-e]+) (\\d+)");
        Matcher passengerMatcher, cargoMatcher;
        String currLine;

    
        while((currLine = input.readLine()) != null) {
            passengerMatcher = passengerPattern.matcher(currLine);
            cargoMatcher = cargoPattern.matcher(currLine);

        }
        */

        Airline airline = new Airline(5);


    }
}