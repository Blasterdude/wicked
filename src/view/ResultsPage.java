package view;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import flight.Flight;
import flight.FlightView;
import flight.Flights;

public class ResultsPage {
	private JFrame frame;
	
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    final static int	 flightStartPosX = 0;
    final static int	 flightStartPosY = 1;		
	
	public ResultsPage(Flights flights) {
		initialize(flights);
	}

	private void addComponentsToPane(Container pane){
		
	}
	private void addFlightsToPane(Container pane, Flights flights){
		pane.setLayout(new GridBagLayout());
		if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }
		GridBagConstraints c = new GridBagConstraints();
		if (shouldFill) {
		//natural height, maximum width
		c.fill = GridBagConstraints.HORIZONTAL;
		}
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = flightStartPosX;
		c.gridy = flightStartPosY;
		
		for(Flight flight: flights){
			System.out.println(flight.toString());
			
			FlightView flightview = new FlightView(flight);
			pane.add(flightview, c);
			c.gridy ++;
		}


	}
	
	private void initialize(Flights flights) {
		frame = new JFrame("Result Page");
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 32));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		//add buttons
		addComponentsToPane(panel);
		//add Flights
		addFlightsToPane(panel, flights);
		
		JScrollPane jsp = new JScrollPane(panel);
		frame.add(jsp);
		
		//Display the window
		frame.pack();
		frame.setVisible(true);
		
	}

}
