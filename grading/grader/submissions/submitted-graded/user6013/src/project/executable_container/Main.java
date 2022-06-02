package project.executable_container;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import project.airline_container.Airline;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Scanner scan = new Scanner(System.in);
//		String filename = scan.nextLine();
//		String output = scan.nextLine();
//		scan.close();		
		try {

			    File myObj = new File(args[0]);
			    Scanner myReader = new Scanner(myObj);
			    String[] line1  = myReader.nextLine().split(" ");
			    int maxAircraft = Integer.parseInt(line1[0]);
				int no_of_Airports = Integer.parseInt(line1[1]);
				int no_of_Passengers = Integer.parseInt(line1[2]);
				String[] line2  = myReader.nextLine().split(" ");
				double prop = Double.parseDouble(line2[0]);
				double widebody = Double.parseDouble(line2[1]);
				double rapid = Double.parseDouble(line2[2]);
			    double jet = Double.parseDouble(line2[3]);
			    double operational_cost = Double.parseDouble(line2[4]);
			    Airline Lounge = new Airline(maxAircraft,operational_cost,prop,widebody,rapid,jet);
			    for(int i=0;i<no_of_Airports;i++) {
			    	String[] line3 = myReader.nextLine().split(":");
			    	String[] line4 = line3[1].strip().split(", ");
			    	Lounge.createAirport(line3[0].strip(), Integer.parseInt(line4[0]), Double.parseDouble(line4[1]), Double.parseDouble(line4[2]), Double.parseDouble(line4[3]), Double.parseDouble(line4[4]),Integer.parseInt(line4[5]));
			    }
			    for(int i=0;i<no_of_Passengers;i++) {
			    	String[] line3 = myReader.nextLine().split(":");
			    	
			    	String[] line4 = line3[1].strip().split("\\[");
			    	String[] line5 = line4[0].split(",");
			    	String[] line6 = line4[1].replace("]","").split(",");
			    	Lounge.createPassenger(line3[0].strip(), Long.parseLong(line5[0].strip()), Double.parseDouble(line5[1].strip()), Integer.parseInt(line5[2].strip()), line6);
			    	
			    }
			   for(int asdx =0;asdx<1;asdx++) {
				   ArrayList <Integer> airports=Lounge.AirportsSorted();
				    for(int x:airports) {
				    	if(Lounge.getNo_of_Aircrafts()<1+Lounge.getMax_Aircraft()/2 &&Lounge.Airports.get(x).getPass().size()!=0) {
				    		
				    		boolean flag = false;
				    		int aircraft_type = 4;
				    		for(int y:Lounge.FindRoutes(Lounge.Airports.get(x))) {
				    			if(Lounge.getAvWeight(aircraft_type)>Lounge.CalculateWeightofFuelNeeded(aircraft_type,Airline.getDistance(Lounge.Airports.get(x), Lounge.Airports.get(y)))&& Lounge.Airports.get(y).getaircraftRatio()<1) {
				    				Lounge.notrealaircraftcreation(aircraft_type);
				    				int tp = Lounge.Airports.get(x).getPass().size();
				    				int ec=0,bus=0,first=0;
				    				for(int i =0;i<tp;i++) {
				    					if(Lounge.Airports.get(x).getPass().get(i).getDestinations().contains(Lounge.Airports.get(y))) {
				    						
				    					
				    					int a=0,b=0,c=0;
			    						switch(Lounge.Airports.get(x).getPass().get(i).getPasstype()){
			    						case 1:
			    							a = 1;
			    							break;
			    						case 2:
			    							b = 0;
			    							break;
			    						case 3:
			    							c = 0;
			    							break;
			    						
			    						
			    						}
				    					if((Lounge.canSetSeats(a, b, c))&&10>ec+bus){
				    						if(!flag) {
					    						flag = true;
					    					}
				    						ec += a;
				    						bus += b;
				    						first += c;
				    					}
				    				}
		    				}
				    				if(flag&&10>=ec+bus) {
				    					Lounge.createAircraft(aircraft_type,Lounge.Airports.get(x));
				    					Lounge.setSeats(ec, bus, first,Lounge.aircrafts.size()-1);
					    				int comp =0;
					    				for(int i =0;i<tp;i++) {
					    					if(Lounge.Airports.get(x).getPass().get(i-comp).getDestinations().contains(Lounge.Airports.get(y)) && Lounge.aircrafts.get(Lounge.aircrafts.size()-1).getAvailableWeight() >= Lounge.CalculateWeightofFuelNeeded(aircraft_type, Airline.getDistance(Lounge.Airports.get(x), Lounge.Airports.get(y)))+Lounge.Airports.get(x).getPass().get(i-comp).getWeight()) {
					    							
							
					    					if((Lounge.canLoadPassenger(Lounge.Airports.get(x).getPass().get(i-comp),Lounge.Airports.get(x) , Lounge.aircrafts.size()-1) )){
					    						Lounge.loadPassenger(Lounge.Airports.get(x).getPass().get(i-comp),Lounge.Airports.get(x) , Lounge.aircrafts.size()-1);
					    						comp += 1;
					    						
					    					}
					    				}
			    				}
					    				for(int i=0;i<Lounge.aircrafts.size();i++) {
					    					if(!(Lounge.aircrafts.get(i).isEmpty())) {
					    						
//					    						Lounge.addFuel(Lounge.aircrafts.size()-1,Lounge.aircrafts.get(Lounge.aircrafts.size()-1).seeFuelConsumption(Airline.getDistance(Lounge.Airports.get(x), Lounge.Airports.get(y)), Lounge.aircrafts.get(Lounge.aircrafts.size()-1).getWeight()+Lounge.CalculateWeightofFuelNeeded(aircraft_type, Airline.getDistance(Lounge.Airports.get(x), Lounge.Airports.get(y)))/0.7));
					    				Lounge.addFuel(Lounge.aircrafts.size()-1, Lounge.CalculateWeightofFuelNeeded(aircraft_type, Airline.getDistance(Lounge.Airports.get(x), Lounge.Airports.get(y)))/0.7);
					    					//Lounge.addFuel(Lounge.aircrafts.size()-1,2719);
					    						Lounge.fly(Lounge.Airports.get(y) , i);
					    						
						    					int tmp = Lounge.aircrafts.get(i).getOnboard_pas().size();
						    					
						    					for(int i2 =0;i2<tmp;i2++) {
						    						
							    					Lounge.unloadPassenger(Lounge.aircrafts.get(i).getOnboard_pas().get(0), Lounge.aircrafts.size()-1);
						    					}
					    					}
					    					
					    				}
				    				}
				    				
				    				
				    		
				    				
				    				break;
				    			}
				    		}
				    	}
				    }
			   }
			   

			    
			    
				 try {
				      FileWriter myWriter = new FileWriter(args[1]);
				      for(String x:Lounge.outputlog) {
							myWriter.write(x+"\n");
						}
				      myWriter.write(Lounge.getProfit());
				      myWriter.close();
				   
				    } catch (IOException e) {
				    }
				myReader.close();
			      
			    } catch (FileNotFoundException e) {
			    } 
		


	}

}
