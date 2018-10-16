/**
 * 
 */
package controller;
/*
 * @author krgray
 *
 */

import view.*;

public class BookingSystem {

	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {	
		try {			
			//Open the first window of the application
			SelectAirportPage window = new SelectAirportPage();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
