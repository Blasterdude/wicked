package utils;

import Trip.Trips;
import Trip.Trip;
import airplane.Airplane;
import airplane.Airplanes;
import airport.Airport;
import airport.Airports;
import dao.ServerInterface;
import flight.Flight;
import flight.Flights;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Operation class is a static factory. It provides algorithm to search database and compute 
 * the flights combinations and send back status information based on the search result.
 * 
 * This class provides two static function:
 * 1. "process" to search the database to make combinations of flights as trips, 
 * 2. "reserveTrip" to book a trip. 
 * 
 * The variable fields are private without getters and setters. Some static fields are modified final as
 * they shall not be accessed or modified outside the class. 
 * 
 * AIRPORTS and AIRPLANES collections are initialized once when Operator class gets called and 
 * are re-usable for the class.
 *  
 * @author Yi 
 * @version 1.0
 * @since 2017/3/26.
 * 
 */
public class Operator {
	/**
	 * TEAM_DB	Agent name to access database server.
	 * minLayover	Minimum layover time between legs as 0.5 hr.
	 * maxLayover	Maximum layover time between legs as 4 hr.
	 * 
	 * MonthSwitch	Transfer three-letter month representation into two-digits representation.
	 * 				For making search query for the next leg upon the GMT date from last leg. 	
	 */
    private static final ServerInterface sys = new ServerInterface();
    private static final String TEAM_DB = "WickedSmaht";
    private static final double minLayover = 0.5 * 3600000;
    private static final double maxLayover = 4 * 3600000;
    private static final HashMap<String, String> MonthSwitch = new HashMap<>();
    static {
        MonthSwitch.put("Jan", "01");
        MonthSwitch.put("Feb", "02");
        MonthSwitch.put("Mar", "03");
        MonthSwitch.put("Apr", "04");
        MonthSwitch.put("May", "05");
        MonthSwitch.put("Jun", "06");
        MonthSwitch.put("Jul", "07");
        MonthSwitch.put("Aug", "08");
        MonthSwitch.put("Sep", "09");
        MonthSwitch.put("Oct", "10");
        MonthSwitch.put("Nov", "11");
        MonthSwitch.put("Dec", "12");
    }

    /**
     * AIRPORT	Collections of all airports.
     */
    private static final HashSet<String> AIRPORT = new HashSet<>();
    static {
        Airports airports = new Airports();
        airports.addAll(sys.getAirports(TEAM_DB));
        for(Airport airport : airports) {
            AIRPORT.add(airport.code());
        }
    }

    /**
     * AIRPLANES	Collections of all airplanes.
     */
    private static final HashMap<String, Airplane> AIRPLANES = new HashMap<>();
    static {
        Airplanes airplanes = new Airplanes();
        airplanes.addAll(sys.getAirplanes(TEAM_DB));
        for(Airplane airplane : airplanes) {
            AIRPLANES.put(airplane.getModel(), airplane);
        }
    }

    /**
     * listOFTrips_departing	Collections store the results of departing trips;
     * listOfTrips_returning	Collections store the results of returning trips.
     * check	Collections store the selected flights for reservation.
     */
    private static Trips listOfTrips_departing = new Trips();
    
    public static Trips getListOfTrips_departing() {
		return listOfTrips_departing;
	}

	private static Trips listOfTrips_returning = new Trips();
	
    public static Trips getListOfTrips_returning() {
		return listOfTrips_returning;
	}


	private static Flights check = new Flights();

    /**
     * Private constructor. Operator class is a static factory and shall not be instantiated
     * as an instance 
     */
    private Operator() {}
    
