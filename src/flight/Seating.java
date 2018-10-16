package flight;

public class Seating {
	private String mPrice;
	private int mReservations;
	
	public Seating(String cPrice, int res) {
		mPrice = cPrice;
		mReservations = res;
	}

	public String mPrice(){
		return mPrice;
	}
	
	public int mReservations()
	{
		return mReservations;
	}
}
