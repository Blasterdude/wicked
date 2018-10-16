package dao;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import airplane.Airplanes;
import airport.Airports;
import flight.Flights;

public class ServerInterfaceTest {

	String TeamName = "WickedSmaht";
	static ServerInterface SI;

	@BeforeClass
	public static void init() {
		SI = new ServerInterface();
	}

	@Test
	public void testGetAirports() throws Exception {
		Airports aList = SI.getAirports(TeamName);

		assertTrue(aList.size() > 0);

	}

	@Test
	public void testGetAirplanes() throws Exception {
		Airplanes apList = SI.getAirplanes(TeamName);

		assertTrue(apList.size() > 0);
	}

	@Test
	public void testGetFlights() throws Exception {
		Flights fList = SI.getFlights(TeamName, "arriving", "BOS", "2017_05_10");

		assertTrue(fList.size() > 0);	}

	@Test
	public void testLock() throws Exception {
		assertTrue(SI.lock(TeamName));
		SI.unlock(TeamName);
	}

	@Test
	public void testUnlock() throws Exception {
		assertTrue(SI.unlock(TeamName));
	}

	@Test
	public void testBuyTickets() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

}
