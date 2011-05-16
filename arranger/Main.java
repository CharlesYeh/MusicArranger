package arranger;

import gui.MainPanel;
import gui.dialogs.NewFileDialog;

import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import logic.LogicManager;
import logic.MidiAPI;
import logic.Editor;
import music.Clef;
import music.ClefName;
import music.Piece;
import instructions.*;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/*
 * Main handles delegations of tasks between
 */
public class Main extends JFrame implements InstructionListener, KeyListener {

	MidiAPI _api;
	MainPanel _mainPanel;

	Editor _editor;
	LogicManager _logicManager;

	Piece _piece;
	
	//dialogs
	NewFileDialog _newFileDialog;

	public Main(){
		super("Music Arranger");
		_piece = new tests.NewPiece();
		
		//_piece = new tests.LongMelodyPiece();
		_logicManager = new LogicManager(_piece);
		_logicManager.addInstructionListener(this);
		_editor = _logicManager.getEditor();
		
		ArrangerConstants.WINDOW_WIDTH = 1100;
		ArrangerConstants.WINDOW_HEIGHT = 700;
		
		_mainPanel = new MainPanel(_piece, this);
		_mainPanel.addInstructionListener(this);
		this.add(_mainPanel);
		
		addMenuBar();
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(ArrangerConstants.WINDOW_WIDTH, ArrangerConstants.WINDOW_HEIGHT);
		this.setVisible(true);
		
		addKeyListener(this);
	}

	public void addMenuBar(){
		JMenuBar menuBar = new JMenuBar();

		//----------------FILE----------------
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		
		final JFrame frame = this;
		_newFileDialog = new NewFileDialog(frame);
		
		// new file
		//Instruction instrNew = new FileInstruction();
		JMenuItem menuItemNew = new JMenuItem("New");
		menuItemNew.setMnemonic(KeyEvent.VK_N);
		menuItemNew.setToolTipText("New song");
		menuItemNew.addActionListener(
			new ActionListener() {
				
				public void actionPerformed(ActionEvent event) {
					// prompt for new song data
					//_newFilerDialog.setLocationRelativeTo();
					_newFileDialog.refreshSuccess();
					_newFileDialog.pack();
					_newFileDialog.setVisible(true);
					
					if (!_newFileDialog.success())
						return;
					
					InstructionBlock myInstr = new InstructionBlock(this, new FileInstructionNew(_newFileDialog.getClefs(),
																					_newFileDialog.getNumMeasures(), _newFileDialog.getTimeNumer(),
																					_newFileDialog.getTimeDenom(), _newFileDialog.getNumAccidentals(),
																					_newFileDialog.getIsMajor()));
					
					receiveInstruction(myInstr);
				}
			});

		JMenuItem menuItemOpen = new JMenuItem("Open");
		menuItemOpen.setMnemonic(KeyEvent.VK_O);
		menuItemOpen.setToolTipText("Open XML file");
		menuItemOpen.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					String wd = System.getProperty("user.dir");
					JFileChooser chooser = new JFileChooser(wd);
					
					int returnVal = chooser.showOpenDialog(frame);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						InstructionBlock myInstr = new InstructionBlock(this, 
										new FileInstructionIO(FileInstructionType.OPEN, 
										chooser.getSelectedFile().getAbsolutePath()));
						receiveInstruction(myInstr);
					}
				}
			});
		
		JMenuItem menuItemSave = new JMenuItem("Save");
		menuItemSave.setMnemonic(KeyEvent.VK_S);
		menuItemSave.setToolTipText("Save current song");
		menuItemSave.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						String wd = System.getProperty("user.dir");
						JFileChooser chooser = new JFileChooser(wd);
						
						int returnVal = chooser.showSaveDialog(frame);
						if(returnVal == JFileChooser.APPROVE_OPTION) {
							InstructionBlock myInstr = new InstructionBlock(this, 
									new FileInstructionIO(FileInstructionType.SAVE, 
											chooser.getSelectedFile().getAbsolutePath()));
							receiveInstruction(myInstr);
						}
					}
				});
		
		JMenuItem menuItemPrint = new JMenuItem("Print");
		menuItemPrint.setMnemonic(KeyEvent.VK_P);
		menuItemPrint.setToolTipText("Print song");
		menuItemPrint.addActionListener(
			new ActionListener() {
				InstructionBlock myInstr = new InstructionBlock(this, new FileInstructionPrint(_mainPanel.getScoreImage()));
				public void actionPerformed(ActionEvent event) {
					receiveInstruction(myInstr);
				}
			});
		
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuItemExit.setMnemonic(KeyEvent.VK_X);
		menuItemExit.setToolTipText("Exit application");
		menuItemExit.addActionListener(
			new ActionListener() {
				InstructionBlock myInstr = new InstructionBlock(this, new FileInstructionIO(FileInstructionType.EXIT, ""));
				public void actionPerformed(ActionEvent event) {
					receiveInstruction(myInstr);
				}
			});

		file.add(menuItemNew);
		file.add(menuItemOpen);
		file.add(menuItemSave);
		file.add(menuItemPrint);
		file.add(menuItemExit);
		//----------------EDIT----------------
		JMenu edit = new JMenu("Edit");
		edit.setMnemonic(KeyEvent.VK_E);
		
		JMenuItem menuItemUndo = new JMenuItem("Set Tempo");
		menuItemUndo.setMnemonic(KeyEvent.VK_T);
		menuItemUndo.setToolTipText("Set the play tempo");
		menuItemUndo.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					String value = JOptionPane.showInputDialog(frame, "Enter tempo:", ArrangerConstants.TEMPO);
					
					int newTempo = 0;
					try {
						newTempo = Integer.parseInt(value);
					}
					catch (Exception e) {
						newTempo = 100;
					}
					
					ArrangerConstants.setTempo(newTempo);
				}
			});

		JMenuItem menuItemRedo = new JMenuItem("Append Measure");
		menuItemRedo.setMnemonic(KeyEvent.VK_M);
		menuItemRedo.setToolTipText("Append a measure to the piece");
		menuItemRedo.addActionListener(
			new ActionListener() {
				InstructionBlock myInstr = new InstructionBlock(this, new EditInstruction(null, EditInstructionType.INSERT, EditType.MEASURE));
				
				public void actionPerformed(ActionEvent event) {
					receiveInstruction(myInstr);
				}
			});
		
		edit.add(menuItemUndo);
		edit.add(menuItemRedo);
		
		menuBar.add(file);
		menuBar.add(edit);
		setJMenuBar(menuBar);
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {
		_mainPanel.keyReleased(e);
	}
	
	public void receiveInstruction(InstructionBlock instr) {
		if (instr.isEmpty())
			return;
		
		Instruction firstInstr = instr.getInstructions().iterator().next();
		if (firstInstr instanceof GUIInstruction) {
			// logic manager to GUI
			_mainPanel.interpretInstrBlock(instr);
		}
		else {
			// GUI to logic manager
			_logicManager.interpretInstrBlock(instr);
			_mainPanel.updateScore();
		}
	}
	
	public static void main(String[] args){
		new Main();
	}
}
