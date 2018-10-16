package airplane;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class AirplaneTest {

	static String Manufacturer;
	static String Model;

	static int firstClassSeats;
	static int coachSeats;

	static Airplane testAirplane;

	@BeforeClass
	public static void setupTest() {
		Manufacturer = "Boeing";
		Model = "747";

		firstClassSeats = 25;
		coachSeats = 24;

		testAirplane = new Airplane(Manufacturer, Model, firstClassSeats, coachSeats);
	}

	@Test
	public void testAirplane() throws Exception {
		try {
			new Airplane();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testAirplaneWithParams() throws Exception {
		try {
			new Airplane(Manufacturer, Model, firstClassSeats, coachSeats);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testToString() throws Exception {
		String testStr = Manufacturer + ", " + Model + ", " + firstClassSeats + ", " + coachSeats;

		assertEquals(testAirplane.toString(), testStr);
	}

	@Test
	public void testIsValid() throws Exception {
		assertTrue(testAirplane.isValid());
	}

	@Test
	public void testGetManufacturer() throws Exception {
		assertEquals(testAirplane.getManufacturer(), "Boeing");
	}

	@Test
	public void testGetModel() throws Exception {
		assertEquals(testAirplane.getModel(), "747");
	}

	@Test
	public void testGetFirstclassSeats() throws Exception {
		assertEquals(testAirplane.getFirstclassSeats(), 25);
	}

	@Test
	public void testGetCoachSeats() throws Exception {
		assertEquals(testAirplane.getCoachSeats(), 24);
	}

}
