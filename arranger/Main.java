package arranger;

import gui.MainPanel;

import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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

/*
 * Main handles delegations of tasks between
 */
public class Main extends JFrame implements InstructionListener {

	MidiAPI _api;
	MainPanel _mainPanel;

	Editor _editor;
	LogicManager _logicManager;

	Piece _piece;

	public Main(){
		super("Music Arranger");

		//#$#$#$#$#$#$#$#$#$#$#$#$#$##$#$# EVAN TEST #$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$
		// ######################################################
		_piece = new tests.LongMelodyPiece();


		//#$#$#$#$#$#$#$#$#$#$#$#$#$##$#$# EVAN TEST #$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$
		_logicManager = new LogicManager(_piece);
		_editor = _logicManager.getEditor();


		ArrangerConstants.WINDOW_WIDTH = 800;
		ArrangerConstants.WINDOW_HEIGHT = 600;

		_mainPanel = new MainPanel(_piece);
		_mainPanel.addInstructionListener(this);
		this.add(_mainPanel);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(ArrangerConstants.WINDOW_WIDTH, ArrangerConstants.WINDOW_HEIGHT);
		this.setVisible(true);

		addMenuBar();
	}

	public void addMenuBar(){
		JMenuBar menuBar = new JMenuBar();

		//----------------FILE----------------
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		// new file
		//Instruction instrNew = new FileInstruction();

		JMenuItem menuItemNew = new JMenuItem("New");
		menuItemNew.setMnemonic(KeyEvent.VK_N);
		menuItemNew.setToolTipText("New song");
		menuItemNew.addActionListener(
			new ActionListener() {
				private List<Clef> clefList = new ArrayList<Clef>();
				{
					// TODO: THIS IS STILL ALL CONSTANTS
					Clef trebleClef = new Clef(ClefName.GCLEF, -2);
					Clef bassClef = new Clef(ClefName.FCLEF, 2);
					clefList.add(trebleClef);
					clefList.add(bassClef);
				}

				InstructionBlock myInstr = new InstructionBlock(this, new FileInstructionNew(clefList, 30, 4, 4, 0, true));

				public void actionPerformed(ActionEvent event) {
					receiveInstruction(myInstr);
				}
			});

		JMenuItem menuItemOpen = new JMenuItem("Open");
		menuItemOpen.setMnemonic(KeyEvent.VK_O);
		menuItemOpen.setToolTipText("Open XML file");
		menuItemOpen.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					String path = JOptionPane.showInputDialog("Open from what path?");

					// error checking
					if (path == null && path.length() == 0) {
						System.out.println("Open path cannot be empty");
					}

					InstructionBlock myInstr = new InstructionBlock(this, new FileInstructionIO(FileInstructionType.OPEN, path));
					receiveInstruction(myInstr);
				}
			});

		JMenuItem menuItemSave = new JMenuItem("Save");
		menuItemSave.setMnemonic(KeyEvent.VK_S);
		menuItemSave.setToolTipText("Save current song");
		menuItemSave.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					String path = JOptionPane.showInputDialog("Save to what path?");

					// error checking
					if (path == null && path.length() == 0) {
						System.out.println("Save path cannot be empty");
					}

					InstructionBlock myInstr = new InstructionBlock(this, new FileInstructionIO(FileInstructionType.SAVE, path));
					receiveInstruction(myInstr);
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

		JMenuItem menuItemUndo = new JMenuItem("Undo");
		menuItemUndo.setMnemonic(KeyEvent.VK_U);
		menuItemUndo.setToolTipText("Undo last action");
		menuItemUndo.addActionListener(
			new ActionListener() {
				InstructionBlock myInstr = new InstructionBlock(this, new FileInstructionIO(FileInstructionType.EXIT, ""));
				public void actionPerformed(ActionEvent event) {
					receiveInstruction(myInstr);
				}
			});

		JMenuItem menuItemRedo = new JMenuItem("Redo");
		menuItemRedo.setMnemonic(KeyEvent.VK_R);
		menuItemRedo.setToolTipText("Redo last action");
		menuItemRedo.addActionListener(
			new ActionListener() {
				InstructionBlock myInstr = new InstructionBlock(this, new FileInstructionIO(FileInstructionType.EXIT, ""));
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

	public void receiveInstruction(InstructionBlock instr) {
		_logicManager.interpretInstrBlock(instr);
		_mainPanel.updateScore();
	}

	public static void main(String[] args){
		new Main();
	}
}
