package dao;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import flight.Flight;
import flight.FlightInfo;
import flight.Flights;
import flight.Seating;
import utils.Utils;

public class DaoFlight {
	/**
	 * Builds a collection of flights from flights described in XML
	 * 
	 * Parses an XML string to read each of the flights and adds each valid
	 * flight to the collection. The method uses Java DOM (Document Object
	 * Model) to convert from XML to Java primitives.
	 * 
	 * @param xmlFlights XML string containing set of Flights
	 * 
	 * @return [possibly empty] collection of Flights in the xml string
	 * 
	 * @throws NullPointerException included to keep signature consistent with
	 * other addAll methods
	 * 
	 * @pre the xmlFlights string adheres to the format specified by the server
	 * API
	 * 
	 * @post the [possibly empty] set of Flights in the XML string are added to
	 * collection
	 */

	public static Flights addAll(String xmlFlights) throws NullPointerException {
		Flights Flights = new Flights();

		// Load the XML string into a DOM tree for ease of processing
		// then iterate over all nodes adding each Flight to our collection
		Document docFlights = Utils.buildDomDoc(xmlFlights);
		NodeList nodesFlights = docFlights.getElementsByTagName("Flight");

		for (int i = 0; i < nodesFlights.getLength(); i++) {
			Element elementFlight = (Element) nodesFlights.item(i);

			Flight Flight;
			try {
				Flight = buildFlight(elementFlight);
				if (Flight.isValid()) {
					Flights.add(Flight);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return Flights;
	}

	/**
	 * Creates an Flight object from a DOM node
	 * 
	 * Processes a DOM Node that describes an Flight and creates an Flight
	 * object from the information
	 * 
	 * @param nodeFlight
	 *            is a DOM Node describing an Flight
	 * @return Flight object created from the DOM Node representation of the
	 *         Flight
	 * @throws ParseException 
	 * 
	 * @pre nodeFlight is of format specified by CS509 server API
	 */
	static private Flight buildFlight(Node nodeFlight) throws ParseException {
		String Airplane;
		String FlightTime;
		String FlightNumber;

		FlightInfo departInfo;
		FlightInfo arriveInfo;

		Seating firstClass;
		Seating coach;
		// The Flight element has attributes of Airplane, Flighttime, number
		Element elementFlight = (Element) nodeFlight;
		Airplane = elementFlight.getAttributeNode("Airplane").getValue();
		FlightTime = elementFlight.getAttributeNode("FlightTime").getValue();
		FlightNumber = elementFlight.getAttributeNode("Number").getValue();
		// Get Airport codes and times from XML

		NodeList childNodes = elementFlight.getChildNodes();
		String depCode = "";
		String depTime = "";

		String arrCode = "";
		String arrTime = "";
		
		String cPrice = "";
		String fcPrice = "";
		
		int coachRes = -1;
		int fcRes = -1;
		
		// For each child, confirm name is departure or arrival
		for (int i = 0; i < childNodes.getLength(); i++) {
			String childName = childNodes.item(i).getNodeName();
			// Get departures
			if (childName == "Departure") {
				depCode = getCharacterDataFromElement(
						(Element) ((Element) childNodes.item(i)).getElementsByTagName("Code").item(0));
				depTime = getCharacterDataFromElement(
						(Element) ((Element) childNodes.item(i)).getElementsByTagName("Time").item(0));
			}
			// Get Arrivals
			if (childName == "Arrival") {
				arrCode = getCharacterDataFromElement(
						(Element) ((Element) childNodes.item(i)).getElementsByTagName("Code").item(0));
				arrTime = getCharacterDataFromElement(
						(Element) ((Element) childNodes.item(i)).getElementsByTagName("Time").item(0));
			}
			if (childName == "Seating") {
				//Get and set coach seating, reserved seats
				coachRes = Integer.parseInt(getCharacterDataFromElement(
						(Element) ((Element) childNodes.item(i)).getElementsByTagName("Coach").item(0)));
				
				fcRes = Integer.parseInt(getCharacterDataFromElement(
						(Element) ((Element) childNodes.item(i)).getElementsByTagName("FirstClass").item(0)));
				
	
				
				//Get coach and  first class prices
				NodeList seatChildren = childNodes.item(i).getChildNodes();
				for(int j = 0; j<seatChildren.getLength();j++)
				{
					if(seatChildren.item(j).getNodeName() == "FirstClass"){
						fcPrice = ((Element) seatChildren.item(j)).getAttributeNode("Price").getValue();
					}
					if(seatChildren.item(j).getNodeName() == "Coach"){
						cPrice = ((Element) seatChildren.item(j)).getAttributeNode("Price").getValue();
					}
				}
			}

		}

		// Create the objects that will hold the flightinfo
		departInfo = new FlightInfo(depCode, depTime);
		arriveInfo = new FlightInfo(arrCode, arrTime);

		firstClass = new Seating(fcPrice, fcRes);
		coach = new Seating(cPrice, coachRes);
				
		// Create Flight using airplane, flighttime, and flightnumber strings
		Flight Flight = new Flight(Airplane, Integer.parseInt(FlightTime), Integer.parseInt(FlightNumber), departInfo, arriveInfo, firstClass, coach);

		/**
		 * Update the Flight's arrival and departure information
		 */
		return Flight;
	}

	/**
	 * Retrieve character data from an element if it exists
	 * 
	 * @param e
	 *            is the DOM Element to retrieve character data from
	 * @return the character data as String [possibly empty String]
	 */
	private static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}
}