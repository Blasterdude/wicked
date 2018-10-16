package flight;

import java.text.SimpleDateFormat;
import java.util.Date;

import utils.Utils;

public class FlightController {
	private static FlightController myFC;

	private FlightController() {
	}

	public static FlightController getInstance() {
		if (myFC == null) {
			myFC = new FlightController();
			return myFC;
		} else {
			return myFC;
		}
	}

	public static String extractFlightTime(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");

		return df.format(date);
	}

	public String extractFlightMDY(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("MMM dd YYYY");

		return df.format(date);
	}
	
	public String getLocalTime(Date date, String airportCode)
	{		 
		 return extractFlightTime(Utils.getUTC(date,  airportCode));
	}

}
