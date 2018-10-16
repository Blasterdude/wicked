package airplane;
/**
 * This class holds values pertaining to a single Airplane. Class member attributes
 * are the same as defined by the CS509 server API and store values after conversion from
 * XML received from the server to Java primitives. Attributes are accessed via getter and 
 * setter methods.
 * 
 * @author Yi
 * @version 1.0
 * @since 2017/3/26.
 */
public class Airplane {
	/**
	 * Attributes of airplane as shown in the xml document.
	 */
	private String manufacturer;
	private String model;
	private int firstclassSeats = 0;
	private int coachSeats = 0;
	
	public Airplane() {
		
	}
	
	/**
	 * Default constructor
	 * 
	 * @param manufacturer	String as a readable name of the manufacturing company.
	 * @param model		String as a name of the model of the airplane.
	 * @param firstclassSeats	Number of the total available firstclass seat.
	 * @param coachSeats	Number of the total available coach seat.
	 */
	public Airplane(String manufacturer,
					String model,
					int firstclassSeats,
					int coachSeats) {
		this.manufacturer = manufacturer;
		this.model = model;
		this.firstclassSeats = firstclassSeats;
		this.coachSeats = coachSeats;
	}
	
	/**
	 * Transfer the airplane object into a readable string with all the information displayed.
	 * @return	The string with information to display.
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(manufacturer).append(", ");
		sb.append(model).append(", ");
		sb.append(firstclassSeats).append(", ");
		sb.append(coachSeats);

		return sb.toString();
	}

	/**
	 * Check if the airplane object has a valid parameter set.
	 * 
	 * manufacture is a string cannot be null nor empty
	 * model is a string cannot be null nor empty
	 * coachSeat must be an integer larger than 0
	 * firstclassSeat must be an integer larger than 0
	 * 
	 * @return true if airplane is valid and false if not.
	 */
	public boolean isValid() {
		if(this.getManufacturer() == null || this.getManufacturer().isEmpty() ||
			this.getModel() == null || this.getModel().isEmpty() ||
			this.getCoachSeats() <= 0||
			this.getFirstclassSeats() <= 0) {
			return false;
		}
		return true;
	}
	
	// Getters and setters
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getFirstclassSeats() {
		return firstclassSeats;
	}

	public void setFirstclassSeats(int firstclassSeats) {
		this.firstclassSeats = firstclassSeats;
	}

	public int getCoachSeats() {
		return coachSeats;
	}

	public void setCoachSeats(int coachSeats) {
		this.coachSeats = coachSeats;
	}
}
