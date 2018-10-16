package flight;

import java.sql.Date;

public class Flight {
	private String mAirplane;
	private int mFlightTime;
	private int mNumber;

	// Flight info contains airport code, date, and time.
	private FlightInfo mDepartInfo;
	private FlightInfo mArriveInfo;

	private Seating mFirstClass;
	private Seating mCoach;

	/**
	 * Default Constructor
	 */
	public Flight() {
		mAirplane = "";
		mFlightTime = 0;
		mNumber = 0;

		mDepartInfo = new FlightInfo();
		mArriveInfo = new FlightInfo();

		mFirstClass = new Seating("",-1);
		mCoach = new Seating("",-1);
	}

	/**
	 * @param airplane
	 * @param fTime
	 * @param num
	 * @param departure
	 * @param arrival
	 * @param fClass
	 * @param coach
	 */
	public Flight(String airplane, int fTime, int num, FlightInfo departure, FlightInfo arrival, Seating fClass,
			Seating coach) {
		if (!isValidAirplane(airplane))
			throw new IllegalArgumentException("Error :" + airplane + " is not a valid airplane");	
		if (!isValidAirport(departure))
			throw new IllegalArgumentException(departure.toString());
		if (!isValidAirport(arrival))
			throw new IllegalArgumentException(arrival.toString());
		if(!isValidSeats(fClass))
			throw new IllegalArgumentException(fClass.toString());
		if(!isValidSeats(coach))
			throw new IllegalArgumentException(fClass.toString());
		
		mAirplane = airplane;
		mFlightTime = fTime;
		mNumber = num;

		mDepartInfo = departure;
		mArriveInfo = arrival;

		mFirstClass = fClass;
		mCoach = coach;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();

		//print out a string informat Airplane, DepCode-ArrAir from DepAir on Date
		sb.append(mAirplane).append(", ");
		sb.append(mArriveInfo.mCode()+"-" + mNumber + " from " + mDepartInfo.mCode());
		sb.append(" on " + mDepartInfo.mTime());

		return sb.toString();
	}

	public String Airplane() {
		return mAirplane;
	}

	public boolean isValid() {
		// TODO Check for flight validity

		return true;
	}

	private boolean isValidAirplane(String airplane) {
		if(airplane.matches("A310|A320|A330|A340|A380|717|737|747|757|767|777|")) //TODO: Get airplane types dynamically from server	
			return true;
		else
			return false;
	}
	
	private boolean isValidAirport(FlightInfo info) {
		return info.isValid(info);
	}
	
	private boolean isValidSeats(Seating s) {
		if(s.mPrice()!=null && s.mReservations() != -1)
			return true;
		return false;
	}

	public int FlightTime() {
		return mFlightTime;
	}

	public int Number() {
		return mNumber;
	}

	public String getmDepartAir() {
		return mDepartInfo.mCode();
	}

	public String getmDepartDateAndTimeAsString() {
		return mDepartInfo.mTime();
	}
	
	public Date getmDepartDate()
	{
		return mDepartInfo.mDate();
	}

	public String getmArriveAir() {
		return mArriveInfo.mCode();
	}

	public String getmArriveDateAndTimeAsString() {
		return mArriveInfo.mTime();
	}

	public Date getmArriveDate()
	{
		return mArriveInfo.mDate();
	}
	
	public Seating Coach() {
		return mCoach;
	}

	public Seating FirstClass() {
		return mFirstClass;
	}

	public String mNumberAsString() {
		return Integer.toString(mNumber);
	}

}
