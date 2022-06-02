package project.passengercontainer;

import java.util.ArrayList;

import project.airportcontainer.Airport;
import project.airportcontainer.HubAirport;
import project.airportcontainer.MajorAirport;
import project.airportcontainer.RegionalAirport;

public class FirstClassPassenger extends Passenger{
	public FirstClassPassenger(long ID, double weight, int baggageCount, ArrayList destinations) {
		super(ID, weight, baggageCount, destinations);
		
	}

	protected double calculateTicketPrice(Airport toAirport, double aircraftTypeMultiplier) {
		double airportMultiplier=0;
		double distance= Math.pow(( Math.pow((toAirport.getxPosition()-currentAirport.getxPosition()),2)+Math.pow((toAirport.getyPosition()-currentAirport.getyPosition()),2)),0.5);
		
		if(currentAirport instanceof HubAirport & toAirport instanceof HubAirport )
			airportMultiplier=0.5;
		if(currentAirport instanceof HubAirport & toAirport instanceof MajorAirport )
			airportMultiplier=0.7;
		if(currentAirport instanceof HubAirport& toAirport instanceof RegionalAirport )
			airportMultiplier=1;
		if(currentAirport instanceof MajorAirport & toAirport instanceof HubAirport )
			airportMultiplier=0.8;
		if(currentAirport instanceof MajorAirport & toAirport instanceof HubAirport )
			airportMultiplier=0.6;
		if(currentAirport instanceof MajorAirport & toAirport instanceof HubAirport )
			airportMultiplier=1.8;
		if(currentAirport instanceof RegionalAirport & toAirport instanceof HubAirport )
			airportMultiplier=3.0;
		if(currentAirport instanceof RegionalAirport & toAirport instanceof HubAirport )
			airportMultiplier=0.9;
		if(currentAirport instanceof RegionalAirport & toAirport instanceof HubAirport )
			
			airportMultiplier=1.6;
		double passengerMultiplier=3.2;
		double priceWithoutBaggage= distance*aircraftTypeMultiplier*connectionMultiplier*airportMultiplier*passengerMultiplier;
		return priceWithoutBaggage*(1+0.05*countofBaggage());
	}
}
