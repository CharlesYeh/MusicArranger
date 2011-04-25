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
	
	ArrangerXMLParser _parser;
	ArrangerXMLWriter _writer;
	
	Piece _piece;
	
	public Main(){
		super("Music Arranger");
		
		_parser = new ArrangerXMLParser();
		_writer = new ArrangerXMLWriter();
		
		// ######################################################
		_piece = new test.TestPiece();
		
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