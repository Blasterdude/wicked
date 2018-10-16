package dao;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class DaoFlightTest {

	@Test
	public void testAddAll() throws Exception {
		String contents = new String(Files.readAllBytes(Paths.get("src/sampleXML/sampleFlights.xml")));

		try{
		DaoFlight.addAll(contents);
		}
		catch(Exception  e){
			e.toString();
		}
	}

}
