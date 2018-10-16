package utils;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.corba.se.impl.orbutil.graph.Node;
import org.w3c.dom.Element;

import dao.DaoAirport;

public class Utils {

	public static String convertDateFromUI(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy_MM_dd");
		return formater.format(date);
	}
	
	/**
	 * Compare dates.
	 *
	 * @return true, if depart date is ealier than arrival date
	 */
	public static boolean compareDates(String DepartDate, String ArrivalDate){
		String[] depArr = DepartDate.split("_");
		String[] retArr = ArrivalDate.split("_");

		if(Integer.parseInt(depArr[0].toString()) <= Integer.parseInt(retArr[0].toString()) &&
				Integer.parseInt(depArr[1].toString()) <= Integer.parseInt(retArr[1].toString()) &&
						Integer.parseInt(depArr[2].toString()) <= Integer.parseInt(retArr[2].toString()))
		{
			return  true;
		}


				

		return false;
		
	}
	
	/**
	 * Price to double.
	 *
	 * @param price the price from the databse
	 * @return the price as a double, instead of a string - removes '$' and ',"
	 */
	public static double priceToDouble(String price)
	{
		Double val;
		String parsablePrice = "";
		String[] sb;


		price.substring(1); //remove the $
		
		//System.out.println(price);
		parsablePrice = price.substring(1);

		if(price.contains(","))
		{
			sb = parsablePrice.split(",");
			parsablePrice = sb[0] + sb[1];
		}
		//System.out.println(parsablePrice);
		
		val = Double.parseDouble(parsablePrice);
		return val;
	}
	
	/**
	 * Builds a DOM tree form an XML string
	 * 
	 * Parses the XML file and returns a DOM tree that can be processed
	 * 
	 * @param xmlString XML String containing set of objects
	 * @return DOM tree from parsed XML or null if exception is caught
	 */
	public static Document buildDomDoc (String xmlString) {
		/**
		 * load the xml string into a DOM document and return the Document
		 */
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(xmlString));
			
			return docBuilder.parse(inputSource);
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		catch (SAXException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date getUTC (Date giv, String code){
		
		
		try{
			//DaoAirport.addAll(contents);
			
			String contents = new String(Files.readAllBytes(Paths.get("src/sampleXML/airportoff.xml")));
			
			Document xmlDoc = Utils.buildDomDoc(contents);
			
			NodeList myAirport = xmlDoc.getElementsByTagName("Airport");
			
			//Element airportElement = myAirport.item(index)
			
			for (int i=0; i<myAirport.getLength(); i++){
				Element e =  (Element) myAirport.item(i);
				
				System.out.println("Code = " + e.getAttribute("Code"));
				if(e.getAttribute("Code").equals(code))
				{
					return convertUTC(giv, e.getAttribute("Offset"));
				}
			}
			}
			catch(Exception  e){
				e.toString();
			}
		System.out.println("No airport found by that name");
		return null;
	}

	private static Date convertUTC(Date giv, String offset) {
		System.out.println("offset = " + offset);
		return null;
	}
}
