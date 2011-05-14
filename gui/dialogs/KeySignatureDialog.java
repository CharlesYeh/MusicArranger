package gui.dialogs;

import java.beans.*;

import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ComponentAdapter;

public class KeySignatureDialog extends JDialog implements PropertyChangeListener {
	static final long serialVersionUID = 0;
	
	// default values for invalid input
	final static int DEFAULT_TIMESIG = 4;
	
	private JOptionPane _optionPane;
	
	JComboBox _isMajor;
	JTextField _txtAccidentals;
	
	boolean _success;
	
	public KeySignatureDialog(Frame frame) {
		super(frame, true);
		
		// button labels
		Object[] options = {"Create", "Cancel"};
		
		// objects in dialog
		String[] qualities = {"Major", "Minor"};
		_isMajor = new JComboBox(qualities);
		
		_txtAccidentals = new JTextField();
		
		Object[] startArray = {"Accidentals:", _txtAccidentals, "Key Signature Quality: ", _isMajor};
		
		_optionPane = new JOptionPane(startArray, JOptionPane.DEFAULT_OPTION,
												JOptionPane.YES_NO_OPTION,
												null,
												options,
												options[0]);
		
		setContentPane(_optionPane);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.pack();
		
		// when X button is pressed
		addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					_optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
				}
			});
		
		_optionPane.addPropertyChangeListener(this);
	}
	
	public void propertyChange(PropertyChangeEvent e) {
		if (!e.getPropertyName().equals("value") || _optionPane.getValue().equals(-1))
			return;
		
		_success = _optionPane.getValue().equals("Create");
		_optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
		setVisible(false);
	}
	
	public boolean getIsMajor() {
		return _isMajor.getSelectedItem().equals("Major");
	}
	
	public int getNumAccidentals() {
		try {
			return Integer.parseInt(_txtAccidentals.getText());
		}
		catch (Exception e) {
			return 0;
		}
	}
	
	public void refreshSuccess() {
		_success = false;
	}
	
	public boolean success() {
		return _success;
	}
}