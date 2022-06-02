package project.executablecontainer;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import project.airlinecontainer.Airline;


public class Main {
	
	public static void main(String[] args) throws Exception {

		
		Airline airline = new Airline();
		ArrayList<String> inputarraylist = new ArrayList<String>();
		// TODO Auto-generated method stub
		    // pass the path to the file as a parameter
	    File file = new File(args[0]);
		Scanner sc = new Scanner(file);
		    
	    while (sc.hasNextLine()){
	    	String a = sc.nextLine();
	    	inputarraylist.add(a);
	    }
	    
	    File myObj = new File(args[1]);
	    myObj.createNewFile();
	    FileWriter myWriter = new FileWriter(args[1]);
	   
	    
	    
	    String[] x=inputarraylist.get(0).split(" ");
	    String new_string = "";
	    for (int i=1; i<x.length;i++)
	    	new_string+=x[i];
	   
	    Integer maximum_aircraft_number = Integer.valueOf(new_string);
	    
	    Integer number_of_airport = Integer.parseInt(x[1]);
	    Integer number_of_passenger=Integer.parseInt(x[2]);	    
	    String[] y=inputarraylist.get(1).split(" ");
	    
	    double prop_operation_fee=Double.parseDouble(y[0]);
	    double widebody_operation_fee=Double.parseDouble(y[1]);
	    double rapid_operation_fee=Double.parseDouble(y[2]);
	    double jet_operation_fee=Double.parseDouble(y[3]);
	    double operational_cost=Double.parseDouble(y[4]);
	    airline.set_operational_cost(operational_cost);
	    
	    ArrayList<String> input_perline_arraylist = new ArrayList<String>();
	    for (int j=0;j<number_of_airport;j++) {	    	
	    	String[] z = inputarraylist.get(j+2).split(",");
	    	 String[] qwerty = z[0].split(":");
	    	 String new_qwerty1= new String();
	    	 for (int i=1;i<qwerty[1].length();i++) {
	    		 new_qwerty1+=qwerty[1].charAt(i);
	    	 }
	    	 qwerty[1]=new_qwerty1;
	    	 for (int i=0;i<qwerty.length;i++) {
	    		String qwertyy = qwerty[i].strip();
		    	input_perline_arraylist.add(qwerty[i]);
		    }
	    	
	    	for (int i=1;i<z.length;i++) {
	    		String zz = z[i].strip();
	    		
	    		
	    		
	    		input_perline_arraylist.add(zz);
	    		
	    	}  
	    	Long airport_parameter1 =Long.parseLong(input_perline_arraylist.get(1));
	    	Double airport_parameter2 = Double.parseDouble(input_perline_arraylist.get(2));
	    	Double airport_parameter3 = Double.parseDouble(input_perline_arraylist.get(3));
	    	Double airport_parameter4 =Double.parseDouble(input_perline_arraylist.get(4));
	    	Double airport_parameter5 =Double.parseDouble(input_perline_arraylist.get(5));
	    	Integer airport_parameter6 = Integer.parseInt(input_perline_arraylist.get(6));
	    	
	    	airline.airport_creations(input_perline_arraylist.get(0),airport_parameter1,airport_parameter2,airport_parameter3,airport_parameter4,airport_parameter5,airport_parameter6);  
	    	
	    	input_perline_arraylist.clear();	
	    }
	   
	    input_perline_arraylist.clear();	
	    // splitting part 
	    for (int j=0;j<number_of_passenger;j++) {	    	
	    	String[] z = inputarraylist.get(j+number_of_airport+2).split(",");
	    	String[] qwerty = z[0].split(":");
	    	String edited_string ="";
	    	String new_edited_string ="";
	    	String new_qwerty1="";
	    	ArrayList<Long> destinationListsPerPassenger = new ArrayList<Long>();
	    	for (int i=1;i<qwerty[1].length();i++) {
	    		 new_qwerty1+=qwerty[1].charAt(i);
	    	 }
	    	 qwerty[1]=new_qwerty1;
	    	for (int i=2;i<z[3].length();i++) {
	    		edited_string+=z[3].charAt(i);
	    	}
	    	z[3]=edited_string.strip();
	    	for (int i=0;i<z[z.length-1].length()-1;i++) {
	    		new_edited_string+=z[z.length-1].charAt(i);
	    	}
	    	
	    	z[z.length-1]=new_edited_string.strip();
	    	for (int k=0;k<qwerty.length;k++) {
	    		input_perline_arraylist.add(qwerty[k]);
	    	}
	    	for (int i=1;i<z.length;i++) {
	    		input_perline_arraylist.add(z[i].strip());
	    	}
	    	for (int i=4;i<input_perline_arraylist.size();i++) {
	    		String[] for_striping = input_perline_arraylist.get(i).split(" ");
	    		Long destinationn =Long.parseLong(for_striping[0]);
	    		destinationListsPerPassenger.add(destinationn);
	    	}
	    	
	    	
	    	
	    	Long passenger_parameter1 =Long.parseLong(input_perline_arraylist.get(1));
	    	Double passenger_parameter2 = Double.parseDouble(input_perline_arraylist.get(2));
	    	Integer passenger_parameter3 = Integer.parseInt(input_perline_arraylist.get(3));
	    	
	    	airline.passengercreation(input_perline_arraylist.get(0), passenger_parameter1, passenger_parameter2, passenger_parameter3, destinationListsPerPassenger);
	    	input_perline_arraylist.clear();	
	    	
	    }
	    
	    airline.aircraft_creations(airline.get_id_of_passengers_first_destination(0), widebody_operation_fee, 1);
	    airline.check_passenger_cangoanydestionation(0);
	    
	    ArrayList<String> output_arraylist = airline.whole_file_to_write();
	    for (String elem:output_arraylist) {
	    	 myWriter.write(elem);
	    	 myWriter.write("\n");
	    }
	    myWriter.close();
	}
	
}