    /**
     * Return an array of trips of length two (Trips[0] is the departing trips and Trips[1]
     * is the returning trips. All the trips will have a direct flight from depAIR to arrAIR,
     * or a series of linked flights with legs no more than three, where the first leg starts
     * from depAIR and the last leg arrives at arrAIR. The layover between each leg of a trip
     * will meet the requirement of the maximum and minimum layover time.
     * @param depAIR 
     * 			The departure airport code.
     * @param  arrAIR 
     * 			The arrival airport code.
     * @param  depTime 
     * 			The departure time as GMT in format YYYY_MM_DD
     * @param  retTime
     * 			The returning time as GMT in format YYYY_MM_DD; for one-way trip retTime can
     * 			be null as an input
     * @return An array of Trips between depAIR and arrAIR; can be empty array if no result
     * 			found 
     */
    public static Trips[] process(String depAIR,
                                           String arrAIR,
                                           String depTime,
                                           String retTime) {
        listOfTrips_departing.clear();
        listOfTrips_returning.clear();
        // Valid input check

        if(depAIR == null || arrAIR == null || depTime == null ||
                !AIRPORT.contains(depAIR) || !AIRPORT.contains(arrAIR)) {
            return new Trips[]{listOfTrips_departing, listOfTrips_returning};
        }

        System.out.println("Process function gets called. Working ...");
        listOfTrips_departing = searchHelper(depAIR, arrAIR, depTime);
        if(retTime != null) {
            System.out.println("Search for returning trips ...");
            listOfTrips_returning = searchHelper(arrAIR, depAIR, retTime);
        }

        return new Trips[] {listOfTrips_departing, listOfTrips_returning};
    }

