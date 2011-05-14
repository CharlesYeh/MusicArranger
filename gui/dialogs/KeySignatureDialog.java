package gui.dialogs;

import java.beans.*;

import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JTextField;

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
	
	JTextField _timeSigNumer;
	JTextField _timeSigDenom;
	
	boolean _success;
	
	public KeySignatureDialog(Frame frame) {
		super(frame, true);
		
		// button labels
		Object[] options = {"Create", "Cancel"};
		
		// objects in dialog
		_timeSigNumer = new JTextField();
		_timeSigDenom = new JTextField();
		
		Object[] startArray = {"Time Signature Numerator:", _timeSigNumer, "Time Signature Denominator: ", _timeSigDenom};
		
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
	
	public int getTimeNumer() {
		try {
			return Integer.parseInt(_timeSigNumer.getText());
		}
		catch (Exception e) {
			return DEFAULT_TIMESIG;
		}
	}
	
	public int getTimeDenom() {
		try {
			return Integer.parseInt(_timeSigDenom.getText());
		}
		catch (Exception e) {
			return DEFAULT_TIMESIG;
		}
	}
	
	public void refreshSuccess() {
		_success = false;
	}
	
	public boolean success() {
		return _success;
	}
}