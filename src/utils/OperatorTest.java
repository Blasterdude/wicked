package utils;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import Trip.Trips;
import flight.Flights;

public class OperatorTest {
    Trips listOfTrips_departing;
    Trips listOfTrips_returning;

	String depAIR;
	String arrAIR;
	String depTime;
	String retTime;
	
	Flights reservation;
	
	String typeOfSeat;
	
    @Before
    public void initialize() {
        depAIR = "BOS";
        arrAIR = "SFO";
        depTime = "2017_05_12";
        retTime = "2017_05_13";
        typeOfSeat = "Coach";
    }
	
	
    @Test
	public void processTest() {
    	Trips[] trips = Operator.process(depAIR, arrAIR, depTime, retTime);
    	listOfTrips_departing = trips[0];
    	listOfTrips_returning = trips[1];
    	assertFalse(listOfTrips_departing.isEmpty());
    	assertFalse(listOfTrips_returning.isEmpty());
    	
    	trips = Operator.process(depAIR, arrAIR, depTime, null);
    	listOfTrips_departing = trips[0];
    	listOfTrips_returning = trips[1];
    	assertFalse(listOfTrips_departing.isEmpty());
    	assertTrue(listOfTrips_returning.isEmpty());
    	
    	reservation.addAll(listOfTrips_departing.get(0).Flights());
	}
    
    @Test
    public void reserveFlightTest() {
    	boolean result = Operator.reserveTrip(reservation, typeOfSeat);
    	assertTrue(result);
    }
}
