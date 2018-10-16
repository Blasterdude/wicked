package flight;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * The Class FlightInfoTest.
 */
public class FlightInfoTest {
	String tCode = "MDW";
	String tDate = "2017 May 11 08:36 GMT";

	FlightInfo testInfo = new FlightInfo(tCode, tDate);

	public ExpectedException thrown = ExpectedException.none();

	/**
	 * Test default constructor.
	 *
	 * @throws Exception if class fails to instantiate
	 */
	@Test
	public void testDefaultFlightInfo() throws Exception {

		try {
			new FlightInfo();
		} catch (Exception e) {
			e.toString();
		}
	}
	/**
	 * Test constructor given correct airport code and date string.
	 *
	 * @throws Exception if class fails to instantiate
	 */
	@Test
	public void testFlightInfo() throws Exception {

		try {
			new FlightInfo(tCode, tDate);
		} catch (Exception e) {
			e.toString();
		}

	}

	/**
	 * Test bad flight info.
	 * If the input date string is not correct, throw an exception
	 * @throws Exception that date is not correct.  
	 */
	@Test
	public void testBadFlightInfo() throws Exception {
		try {
			new FlightInfo(tCode, "DJF");
			thrown.expect(RuntimeException.class);
		} catch (Exception e) {
			e.toString();
		}

	}

	@Test
	public void testFlightInfoToString() {
		String testStr = "2017 May 11 08:36 GMT MDW";
		assertEquals(testInfo.toString(), testStr);
	}
	
	@Test
	public void testIsFlightValid(){
		assertTrue(testInfo.isValid(testInfo));
	}

}
