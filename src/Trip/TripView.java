package Trip;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.swing.JScrollPane;

import flight.Flight;
import flight.FlightView;
import flight.Flights;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import utils.Operator;

import javax.swing.border.EtchedBorder;

import com.sun.prism.paint.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TripView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Trip myTrip;
	private JLabel arrivesVal;
	private JLabel departsVal;

	public TripView(Trip t) {
		setSize(new Dimension(1000,1000));
		
		setBorder(new LineBorder(new java.awt.Color(0, 0, 0)));
		myTrip = t;
		
		setLayout(new MigLayout("wrap 5", "[grow][][][][]", "[][grow][grow][grow][][]"));
	
		addPriceToTripView();

		addRelevantTripInfo();
		
		JPanel newPane = new JPanel(new FlowLayout());
		add(newPane, "cell 0 10");
		
		newPane.setBorder(new LineBorder(new java.awt.Color(0, 255, 0)));
		
		  if (!t.Flights().isEmpty()) { // Add flights to current page if
			  addFlightsToTrip(t.Flights(), newPane);
		  }
	}

	private void addRelevantTripInfo() {
		
		JButton reserveButton = new JButton("Reserve Coach");
		reserveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Operator.reserveTrip(myTrip.Flights(), "Coach");
			}
		});
		add(reserveButton, "cell 1 0");
		JPanel DurationFlowPanel = new JPanel();
		add(DurationFlowPanel, "cell 0 1,grow");
		
		JLabel Length = new JLabel("Length of Trip:");
		DurationFlowPanel.add(Length);
		
		JLabel tripLengthVal = new JLabel(Integer.toString(myTrip.getTravelTime()));
		DurationFlowPanel.add(tripLengthVal);
		
		JButton reserveFC = new JButton("Reserve First Class");
		reserveFC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operator.reserveTrip(myTrip.Flights(), "FirstClass");
			}
		});
		add(reserveFC, "cell 1 1");
		
		JPanel DepartsFlowPanel = new JPanel();
		add(DepartsFlowPanel, "cell 0 2,grow");
		
		JLabel Departslbl = new JLabel("Departs: ");
		DepartsFlowPanel.add(Departslbl);
		
		departsVal = new JLabel();
		DepartsFlowPanel.add(departsVal);
		
		JLabel sep = new JLabel(", ");
		DepartsFlowPanel.add(sep);
		
		JLabel Arriveslbl = new JLabel("Arrives");
		DepartsFlowPanel.add(Arriveslbl);
		
		arrivesVal = new JLabel();
		DepartsFlowPanel.add(arrivesVal);
				
	}

	private void addPriceToTripView() {
		String fmt = "$#.##";
    	DecimalFormat df = new DecimalFormat(fmt);
    	df.setRoundingMode(RoundingMode.DOWN);
		
		JPanel TripFlowPanel = new JPanel();
		add(TripFlowPanel, "cell 0 0,grow");
		
		JLabel Prices = new JLabel("Prices: ");
		TripFlowPanel.add(Prices);
    	
		JPanel CoachPricePanel = new JPanel();
		TripFlowPanel.add(CoachPricePanel);
		
		JLabel CoachPricelbl = new JLabel("Coach: ");
		CoachPricePanel.add(CoachPricelbl);
		
		JLabel coachPriceValue = new JLabel(df.format(myTrip.getPriceCoach()));
		CoachPricePanel.add(coachPriceValue);
		
		JPanel FClassPricePanel = new JPanel();
		TripFlowPanel.add(FClassPricePanel);
		
		JLabel FClassPricelbl = new JLabel("First Class: ");
		FClassPricePanel.add(FClassPricelbl);
		
		JLabel FClassValue = new JLabel(df.format(myTrip.getPriceFirstClass()));
		FClassPricePanel.add(FClassValue);		
		
	}

	void addFlightsToTrip(Flights flights, JPanel pane) {

		for (Flight flight : flights) {
			FlightView flightview = new FlightView(flight);
			pane.add(flightview);
		}
	}

	public void setDepartsAndArrives(String departs, String arrives) {
		departsVal.setText(departs);
		arrivesVal.setText(arrives);
	}
	
}
