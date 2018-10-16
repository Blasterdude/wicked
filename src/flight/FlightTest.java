package flight;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class FlightTest {

    static Flight testFlight;
	static FlightInfo departInfo;
	static FlightInfo arriveInfo;

	static Seating firstClass;
	static Seating coach;

	@BeforeClass
	public static void setup() {
		departInfo = new FlightInfo("MDW", "2017 May 10 00:36 GMT");
		arriveInfo = new FlightInfo("BOS", "2017 May 10 00:36 GMT");

		firstClass = new Seating("27.50", 55);
		coach = new Seating("13.50", 40);

		testFlight = new Flight("747", 90, 555, departInfo, arriveInfo, firstClass, coach);

	}

	@Test
	public void testFlight() {
		try {
			new Flight();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	/**
	 * @param airplane
	 *            * @param fTime * @param num * @param departure * @param
	 *            arrival
	 * @param fClass
	 *            * @param coach
	 */
	public void testFlightWithParameters() {
		try {
			new Flight("747", 90, 555, departInfo, arriveInfo, firstClass, coach);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	/**
	 * @param airplane
	 *            * @param fTime * @param num * @param departure * @param
	 *            arrival
	 * @param fClass
	 *            * @param coach
	 */
	public void testFlightToString() {
		String testString = "747, BOS-555 from MDW on 2017 May 10 00:36 GMT";

		try {
			assertEquals(testFlight.toString(), testString);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
