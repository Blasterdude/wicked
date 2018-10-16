package dao;

import java.io.IOException;
import java.io.StringReader;

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

import airplane.Airplanes;
import utils.Utils;
import airplane.Airplane;
/** 
 * @author Yi
 * @version 1.0
 * @since 2017/3/26.
 */
public class DaoAirplane {
	/**
	 * Builds collection of airports from airplanes described in XML
	 *  
	 * Parses an XML string to read each of the airplanes and adds each valid airplane 
	 * to the collection. The method uses Java DOM (Document Object Model) to convert
	 * from XML to Java primitives.
	 * @param xmlAirplanes	String in xml format received from database serve containing 
	 * 						all the airplanes.
	 * @return	Collections of airplanes. Can be empty.
	 * @throws NullPointerException included to keep signature consistent with other addAll methods
	 */
	public static Airplanes addAll (String xmlAirplanes) throws NullPointerException {
		Airplanes airplanes = new Airplanes();
		
		// Load the XML string into a DOM tree for ease of processing
		// then iterate over all nodes adding each airport to our collection
		Document docAirplanes = Utils.buildDomDoc (xmlAirplanes);
		NodeList nodesAirplanes = docAirplanes.getElementsByTagName("Airplane");
		
		for (int i = 0; i < nodesAirplanes.getLength(); i++) {
			Element elementAirport = (Element) nodesAirplanes.item(i);
			Airplane airplane = buildAirplane (elementAirport);
			
			if (airplane.isValid()) {
				airplanes.add(airplane);
			}
		}
		
		return airplanes;
	}

	/**
	 * Creates an Airport object from a DOM node
	 * 
	 * Processes a DOM Node that describes an Airport and creates an Airport object from the information
	 * @param nodeAirport is a DOM Node describing an Airport
	 * @return Airport object created from the DOM Node representation of the Airport
	 * 
	 * @pre nodeAirport is of format specified by CS509 server API
	 */
	static private Airplane buildAirplane (Node nodeAirplane) {
		/**
		 * Instantiate an empty Airport object
		 */
		Airplane airplane = new Airplane();

		String manufacturer;
		String model;
		int firstclassSeats;
		int coachSeats;
		
		// The airport element has attributes of Name and 3 character airport code
		Element elementAirplane = (Element) nodeAirplane;
		manufacturer = elementAirplane.getAttributeNode("Manufacturer").getValue();
		model = elementAirplane.getAttributeNode("Model").getValue();
		
		// The latitude and longitude are child elements
		Element elementSeats;
		elementSeats = (Element)elementAirplane.getElementsByTagName("FirstClassSeats").item(0);
		firstclassSeats = Integer.parseInt(getCharacterDataFromElement(elementSeats));
		
		elementSeats = (Element)elementAirplane.getElementsByTagName("CoachSeats").item(0);
		coachSeats = Integer.parseInt(getCharacterDataFromElement(elementSeats));

		/**
		 * Update the Airport object with values from XML node
		 */
		airplane.setManufacturer(manufacturer);
		airplane.setModel(model);
		airplane.setFirstclassSeats(firstclassSeats);
		airplane.setCoachSeats(coachSeats);
		
		return airplane;
	}
	
	/**
	 * Retrieve character data from an element if it exists
	 * 
	 * @param e is the DOM Element to retrieve character data from
	 * @return the character data as String [possibly empty String]
	 */
	private static String getCharacterDataFromElement (Element e) {
		Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	        CharacterData cd = (CharacterData) child;
	        return cd.getData();
	      }
	      return "";
	}
}
