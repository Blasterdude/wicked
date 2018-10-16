package Trip;


import airport.Airports;
import dao.ServerInterface;
import utils.Operator;
import utils.Utils;

/**
 * The Class ViewController. A singleton class that serves as an interface
 * between the data in the view and the data created by the application and
 * supplied by the server
 */
public class TripsController {
	private ServerInterface SI;
	private String teamName = "WickedSmaht";
	private static TripsController myVC;
	private static boolean instanced;

	private TripsController() {
		SI = new ServerInterface();
	}

	public static TripsController getInstance() {
		if (!instanced) {
			myVC = new TripsController();
			return myVC;
		} else {
			return myVC;
		}
	}

	public Airports getAirportsForView() {
		return SI.getAirports(teamName);
	}

	public Trips getTripsByNumLegs(int i, Trips tripsToSort) {
			Trips filteredTrips = new Trips();
			for (Trip t :  tripsToSort) {
				if (t.getNumberOfLegs() == i) {
					filteredTrips.add(t);
				}
			}
			return filteredTrips;
		}
	
	public String validateDates(String depDate, String retDate) {
		String message;
		if (retDate != null) {
			// Check that dates occur in sequence
			if (!Utils.compareDates(depDate, retDate)) {
				// ((ValidatedViewPanel)
				// departPicker.getParent()).setErrorMessage("Help");
				message = "Enter a date that is after departure date";
			} else {
				message = "";
			}
		}

		else {
			message = "";
		}
		return message;
	}

	public String validateTripsExist(Trips trips, String type) {
		String message;

		if (trips.size() > 0) {
			message = "";
		} else {
			message = "No " + type + " Flights between these airports on this date";
		}
		return message;
	}

	public String validateAirportCodes(String departuresAirportCode, String destinationAirportCode) {
		if (departuresAirportCode.equals(destinationAirportCode)) {
			return "Departure and Destination are the Same";
		}

		return "";

	}
	
	public String validateAirportCode(String airportCode, String type) {
		if (airportCode.length() != 3) {
			switch (type) {
			case "departure":
				return "Select a departure airport";
			case "arrival":
				return "Select an arrival airport";
			default:
				return "Unable to validate airport code";
			}

		} else {
			return "";
		}
	}
}
