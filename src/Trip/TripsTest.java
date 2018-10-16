package Trip;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import utils.Operator;

public class TripsTest {
	Trips trips;
	
	@Before
	public void initialize() {
		Trips[] tripsArray = Operator.process("BOS", 
										"SFO", 
										"2017_05_12", 
										"2017_05_13");
		trips = tripsArray[0];
	}
	
	
	@Test
	public void SortByFirstClassPriceTest() {
		trips.SortByFirstClassPrice();
		double pre = 0;
		for(Trip tp : trips) {
			System.out.println(tp.getPriceFirstClass());
			if(tp.getPriceFirstClass() < pre) {
				fail("Sort by firstclass price error");
			}
			pre = tp.getPriceFirstClass();
		}
	}
	
	@Test
	public void SortByTravelTimeTest() {
		trips.SortByTravelTime();
		int pre = 0;
		for(Trip tp : trips) {
			System.out.println(tp.getTravelTime());
			if(tp.getTravelTime() < pre) {
				fail("Sort by travel time error");
			}
			pre = tp.getTravelTime();
		}
	}

}
