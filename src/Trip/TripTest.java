package Trip;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import flight.Flight;
import flight.FlightInfo;
import flight.Flights;
import flight.Seating;

public class TripTest {

	static public Flights flightList;
	static public int tripID;
	static public Trip testTrip;
	static Flight f1;
	static Flight f2;
	static Flight f3;

	@BeforeClass
	public static void setup() {
		flightList = new Flights();

		f1 = new Flight("747", 90, 555, new FlightInfo("MDW", "2017 May 10 00:36 GMT"),
				new FlightInfo("BOS", "2017 May 10 00:36 GMT"), new Seating("27.50", 34), new Seating("20.80", 27));

		f2 = new Flight("747", 90, 555, new FlightInfo("BOS", "2017 May 10 00:36 GMT"),
				new FlightInfo("CHI", "2017 May 10 00:36 GMT"), new Seating("18.25", 19), new Seating("14.75", 16));

		f3 = new Flight("747", 90, 555, new FlightInfo("CHI", "2017 May 10 00:36 GMT"),
				new FlightInfo("ATL", "2017 May 10 00:36 GMT"), new Seating("15.00", 21), new Seating("12.00", 22));

		testTrip = new Trip();
		testTrip.setID(1);
		testTrip.Flights().add(f1);
		testTrip.Flights().add(f2);
		testTrip.Flights().add(f3);

	}

	@Test
	public void testTrip() throws Exception {
		try {
			new Trip();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@Test
	public void testAdd() throws Exception {
		try {
			testTrip.add(f1);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@Test
	public void testGetID() throws Exception {
		assertEquals(testTrip.getID(), 1);
	}

	@Test
	public void testSetID() throws Exception {
		testTrip.setID(2);
		assertEquals(testTrip.getID(), 2);
	}

	@Test
	public void testFlights() throws Exception {
		assertEquals(3, testTrip.Flights().size());
	}

	@Test
	public void testToString() throws Exception {
		String testString = "1: 747, BOS-555 from MDW on 2017 May 10 00:36 GMT, "
				+ "2: 747, CHI-555 from BOS on 2017 May 10 00:36 GMT, " 
				+ "3: 747, ATL-555 from CHI on 2017 May 10 00:36 GMT, ";

		assertEquals(testString, testTrip.toString());
	}

}
