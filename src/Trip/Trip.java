package Trip;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import flight.Flight;
import flight.Flights;
import utils.Utils;

/**
 * This class contains a Flights object as a set of linked flights from departure to 
 * destination. 
 * 
 * ID is the attribute used to identify and search the Trip object.
 * depAirportCode and depTime uses the first flight's depart airport code and depTime
 * arrAirportCode and arrTime uses the last flight's arrival airport code and arrTime
 * priceCoach and priceFirstClass are summations of coach and firstclass price in each flight
 * numberOfLegs is the number of flights added.
 * 
 * Trip class is designed to contain a list of flight that will meet the requirement of a
 * trip from departure airport to arrival airport with legs no more than 3.
 * 
 * @author Yi
 * @version 1.0
 * @since 2017/3/26.
 */
public class Trip {
    private Flights flights = new Flights();
    private int ID;
    private String depAirportCode;
    private String arrAirportCode;
    private String depTime;
    private String arrTime;
    private double priceCoach = 0;
    private double priceFirstClass = 0;
    private int travelTime = 0;
	private int numberOfLegs = 0;
    

    /**
     * The method is used to add a flight to the variable flights.
     * 
     * @param flight
     */
    public void add(Flight flight) { 	
    	if(this.flights.size() == 0) {
    		this.depAirportCode = flight.getmDepartAir();
    		this.depTime = flight.getmDepartDateAndTimeAsString();
        }
    	this.arrAirportCode = flight.getmArriveAir();
    	this.arrTime = flight.getmArriveDateAndTimeAsString();
    	this.priceCoach += Utils.priceToDouble(flight.Coach().mPrice()) ;
    	this.priceFirstClass +=  Utils.priceToDouble(flight.FirstClass().mPrice()) ;
    	this.travelTime += flight.FlightTime();
    	this.numberOfLegs ++;
    	this.flights.add(flight);
        
    }

    // Getter and setters
    public int getID() {
        return ID;
    }

    public Flights Flights() {
		return flights;
	}

	public String getDepAirportCode() {
		return depAirportCode;
	}

	public String getArrAirportCode() {
		return arrAirportCode;
	}

	public String getDepTime() {
		return depTime;
	}

	public String getArrTime() {
		return arrTime;
	}

	public double getPriceCoach() {
		return priceCoach;
	}

	public double getPriceFirstClass() {
		return priceFirstClass;
	}

	public int getNumberOfLegs() {
		return numberOfLegs;
	}
	
	public int getTravelTime() {
		return travelTime;
	}

	public void setID(int ID) {
        this.ID = ID;
    }

	
}
