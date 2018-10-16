package flight;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FlightInfo {
	private String mCode;
	
	private Date mDate;
	
	private String mTime;

	SimpleDateFormat df = new SimpleDateFormat("yyyy MMM dd HH:mm Z");

	public FlightInfo() {
	}

	public FlightInfo(String code, String dateAndTime) {
		if (!isValidAirport(code))
			throw new IllegalArgumentException("Error :" + code + " is not a valid airport");
		if (!isValidDate(dateAndTime))
			throw new IllegalArgumentException("Date is not in the correct format");
		
		mCode = code;
		try{
			mDate = new Date(df.parse(dateAndTime).getTime());
		} catch(Exception e)
		{
			
		}
		
	}
	public boolean isValid(FlightInfo flightInfo) {
		return (isValidDate(flightInfo.mTime()) && isValidAirport(flightInfo.mCode));
	}

	private boolean isValidDate(String dateAndTime) {
		// mDate = dateAndTime;
		// example: 2017 May 05 17:22 GMT
		try {
			mTime = dateAndTime;
			mDate = new Date(df.parse(dateAndTime).getTime());
		} catch (ParseException e) {
			throw new IllegalArgumentException("Date is not in the proper format");
		}
		return true;
	}

	private boolean isValidAirport(String code) {
		// TODO Check that code is a valid airport code in database
		return true;
	}

	public String toString() {
		
		return mTime + " " + mCode;
	}

	public String mCode() {
		return mCode;
	}

	public void mCode(String code) {
		mCode = code;
	}

	public String mTime() {
		return mTime;
	}

	public void mTime(String time) {
		mTime = time;
	}
	
	public Date mDate() {
		return mDate;
	}

	public void mDate(Date mDate) {
		this.mDate = mDate;
	}

}
