package gui;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public class MainPanel extends JPanel {
	
	ArrayList<Drawable> _toolbars;
	
	public MainPanel() {
		_toolbars = new ArrayList<Drawable>();
		
		// add left hand side toolbar with buttons for input modes
		ModeToolbar _modeToolbar = new ModeToolbar();
		
		
		NoteToolbar _noteToolbar = new NoteToolbar();
		
		
		ScoreWindow _scoreWindow = new ScoreWindow();
		
		
		PlaybackToolbar _playToolbar = new PlaybackToolbar();
		
		
		
		// add all to main panel
		_toolbars.add(_modeToolbar);
		_toolbars.add(_noteToolbar);
		_toolbars.add(_scoreWindow);
		_toolbars.add(_playToolbar);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Iterator<Drawable> iter = _toolbars.iterator();
		while (iter.hasNext()) {
			Drawable drawer = iter.next();
			drawer.drawSelf(g);
		}
	}
}