package Trip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This class is a collection of Trip class as an ArrayList. Trip can be added to the class
 * via the method append.
 * 
 * Three sort methods are provided within the class. Each of them will sort the list in the
 * order of priceCoach, priceFirstClass and travelTime of the trip.
 * 
 * @author Yi 
 * @version 1.0
 * @since 2017/3/26.
 */
public class Trips extends ArrayList<Trip> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Trip object can be add to the ArrayList via append. Null trip object is not appropriate
	 * for the solution and cannot be add with this method.
	 * @param trip
	 * @return	true if appended or false if not.
	 */
	public boolean append(Trip trip) {
        if(trip == null) {
            return false;
        }
        this.add(trip);
        return true;
    }
	
	public void SortByCoachPrice(String order) {
		Collections.sort(this, new PriceCoachComparator());
		
		if(order == "descending")
			Collections.reverse(this);
		
	}
	
	public void SortByFirstClassPrice() {
		Collections.sort(this, new PriceFirstClassComparator());
	}
	
	public void SortByTravelTime() {
		Collections.sort(this, new TravelTimeComparator());
	}
	
	static class PriceCoachComparator implements Comparator<Trip> {
		@Override
		public int compare(Trip o1, Trip o2) {
			return o1.getPriceCoach() > o2.getPriceCoach()?1:-1;
		}	
	}
	
	static class PriceFirstClassComparator implements Comparator<Trip> {

		@Override
		public int compare(Trip o1, Trip o2) {
			return o1.getPriceFirstClass() > o2.getPriceFirstClass()?1:-1;
		}
		
	}
	
	static class TravelTimeComparator implements Comparator<Trip> {

		@Override
		public int compare(Trip o1, Trip o2) {
			return o1.getTravelTime() > o2.getTravelTime()?1:-1;
		}
		
	}
}
