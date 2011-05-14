package gui.dialogs;

import music.Clef;
import music.ClefName;
import java.beans.*;

import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ComponentAdapter;

public class NewFileDialog extends JDialog implements PropertyChangeListener {
	static final long serialVersionUID = 0;
	
	// default values for invalid input
	final static int DEFAULT_TIMESIG = 4;
	final static int DEFAULT_CLEFS = 2;
	final static int DEFAULT_ACCIDENTALS = 0;
	final static int DEFAULT_MEASURES = 30;
	
	final static int MAX_CLEFS = 8;
	
	private JOptionPane _optionPane;
	
	JTextField _timeSigNumer;
	JTextField _timeSigDenom;
	
	JComboBox _isMajor;
	
	JTextField _txtAccidentals;
	JComboBox _comboNumClefs;
	List<JComboBox> _comboClefs;
	JTextField _txtMeasureNum;
	
	boolean _createFile;
	
	public NewFileDialog(Frame frame) {
		super(frame, true);
		
		// button labels
		Object[] options = {"Create", "Cancel"};
		
		// objects in dialog
		_timeSigNumer = new JTextField();
		_timeSigDenom = new JTextField();
		
		String[] qualities = {"Major", "Minor"};
		_isMajor = new JComboBox(qualities);
		
		_txtAccidentals = new JTextField();
		
		Integer[] numClefs = {1, 2, 3, 4, 5, 6, 7};
		_comboNumClefs = new JComboBox(numClefs);
		_comboNumClefs.setSelectedIndex(DEFAULT_CLEFS - 1);
		
		String[] clefs = {"Treble Clef", "Bass Clef", "Alto Clef"};
		_comboClefs = new ArrayList<JComboBox>();
		for (int i = 0; i < MAX_CLEFS; i++) {
			JComboBox b = new JComboBox(clefs);
			b.setSelectedIndex(0);
			_comboClefs.add(b);
		}
		
		_comboClefs.get(1).setSelectedIndex(1);
		
		_txtMeasureNum = new JTextField();
		_txtMeasureNum.setText("30");
		
		_createFile = false;
		
		_optionPane = new JOptionPane(null, JOptionPane.DEFAULT_OPTION,
												JOptionPane.YES_NO_OPTION,
												null,
												options,
												options[0]);
		
		setNumComboBoxes(DEFAULT_CLEFS);
		setContentPane(_optionPane);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		// when X button is pressed
		addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					_optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
				}
			});
		
		_comboNumClefs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					// change number of combo boxes
					setNumComboBoxes(Integer.parseInt("" + _comboNumClefs.getSelectedItem()));
				}
			});
			
		_optionPane.addPropertyChangeListener(this);
	}
	
	public void propertyChange(PropertyChangeEvent e) {
		if (!e.getPropertyName().equals("value") || _optionPane.getValue().equals(-1))
			return;
		
		_createFile = _optionPane.getValue().equals("Create");
		_optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
		setVisible(false);
	}
	
	public void setNumComboBoxes(int clefs) {
		Object[] startArray = {"Time Signature Numerator:", _timeSigNumer, "Time Signature Denominator: ", _timeSigDenom,
										"Scale quality:", _isMajor, "Number of measures:", _txtMeasureNum,
										"Number of accidentals:", _txtAccidentals, "Number of staffs:", _comboNumClefs};
		
		List<Object> items = new ArrayList<Object>();
		for (int i = 0; i < startArray.length; i++) {
			items.add(startArray[i]);
		}
		
		for (int i = 0; i < clefs; i++) {
			items.add(_comboClefs.get(i));
		}
		
		Object[] itemsArray = items.toArray();
		_optionPane.setMessage(itemsArray);
		this.pack();
	}
	
	public int getNumAccidentals() {
		try {
			return Integer.parseInt(_txtAccidentals.getText());
		}
		catch (Exception e) {
			return DEFAULT_ACCIDENTALS;
		}
	}
	
	public int getNumMeasures() {
		try {
			return Integer.parseInt(_txtMeasureNum.getText());
		}
		catch (Exception e) {
			return DEFAULT_MEASURES;
		}
		
	}
	
	public List<Clef> getClefs() {
		List<Clef> clefs = new ArrayList<Clef>();
		
		for (int i = 0; i < _comboNumClefs.getSelectedIndex() + 1; i++) {
			int clefIndex = _comboClefs.get(i).getSelectedIndex();
			
			switch (clefIndex) {
			case 0:
				// treble
				clefs.add(new Clef(ClefName.GCLEF, -2));
				break;
			case 1:
				// bass
				clefs.add(new Clef(ClefName.FCLEF, 2));
				break;
			case 2:
				// alto
				clefs.add(new Clef(ClefName.CCLEF, 0));
				break;
			}
		}
		
		return clefs;
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
	
	public boolean getIsMajor() {
		return _isMajor.getSelectedItem().equals("Major");
	}
	
	public void refreshSuccess() {
		_createFile = false;
	}
	
	public boolean success() {
		return _createFile;
	}
}