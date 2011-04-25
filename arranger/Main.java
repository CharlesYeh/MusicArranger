package arranger;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gui.MainPanel;
import logic.*;
import music.*;

/* 
 * Main handles delegations of tasks between 
 */
public class Main extends JFrame{
	
	XMLParser _parser;
	XMLWriter _writer;
	
	Piece _piece;
	
	public Main(){
		super("Music Arranger");
		
		_parser = new XMLParser();
		_writer = new XMLWriter();
		
		// ######################################################
		_piece = new test.TestPiece();
		
		
		MainPanel mainPanel = new MainPanel(_piece);
		this.add(mainPanel);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 600);
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