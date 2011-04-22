package gui;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.events.MouseListener;

public class MainPanel extends JPanel implements MouseListener{
	
	ArrayList<Toolbar> _toolbars;
	Toolbar _modeToolbar, _noteToolbar, _playToolbar;
	ScoreWindow _scoreWindow;
	
	
	public MainPanel() {
		_toolbars = new ArrayList<Drawable>();
		
		// add left hand side toolbar with buttons for input modes
		_modeToolbar = new ModeToolbar();
		
		
		_noteToolbar = new NoteToolbar();
		
		
		
		_playToolbar = new PlaybackToolbar();
		
		
		_scoreWindow = new ScoreWindow();
		
		
		// add toolbars to list of toolbars
		_toolbars.add(_modeToolbar);
		_toolbars.add(_noteToolbar);
		_toolbars.add(_playToolbar);
		repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		_scoreWindow.drawSelf(g);
		
		Iterator<Toolbar> iter = _toolbars.iterator();
		while (iter.hasNext()) {
			Drawable drawer = iter.next();
			drawer.drawSelf(g);
		}
	}
	
	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
    	
	}

	public void mouseEntered(MouseEvent e) {
    	
	}

	public void mouseExited(MouseEvent e) {
    	
	}

	public void mouseClicked(MouseEvent e) {
    	// clicked on which toolbar?
    	
		
	}
	
	/*
	 * Returns: the tool which the user clicked/pressed
	 */
	private void toolbarEvent(MouseEvent e) {
		Iterator<Toolbar> iter = _toolbars.iterator();
		while (iter.hasNext()) {
			Toolbar drawer = iter.next();
			if (drawer.hitTestObject(e.getX(), e.getY())) {
				// mouse clicked on this object
				
				return drawer;
			}
		}
	}
}