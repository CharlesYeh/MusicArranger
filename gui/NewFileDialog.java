package gui;

import music.Clef;

import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.util.List;
import java.util.ArrayList;

import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ComponentAdapter;

public class NewFileDialog extends JDialog {
	static final long serialVersionUID = 0;
	
	private JOptionPane _optionPane;
	
	JTextField _txtAccidentals;
	JTextField _txtNumClefs;
	JComboBox _comboClefs;
	JTextField _txtMeasureNum;
	
	public NewFileDialog(Frame frame) {
		super(frame, true);
		
		Object[] options = {"Generate", "Cancel"};
		
		JTextField _txtAccidentals = new JTextField();
		
		String[] clefs = {"Treble Clef", "Bass Clef", "Alto Clef"};
		_txtNumClefs = new JTextField();
		_comboClefs = new JComboBox(clefs);
		_comboClefs.setSelectedIndex(1);
		
		_txtMeasureNum = new JTextField();
		_txtMeasureNum.setText("30");
		
		Object[] items = {"Number of measures:", _txtMeasureNum, "Number of accidentals:", _txtAccidentals,
								"Number of staffs:",_txtNumClefs, _comboClefs};
		
		_optionPane = new JOptionPane(items, JOptionPane.DEFAULT_OPTION,
												JOptionPane.YES_NO_OPTION,
												null,
												options,
												options[0]);
		
		setContentPane(_optionPane);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					_optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
				}
			});
		
		addComponentListener(new ComponentAdapter() {
				public void componentShown(ComponentEvent ce) {
					// focus textfield?
				}
			});
		
		_txtNumClefs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					// change number of combo boxes
					
				}
			});
	}
	
	public int getNumAccidentals() {
		return Integer.parseInt(_txtAccidentals.getText());
	}
	
	public int getNumMeasures() {
		return Integer.parseInt(_txtMeasureNum.getText());
	}
	
	public List<Clef> getClefs() {
		return null;
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("HI");
	}
}