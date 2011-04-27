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
import logic.Editor;
import music.Piece;
import instructions.*;

/*
 * Main handles delegations of tasks between
 */
public class Main extends JFrame{

	ArrangerXMLParser _parser;
	ArrangerXMLWriter _writer;

	//#$#$#$#$#$#$#$#$#$#$#$#$#$##$#$# EVAN TEST #$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$
	Editor _editor;

	Piece _piece;

	public Main(){
		super("Music Arranger");


		//#$#$#_parser = new ArrangerXMLParser();
		_writer = new ArrangerXMLWriter();


		//#$#$#$#$#$#$#$#$#$#$#$#$#$##$#$# EVAN TEST #$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$
		_editor = new Editor();
		_parser = new ArrangerXMLParser(_editor);

		// ######################################################
		_piece = new test.SimplePiece();


		//#$#$#$#$#$#$#$#$#$#$#$#$#$##$#$# EVAN TEST #$#$#$#$#$#$#$#$#$#$#$#$#$#$#$#$
		_editor.setPiece(_piece);

		ArrangerConstants.WINDOW_WIDTH = 800;
		ArrangerConstants.WINDOW_HEIGHT = 600;

		MainPanel mainPanel = new MainPanel(_piece);
		this.add(mainPanel);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(ArrangerConstants.WINDOW_WIDTH, ArrangerConstants.WINDOW_HEIGHT);
		this.setVisible(true);

		addMenuBar();
	}

	public void addMenuBar(){
		JMenuBar menubar = new JMenuBar();

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem eMenuItem = new JMenuItem("Exit");
		eMenuItem.setMnemonic(KeyEvent.VK_C);
		eMenuItem.setToolTipText("Exit application");
		eMenuItem.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					System.exit(0);
				}
			});

		file.add(eMenuItem);
	}

	public void receiveInstruction(Instruction instr) {
		// delegate instruction

	}

	public static void main(String[] args){
		new Main();
	}
}