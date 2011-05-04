package arranger;

import gui.MainPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logic.ArrangerXMLParser;
import logic.ArrangerXMLWriter;
import logic.LogicManager;
import logic.MidiAPI;
import logic.Editor;
import music.Piece;
import instructions.*;

/*
 * Main handles delegations of tasks between
 */
public class Main extends JFrame implements InstructionListener {

	MidiAPI _api;
	MainPanel _mainPanel;
	
	ArrangerXMLParser _parser;
	ArrangerXMLWriter _writer;

	//#$#$#$#$#$#$#$#$#$#$#$#$#$##$#$# EVAN TEST #$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$
	Editor _editor;
	LogicManager _logicManager;

	Piece _piece;

	public Main(){
		super("Music Arranger");
		
		_api = new MidiAPI(30);

		//#$#$#_parser = new ArrangerXMLParser();
		_writer = new ArrangerXMLWriter();


		//#$#$#$#$#$#$#$#$#$#$#$#$#$##$#$# EVAN TEST #$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$
		// ######################################################
		_piece = new tests.LongMelodyPiece();


		//#$#$#$#$#$#$#$#$#$#$#$#$#$##$#$# EVAN TEST #$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$
		_logicManager = new LogicManager(_piece);
		_editor = _logicManager.getEditor();
		
		_parser = new ArrangerXMLParser(_editor);
		
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
				public void actionPerformed(ActionEvent event) {
					
				}
			});
		
		JMenuItem menuItemOpen = new JMenuItem("Open");
		menuItemOpen.setMnemonic(KeyEvent.VK_O);
		menuItemOpen.setToolTipText("Open XML file");
		menuItemOpen.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					
				}
			});
		
		JMenuItem menuItemSave = new JMenuItem("Save");
		menuItemSave.setMnemonic(KeyEvent.VK_S);
		menuItemSave.setToolTipText("Save current song");
		menuItemSave.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					System.out.println("SAVE FILE");
					try {
						_writer.write(_piece, "saved.xml");
					}
					catch (Exception e) {
						System.out.println("Error saving: " + e);
					}
				}
			});
		
		JMenuItem menuItemPrint = new JMenuItem("Print");
		menuItemPrint.setMnemonic(KeyEvent.VK_X);
		menuItemPrint.setToolTipText("Print song");
		menuItemPrint.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					System.exit(0);
				}
			});
		
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuItemExit.setMnemonic(KeyEvent.VK_X);
		menuItemExit.setToolTipText("Exit application");
		menuItemExit.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					System.exit(0);
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
		// delegate instruction
		if (instr instanceof EditInstruction) {
			EditInstruction editInstr = (EditInstruction) instr;
			_logicManager.interpretEditInstr(editInstr);
			_mainPanel.updateScore();
		}
		else if (instr instanceof FileInstruction) {
			
			
			_mainPanel.repaint();
		}
		else if (instr instanceof PlaybackInstruction) {
			PlaybackInstruction playInstr = (PlaybackInstruction) instr;
			
			switch (playInstr.getPlaybackType()) {
			case START:
				_api.playPiece(_piece);
				break;
			case STOP:
				_api.stopPlayback();
				break;
			}
		}
		else {
			System.out.println("Instruction unrecognized: " + instr);
			System.exit(1);
		}
	}
	
	public static void main(String[] args){
		new Main();
	}
}
