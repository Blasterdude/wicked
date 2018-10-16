package flight;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import utils.Utils;

import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class FlightView extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel EconPriceValue;
	private JLabel CoachPriceValue;
	

	public FlightView(Flight inputFlight) {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(new MigLayout("", "[][][][][][][][][][]", "[][][][][][][][][]"));
		
		//JLabel FlightNumber
		JLabel FlightNumber = new JLabel("Flight No." + " " + inputFlight.Number());
		add(FlightNumber, "cell 0 0,alignx left,growy");
		
		JLabel Airplane = new JLabel(inputFlight.Airplane());
		add(Airplane, "cell 5 0");
		
		JLabel MDY_Label = new JLabel(FlightController.getInstance().extractFlightMDY(inputFlight.getmDepartDate()));
		add(MDY_Label, "cell 9 0");
		
		JLabel DepartsLabel = new JLabel("Departs: ");
		add(DepartsLabel, "cell 0 3,alignx left,aligny baseline");
		
		//JLabel DepartCode
		JLabel DepartCode = new JLabel(inputFlight.getmDepartAir());
		add(DepartCode, "cell 1 3");
		
		JLabel DepTimeLabel = new JLabel(FlightController.getInstance().getLocalTime(inputFlight.getmDepartDate(), inputFlight.getmDepartAir()));
		add(DepTimeLabel, "cell 5 3,aligny center");
		
		//JLabel DepartTimeVal		
		JLabel ArrivesLabel = new JLabel("Arrives:");
		add(ArrivesLabel, "cell 0 4");
		
		//JLabel ArriveCode
		JLabel ArriveCode = new JLabel(inputFlight.getmArriveAir());
		add(ArriveCode, "cell 1 4");
		
		JLabel ArrTimeLabel = new JLabel(FlightController.getInstance().getLocalTime(inputFlight.getmArriveDate(),inputFlight.getmArriveAir()));
		add(ArrTimeLabel, "cell 5 4");
		
		//JLabel  CoachPrice
		JLabel CoachPriceLabel = new JLabel("Coach Price:");
		add(CoachPriceLabel, "cell 8 7");
		CoachPriceValue = new JLabel(inputFlight.Coach().mPrice());
		add(CoachPriceValue, "cell 9 7");
		
		//TODO: Make seat numbers update dynamically after reservations are made
		JLabel coachSeatsAvailable = new JLabel("(" + Integer.toString(inputFlight.Coach().mReservations())+ ")");
		coachSeatsAvailable.setVisible(false);
		coachSeatsAvailable.setForeground(Color.LIGHT_GRAY);
		add(coachSeatsAvailable, "cell 7 7,alignx center,aligny baseline");
		
		
		JLabel FC_SeatsAvailable = new JLabel("(" + Integer.toString(inputFlight.FirstClass().mReservations()) +")");
		FC_SeatsAvailable.setVisible(false);
		FC_SeatsAvailable.setForeground(Color.LIGHT_GRAY);
		add(FC_SeatsAvailable, "cell 7 8,growy");
		
		//JLabel ArriveTimeVal		
		JLabel EconPriceLabel = new JLabel("Economy Price: ");
		add(EconPriceLabel, "flowy,cell 8 8");
		EconPriceValue = new JLabel(inputFlight.FirstClass().mPrice());
		add(EconPriceValue, "cell 9 8");
		
		
		initialize();
	}

	private void initialize() {
	}
}
