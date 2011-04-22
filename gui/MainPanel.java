package gui;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

public class MainPanel extends JPanel implements MouseListener, MouseMotionListener{
	
	ArrayList<Toolbar> _toolbars;
	Toolbar _modeToolbar, _noteToolbar, _playToolbar;
	ScoreWindow _scoreWindow;
	
	
	public MainPanel() {
		_toolbars = new ArrayList<Toolbar>();
		
		// add left hand side toolbar with buttons for input modes
		_modeToolbar = new ModeToolbar();
		
		
		_noteToolbar = new NoteToolbar();
		
		
		
		_playToolbar = new PlaybackToolbar();
		
		
		_scoreWindow = new ScoreWindow();
		
		
		// add toolbars to list of toolbars
		_toolbars.add(_modeToolbar);
		_toolbars.add(_noteToolbar);
		_toolbars.add(_playToolbar);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
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
	
	/* Handle a mouse press on a toolbar or the score window
	 * 
	 */
	public void mousePressed(MouseEvent e) {
		// clicked on which toolbar?
    	Toolbar tbar = mouseEventToolbar(e);
		if (tbar == null) {
			// clicked on score window
			_scoreWindow.mousePressed(e);
		}
		else {
			// clicked on a toolbar
			tbar.mousePressed(e);
		}
	}

	/* Handle a mouse release on a toolbar or the score window
	 * 
	 */
	public void mouseReleased(MouseEvent e) {
    	// clicked on which toolbar?
    	Toolbar tbar = mouseEventToolbar(e);
		if (tbar == null) {
			// clicked on score window
			_scoreWindow.mouseClicked(e);
		}
		else {
			// clicked on a toolbar
			tbar.mouseReleased(e);
		}
	}

	public void mouseEntered(MouseEvent e) {
    	
	}

	public void mouseExited(MouseEvent e) {
    	
	}

	/* Handle a mouse click on a toolbar or the score window
	 * 
	 */
	public void mouseClicked(MouseEvent e) {
    	// clicked on which toolbar?
    	Toolbar tbar = mouseEventToolbar(e);
		if (tbar == null) {
			// clicked on score window
			_scoreWindow.mouseClicked(e);
		}
		else {
			// clicked on a toolbar
			tbar.mouseClicked(e);
		}
	}
	
	/*
	 * Returns: the toolbar on which the mouse event occurred
	 */
	private Toolbar mouseEventToolbar(MouseEvent e) {
		Iterator<Toolbar> iter = _toolbars.iterator();
		while (iter.hasNext()) {
			Toolbar drawer = iter.next();
			
			if (drawer.hitTestPoint(e.getX(), e.getY())) {
				// mouse clicked on this object
				return drawer;
			}
		}
		
		// mouse event was not on a toolbar
		return null;
	}
	
	public void mouseDragged(MouseEvent e) {
		// clicked on which toolbar?
    	Toolbar tbar = mouseEventToolbar(e);
		if (tbar == null) {
			// clicked on score window
			_scoreWindow.mouseDragged(e);
		}
		else {
			// clicked on a toolbar
			tbar.mouseDragged(e);
		}
		
		repaint();
	}
	
	public void mouseMoved(MouseEvent e) {
		
	}
}