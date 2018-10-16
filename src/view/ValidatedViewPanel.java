package view;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Color;



public class ValidatedViewPanel extends JPanel {
	JLabel asterisk;
	JLabel errorMsg;
	
	public ValidatedViewPanel() {
		setBorder(null);
		setLayout(new MigLayout("", "[][]", "[][]"));
		
		asterisk = new JLabel("*");
		asterisk.setForeground(Color.RED);
		asterisk.setVisible(false);
		add(asterisk, "cell 1 0,aligny top");
		
		errorMsg = new JLabel("Error Message");
		errorMsg.setForeground(Color.RED);
		errorMsg.setVisible(false);
		add(errorMsg, "cell 0 1");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	void setErrorMessage(String message)
	{
		if(message == ""){
			hideError();
		}
		else{
		asterisk.setVisible(true);
		errorMsg.setVisible(true);
		errorMsg.setText("Error: " + message);
		}
	}

	public void hideError() {
		asterisk.setVisible(false);
		errorMsg.setVisible(false);		
	}
	

}
