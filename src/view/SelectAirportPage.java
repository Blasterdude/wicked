package view;

import airport.Airport;
import airport.Airports;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComboBox;
import net.miginfocom.swing.MigLayout;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.jdesktop.swingx.JXDatePicker;

import utils.Operator;
import utils.Utils;

import Trip.Trip;
import Trip.TripView;
import Trip.Trips;
import Trip.TripsController;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;

public class SelectAirportPage {

	private JFrame topLevelFrame;
	boolean tripsAdded = false;
	private Dimension ScreenSize;
	private Dimension minSize;

	private JXDatePicker departPicker;
	private JXDatePicker returnPicker;

	private JComboBox<String> departuresBox;
	private JComboBox<String> destinationBox;

	private JComboBox<String> maxLegsBox;

	ValidatedViewPanel depTabbedVV;
	ValidatedViewPanel retTabbedVV;

	private JCheckBox isTwoWay;

	private JPanel sortPanel;
	private JPanel flowLayout_1;

	/**
	 * Create the application.
	 */
	public SelectAirportPage() {
		initialize();
		topLevelFrame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ScreenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		minSize = new Dimension(ScreenSize.width / 4, ScreenSize.height / 4);

		topLevelFrame = new JFrame();
		topLevelFrame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));

		topLevelFrame.setMinimumSize(minSize);
		topLevelFrame.setSize(new Dimension(ScreenSize.width / 2, ScreenSize.height / 2));

		topLevelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		topLevelFrame.getContentPane().setLayout(new MigLayout("wrap 3", "[][][grow][][][][]", "[][][][][]"));

		JLabel Title = new JLabel("Select airports");
		topLevelFrame.getContentPane().add(Title, "cell 0 0");
		Title.setFont(new Font("Tahoma", Font.PLAIN, 78));

		JButton clearBtn = new JButton("Clear");
		topLevelFrame.getContentPane().add(clearBtn, "cell 4 0,alignx left,aligny center");

		flowLayout_1 = new JPanel();
		topLevelFrame.getContentPane().add(flowLayout_1, "cell 0 2");

		addDepartureAndArrivalPickers(flowLayout_1);

		depTabbedVV = new ValidatedViewPanel();
		topLevelFrame.getContentPane().add(depTabbedVV, "cell 0 4");

		retTabbedVV = new ValidatedViewPanel();
		topLevelFrame.getContentPane().add(retTabbedVV, "cell 0 4");

		// Submit Button
		JButton btnSubmit = new JButton("Submit");
		topLevelFrame.getContentPane().add(btnSubmit, "cell 3 0,alignx left,aligny center");
		setupSubmitButton(btnSubmit);

		// Panels to show off returned list of trips
		JTabbedPane depTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		depTabbedPane.setName("Departure");
		depTabbedPane.setVisible(false);
		depTabbedVV.add(depTabbedPane, "cell 0 0");

		JTabbedPane retTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		retTabbedPane.setName("Return");
		retTabbedVV.add(retTabbedPane, "cell 0 0");
		retTabbedPane.setVisible(false);

		// Add Sort bar tool for dissecting results
		addSortToolbar();
		sortPanel.setVisible(false);

		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetSelectionPage();
			}
		});
	} // end initialize

	private void resetSelectionPage() {
		for (Component c : topLevelFrame.getContentPane().getComponents()) {
			if (c.getClass().equals(ValidatedViewPanel.class)) {
				((ValidatedViewPanel) c).hideError();
			}
		}

		for (Component c : flowLayout_1.getComponents()) {
			if (c.getClass().equals(ValidatedViewPanel.class)) {
				((ValidatedViewPanel) c).hideError();
			}
		}

		for (Component c : depTabbedVV.getComponents()) {
			if (c.getName() != null) {
				if (c.getName().equals("Departure")) {
					((JTabbedPane) c).removeAll();
				}
			}
		}

		for (Component c : retTabbedVV.getComponents()) {
			if (c.getName() != null) {
				if (c.getName().equals("Return")) {
					((JTabbedPane) c).removeAll();
				}
			}
		}

		depTabbedVV.setSize(minSize);
		retTabbedVV.setSize(minSize);
	}// end resetSelectionPage

	/**
	 * Adds the departure and arrival pickers.
	 *
	 * @param flowLayout
	 *            the flow layout Add a row into the GUI that contains the
	 *            selectors for Airport codes, depart date, return date
	 */
	private void addDepartureAndArrivalPickers(JPanel flowLayout) {
		String DepDefault = "(Select Departure Airport)";
		String DestDefault = "(Select Arrival Airport)";

		
		ValidatedViewPanel departuresVV = new ValidatedViewPanel();
		flowLayout.add(departuresVV);
		
		//Add departures selection combobox to departures validated view
		departuresBox = new JComboBox<String>();
		departuresVV.add(departuresBox, "cell 0 0");
		departuresBox.addItem(DepDefault);
		departuresBox.setSelectedItem(DepDefault);

		//Create calendar Validated view for departure calendar.  add it to flowlayout panel
		ValidatedViewPanel depCalendarVV = new ValidatedViewPanel();
		flowLayout.add(depCalendarVV);
		
		//add label datepicker to depValidated view
		JLabel depLabel = new JLabel("Departing On");
		depLabel.setAlignmentX(0.5f);
		depCalendarVV.add(depLabel, "cell 0 0");

		departPicker = new JXDatePicker();
		depCalendarVV.add(departPicker, "cell 1 0");
		departPicker.setDate(Calendar.getInstance().getTime());
		departPicker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
		departPicker.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		//ValidatedView for number of legs in trip
		ValidatedViewPanel maxLegsVV = new ValidatedViewPanel();
		flowLayout.add(maxLegsVV);

		JLabel maxLegsLbl = new JLabel("Allowable Stops");
		maxLegsVV.add(maxLegsLbl, "cell 0 0");

		// Add destination Box
		maxLegsBox = new JComboBox<String>();
		maxLegsVV.add(maxLegsBox, "cell 0 0");
		// DestinationBox.setFont(myFont);
		maxLegsBox.addItem("0");
		maxLegsBox.addItem("1");
		maxLegsBox.addItem("2");
		maxLegsBox.setSelectedItem("2");
		
		
		//Add destination validated View to flowlayout panel
		ValidatedViewPanel destValidatedViewPanel = new ValidatedViewPanel();
		flowLayout.add(destValidatedViewPanel);
		
		//Add destination selection combobox to destination validated view
		destinationBox = new JComboBox<String>();
		destValidatedViewPanel.add(destinationBox, "cell 0 0");
		destinationBox.addItem(DestDefault);
		destinationBox.setSelectedItem(DestDefault);
		
		
		//Checkbox for round trips
		isTwoWay = new JCheckBox("Return Trip?");
		isTwoWay.setVerticalAlignment(SwingConstants.TOP);
		flowLayout.add(isTwoWay, "AlignY.TOP");
		
		//for round trips
		//Create calendar Validated view for destination calendar.  add it to flowlayout panel		
		ValidatedViewPanel returnCalendarVV = new ValidatedViewPanel();
		flowLayout.add(returnCalendarVV);
		returnCalendarVV.setVisible(false);
		
		JLabel retLabel = new JLabel("Returning On");
		retLabel.setAlignmentX(0.5f);
		returnCalendarVV.add(retLabel, "cell 0 0 ");
		
		returnPicker = new JXDatePicker();
		returnCalendarVV.add(returnPicker, "cell 0 0");
		returnPicker.setDate(Calendar.getInstance().getTime());
		returnPicker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
		returnPicker.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		Airports airports = TripsController.getInstance().getAirportsForView();
		// Add airports to combo-boxes
		for (Airport airport : airports) {
			departuresBox.addItem("(" + airport.code() + ")" + airport.name());
			destinationBox.addItem("(" + airport.code() + ")" + airport.name());
		}

		
	//	ValidatedViewPanel returnVV = new ValidatedViewPanel();
	//	flowLayout.add(returnVV);
	//	returnVV.setVisible(false);
		 isTwoWay.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					System.out.println("Checkbox changed to " + isTwoWay.isSelected());
					returnCalendarVV.setVisible(isTwoWay.isSelected());
				}
	        });
	       

	}//End add Departs and Selection pickers

	/**
	 * Adds the trips tab.
	 *
	 * @param tabPane
	 *            - either the arrival or returning panes, where trips will be
	 *            added
	 * @param tabName
	 *            - name of the new tab. Current tabs with same name will be
	 *            replaced by new tab
	 * @param tripArray
	 *            the array of trips to add.
	 */
	void addTripsTab(JTabbedPane tabPane, String tabName, Trips tripArray) {
		tabPane.setVisible(true);
		for (int i = 0; i < tabPane.getTabCount(); i++) {
			if (tabPane.getComponentAt(i).getName().equals(tabName)) {
				tabPane.removeTabAt(i);
			}
		}

		ButtonTabComponent btnTab = new ButtonTabComponent(tabPane, tripArray);
		btnTab.setName(tabName);
		btnTab.setSize(minSize);
		tabPane.add(btnTab);

		JLabel tabHeader = new JLabel();

		if (tabPane.getName().equals("Departure")) {
			tabHeader.setText("Departing");
		} else {
			tabHeader.setText("Returning");
		}
		btnTab.add(tabHeader, "wrap");

		
		if (tripArray.size() <= 0) {
			System.out.println("NO TRIPS");
			JLabel noTrips = new JLabel("No trips returned");
			noTrips.setName(tabName);
			btnTab.add(noTrips);
			
			return;
		}
		
		JScrollPane tripsScrollPane = new JScrollPane();
		btnTab.add(tripsScrollPane);
		
		JPanel returnedTripsPanel = new JPanel();
		returnedTripsPanel.setBorder(null);
		returnedTripsPanel.setLayout(new BoxLayout(returnedTripsPanel, BoxLayout.Y_AXIS));

		tripsScrollPane.add(returnedTripsPanel);
		
			for (Trip t : tripArray) {
				TripView tView = new TripView(t);
				returnedTripsPanel.add(tView);

			}
		

		tripsScrollPane.setViewportView(returnedTripsPanel);

		tabPane.setSelectedIndex(tabPane.getTabCount()-1);

		topLevelFrame.revalidate();
		topLevelFrame.repaint();

	}

	/**
	 * Adds the sort toolbar. which is made up of buttons that allow for sorting
	 * of selected tabs
	 */
	private void addSortToolbar() {
		sortPanel = new JPanel();
		topLevelFrame.getContentPane().add(sortPanel, "cell 1 2");
		sortPanel.setLayout(new BoxLayout(sortPanel, BoxLayout.Y_AXIS));

		JLabel searchTools = new JLabel("Search Tools");
		sortPanel.add(searchTools);

		JButton showAllTrips = new JButton("Show All Trips");
		sortPanel.add(showAllTrips);

		showAllTrips.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTabbedPane myDepTab = (JTabbedPane) depTabbedVV.getComponent(2);
				JTabbedPane myRetTab = (JTabbedPane) retTabbedVV.getComponent(2);

				addTripsTab(myDepTab, "All Trips", Operator.getListOfTrips_departing());

				if (isTwoWay.isSelected()) {
					addTripsTab(myRetTab, "All Trips", Operator.getListOfTrips_returning());
				}
			}

		});
		
		JPanel sortByCoachPricePanel = new JPanel();
		sortPanel.add(sortByCoachPricePanel);
		sortByCoachPricePanel.setLayout(new MigLayout("", "[226px,grow]", "[33px][][grow]"));
		
		JLabel sortCoachPrice = new JLabel("Sort by coach price");
		sortByCoachPricePanel.add(sortCoachPrice, "cell 0 0,alignx left,aligny top");
		
				JButton ascendingCoach = new JButton("Ascending");
				sortByCoachPricePanel.add(ascendingCoach, "flowx,cell 0 1");
				
				JButton descendingCoach = new JButton("Descending");
				sortByCoachPricePanel.add(descendingCoach, "cell 0 1");
				
						ascendingCoach.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								sortByType(depTabbedVV, "Coach Price", "ascending");
								if (isTwoWay.isSelected())
									sortByType(retTabbedVV, "Coach Price", "ascending");
							}
				
						});
						
						descendingCoach.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								sortByType(depTabbedVV, "Coach Price","descending");
								if (isTwoWay.isSelected())
									sortByType(retTabbedVV, "Coach Price","descending");
							}
				
						});


	}

	/**
	 * Sort ascending.
	 *
	 * @param validatedTab
	 *            the container for the tab that will be sorted
	 * @param type
	 *            - the type of sort to run. By coach price, first class price,
	 *            seats available, etc.
	 */
	private void sortByType(ValidatedViewPanel validatedTab, String type, String order) {
		JTabbedPane myTab = (JTabbedPane) validatedTab.getComponent(2);

		String tabName = myTab.getSelectedComponent().getName();
		ButtonTabComponent currentTab = null;
		Trips sortedTrips = new Trips();

		for (Component c : myTab.getComponents()) {
			if (c.getName().equals(tabName)) {
				currentTab = (ButtonTabComponent) c;
			}
		}

		sortedTrips = currentTab.sortCoachPrice(order);

		addTripsTab(myTab, tabName, sortedTrips);

	}

	/**
	 * Creates a list of trips based on number of legs in the trip and adds a
	 * tab for that trip.
	 * 
	 * @param maxLegs
	 *            - the max legs specified by the user
	 */
	private void sortTripsByNumLegs(int maxLegs) {
		JTabbedPane myDepTab = (JTabbedPane) depTabbedVV.getComponent(2);
		JTabbedPane myRetTab = (JTabbedPane) retTabbedVV.getComponent(2);

		// Departing Trips
		if (maxLegs >= 0) {
			addTripsTab(myDepTab, "Direct Trip",
					TripsController.getInstance().getTripsByNumLegs(1, Operator.getListOfTrips_departing()));
		}
		if (maxLegs >= 1) {
			addTripsTab(myDepTab, "One-Stop Trip",
					TripsController.getInstance().getTripsByNumLegs(2, Operator.getListOfTrips_departing()));
		}
		if (maxLegs == 2) {
			addTripsTab(myDepTab, "Two-Stop Trip",
					TripsController.getInstance().getTripsByNumLegs(3, Operator.getListOfTrips_departing()));
		}

		// Returning Trips
		if (isTwoWay.isSelected()) {
			if (maxLegs >= 0) {
				addTripsTab(myRetTab, "Direct Trip",
						TripsController.getInstance().getTripsByNumLegs(1, Operator.getListOfTrips_returning()));
			}
			if (maxLegs >= 1) {
				addTripsTab(myRetTab, "One-Stop Trip",
						TripsController.getInstance().getTripsByNumLegs(2, Operator.getListOfTrips_returning()));
			}
			if (maxLegs == 2) {
				addTripsTab(myRetTab, "Two-Stop Trip",
						TripsController.getInstance().getTripsByNumLegs(3, Operator.getListOfTrips_returning()));
			}
		}
	}

	/**
	 * Sets the up submit button. Includes the actionlistener for the button ->
	 * this calls out to other classes to get flight information
	 */
	private void setupSubmitButton(JButton submit) {
		
		ArrayList<String> errorArray = new ArrayList<String>();

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// retrieve user input information
				String depDate = Utils.convertDateFromUI(departPicker.getDate());
				String retDate;

				if (isTwoWay.isSelected()) {
					retDate = Utils.convertDateFromUI(returnPicker.getDate());
				} else
					retDate = null;

				String departuresAirportCode = ((String) departuresBox.getSelectedItem()).split("[\\(\\)]")[1];
				String destinationAirportCode = ((String) destinationBox.getSelectedItem()).split("[\\(\\)]")[1];

				// TODO: Remove this debug code
				//depDate = "2017_05_10";
				//departuresAirportCode = "BOS";
				//destinationAirportCode = "ATL";
				//retDate = "2017_09_10";
				// *************************

				errorArray.clear();
				resetSelectionPage();

				validateAiportCodes(departuresBox, destinationBox);
				validateDates();
				if (countInvalidFields() != 0) {
					return;
				}

				// Get Trips
				Operator.process(departuresAirportCode, destinationAirportCode, depDate, retDate);

				validateTripsReturned();

				if (countInvalidFields() != 0) {
					return;
				}

				JTabbedPane myDepTab = (JTabbedPane) depTabbedVV.getComponent(2);
				JTabbedPane myRetTab = (JTabbedPane) retTabbedVV.getComponent(2);

				myDepTab.removeAll();
				myRetTab.removeAll();

				sortTripsByNumLegs(Integer.parseInt(maxLegsBox.getSelectedItem().toString()));

				sortPanel.setVisible(true);
			}

			/**
			 * Validate aiport codes from the departures and arrival box in the
			 * GUI.
			 *
			 * @param depBox
			 *            the dep box
			 * @param destBox
			 *            the dest box
			 */
			private void validateAiportCodes(JComboBox<String> depBox, JComboBox<String> destBox) {
				String errorStr;

				String depCode = ((String) departuresBox.getSelectedItem()).split("[\\(\\)]")[1];
				String destCode = ((String) destinationBox.getSelectedItem()).split("[\\(\\)]")[1];

				errorStr = TripsController.getInstance().validateAirportCodes(depCode, destCode);
				errorArray.add(errorStr);
				depTabbedVV.setErrorMessage(errorStr);

				errorStr = TripsController.getInstance().validateAirportCode(depCode, "departure");
				errorArray.add(errorStr);
				((ValidatedViewPanel) departuresBox.getParent()).setErrorMessage(errorStr);

				errorStr = TripsController.getInstance().validateAirportCode(destCode, "arrival");
				errorArray.add(errorStr);
				((ValidatedViewPanel) destinationBox.getParent()).setErrorMessage(errorStr);
			}

			/**
			 * Validate the dates input in the GUI.
			 */
			private void validateDates() {
				String errorStr;

				String depDate = Utils.convertDateFromUI(departPicker.getDate());
				String retDate;

				if (isTwoWay.isSelected()) {
					retDate = Utils.convertDateFromUI(returnPicker.getDate());
				} else
					retDate = null;

				errorStr = TripsController.getInstance().validateDates(depDate, retDate);
				errorArray.add(errorStr);
				((ValidatedViewPanel) returnPicker.getParent())
						.setErrorMessage(TripsController.getInstance().validateDates(depDate, retDate));
			}

			/**
			 * Validate a non-zero list of trips returned from the server
			 */
			private void validateTripsReturned() {
				String errorStr;

				errorStr = TripsController.getInstance().validateTripsExist(Operator.getListOfTrips_departing(),
						"departing");
				depTabbedVV.setErrorMessage(errorStr);
				errorArray.add(errorStr);

				if (isTwoWay.isSelected()) {
					errorStr = TripsController.getInstance().validateTripsExist(Operator.getListOfTrips_returning(),
							"returning");
					retTabbedVV.setErrorMessage(errorStr);
					// errorArray.add(errorStr);
				}

			}

			/**
			 * Count invalid fields.
			 *
			 * @return the number of invalid fields.
			 */
			private int countInvalidFields() {
				int eCt = 0;
				for (String s : errorArray) {
					if (s != "")
						eCt++;
				}

				return eCt;
			}

		});

	}

}