    /**
     * Return a list of Trips from depAIR to arrAIR at depTime.
     * 
     * Retrieve a list of flights starting from depAIR at depTime and a list of flights
     * arriving at arrAIR in the next three days. Search for connecting flights between both
     * lists which will satisfy the layover time.
     * @param depAIR	The departure airport code
     * @param arrAIR	The arrival airport code
     * @param depTime	The departure GMT.
     * @return list of Trips from depAIR to arrAIR
     */
    private static Trips searchHelper(String depAIR, String arrAIR, String depTime) {

        Trips trips = new Trips();

        HashMap<Flight, Flights> search_two = new HashMap<>();

        int tripID = 0;
        //Search the DB: the real logic part

        //First leg
        Flights search_one;
        search_one = sys.getFlights(TEAM_DB,"departing", depAIR, depTime);

        System.out.println("Leg 1 search completed. Working on next stage ...");

        //Third leg
        Flights search_three;
        search_three = sys.getFlights(TEAM_DB,"arriving", arrAIR, depTime);
        Flights search_three_2;
        search_three_2 = sys.getFlights(TEAM_DB,"arriving", arrAIR, getNextDay(depTime));
        search_three.addAll(search_three_2);
        Flights search_three_3 ;
        search_three_3 = sys.getFlights(TEAM_DB, "arriving", arrAIR, getNextDay(getNextDay(depTime)));
        search_three.addAll(search_three_3);
        System.out.println("Leg 3 search completed. Working on next stage ...");

        if(search_one.isEmpty() || search_three.isEmpty()) {
            System.out.println("No flight from depart airport OR no flight to arriving airport. ");
            return trips;
        }

        for(Flight f : search_one) {
            if(f.isValid()) {
                if (f.getmArriveAir().equals(arrAIR)) {
                    // For direct flight it will be added straight to the result listOfTrips
                    Trip li = new Trip();
                    li.setID(tripID);
                    li.add(f);
                    trips.append(li);
                    tripID ++;
                    //System.out.println("Trip added to results." + tripID);
                } else {
                    // Search for the second leg and store the result temporarily in the HashMap search_two
                    String arrival = toTime(f.getmArriveDateAndTimeAsString());
                    String arr_next = getNextDay(arrival);
                    Flights search_second = new Flights();
                    Flights search_second_2 = new Flights();
                    search_second.addAll(sys.getFlights(TEAM_DB,"departing", f.getmArriveAir(), arrival));
                    search_second_2.addAll(sys.getFlights(TEAM_DB, "departing", f.getmArriveAir(), arr_next));
                    search_second.addAll(search_second_2);
                    if(!search_second.isEmpty()) {
                        search_two.put(f, search_second);
                    }
                }
            }
        }

        System.out.println("Leg 2 search complete. Working on next stage ...");

        for(Flight f : search_two.keySet()) {
            Flights flights = search_two.get(f);
            for(Flight f_s : flights) {
                //System.out.println(f.getmTimeArrival() + f_s.getmTimeDepart() + " " + isWithinLayover(f.getmTimeArrival(), f_s.getmTimeDepart()));
                if(f_s.isValid() && isWithinLayover(f.getmArriveDateAndTimeAsString(), f_s.getmDepartDateAndTimeAsString())) {
                    if (f_s.getmArriveAir().equals(arrAIR)) {
                        // For 1 leg flight it will be added to the result listOfTrips when the second flight has the arrAIR code the same as the destination
                        Trip li = new Trip();
                        li.setID(tripID);
                        li.add(f);
                        li.add(f_s);
                        trips.append(li);
                       // System.out.println("Trip added to results." + tripID);
                        tripID ++;
                    } else {
                        for(Flight f_t : search_three) {
                            // For 2 legs flight it will be added to the result of the f_s.ariAIR == f_t.depAIR and layover is reasonable
                            if(f_s.getmArriveAir().equals(f_t.getmDepartAir())
                                    && isWithinLayover(f_s.getmArriveDateAndTimeAsString(), f_t.getmDepartDateAndTimeAsString())) {
                                Trip li = new Trip();
                                li.setID(tripID);
                                li.add(f);
                                li.add(f_s);
                                li.add(f_t);
                                trips.append(li);
                           //     System.out.println("Trip added to results." + tripID);
                                tripID ++;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Search completed. Returning results ...");

        return trips;
    }

    /**
     * Send reserve request with a selected trip to the database server and book the flights
     * 
     * @param reservation	List of flights contained in the selected Trip
     * @param typeOfSeat	Coach or FirstClass
     * @return	True for successful reservation and false for failure
     */
    public static boolean reserveTrip(List<Flight> reservation,
                                               String typeOfSeat) {

        System.out.println("reserveTrip function gets called. Working ...");
        return reserveHelper(reservation, typeOfSeat);
    }

    /**
     * Lock the database, check the availability of the seat chosen for the selected Trip
     * book the flights if all the required seats are available and retrieve success
     * information; or send failure information if any of the required seat is not available.
     * 
     * @param reservation	List of flights contained in the selected Trip.
     * @param typeOfSeat	Coach of FirstClass.
     * @return	True for successful reservation and false for failure.
     */
    private static boolean reserveHelper(List<Flight> reservation,
                                        String typeOfSeat) {
        check.clear();
        
        // Availability check
        //TODO:Don't need to lock database a second time when reserving multiple flights
        //There is an error here with reserving multiple flights
        sys.lock(TEAM_DB);
        for(Flight flight : reservation) {
            Flights temp= sys.getFlights(TEAM_DB, "departing", flight.getmDepartAir(), toTime(flight.getmDepartDateAndTimeAsString()));

            for (Flight f : temp) {

                String tmpFlightNumber = f.mNumberAsString();
                if (tmpFlightNumber.equals(flight.mNumberAsString())) {
                    int seatsAva = typeOfSeat.equals("Coach") ? AIRPLANES.get(f.Airplane()).getCoachSeats() : AIRPLANES.get(f.Airplane()).getFirstclassSeats();
                    if(getSeats(f, typeOfSeat) >= seatsAva) {
                        sys.unlock(TEAM_DB);
                        System.out.println("Reservation fails: seats not available.");
                        return false;
                    }
                    check.add(f);
                    break;
                }
            }
        }

        // Reserve the flights
        for(Flight f : check) {
            String xmlReservation = "<Flights>"
                    + "<Flight number=\"" + f.Number() + "\" seating=\""
                    + typeOfSeat + "\"/>" + "</Flights>";
            sys.buyTickets(TEAM_DB, xmlReservation);
            sys.unlock(TEAM_DB);
        }

        System.out.println("Database updated. Checking the reservation information ...");

        //TODO:This verification code does not seem to work properly, but flights are still  booked since 'sys.buyTickets' is called above"
        //Verify the operation worked
        List<Integer> seatsReservedEnd = new ArrayList<Integer>();
        for(Flight flight : check) {
            int seatsReservedStart = getSeats(flight, typeOfSeat);

            Flights temp = sys.getFlights(TEAM_DB, "departing", flight.getmDepartAir(), toTime(flight.getmDepartDateAndTimeAsString()));

            for (Flight f : temp) {
                String tmpFlightNumber = f.mNumberAsString();
                if (tmpFlightNumber.equals(flight.mNumberAsString())) {
                	System.out.println(seatsReservedEnd);
                    seatsReservedEnd.add(getSeats(f, typeOfSeat));
                    break;
                }
            }
            
            if (seatsReservedEnd.get(seatsReservedEnd.size() - 1) == (seatsReservedStart + 1)) {
            	continue;
            } else {
            	System.out.println("Reservation failed: database update error.");
            	return false;
            }
        }

        System.out.println("Reservation confirmed. All set.");
        return true;
    }

    /**
     * Return the number of seats have been booked for the selected type of seats of the flight
     * 
     * @param flight	
     * @param typeOfSeat Coach or FirstClass
     * @return	Number of seats booked with specified type of seat.
     */
    private static int getSeats(Flight flight, String typeOfSeat) {
        if(typeOfSeat.equals("Coach")) {
            return flight.Coach().mReservations();
        }
        return flight.FirstClass().mReservations();
    }
    
    /**
     * Return if the connecting flight have the layover time meet the requirement of
     * the maximum and minimum layover time.
     * 
     * @param arr	The arriving time of the first flight in GMT.
     * @param dep	The departure time of the second flight in GMT.
     * @return	True for meeting the requirement and false for not meeting the requirement.
     */
    private static boolean isWithinLayover(String arr, String dep) {
        Calendar depC = Calendar.getInstance();
        depC.set(Calendar.YEAR, Integer.parseInt(dep.split("\\s")[0].trim()) + 1900);
        depC.set(Calendar.MONTH, Integer.parseInt(MonthSwitch.get(dep.split("\\s")[1].trim())));
        depC.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dep.split("\\s")[2].trim()));
        depC.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dep.split("\\s")[3].trim().split(":")[0].trim()));
        depC.set(Calendar.MINUTE, Integer.parseInt(dep.split("\\s")[3].trim().split(":")[1].trim()));
        Calendar arrC = Calendar.getInstance();
        arrC.set(Calendar.YEAR, Integer.parseInt(arr.split("\\s")[0].trim()) + 1900);
        arrC.set(Calendar.MONTH, Integer.parseInt(MonthSwitch.get(arr.split("\\s")[1].trim())));
        arrC.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arr.split("\\s")[2].trim()));
        arrC.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arr.split("\\s")[3].trim().split(":")[0].trim()));
        arrC.set(Calendar.MINUTE, Integer.parseInt(arr.split("\\s")[3].trim().split(":")[1].trim()));
        double delay = depC.getTimeInMillis() - arrC.getTimeInMillis();
        return !(delay > maxLayover || delay < minLayover);
    }

    /**
     * Get the next calendar date of the input date in GMT.
     * 
     * @param date 	Current calendar date in format YYYY_MM_DD.
     * @return	The next date of the input date in format YYYY_MM_DD.
     */
    private static String getNextDay(String date) {
        Calendar C = Calendar.getInstance();
        C.set(Calendar.YEAR, Integer.parseInt(date.split("_")[0].trim()));
        C.set(Calendar.MONTH, Integer.parseInt(date.split("_")[1].trim()));
        C.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.split("_")[2].trim()));
        C.setTimeInMillis(C.getTimeInMillis() + 86400000);
        return "" +  Calendar.YEAR + "_" + String.format("%02d",Calendar.MONTH) + "_" + String.format("%02d", Calendar.DAY_OF_MONTH);
    }

    /**
     * Date format switcher. From format YYYY MMM DD HH:mm GMT to YYYY_MM_DD.
     * 
     * @param Time	GMT format of date.
     * @return	The same date in format YYYY_MM_DD.
     */
    private static String toTime(String Time) {
        String[] dataForm = Time.split(" ");
        return dataForm[0].trim() + '_' + MonthSwitch.get(dataForm[1].trim()) + '_' + dataForm[2].trim();
    }

}
