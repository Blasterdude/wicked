package flight;

import java.util.ArrayList;

/**
 * This class aggregates a number of Flights. The aggregate is implemented as an ArrayList.
 * Flights can be added to the aggregate via XML strings in the format returned form the 
 * CS509 server, or as Flight objects using the ArrayList interface. Objects can 
 * be removed from the collection using the ArrayList interface.
 * 
 * @author Yi
 * @version 1.0
 * @since 2017/3/26.
 */
public class Flights extends ArrayList<Flight>{
	private static final long serialVersionUID = 1L;

	@Override 
	public boolean add(Flight e) {
		if (!(this.contains(e))) {
			return super.add((Flight)e);
		}
		return true;
	}
}
