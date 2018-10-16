package flight;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import flight.Seating;

public class FlightViewTest {

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
	public void testFlightView() throws Exception {
		try {
			new FlightView(testFlight);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
