package gui.dialogs;

import java.beans.*;

import music.Clef;
import music.ClefName;
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

public class ClefDialog extends JDialog implements PropertyChangeListener {
	static final long serialVersionUID = 0;
	
	// default values for invalid input
	
	private JOptionPane _optionPane;
	
	boolean _success;
	JComboBox _clefs;
	JTextField _centerLine;
	
	public ClefDialog(Frame frame) {
		super(frame, true);
		
		// button labels
		Object[] options = {"Create", "Cancel"};
		
		// objects in dialog
		String[] clefs = {"Treble Clef", "Bass Clef", "Alto Clef"};
		_clefs = new JComboBox(clefs);
		_clefs.setSelectedIndex(0);
		
		_centerLine = new JTextField("-2");
		
		Object[] startArray = {"Clef:", _clefs, "Center Line: ", _centerLine};
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
		
		_clefs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					switch (_clefs.getSelectedIndex()) {
					case 0:
						_centerLine.setText("-2");
						break;
					case 1:
						_centerLine.setText("2");
						break;
					case 2:
						_centerLine.setText("0");
						break;
					}
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
	
	public Clef getClef() {
		int clefIndex = _clefs.getSelectedIndex();
		
		switch (clefIndex) {
		case 0:
			// treble
			return new Clef(ClefName.GCLEF, getCenterLine());
		case 1:
			// bass
			return new Clef(ClefName.FCLEF, getCenterLine());
		case 2:
			// alto
			return new Clef(ClefName.CCLEF, getCenterLine());
		}
		
		return null;
	}
	
	private int getCenterLine() {
		try {
			return Integer.parseInt(_centerLine.getText());
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