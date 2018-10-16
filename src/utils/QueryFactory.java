/**
 * 
 */
package utils;

/**
 * @author blake
 * @version 1.2
 *
 */
public class QueryFactory {

	/**
	 * Return a query string that can be passed to HTTP URL to request list of
	 * airports
	 * 
	 * @param teamName
	 *            is the name of the team to specify the data copy on server
	 * @return the query String which can be appended to URL to form HTTP GET
	 *         request
	 *         
	 * Here is an example query
	 * http://cs509.cs.wpi.edu:8181/CS509.server/ReservationSystem?team=WickedSmaht&action=list&list_type=departing&airport=ANC&day=2017_05_10;
	 */
	public static String getAirports(String teamName) {
		return "?team=" + teamName + "&action=list&list_type=airports";
	}

	public static String getFlights(String teamName, String type, String code, String day) {
		return "?team=" + teamName + "&action=list&list_type=" + type + "&airport=" + code + "&day=" + day;
	}

	public static String getAirplanes(String teamName) {
		return "?team=" + teamName + "&action=list&list_type=airplanes";
	}

	/**
	 * Lock the server database so updates can be written
	 * 
	 * @param teamName
	 *            is the name of the team to acquire the lock
	 * @return the String written to HTTP POST to lock server database
	 */
	public static String lock(String teamName) {
		return "team=" + teamName + "&action=lockDB";
	}

	/**
	 * Unlock the server database after updates are written
	 * 
	 * @param teamName
	 *            is the name of the team holding the lock
	 * @return the String written to the HTTP POST to unlock server database
	 */
	public static String unlock(String teamName) {
		return "team=" + teamName + "&action=unlockDB";
	}
	
	public static String buyTicket(String teamName, String xmlFlights) {
		return "team=" + teamName + "&action=buyTickets" + "&flightData=" + xmlFlights;
		
	}
	
	public static String reset (String teamName) {
		return "team=" + teamName + "&action=resetDB";
	}
}
