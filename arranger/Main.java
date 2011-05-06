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
		
		_api = new MidiAPI(30);


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
				Instruction myInstr = new FileInstructionNew(this, clefList, 30, 4, 4, 0, true);
				public void actionPerformed(ActionEvent event) {
					receiveInstruction(myInstr);
				}
			});
		
		JMenuItem menuItemOpen = new JMenuItem("Open");
		menuItemOpen.setMnemonic(KeyEvent.VK_O);
		menuItemOpen.setToolTipText("Open XML file");
		menuItemOpen.addActionListener(
			new ActionListener() {
				Instruction myInstr = new FileInstructionIO(this, FileInstructionType.OPEN, "open.xml");
				public void actionPerformed(ActionEvent event) {
					receiveInstruction(myInstr);
				}
			});
		
		JMenuItem menuItemSave = new JMenuItem("Save");
		menuItemSave.setMnemonic(KeyEvent.VK_S);
		menuItemSave.setToolTipText("Save current song");
		menuItemSave.addActionListener(
			new ActionListener() {
				Instruction myInstr = new FileInstructionIO(this, FileInstructionType.SAVE, "saved.xml");
				public void actionPerformed(ActionEvent event) {
					receiveInstruction(myInstr);
				}
			});
		
		JMenuItem menuItemPrint = new JMenuItem("Print");
		menuItemPrint.setMnemonic(KeyEvent.VK_X);
		menuItemPrint.setToolTipText("Print song");
		menuItemPrint.addActionListener(
			new ActionListener() {
				Instruction myInstr = new FileInstructionIO(this, FileInstructionType.PRINT, "");
				public void actionPerformed(ActionEvent event) {
					receiveInstruction(myInstr);
				}
			});
		
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuItemExit.setMnemonic(KeyEvent.VK_X);
		menuItemExit.setToolTipText("Exit application");
		menuItemExit.addActionListener(
			new ActionListener() {
				Instruction myInstr = new FileInstructionIO(this, FileInstructionType.EXIT, "");
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
		menuBar.add(file);
		
		setJMenuBar(menuBar);
	}
	
	public void receiveInstruction(Instruction instr) {
		if (instr instanceof PlaybackInstruction) {
			PlaybackInstruction playInstr = (PlaybackInstruction) instr;
			
			switch (playInstr.getType()) {
			case START:
				_api.playPiece(_piece);
				break;
			case STOP:
				_api.stopPlayback();
				break;
			}
		}
		else {
			_logicManager.interpretInstr(instr);
			_mainPanel.updateScore();
		}
	}
	
	public static void main(String[] args){
		new Main();
	}
}
